package com.hypers.hm;
import com.hypers.hm.ExecEngine;

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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;
import org.lsposed.hiddenapibypass.library.*;
import retrofit2.*;
import rikka.shizuku.api.*;
import rikka.shizuku.provider.*;
import java.lang.Process;
import rikka.shizuku.Shizuku;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.widget.Toast;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;



public class WebuiActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
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
	private String webVieww = "";
	
	private FrameLayout linear1;
	private WebView webview1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear5;
	private ImageView imageview2;
	private LinearLayout linear6;
	private ImageView imageview3;
	private TextView textview1;
	private TextView textview2;
	
	private TimerTask t;
	private SharedPreferences webUi;
	private TimerTask timeLaunch;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.webui);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		webview1 = findViewById(R.id.webview1);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.getSettings().setSupportZoom(true);
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		linear5 = findViewById(R.id.linear5);
		imageview2 = findViewById(R.id.imageview2);
		linear6 = findViewById(R.id.linear6);
		imageview3 = findViewById(R.id.imageview3);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		webUi = getSharedPreferences("webUi", Activity.MODE_PRIVATE);
		
		webview1.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				
				super.onPageFinished(_param1, _param2);
			}
		});
		
		imageview3.setOnClickListener(_v -> {
			LoadingBooster.show(WebuiActivity.this)
			.size(180)
			.color(0xFF00ACC1);
			
			// Buat TimerTask
			timeLaunch = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								String html = readFile(webVieww);
								
								html = html.replaceAll(
								"import\\s+\\{([^}]+)\\}\\s+from\\s+['\"]([^'\"]+)['\"]",
								"const {$1} = await importModule('$2')"
								);
								
								html = html.replaceAll(
								"<script>([\\s\\S]*?)</script>",
								"<script>(async function(){ try {\n$1\n} catch(e){ console.log(e); } })();</script>"
								);
								
								String runtime =
								"<script>" + readAsset("js/hypers-manager.js") + "</script>\n" +
								"<script>" + readAsset("js/loader.js") + "</script>\n";
								
								if (html.contains("<head>")) {
									html = html.replace("<head>", "<head>\n" + runtime);
								} else {
									html = runtime + html;
								}
								
								String base = "file://" + new File(webVieww).getParent() + "/";
								
								webview1.loadDataWithBaseURL(
								base,
								html,
								"text/html",
								"UTF-8",
								null
								);
								webview1.clearCache(true);
								LoadingBooster.hide();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			};
			
			
			_timer.schedule(timeLaunch, 2000L);
		});
	}
	
	private void initializeLogic() {
		{
			getWindow()
			.setStatusBarColor(Color.TRANSPARENT); 
		}
		
		{
			getWindow().
			setFlags
			(
			WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
			); 
		}
		
		
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
			getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
		}
		hideSystemUI();
		
		
		getWindow().getDecorView()
		.setOnSystemUiVisibilityChangeListener(new View
		.OnSystemUiVisibilityChangeListener() {
			@Override
			public void onSystemUiVisibilityChange(int visibility) {
				if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
					hideSystemUI();
				}
			}
		});
		
		
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#131313")));
		
		getWindow().clearFlags(
		WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
		);
		
		getWindow().getDecorView().setSystemUiVisibility(
		View.SYSTEM_UI_FLAG_FULLSCREEN
		);
		
		if (android.os.Build.VERSION.SDK_INT >= 35) {
			
			getWindow().setDecorFitsSystemWindows(false);
			
			getWindow().setStatusBarColor(
			android.graphics.Color.TRANSPARENT
			);
			
			getWindow().setNavigationBarColor(
			android.graphics.Color.TRANSPARENT
			);
		}
		_AksesShizuku();
		_sizetetap();
		webVieww = webUi.getString("webUI", "");
		// 1. Inisialisasi Settings (Taruh di onCreate / initializeLogic)
		WebSettings s = webview1.getSettings();
		s.setJavaScriptEnabled(true);
		s.setDomStorageEnabled(true);
		s.setDatabaseEnabled(true);
		
		// TRICK MAGISK: Izinkan akses file cross-origin agar CSS kedetect
		s.setAllowFileAccess(true);
		s.setAllowContentAccess(true);
		s.setAllowFileAccessFromFileURLs(true);
		s.setAllowUniversalAccessFromFileURLs(true);
		
		s.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
		s.setUseWideViewPort(true);
		s.setLoadWithOverviewMode(true);
		s.setCacheMode(WebSettings.LOAD_NO_CACHE);
		
		// Aktifkan debugging buat chrome://inspect
		WebView.setWebContentsDebuggingEnabled(true);
		
		// Interface bridge
		webview1.addJavascriptInterface(new PluginBridge(this), "Hypers");
		webview1.addJavascriptInterface(new KsuWebInterface(this, webview1), "ksu");
		
		// 2. WebViewClient (Fix Intercept & MimeType)
		webview1.setWebViewClient(new WebViewClient() {
			@Override
			public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
				Uri url = request.getUrl();
				String host = url.getHost();
				String path = url.getPath();
				
				try {
					// Handle Virtual Assets dari APK
					if ("kernelsu.js".equals(host)) {
						return new WebResourceResponse("application/javascript", "UTF-8", getAssets().open("js/kernelsu.js"));
					}
					if ("hypers.js".equals(host)) {
						return new WebResourceResponse("application/javascript", "UTF-8", getAssets().open("js/hypers.js"));
					}
					
					// Handle Plugin Assets (CSS, JS, Images) dari /data/local/tmp/
					if ("mui.kernelsu.org".equals(host)) {
						String folderPath = webUi.getString("folderPath", "");
						File file = new File("/data/local/tmp/HYPERS/data/Plugins/" + folderPath, path);
						
						if (file.exists()) {
							// FIX: Gunakan getMimeType buatan sendiri & Paksa UTF-8 biar CSS gak mogok
							return new WebResourceResponse(
							getMimeType(path),
							"UTF-8",
							new FileInputStream(file)
							);
						}
					}
				} catch (Exception e) {
					Log.e("WebUI", "Intercept Error: " + e.getMessage());
				}
				return super.shouldInterceptRequest(view, request);
			}
		});
		
		// 3. WebChromeClient (Handle Console & Blob)
		webview1.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
				if (consoleMessage != null) {
					String msg = consoleMessage.message();
					if (msg.startsWith("ksuBlobData:")) {
						try {
							JSONObject json = new JSONObject(msg.substring(12));
							saveDataUrlToDownloads(json.getString("dataUrl"), json.getString("mimeType"));
							return true;
						} catch (Exception ignored) {}
					}
				}
				return super.onConsoleMessage(consoleMessage);
			}
		});
		
		// 4. Download Listener
		webview1.setDownloadListener((url, userAgent, contentDisposition, mimeType, contentLength) -> {
			if (url.startsWith("blob:")) {
				String script = "javascript:(function(){fetch('" + url + "').then(r=>r.blob()).then(blob=>{let reader=new FileReader();reader.onloadend=function(){console.log('ksuBlobData:'+JSON.stringify({dataUrl:reader.result,mimeType:'" + mimeType + "'}));};reader.readAsDataURL(blob);});})();";
				webview1.evaluateJavascript(script, null);
			} else if (url.startsWith("data:")) {
				saveDataUrlToDownloads(url, mimeType);
			}
		});
		
		// 5. Final Load (Trik Base URL agar CSS Kedetect)
		// Gunakan readFile versi Shizuku jika path ada di /data/local/tmp/
		String html = readFile(webVieww); 
		
		// Inject ESM Modules Fix
		html = html.replaceAll("import\\s+\\{([^}]+)\\}\\s+from\\s+['\"]([^'\"]+)['\"]", "const {$1} = await importModule('$2')");
		
		// Inject Runtime Scripts
		String runtime = "<script src='https://kernelsu.js'></script>\n" +
		"<script src='https://hypers.js'></script>\n" +
		"<script>" + readAsset("js/hypers-manager.js") + "</script>\n" +
		"<script>" + readAsset("js/loader.js") + "</script>\n";
		
		if (html.contains("</head>")) {
			html = html.replace("</head>", runtime + "</head>");
		} else {
			html = runtime + html;
		}
		
		// FIX: Set Base URL ke folder induk agar relative path (style.css) terbaca
		File webFile = new File(webVieww);
		String base = "file://" + webFile.getParent() + "/";
		
		webview1.loadDataWithBaseURL(base, html, "text/html", "UTF-8", null);
		linear5.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)50, (int)0, Color.TRANSPARENT, 0xFF212121));
		linear5.setAlpha(0.8f);
		linear5.setEnabled(false);
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		_sizetetap();
		LoadingBooster.show(WebuiActivity.this)
		.size(180)
		.color(0xFF00ACC1);
		
		// Buat TimerTask
		timeLaunch = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						try {
							
							LoadingBooster.hide();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		};
		
		
		_timer.schedule(timeLaunch, 2000L);
	}
	
	@Override
	public void onBackPressed() {
		animateExit();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		_sizetetap();
	}
	public void _var() {
	}
	private Process process;
	{
	}
	
	
	public void _pluginsBridge() {
	}
	public class PluginBridge {
		
		private Context context;
		
		public PluginBridge(Context ctx) {
			this.context = ctx;
		}
		
		@JavascriptInterface
		public String exec(String cmd, String optionsStr) {
			
			try {
				
				JSONObject opt = new JSONObject(optionsStr == null ? "{}" : optionsStr);
				
				String engine = opt.optString("engine", "auto");
				
				String stdout = "";
				String stderr = "";
				int errno = 0;
				
				if (engine.equals("auto")) {
					
					try {
						if (isShizukuAvailable()) {
							engine = "shizuku";
						} else if (isRootAvailable()) {
							engine = "root";
						} else {
							engine = "bridge";
						}
					} catch (Exception e) {
						engine = "bridge";
					}
				}
				
				if (engine.equals("shizuku")) {
					
					try {
						stdout = runShizuku(cmd);
					} catch (Exception e) {
						stdout = "Shizuku failed";
					}
					
				} else if (engine.equals("root")) {
					
					try {
						
						Process p = Runtime.getRuntime().exec(cmd);
						
						BufferedReader br = new BufferedReader(
						new InputStreamReader(p.getInputStream())
						);
						
						StringBuilder sb = new StringBuilder();
						String line;
						
						while ((line = br.readLine()) != null) {
							sb.append(line).append("\n");
						}
						
						stdout = sb.toString();
						
					} catch (Exception e) {
						stdout = "Root failed";
					}
					
				} else {
					stdout = "Bridge mode executed: " + cmd;
				}
				
				JSONObject res = new JSONObject();
				res.put("errno", errno);
				res.put("stdout", stdout);
				res.put("stderr", stderr);
				res.put("engine", engine);
				
				return res.toString();
				
			} catch (Exception e) {
				return "{\"errno\":-1,\"stderr\":\"" + e.toString() + "\"}";
			}
		}
		
		private boolean isShizukuAvailable() {
			try {
				return Shizuku.pingBinder();
			} catch (Exception e) {
				return false;
			}
		}
		
		private boolean isRootAvailable() {
			try {
				Process p = Runtime.getRuntime().exec("su");
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		
		private String runShizuku(String cmd) {
			
			try {
				
				String[] command = new String[]{"sh", "-c", cmd};
				
				Process process = ExecEngine.newProcess(command);
				
				BufferedReader br = new BufferedReader(
				new InputStreamReader(process.getInputStream())
				);
				
				StringBuilder sb = new StringBuilder();
				String line;
				
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				
				return sb.toString();
				
			} catch (Exception e) {
				return "Shizuku error";
			}
		}
		
		@JavascriptInterface
		public void toast(String text) {
			android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_SHORT).show();
		}
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
	
	
	public void _fullScreen() {
	}
	private static final int UI_OPTIONS = View.SYSTEM_UI_FLAG_LOW_PROFILE
	| View.SYSTEM_UI_FLAG_LAYOUT_STABLE
	| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
	| 
	View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
	| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
	|
	View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
	
	
	
	private void hideSystemUI() {
		ActionBar actionBar = getActionBar();
		if (actionBar != null) actionBar.hide();
		getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);
	}
	{
	}
	
	
	public void _sizetetap() {
	}
	private static final int VIRTUAL_WIDTH = 625;
	private static final int VIRTUAL_HEIGHT = 1045;
	private Configuration virtualConfig;
	
	@Override
	protected void attachBaseContext(Context newBase) {
		Context wrapped = wrapContext(newBase);
		virtualConfig = new Configuration(wrapped.getResources().getConfiguration());
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
	public void onConfigurationChanged(Configuration newConfig) {
		if (virtualConfig != null) {
			newConfig.setTo(virtualConfig);
			super.onConfigurationChanged(newConfig);
			getResources().updateConfiguration(virtualConfig, getResources().getDisplayMetrics());
		} else {
			super.onConfigurationChanged(newConfig);
		}
	}
	
	@Override
	public Resources getResources() {
		Resources res = super.getResources();
		if (virtualConfig != null) {
			res.updateConfiguration(virtualConfig, res.getDisplayMetrics());
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
	
	
	public void _WebuiEnginer() {
	}
	private void saveDataUrlToDownloads(String dataUrl, String mimeType) {
		Toast.makeText(this, "Download: " + mimeType, Toast.LENGTH_SHORT).show();
	}
	
	private void injectJS(WebView webView, String fileName) {
		try {
			InputStream is = getAssets().open(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			
			StringBuilder js = new StringBuilder();
			String line;
			
			while ((line = reader.readLine()) != null) {
				js.append(line).append("\n");
			}
			
			reader.close();
			
			webView.evaluateJavascript(js.toString(), null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String readAsset(String fileName) {
		StringBuilder sb = new StringBuilder();
		
		try {
			java.io.InputStream is = getAssets().open(fileName);
			java.io.BufferedReader br = new java.io.BufferedReader(
			new java.io.InputStreamReader(is)
			);
			
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	
	private String getMimeType(String path) {
		String p = path.toLowerCase();
		if (p.endsWith(".html") || p.endsWith(".htm")) return "text/html";
		if (p.endsWith(".css")) return "text/css";
		if (p.endsWith(".js")) return "application/javascript";
		if (p.endsWith(".json")) return "application/json";
		if (p.endsWith(".png")) return "image/png";
		if (p.endsWith(".jpg") || p.endsWith(".jpeg")) return "image/jpeg";
		if (p.endsWith(".gif")) return "image/gif";
		if (p.endsWith(".webp")) return "image/webp";
		if (p.endsWith(".svg") || p.endsWith(".svgz")) return "image/svg+xml";
		if (p.endsWith(".ico")) return "image/x-icon";
		// 3. Fonts (Biar Font Custom Lo Gak Error)
		if (p.endsWith(".ttf")) return "font/ttf";
		if (p.endsWith(".otf")) return "font/otf";
		if (p.endsWith(".woff")) return "font/woff";
		if (p.endsWith(".woff2")) return "font/woff2";
		if (p.endsWith(".mp4")) return "video/mp4";
		if (p.endsWith(".webm")) return "video/webm";
		if (p.endsWith(".mp3")) return "audio/mpeg";
		if (p.endsWith(".wav")) return "audio/wav";
		if (p.endsWith(".ogg")) return "audio/ogg";
		if (p.endsWith(".pdf")) return "application/pdf";
		if (p.endsWith(".txt")) return "text/plain";
		if (p.endsWith(".zip")) return "application/zip";
		return "application/octet-stream";
	}
	
	
	private String readFile(String path) {
		
		if (path.startsWith("/data/local/tmp/")) {
			return shizukuExec("cat '" + path + "'");
		}
		
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line;
			while ((line = br.readLine()) != null) sb.append(line).append("\n");
		} catch (Exception e) { e.printStackTrace(); }
		return sb.toString();
	}
	
	{
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
	
	
	public void _KsuBridge() {
	}
	
	public class KsuWebInterface {
		
		private final Context context;
		private final WebView webView;
		private final File modDir;
		
		private final ExecutorService executor = Executors.newCachedThreadPool();
		
		public KsuWebInterface(Context context, WebView webView) {
			this.context = context;
			this.webView = webView;
			
			// 🔥 PATH LU
			this.modDir = new File("/data/local/tmp/HYPERS/data/Plugins/");
		}
		
		// ===== plugin bin =====
		public String getPluginBin() {
			return modDir.getAbsolutePath() + webUi.getString("folderPath", "");
		}
		
		// ===== EXEC SIMPLE =====
		@JavascriptInterface
		public String exec(String cmd) {
			try {
				Process p = Runtime.getRuntime().exec(cmd);
				
				BufferedReader reader = new BufferedReader(
				new InputStreamReader(p.getInputStream())
				);
				
				StringBuilder out = new StringBuilder();
				String line;
				
				while ((line = reader.readLine()) != null) {
					out.append(line).append("\n");
				}
				
				reader.close();
				return out.toString();
				
			} catch (Exception e) {
				return e.toString();
			}
		}
		
		// ===== EXEC CALLBACK =====
		@JavascriptInterface
		public void exec(final String cmd, final String callbackFunc) {
			exec(cmd, null, callbackFunc);
		}
		
		@JavascriptInterface
		public void exec(final String cmd, final String options, final String callbackFunc) {
			
			executor.execute(() -> {
				try {
					
					String finalCmd = buildCommand(cmd, options);
					
					Process p = Runtime.getRuntime().exec(new String[]{"sh", "-c", finalCmd});
					
					BufferedReader stdout = new BufferedReader(
					new InputStreamReader(p.getInputStream())
					);
					
					BufferedReader stderr = new BufferedReader(
					new InputStreamReader(p.getErrorStream())
					);
					
					StringBuilder out = new StringBuilder();
					StringBuilder err = new StringBuilder();
					
					String line;
					while ((line = stdout.readLine()) != null) {
						out.append(line).append("\n");
					}
					
					while ((line = stderr.readLine()) != null) {
						err.append(line).append("\n");
					}
					
					int code = p.waitFor();
					
					String js = "(function(){try{" +
					callbackFunc + "(" + code + "," +
					JSONObject.quote(out.toString()) + "," +
					JSONObject.quote(err.toString()) +
					");}catch(e){console.error(e);}})();";
					
					webView.post(() -> webView.evaluateJavascript(js, null));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
		
		// ===== SPAWN (STREAM) =====
		@JavascriptInterface
		public void spawn(final String command, final String args,
		final String options, final String callbackFunc) {
			
			executor.execute(() -> {
				try {
					
					String finalCmd = buildSpawnCommand(command, args, options);
					
					Process p = Runtime.getRuntime().exec(new String[]{"sh", "-c", finalCmd});
					
					BufferedReader stdout = new BufferedReader(
					new InputStreamReader(p.getInputStream())
					);
					
					BufferedReader stderr = new BufferedReader(
					new InputStreamReader(p.getErrorStream())
					);
					
					String line;
					
					while ((line = stdout.readLine()) != null) {
						emit(callbackFunc, "stdout", line);
					}
					
					while ((line = stderr.readLine()) != null) {
						emit(callbackFunc, "stderr", line);
					}
					
					int code = p.waitFor();
					
					emitExit(callbackFunc, code);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
		
		// ===== BUILD COMMAND =====
		private String buildCommand(String cmd, String options) {
			StringBuilder sb = new StringBuilder();
			
			try {
				if (options != null) {
					JSONObject opts = new JSONObject(options);
					
					String cwd = opts.optString("cwd");
					if (!TextUtils.isEmpty(cwd)) {
						sb.append("cd ").append(cwd).append(";");
					}
					
					sb.append("export PATH=").append(getPluginBin()).append(":$PATH;");
					
					JSONObject env = opts.optJSONObject("env");
					if (env != null) {
						Iterator<String> keys = env.keys();
						while (keys.hasNext()) {
							String key = keys.next();
							sb.append("export ")
							.append(key)
							.append("=")
							.append(env.getString(key))
							.append(";");
						}
					}
				}
				
			} catch (Exception ignored) {}
			
			sb.append(cmd);
			return sb.toString();
		}
		
		private String buildSpawnCommand(String command, String args, String options) {
			StringBuilder sb = new StringBuilder();
			sb.append(buildCommand("", options));
			
			sb.append(command).append(" ");
			
			try {
				if (!TextUtils.isEmpty(args)) {
					JSONArray arr = new JSONArray(args);
					for (int i = 0; i < arr.length(); i++) {
						sb.append(arr.getString(i)).append(" ");
					}
				}
			} catch (Exception ignored) {}
			
			return sb.toString();
		}
		
		// ===== EMIT =====
		private void emit(String cb, String type, String data) {
			String js = "(function(){try{" +
			cb + "." + type + ".emit('data'," +
			JSONObject.quote(data) +
			");}catch(e){console.error(e);}})();";
			
			webView.post(() -> webView.evaluateJavascript(js, null));
		}
		
		private void emitExit(String cb, int code) {
			String js = "(function(){try{" +
			cb + ".emit('exit'," + code +
			");}catch(e){console.error(e);}})();";
			
			webView.post(() -> webView.evaluateJavascript(js, null));
		}
		
		// ===== TOAST =====
		@JavascriptInterface
		public void toast(String msg) {
			new Handler(Looper.getMainLooper()).post(() ->
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
			);
		}
		
		// ===== FULLSCREEN =====
		@JavascriptInterface
		public void fullScreen(boolean enable) {
			if (!(context instanceof Activity)) return;
			
			Activity act = (Activity) context;
			
			act.runOnUiThread(() -> {
				if (enable) {
					act.getWindow().getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_FULLSCREEN |
					View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
					View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
					);
				} else {
					act.getWindow().getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_VISIBLE
					);
				}
			});
		}
	}
	
	{
	}
	
}
