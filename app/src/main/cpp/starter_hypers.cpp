#include <cstdio>
#include <cstdlib>
#include <fcntl.h>
#include <unistd.h>
#include <dirent.h>
#include <ctime>
#include <cstring>
#include <libgen.h>
#include <sys/stat.h>
#include <sys/system_properties.h>
#include <cerrno>
#include <string>
#include <vector>
#include <android/api-level.h>
#include <sys/types.h>
#include <signal.h>
#include <limits.h>

// Library internal bawaan project lu
#include "android.h"
#include "misc.h"
#include "selinux.h"
#include "cgroup.h"
#include "logging.h"

#define perrorf(...) fprintf(stderr, __VA_ARGS__)

#define EXIT_FATAL_SET_CLASSPATH 3
#define EXIT_FATAL_FORK 4
#define EXIT_FATAL_APP_PROCESS 5
#define EXIT_FATAL_PM_PATH 7
#define EXIT_FATAL_KILL 9

// Target package manager disesuaikan dengan manifes lu
#define PACKAGE_NAME "com.hypers.hm.privileged.api"
#define SERVER_NAME "hypers_server"
#define SERVER_CLASS_PATH "com.hypers.hm.server.HypersService"

// Fungsi mandiri untuk mendeteksi dan membersihkan sisa proses server lama
static void kill_existing_server(const char *process_name) {
    DIR *dir = opendir("/proc");
    if (!dir) return;

    struct dirent *entry;
    while ((entry = readdir(dir)) != nullptr) {
        pid_t pid = atoi(entry->d_name);
        if (pid <= 0 || pid == getpid()) continue;

        char cmdline_path[PATH_MAX];
        snprintf(cmdline_path, sizeof(cmdline_path), "/proc/%d/cmdline", pid);
        
        int fd = open(cmdline_path, O_RDONLY);
        if (fd >= 0) {
            char buf[PATH_MAX]{0};
            read(fd, buf, sizeof(buf) - 1);
            close(fd);

            // Jika cmdline cocok dengan nama server, langsung matikan
            if (strcmp(buf, process_name) == 0 || strstr(buf, SERVER_NAME) != nullptr) {
                if (kill(pid, SIGKILL) == 0) {
                    printf("[Biner] Mematikan proses menggantung lama dengan PID %d\n", pid);
                }
            }
        }
    }
    closedir(dir);
}

int main(int argc, char *argv[]) {
    printf("[Biner] Menginisialisasi jabat tangan Daemon...\n");
    fflush(stdout);

    std::string apk_path = "";

    // Bersihkan sisa server lama secara mandiri
    kill_existing_server(SERVER_NAME);

    // Lacak otomatis lokasi instalasi APK dasar
    auto f = popen("pm path " PACKAGE_NAME, "r");
    if (f) {
        char line[PATH_MAX]{0};
        if (fgets(line, PATH_MAX, f)) {
            // Memanggil fungsi trim dari misc.h asli project lu
            trim(line);
            if (strstr(line, "package:") == line) {
                apk_path = line + strlen("package:");
            }
        }
        pclose(f);
    }

    if (apk_path.empty()) {
        perrorf("[Biner] fatal: Gagal melacak base APK %s. Apakah app sudah diinstall?\n", PACKAGE_NAME);
        exit(EXIT_FATAL_PM_PATH);
    }

    // Suntik memori CLASSPATH ke lingkungan Virtual Machine Android
    if (setenv("CLASSPATH", apk_path.c_str(), 1) != 0) {
        perrorf("[Biner] fatal: Alokasi CLASSPATH memori error.\n");
        exit(EXIT_FATAL_SET_CLASSPATH);
    }

    // Lakukan FORK: Kunci utama agar shell komputer/OTG lu bisa langsung "Exit" lepas (tidak macet hang nungguin server)
    pid_t pid = fork();
    if (pid < 0) {
        perrorf("[Biner] fatal: Proses replikasi fork gagal.\n");
        exit(EXIT_FATAL_FORK);
    }

    // DAEMON CHILD PROCESS (Berjalan mandiri di sistem internal Android)
    if (pid == 0) {
        // Alihkan stdio ke null agar output server tidak mengunci console terminal komputer lu
        int null_fd = open("/dev/null", O_RDWR);
        if (null_fd >= 0) {
            dup2(null_fd, STDIN_FILENO);
            dup2(null_fd, STDOUT_FILENO);
            dup2(null_fd, STDERR_FILENO);
            close(null_fd);
        }

        chdir("/");

        // Tembus pembatasan SELinux ke konteks aman ADB shell
        if (se::setcon) {
            se::setcon("u:r:shell:s0");
        }

        // Eksekusi core virtual machine android
        std::vector<char *> exec_args;
        exec_args.push_back((char *) "/system/bin/app_process");
        exec_args.push_back((char *) "/system/bin");
        exec_args.push_back((char *) "--nice-name=hypers_server");
        exec_args.push_back((char *) SERVER_CLASS_PATH);
        exec_args.push_back(nullptr);

        execv(exec_args[0], exec_args.data());
        exit(EXIT_FATAL_APP_PROCESS);
    }

    // PARENT PROCESS (Mengirim laporan balik ke start.sh komputer)
    printf("[Biner] success: Core hypers_server berhasil didorong ke background sistem!\n");
    return 0;
}
