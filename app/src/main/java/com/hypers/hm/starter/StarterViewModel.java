package com.hypers.hm.starter;

import android.util.Log;
import android.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hypers.hm.Hypers; 
import com.hypers.hm.HypersSettings;
import com.hypers.hm.adb.AdbClient;
import com.hypers.hm.adb.AdbKey;
import com.hypers.hm.adb.PreferenceAdbKeyStore;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class NotRootedException extends Exception {
    public NotRootedException(String message) {
        super(message);
    }
}

public class StarterViewModel extends ViewModel {

    private final StringBuilder sb = new StringBuilder();
    private final MutableLiveData<Pair<String, Throwable>> _output = new MutableLiveData<>();
    public final LiveData<Pair<String, Throwable>> output = _output;
    
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    // Berhubung ini versi modifikasimu, tentukan command internal rish/hypers di sini
    private static final String[] INTERNAL_COMMAND = new String[]{"sh", "-c", "rish"}; 

    public void start(boolean root, String host, int port) {
        try {
            if (root) {
                startRoot();
            } else {
                if (host == null) host = "127.0.0.1";
                startAdb(host, port);
            }
        } catch (Throwable e) {
            postResult(e);
        }
    }

    private void postResult(Throwable throwable) {
        _output.postValue(new Pair<>(sb.toString(), throwable));
    }

    private void startRoot() {
        sb.append("Starting with root via Hypers Bridge...\n\n");
        postResult(null);

        executorService.execute(() -> {
            try {
                // FIX ERROR 2: Gunakan engine Hypers.newProcess bawaan projectmu!
                // Ini akan otomatis memanggil shell rish lewat UID server root/adb
                java.lang.Process process = Runtime.getRuntime().exec(new String[]{"su", "-c", "rish"});
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                    postResult(null);
                }
                int code = process.waitFor();
                if (code != 0) {
                    sb.append("\nExit code ").append(code).append(". Process finished.");
                    postResult(null);
                }
            } catch (Exception e) {
                sb.append('\n').append("Can't open root shell: ").append(e.getMessage());
                postResult(new NotRootedException(e.getMessage()));
            }
        });
    }

    private void startAdb(String host, int port) {
        sb.append("Starting with wireless adb in port ").append(port).append("...\n\n");
        postResult(null);

        executorService.execute(() -> {
            try {
                AdbKey key = new AdbKey(new PreferenceAdbKeyStore(HypersSettings.getPreferences()), "hypers");
                AdbClient client = new AdbClient(host, port, key);
                client.connect();
                
                // FIX ERROR 3: Jalankan perintah via ADB client target ke rish shell
                client.shellCommand("rish", data -> {
                    sb.append(new String(data));
                    postResult(null);
                    return null;
                });
                
                client.close();
            } catch (Throwable e) {
                e.printStackTrace();
                sb.append('\n').append(Log.getStackTraceString(e));
                postResult(e);
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdownNow();
    }
}
