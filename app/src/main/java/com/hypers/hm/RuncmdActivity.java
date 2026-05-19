package com.hypers.hm;
import com.hypers.hm.ExecEngine;

import android.animation.*;
import android.app.*;
import android.content.*;
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
import android.widget.EditText;
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
import com.google.android.material.textfield.*;
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




public class RuncmdActivity extends AppCompatActivity {
	
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
	private LinearLayout linear12;
	private ImageView imageview1;
	private TextView textview2;
	private LinearLayout linear9;
	private ImageView imageview3;
	private LinearLayout exec2;
	private LinearLayout exec1;
	private LinearLayout linear3;
	private FrameLayout linear4;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private ScrollView vscroll2;
	private TextInputLayout textinputlayout1;
	private EditText edittext2;
	private ImageView imageview2;
	private ScrollView vscroll1;
	private LinearLayout linear10;
	private LinearLayout linear5;
	private EditText textview1;
	private LinearLayout linear11;
	private ImageView imageview4;
	private TextView textview3;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.runcmd);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear12 = findViewById(R.id.linear12);
		imageview1 = findViewById(R.id.imageview1);
		textview2 = findViewById(R.id.textview2);
		linear9 = findViewById(R.id.linear9);
		imageview3 = findViewById(R.id.imageview3);
		exec2 = findViewById(R.id.exec2);
		exec1 = findViewById(R.id.exec1);
		linear3 = findViewById(R.id.linear3);
		linear4 = findViewById(R.id.linear4);
		linear6 = findViewById(R.id.linear6);
		linear7 = findViewById(R.id.linear7);
		linear8 = findViewById(R.id.linear8);
		vscroll2 = findViewById(R.id.vscroll2);
		textinputlayout1 = findViewById(R.id.textinputlayout1);
		edittext2 = findViewById(R.id.edittext2);
		imageview2 = findViewById(R.id.imageview2);
		vscroll1 = findViewById(R.id.vscroll1);
		linear10 = findViewById(R.id.linear10);
		linear5 = findViewById(R.id.linear5);
		textview1 = findViewById(R.id.textview1);
		linear11 = findViewById(R.id.linear11);
		imageview4 = findViewById(R.id.imageview4);
		textview3 = findViewById(R.id.textview3);
		
		linear9.setOnClickListener(_v -> {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("*/*");
			intent.addCategory(Intent.CATEGORY_OPENABLE);
			
			intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{
				"text/x-sh",
				"application/x-sh",
				"text/plain"
			});
			
			startActivityForResult(intent, 1001);
		});
		
		imageview2.setOnClickListener(_v -> {
			try {
				String command = edittext2.getText().toString().trim();
				
				// Sesuaikan dengan path dari setupBusybox lo
				final String bbPath = getFilesDir().getAbsolutePath() + "/bb";
				final String bbTmpPath = "/data/local/tmp/bb";
				
				if (command.isEmpty()) {
					SketchwareUtil.showMessage(getApplicationContext(), "Command kosong!");
					return;
				}
				
				commandHistory.add(command);
				historyIndex = commandHistory.size();
				
				edittext2.setEnabled(false);
				imageview2.setVisibility(View.GONE);
				linear11.setVisibility(View.GONE);
				runnings = true;
				
				textview1.append(parseAnsi("[HM]:~ $ " + command + "\n", true));
				
				new Thread(() -> {
					try {
						// STEP 1: Eksekusi Standard Android
						Process process = ExecEngine.newProcess(new String[]{"sh", "-c", "echo $$; " + command});
						
						// Ambil PID (Output baris pertama)
						BufferedReader pidReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
						String pidLine = pidReader.readLine();
						if (pidLine != null) {
							runOnUiThread(() -> textview1.append(parseAnsi("[HM] PID : " + pidLine + "\n", true)));
						}
						
						// Baca Output secara real-time
						captureOutput(process);
						int exitCode = process.waitFor();
						
						// STEP 2: Jika gagal (exitCode 127 = Not Found), otomatis hajar pake BusyBox
						if (exitCode == 127 || exitCode == 1) {
							runOnUiThread(() -> textview1.append(parseAnsi("[HM] Fallback: Executing via BusyBox...\n", true)));
							
							// Cek path busybox mana yang ready
							String activeBB = new File(bbPath).exists() ? bbPath : bbTmpPath;
							String bbCommand = activeBB + " " + command;
							
							Process bbProcess = ExecEngine.newProcess(new String[]{"sh", "-c", bbCommand});
							
							captureOutput(bbProcess);
							bbProcess.waitFor();
						}
						
						runOnUiThread(() -> {
							textview1.append(parseAnsi("\n[HM] Done Execute [HasExited=true]\n\n", true));
							finishExecution();
						});
						
					} catch (Exception e) {
						runOnUiThread(() -> {
							textview1.append(parseAnsi("[HM] ERROR: " + e.getMessage() + "\n", true));
							finishExecution();
						});
					}
				}).start();
				
			} catch (Exception e) {
				SketchwareUtil.showMessage(getApplicationContext(), "Shizuku error: " + e.getMessage());
			}
		});
		
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
		textview1.setMovementMethod(new android.text.method.ScrollingMovementMethod());
		linear6.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)35, (int)0, 0x7081E4F6, 0xFF212121));
		linear11.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)35, (int)0, 0x7081E4F6, 0xFF212121));
		linear9.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)35, (int)0, 0x7081E4F6, 0xFF1A1A1A));
		textview1.setEnabled(false);
		_onReq();
		linear11.setVisibility(View.GONE);
		_sizetetap();
	}
	
	
	@Override
	public void onBackPressed() {
		if (!runnings) {
			
		} else {
			animateExit();
		}
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
	public void _execute() {
	}
	private void captureOutput(java.lang.Process p) {
		// Thread STDOUT
		new Thread(() -> {
			try (InputStreamReader isr = new InputStreamReader(p.getInputStream())) {
				char[] buffer = new char[1024];
				int len;
				while ((len = isr.read(buffer)) != -1) {
					String chunk = new String(buffer, 0, len);
					runOnUiThread(() -> {
						textview1.append(parseAnsi(chunk, false));
						vscroll1.post(() -> vscroll1.fullScroll(View.FOCUS_DOWN));
					});
				}
			} catch (Exception ignored) {}
		}).start();
		
		// Thread STDERR
		new Thread(() -> {
			try (InputStreamReader isr = new InputStreamReader(p.getErrorStream())) {
				char[] buffer = new char[1024];
				int len;
				while ((len = isr.read(buffer)) != -1) {
					String chunk = new String(buffer, 0, len);
					runOnUiThread(() -> {
						textview1.append(parseAnsi(chunk, false));
						vscroll1.post(() -> vscroll1.fullScroll(View.FOCUS_DOWN));
					});
				}
			} catch (Exception ignored) {}
		}).start();
	}
	
	private void finishExecution() {
		linear11.setVisibility(View.VISIBLE);
		runnings = false;
		edittext2.setEnabled(true);
		imageview2.setVisibility(View.VISIBLE);
		textview1.invalidate();
		textview1.requestLayout();
		vscroll1.post(() -> vscroll1.fullScroll(View.FOCUS_DOWN));
	}
	
	
	private void runExecutor() {
		
		try {
			String command = edittext2.getText().toString().trim();
			
			// Sesuaikan dengan path dari setupBusybox lo
			final String bbPath = getFilesDir().getAbsolutePath() + "/bb";
			final String bbTmpPath = "/data/local/tmp/bb";
			
			if (command.isEmpty()) {
				SketchwareUtil.showMessage(getApplicationContext(), "Command kosong!");
				return;
			}
			
			commandHistory.add(command);
			historyIndex = commandHistory.size();
			
			edittext2.setEnabled(false);
			imageview2.setVisibility(View.GONE);
			linear11.setVisibility(View.GONE);
			runnings = true;
			
			textview1.append(parseAnsi("[HM]:~ $ " + command + "\n", true));
			
			new Thread(() -> {
				try {
					// STEP 1: Eksekusi Standard Android
					Process process = ExecEngine.newProcess(new String[]{"sh", "-c", "echo $$; " + command});
					
					// Ambil PID (Output baris pertama)
					BufferedReader pidReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String pidLine = pidReader.readLine();
					if (pidLine != null) {
						runOnUiThread(() -> textview1.append(parseAnsi("[HM] PID : " + pidLine + "\n", true)));
					}
					
					// Baca Output secara real-time
					captureOutput(process);
					int exitCode = process.waitFor();
					
					
					if (exitCode == 127 || exitCode == 1) {
						runOnUiThread(() -> textview1.append(parseAnsi("[HM]Executing via BusyBox...\n", true)));
						
						// Cek path busybox mana yang ready
						String activeBB = new File(bbPath).exists() ? bbPath : bbTmpPath;
						String bbCommand = activeBB + " " + command;
						
						Process bbProcess = ExecEngine.newProcess(new String[]{"sh", "-c", bbCommand});
						
						captureOutput(bbProcess);
						bbProcess.waitFor();
					}
					
					runOnUiThread(() -> {
						textview1.append(parseAnsi("\n[HM] Done Execute [HasExited=true]\n\n", true));
						finishExecution();
					});
					
				} catch (Exception e) {
					runOnUiThread(() -> {
						textview1.append(parseAnsi("[HM] ERROR: " + e.getMessage() + "\n", true));
						finishExecution();
					});
				}
			}).start();
			
		} catch (Exception e) {
			SketchwareUtil.showMessage(getApplicationContext(), "Shizuku error: " + e.getMessage());
		}
	}
	{
	}
	
	
	public void _onReq() {
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 1001 && resultCode == RESULT_OK && data != null) {
			try {
				Uri uri = data.getData();
				if (uri == null) return;
				
				String path = uri.toString();
				
				if (!path.toLowerCase().endsWith(".sh")) {
					Toast.makeText(this, "Pilih file .sh!", Toast.LENGTH_SHORT).show();
					return;
				}
				
				String originalPath = FileUtil.convertUriToFilePath(getApplicationContext(), uri);
				
				if (originalPath == null || originalPath.equals("")) {
					
					return;
				}
				
				if (!Shizuku.pingBinder()) {
					
					return;
				}
				
				
				String fileName = new File(originalPath).getName();
				
				String newPath = "/data/local/tmp/" + fileName;
				
				
				shizukuExec("cp \"" + originalPath + "\" \"" + newPath + "\"");
				shizukuExec("chmod 755 \"" + newPath + "\"");
				
				edittext2.setText("sh " + newPath);
				
				runExecutor();
				
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
	}
	{
	}
	
	
	public void _shizuku_access() {
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
	
	
	public void _shizukuexec() {
	}
	public boolean isShizukuReady() {
		
		if (!Shizuku.pingBinder()) return false;
		
		if (Shizuku.checkSelfPermission() != PackageManager.PERMISSION_GRANTED) {
			
			
			return false;
		}
		
		return true;
	}
	
	public String shizukuExec(String cmd) {
		
		try {
			
			process =
			ExecEngine.newProcess(new String[]{"sh","-c",cmd});
			
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
	private java.lang.Process process;
	{
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
	
	
	public void _getToast() {
	}
	
	
	public void _onBack() {
	}
	private void animateExit() {
		final View root = findViewById(android.R.id.content);
		
		if (root == null) {
			finish();
			return;
		}
		
		root.animate()
		.translationX(root.getWidth())
		.alpha(0f)
		.scaleX(0.9f)
		.scaleY(0.9f)
		.setDuration(180)
		.withEndAction(new Runnable() {
			@Override
			public void run() {
				finish();
				overridePendingTransition(0, android.R.anim.fade_out);
			}
		})
		.start();
	}
	{
	}
	
}
