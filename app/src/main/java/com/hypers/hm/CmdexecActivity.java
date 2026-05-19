package com.hypers.hm;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
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
import com.cocode.focora.*;
import com.droidx.*;
import com.facebook.shimmer.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;
import org.lsposed.hiddenapibypass.library.*;
import rikka.shizuku.api.*;
import rikka.shizuku.provider.*;
import rikka.shizuku.Shizuku;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import java.lang.Process;

public class CmdexecActivity extends AppCompatActivity {
	
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
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private ImageView imageview1;
	private LinearLayout linear5;
	private ImageView imageview2;
	private TextView textview2;
	private TextView textview3;
	private ScrollView vscroll1;
	private LinearLayout linear6;
	private TextView textview1;
	
	private SharedPreferences shellExc;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.cmdexec);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		imageview1 = findViewById(R.id.imageview1);
		linear5 = findViewById(R.id.linear5);
		imageview2 = findViewById(R.id.imageview2);
		textview2 = findViewById(R.id.textview2);
		textview3 = findViewById(R.id.textview3);
		vscroll1 = findViewById(R.id.vscroll1);
		linear6 = findViewById(R.id.linear6);
		textview1 = findViewById(R.id.textview1);
		shellExc = getSharedPreferences("shellExc", Activity.MODE_PRIVATE);
		
		imageview1.setOnClickListener(_v -> finish());
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
		imageview1.setVisibility(View.GONE);
		runExecutor();
		textview1.setMovementMethod(new android.text.method.ScrollingMovementMethod());
		_sizetetap();
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
	}
	public void _more() {
	}
	private void runExecutor() {
		try {
			String path = shellExc.getString("shell", "").trim();
			String folderPathSh = shellExc.getString("pathshell", "").trim();
			
			if (path.isEmpty() || folderPathSh.isEmpty()) {
				SketchwareUtil.showMessage(getApplicationContext(), "Path atau Script kosong!");
				return;
			}
			
			// Setup Environment standar tanpa busybox
			String globalEnv = "export HYPERSDIR='/data/local/tmp/HYPERS/data/Plugins'; " +
			"export MODDIR='/data/local/tmp/HYPERS/data/Plugins'; " +
			"export PATH='/system/bin:/vendor/bin:/product/bin:$PATH'; ";
			
			// Kita pake cd ke folder script biar path file di dalem script-nya sinkron
			String fullCommand = globalEnv + "cd \"" + folderPathSh + "\" && sh \"" + path + "\"";
			
			commandHistory.add(fullCommand);
			runnings = true;
			
			String prompt = "[HM]:~ $ ";
			textview1.append(parseAnsi(prompt + "EXECUTING VIA SMART ENGINE..." + "\n", true));
			
			new Thread(() -> {
				try {
					java.lang.Process process;
					
					// Logic Hybrid: Gunakan execSmart buat cek akses, tapi tetep pake Process untuk streaming
					if (isRootAvailable()) { 
						// Jika Root, pake su
						process = Runtime.getRuntime().exec(new String[]{"su", "-c", "echo $$; " + fullCommand});
					} else {
						// Jika Shizuku, pake Shizuku process
						process = rikka.shizuku.Shizuku.newProcess(
						new String[]{"sh", "-c", "echo $$; " + fullCommand},
						null,
						null
						);
					}
					
					// Thread khusus baca Output (STDOUT)
					new Thread(() -> {
						try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
							String line;
							boolean firstLine = true;
							while ((line = reader.readLine()) != null) {
								final String outLine = line;
								if (firstLine) {
									// Baris pertama hasil 'echo $$' adalah PID
									runOnUiThread(() -> textview1.append(parseAnsi("[HM] PID : " + outLine + "\n", true)));
									firstLine = false;
								} else {
									runOnUiThread(() -> {
										textview1.append(parseAnsi(outLine + "\n", false));
										autoScroll();
									});
								}
							}
						} catch (Exception ignored) {}
					}).start();
					
					// Thread khusus baca Error (STDERR)
					new Thread(() -> {
						try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
							String line;
							while ((line = reader.readLine()) != null) {
								final String errLine = line;
								runOnUiThread(() -> {
									textview1.append(parseAnsi(errLine + "\n", false));
									autoScroll();
								});
							}
						} catch (Exception ignored) {}
					}).start();
					
					int exitVal = process.waitFor();
					
					runOnUiThread(() -> {
						textview1.append(parseAnsi("\n[HM] Process Finished [ExitCode=" + exitVal + "]\n\n", true));
						runnings = false;
						autoScroll();
					});
					
				} catch (Exception e) {
					runOnUiThread(() -> {
						textview1.append(parseAnsi("[HM] EXECUTION ERROR: " + e.getMessage() + "\n", true));
						runnings = false;
						autoScroll();
					});
				}
			}).start();
			
		} catch (Exception e) {
			SketchwareUtil.showMessage(getApplicationContext(), "Hadehh Error: " + e.getMessage());
		}
	}
	
	{
	}
	
	
	public void _shizuku_access() {
		Shizuku.addRequestPermissionResultListener(REQUEST_PERMISSION_RESULT_LISTENER);
		
		try {
			PackageManager packageManager = getApplicationContext().getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo("moe.shizuku.privileged.api", PackageManager.GET_ACTIVITIES);
			if (packageInfo != null) {
				
				if (Shizuku.pingBinder()) {
					if (Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
						
					} else {
						
					}
				} else {
					
				}
			} else {
				
				
			}
		} catch (NameNotFoundException e) {
			
			
		}
	}
	
	
	public void _output() {
	}
	private void appendRealtime(String text) {
		textview1.post(() -> {
			textview1.append(text + "\n");
			
			// force redraw
			textview1.invalidate();
			textview1.requestLayout();
			
			// auto scroll
			if (textview1.getLayout() != null) {
				int scrollAmount = textview1.getLayout().getLineTop(textview1.getLineCount()) - textview1.getHeight();
				if (scrollAmount > 0) {
					textview1.scrollTo(0, scrollAmount);
				} else {
					textview1.scrollTo(0, 0);
				}
			}
		});
	}
	
	private void autoScroll() {
		runOnUiThread(() -> {
			textview1.invalidate();
			textview1.requestLayout();
			if (vscroll1 != null) {
				vscroll1.post(() -> vscroll1.fullScroll(View.FOCUS_DOWN));
			}
		});
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
	
	
	public void _shizukuexec() {
	}
	public boolean isShizukuReady() {
		
		if (!Shizuku.pingBinder()) return false;
		
		if (Shizuku.checkSelfPermission() != PackageManager.PERMISSION_GRANTED) {
			
			
			return false;
		}
		
		return true;
	}
	
	private String shizukuExecRead(String command) {
		StringBuilder output = new StringBuilder();
		try {
			java.lang.Process process = Shizuku.newProcess(new String[]{"sh", "-c", command}, null, null);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line).append("\n");
			}
			process.waitFor();
		} catch (Exception e) {
			return "error: " + e.getMessage();
		}
		return output.toString().trim();
	}
	
	
	public String shizukuExec(String cmd) {
		
		try {
			
			process =
			Shizuku.newProcess(new String[]{"sh","-c",cmd}, null, null);
			
			InputStream in = process.getInputStream();
			
			byte[] buffer = new byte[1024];
			int len;
			StringBuilder out = new StringBuilder();
			
			while ((len = in.read(buffer)) > 0) {
				out.append(new String(buffer, 0, len));
			}
			
			process.waitFor();
			process.destroy();
			
			return out.toString();
			
		} catch (Exception e) {
			return "";
		}
	}
	{
	}
	
	
	public void _variable() {
	}
	private boolean runnings = false;
	private Process process;
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
		int targetDpi = (int) (scale * 220);
		
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
	
}