package com.hypers.hm;
import com.hypers.hm.ExecEngine;

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
import java.io.InputStream;
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



public class MainsFragmentActivity extends Fragment {
	
	private final Shizuku.OnRequestPermissionResultListener REQUEST_PERMISSION_RESULT_LISTENER =
	new Shizuku.OnRequestPermissionResultListener() {
		@Override
		public void onRequestPermissionResult(int requestCode, int grantResult) {
			
			if (requestCode == 1000) {
				if (grantResult == PackageManager.PERMISSION_GRANTED) {
					
					android.widget.Toast.makeText(getContext().getApplicationContext(),
					"Shizuku Permission Granted", 0).show();
					
				} else {
					
					android.widget.Toast.makeText(getContext().getApplicationContext(),
					"Shizuku Permission Denied", 0).show();
					
				}
			}
			
		}
	};
	
	private LinearLayout l1;
	private ScrollView vscroll1;
	private LinearLayout linear145;
	private LinearLayout Main;
	private LinearLayout linear45;
	private LinearLayout linear6;
	private LinearLayout linear15;
	private LinearLayout linear23;
	private LinearLayout linear31;
	private LinearLayout linear94;
	private LinearLayout linear142;
	private LinearLayout linear149;
	private LinearLayout linear12;
	private RelativeLayout relativelayout1;
	private TextView textview2;
	private TextView textview3;
	private TextView textview1;
	private ImageView imageview1;
	private LinearLayout linear13;
	private LinearLayout linear14;
	private ImageView imageview2;
	private LinearLayout linear16;
	private LinearLayout linear146;
	private LinearLayout linear17;
	private LinearLayout linear18;
	private TextView textview4;
	private TextView textview5;
	private LinearLayout linear147;
	private ImageView ic_chipset;
	private LinearLayout linear148;
	private TextView textview93;
	private TextView textview94;
	private LinearLayout linear19;
	private LinearLayout linear20;
	private ImageView imageview3;
	private LinearLayout linear21;
	private TextView textview6;
	private TextView textview7;
	private ImageView imageview4;
	private LinearLayout linear22;
	private TextView textview8;
	private TextView textview9;
	private LinearLayout linear24;
	private LinearLayout linear25;
	private LinearLayout linear27;
	private LinearLayout linear29;
	private ImageView imageview5;
	private TextView textview11;
	private TextView textview10;
	private LinearLayout linear28;
	private LinearLayout linear30;
	private ImageView imageview6;
	private TextView textview13;
	private TextView textview12;
	private LinearLayout linear33;
	private LinearLayout linear32;
	private ImageView imageview8;
	private ImageView imageview7;
	private TextView textview14;
	private TextView textview15;
	private LinearLayout linear95;
	private LinearLayout linear96;
	private ImageView imageview36;
	private ImageView imageview37;
	private TextView textview58;
	private TextView textview59;
	private LinearLayout linear143;
	private LinearLayout linear144;
	private ImageView imageview56;
	private ImageView imageview57;
	private TextView textview91;
	private TextView textview92;
	
	private Intent i = new Intent();
	private Intent in = new Intent();
	private SharedPreferences listmanager;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.mains_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		l1 = _view.findViewById(R.id.l1);
		vscroll1 = _view.findViewById(R.id.vscroll1);
		linear145 = _view.findViewById(R.id.linear145);
		Main = _view.findViewById(R.id.Main);
		linear45 = _view.findViewById(R.id.linear45);
		linear6 = _view.findViewById(R.id.linear6);
		linear15 = _view.findViewById(R.id.linear15);
		linear23 = _view.findViewById(R.id.linear23);
		linear31 = _view.findViewById(R.id.linear31);
		linear94 = _view.findViewById(R.id.linear94);
		linear142 = _view.findViewById(R.id.linear142);
		linear149 = _view.findViewById(R.id.linear149);
		linear12 = _view.findViewById(R.id.linear12);
		relativelayout1 = _view.findViewById(R.id.relativelayout1);
		textview2 = _view.findViewById(R.id.textview2);
		textview3 = _view.findViewById(R.id.textview3);
		textview1 = _view.findViewById(R.id.textview1);
		imageview1 = _view.findViewById(R.id.imageview1);
		linear13 = _view.findViewById(R.id.linear13);
		linear14 = _view.findViewById(R.id.linear14);
		imageview2 = _view.findViewById(R.id.imageview2);
		linear16 = _view.findViewById(R.id.linear16);
		linear146 = _view.findViewById(R.id.linear146);
		linear17 = _view.findViewById(R.id.linear17);
		linear18 = _view.findViewById(R.id.linear18);
		textview4 = _view.findViewById(R.id.textview4);
		textview5 = _view.findViewById(R.id.textview5);
		linear147 = _view.findViewById(R.id.linear147);
		ic_chipset = _view.findViewById(R.id.ic_chipset);
		linear148 = _view.findViewById(R.id.linear148);
		textview93 = _view.findViewById(R.id.textview93);
		textview94 = _view.findViewById(R.id.textview94);
		linear19 = _view.findViewById(R.id.linear19);
		linear20 = _view.findViewById(R.id.linear20);
		imageview3 = _view.findViewById(R.id.imageview3);
		linear21 = _view.findViewById(R.id.linear21);
		textview6 = _view.findViewById(R.id.textview6);
		textview7 = _view.findViewById(R.id.textview7);
		imageview4 = _view.findViewById(R.id.imageview4);
		linear22 = _view.findViewById(R.id.linear22);
		textview8 = _view.findViewById(R.id.textview8);
		textview9 = _view.findViewById(R.id.textview9);
		linear24 = _view.findViewById(R.id.linear24);
		linear25 = _view.findViewById(R.id.linear25);
		linear27 = _view.findViewById(R.id.linear27);
		linear29 = _view.findViewById(R.id.linear29);
		imageview5 = _view.findViewById(R.id.imageview5);
		textview11 = _view.findViewById(R.id.textview11);
		textview10 = _view.findViewById(R.id.textview10);
		linear28 = _view.findViewById(R.id.linear28);
		linear30 = _view.findViewById(R.id.linear30);
		imageview6 = _view.findViewById(R.id.imageview6);
		textview13 = _view.findViewById(R.id.textview13);
		textview12 = _view.findViewById(R.id.textview12);
		linear33 = _view.findViewById(R.id.linear33);
		linear32 = _view.findViewById(R.id.linear32);
		imageview8 = _view.findViewById(R.id.imageview8);
		imageview7 = _view.findViewById(R.id.imageview7);
		textview14 = _view.findViewById(R.id.textview14);
		textview15 = _view.findViewById(R.id.textview15);
		linear95 = _view.findViewById(R.id.linear95);
		linear96 = _view.findViewById(R.id.linear96);
		imageview36 = _view.findViewById(R.id.imageview36);
		imageview37 = _view.findViewById(R.id.imageview37);
		textview58 = _view.findViewById(R.id.textview58);
		textview59 = _view.findViewById(R.id.textview59);
		linear143 = _view.findViewById(R.id.linear143);
		linear144 = _view.findViewById(R.id.linear144);
		imageview56 = _view.findViewById(R.id.imageview56);
		imageview57 = _view.findViewById(R.id.imageview57);
		textview91 = _view.findViewById(R.id.textview91);
		textview92 = _view.findViewById(R.id.textview92);
		listmanager = getContext().getSharedPreferences("listmanager", Activity.MODE_PRIVATE);
		
		//OnTouch
		Main.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event){
				int ev = event.getAction();
				switch (ev) {
					case MotionEvent.ACTION_DOWN:
					
					
					
					break;
					case MotionEvent.ACTION_UP:
					
					
					
					break;
				} return true;
			}
		});
		
		linear31.setOnClickListener(_v -> {
			in.setAction(Intent.ACTION_VIEW);
			in.setData(Uri.parse("https://whatsapp.com/channel/0029Vb3mFQGHbFV9XTlk7e0D"));
			startActivity(in);
			_click(linear31);
		});
		
		linear94.setOnClickListener(_v -> {
			i.setClass(requireContext(), ListeditActivity.class);
			startActivity(i);
			_click(linear94);
		});
		
		linear142.setOnClickListener(_v -> {
			_click(linear142);
			i.setClass(requireContext(), PrivacypluginsActivity.class);
			startActivity(i);
		});
		
		linear24.setOnClickListener(_v -> {
			_click(linear24);
			android.content.pm.ShortcutManager shortcutManager = null;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
				shortcutManager = requireContext().getSystemService(android.content.pm.ShortcutManager.class);
			}
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				if (shortcutManager != null) {
					android.content.pm.ShortcutInfo a1 = new android.content.pm.ShortcutInfo.Builder(requireContext(), "TERMINAL CMD")
					.setShortLabel("TERMINAL CMD")
					.setLongLabel("TERMINAL CMD")
					.setRank(0)
					.setIntent(new android.content.Intent(android.content.Intent.ACTION_VIEW, null, requireContext(), RuncmdActivity.class))
					.setIcon(android.graphics.drawable.Icon.createWithResource(requireContext(), R.drawable.icon_terminal_round))
					.build();
					//Edit by Android Sketchware Master ✅
					shortcutManager.setDynamicShortcuts(java.util.Arrays.asList(a1));
				}
			}
			else {
				Toast.makeText(requireContext(), "opps your device is Pinned shortcuts are not supported!", Toast.LENGTH_SHORT).show();
			}
			
			String mode = listmanager.getString("aktifitas", "Adb");
			
			if (mode.equalsIgnoreCase("Shizuku")) {
				
				i.setClass(requireContext(), RuncmdActivity.class);
				startActivity(i);
				
			} else if (mode.equalsIgnoreCase("Adb") || mode.contains("Wireless")) {
				
				i.setClass(requireContext(), AdbcommandActivity.class);
				startActivity(i);
				
			} else {
				
			}
			
		});
		
		linear25.setOnClickListener(_v -> {
			_click(linear25);
			android.content.pm.ShortcutManager shortcutManager = null;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
				shortcutManager = requireContext().getSystemService(android.content.pm.ShortcutManager.class);
			}
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				if (shortcutManager != null) {
					android.content.pm.ShortcutInfo a2 = new android.content.pm.ShortcutInfo.Builder(requireContext(), "PLUGINS INSTALLER")
					.setShortLabel("PLUGINS INSTALLER")
					.setLongLabel("PLUGINS INSTALLER")
					.setRank(1)
					.setIntent(new android.content.Intent(android.content.Intent.ACTION_VIEW, null, requireContext(), PluginsActivity.class))
					.setIcon(android.graphics.drawable.Icon.createWithResource(requireContext(), R.drawable.icon_extension_round))
					.build();
					//Edit by Android Sketchware Master ✅
					shortcutManager.setDynamicShortcuts(java.util.Arrays.asList(a2));
				}
			}
			else {
				Toast.makeText(requireContext(), "opps your device is Pinned shortcuts are not supported!", Toast.LENGTH_SHORT).show();
			}
			
			i.setClass(requireContext(), PluginsActivity.class);
			startActivity(i);
		});
	}
	
	private void initializeLogic() {
		_shizuku_access();
		_main();
		_font();
		_sizetetap();
		vscroll1.setVerticalScrollBarEnabled(false);
		String services = listmanager.getString("aktifitas", "Adb");
		
		// 1. LOGIKA UNTUK SHIZUKU / ROOT
		if (services.equalsIgnoreCase("Shizuku")) {
			if (isRootDevice()) {
				// Cek Root di Background Thread agar tidak Lag
				new Thread(() -> {
					boolean granted = false;
					try {
						java.lang.Process p = Runtime.getRuntime().exec(new String[]{"su", "-c", "id"});
						BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
						String line = br.readLine();
						if (line != null && line.contains("uid=0")) granted = true;
						p.waitFor();
					} catch (Exception e) { e.printStackTrace(); }
					
					final boolean isRootOk = granted;
					getActivity().runOnUiThread(() -> {
						if (isRootOk) {
							updateStatusUI("RUNNING", 0xFF65EF6C, 0xFF112F10);
							setupBusybox();
						} else {
							updateStatusUI("NOT RUNNING", 0xFFEF6965, 0xFF2F1014);
						}
					});
				}).start();
			} else {
				// Cek Shizuku
				int status;
				if (!rikka.shizuku.Shizuku.pingBinder()) {
					status = 0; // Not Running
				} else if (rikka.shizuku.Shizuku.checkSelfPermission() == android.content.pm.PackageManager.PERMISSION_GRANTED) {
					status = 1; // Running
				} else {
					status = 2; // No Permission
				}
				
				if (status == 1) {
					updateStatusUI("RUNNING", 0xFF65EF6C, 0xFF112F10);
					setupBusybox();
				} else {
					updateStatusUI("NOT RUNNING", 0xFFEF6965, 0xFF2F1014);
				}
			}
		} 
		// 2. LOGIKA UNTUK ADB (HYPERS ENGINE)
		else if (services.equalsIgnoreCase("Adb") || services.contains("Wireless")) {
			// Pastikan pakai getContext() atau getActivity() jika di Fragment
			com.hypers.hm.debug.ADB adb = com.hypers.hm.debug.ADB.getInstance(getContext());
			
			adb.started.observe(getViewLifecycleOwner(), isRunning -> {
				if (isRunning != null && isRunning) {
					updateStatusUI("HYPERS RUNNING", 0xFF65EF6C, 0xFF112F10);
				} else {
					updateStatusUI("HYPERS NOT RUNNING", 0xFFEF6965, 0xFF2F1014);
				}
			});
		} 
		// 3. DEFAULT (OFF)
		else {
			updateStatusUI("NOT RUNNING", 0xFFEF6965, 0xFF2F1014);
		}
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		String services = listmanager.getString("aktifitas", "Adb");
		
		// 1. LOGIKA UNTUK SHIZUKU / ROOT
		if (services.equalsIgnoreCase("Shizuku")) {
			if (isRootDevice()) {
				// Cek Root di Background Thread agar tidak Lag
				new Thread(() -> {
					boolean granted = false;
					try {
						java.lang.Process p = Runtime.getRuntime().exec(new String[]{"su", "-c", "id"});
						BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
						String line = br.readLine();
						if (line != null && line.contains("uid=0")) granted = true;
						p.waitFor();
					} catch (Exception e) { e.printStackTrace(); }
					
					final boolean isRootOk = granted;
					getActivity().runOnUiThread(() -> {
						if (isRootOk) {
							updateStatusUI("RUNNING", 0xFF65EF6C, 0xFF112F10);
							setupBusybox();
						} else {
							updateStatusUI("NOT RUNNING", 0xFFEF6965, 0xFF2F1014);
						}
					});
				}).start();
			} else {
				// Cek Shizuku
				int status;
				if (!rikka.shizuku.Shizuku.pingBinder()) {
					status = 0; // Not Running
				} else if (rikka.shizuku.Shizuku.checkSelfPermission() == android.content.pm.PackageManager.PERMISSION_GRANTED) {
					status = 1; // Running
				} else {
					status = 2; // No Permission
				}
				
				if (status == 1) {
					updateStatusUI("RUNNING", 0xFF65EF6C, 0xFF112F10);
					setupBusybox();
				} else {
					updateStatusUI("NOT RUNNING", 0xFFEF6965, 0xFF2F1014);
				}
			}
		} 
		// 2. LOGIKA UNTUK ADB (HYPERS ENGINE)
		else if (services.equalsIgnoreCase("Adb") || services.contains("Wireless")) {
			// Pastikan pakai getContext() atau getActivity() jika di Fragment
			com.hypers.hm.debug.ADB adb = com.hypers.hm.debug.ADB.getInstance(getContext());
			
			adb.started.observe(getViewLifecycleOwner(), isRunning -> {
				if (isRunning != null && isRunning) {
					updateStatusUI("HYPERS RUNNING", 0xFF65EF6C, 0xFF112F10);
				} else {
					updateStatusUI("HYPERS NOT RUNNING", 0xFFEF6965, 0xFF2F1014);
				}
			});
		} 
		// 3. DEFAULT (OFF)
		else {
			updateStatusUI("NOT RUNNING", 0xFFEF6965, 0xFF2F1014);
		}
	}
	public void _main() {
		_colorTransform(imageview1, "#2D2F2F");
		String version = android.os.Build.VERSION.RELEASE; 
		int sdk = android.os.Build.VERSION.SDK_INT;       
		
		String result = version + " (SDK " + sdk + ")";
		
		textview7.setText(result);
		String model = android.os.Build.MODEL;
		textview9.setText(model);
		String hw = android.os.Build.HARDWARE.toLowerCase();
		
		if (hw.contains("qcom") || hw.contains("sm")) {
			textview94.setText("Snapdragon");
			
		} else if (hw.contains("mt")) {
			textview94.setText("MediaTek");
			
		} else if (hw.contains("exynos")) {
			textview94.setText("Exynos");
			
		} else {
			textview94.setText(hw);
		}
		linear14.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0x8026A69A));
		linear27.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0x80212E30));
		linear28.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0x801D2928));
		linear33.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0x80202426));
		linear95.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0x80202426));
		linear33.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0x80202426));
		linear143.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0x80202426));
		linear6.setBackground(new android.graphics.drawable.GradientDrawable(
		android.graphics.drawable.GradientDrawable.Orientation.TL_BR,
		new int[]{0xFF1A1A1A, 0xFF131313}
		) {{
				setCornerRadius(28);
			}});
		linear15.setBackground(new android.graphics.drawable.GradientDrawable(
		android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM,
		new int[]{0xFF1A1A1A, 0xFF131313}
		) {{
				setCornerRadius(28);
			}});
		textview5.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0xFF2F1014));
		linear18.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0xFFFF716C));
		linear19.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear20.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear24.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear25.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear31.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear94.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear142.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear146.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
	}
	
	
	public void _font() {
		textview1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
		textview2.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview3.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview4.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview5.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
		textview6.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
		textview7.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview8.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
		textview9.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview11.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview10.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
		textview13.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview12.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
		textview14.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview15.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
		textview58.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview59.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
		textview91.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview92.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
	}
	
	
	public void _shizuku_access() {
		Shizuku.addRequestPermissionResultListener(REQUEST_PERMISSION_RESULT_LISTENER);
		
		try {
			PackageManager packageManager = requireContext().getPackageManager();
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
	
	
	public void _aksesPintarCmd() {
	}
	private boolean isRootDevice() {
		String[] paths = {
			"/system/bin/su", 
			"/system/xbin/su", 
			"/sbin/su", 
			"/system/sd/xbin/su", 
			"/data/local/xbin/su", 
			"/data/local/bin/su", 
			"/data/local/su"
		};
		for (String path : paths) {
			if (new java.io.File(path).exists()) return true;
		}
		return false;
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
		
		com.hypers.hm.debug.ADB adb = com.hypers.hm.debug.ADB.getInstance(requireContext());
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
	private Process process;
	{
	}
	
	
	public void _comment(final String _message) {
		
	}
	
	
	public void _colorTransform(final ImageView _img, final String _hex) {
		_img.clearColorFilter(); 
		_img.setColorFilter(Color.parseColor(_hex.toString()));
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
	
	
	public void _sizetetap() {
	}
	private static final int VIRTUAL_WIDTH = 625;
	private static final int VIRTUAL_HEIGHT = 1045;
	
	private Configuration baseConfig;
	
	@Override
	public void onAttach(Context context) {
		super.onAttach(wrapContext(context));
		
		Context wrapped = wrapContext(context);
		baseConfig = new Configuration(wrapped.getResources().getConfiguration());
	}
	
	private static Context wrapContext(Context base) {
		DisplayMetrics dm = base.getResources().getDisplayMetrics();
		
		float density = (float) dm.widthPixels / VIRTUAL_WIDTH;
		int densityDpi = (int) (density * 120);
		
		Configuration config = new Configuration(base.getResources().getConfiguration());
		config.densityDpi = densityDpi;
		config.screenWidthDp = (int) (VIRTUAL_WIDTH / density);
		config.screenHeightDp = (int) (VIRTUAL_HEIGHT / density);
		config.smallestScreenWidthDp = (int) (VIRTUAL_WIDTH / density);
		
		Context context = base.createConfigurationContext(config);
		
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		metrics.density = densityDpi / 160f;
		metrics.densityDpi = densityDpi;
		metrics.scaledDensity = metrics.density;
		
		return context;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
		try {
			if (baseConfig != null) {
				applyOverrideConfiguration(new Configuration(baseConfig));
			}
		} catch (Exception ignored) {}
	}
	
	public void applyOverrideConfiguration(Configuration overrideConfiguration) {
		if (overrideConfiguration == null) return;
		
		try {
			if (baseConfig != null) {
				overrideConfiguration.updateFrom(baseConfig);
			}
		} catch (Exception ignored) {
			
		}
	}
	{
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
			
			File bb = new File(getActivity().getFilesDir(), "bb");
			
			if (!bb.exists()) {
				InputStream is = getContext().getAssets().open(assetName);
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
	
	
	public void _updateStatusRunning() {
	}
	private void updateStatusUI(String text, int textColor, int bgColor) {
		textview5.setText(text);
		textview5.setTextColor(textColor);
		GradientDrawable gd = new GradientDrawable();
		gd.setCornerRadius(50);
		gd.setColor(bgColor);
		textview5.setBackground(gd);
	}
	{
	}
	
}
