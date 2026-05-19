package com.hypers.hm;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.Intent;
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
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;
import org.lsposed.hiddenapibypass.library.*;
import rikka.shizuku.api.*;
import rikka.shizuku.provider.*;
import com.hypers.hm.service.HypersPairService;
import com.hypers.hm.debug.ADB;
import android.provider.Settings;

import com.hypers.hm.Hypers;
import com.hypers.hm.bridge.HypersPairingManager;
import com.hypers.hm.bridge.HypersTcpManager;
import com.hypers.hm.model.ServiceStatus;
import com.hypers.hm.rish.RishConfig;
import android.os.Process;
import com.hypers.hm.api.HypersApiConstants;
import com.hypers.hm.utils.EnvironmentUtils;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.ClipboardManager;
import android.content.ClipData;

import android.system.Os;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AdbpairActivity extends AppCompatActivity {
	
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private ImageView imageview1;
	private TextView textview1;
	private ImageView imageview11;
	private ScrollView vscroll1;
	private LinearLayout linear6;
	private LinearLayout linear11;
	private LinearLayout linear5;
	private LinearLayout linear15;
	private LinearLayout linear21;
	private LinearLayout linear12;
	private LinearLayout linear13;
	private LinearLayout linear14;
	private ImageView imageview5;
	private TextView textview6;
	private TextView pidrun;
	private LinearLayout linear8;
	private TextView textview2;
	private LinearLayout linear9;
	private LinearLayout linear10;
	private LinearLayout linear26;
	private LinearLayout linear27;
	private LinearLayout linear7;
	private TextView textview3;
	private ImageView imageview2;
	private ImageView imageview3;
	private TextView textview4;
	private ImageView imageview4;
	private TextView textview5;
	private ImageView imageview12;
	private TextView textview16;
	private ImageView imageview13;
	private TextView textview17;
	private LinearLayout linear16;
	private TextView textview7;
	private LinearLayout linear20;
	private LinearLayout linear18;
	private LinearLayout linear19;
	private TextView textview8;
	private ImageView imageview6;
	private TextView textview11;
	private TextView textview12;
	private ImageView imageview8;
	private TextView textview10;
	private LinearLayout linear22;
	private TextView textview13;
	private LinearLayout linear24;
	private LinearLayout linear25;
	private TextView textview14;
	private ImageView imageview9;
	private ImageView imageview10;
	private TextView textview15;
	
	private Intent inte = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.adbpair);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		linear4 = findViewById(R.id.linear4);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
		imageview11 = findViewById(R.id.imageview11);
		vscroll1 = findViewById(R.id.vscroll1);
		linear6 = findViewById(R.id.linear6);
		linear11 = findViewById(R.id.linear11);
		linear5 = findViewById(R.id.linear5);
		linear15 = findViewById(R.id.linear15);
		linear21 = findViewById(R.id.linear21);
		linear12 = findViewById(R.id.linear12);
		linear13 = findViewById(R.id.linear13);
		linear14 = findViewById(R.id.linear14);
		imageview5 = findViewById(R.id.imageview5);
		textview6 = findViewById(R.id.textview6);
		pidrun = findViewById(R.id.pidrun);
		linear8 = findViewById(R.id.linear8);
		textview2 = findViewById(R.id.textview2);
		linear9 = findViewById(R.id.linear9);
		linear10 = findViewById(R.id.linear10);
		linear26 = findViewById(R.id.linear26);
		linear27 = findViewById(R.id.linear27);
		linear7 = findViewById(R.id.linear7);
		textview3 = findViewById(R.id.textview3);
		imageview2 = findViewById(R.id.imageview2);
		imageview3 = findViewById(R.id.imageview3);
		textview4 = findViewById(R.id.textview4);
		imageview4 = findViewById(R.id.imageview4);
		textview5 = findViewById(R.id.textview5);
		imageview12 = findViewById(R.id.imageview12);
		textview16 = findViewById(R.id.textview16);
		imageview13 = findViewById(R.id.imageview13);
		textview17 = findViewById(R.id.textview17);
		linear16 = findViewById(R.id.linear16);
		textview7 = findViewById(R.id.textview7);
		linear20 = findViewById(R.id.linear20);
		linear18 = findViewById(R.id.linear18);
		linear19 = findViewById(R.id.linear19);
		textview8 = findViewById(R.id.textview8);
		imageview6 = findViewById(R.id.imageview6);
		textview11 = findViewById(R.id.textview11);
		textview12 = findViewById(R.id.textview12);
		imageview8 = findViewById(R.id.imageview8);
		textview10 = findViewById(R.id.textview10);
		linear22 = findViewById(R.id.linear22);
		textview13 = findViewById(R.id.textview13);
		linear24 = findViewById(R.id.linear24);
		linear25 = findViewById(R.id.linear25);
		textview14 = findViewById(R.id.textview14);
		imageview9 = findViewById(R.id.imageview9);
		imageview10 = findViewById(R.id.imageview10);
		textview15 = findViewById(R.id.textview15);
		
		imageview1.setOnClickListener(_v -> finish());
		
		imageview11.setOnClickListener(_v -> {
			_click(imageview11);
			PrivilegeDialog.show(
			this,
			hypersReady,
			permissionGranted,
			action -> {
				if (action == PrivilegeDialog.ACTION_REVOKE) {
					// Cabut izin — Binder tetap hidup
					if (!Hypers.pingBinder()) return;
					int myUid = Process.myUid();
					Hypers.updateFlagsForUid(
					myUid,
					HypersApiConstants.MASK_PERMISSION,
					HypersApiConstants.FLAG_DENIED
					);
					permissionGranted = false;
					updateButtonStates(false);
					Toast.makeText(this,
					"Privilege dicabut. Binder masih aktif.",
					Toast.LENGTH_SHORT).show();
					
				} else if (action == PrivilegeDialog.ACTION_EXIT) {
					// Disconnect total — exit HypersManager
					if (!Hypers.pingBinder()) return;
					Hypers.exit();
					hypersReady = false;
					permissionGranted = false;
					updateButtonStates(false);
					Toast.makeText(this,
					"HypersManager dihentikan.",
					Toast.LENGTH_SHORT).show();
					
				} else if (action == PrivilegeDialog.ACTION_RESTORE) {
					// Restore privilege
					if (!Hypers.pingBinder()) {
						Toast.makeText(this,
						"Binder mati. Perlu pairing ulang.",
						Toast.LENGTH_SHORT).show();
						return;
					}
					int myUid = Process.myUid();
					Hypers.updateFlagsForUid(
					myUid,
					HypersApiConstants.MASK_PERMISSION,
					HypersApiConstants.FLAG_ALLOWED
					);
					permissionGranted = true;
					updateButtonStates(true);
					Toast.makeText(this,
					"Privilege dipulihkan.",
					Toast.LENGTH_SHORT).show();
				}
			});
		});
		
		linear9.setOnClickListener(_v -> {
			_click(linear9);
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
				
				// KUNCI TARGET SDK 36: Cek ijin NEARBY_WIFI_DEVICES untuk Android 13+ (API 33+)
				if (android.os.Build.VERSION.SDK_INT >= 33 && 
				checkSelfPermission("android.permission.NEARBY_WIFI_DEVICES") != android.content.pm.PackageManager.PERMISSION_GRANTED) {
					
					// Senggol OS biar nampilin pop-up ijin resmi ke user
					requestPermissions(new String[]{"android.permission.NEARBY_WIFI_DEVICES"}, 101);
					
				} else {
					// Ijin sudah aman atau versi Android 11/12, langsung gaskeun buka dialog pairing
					try {
						AdbPairDialogFragment dialog = new AdbPairDialogFragment();
						dialog.show(
						((androidx.fragment.app.FragmentActivity) this).getSupportFragmentManager(),
						"pairing"
						);
					} catch (Exception e) {
						android.widget.Toast.makeText(this, "Pairing error: " + e.getMessage(), android.widget.Toast.LENGTH_SHORT).show();
					}
				}
				
			} else {
				// Android 10 kebawah (Zonk / Wireless Debugging belum ada via API resmi)
				android.widget.Toast.makeText(this, "Wireless Debugging butuh minimal Android 11, Bree!", android.widget.Toast.LENGTH_SHORT).show();
			}
			
		});
		
		linear10.setOnClickListener(_v -> {
			_click(linear10);
			onClickStart();
		});
		
		linear26.setOnClickListener(_v -> {
			_click(linear26);
			int port = (detectedPort > 0) ? detectedPort 
			: EnvironmentUtils.getAdbTcpPort();
			String host = (detectedPort > 0) ? detectedHost : "127.0.0.1";
			
			if (port > 0) {
				Intent i = new Intent(this, StarterActivity.class);
				i.putExtra(StarterActivity.EXTRA_IS_ROOT, false);
				i.putExtra(StarterActivity.EXTRA_HOST, host);
				i.putExtra(StarterActivity.EXTRA_PORT, port);
				startActivity(i);
				Toast.makeText(this, "Connecting to TCP " + port, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "TCP ADB NOT ENABLED", Toast.LENGTH_SHORT).show();
			}
		});
		
		linear27.setOnClickListener(_v -> {
			_click(linear27);
			onClickDisableTcp();
		});
		
		linear18.setOnClickListener(_v -> {
			_click(linear18);
			
			Intent i = new Intent(this, StarterActivity.class);
			i.putExtra(StarterActivity.EXTRA_IS_ROOT, true);
			
			startActivity(i);
			
			Toast.makeText(this,
			"Starting ROOT mode...",
			Toast.LENGTH_SHORT).show();
		});
		
		textview12.setOnClickListener(_v -> {
			_click(textview12);
			inte.setAction(Intent.ACTION_VIEW);
			inte.setData(Uri.parse("https://dontkillmyapp.com/"));
			startActivity(inte);
		});
		
		linear24.setOnClickListener(_v -> {
			_click(linear24);
			onClickComputer();
		});
	}
	
	private void initializeLogic() {
		_sizetetap();
		vscroll1.setVerticalScrollBarEnabled(false);
		if (Build.VERSION.SDK_INT >= 34) {
			if (checkSelfPermission(Manifest.permission.FOREGROUND_SERVICE_CONNECTED_DEVICE) != PackageManager.PERMISSION_GRANTED) {
				requestPermissions(new String[]{
					Manifest.permission.FOREGROUND_SERVICE_CONNECTED_DEVICE
				}, 101);
			}
		}
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			if (checkSelfPermission(android.Manifest.permission.NEARBY_WIFI_DEVICES) != PackageManager.PERMISSION_GRANTED) {
				requestPermissions(new String[]{android.Manifest.permission.NEARBY_WIFI_DEVICES}, 101);
			}
		}
		
		androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(
		linear2,
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
		Hypers.addBinderReceivedListenerSticky(new Hypers.OnBinderReceivedListener() {
			@Override
			public void onBinderReceived() {
				try {
					IBinder binder = Hypers.requireService().asBinder(); // atau getBinder()
					String token   = "hypers.manager.hm.server.IHypersService";
					int    txStart = 1;
					
					RishConfig.init(binder, token, txStart);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				hypersReady = true;
				runOnUiThread(() -> updateHypersStatus());
			}
		});
		
		Hypers.addBinderDeadListener(new Hypers.OnBinderDeadListener() {
			@Override
			public void onBinderDead() {
				hypersReady = false;
				runOnUiThread(() -> updateHypersStatus());
			}
		});
		checkAdbTcp();
		updateHypersStatus();
		_colorModel();
		_font();
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		
		try {
			
			File dir = getExternalFilesDir(null);
			
			if (dir == null) return;
			
			// ===================================
			// COPY hypers_starter FROM ASSETS
			// ===================================
			
			File starter = new File(dir, "hypers_starter");
			
			// Selalu overwrite agar versi terbaru dari assets
			InputStream in = getAssets().open("hypers_starter");
			FileOutputStream out = new FileOutputStream(starter);
			byte[] buffer = new byte[8192];
			int len;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			in.close();
			out.close();
			
			// ===================================
			// CHMOD hypers_starter — waitFor!
			// ===================================
			
			try {
				// Cara 1: Os.chmod (paling reliable, tidak butuh shell)
				android.system.Os.chmod(starter.getAbsolutePath(), 0755);
			} catch (Exception e1) {
				// Fallback: Runtime.exec + waitFor
				java.lang.Process p = Runtime.getRuntime().exec(
				new String[]{"chmod", "755", starter.getAbsolutePath()}
				);
				p.waitFor();
			}
			
			// ===================================
			// CREATE start.sh
			// ===================================
			
			autoCreateStartScript();
			
			// ===================================
			// COPY APK -> base.apk
			// ===================================
			
			File apk = new File(dir, "base.apk");
			
			// Cek apakah APK source lebih baru dari base.apk yang tersimpan
			File apkSource = new File(getPackageCodePath());
			if (!apk.exists() || apk.length() != apkSource.length()) {
				
				InputStream apkIn = new FileInputStream(apkSource);
				FileOutputStream apkOut = new FileOutputStream(apk);
				byte[] buf = new byte[8192];
				int read;
				while ((read = apkIn.read(buf)) > 0) {
					apkOut.write(buf, 0, read);
				}
				apkIn.close();
				apkOut.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Hypers.addBinderReceivedListenerSticky(new Hypers.OnBinderReceivedListener() {
			@Override
			public void onBinderReceived() {
				hypersReady = true;
				runOnUiThread(() -> updateHypersStatus());
			}
		});
		
		Hypers.addBinderDeadListener(new Hypers.OnBinderDeadListener() {
			@Override
			public void onBinderDead() {
				hypersReady = false;
				runOnUiThread(() -> updateHypersStatus());
			}
		});
		checkAdbTcp();
		updateHypersStatus();
	}
	public void _startAdb() {
	}
	private void runTechnicalCommands() {
		ADB adb = ADB.getInstance(this);
		
		if (Boolean.TRUE.equals(adb.started.getValue())) {
			
			adb.sendToShellProcess("pm grant " + getPackageName() + " android.permission.WRITE_SECURE_SETTINGS");
			
			
			Toast.makeText(this, "Running!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "ADB belum siap, silakan pairing dulu", Toast.LENGTH_SHORT).show();
		}
	}
	{
	}
	
	
	public void _colorModel() {
		linear11.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear13.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0x1500ACC1));
		linear5.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear7.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0x1500ACC1));
		linear15.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear19.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0x1500ACC1));
		linear21.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear25.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0x1500ACC1));
		linear9.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)23, 0x5000ACC1));
		linear10.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)23, 0x5000ACC1));
		linear18.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)23, 0x5000ACC1));
		linear24.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)23, 0x5000ACC1));
		linear26.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)23, 0x5000ACC1));
		linear27.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)23, 0x5000ACC1));
	}
	
	
	public void _click(final View _view) {
		RippleDrawable ripple = new RippleDrawable(
		ColorStateList.valueOf(Color.parseColor("#33FFFFFF")),
		_view.getBackground(),
		null
		);
		
		_view.setBackground(ripple);
		_view.setClickable(true);
		_view.setFocusable(true);
	}
	
	
	public void _font() {
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffbold.ttf"), 0);
		textview6.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/style01.ttf"), 0);
		pidrun.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/style01.ttf"), 0);
		textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffbold.ttf"), 0);
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/style01.ttf"), 0);
		textview4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/style01.ttf"), 0);
		textview5.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/style01.ttf"), 0);
		textview8.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffbold.ttf"), 0);
		textview7.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/style01.ttf"), 0);
		textview11.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/style01.ttf"), 0);
		textview12.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/style01.ttf"), 0);
		textview10.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/style01.ttf"), 0);
		textview14.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffbold.ttf"), 0);
		textview13.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/style01.ttf"), 0);
		textview15.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/style01.ttf"), 0);
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
	
	
	public void _getTCP() {
	}                                   
	// === TAMBAHKAN FIELD INI DI ATAS onCreate ===
	private com.hypers.hm.adb.AdbMdns adbMdns;
	private int detectedPort = -1;
	private String detectedHost = "127.0.0.1";
	
	// === GANTI checkAdbTcp() DENGAN INI ===
	private void checkAdbTcp() {
		// Coba port lama dulu (Android 10 ke bawah / TCP ADB)
		int port = EnvironmentUtils.getAdbTcpPort();
		if (port > 0) {
			detectedPort = port;
			linear26.setVisibility(View.VISIBLE);
			linear27.setVisibility(View.VISIBLE);
			return;
		}
		
		// Android 11+ → pakai mDNS untuk auto-detect
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
			startMdnsDiscovery();
		} else {
			linear26.setVisibility(View.GONE);
			linear27.setVisibility(View.GONE);
		}
	}
	
	// === TAMBAHKAN METHOD BARU INI ===
	@android.annotation.SuppressLint("NewApi")
	private void startMdnsDiscovery() {
		if (adbMdns != null) return; // sudah jalan
		
		adbMdns = new com.hypers.hm.adb.AdbMdns(
		this,
		com.hypers.hm.adb.AdbMdns.TLS_CONNECT,
		new com.hypers.hm.adb.AdbMdns.Callback() {
			@Override
			public void onChanged(String host, int port) {
				runOnUiThread(() -> {
					if (port > 0) {
						detectedPort = port;
						detectedHost = (host != null && !host.isEmpty()) 
						? host : "127.0.0.1";
						linear26.setVisibility(View.VISIBLE);
						linear27.setVisibility(View.VISIBLE);
						Toast.makeText(AdbpairActivity.this,
						"Port terdeteksi: " + port, Toast.LENGTH_SHORT).show();
					} else {
						// port = -1 → service hilang
						detectedPort = -1;
						linear26.setVisibility(View.GONE);
						linear27.setVisibility(View.GONE);
					}
				});
			}
		}
		);
		adbMdns.start();
	}
	{
	}
	
	
	public void _runPrivilage() {
	}
	public void runAdbCommand(String command, String label) {
		if (!hypersReady || !permissionGranted) { showHypersNotReadyDialog(); return; }
		Toast.makeText(this, "Menjalankan: " + label, Toast.LENGTH_SHORT).show();
		HypersPairingManager.startOnAdb().execute(command, result ->
		showInfoDialog("ADB: " + label, result.isEmpty() ? "(tidak ada output)" : result));
	}
	
	public void runRootCommand(String command, String label) {
		if (!hypersReady || !permissionGranted) { showHypersNotReadyDialog(); return; }
		Toast.makeText(this, "Root: " + label, Toast.LENGTH_SHORT).show();
		HypersPairingManager.startOnRoot().execute(command, result ->
		showInfoDialog("Root: " + label, result.isEmpty() ? "(tidak ada output)" : result));
	}
	
	
	private void updateButtonStates(boolean enabled) {}
	{
	}
	
	
	public void _dialogShow() {
	}
	private void showInfoDialog(String title, String message) {
		runOnUiThread(() -> new AlertDialog.Builder(this)
		.setTitle(title).setMessage(message)
		.setPositiveButton("Oke", null).show());
	}
	
	private void showErrorDialog(String title, String message) {
		runOnUiThread(() -> new AlertDialog.Builder(this)
		.setTitle("" + title).setMessage(message)
		.setPositiveButton("Oke", null).show());
	}
	public void onClickPairing() {
		
		new AlertDialog.Builder(this)
		.setTitle("🔗 Mulai Pairing")
		.setMessage(
		"Aplikasi akan membuka Wireless Debugging.\n\n" +
		"Aktifkan:\n" +
		"• Wireless debugging\n" +
		"• Pair device with pairing code\n\n" +
		"Lanjutkan?"
		)
		
		.setPositiveButton("Buka Pengaturan", (d, w) -> {
			
			try {
				
				Intent intent;
				
				if (android.os.Build.VERSION.SDK_INT >= 30) {
					
					intent = new Intent(
					"com.android.settings.WIRELESS_DEBUGGING_SETTINGS"
					);
					
				} else {
					
					intent = new Intent(
					android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS
					);
				}
				
				startActivity(intent);
				
				Toast.makeText(
				this,
				"Aktifkan Wireless Debugging",
				Toast.LENGTH_LONG
				).show();
				
			} catch (Exception e) {
				
				try {
					
					Intent intent = new Intent(
					android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS
					);
					
					startActivity(intent);
					
				} catch (Exception ee) {
					
					Toast.makeText(
					this,
					"Tidak dapat membuka Developer Options",
					Toast.LENGTH_SHORT
					).show();
				}
			}
		})
		
		.setNeutralButton("Start Pairing", (d, w) -> {
			
			try {
				
				HypersPairingManager.startPairing(this);
				
				Toast.makeText(
				this,
				"Pairing dimulai",
				Toast.LENGTH_LONG
				).show();
				
			} catch (Exception e) {
				
				showErrorDialog(
				"Pairing Gagal",
				String.valueOf(e)
				);
			}
		})
		
		.setNegativeButton("Batal", null)
		
		.show();
	}
	
	// =================================================================
	//  ONCLICK — START ON ROOT
	// =================================================================
	
	public void onClickRoot() {
		if (!hypersReady || !permissionGranted) {
			showHypersNotReadyDialog();
			return;
		}
		
		new AlertDialog.Builder(this)
		.setTitle("⚠ Start via Root")
		.setMessage(
		"Akan menjalankan perintah sebagai ROOT.\n\n" +
		"Akses root memberikan kontrol penuh ke sistem.\n" +
		"Gunakan hanya jika Anda tahu risikonya.\n\n" +
		"Lanjutkan?"
		)
		.setPositiveButton("Lanjutkan", (d, w) -> {
			HypersPairingManager.startOnRoot().execute("id && whoami", result -> {
				runOnUiThread(() -> {
					if (result.contains("uid=0") || result.contains("root")) {
						showInfoDialog("✓ Root Aktif",
						"Berhasil menjalankan perintah sebagai ROOT.\n\nOutput:\n" + result);
					} else if (!result.isEmpty()) {
						showInfoDialog("Root - Output", "Output:\n" + result);
					} else {
						showErrorDialog("Root Gagal",
						"Tidak ada output dari root shell.\n" +
						"Pastikan perangkat sudah di-root dan\n" +
						"Hypers memiliki akses root.");
					}
				});
			});
		})
		.setNegativeButton("Batal", null)
		.show();
	}
	
	// =================================================================
	//  ONCLICK — START ON COMPUTER
	// =================================================================
	
	public void onClickComputer() {
		
		String command =
		"adb shell sh /storage/emulated/0/Android/data/" +
		getPackageName() +
		"/files/start.sh";
		
		new AlertDialog.Builder(this)
		.setTitle("View command")
		.setMessage(
		command +
		"\n\n• Pastikan Wireless Debugging aktif."
		)
		
		.setPositiveButton("Send", (d, w) -> {
			
			Intent send = new Intent(Intent.ACTION_SEND);
			
			send.setType("text/plain");
			
			send.putExtra(
			Intent.EXTRA_TEXT,
			command
			);
			
			startActivity(
			Intent.createChooser(
			send,
			"Send command"
			)
			);
		})
		
		.setNeutralButton("Copy", (d, w) -> {
			
			android.content.ClipboardManager cb =
			(android.content.ClipboardManager)
			getSystemService(CLIPBOARD_SERVICE);
			
			cb.setPrimaryClip(
			ClipData.newPlainText(
			"hypers_cmd",
			command
			)
			);
			
			Toast.makeText(
			this,
			"Command copied",
			Toast.LENGTH_SHORT
			).show();
		})
		
		.setNegativeButton("Cancel", null)
		
		.show();
	}
	public void onClickStart() {
		if (!hypersReady) {
			// Cek dulu apakah wireless debugging aktif, tawarkan pairing dulu
			new AlertDialog.Builder(this)
			.setTitle("Hypers belum aktif")
			.setMessage("Hypers belum connect. Mau mulai pairing via Wireless Debugging dulu?")
			.setPositiveButton("Pairing Dulu", (d, w) -> onClickPairing())
			.setNegativeButton("Batal", null)
			.show();
			return;
		}
		if (!permissionGranted) { showPermissionDialog(); return; }
		
		new AlertDialog.Builder(this)
		.setTitle("▶ Start Hypers")
		.setMessage("Mulai sesi ADB shell melalui Hypers?")
		.setPositiveButton("Ya, Mulai", (d, w) -> {
			HypersPairingManager.startOnAdb().execute("echo Hypers_OK && id", result -> {
				runOnUiThread(() -> {
					if (!result.isEmpty()) {
						showInfoDialog("✓ Hypers Aktif",
						"ADB shell berhasil dijalankan.\n\nOutput:\n" + result);
					} else {
						showErrorDialog("Gagal Start",
						"Tidak ada output dari shell.\n" +
						"Pastikan HypersManager sudah berjalan.");
					}
				});
			});
		})
		.setNegativeButton("Batal", null)
		.show();
	}
	
	
	private void autoGrantSelfPermission() {
		new Thread(() -> {
			try {
				int current = Hypers.getFlagsForUid(
				getApplicationInfo().uid, MASK_PERMISSION);
				
				boolean alreadyGranted = (current & FLAG_ALLOWED) == FLAG_ALLOWED;
				if (alreadyGranted) {
					runOnUiThread(() -> {
						permissionGranted = true; // ← TAMBAH INI
						textview6.setText("HYPERS CONNECTED ✓");
					});
					return;
				}
				
				Hypers.updateFlagsForUid(
				getApplicationInfo().uid, MASK_PERMISSION, FLAG_ALLOWED);
				
				runOnUiThread(() -> {
					permissionGranted = true; // ← TAMBAH INI
					textview6.setText("HYPERS CONNECTED ✓");
				});
				
			} catch (Throwable e) {
				try {
					if (Hypers.checkSelfPermission() 
					!= android.content.pm.PackageManager.PERMISSION_GRANTED) {
						Hypers.requestPermission(0);
					}
				} catch (Throwable ignored) { }
			}
		}).start();
	}
	private void showHypersNotReadyDialog() {
		
		new androidx.appcompat.app.AlertDialog.Builder(this)
		.setTitle("Hypers belum aktif")
		.setMessage("Jalankan Hypers terlebih dahulu.")
		.setPositiveButton("OK", null)
		.show();
	}
	
	private void showPermissionDialog() {
		
		new androidx.appcompat.app.AlertDialog.Builder(this)
		.setTitle("Permission")
		.setMessage("Grant permission untuk aplikasi ini?")
		.setPositiveButton("Grant", (d, w) -> {
			
			Hypers.requestPermission(1001);
			
		})
		.setNegativeButton("Cancel", null)
		.show();
	}
	{
	}
	
	
	public void _updateStatus() {
	}
	private static final int FLAG_ALLOWED = 1 << 1;
	private static final int FLAG_DENIED  = 1 << 2;
	private static final int MASK_PERMISSION = FLAG_ALLOWED | FLAG_DENIED;
	
	private void updateHypersStatus() {
		
		boolean connected;
		try {
			connected = Hypers.pingBinder();
		} catch (Throwable e) {
			connected = false;
		}
		
		if (!connected) {
			textview6.setText("HYPERS NOT CONNECTED");
			textview6.setTextColor(Color.RED);
			imageview11.setVisibility(View.GONE);
			pidrun.setVisibility(View.GONE);
			linear26.setVisibility(View.GONE);
			linear27.setVisibility(View.GONE);
			return;
		}
		
		// CONNECTED
		textview6.setText("HYPERS CONNECTED");
		textview6.setTextColor(Color.parseColor("#4CAF50"));
		imageview11.setVisibility(View.VISIBLE);
		pidrun.setVisibility(View.VISIBLE);
		linear26.setVisibility(View.VISIBLE);
		linear27.setVisibility(View.VISIBLE);
		
		int uid = -1;
		try {
			uid = Hypers.getUid();
		} catch (Throwable e) { }
		
		if (uid == 0) {
			pidrun.setText("UID: ROOT (0)");
		} else if (uid > 0) {
			pidrun.setText("UID: " + uid + " (ADB)");
		} else {
			pidrun.setText("UID: UNKNOWN");
		}
		
		// Auto-grant permission untuk app sendiri
		autoGrantSelfPermission();
	}
	{
	}
	
	
	public void _variable() {
	}
	private boolean hypersReady      = false;
	private boolean tcpEnabled = false;
	private boolean permissionGranted = false;
	{
	}
	
	
	public void _tcpMode() {
	}
	
	public void onClickEnableTcp() {
		// Jika Binder sudah hidup, gunakan via Hypers (lebih aman)
		if (hypersReady && permissionGranted) {
			HypersTcpManager.enableTcpViaHypers(false, (success, message) -> {
				runOnUiThread(() -> {
					tcpEnabled = success;
					new androidx.appcompat.app.AlertDialog.Builder(this)
					.setTitle(success ? "✅ TCP Aktif" : "❌ Gagal")
					.setMessage(message)
					.setPositiveButton("Oke", null)
					.show();
				});
			});
			return;
		}
		
		new androidx.appcompat.app.AlertDialog.Builder(this)
		.setTitle("🌐 Aktifkan TCP")
		.setMessage("Pilih mode untuk aktifkan TCP/ADB:")
		.setPositiveButton("Via ADB (Tidak Root)", (d, w) -> {
			HypersTcpManager.enableTcp(false, (success, message) -> {
				runOnUiThread(() -> {
					tcpEnabled = success;
					Toast.makeText(this, message, Toast.LENGTH_LONG).show();
				});
			});
		})
		.setNeutralButton("Via Root", (d, w) -> {
			HypersTcpManager.enableTcp(true, (success, message) -> {
				runOnUiThread(() -> {
					tcpEnabled = success;
					Toast.makeText(this, message, Toast.LENGTH_LONG).show();
				});
			});
		})
		.setNegativeButton("Batal", null)
		.show();
	}
	
	public void onClickDisableTcp() {
		new androidx.appcompat.app.AlertDialog.Builder(this)
		.setTitle("🔌 Matikan TCP")
		.setMessage(
		"Matikan ADB over TCP?\n\n" +
		(hypersReady
		? "✅ Hypers Binder masih hidup.\n   App tetap berfungsi tanpa TCP."
		: "⚠ Hypers Binder belum aktif.\n   Kamu mungkin tidak bisa reconnect\n   tanpa TCP atau USB.")
		)
		.setPositiveButton("Ya, Matikan", (d, w) -> {
			if (hypersReady && permissionGranted) {
				// Cara terbaik: via Hypers Binder
				HypersTcpManager.disableTcpViaHypers(false, (success, message) -> {
					runOnUiThread(() -> {
						tcpEnabled = !success;
						Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
					});
				});
			} else {
				// Fallback via shell
				HypersTcpManager.disableTcp(false, (success, message) -> {
					runOnUiThread(() -> {
						tcpEnabled = !success;
						Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
					});
				});
			}
		})
		.setNegativeButton("Batal", null)
		.show();
	}
	
	{
	}
	
	
	public void _create() {
	}
	private void autoCreateStartScript() {
		// 1. Tentukan folder target eksternal files (scoped storage)
		File filesDir = getExternalFilesDir(null);
		if (filesDir == null) {
			Log.e("HypersAutoCreate", "Gagal mengakses External Files Directory!");
			return;
		}
		
		// 2. Tentukan koordinat file start.sh
		File scriptFile = new File(filesDir, "start.sh");
		
		// 3. Susun isi teks shell script script secara utuh
		String scriptContent = "#!/system/bin/sh\n" +
		"# -------------------------------------------------------------\n" +
		"# HYPERS Privileged Server Loader via ADB Shell Launcher\n" +
		"# -------------------------------------------------------------\n\n" +
		"BASE_DIR=\"/storage/emulated/0/Android/data/com.hypers.hm/files\"\n" +
		"BIN_NAME=\"starter_hypers\"\n" +
		"TARGET_BIN=\"/data/local/tmp/$BIN_NAME\"\n\n" +
		"echo \"==================================================\"\n" +
		"echo \"      🚀 HYPERS Privileged Server Initializer     \"\n" +
		"echo \"==================================================\"\n\n" +
		"# 1. Validasi Keberadaan Biner Hasil Compile\n" +
		"if [ ! -f \"$BASE_DIR/$BIN_NAME\" ]; then\n" +
		"    echo \"❌ Error: Biner '$BIN_NAME' tidak ditemukan di folder files!\"\n" +
		"    echo \"   Pastikan biner hasil compile Termux sudah ada di:\"\n" +
		"    echo \"   $BASE_DIR/\"\n" +
		"    echo \"==================================================\"\n" +
		"    exit 1\n" +
		"fi\n\n" +
		"# 2. Proses Cloning Biner ke Area Eksekusi Aman ADB\n" +
		"echo \"📦 Menyalin biner ke wilayah eksekusi aman (/data/local/tmp)...\"\n" +
		"cp \"$BASE_DIR/$BIN_NAME\" \"$TARGET_BIN\"\n\n" +
		"# 3. Berikan Akses Eksekusi Penuh (Chmod +x)\n" +
		"chmod +x \"$TARGET_BIN\"\n" +
		"echo \"✅ Izin eksekusi berhasil diberikan.\"\n\n" +
		"# 4. Tembakkan Biner Utama untuk Melepas Daemon Server ke Background\n" +
		"echo \"⚡ Meluncurkan Core hypers_server...\"\n" +
		"echo \"--------------------------------------------------\"\n" +
		"\"$TARGET_BIN\"\n" +
		"echo \"--------------------------------------------------\"\n\n" +
		"echo \"🎉 Proses inisialisasi selesai!\"\n" +
		"echo \"==================================================\"\n";
		
		// 4. Proses penulisan file secara aman ke dalam storage
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(scriptFile);
			fos.write(scriptContent.getBytes());
			fos.flush();
			Log.d("HypersAutoCreate", "Sukses! start.sh berhasil dibuat secara otomatis di: " + scriptFile.getAbsolutePath());
		} catch (IOException e) {
			Log.e("HypersAutoCreate", "Gagal menulis file start.sh", e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException ignored) {}
			}
		}
	}
	{
	}
	
	
	public void _onReq() {
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 101) {
			if (grantResults.length > 0 && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
				// User setuju! Eksekusi aman tanpa memicu crash SecurityException FGS
				try {
					AdbPairDialogFragment dialog = new AdbPairDialogFragment();
					dialog.show(
					((androidx.fragment.app.FragmentActivity) this).getSupportFragmentManager(),
					"pairing"
					);
				} catch (Exception e) {
					android.widget.Toast.makeText(this, "Pairing error: " + e.getMessage(), android.widget.Toast.LENGTH_SHORT).show();
				}
			} else {
				android.widget.Toast.makeText(this, "Wajib diizinkan biar radar mDNS kaga budek, Bree!", android.widget.Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	{
	}
	
}