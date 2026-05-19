package com.hypers.hm;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
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
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.annotation.*;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnAdapterChangeListener;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import com.bumptech.glide.*;
import com.cocode.focora.*;
import com.droidx.*;
import com.facebook.shimmer.*;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;
import org.lsposed.hiddenapibypass.library.*;
import rikka.shizuku.api.*;
import rikka.shizuku.provider.*;
import android.content.pm.PackageManager;
import java.lang.Process;
import android.provider.Settings;
import rikka.shizuku.Shizuku;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.app.AppOpsManager;
import android.Manifest;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.content.pm.ApplicationInfo;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.security.MessageDigest;
import android.content.ClipboardManager;
import android.content.ClipData;

public class MainActivity extends AppCompatActivity {
	
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
	private MaterialToolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private DrawerLayout _drawer;
	private HashMap<String, Object> map = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	private ArrayList<String> list_app_name = new ArrayList<>();
	private ArrayList<String> list_app_package = new ArrayList<>();
	
	private LinearLayout linear1b;
	private LinearLayout linear3;
	private FrameLayout linear_container;
	private LinearLayout linear44;
	private LinearLayout linear40;
	private ViewPager viewpager1;
	private LinearLayout linear2;
	private LinearLayout linear43;
	private LinearLayout linear46;
	private ImageView imageview1;
	private LinearLayout linear4;
	private TextView textview13;
	private TextView textview1;
	private LinearLayout indicator;
	private LinearLayout linear42;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private LinearLayout linear9;
	private LinearLayout container7;
	private ImageView imageview4;
	private TextView textview4;
	private LinearLayout container8;
	private ImageView imageview5;
	private TextView textview5;
	private LinearLayout container9;
	private ImageView imageview6;
	private TextView textview6;
	private LinearLayout _drawer_linear1;
	private LinearLayout _drawer_linear2;
	private LinearLayout _drawer_linear3;
	private CircleImageView _drawer_circleimageview1;
	private LinearLayout _drawer_linear4;
	private TextView _drawer_textview1;
	private TextView _drawer_textview2;
	private TextView _drawer_textview3;
	private LinearLayout _drawer_linear59;
	private LinearLayout _drawer_linear58;
	private LinearLayout _drawer_linear7;
	private TextView _drawer_textview8;
	private ImageView _drawer_imageview3;
	private ImageView _drawer_imageview4;
	private ImageView _drawer_imageview1;
	private LinearLayout _drawer_linear60;
	private TextView _drawer_textview4;
	private TextView _drawer_textview5;
	private ImageView _drawer_imageview2;
	private LinearLayout _drawer_linear57;
	private TextView _drawer_textview6;
	private TextView _drawer_textview7;
	
	private Intent i = new Intent();
	private SharedPreferences user;
	private SharedPreferences totalApp;
	private SharedPreferences ppkg;
	private SharedPreferences listmanager;
	private TimerTask timeLaunch;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_app_bar = findViewById(R.id._app_bar);
		_coordinator = findViewById(R.id._coordinator);
		_toolbar = findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		_drawer = findViewById(R.id._drawer);
		ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(MainActivity.this, _drawer, _toolbar, R.string.app_name, R.string.app_name);
		_drawer.addDrawerListener(_toggle);
		_toggle.syncState();
		
		LinearLayout _nav_view = findViewById(R.id._nav_view);
		
		linear1b = findViewById(R.id.linear1b);
		linear3 = findViewById(R.id.linear3);
		linear_container = findViewById(R.id.linear_container);
		linear44 = findViewById(R.id.linear44);
		linear40 = findViewById(R.id.linear40);
		viewpager1 = findViewById(R.id.viewpager1);
		linear2 = findViewById(R.id.linear2);
		linear43 = findViewById(R.id.linear43);
		linear46 = findViewById(R.id.linear46);
		imageview1 = findViewById(R.id.imageview1);
		linear4 = findViewById(R.id.linear4);
		textview13 = findViewById(R.id.textview13);
		textview1 = findViewById(R.id.textview1);
		indicator = findViewById(R.id.indicator);
		linear42 = findViewById(R.id.linear42);
		linear7 = findViewById(R.id.linear7);
		linear8 = findViewById(R.id.linear8);
		linear9 = findViewById(R.id.linear9);
		container7 = findViewById(R.id.container7);
		imageview4 = findViewById(R.id.imageview4);
		textview4 = findViewById(R.id.textview4);
		container8 = findViewById(R.id.container8);
		imageview5 = findViewById(R.id.imageview5);
		textview5 = findViewById(R.id.textview5);
		container9 = findViewById(R.id.container9);
		imageview6 = findViewById(R.id.imageview6);
		textview6 = findViewById(R.id.textview6);
		_drawer_linear1 = _nav_view.findViewById(R.id.linear1);
		_drawer_linear2 = _nav_view.findViewById(R.id.linear2);
		_drawer_linear3 = _nav_view.findViewById(R.id.linear3);
		_drawer_circleimageview1 = _nav_view.findViewById(R.id.circleimageview1);
		_drawer_linear4 = _nav_view.findViewById(R.id.linear4);
		_drawer_textview1 = _nav_view.findViewById(R.id.textview1);
		_drawer_textview2 = _nav_view.findViewById(R.id.textview2);
		_drawer_textview3 = _nav_view.findViewById(R.id.textview3);
		_drawer_linear59 = _nav_view.findViewById(R.id.linear59);
		_drawer_linear58 = _nav_view.findViewById(R.id.linear58);
		_drawer_linear7 = _nav_view.findViewById(R.id.linear7);
		_drawer_textview8 = _nav_view.findViewById(R.id.textview8);
		_drawer_imageview3 = _nav_view.findViewById(R.id.imageview3);
		_drawer_imageview4 = _nav_view.findViewById(R.id.imageview4);
		_drawer_imageview1 = _nav_view.findViewById(R.id.imageview1);
		_drawer_linear60 = _nav_view.findViewById(R.id.linear60);
		_drawer_textview4 = _nav_view.findViewById(R.id.textview4);
		_drawer_textview5 = _nav_view.findViewById(R.id.textview5);
		_drawer_imageview2 = _nav_view.findViewById(R.id.imageview2);
		_drawer_linear57 = _nav_view.findViewById(R.id.linear57);
		_drawer_textview6 = _nav_view.findViewById(R.id.textview6);
		_drawer_textview7 = _nav_view.findViewById(R.id.textview7);
		user = getSharedPreferences("user", Activity.MODE_PRIVATE);
		totalApp = getSharedPreferences("totalApp", Activity.MODE_PRIVATE);
		ppkg = getSharedPreferences("ppkg", Activity.MODE_PRIVATE);
		listmanager = getSharedPreferences("listmanager", Activity.MODE_PRIVATE);
		
		viewpager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int _position, float _positionOffset, int _positionOffsetPixels) {
				
			}
			
			@Override
			public void onPageSelected(int _position) {
				
				if (_position == 0) {
					textview13.setText("Dashboard");
				} else if (_position == 1) {
					textview13.setText("Apps");
				} else if (_position == 2) {
					textview13.setText("Settings");
				}
				
				selectTab(_position);
			}
			
			@Override
			public void onPageScrollStateChanged(int _scrollState) {
				
			}
		});
		
		imageview1.setOnClickListener(_v -> _drawer.openDrawer(GravityCompat.START));
		
		linear7.setOnClickListener(_v -> {
			_click(linear7);
			selectTab(0);
		});
		
		linear8.setOnClickListener(_v -> {
			_click(linear8);
			selectTab(1);
		});
		
		linear9.setOnClickListener(_v -> {
			_click(linear9);
			selectTab(2);
		});
	}
	
	private void initializeLogic() {
		androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(
		linear1b,
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
		if (android.os.Build.VERSION.SDK_INT >= 35) {
			getWindow().setDecorFitsSystemWindows(true);
		}
		
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0E0E0E")));
		final LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view); _nav_view.setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
		
		getSupportActionBar().hide();
		viewpager1.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
		viewpager1.setOffscreenPageLimit((int)3);
		int status;
		
		if (!Shizuku.pingBinder()) {
			status = 0;
		} else if (Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
			status = 1;
		} else {
			status = 2;
		}
		_onReqNotifications();
		_sizetetap();
		_shizuku_access();
		_onResultPermission();
		checkPermissionFlow();
		// DRAWER
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffbold.ttf"), 1);
		textview13.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffbold.ttf"), 1);
		textview4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffregular.ttf"), 1);
		textview5.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffregular.ttf"), 1);
		textview6.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffregular.ttf"), 1);
		_drawer_dissing();
		_drawer_textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffbold.ttf"), 1);
		
		_drawer_textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffbold.ttf"), 1);
		
		_drawer_textview4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffbold.ttf"), 1);
		
		_drawer_textview6.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffbold.ttf"), 1);
		_drawer_textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffregular.ttf"), 0);
		
		_drawer_textview5.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffregular.ttf"), 0);
		
		_drawer_textview7.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffregular.ttf"), 0);
		_drawer_imageview3.setVisibility(View.GONE);
		_drawer_imageview4.setVisibility(View.GONE);
		StringBuilder abiList = new StringBuilder();
		
		for (String abi : Build.SUPPORTED_ABIS) {
			abiList.append(abi).append(", ");
		}
		
		if (abiList.length() > 2) {
			abiList.setLength(abiList.length() - 2);
		}
		
		_drawer_textview7.setText(abiList.toString());
		new Thread(() -> {
			String result = execSmart("id && id -Z && getenforce");
			
			String context = "Unknown";
			String enforce = "Unknown";
			
			java.util.regex.Matcher m = java.util.regex.Pattern
			.compile("u:r:[^\\s]+")
			.matcher(result);
			
			if (m.find()) {
				context = m.group();
			}
			
			if (result.toLowerCase().contains("enforcing")) {
				enforce = "Enforcing";
			} else if (result.toLowerCase().contains("permissive")) {
				enforce = "Permissive";
			}
			
			String finalText = context;
			
			runOnUiThread(() -> {
				_drawer_textview5.setText(finalText);
			});
			
		}).start();
		String hw = android.os.Build.HARDWARE.toLowerCase();
		
		if (hw.contains("qcom") || hw.contains("sm")) {
		} else if (hw.contains("mt")) {
			
		} else if (hw.contains("exynos")) {
			
		} else {
			
		}
		showTextLong(textview4);
		hideText(textview5);
		hideText(textview6);
		
		// reset background
		linear7.setBackground(getResources().getDrawable(R.drawable.tengah));
		linear8.setBackground(null);
		linear9.setBackground(null);
		
		// reset warna icon
		_colorTransform(imageview4, "#00ACC1");
		_colorTransform(imageview5, "#FFFFFF");
		_colorTransform(imageview6, "#FFFFFF");
		selectTab(0);
		SecurityChecker sc = new SecurityChecker(this);
		
		if (!sc.isSafe()) {
			sc.showBlockDialog();
			
			
			return;
		}
	}
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		_sizetetap();
		if (virtualConfig != null && virtualMetrics != null) {
			getResources().updateConfiguration(virtualConfig, virtualMetrics);
		}
		
		
		if (permissionIndex < permissions.length) {
			checkPermissionFlow();
		}
		_shizuku_access();
		_onReqNotifications();
		new Thread(() -> {
			String result = execSmart("id && id -Z && getenforce");
			
			String context = "Unknown";
			String enforce = "Unknown";
			
			java.util.regex.Matcher m = java.util.regex.Pattern
			.compile("u:r:[^\\s]+")
			.matcher(result);
			
			if (m.find()) {
				context = m.group();
			}
			
			if (result.toLowerCase().contains("enforcing")) {
				enforce = "Enforcing";
			} else if (result.toLowerCase().contains("permissive")) {
				enforce = "Permissive";
			}
			
			String finalText = context;
			
			runOnUiThread(() -> {
				_drawer_textview5.setText(finalText);
			});
			
		}).start();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		_sizetetap();
	}
	
	@Override
	public void onBackPressed() {
		if (_drawer.isDrawerOpen(GravityCompat.START)) {
			_drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
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
	
	
	public void _var() {
	}
	private Process process;
	private int jumlah = 0;
	private int _position = 0;
	private Boolean game = true;
	private String lastPluginList = "";
	private static final int REQ_LOC = 100;
	{
	}
	private ValueAnimator animator;
	private LinearGradient gradient;
	private Matrix matrix = new Matrix();
	private Paint paint = new Paint();
	private float translate = 0f;
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
		int targetDpi = (int) (scale * 230);
		
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
	
	
	public void _ICC(final ImageView _img, final String _c1, final String _c2) {
		_img.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor(_c1), Color.parseColor(_c2)}));
	}
	
	
	public void _colorTransform(final ImageView _img, final String _hex) {
		_img.clearColorFilter(); 
		_img.setColorFilter(Color.parseColor(_hex.toString()));
	}
	
	
	public void _showNotifBoost() {
	}
	public boolean hasUsageAccess() {
		try {
			AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
			int mode = appOps.checkOpNoThrow(
			"android:get_usage_stats",
			android.os.Process.myUid(),
			getPackageName()
			);
			return mode == AppOpsManager.MODE_ALLOWED;
		} catch (Exception e) {
			return false;
		}
	}
	
	private boolean hasUsageStatsPermission() {
		try {
			android.app.AppOpsManager appOps =
			(android.app.AppOpsManager) getSystemService(APP_OPS_SERVICE);
			
			int mode = appOps.unsafeCheckOpNoThrow(
			"android:get_usage_stats",
			android.os.Process.myUid(),
			getPackageName()
			);
			
			return mode == android.app.AppOpsManager.MODE_ALLOWED;
			
		} catch (Exception e) {
			return false;
		}
	}
	{
	}
	
	
	public void _onReqNotifications() {
		
	}
	
	
	public void _nativee() {
	}
	public void setupBusybox() {
		try {
			
			String abi = android.os.Build.SUPPORTED_ABIS[0];
			
			String assetName;
			if (abi.contains("arm64")) {
				assetName = "data/bb_arm64";
			} else {
				assetName = "data/bb_arm";
			}
			
			File bb = new File(getFilesDir(), "bb");
			
			if (!bb.exists()) {
				InputStream is = getAssets().open(assetName);
				FileOutputStream fos = new FileOutputStream(bb);
				
				byte[] buffer = new byte[4096];
				int len;
				
				while ((len = is.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				
				fos.close();
				is.close();
			}
			
			execSmart("chmod 755 '" + bb.getAbsolutePath() + "'");
			execSmart("cp '" + bb.getAbsolutePath() + "' /data/local/tmp/bb");
			execSmart("chmod 755 /data/local/tmp/bb");
			
		} catch (Exception e) {
			e.printStackTrace();
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
	
	public String execCommand(String cmd) {
		StringBuilder output = new StringBuilder();
		
		try {
			
			
			
			if (isRootAvailable()) {
				
				process = Runtime.getRuntime().exec(new String[]{"sh", "-c", cmd});
				
				BufferedReader reader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));
				
				String line;
				while ((line = reader.readLine()) != null) {
					output.append(line).append("\n");
				}
				
				process.waitFor();
				
			} else if (isShizukuAvailable()) {
				
				String res = shizukuExec(cmd);
				output.append(res);
				
			} else {
				return "No root / Shizuku not available";
			}
			
		} catch (Exception e) {
			return "Error: " + e.toString();
		} finally {
			
			
		}
		
		return output.toString();
	}
	
	public String execSmart(String cmd) {
		return execCommand(cmd);
	}
	{
	}
	
	
	public void _mains() {
	}
	androidx.fragment.app.FragmentTransaction transaction; 
	MainsFragmentActivity MainsFragment; GamesFragmentActivity GamesFragment;
	SettingsFragmentActivity SettingsFragment;
	{
	}
	
	
	public void _sz() {
	}
	public int getShizukuStatus() {
		if (!Shizuku.pingBinder()) {
			return 0;
		} else if (Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
			return 1;
		} else {
			return 2;
		}
	}
	{
	}
	
	
	public void _shape(final double _top1, final double _top2, final double _bottom2, final double _bottom1, final String _inside_color, final String _side_color, final double _side_size, final View _view) {
		Double tlr = _top1;
		Double trr = _top2;
		Double blr = _bottom2;
		Double brr = _bottom1;
		Double sw = _side_size;
		android.graphics.drawable.GradientDrawable s = new android.graphics.drawable.GradientDrawable();
		s.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
		s.setCornerRadii(new float[] {tlr.floatValue(),tlr.floatValue(), trr.floatValue(),trr.floatValue(), blr.floatValue(),blr.floatValue(), brr.floatValue(),brr.floatValue()}); 
		
		s.setColor(Color.parseColor(_inside_color));
		s.setStroke(sw.intValue(), Color.parseColor(_side_color));
		_view.setBackground(s);
	}
	
	
	public void _drawer_dissing() {
		_shape(0, 50, 50, 0, "#1A1A1A", "#1A1A1A", 0, _drawer_linear1);
	}
	
	
	public void _permission() {
	}
	private boolean permSucces = false;
	
	private String[] permissions = new String[] {
		android.Manifest.permission.MANAGE_EXTERNAL_STORAGE,
		android.Manifest.permission.SYSTEM_ALERT_WINDOW,
		android.Manifest.permission.ACCESS_NOTIFICATION_POLICY
	};
	
	private int permissionIndex = 0;
	
	private void checkPermissionFlow() {
		
		if (permissionIndex >= permissions.length) {
			createPluginFolders();
			permSucces = true;
			return;
		}
		
		String perm = permissions[permissionIndex];
		boolean granted = false;
		
		switch (perm) {
			
			/* STORAGE */
			case android.Manifest.permission.MANAGE_EXTERNAL_STORAGE:
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
				
				if (!Environment.isExternalStorageManager()) {
					Intent intent = new Intent(
					Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
					Uri.parse("package:" + getPackageName())
					);
					startActivity(intent);
					return;
				} else {
					granted = true;
				}
				
			} else {
				granted = true;
			}
			break;
			
			/* OVERLAY */
			case android.Manifest.permission.SYSTEM_ALERT_WINDOW:
			if (!Settings.canDrawOverlays(this)) {
				Intent intent = new Intent(
				Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
				Uri.parse("package:" + getPackageName())
				);
				startActivity(intent);
				return;
			} else {
				granted = true;
			}
			break;
			
			/* DND */
			case android.Manifest.permission.ACCESS_NOTIFICATION_POLICY:
			NotificationManager nm =
			(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			
			if (nm != null && !nm.isNotificationPolicyAccessGranted()) {
				Intent intent = new Intent(
				Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS
				);
				startActivity(intent);
				return;
			} else {
				granted = true;
			}
			break;
		}
		
		if (granted) {
			permissionIndex++;
			checkPermissionFlow();
		}
	}
	{
	}
	
	
	public void _MainPager() {
	}
	public class MainPagerAdapter extends FragmentPagerAdapter {
		
		public MainPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		
		@Override
		public Fragment getItem(int position) {
			if (position == 0) return new MainsFragmentActivity();
			if (position == 1) return new GamesFragmentActivity();
			return new SettingsFragmentActivity();
		}
		
		@Override
		public int getCount() {
			return 3;
		}
	}
	{
	}
	
	
	public void _signatures() {
		
	}
	
	
	public void _c() {
	}
	private int currentTab = 0;
	
	private void animateWeight(final LinearLayout target, final float from, final float to) {
		ValueAnimator animator = ValueAnimator.ofFloat(from, to);
		animator.setDuration(200);
		
		animator.addUpdateListener(animation -> {
			float value = (float) animation.getAnimatedValue();
			LinearLayout.LayoutParams params =
			(LinearLayout.LayoutParams) target.getLayoutParams();
			
			params.weight = value;
			target.setLayoutParams(params);
		});
		
		animator.start();
	}
	
	private void showText(final TextView tv) {
		
		tv.setVisibility(View.VISIBLE);
		tv.setTextColor(0xFF00ACC1);
		
		tv.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		int targetWidth = (int) (40 * getResources().getDisplayMetrics().density);
		
		ValueAnimator animator = ValueAnimator.ofInt(0, targetWidth);
		animator.setDuration(400);
		
		animator.addUpdateListener(animation -> {
			int value = (int) animation.getAnimatedValue();
			tv.getLayoutParams().width = value;
			tv.requestLayout();
		});
		
		tv.setAlpha(0f);
		
		tv.animate()
		.alpha(1f)
		.setDuration(400)
		.setInterpolator(new DecelerateInterpolator())
		.start();
		
		animator.start();
	}
	
	private void showTextLong(final TextView tv) {
		
		tv.setVisibility(View.VISIBLE);
		tv.setTextColor(0xFF00ACC1);
		
		tv.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		int targetWidth = (int) (80 * getResources().getDisplayMetrics().density);
		
		ValueAnimator animator = ValueAnimator.ofInt(0, targetWidth);
		animator.setDuration(400);
		
		animator.addUpdateListener(animation -> {
			int value = (int) animation.getAnimatedValue();
			tv.getLayoutParams().width = value;
			tv.requestLayout();
		});
		
		tv.setAlpha(0f);
		
		tv.animate()
		.alpha(1f)
		.setDuration(400)
		.setInterpolator(new DecelerateInterpolator())
		.start();
		
		animator.start();
	}
	
	private void hideText(final TextView tv) {
		
		int initialWidth = tv.getWidth();
		tv.setTextColor(0xFFFFFF);
		
		ValueAnimator animator = ValueAnimator.ofInt(initialWidth, 0);
		animator.setDuration(200);
		
		animator.addUpdateListener(animation -> {
			int value = (int) animation.getAnimatedValue();
			tv.getLayoutParams().width = value;
			tv.requestLayout();
		});
		
		tv.animate()
		.alpha(0f)
		.setDuration(400)
		.withEndAction(() -> tv.setVisibility(View.INVISIBLE))
		.start();
		
		animator.start();
	}
	
	private void selectTab(int tab) {
		
		if (tab == currentTab) return;
		
		// hide semua text dulu
		hideText(textview4);
		hideText(textview5);
		hideText(textview6);
		
		// reset background
		linear7.setBackground(null);
		linear8.setBackground(null);
		linear9.setBackground(null);
		
		// reset warna icon
		_colorTransform(imageview4, "#FFFFFF");
		_colorTransform(imageview5, "#FFFFFF");
		_colorTransform(imageview6, "#FFFFFF");
		
		// SELECTED
		if (tab == 0) {
			showTextLong(textview4);
			textview13.setText("Dashboard");
			linear7.setBackground(getResources().getDrawable(R.drawable.tengah));
			
			_colorTransform(imageview4, "#00ACC1");
			viewpager1.setCurrentItem(0, true);
		}
		else if (tab == 1) {
			showText(textview5);
			textview13.setText("Apps");
			linear8.setBackground(getResources().getDrawable(R.drawable.tengah));
			
			_colorTransform(imageview5, "#00ACC1");
			viewpager1.setCurrentItem(1, true);
		}
		else if (tab == 2) {
			showTextLong(textview6);
			textview13.setText("Settings");
			linear9.setBackground(getResources().getDrawable(R.drawable.tengah));
			
			_colorTransform(imageview6, "#00ACC1");
			viewpager1.setCurrentItem(2, true);
		}
		
		currentTab = tab;
	}
	{
	}
	
	
	public void _onResultPermission() {
	}
	
	private void createPluginFolders() {
		String basePath = Environment.getExternalStorageDirectory() + "/ZYREX-TOOLS/data";
		String[] folderNames = {".users", "Database", "Plugins", ".cache"};
		
		for (String name : folderNames) {
			File folder = new File(basePath + "/" + name);
			if (!folder.exists()) {
				boolean created = folder.mkdirs();
				if (created) {
					
				} else {
				}
			}
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		
		if (requestCode == 2000) {
			permissionIndex++;
			checkPermissionFlow();
		}
	}
	{
	}
	
	
	public void _click(final View _view) {
		Drawable bg = getResources().getDrawable(R.drawable.tengah);
		
		RippleDrawable ripple = new RippleDrawable(
		ColorStateList.valueOf(Color.parseColor("#33FFFFFF")),
		bg,
		null
		);
		
		_view.setBackground(ripple);
		_view.setClickable(true);
		_view.setFocusable(true);
	}
	
	
	public void _screenshotDetect() {
		
	}
	
	
	public void _layoutPadding() {
	}
	
	public void applyGlobalPadding(View view) {
		
		if (view instanceof ViewGroup) {
			ViewGroup group = (ViewGroup) view;
			for (int i = 0; i < group.getChildCount(); i++) {
				View child = group.getChildAt(i);
				
				
				child.setPaddingRelative(
				(int)dpToPx(0), 
				(int)dpToPx(1), 
				(int)dpToPx(0), 
				(int)dpToPx(1)
				);
				
				
				applyGlobalPadding(child);
			}
		}
	}
	
	private float dpToPx(float dp) {
		return dp * getResources().getDisplayMetrics().density;
	}
	
	{
	}
	
	
	public void _starterHM() {
	}
	private void extractStarter() {
		
		try {
			
			File outFile =
			new File(
			getExternalFilesDir(null),
			"hypers_starter"
			);
			
			if (outFile.exists()) {
				return;
			}
			
			InputStream is =
			getAssets().open(
			"hypers_starter"
			);
			
			FileOutputStream fos =
			new FileOutputStream(
			outFile
			);
			
			byte[] buffer =
			new byte[8192];
			
			int len;
			
			while ((len = is.read(buffer)) > 0) {
				
				fos.write(
				buffer,
				0,
				len
				);
			}
			
			fos.flush();
			
			fos.close();
			
			is.close();
			
			outFile.setReadable(
			true,
			false
			);
			
			outFile.setWritable(
			true,
			false
			);
			
			outFile.setExecutable(
			true,
			false
			);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	private void createStartSh() {
		
		try {
			
			File shFile =
			new File(
			getExternalFilesDir(null),
			"start.sh"
			);
			
			String script =
			
			"#!/system/bin/sh\n\n"
			
			+
			
			"SOURCE_PATH=\"/storage/emulated/0/Android/data/com.hypers.hm/files/hypers_starter\"\n"
			
			+
			
			"STARTER_PATH=\"/data/local/tmp/hypers_starter\"\n\n"
			
			+
			
			"echo \"info: hypers start.sh begin\"\n\n"
			
			+
			
			"recreate_tmp() {\n"
			
			+
			
			"  echo \"info: recreating /data/local/tmp...\"\n"
			
			+
			
			"  rm -rf /data/local/tmp\n"
			
			+
			
			"  mkdir -p /data/local/tmp\n"
			
			+
			
			"}\n\n"
			
			+
			
			"broken_tmp() {\n"
			
			+
			
			"  echo \"fatal: /data/local/tmp broken\"\n"
			
			+
			
			"  exit 1\n"
			
			+
			
			"}\n\n"
			
			+
			
			"if [ -f \"$SOURCE_PATH\" ]; then\n\n"
			
			+
			
			"    echo \"info: copying hypers starter...\"\n\n"
			
			+
			
			"    rm -f $STARTER_PATH\n\n"
			
			+
			
			"    cp \"$SOURCE_PATH\" $STARTER_PATH\n\n"
			
			+
			
			"    res=$?\n\n"
			
			+
			
			"    if [ $res -ne 0 ]; then\n\n"
			
			+
			
			"      recreate_tmp\n\n"
			
			+
			
			"      cp \"$SOURCE_PATH\" $STARTER_PATH\n\n"
			
			+
			
			"      res=$?\n\n"
			
			+
			
			"      if [ $res -ne 0 ]; then\n\n"
			
			+
			
			"        broken_tmp\n"
			
			+
			
			"      fi\n"
			
			+
			
			"    fi\n\n"
			
			+
			
			"    chmod 700 $STARTER_PATH\n\n"
			
			+
			
			"    chown 2000 $STARTER_PATH\n\n"
			
			+
			
			"    chgrp 2000 $STARTER_PATH\n"
			
			+
			
			"fi\n\n"
			
			+
			
			"if [ -f $STARTER_PATH ]; then\n\n"
			
			+
			
			"    echo \"info: executing hypers starter...\"\n\n"
			
			+
			
			"    APK_PATH=$(pm path com.hypers.hm | cut -d: -f2)\n\n"
			
			+
			
			"    $STARTER_PATH \"$APK_PATH\"\n\n"
			
			+
			
			"    result=$?\n\n"
			
			+
			
			"    if [ ${result} -ne 0 ]; then\n\n"
			
			+
			
			"        echo \"info: hypers_starter exit code $result\"\n\n"
			
			+
			
			"    else\n\n"
			
			+
			
			"        echo \"info: hypers_starter exit success\"\n"
			
			+
			
			"    fi\n\n"
			
			+
			
			"else\n\n"
			
			+
			
			"    echo \"starter file not found, open Hypers Manager first.\"\n"
			
			+
			
			"fi\n";
			
			java.io.OutputStreamWriter writer =
			new java.io.OutputStreamWriter(
			new java.io.FileOutputStream(
			shFile
			),
			java.nio.charset.StandardCharsets.UTF_8
			);
			
			writer.write(script);
			
			writer.flush();
			
			writer.close();
			
			shFile.setReadable(
			true,
			false
			);
			
			shFile.setWritable(
			true,
			false
			);
			
			shFile.setExecutable(
			true,
			false
			);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	{
	}
	
}