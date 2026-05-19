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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.*;
import com.facebook.shimmer.*;
import com.google.android.material.slider.Slider;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;
import org.lsposed.hiddenapibypass.library.*;
import rikka.shizuku.api.*;
import rikka.shizuku.provider.*;
import rikka.shizuku.Shizuku;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import android.app.NotificationManager;

import android.Manifest;
import androidx.core.app.ActivityCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import java.lang.Process;

import androidx.activity.OnBackPressedCallback;

import android.Manifest;
import android.os.Build;

import com.hypers.hm.service.DpiServiceUtils;
import com.hypers.hm.service.NetworkBoosterService;



public class SetpackageActivity extends AppCompatActivity {
	
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
	private String name = "";
	private String packag = "";
	private double opsi = 0;
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private ImageView imageview1;
	private TextView textview1;
	private LinearLayout linear38;
	private Switch switch3;
	private ImageView imageview3;
	private LinearLayout linear4;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private CardView cardview1;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private ImageView imageview2;
	private TextView textview2;
	private TextView textview3;
	private TextView textview31;
	private ScrollView vscroll1;
	private LinearLayout linear9;
	private LinearLayout linear28;
	private LinearLayout linear10;
	private LinearLayout linear12;
	private LinearLayout linear14;
	private LinearLayout linear16;
	private LinearLayout linear26;
	private LinearLayout linear30;
	private LinearLayout linear34;
	private LinearLayout linear36;
	private LinearLayout linear129;
	private LinearLayout linear29;
	private Switch switch5;
	private TextView textview20;
	private TextView textview21;
	private LinearLayout linear11;
	private Switch switch1;
	private TextView textview4;
	private TextView textview5;
	private LinearLayout linear13;
	private Switch switch2;
	private TextView textview6;
	private TextView textview7;
	private LinearLayout linear39;
	private LinearLayout linear42;
	private LinearLayout linear15;
	private LinearLayout linear40;
	private TextView textview8;
	private TextView textview9;
	private LinearLayout linear41;
	private TextView textview32;
	private LinearLayout linear32;
	private LinearLayout linear18;
	private Slider seekbar5;
	private LinearLayout linear19;
	private LinearLayout linear20;
	private LinearLayout linear21;
	private TextView textview12;
	private TextView textview28;
	private TextView textview13;
	private TextView textview24;
	private TextView textview29;
	private TextView textview14;
	private LinearLayout linear17;
	private LinearLayout linear22;
	private TextView textview10;
	private TextView textview11;
	private LinearLayout linear23;
	private LinearLayout linear24;
	private LinearLayout linear25;
	private TextView textview15;
	private TextView textview16;
	private TextView textview17;
	private LinearLayout linear27;
	private Switch switch4;
	private TextView textview18;
	private TextView textview19;
	private LinearLayout linear31;
	private Switch switch6;
	private TextView textview22;
	private TextView textview23;
	private LinearLayout linear35;
	private Switch switch7;
	private TextView textview25;
	private TextView textview26;
	private LinearLayout linear37;
	private Switch switch8;
	private TextView textview27;
	private TextView textview30;
	private LinearLayout linear130;
	private LinearLayout opsippi;
	private LinearLayout linear132;
	private TextView textview73;
	private TextView textview74;
	private LinearLayout linear135;
	private LinearLayout linear133;
	private LinearLayout linear136;
	private LinearLayout linear138;
	private TextView textview79;
	private LinearLayout linear144;
	private ImageView imageview4;
	private EditText edittext1;
	private TextView textview81;
	private TextView textview80;
	private LinearLayout linear145;
	private ImageView imageview5;
	private EditText edittext2;
	private TextView textview82;
	private TextView textview75;
	
	private SharedPreferences user;
	private SharedPreferences ppkg;
	private TimerTask y;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.setpackage);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
		linear38 = findViewById(R.id.linear38);
		switch3 = findViewById(R.id.switch3);
		imageview3 = findViewById(R.id.imageview3);
		linear4 = findViewById(R.id.linear4);
		linear7 = findViewById(R.id.linear7);
		linear8 = findViewById(R.id.linear8);
		cardview1 = findViewById(R.id.cardview1);
		linear5 = findViewById(R.id.linear5);
		linear6 = findViewById(R.id.linear6);
		imageview2 = findViewById(R.id.imageview2);
		textview2 = findViewById(R.id.textview2);
		textview3 = findViewById(R.id.textview3);
		textview31 = findViewById(R.id.textview31);
		vscroll1 = findViewById(R.id.vscroll1);
		linear9 = findViewById(R.id.linear9);
		linear28 = findViewById(R.id.linear28);
		linear10 = findViewById(R.id.linear10);
		linear12 = findViewById(R.id.linear12);
		linear14 = findViewById(R.id.linear14);
		linear16 = findViewById(R.id.linear16);
		linear26 = findViewById(R.id.linear26);
		linear30 = findViewById(R.id.linear30);
		linear34 = findViewById(R.id.linear34);
		linear36 = findViewById(R.id.linear36);
		linear129 = findViewById(R.id.linear129);
		linear29 = findViewById(R.id.linear29);
		switch5 = findViewById(R.id.switch5);
		textview20 = findViewById(R.id.textview20);
		textview21 = findViewById(R.id.textview21);
		linear11 = findViewById(R.id.linear11);
		switch1 = findViewById(R.id.switch1);
		textview4 = findViewById(R.id.textview4);
		textview5 = findViewById(R.id.textview5);
		linear13 = findViewById(R.id.linear13);
		switch2 = findViewById(R.id.switch2);
		textview6 = findViewById(R.id.textview6);
		textview7 = findViewById(R.id.textview7);
		linear39 = findViewById(R.id.linear39);
		linear42 = findViewById(R.id.linear42);
		linear15 = findViewById(R.id.linear15);
		linear40 = findViewById(R.id.linear40);
		textview8 = findViewById(R.id.textview8);
		textview9 = findViewById(R.id.textview9);
		linear41 = findViewById(R.id.linear41);
		textview32 = findViewById(R.id.textview32);
		linear32 = findViewById(R.id.linear32);
		linear18 = findViewById(R.id.linear18);
		seekbar5 = findViewById(R.id.seekbar5);
		linear19 = findViewById(R.id.linear19);
		linear20 = findViewById(R.id.linear20);
		linear21 = findViewById(R.id.linear21);
		textview12 = findViewById(R.id.textview12);
		textview28 = findViewById(R.id.textview28);
		textview13 = findViewById(R.id.textview13);
		textview24 = findViewById(R.id.textview24);
		textview29 = findViewById(R.id.textview29);
		textview14 = findViewById(R.id.textview14);
		linear17 = findViewById(R.id.linear17);
		linear22 = findViewById(R.id.linear22);
		textview10 = findViewById(R.id.textview10);
		textview11 = findViewById(R.id.textview11);
		linear23 = findViewById(R.id.linear23);
		linear24 = findViewById(R.id.linear24);
		linear25 = findViewById(R.id.linear25);
		textview15 = findViewById(R.id.textview15);
		textview16 = findViewById(R.id.textview16);
		textview17 = findViewById(R.id.textview17);
		linear27 = findViewById(R.id.linear27);
		switch4 = findViewById(R.id.switch4);
		textview18 = findViewById(R.id.textview18);
		textview19 = findViewById(R.id.textview19);
		linear31 = findViewById(R.id.linear31);
		switch6 = findViewById(R.id.switch6);
		textview22 = findViewById(R.id.textview22);
		textview23 = findViewById(R.id.textview23);
		linear35 = findViewById(R.id.linear35);
		switch7 = findViewById(R.id.switch7);
		textview25 = findViewById(R.id.textview25);
		textview26 = findViewById(R.id.textview26);
		linear37 = findViewById(R.id.linear37);
		switch8 = findViewById(R.id.switch8);
		textview27 = findViewById(R.id.textview27);
		textview30 = findViewById(R.id.textview30);
		linear130 = findViewById(R.id.linear130);
		opsippi = findViewById(R.id.opsippi);
		linear132 = findViewById(R.id.linear132);
		textview73 = findViewById(R.id.textview73);
		textview74 = findViewById(R.id.textview74);
		linear135 = findViewById(R.id.linear135);
		linear133 = findViewById(R.id.linear133);
		linear136 = findViewById(R.id.linear136);
		linear138 = findViewById(R.id.linear138);
		textview79 = findViewById(R.id.textview79);
		linear144 = findViewById(R.id.linear144);
		imageview4 = findViewById(R.id.imageview4);
		edittext1 = findViewById(R.id.edittext1);
		textview81 = findViewById(R.id.textview81);
		textview80 = findViewById(R.id.textview80);
		linear145 = findViewById(R.id.linear145);
		imageview5 = findViewById(R.id.imageview5);
		edittext2 = findViewById(R.id.edittext2);
		textview82 = findViewById(R.id.textview82);
		textview75 = findViewById(R.id.textview75);
		user = getSharedPreferences("user", Activity.MODE_PRIVATE);
		ppkg = getSharedPreferences("ppkg", Activity.MODE_PRIVATE);
		
		imageview1.setOnClickListener(_v -> finish());
		
		switch3.setOnCheckedChangeListener((_buttonView, _isChecked) -> {
			if (_isChecked) {
				try {
					
					File file = new File(Environment.getExternalStorageDirectory(), "ZYREX-TOOLS/data/.cache/" + packag + ".json");
					
					FileInputStream fis = new FileInputStream(file);
					byte[] data = new byte[(int) file.length()];
					fis.read(data);
					fis.close();
					
					
					JSONObject obj = new JSONObject(new String(data));
					
					
				} catch (Exception e) {
					try {
						JSONObject json = new JSONObject();
						json.put("Perform", "disable");
						json.put("block", "off");
						json.put("network", "off");
						json.put("sensi", "default");
						json.put("fps", "60");
						json.put("aimtrick", "off");
						json.put("elixirSOC", "off");
						json.put("surface", "off");
						json.put("aiming", "off");
						
						File tempJsonFile = new File(SetpackageActivity.this.getFilesDir(), packag + ".json");
						FileOutputStream fos = new FileOutputStream(tempJsonFile);
						fos.write(json.toString(9).getBytes());
						fos.close();
						
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
							if (!Environment.isExternalStorageManager()) {
								Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
								startActivity(intent);
								return;
							}
						} else {
							
							ActivityCompat.requestPermissions(SetpackageActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
						}
						
						File targetDir = new File(Environment.getExternalStorageDirectory(), "ZYREX-TOOLS/data/.cache/");
						if (!targetDir.exists()) {
							targetDir.mkdirs();
						}
						
						File targetFile = new File(targetDir, packag + ".json");
						
						FileInputStream in = new FileInputStream(tempJsonFile);
						OutputStream out = new FileOutputStream(targetFile);
						
						byte[] buffer = new byte[1024];
						int length;
						while ((length = in.read(buffer)) > 0) {
							out.write(buffer, 0, length);
						}
						
						in.close();
						out.close();
						
						Log.d("PluginJSON", "File copied to: " + targetFile.getAbsolutePath());
						
					} catch (Exception ex) {
						
					}
					
				}
				linear9.setAlpha(1.0f);
				switch5.setEnabled(true);
				switch1.setEnabled(true);
				switch2.setEnabled(true);
				switch4.setEnabled(true);
				switch6.setEnabled(true);
				switch7.setEnabled(true);
				switch8.setEnabled(true);
				linear23.setEnabled(true);
				linear24.setEnabled(true);
				linear25.setEnabled(true);
				seekbar5.setEnabled(true);
			} else {
				File json = new File(Environment.getExternalStorageDirectory(), "ZYREX-TOOLS/data/.cache/" + packag + ".json");
				
				if (json.exists()) {
					if (json.delete()) {
						
					} else {
						
					}
				} else {
					
				}
				
				linear9.setAlpha(0.6f);
				switch5.setEnabled(false);
				switch1.setEnabled(false);
				switch2.setEnabled(false);
				switch4.setEnabled(false);
				switch6.setEnabled(false);
				switch7.setEnabled(false);
				switch8.setEnabled(false);
				linear23.setEnabled(false);
				linear24.setEnabled(false);
				linear25.setEnabled(false);
				seekbar5.setEnabled(false);
			}
		});
		
		imageview3.setOnClickListener(_v -> {
			File json = new File(Environment.getExternalStorageDirectory(), "ZYREX-TOOLS/data/.cache/" + packag + ".json");
			
			if (json.exists()) {
				if (json.delete()) {
					
				} else {
					
				}
			} else {
				
			}
			
			final ExecutorService executor = Executors.newSingleThreadExecutor();
			LoadingBooster.show(SetpackageActivity.this).size(180).color(0xFF00ACC1);
			
			// Definisi variabel agar bisa dibaca di dalam thread
			final int sdkVersion = android.os.Build.VERSION.SDK_INT;
			final String pkgName = packag;
			
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						// --- AIM TRICK RESET SECTION ---
						execSmart("settings put system pointer_speed 0");
						execSmart("settings delete secure long_press_timeout");
						execSmart("settings delete secure multi_press_timeout");
						execSmart("settings delete system scroll_friction");
						execSmart("settings delete global window_animation_scale");
						execSmart("settings delete global transition_animation_scale");
						execSmart("settings delete global animator_duration_scale");
						execSmart("settings delete system min_fling_velocity");
						execSmart("settings delete system max_fling_velocity");
						execSmart("settings delete global touch_slop");
						
						execSmart("setprop debug.input.event_rate ''");
						execSmart("setprop debug.input.max_events_per_sec ''");
						execSmart("setprop debug.input.resample ''");
						execSmart("setprop debug.input.sampling_rate ''");
						
						execSmart("settings delete system peak_refresh_rate");
						execSmart("settings delete system min_refresh_rate");
						execSmart("settings put global window_animation_scale 1");
						execSmart("settings put global transition_animation_scale 1");
						execSmart("settings put global animator_duration_scale 1");
						execSmart("settings delete global background_process_limit");
						
						execSmart("setprop debug.hwui.renderer ''");
						execSmart("setprop debug.hwui.use_vulkan ''");
						execSmart("setprop debug.hwui.overdraw ''");
						execSmart("setprop debug.hwui.use_partial_updates ''");
						execSmart("setprop debug.hwui.max_frame_buffer_acquired_buffers ''");
						execSmart("setprop debug.hwui.drop_shadow_cache_size ''");
						execSmart("setprop debug.hwui.texture_cache_size ''");
						execSmart("setprop debug.hwui.layer_cache_size ''");
						execSmart("setprop debug.hwui.path_cache_size ''");
						
						execSmart("setprop debug.sf.disable_backpressure ''");
						execSmart("setprop debug.sf.latch_unsignaled ''");
						execSmart("setprop debug.sf.enable_gl_backpressure ''");
						execSmart("setprop debug.sf.early.app.duration ''");
						execSmart("setprop debug.sf.early.sf.duration ''");
						execSmart("setprop debug.sf.early_phase_offset_ns ''");
						execSmart("setprop debug.sf.late.app.duration ''");
						execSmart("setprop debug.sf.late.sf.duration ''");
						
						execSmart("setprop debug.thermal.throttling ''");
						execSmart("cmd power set-fixed-performance-mode-enabled false");
						
						execSmart("content insert --uri content://settings/system --bind name:s:touch_blocking_period --bind value:s:0");
						execSmart("content insert --uri content://settings/system --bind name:s:tap_duration_threshold --bind value:s:0");
						execSmart("content insert --uri content://settings/system --bind name:s:touch_slop --bind value:s:8");
						execSmart("content insert --uri content://settings/system --bind name:s:pointer_gesture_zoom_combined_slop --bind value:s:20");
						
						if (sdkVersion >= 31) {
							execSmart("cmd game reset " + pkgName);
						} else {
							execSmart("wm size reset");
							execSmart("wm density reset");
						}
						
						new Handler(Looper.getMainLooper()).post(new Runnable() {
							@Override
							public void run() {
								SketchwareUtil.showMessage(SetpackageActivity.this, "AimTrick : OFF");
							}
						});
						
						// --- CHIPSET OPTIMIZATION RESET SECTION ---
						runCmdList(hypersExecute.getJitterDefault());
						
						execSmart("setprop debug.sf.latch_unsignaled ''");
						execSmart("setprop debug.hwui.renderer ''");
						execSmart("settings delete global activity_manager_constants");
						execSmart("cmd power set-fixed-performance-mode-enabled false");
						execSmart("cmd looper_stats enable");
						
						new Handler(Looper.getMainLooper()).post(new Runnable() {
							@Override
							public void run() {
								SketchwareUtil.showMessage(SetpackageActivity.this, "Chipset Optimization : OFF");
							}
						});
						
						// --- NETWORK OPTIMIZE RESET SECTION ---
						execSmart("dumpsys deviceidle enable");
						execSmart("settings delete global tcp_default_init_rwnd");
						execSmart("settings put global mobile_data_always_on 0");
						execSmart("settings put global wifi_sleep_policy 0");
						execSmart("cmd netpolicy set restrict-background true");
						execSmart("ndc resolver flushdefaultif");
						
						new Handler(Looper.getMainLooper()).post(new Runnable() {
							@Override
							public void run() {
								SketchwareUtil.showMessage(SetpackageActivity.this, "Network Optimize : OFF");
							}
						});
						
						// --- REFRESH RATE & SHIZUKU EXTRA SECTION ---
						hypersExecute.getDefault(pkgName); 
						
						execSmart("settings put global peak_refresh_rate 60.0");
						execSmart("settings put global min_refresh_rate 60.0");
						execSmart("settings put system peak_refresh_rate 60.0");
						execSmart("settings put system min_refresh_rate 60.0");
						execSmart("settings put global speed_mode 1");
						execSmart("sh -c 'service call SurfaceFlinger 1035 i32 60'");
						execSmart("sh -c 'service call SurfaceFlinger 1011 i32 60'");
						
						new Handler(Looper.getMainLooper()).post(() -> {
							LoadingBooster.hide();
						});
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
		});
		
		linear129.setOnClickListener(_v -> {
			if (opsi == 0) {
				opsi++;
				_click(linear129);
				opsippi.setVisibility(View.VISIBLE);
				opsippi.measure(
				View.MeasureSpec.makeMeasureSpec(((View) opsippi.getParent()).getWidth(), View.MeasureSpec.EXACTLY),
				View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
				);
				
				int targetHeight = opsippi.getMeasuredHeight();
				
				// mulai dari 0
				ViewGroup.LayoutParams lp = opsippi.getLayoutParams();
				lp.height = 0;
				opsippi.setLayoutParams(lp);
				
				ValueAnimator anim = ValueAnimator.ofInt(0, targetHeight);
				anim.addUpdateListener(valueAnimator -> {
					int val = (int) valueAnimator.getAnimatedValue();
					ViewGroup.LayoutParams params = opsippi.getLayoutParams();
					params.height = val;
					opsippi.setLayoutParams(params);
				});
				
				anim.setDuration(300);
				anim.setInterpolator(new DecelerateInterpolator());
				anim.start();
				
				// fade in
				opsippi.setAlpha(0f);
				opsippi.animate().alpha(1f).setDuration(300).start();
				vscroll1.fullScroll(View.FOCUS_DOWN);
			} else {
				opsi--;
				_click(linear129);
				int initialHeight = opsippi.getMeasuredHeight();
				
				ValueAnimator anim = ValueAnimator.ofInt(initialHeight, 0);
				anim.addUpdateListener(valueAnimator -> {
					int val = (int) valueAnimator.getAnimatedValue();
					ViewGroup.LayoutParams params = opsippi.getLayoutParams();
					params.height = val;
					opsippi.setLayoutParams(params);
				});
				
				anim.setDuration(300);
				anim.setInterpolator(new DecelerateInterpolator());
				anim.start();
				
				anim.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						opsippi.setVisibility(View.GONE);
					}
				});
			}
		});
		
		switch5.setOnCheckedChangeListener((_buttonView, _isChecked) -> {
			if (_isChecked) {
				LoadingBooster.show(SetpackageActivity.this).size(180).color(0xFF00ACC1);
				
				
				ppkg.edit().putString("perform", "enable").commit();
				y = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(() -> {
							try {
								File targetFile = new File(Environment.getExternalStorageDirectory(),
								"ZYREX-TOOLS/data/.cache/" + packag + ".json");
								
								FileInputStream fis = new FileInputStream(targetFile);
								BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
								StringBuilder builder = new StringBuilder();
								
								String line;
								while ((line = reader.readLine()) != null) {
									builder.append(line);
								}
								
								reader.close();
								fis.close();
								
								JSONObject json = new JSONObject(builder.toString());
								json.put("Perform", "enable");
								
								
								FileOutputStream fos = new FileOutputStream(targetFile);
								fos.write(json.toString(1).getBytes());
								fos.close();
								
								Log.d("PluginJSON", "JSON updated!");
								
							} catch (Exception e) {
								Log.e("PluginJSON", "Error: " + e.getMessage());
							}
							
							LoadingBooster.hide();
						});
					}
				};
				_timer.schedule(y, 1000);
			} else {
				LoadingBooster.show(SetpackageActivity.this).size(180).color(0xFF00ACC1);
				ppkg.edit().remove("perform").commit();
				y = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(() -> {
							try {
								File targetFile = new File(Environment.getExternalStorageDirectory(),
								"ZYREX-TOOLS/data/.cache/" + packag + ".json");
								
								FileInputStream fis = new FileInputStream(targetFile);
								BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
								StringBuilder builder = new StringBuilder();
								
								String line;
								while ((line = reader.readLine()) != null) {
									builder.append(line);
								}
								
								reader.close();
								fis.close();
								
								JSONObject json = new JSONObject(builder.toString());
								json.put("Perform", "disable");
								
								
								FileOutputStream fos = new FileOutputStream(targetFile);
								fos.write(json.toString(1).getBytes());
								fos.close();
								
								Log.d("PluginJSON", "JSON updated!");
								
							} catch (Exception e) {
								Log.e("PluginJSON", "Error: " + e.getMessage());
							}
							
							LoadingBooster.hide();
						});
					}
				};
				_timer.schedule(y, 1000);
			}
		});
		
		switch1.setOnCheckedChangeListener((_buttonView, _isChecked) -> {
			if (_isChecked) {
				LoadingBooster.show(SetpackageActivity.this).size(180).color(0xFF00ACC1);
				
				y = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(() -> {
							try {
								File targetFile = new File(Environment.getExternalStorageDirectory(),
								"ZYREX-TOOLS/data/.cache/" + packag + ".json");
								
								FileInputStream fis = new FileInputStream(targetFile);
								BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
								StringBuilder builder = new StringBuilder();
								
								String line;
								while ((line = reader.readLine()) != null) {
									builder.append(line);
								}
								
								reader.close();
								fis.close();
								
								JSONObject json = new JSONObject(builder.toString());
								json.put("block", "on");
								
								
								FileOutputStream fos = new FileOutputStream(targetFile);
								fos.write(json.toString(1).getBytes());
								fos.close();
								
								Log.d("PluginJSON", "JSON updated!");
								
							} catch (Exception e) {
								Log.e("PluginJSON", "Error: " + e.getMessage());
							}
							ppkg.edit().putString("block", "on").commit();
							
							LoadingBooster.hide();
						});
					}
				};
				_timer.schedule(y, 1500);
			} else {
				LoadingBooster.show(SetpackageActivity.this).size(180).color(0xFF00ACC1);
				y = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(() -> {
							
							
							LoadingBooster.hide();
							try {
								File targetFile = new File(Environment.getExternalStorageDirectory(),
								"ZYREX-TOOLS/data/.cache/" + packag + ".json");
								
								FileInputStream fis = new FileInputStream(targetFile);
								BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
								StringBuilder builder = new StringBuilder();
								
								String line;
								while ((line = reader.readLine()) != null) {
									builder.append(line);
								}
								
								reader.close();
								fis.close();
								
								JSONObject json = new JSONObject(builder.toString());
								json.put("block", "off");
								
								
								FileOutputStream fos = new FileOutputStream(targetFile);
								fos.write(json.toString(1).getBytes());
								fos.close();
								
								Log.d("PluginJSON", "JSON updated!");
								
							} catch (Exception e) {
								Log.e("PluginJSON", "Error: " + e.getMessage());
							}
							ppkg.edit().remove("block").commit();
						});
					}
				};
				_timer.schedule(y, 1500);
			}
		});
		
		switch2.setOnCheckedChangeListener((_buttonView, _isChecked) -> {
			if (_isChecked) {
				LoadingBooster.show(SetpackageActivity.this).size(180).color(0xFF00ACC1);
				y = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(() -> {
							ppkg.edit().putString("network", "on").commit();
							try {
								File targetFile = new File(Environment.getExternalStorageDirectory(),
								"ZYREX-TOOLS/data/.cache/" + packag + ".json");
								
								FileInputStream fis = new FileInputStream(targetFile);
								BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
								StringBuilder builder = new StringBuilder();
								
								String line;
								while ((line = reader.readLine()) != null) {
									builder.append(line);
								}
								
								reader.close();
								fis.close();
								
								JSONObject json = new JSONObject(builder.toString());
								json.put("network", "on");
								
								
								FileOutputStream fos = new FileOutputStream(targetFile);
								fos.write(json.toString(1).getBytes());
								fos.close();
								
								Log.d("PluginJSON", "JSON updated!");
								
							} catch (Exception e) {
								Log.e("PluginJSON", "Error: " + e.getMessage());
							}
							
							LoadingBooster.hide();
							LoadingBooster.hide();
						});
					}
				};
				_timer.schedule(y, 1000);
			} else {
				LoadingBooster.show(SetpackageActivity.this).size(180).color(0xFF00ACC1);
				y = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(() -> {
							ppkg.edit().remove("network").commit();
							try {
								File targetFile = new File(Environment.getExternalStorageDirectory(),
								"ZYREX-TOOLS/data/.cache/" + packag + ".json");
								
								FileInputStream fis = new FileInputStream(targetFile);
								BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
								StringBuilder builder = new StringBuilder();
								
								String line;
								while ((line = reader.readLine()) != null) {
									builder.append(line);
								}
								
								reader.close();
								fis.close();
								
								JSONObject json = new JSONObject(builder.toString());
								json.put("network", "off");
								
								
								FileOutputStream fos = new FileOutputStream(targetFile);
								fos.write(json.toString(1).getBytes());
								fos.close();
								
								Log.d("PluginJSON", "JSON updated!");
								
							} catch (Exception e) {
								Log.e("PluginJSON", "Error: " + e.getMessage());
							}
							
							LoadingBooster.hide();
							LoadingBooster.hide();
						});
					}
				};
				_timer.schedule(y, 1000);
			}
		});
		
		switch4.setOnCheckedChangeListener((_buttonView, _isChecked) -> {
			if (_isChecked) {
				ppkg.edit().putString("aimtri", "on").commit();
				try {
					File targetFile = new File(Environment.getExternalStorageDirectory(),
					"ZYREX-TOOLS/data/.cache/" + packag + ".json");
					
					FileInputStream fis = new FileInputStream(targetFile);
					BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
					StringBuilder builder = new StringBuilder();
					
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
					
					reader.close();
					fis.close();
					
					JSONObject json = new JSONObject(builder.toString());
					json.put("aimtrick", "on");
					
					
					FileOutputStream fos = new FileOutputStream(targetFile);
					fos.write(json.toString(1).getBytes());
					fos.close();
					
					Log.d("PluginJSON", "JSON updated!");
					
				} catch (Exception e) {
					Log.e("PluginJSON", "Error: " + e.getMessage());
				}
				
				LoadingBooster.hide();
			} else {
				ppkg.edit().remove("aimtri").commit();
				try {
					File targetFile = new File(Environment.getExternalStorageDirectory(),
					"ZYREX-TOOLS/data/.cache/" + packag + ".json");
					
					FileInputStream fis = new FileInputStream(targetFile);
					BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
					StringBuilder builder = new StringBuilder();
					
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
					
					reader.close();
					fis.close();
					
					JSONObject json = new JSONObject(builder.toString());
					json.put("aimtrick", "off");
					
					
					FileOutputStream fos = new FileOutputStream(targetFile);
					fos.write(json.toString(1).getBytes());
					fos.close();
					
					Log.d("PluginJSON", "JSON updated!");
					
				} catch (Exception e) {
					Log.e("PluginJSON", "Error: " + e.getMessage());
				}
				
				LoadingBooster.hide();
			}
		});
		
		switch6.setOnCheckedChangeListener((_buttonView, _isChecked) -> {
			if (_isChecked) {
				ppkg.edit().putString("chipset", "active").commit();
				try {
					File targetFile = new File(Environment.getExternalStorageDirectory(),
					"ZYREX-TOOLS/data/.cache/" + packag + ".json");
					
					FileInputStream fis = new FileInputStream(targetFile);
					BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
					StringBuilder builder = new StringBuilder();
					
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
					
					reader.close();
					fis.close();
					
					JSONObject json = new JSONObject(builder.toString());
					json.put("elixirSOC", "on");
					
					
					FileOutputStream fos = new FileOutputStream(targetFile);
					fos.write(json.toString(1).getBytes());
					fos.close();
					
					Log.d("PluginJSON", "JSON updated!");
					
				} catch (Exception e) {
					Log.e("PluginJSON", "Error: " + e.getMessage());
				}
				
				LoadingBooster.hide();
			} else {
				ppkg.edit().remove("chipset").commit();
				try {
					File targetFile = new File(Environment.getExternalStorageDirectory(),
					"ZYREX-TOOLS/data/.cache/" + packag + ".json");
					
					FileInputStream fis = new FileInputStream(targetFile);
					BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
					StringBuilder builder = new StringBuilder();
					
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
					
					reader.close();
					fis.close();
					
					JSONObject json = new JSONObject(builder.toString());
					json.put("elixirSOC", "off");
					
					
					FileOutputStream fos = new FileOutputStream(targetFile);
					fos.write(json.toString(1).getBytes());
					fos.close();
					
					Log.d("PluginJSON", "JSON updated!");
					
				} catch (Exception e) {
					Log.e("PluginJSON", "Error: " + e.getMessage());
				}
				
				LoadingBooster.hide();
			}
		});
		
		switch7.setOnCheckedChangeListener((_buttonView, _isChecked) -> {
			if (_isChecked) {
				ppkg.edit().putString("surface", "active").commit();
				try {
					File targetFile = new File(Environment.getExternalStorageDirectory(),
					"ZYREX-TOOLS/data/.cache/" + packag + ".json");
					
					FileInputStream fis = new FileInputStream(targetFile);
					BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
					StringBuilder builder = new StringBuilder();
					
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
					
					reader.close();
					fis.close();
					
					JSONObject json = new JSONObject(builder.toString());
					json.put("surface", "on");
					
					
					FileOutputStream fos = new FileOutputStream(targetFile);
					fos.write(json.toString(1).getBytes());
					fos.close();
					
					Log.d("PluginJSON", "JSON updated!");
					
				} catch (Exception e) {
					Log.e("PluginJSON", "Error: " + e.getMessage());
				}
				
				LoadingBooster.hide();
			} else {
				ppkg.edit().remove("surface").commit();
				try {
					File targetFile = new File(Environment.getExternalStorageDirectory(),
					"ZYREX-TOOLS/data/.cache/" + packag + ".json");
					
					FileInputStream fis = new FileInputStream(targetFile);
					BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
					StringBuilder builder = new StringBuilder();
					
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
					
					reader.close();
					fis.close();
					
					JSONObject json = new JSONObject(builder.toString());
					json.put("surface", "off");
					
					
					FileOutputStream fos = new FileOutputStream(targetFile);
					fos.write(json.toString(1).getBytes());
					fos.close();
					
					Log.d("PluginJSON", "JSON updated!");
					
				} catch (Exception e) {
					Log.e("PluginJSON", "Error: " + e.getMessage());
				}
				
				LoadingBooster.hide();
			}
		});
		
		switch8.setOnCheckedChangeListener((_buttonView, _isChecked) -> {
			if (_isChecked) {
				ppkg.edit().putString("jitter", "enabled").commit();
				try {
					File targetFile = new File(Environment.getExternalStorageDirectory(),
					"ZYREX-TOOLS/data/.cache/" + packag + ".json");
					
					FileInputStream fis = new FileInputStream(targetFile);
					BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
					StringBuilder builder = new StringBuilder();
					
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
					
					reader.close();
					fis.close();
					
					JSONObject json = new JSONObject(builder.toString());
					json.put("aiming", "on");
					
					
					FileOutputStream fos = new FileOutputStream(targetFile);
					fos.write(json.toString(1).getBytes());
					fos.close();
					
					Log.d("PluginJSON", "JSON updated!");
					
				} catch (Exception e) {
					Log.e("PluginJSON", "Error: " + e.getMessage());
				}
				
				LoadingBooster.hide();
			} else {
				ppkg.edit().remove("jitter").commit();
				try {
					File targetFile = new File(Environment.getExternalStorageDirectory(),
					"ZYREX-TOOLS/data/.cache/" + packag + ".json");
					
					FileInputStream fis = new FileInputStream(targetFile);
					BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
					StringBuilder builder = new StringBuilder();
					
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
					
					reader.close();
					fis.close();
					
					JSONObject json = new JSONObject(builder.toString());
					json.put("aiming", "off");
					
					
					FileOutputStream fos = new FileOutputStream(targetFile);
					fos.write(json.toString(1).getBytes());
					fos.close();
					
					Log.d("PluginJSON", "JSON updated!");
					
				} catch (Exception e) {
					Log.e("PluginJSON", "Error: " + e.getMessage());
				}
				
				LoadingBooster.hide();
			}
		});
		
		linear133.setOnClickListener(_v -> {
			_click(linear133);
			final String inputVal = edittext1.getText().toString();
			final String inputClose = edittext2.getText().toString();
			
			if (inputVal.isEmpty() || inputClose.isEmpty()) return;
			
			if (rikka.shizuku.Shizuku.pingBinder()) {
				
				SharedPreferences.Editor edit =
				getSharedPreferences("ServicePrefs", Context.MODE_PRIVATE).edit();
				
				
				edit.putInt("dpi", Integer.parseInt(inputVal));
				
				
				edit.putInt("dpiC", Integer.parseInt(inputClose));
				
				
				edit.putString("pkg", "com.dts.freefireth");
				
				
				edit.putBoolean("isActive", true);
				
				edit.apply();
				
				Intent serviceIntent =
				new Intent(this, DpiServiceUtils.class);
				
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
					
					startForegroundService(serviceIntent);
					
				} else {
					
					startService(serviceIntent);
				}
				
				
				android.app.usage.UsageStatsManager usm =
				(android.app.usage.UsageStatsManager)
				getSystemService(Context.USAGE_STATS_SERVICE);
				
				long time = System.currentTimeMillis();
				
				java.util.List<android.app.usage.UsageStats> stats =
				usm.queryUsageStats(
				android.app.usage.UsageStatsManager.INTERVAL_DAILY,
				time - 1000 * 10,
				time
				);
				
				if (stats == null || stats.isEmpty()) {
					
					startActivity(
					new Intent(
					android.provider.Settings.ACTION_USAGE_ACCESS_SETTINGS
					)
					);
				}
				
				SketchwareUtil.showMessage(
				getApplicationContext(),
				"Service Smart Started!"
				);
				
			} else {
				
				SketchwareUtil.showMessage(
				getApplicationContext(),
				"Shizuku Belum Aktif!"
				);
			}
		});
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
		
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0E0E0E")));
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
			!= PackageManager.PERMISSION_GRANTED) {
				
				requestPermissions(
				new String[]{Manifest.permission.POST_NOTIFICATIONS},
				1001
				);
			}
		}
		packag = user.getString("pc", "");
		name = user.getString("us", "");
		textview1.setText(name);
		textview2.setText(packag);
		try {
			// আপনার কোড এখানে দিন
			
			Drawable app_icon = getPackageManager().getApplicationIcon(packag);
			
			imageview2.setImageDrawable(app_icon);
			Bitmap bmp = ImageHelper.getCache(packag);
			
			if (bmp != null) {
				linear5.setBackground(
				new BitmapDrawable(getApplicationContext().getResources(), bmp)
				);
			} else {
				linear5.setBackgroundColor(0x22000000);
			}
		} catch (Exception e) {
			SketchwareUtil.showMessage(getApplicationContext(), "error: " + e.getMessage());
		}
		
		_AksesShizuku();
		_sensiAdjust();
		_fpsCharge();
		_sizetetap();
		_permNotif();
		_LIST();
		SharedPreferences pref =
		getSharedPreferences("ServicePrefs", Context.MODE_PRIVATE);
		
		int dpiOpen = pref.getInt("dpi", 360);
		
		int dpiClose = pref.getInt("dpiC", 360);
		
		edittext1.setText(String.valueOf(dpiOpen));
		
		edittext2.setText(String.valueOf(dpiClose));
		seekbar5.setValue(2);
		linear38.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)30, (int)1, 0xFF00ACC1, 0x5000ACC1));
		linear10.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)0, Color.TRANSPARENT, 0xFF1A1A1A));
		linear12.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)0, Color.TRANSPARENT, 0xFF1A1A1A));
		linear14.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)0, Color.TRANSPARENT, 0xFF1A1A1A));
		linear16.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)0, Color.TRANSPARENT, 0xFF1A1A1A));
		linear26.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)0, Color.TRANSPARENT, 0xFF1A1A1A));
		linear28.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)0, Color.TRANSPARENT, 0xFF1A1A1A));
		linear30.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)0, Color.TRANSPARENT, 0xFF1A1A1A));
		linear34.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)0, Color.TRANSPARENT, 0xFF1A1A1A));
		linear36.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)0, Color.TRANSPARENT, 0xFF1A1A1A));
		linear129.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)0, Color.TRANSPARENT, 0xFF1A1A1A));
		try {
			
			File file = new File(Environment.getExternalStorageDirectory(), "ZYREX-TOOLS/data/.cache/" + packag + ".json");
			
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();
			
			
			JSONObject obj = new JSONObject(new String(data));
			switch3.setChecked(true);
			linear9.setAlpha((float)(1.0d));
			switch5.setEnabled(true);
			switch1.setEnabled(true);
			switch2.setEnabled(true);
			switch4.setEnabled(true);
			switch6.setEnabled(true);
			switch7.setEnabled(true);
			switch8.setEnabled(true);
			linear23.setEnabled(true);
			linear24.setEnabled(true);
			linear25.setEnabled(true);
			seekbar5.setEnabled(true);
			
		} catch (Exception e) {
			switch3.setChecked(false);
			linear9.setAlpha((float)(0.6d));
			switch5.setEnabled(false);
			switch1.setEnabled(false);
			switch2.setEnabled(false);
			switch4.setEnabled(false);
			switch6.setEnabled(false);
			switch7.setEnabled(false);
			switch8.setEnabled(false);
			linear23.setEnabled(false);
			linear24.setEnabled(false);
			linear25.setEnabled(false);
			seekbar5.setEnabled(false);
			
		}
		if (packag.equals("com.dts.freefireth") || packag.equals("com.dts.freefiremax")) {
			linear26.setVisibility(View.VISIBLE);
		} else {
			linear26.setVisibility(View.GONE);
		}
		try {
			
			File targetFile = new File(Environment.getExternalStorageDirectory(),
			"ZYREX-TOOLS/data/.cache/" + packag + ".json");
			
			FileInputStream fis = new FileInputStream(targetFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			StringBuilder builder = new StringBuilder();
			
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			
			reader.close();
			fis.close();
			
			
			JSONObject json = new JSONObject(builder.toString());
			
			
			String mode = json.optString("Perform", "");
			
			
			switch (mode.toLowerCase()) {
				
				case "enable":
				switch5.setChecked(true);
				break;
				
				case "disable":
				switch5.setChecked(false);
				break;
			}
			
		} catch (Exception e) {
			switch5.setChecked(false);
		}
		try {
			
			File targetFile = new File(Environment.getExternalStorageDirectory(),
			"ZYREX-TOOLS/data/.cache/" + packag + ".json");
			
			FileInputStream fis = new FileInputStream(targetFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			StringBuilder builder = new StringBuilder();
			
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			
			reader.close();
			fis.close();
			
			
			JSONObject json = new JSONObject(builder.toString());
			
			
			String mode = json.optString("block", "");
			
			
			switch (mode.toLowerCase()) {
				
				case "on":
				switch1.setChecked(true);
				break;
				
				case "off":
				switch1.setChecked(false);
				break;
			}
			
		} catch (Exception e) {
			switch1.setChecked(false);
		}
		try {
			
			File targetFile = new File(Environment.getExternalStorageDirectory(),
			"ZYREX-TOOLS/data/.cache/" + packag + ".json");
			
			FileInputStream fis = new FileInputStream(targetFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			StringBuilder builder = new StringBuilder();
			
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			
			reader.close();
			fis.close();
			
			
			JSONObject json = new JSONObject(builder.toString());
			
			
			String mode = json.optString("network", "");
			
			
			switch (mode.toLowerCase()) {
				
				case "on":
				switch2.setChecked(true);
				break;
				
				case "off":
				switch2.setChecked(false);
				break;
			}
			
		} catch (Exception e) {
			switch2.setChecked(false);
		}
		try {
			
			File targetFile = new File(Environment.getExternalStorageDirectory(),
			"ZYREX-TOOLS/data/.cache/" + packag + ".json");
			
			FileInputStream fis = new FileInputStream(targetFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			StringBuilder builder = new StringBuilder();
			
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			
			reader.close();
			fis.close();
			
			
			JSONObject json = new JSONObject(builder.toString());
			
			
			String mode = json.optString("sensi", "");
			
			
			switch (mode.toLowerCase()) {
				
				case "lowest":
				seekbar5.setValue(0);
				textview12.setTextColor(0xFFF5F5F5);
				textview13.setTextColor(0xFF9E9E9E);
				textview14.setTextColor(0xFF9E9E9E);
				textview24.setTextColor(0xFF9E9E9E);
				textview28.setTextColor(0xFF9E9E9E);
				textview29.setTextColor(0xFF9E9E9E);
				break;
				
				case "low":
				seekbar5.setValue(1);
				textview28.setTextColor(0xFFF5F5F5);
				textview12.setTextColor(0xFF9E9E9E);
				textview14.setTextColor(0xFF9E9E9E);
				textview24.setTextColor(0xFF9E9E9E);
				textview13.setTextColor(0xFF9E9E9E);
				textview29.setTextColor(0xFF9E9E9E);
				break;
				case "default":
				seekbar5.setValue(2);
				textview13.setTextColor(0xFFF5F5F5);
				textview12.setTextColor(0xFF9E9E9E);
				textview14.setTextColor(0xFF9E9E9E);
				textview24.setTextColor(0xFF9E9E9E);
				textview28.setTextColor(0xFF9E9E9E);
				textview29.setTextColor(0xFF9E9E9E);
				break;
				case "smooth":
				seekbar5.setValue(3);
				textview24.setTextColor(0xFFF5F5F5);
				textview13.setTextColor(0xFF9E9E9E);
				textview12.setTextColor(0xFF9E9E9E);
				textview28.setTextColor(0xFF9E9E9E);
				textview14.setTextColor(0xFF9E9E9E);
				textview29.setTextColor(0xFF9E9E9E);
				break;
				case "highest":
				seekbar5.setValue(4);
				textview29.setTextColor(0xFFF5F5F5);
				textview13.setTextColor(0xFF9E9E9E);
				textview12.setTextColor(0xFF9E9E9E);
				textview24.setTextColor(0xFF9E9E9E);
				textview28.setTextColor(0xFF9E9E9E);
				textview14.setTextColor(0xFF9E9E9E);
				break;
				case "ultra":
				seekbar5.setValue(5);
				textview14.setTextColor(0xFFF5F5F5);
				textview13.setTextColor(0xFF9E9E9E);
				textview12.setTextColor(0xFF9E9E9E);
				textview24.setTextColor(0xFF9E9E9E);
				textview28.setTextColor(0xFF9E9E9E);
				textview29.setTextColor(0xFF9E9E9E);
				break;
			}
			
		} catch (Exception e) {
			seekbar5.setValue(2);
			textview13.setTextColor(0xFFF5F5F5);
			textview12.setTextColor(0xFF9E9E9E);
			textview14.setTextColor(0xFF9E9E9E);
			textview24.setTextColor(0xFF9E9E9E);
			textview28.setTextColor(0xFF9E9E9E);
			textview29.setTextColor(0xFF9E9E9E);
		}
		try {
			
			File targetFile = new File(Environment.getExternalStorageDirectory(),
			"ZYREX-TOOLS/data/.cache/" + packag + ".json");
			
			FileInputStream fis = new FileInputStream(targetFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			StringBuilder builder = new StringBuilder();
			
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			
			reader.close();
			fis.close();
			
			
			JSONObject json = new JSONObject(builder.toString());
			
			
			String mode = json.optString("fps", "");
			
			
			switch (mode.toLowerCase()) {
				
				case "fps60":
				selectTab(0, linear23, linear24, linear25, textview15, textview16, textview17);
				break;
				
				case "fps90":
				selectTab(1, linear23, linear24, linear25, textview15, textview16, textview17);
				break;
				case "fps120":
				selectTab(2, linear23, linear24, linear25, textview15, textview16, textview17);
				break;
			}
			
		} catch (Exception e) {
			selectTab(0, linear23, linear24, linear25, textview15, textview16, textview17);
		}
		try {
			
			File targetFile = new File(Environment.getExternalStorageDirectory(),
			"ZYREX-TOOLS/data/.cache/" + packag + ".json");
			
			FileInputStream fis = new FileInputStream(targetFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			StringBuilder builder = new StringBuilder();
			
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			
			reader.close();
			fis.close();
			
			
			JSONObject json = new JSONObject(builder.toString());
			
			
			String mode = json.optString("aimtrick", "");
			
			
			switch (mode.toLowerCase()) {
				
				case "on":
				switch4.setChecked(true);
				break;
				
				case "off":
				switch4.setChecked(false);
				break;
			}
			
		} catch (Exception e) {
			switch4.setChecked(false);
		}
		try {
			
			File targetFile = new File(Environment.getExternalStorageDirectory(),
			"ZYREX-TOOLS/data/.cache/" + packag + ".json");
			
			FileInputStream fis = new FileInputStream(targetFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			StringBuilder builder = new StringBuilder();
			
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			
			reader.close();
			fis.close();
			
			
			JSONObject json = new JSONObject(builder.toString());
			
			
			String mode = json.optString("elixirSOC", "");
			
			
			switch (mode.toLowerCase()) {
				
				case "on":
				switch6.setChecked(true);
				break;
				
				case "off":
				switch6.setChecked(false);
				break;
			}
			
		} catch (Exception e) {
			switch6.setChecked(false);
		}
		try {
			
			File targetFile = new File(Environment.getExternalStorageDirectory(),
			"ZYREX-TOOLS/data/.cache/" + packag + ".json");
			
			FileInputStream fis = new FileInputStream(targetFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			StringBuilder builder = new StringBuilder();
			
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			
			reader.close();
			fis.close();
			
			
			JSONObject json = new JSONObject(builder.toString());
			
			
			String mode = json.optString("surface", "");
			
			
			switch (mode.toLowerCase()) {
				
				case "on":
				switch7.setChecked(true);
				break;
				
				case "off":
				switch7.setChecked(false);
				break;
			}
			
		} catch (Exception e) {
			switch7.setChecked(false);
		}
		try {
			
			File targetFile = new File(Environment.getExternalStorageDirectory(),
			"ZYREX-TOOLS/data/.cache/" + packag + ".json");
			
			FileInputStream fis = new FileInputStream(targetFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			StringBuilder builder = new StringBuilder();
			
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			
			reader.close();
			fis.close();
			
			
			JSONObject json = new JSONObject(builder.toString());
			
			
			String mode = json.optString("aiming", "");
			
			
			switch (mode.toLowerCase()) {
				
				case "on":
				switch8.setChecked(true);
				break;
				
				case "off":
				switch8.setChecked(false);
				break;
			}
			
		} catch (Exception e) {
			switch8.setChecked(false);
		}
		linear41.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)15, (int)0, Color.TRANSPARENT, 0x7043A047));
		
		try {
			
			String command = "cmd game list modes";
			Process process = ExecEngine.newProcess(new String[]{"sh", "-c", command});
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = reader.readLine();
			
			if (line != null && !line.isEmpty()) {
				linear41.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)15, (int)0, Color.TRANSPARENT, 0x7043A047));
				seekbar5.setEnabled(true);
				textview32.setText("Support");
			} else {
				linear41.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)15, (int)0, Color.TRANSPARENT, 0x70E53935));
				seekbar5.setEnabled(false);
				textview32.setText("Not Support");
			}
		} catch (Exception e) {
			linear41.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)15, (int)0, Color.TRANSPARENT, 0x70E53935));
			seekbar5.setEnabled(false);
			textview32.setText("Not Running");
		}
		linear133.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)0, Color.TRANSPARENT, 0x5000ACC1));
		linear144.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)0, Color.TRANSPARENT, 0xFF212121));
		linear145.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)0, Color.TRANSPARENT, 0xFF212121));
	}
	
	
	@Override
	public void onBackPressed() {
		animateExit();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		_sizetetap();
		if (virtualConfig != null && virtualMetrics != null) {
			getResources().updateConfiguration(virtualConfig, virtualMetrics);
		}
		try {
			
			File file = new File(Environment.getExternalStorageDirectory(), "ZYREX-TOOLS/data/.cache/" + packag + ".json");
			
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();
			
			
			JSONObject obj = new JSONObject(new String(data));
			switch3.setChecked(true);
			linear9.setAlpha((float)(1.0d));
			switch5.setEnabled(true);
			switch1.setEnabled(true);
			switch2.setEnabled(true);
			switch4.setEnabled(true);
			switch6.setEnabled(true);
			switch7.setEnabled(true);
			switch8.setEnabled(true);
			linear23.setEnabled(true);
			linear24.setEnabled(true);
			linear25.setEnabled(true);
			seekbar5.setEnabled(true);
			
		} catch (Exception e) {
			switch3.setChecked(false);
			linear9.setAlpha((float)(0.6d));
			switch5.setEnabled(false);
			switch1.setEnabled(false);
			switch2.setEnabled(false);
			switch4.setEnabled(false);
			switch6.setEnabled(false);
			switch7.setEnabled(false);
			switch8.setEnabled(false);
			linear23.setEnabled(false);
			linear24.setEnabled(false);
			linear25.setEnabled(false);
			seekbar5.setEnabled(false);
			
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		_sizetetap();
	}
	public void _variable() {
	}
	private boolean isEditMode = true;
	private boolean running = false;
	private int targetX = 0;
	private int targetY = 0;
	private int clickInterval = 10;
	private int screenWidth, screenHeight;
	private float percentX = 0.5f;
	private float percentY = 0.5f;
	private boolean isDragging = true;
	private boolean dragEnabled = true;
	private int lastX, lastY;
	private float downX, downY;
	private float moveThreshold = 20;
	private ScheduledExecutorService executor;
	private Handler hendl = new Handler(Looper.getMainLooper());
	private WindowManager wmArea, wmSentuh;
	private WindowManager.LayoutParams paramsArea, paramsSentuh;
	private View viewArea, viewSentuh;
	private HandlerThread macroThread;
	private Handler macroHandler;
	private WindowManager wmMacro;
	private View viewMacro;
	private java.lang.Process process;
	private WindowManager.LayoutParams params;
	private long downTime;
	private int xOffset = 0;
	private int screenW, screenH;
	private View.OnTouchListener touchListener;
	private NotificationManager nm;
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
	
	public String shizukuExecMacro(String cmd) {
		
		try {
			
			process =
			ExecEngine.newProcess(new String[]{"sh","-c",cmd + " &"});
			
			InputStream in = process.getInputStream();
			
			byte[] buffer = new byte[1024];
			int len;
			StringBuilder out = new StringBuilder();
			
			while ((len = in.read(buffer)) > 0) {
				out.append(new String(buffer, 0, len));
			}
			
			
			process.destroy();
			
			return out.toString();
			
		} catch (Exception e) {
			return "";
		}
	}
	{
	}
	
	
	public void _backgroundPlugins() {
	}
	private Bitmap removeWhiteArea(Bitmap src) {
		int width = src.getWidth();
		int height = src.getHeight();
		Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		
		int white = Color.WHITE; // warna putih
		int tolerance = 40; // toleransi warna putih (semakin besar makin banyak yg dihapus)
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = src.getPixel(x, y);
				
				int r = Color.red(pixel);
				int g = Color.green(pixel);
				int b = Color.blue(pixel);
				
				// cek apakah mendekati putih
				if (Math.abs(r - 255) < tolerance &&
				Math.abs(g - 255) < tolerance &&
				Math.abs(b - 255) < tolerance) {
					result.setPixel(x, y, Color.TRANSPARENT);
				} else {
					result.setPixel(x, y, pixel);
				}
			}
		}
		return result;
	}
	
	public Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}
		
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);
		
		return bitmap;
	}
	
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int radius) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		
		paint.setAntiAlias(true);
		canvas.drawRoundRect(rectF, radius, radius, paint);
		
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		
		return output;
	}
	
	private Bitmap getRoundedBitmap(Bitmap bitmap, float cornerRadius) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
		
		RectF rect = new RectF(0f, 0f, bitmap.getWidth(), bitmap.getHeight());
		canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);
		
		return output;
	}
	
	{
	}
	
	
	public void _netWorkChecked() {
	}
	private boolean isNetworkBoosterActive() {
		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		if (am != null) {
			for (ActivityManager.RunningServiceInfo service : am.getRunningServices(Integer.MAX_VALUE)) {
				if (service.service.getClassName().equals(NetworkBoosterService.class.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	{
	}
	
	
	public void _sensiAdjust() {
		seekbar5.addOnSliderTouchListener(new com.google.android.material.slider.Slider.OnSliderTouchListener() {
			
			@Override
			public void onStartTrackingTouch(com.google.android.material.slider.Slider slider) {
				int mode = (int) slider.getValue();
				
				textview12.setTextColor(0xFF9E9E9E);
				textview13.setTextColor(0xFF9E9E9E);
				textview14.setTextColor(0xFF9E9E9E);
				textview24.setTextColor(0xFF9E9E9E);
				textview28.setTextColor(0xFF9E9E9E);
				textview29.setTextColor(0xFF9E9E9E);
				
				if (mode == 0) {
					textview12.setTextColor(0xFFF5F5F5);
					
				} else if (mode == 1) {
					textview28.setTextColor(0xFFF5F5F5);
				} 
				else if (mode == 2) {
					textview13.setTextColor(0xFFF5F5F5);
				} 
				else if (mode == 3) {
					textview24.setTextColor(0xFFF5F5F5);
				}
				else if (mode == 4) {
					textview29.setTextColor(0xFFF5F5F5);
				}
				else if (mode == 5) {
					textview14.setTextColor(0xFFF5F5F5);
				}
			}
			
			@Override
			public void onStopTrackingTouch(com.google.android.material.slider.Slider slider) {
				
				int mode = (int) slider.getValue();
				
				textview12.setTextColor(0xFF9E9E9E);
				textview13.setTextColor(0xFF9E9E9E);
				textview14.setTextColor(0xFF9E9E9E);
				textview24.setTextColor(0xFF9E9E9E);
				textview28.setTextColor(0xFF9E9E9E);
				textview29.setTextColor(0xFF9E9E9E);
				
				if (mode == 0) {
					textview12.setTextColor(0xFFF5F5F5);
					
					LoadingBooster.show(SetpackageActivity.this)
					.size(180)
					.color(0xFF00ACC1);
					
					user.edit().putString("lowest", "nub").commit();
					try {
						File targetFile = new File(Environment.getExternalStorageDirectory(),
						"ZYREX-TOOLS/data/.cache/" + packag + ".json");
						
						FileInputStream fis = new FileInputStream(targetFile);
						BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
						StringBuilder builder = new StringBuilder();
						
						String line;
						while ((line = reader.readLine()) != null) {
							builder.append(line);
						}
						
						reader.close();
						fis.close();
						
						JSONObject json = new JSONObject(builder.toString());
						json.put("sensi", "lowest");
						
						
						FileOutputStream fos = new FileOutputStream(targetFile);
						fos.write(json.toString(1).getBytes());
						fos.close();
						
						Log.d("PluginJSON", "JSON updated!");
						
					} catch (Exception e) {
						Log.e("PluginJSON", "Error: " + e.getMessage());
					}
					
					LoadingBooster.hide();
					user.edit().remove("smooth").commit();
					user.edit().remove("highest").commit();
					user.edit().remove("ultra").commit();
					user.edit().remove("default").commit();
					user.edit().remove("low").commit();
					
				} else if (mode == 1) {
					textview28.setTextColor(0xFFF5F5F5);
					
					LoadingBooster.show(SetpackageActivity.this)
					.size(180)
					.color(0xFF00ACC1);
					
					user.edit().putString("low", "nub").commit();
					try {
						File targetFile = new File(Environment.getExternalStorageDirectory(),
						"ZYREX-TOOLS/data/.cache/" + packag + ".json");
						
						FileInputStream fis = new FileInputStream(targetFile);
						BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
						StringBuilder builder = new StringBuilder();
						
						String line;
						while ((line = reader.readLine()) != null) {
							builder.append(line);
						}
						
						reader.close();
						fis.close();
						
						JSONObject json = new JSONObject(builder.toString());
						json.put("sensi", "low");
						
						
						FileOutputStream fos = new FileOutputStream(targetFile);
						fos.write(json.toString(1).getBytes());
						fos.close();
						
						Log.d("PluginJSON", "JSON updated!");
						
					} catch (Exception e) {
						Log.e("PluginJSON", "Error: " + e.getMessage());
					}
					
					LoadingBooster.hide();
					user.edit().remove("smooth").commit();
					user.edit().remove("highest").commit();
					user.edit().remove("ultra").commit();
					user.edit().remove("default").commit();
					user.edit().remove("lowest").commit();
				} 
				else if (mode == 2) {
					textview13.setTextColor(0xFFF5F5F5);
					
					LoadingBooster.show(SetpackageActivity.this)
					.size(180)
					.color(0xFF00ACC1);
					
					user.edit().putString("default", "nub").commit();
					try {
						File targetFile = new File(Environment.getExternalStorageDirectory(),
						"ZYREX-TOOLS/data/.cache/" + packag + ".json");
						
						FileInputStream fis = new FileInputStream(targetFile);
						BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
						StringBuilder builder = new StringBuilder();
						
						String line;
						while ((line = reader.readLine()) != null) {
							builder.append(line);
						}
						
						reader.close();
						fis.close();
						
						JSONObject json = new JSONObject(builder.toString());
						json.put("sensi", "default");
						
						
						FileOutputStream fos = new FileOutputStream(targetFile);
						fos.write(json.toString(1).getBytes());
						fos.close();
						
						Log.d("PluginJSON", "JSON updated!");
						
					} catch (Exception e) {
						Log.e("PluginJSON", "Error: " + e.getMessage());
					}
					
					LoadingBooster.hide();
					user.edit().remove("smooth").commit();
					user.edit().remove("highest").commit();
					user.edit().remove("ultra").commit();
					user.edit().remove("low").commit();
					user.edit().remove("lowest").commit();
				} 
				else if (mode == 3) {
					textview24.setTextColor(0xFFF5F5F5);
					
					LoadingBooster.show(SetpackageActivity.this)
					.size(180)
					.color(0xFF00ACC1);
					
					user.edit().putString("smooth", "standard").commit();
					try {
						File targetFile = new File(Environment.getExternalStorageDirectory(),
						"ZYREX-TOOLS/data/.cache/" + packag + ".json");
						
						FileInputStream fis = new FileInputStream(targetFile);
						BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
						StringBuilder builder = new StringBuilder();
						
						String line;
						while ((line = reader.readLine()) != null) {
							builder.append(line);
						}
						
						reader.close();
						fis.close();
						
						JSONObject json = new JSONObject(builder.toString());
						json.put("sensi", "smooth");
						
						
						FileOutputStream fos = new FileOutputStream(targetFile);
						fos.write(json.toString(1).getBytes());
						fos.close();
						
						Log.d("PluginJSON", "JSON updated!");
						
					} catch (Exception e) {
						Log.e("PluginJSON", "Error: " + e.getMessage());
					}
					
					LoadingBooster.hide();
					user.edit().remove("default").commit();
					user.edit().remove("highest").commit();
					user.edit().remove("ultra").commit();
					user.edit().remove("low").commit();
					user.edit().remove("lowest").commit();
				}
				else if (mode == 4) {
					textview29.setTextColor(0xFFF5F5F5);
					
					LoadingBooster.show(SetpackageActivity.this)
					.size(180)
					.color(0xFF00ACC1);
					
					user.edit().putString("highest", "pro").commit();
					try {
						File targetFile = new File(Environment.getExternalStorageDirectory(),
						"ZYREX-TOOLS/data/.cache/" + packag + ".json");
						
						FileInputStream fis = new FileInputStream(targetFile);
						BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
						StringBuilder builder = new StringBuilder();
						
						String line;
						while ((line = reader.readLine()) != null) {
							builder.append(line);
						}
						
						reader.close();
						fis.close();
						
						JSONObject json = new JSONObject(builder.toString());
						json.put("sensi", "highest");
						
						
						FileOutputStream fos = new FileOutputStream(targetFile);
						fos.write(json.toString(1).getBytes());
						fos.close();
						
						Log.d("PluginJSON", "JSON updated!");
						
					} catch (Exception e) {
						Log.e("PluginJSON", "Error: " + e.getMessage());
					}
					
					LoadingBooster.hide();
					user.edit().remove("default").commit();
					user.edit().remove("smooth").commit();
					user.edit().remove("ultra").commit();
					user.edit().remove("low").commit();
					user.edit().remove("lowest").commit();
				}
				else if (mode == 5) {
					textview14.setTextColor(0xFFF5F5F5);
					
					LoadingBooster.show(SetpackageActivity.this)
					.size(180)
					.color(0xFF00ACC1);
					
					user.edit().putString("ultra", "proo").commit();
					try {
						File targetFile = new File(Environment.getExternalStorageDirectory(),
						"ZYREX-TOOLS/data/.cache/" + packag + ".json");
						
						FileInputStream fis = new FileInputStream(targetFile);
						BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
						StringBuilder builder = new StringBuilder();
						
						String line;
						while ((line = reader.readLine()) != null) {
							builder.append(line);
						}
						
						reader.close();
						fis.close();
						
						JSONObject json = new JSONObject(builder.toString());
						json.put("sensi", "ultra");
						
						
						FileOutputStream fos = new FileOutputStream(targetFile);
						fos.write(json.toString(1).getBytes());
						fos.close();
						
						Log.d("PluginJSON", "JSON updated!");
						
					} catch (Exception e) {
						Log.e("PluginJSON", "Error: " + e.getMessage());
					}
					
					LoadingBooster.hide();
					user.edit().remove("default").commit();
					user.edit().remove("smooth").commit();
					user.edit().remove("highest").commit();
					user.edit().remove("low").commit();
					user.edit().remove("lowest").commit();
				}
				
				TimerTask y = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(() -> LoadingBooster.hide());
					}
				};
				
				_timer.schedule(y, 1000);
			}
		});
	}
	
	
	public void _fpsCharge() {
		linear23.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				LoadingBooster.show(SetpackageActivity.this).size(180).color(0xFF00ACC1);
				selectTab(0, linear23, linear24, linear25, textview15, textview16, textview17);
				user.edit().putString("fps60", "low").commit();
				user.edit().remove("fps90").commit();
				user.edit().remove("fps120").commit();
				y = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(() -> {
							try {
								File targetFile = new File(Environment.getExternalStorageDirectory(),
								"ZYREX-TOOLS/data/.cache/" + packag + ".json");
								
								FileInputStream fis = new FileInputStream(targetFile);
								BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
								StringBuilder builder = new StringBuilder();
								
								String line;
								while ((line = reader.readLine()) != null) {
									builder.append(line);
								}
								
								reader.close();
								fis.close();
								
								JSONObject json = new JSONObject(builder.toString());
								json.put("fps", "fps60");
								
								
								FileOutputStream fos = new FileOutputStream(targetFile);
								fos.write(json.toString(1).getBytes());
								fos.close();
								
								Log.d("PluginJSON", "JSON updated!");
								
							} catch (Exception e) {
								Log.e("PluginJSON", "Error: " + e.getMessage());
							}
							
							LoadingBooster.hide();
							LoadingBooster.hide();
						});
					}
				};
				_timer.schedule(y, 1000);
			}
		});
		linear24.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				LoadingBooster.show(SetpackageActivity.this).size(180).color(0xFF00ACC1);
				selectTab(1, linear23, linear24, linear25, textview15, textview16, textview17);
				user.edit().putString("fps90", "high").commit();
				user.edit().remove("fps60").commit();
				user.edit().remove("fps120").commit();
				y = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(() -> {
							try {
								File targetFile = new File(Environment.getExternalStorageDirectory(),
								"ZYREX-TOOLS/data/.cache/" + packag + ".json");
								
								FileInputStream fis = new FileInputStream(targetFile);
								BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
								StringBuilder builder = new StringBuilder();
								
								String line;
								while ((line = reader.readLine()) != null) {
									builder.append(line);
								}
								
								reader.close();
								fis.close();
								
								JSONObject json = new JSONObject(builder.toString());
								json.put("fps", "fps90");
								
								
								FileOutputStream fos = new FileOutputStream(targetFile);
								fos.write(json.toString(1).getBytes());
								fos.close();
								
								Log.d("PluginJSON", "JSON updated!");
								
							} catch (Exception e) {
								Log.e("PluginJSON", "Error: " + e.getMessage());
							}
							
							LoadingBooster.hide();
							LoadingBooster.hide();
						});
					}
				};
				_timer.schedule(y, 1000);
			}
		});
		linear25.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				LoadingBooster.show(SetpackageActivity.this).size(180).color(0xFF00ACC1);
				selectTab(2, linear23, linear24, linear25, textview15, textview16, textview17);
				user.edit().putString("fps120", "hig").commit();
				user.edit().remove("fps90").commit();
				y = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(() -> {
							try {
								File targetFile = new File(Environment.getExternalStorageDirectory(),
								"ZYREX-TOOLS/data/.cache/" + packag + ".json");
								
								FileInputStream fis = new FileInputStream(targetFile);
								BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
								StringBuilder builder = new StringBuilder();
								
								String line;
								while ((line = reader.readLine()) != null) {
									builder.append(line);
								}
								
								reader.close();
								fis.close();
								
								JSONObject json = new JSONObject(builder.toString());
								json.put("fps", "fps120");
								
								
								FileOutputStream fos = new FileOutputStream(targetFile);
								fos.write(json.toString(1).getBytes());
								fos.close();
								
								Log.d("PluginJSON", "JSON updated!");
								
							} catch (Exception e) {
								Log.e("PluginJSON", "Error: " + e.getMessage());
							}
							
							LoadingBooster.hide();
							LoadingBooster.hide();
						});
					}
				};
				_timer.schedule(y, 1000);
				user.edit().remove("fps60").commit();
			}
		});
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
		int targetDpi = (int) (scale * 200);
		
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
	
	
	public void _LIST() {
		linear22.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
	}
	
	
	public void _v() {
	}
	private void selectTab(int pos, LinearLayout tab1, LinearLayout tab2, LinearLayout tab3, TextView txt1, TextView txt2, TextView txt3) {
		
		if (pos == -1) {
			
			tab1.setBackground(null);
			tab2.setBackground(null);
			tab3.setBackground(null);
			
			txt1.setTextColor(0xFFAAAAAA);
			txt2.setTextColor(0xFFAAAAAA);
			txt3.setTextColor(0xFFAAAAAA);
			
			return;
		}
		
		if (pos == 0) {
			
			tab1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)1, 0xFF00ACC1, 0x7000ACC1));
			tab2.setBackground(null);
			tab3.setBackground(null);
			
			txt1.setTextColor(0xFF000000);
			txt2.setTextColor(0xFFAAAAAA);
			txt3.setTextColor(0xFFAAAAAA);
			
			animClick(tab1);
			
		} else if (pos == 1) {
			
			tab2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)1, 0xFF00ACC1, 0x7000ACC1));
			tab1.setBackground(null);
			tab3.setBackground(null);
			
			txt1.setTextColor(0xFFAAAAAA);
			txt2.setTextColor(0xFF000000);
			txt3.setTextColor(0xFFAAAAAA);
			
			animClick(tab2);
		} else {
			tab3.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)1, 0xFF00ACC1, 0x7000ACC1));
			tab1.setBackground(null);
			tab2.setBackground(null);
			
			txt3.setTextColor(0xFF000000);
			txt2.setTextColor(0xFFAAAAAA);
			txt1.setTextColor(0xFFAAAAAA);
			
			animClick(tab3);
		}
	}
	
	private GradientDrawable getBg(int color) {
		GradientDrawable gd = new GradientDrawable();
		gd.setColor(color);
		gd.setCornerRadius(20);
		return gd;
	}
	
	private void animClick(View v) {
		v.setScaleX(0.95f);
		v.setScaleY(0.95f);
		v.animate().scaleX(1f).scaleY(1f).setDuration(150).start();
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
	
	
	public void _aksesPintarCmd() {
	}
	
	private void runCmdList(ArrayList<String> cmds) {
		if (cmds == null) return;
		
		for (String cmd : cmds) {
			try {
				shizukuExec(cmd);
				Thread.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
	
	
	public void _permNotif() {
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		
		if (requestCode == 1001) {
			if (grantResults.length > 0
			&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				
				// Permission diizinkan
			} else {
				
				// Permission ditolak
			}
		}
	}
	{
	}
	
}
