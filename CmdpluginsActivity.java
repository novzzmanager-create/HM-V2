package com.hypers.hm;
import com.hypers.hm.ExecEngine;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Build;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Message;
import android.os.SystemClock;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.*;
import com.facebook.shimmer.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;
import org.lsposed.hiddenapibypass.library.*;
import retrofit2.*;
import rikka.shizuku.api.*;
import rikka.shizuku.provider.*;
import rikka.shizuku.Shizuku;
import android.content.pm.PackageManager;

import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import java.lang.Process;



public class CmdpluginsActivity extends AppCompatActivity {
	
	private final Shizuku.OnRequestPermissionResultListener REQUEST_PERMISSION_RESULT_LISTENER =
	new Shizuku.OnRequestPermissionResultListener() {
		@Override
		public void onRequestPermissionResult(int requestCode, int grantResult) {
			
			if (requestCode == 1000) {
				if (grantResult == PackageManager.PERMISSION_GRANTED) {
					
					android.widget.Toast.makeText(getApplicationContext(),
					"Shizuku Permission Granted", 0).show();
					
				} else {
					
					android.widget.Toast.makeText(getApplicationContext(),
					"Shizuku Permission Denied", 0).show();
					
				}
			}
			
		}
	};
	private String pluginss = "";
	private String propName = "";
	private String prop = "";
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private FrameLayout linear3;
	private ImageView imageview1;
	private TextView textview2;
	private ScrollView vscroll1;
	private LinearLayout linear5;
	private LinearLayout linear4;
	private TextView textview1;
	private LinearLayout linear11;
	private ImageView imageview4;
	private TextView textview3;
	
	private SharedPreferences modules;
	private SharedPreferences zipplug;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.cmdplugins);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		imageview1 = findViewById(R.id.imageview1);
		textview2 = findViewById(R.id.textview2);
		vscroll1 = findViewById(R.id.vscroll1);
		linear5 = findViewById(R.id.linear5);
		linear4 = findViewById(R.id.linear4);
		textview1 = findViewById(R.id.textview1);
		linear11 = findViewById(R.id.linear11);
		imageview4 = findViewById(R.id.imageview4);
		textview3 = findViewById(R.id.textview3);
		modules = getSharedPreferences("modules", Activity.MODE_PRIVATE);
		zipplug = getSharedPreferences("zipplug", Activity.MODE_PRIVATE);
		
		linear11.setOnClickListener(_v -> finish());
	}
	
	private void initializeLogic() {
		androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(
		linear1,
		(v, insets) -> {
			
			androidx.core.graphics.Insets systemBars =
			insets.getInsets(
			androidx.core.view.WindowInsetsCompat.Type.systemBars()
			);
			
			v.setPadding(
			systemBars.left,
			systemBars.top,
			systemBars.right,
			systemBars.bottom
			);
			
			return insets;
		}
		);
		getWindow().setDecorFitsSystemWindows(false);
		
		if (Build.VERSION.SDK_INT >= 30) {
			getWindow().setDecorFitsSystemWindows(true);
		}
		
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#131313")));
		linear11.setVisibility(View.GONE);
		_sizetetap();
		linear11.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)35, (int)0, 0x7081E4F6, 0xFF212121));
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffregular.ttf"), 2);
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffbold.ttf"), 2);
		String pluginPath = getIntent().getStringExtra("plugin_path");
		String nameOnly = zipplug.getString("nameOnly", "").trim();
		
		try {
			File pluginDir = new File(pluginPath);
			File propFile = new File(pluginDir, "hypers.prop");
			File customizeFile = new File(pluginDir, "customize.sh");
			
			textview1.setText(parseAnsi("CHECKING PLUGIN (SMART MODE)...\n\n", true));
			
			// 1. Validasi File via execSmart
			// Kita pake perintah shell standar buat ngecek file
			boolean propExists = !execSmart("[ -f \"" + propFile.getAbsolutePath() + "\" ] && echo 'OK'").isEmpty();
			boolean customizeExists = !execSmart("[ -f \"" + customizeFile.getAbsolutePath() + "\" ] && echo 'OK'").isEmpty();
			
			// Cari zyrexx.function
			String funcCheck = execSmart("find \"" + pluginPath + "\" -type f -iname \"zyrexx.function\" | head -n 1");
			boolean funcExists = funcCheck != null && !funcCheck.trim().isEmpty();
			
			textview1.append(parseAnsi(
			"[INFO]\n" +
			"hypers.prop     : " + (propExists ? "FOUND" : "MISSING") + "\n" +
			"customize.sh    : " + (customizeExists ? "FOUND" : "MISSING") + "\n" +
			"zyrexx.function : " + (funcExists ? "FOUND" : "MISSING") + "\n\n", false));
			
			if (!propExists || !customizeExists || !funcExists) {
				textview1.append(parseAnsi("[ERROR] Required file not found!\n", true));
				execSmart("rm -rf \"" + pluginPath + "\""); // Hapus folder sampah
				linear11.setVisibility(View.VISIBLE);
				return;
			}
			
			// 2. Ambil Property via execSmart (Cat)
			String propRaw = execSmart("cat \"" + propFile.getAbsolutePath() + "\"");
			String pluginId = "unknown", pluginName = "unknown", authors = "unknown";
			
			if (propRaw != null && !propRaw.isEmpty()) {
				for (String line : propRaw.split("\n")) {
					line = line.trim();
					if (line.isEmpty() || line.startsWith("#")) continue;
					if (line.contains("=")) {
						String[] parts = line.split("=", 2);
						String key = parts[0].trim();
						String value = parts[1].trim();
						if (key.equalsIgnoreCase("id")) pluginId = value;
						if (key.equalsIgnoreCase("name")) pluginName = value;
						if (key.equalsIgnoreCase("author")) authors = value;
					}
				}
			}
			
			textview1.append(parseAnsi("[VALID]\nName   : " + pluginName + "\nID     : " + pluginId + "\nAuthor : " + authors + "\n\n", true));
			
			// 3. Persiapan Eksekusi Script Utama
			String mainScriptPath = customizeFile.getAbsolutePath();
			
			// Kita set environment standar Android tanpa BusyBox
			String globalEnv = "export HYPERSDIR='/data/local/tmp/HYPERS/data/Plugins'; " +
			"export MODDIR='/data/local/tmp/HYPERS/data/Plugins'; " +
			"export PATH='/system/bin:/vendor/bin:/product/bin:$PATH'; ";
			
			String fullCommand = globalEnv + "cd \"" + pluginPath + "\" && sh \"" + mainScriptPath + "\"";
			
			new Thread(() -> {
				try {
					runOnUiThread(() -> {
						textview1.append(parseAnsi("[RUNNING SCRIPT...]\n\n", true));
						vscroll1.post(() -> vscroll1.fullScroll(View.FOCUS_DOWN));
					});
					
					// Kasih izin eksekusi
					execSmart("chmod +x \"" + mainScriptPath + "\"");
					
					// Gunakan Process Builder agar bisa streaming output (Hybrid Root/Shizuku)
					java.lang.Process process;
					// Di sini kita manual su/sh karena execSmart biasanya cuma return String
					if (isRootAvailable()) { 
						process = Runtime.getRuntime().exec(new String[]{"su", "-c", fullCommand});
					} else {
						process = ExecEngine.newProcess(new String[]{"sh", "-c", fullCommand});
					}
					
					// Baca Output (STDOUT) & Error (STDERR) secara real-time
					captureStreamToTerminal(process.getInputStream());
					captureStreamToTerminal(process.getErrorStream());
					
					int exitVal = process.waitFor();
					
					runOnUiThread(() -> {
						textview1.append(parseAnsi("\n[EXIT CODE: " + exitVal + "]\n", true));
						textview1.append(parseAnsi("[DONE]\n", true));
						linear11.setVisibility(View.VISIBLE);
						
						runScriptsRecursive(pluginPath);
						savePluginStatus(nameOnly, zipplug.getString("status", "").trim());
						vscroll1.post(() -> vscroll1.fullScroll(View.FOCUS_DOWN));
					});
					
				} catch (Exception e) {
					runOnUiThread(() -> {
						textview1.append(parseAnsi("[ERROR] " + e.getMessage() + "\n", true));
						linear11.setVisibility(View.VISIBLE);
					});
				}
			}).start();
			
		} catch (Exception e) {
			textview1.setText(parseAnsi("[FATAL] " + e.getMessage() + "\n", true));
			linear11.setVisibility(View.VISIBLE);
		}
	}
	
	
	@Override
	public void onBackPressed() {
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (virtualConfig != null && virtualMetrics != null) {
			getResources().updateConfiguration(virtualConfig, virtualMetrics);
		}
		_sizetetap();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		_sizetetap();
	}
	public void _shizukuexec() {
	}
	public boolean isShizukuReady() {
		
		if (!Shizuku.pingBinder()) return false;
		
		if (Shizuku.checkSelfPermission() != PackageManager.PERMISSION_GRANTED) {
			
			
			return false;
		}
		
		return true;
	}
	{
	}
	
	
	public void _AksesShizuku() {
		Shizuku.addRequestPermissionResultListener(REQUEST_PERMISSION_RESULT_LISTENER);
		
		try {
			PackageManager packageManager = getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo("moe.shizuku.privileged.api", PackageManager.GET_ACTIVITIES);
			if (packageInfo != null) {
				
				if (Shizuku.pingBinder()) {
					if (Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
						
					} else {
						
					}
				}
			} else {
				
				
			}
		} catch (NameNotFoundException e) {
			
			
		}
	}
	
	
	public void _var() {
	}
	private java.lang.Process process;
	{
	}
	
	
	public void _sizetetap() {
	}
	private static final int VIRTUAL_WIDTH = 625;
	private static final int VIRTUAL_HEIGHT = 1045;
	private Configuration virtualConfig;
	private DisplayMetrics virtualMetrics;
	
	@Override
	protected void attachBaseContext(Context newBase) {
		Context wrapped = wrapContext(newBase);
		virtualConfig = new Configuration(wrapped.getResources().getConfiguration());
		virtualMetrics = new DisplayMetrics();
		virtualMetrics.setTo(wrapped.getResources().getDisplayMetrics());
		super.attachBaseContext(wrapped);
	}
	
	private Context wrapContext(Context base) {
		DisplayMetrics dm = base.getResources().getDisplayMetrics();
		float scale = (float) dm.widthPixels / VIRTUAL_WIDTH;
		int targetDpi = (int) (scale * 210);
		
		Configuration config = new Configuration(base.getResources().getConfiguration());
		config.densityDpi = targetDpi;
		config.fontScale = 1.0f;
		config.screenWidthDp = VIRTUAL_WIDTH;
		config.screenHeightDp = VIRTUAL_HEIGHT;
		config.smallestScreenWidthDp = VIRTUAL_WIDTH;
		
		Context context = base.createConfigurationContext(config);
		
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		metrics.density = targetDpi / 160f;
		metrics.densityDpi = targetDpi;
		metrics.scaledDensity = metrics.density;
		metrics.widthPixels = dm.widthPixels;
		metrics.heightPixels = dm.heightPixels;
		
		return context;
	}
	
	@Override
	public void applyOverrideConfiguration(Configuration overrideConfiguration) {
		if (overrideConfiguration != null && virtualConfig != null) {
			overrideConfiguration.setTo(virtualConfig);
		}
		super.applyOverrideConfiguration(overrideConfiguration);
	}
	
	@Override
	public Resources getResources() {
		Resources res = super.getResources();
		if (virtualConfig != null && virtualMetrics != null) {
			res.updateConfiguration(virtualConfig, virtualMetrics);
		}
		return res;
	}
	{
	}
	
	
	public void _aksesPintarCmd() {
	}
	public boolean isRootAvailable() {
		try {
			process = Runtime.getRuntime().exec("su");
			DataOutputStream os = new DataOutputStream(process.getOutputStream());
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
			return process.exitValue() == 0;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean isShizukuAvailable() {
		try {
			return rikka.shizuku.Shizuku.pingBinder();
		} catch (Throwable e) {
			return false;
		}
	}
	
	public String execCommand(String cmd) {
		StringBuilder output = new StringBuilder();
		
		try {
			
			
			if (isRootAvailable()) {
				process = Runtime.getRuntime().exec(new String[]{"su", "-c", cmd});
			} else {
				// fallback ke shizuku (pastikan kamu sudah punya method shizukuExec)
				return shizukuExec(cmd);
			}
			
			BufferedReader reader = new BufferedReader(
			new InputStreamReader(process.getInputStream()));
			
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line).append("\n");
			}
			
			process.waitFor();
			
		} catch (Exception e) {
			return "Error: " + e.toString();
		}
		
		return output.toString();
	}
	
	public String execSmart(String cmd) {
		if (isRootAvailable()) {
			return execCommand(cmd);
		} 
		
		com.hypers.hm.debug.ADB adb = com.hypers.hm.debug.ADB.getInstance(this);
		if (adb.started.getValue() != null && adb.started.getValue()) {
			try {
				adb.sendToShellProcess(cmd);
				return "Executed via ADB"; 
			} catch (Exception e) {
			}
		}
		
		// 3. Cek Shizuku (Fallback Terakhir)
		if (isShizukuAvailable()) {
			return shizukuExec(cmd);
		} 
		
		// 4. Jika semua gagal
		return "Error: No Root, ADB, or Shizuku available";
	}
	{
	}
	
	
	public void _getColorCommand() {
	}
	private ArrayList<String> commandHistory = new ArrayList<>();
	private int historyIndex = -1;
	
	private Handler cursorHandler = new Handler();
	private boolean cursorVisible = true;
	
	private SpannableStringBuilder parseAnsi(String text, boolean forceWhite) {
		SpannableStringBuilder builder = new SpannableStringBuilder();
		
		Pattern pattern = Pattern.compile("\\u001B\\[([0-9;]+)m");
		Matcher matcher = pattern.matcher(text);
		
		int lastEnd = 0;
		
		int fgColor = Color.WHITE;
		int bgColor = Color.TRANSPARENT;
		
		while (matcher.find()) {
			String before = text.substring(lastEnd, matcher.start());
			
			int start = builder.length();
			builder.append(before);
			int end = builder.length();
			
			if (start < end) {
				builder.setSpan(new ForegroundColorSpan(fgColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				builder.setSpan(new ForegroundColorSpan(fgColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			
			if (!forceWhite) {
				String[] codes = matcher.group(1).split(";");
				
				for (String code : codes) {
					int c;
					try { c = Integer.parseInt(code); } catch (Exception e) { continue; }
					
					if (c == 0) {
						fgColor = Color.WHITE;
						bgColor = Color.TRANSPARENT;
					} else if (c >= 30 && c <= 37) {
						fgColor = getColorBasic(c - 30);
					} else if (c >= 90 && c <= 97) {
						fgColor = getColorBright(c - 90);
					}
				}
			}
			
			lastEnd = matcher.end();
		}
		
		if (lastEnd < text.length()) {
			int start = builder.length();
			builder.append(text.substring(lastEnd));
			int end = builder.length();
			
			int finalColor = forceWhite ? Color.WHITE : fgColor;
			
			builder.setSpan(new ForegroundColorSpan(finalColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			builder.setSpan(new TypefaceSpan("sans-serif"), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		
		return builder;
	}
	
	private int getColorBasic(int i) {
		switch (i) {
			case 0: return Color.BLACK;
			case 1: return Color.RED;
			case 2: return Color.GREEN;
			case 3: return Color.YELLOW;
			case 4: return Color.BLUE;
			case 5: return Color.MAGENTA;
			case 6: return Color.CYAN;
			case 7: return Color.WHITE;
			default: return Color.WHITE;
		}
	}
	
	private int getColorBright(int i) {
		switch (i) {
			case 0: return Color.DKGRAY;
			case 1: return 0xFFFF5555;
			case 2: return 0xFF55FF55;
			case 3: return 0xFFFFFF55;
			case 4: return 0xFF5555FF;
			case 5: return 0xFFFF55FF;
			case 6: return 0xFF55FFFF;
			case 7: return Color.WHITE;
			default: return Color.WHITE;
		}
	}
	{
	}
	
	
	public void _plugins() {
	}
	public boolean fileExistsShizuku(String path) {
		String result = shizukuExecRead("[ -f \"" + path + "\" ] && echo true || echo false");
		return result != null && result.trim().equals("true");
	}
	
	public boolean validateZyrexxFunction(File dir) {
		if (dir == null || !dir.exists()) return false;
		return new File(dir, "zyrexx.function").exists();
	}
	
	public boolean validateZyrexxFunction(String zipPath) {
		try {
			java.util.zip.ZipInputStream zis = new java.util.zip.ZipInputStream(new FileInputStream(zipPath));
			java.util.zip.ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
				String name = entry.getName();
				if (name.toLowerCase().trim().endsWith("zyrexx.function")) {
					zis.close();
					return true;
				}
			}
			zis.close();
		} catch (Exception e) {
			Log.e("VALIDATE", e.toString());
		}
		return false;
	}
	
	private Boolean isRoot = null;
	
	public boolean isRootAvailableCached() {
		if (isRoot == null) {
			isRoot = isRootAvailablee();
		}
		return isRoot;
	}
	
	public boolean isRootAvailablee() {
		try {
			Process p = Runtime.getRuntime().exec("su");
			p.getOutputStream().write("exit\n".getBytes());
			p.getOutputStream().flush();
			p.waitFor();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void exec(String cmd) {
		if (isRootAvailableCached()) {
			execRoot(cmd);
		} else {
			execShizuku(cmd);
		}
	}
	
	public String execRead(String cmd) {
		if (isRootAvailableCached()) {
			return execRootRead(cmd);
		} else {
			return shizukuExecRead(cmd);
		}
	}
	
	public String shizukuExec(String cmd) {
		return shizukuExecRead(cmd);
	}
	
	public void execRoot(String cmd) {
		try {
			Process p = Runtime.getRuntime().exec("su");
			java.io.DataOutputStream os = new java.io.DataOutputStream(p.getOutputStream());
			os.writeBytes(cmd + "\n");
			os.writeBytes("exit\n");
			os.flush();
			p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String execRootRead(String cmd) {
		StringBuilder output = new StringBuilder();
		try {
			Process p = Runtime.getRuntime().exec("su");
			java.io.DataOutputStream os = new java.io.DataOutputStream(p.getOutputStream());
			os.writeBytes(cmd + "\n");
			os.writeBytes("exit\n");
			os.flush();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line).append("\n");
			}
			reader.close();
			p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output.toString();
	}
	
	public void execShizuku(String cmd) {
		try {
			ExecEngine.newProcess(new String[]{"sh", "-c", cmd});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String shizukuExecRead(String command) {
		StringBuilder output = new StringBuilder();
		try {
			Process p = ExecEngine.newProcess(new String[]{"sh", "-c", command});
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line).append("\n");
			}
			p.waitFor();
		} catch (Exception e) {
			return "";
		}
		return output.toString().trim();
	}
	
	private void deleteRecursive(File fileOrDirectory) {
		if (fileOrDirectory.isDirectory()) {
			File[] children = fileOrDirectory.listFiles();
			if (children != null) {
				for (File child : children) deleteRecursive(child);
			}
		}
		fileOrDirectory.delete();
	}
	
	public void runScriptsRecursive(String path) {
		if (path != null) runScriptsRecursive(new File(path));
	}
	
	private void runScriptsRecursive(File dir) {
		if (dir == null || !dir.exists() || !dir.isDirectory()) return;
		File postFs = new File(dir, "post-fs-data.sh");
		File service = new File(dir, "service.sh");
		
		if (postFs.exists()) {
			exec("chmod 755 \"" + postFs.getAbsolutePath() + "\" && sh \"" + postFs.getAbsolutePath() + "\"");
		}
		if (service.exists()) {
			String p = service.getAbsolutePath();
			exec("chmod 755 \"" + p + "\" && pkill -f \"" + p + "\" && nohup sh \"" + p + "\" > /dev/null 2>&1 &");
		}
		File[] files = dir.listFiles();
		if (files != null) {
			for (File f : files) runScriptsRecursive(f);
		}
	}
	
	private void savePluginStatus(String name, String status) {
		try {
			JSONObject json = new JSONObject();
			json.put("status", status);
			File targetFile = new File(Environment.getExternalStorageDirectory(), "ZYREX-TOOLS/data/" + name + ".json");
			java.io.FileOutputStream fos = new java.io.FileOutputStream(targetFile);
			fos.write(json.toString(1).getBytes());
			fos.close();
		} catch (Exception ignored) {}
	}
	
	private void captureStreamToTerminal(java.io.InputStream is) {
		new Thread(() -> {
			try (java.io.InputStreamReader reader = new java.io.InputStreamReader(is)) {
				char[] buffer = new char[1024];
				int len;
				while ((len = reader.read(buffer)) != -1) {
					String chunk = new String(buffer, 0, len);
					runOnUiThread(() -> {
						textview1.append(parseAnsi(chunk, false));
						vscroll1.post(() -> vscroll1.fullScroll(View.FOCUS_DOWN));
					});
				}
			} catch (Exception ignored) {}
		}).start();
	}
	{
	}
	
}
