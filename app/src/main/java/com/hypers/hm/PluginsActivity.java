package com.hypers.hm;
import android.os.Environment;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import com.bumptech.glide.*;
import com.facebook.shimmer.*;
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
import retrofit2.*;
import rikka.shizuku.api.*;
import rikka.shizuku.provider.*;
import java.lang.Process;
import android.graphics.*;
import android.graphics.drawable.*;

import androidx.activity.OnBackPressedCallback;
import rikka.shizuku.Shizuku;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.provider.Settings;
import android.Manifest;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;

import com.hypers.hm.service.ProBoosterService;



public class PluginsActivity extends AppCompatActivity {
	
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
	private double sw = 0;
	private String BASE_DIR = "";
	private String PLUGINS_DIR = "";
	private double search = 0;
	
	private ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear4;
	private ImageView imageview1;
	private LinearLayout linear5;
	private LinearLayout linear12;
	private TextView textview2;
	private TextView textview3;
	private EditText edittext1;
	private ImageView imageview3;
	private LinearLayout linear9;
	private LinearLayout linear10;
	private FrameLayout linear3;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private SwipeRefreshLayout swiperefreshlayout1;
	private ListView listview1;
	private LinearLayout linear8;
	private ImageView imageview2;
	private TextView textview4;
	private TextView textview5;
	private LinearLayout linear11;
	private TextView textview6;
	
	private SharedPreferences webUi;
	private Intent i = new Intent();
	private SharedPreferences modules;
	private TimerTask timeLaunch;
	private SharedPreferences shellExc;
	private SharedPreferences zipplug;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.plugins);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear4 = findViewById(R.id.linear4);
		imageview1 = findViewById(R.id.imageview1);
		linear5 = findViewById(R.id.linear5);
		linear12 = findViewById(R.id.linear12);
		textview2 = findViewById(R.id.textview2);
		textview3 = findViewById(R.id.textview3);
		edittext1 = findViewById(R.id.edittext1);
		imageview3 = findViewById(R.id.imageview3);
		linear9 = findViewById(R.id.linear9);
		linear10 = findViewById(R.id.linear10);
		linear3 = findViewById(R.id.linear3);
		linear6 = findViewById(R.id.linear6);
		linear7 = findViewById(R.id.linear7);
		swiperefreshlayout1 = findViewById(R.id.swiperefreshlayout1);
		listview1 = findViewById(R.id.listview1);
		linear8 = findViewById(R.id.linear8);
		imageview2 = findViewById(R.id.imageview2);
		textview4 = findViewById(R.id.textview4);
		textview5 = findViewById(R.id.textview5);
		linear11 = findViewById(R.id.linear11);
		textview6 = findViewById(R.id.textview6);
		webUi = getSharedPreferences("webUi", Activity.MODE_PRIVATE);
		modules = getSharedPreferences("modules", Activity.MODE_PRIVATE);
		shellExc = getSharedPreferences("shellExc", Activity.MODE_PRIVATE);
		zipplug = getSharedPreferences("zipplug", Activity.MODE_PRIVATE);
		
		imageview1.setOnClickListener(_v -> {
			_click(imageview1);
			
			if (searchplug) {
				edittext1.setVisibility(View.GONE);
				linear5.setVisibility(View.VISIBLE);
				imageview3.setVisibility(View.VISIBLE);
				searchplug = false;
			} else {
				finish();
			}
		});
		
		swiperefreshlayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				listMap.clear(); 
				_refresh_lost();
				swiperefreshlayout1.setRefreshing(false);
			}
		});
		
		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (selectedPosition == _position) {
					selectedPosition = -1; 
				} else {
					selectedPosition = _position; 
				}
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
		});
		
		listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				
				return true;
			}
		});
		
		linear8.setOnClickListener(_v -> {
			_click(linear8);
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("*/*");
			intent.addCategory(Intent.CATEGORY_OPENABLE);
			intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{
				"application/zip",
				"application/x-zip-compressed"
			});
			startActivityForResult(intent, 1001);
		});
		
		linear11.setOnClickListener(_v -> {
			_click(linear11);
			
			String basePath = Environment.getExternalStorageDirectory() + "/ZYREX-TOOLS/data";
			String[] folderNames = {".users", "Database", "Plugins", ".cache", "Installed"};
			
			for (String name : folderNames) {
				File folder = new File(basePath + "/" + name);
				if (!folder.exists()) {
					boolean created = folder.mkdirs();
					if (created) {
						
					} else {
					}
				}
			}
			linear10.setVisibility(View.GONE);
			linear9.setVisibility(View.VISIBLE);
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
		if (android.os.Build.VERSION.SDK_INT >= 35) {
			getWindow().setDecorFitsSystemWindows(true);
		}
		
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0E0E0E")));
		listview1.setAdapter(new Listview1Adapter(listMap));
		edittext1.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// Tidak perlu pakai ini
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// Ini mirip onQueryTextChange
				searchPlugins(s.toString());
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// Tidak perlu pakai ini
			}
		});
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
			if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
				requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
			} else {
				String basePath = Environment.getExternalStorageDirectory() + "/ZYREX-TOOLS/data";
				String[] folderNames = {".users", "Database", "Plugins", ".cache", "Installed"};
				
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
		} else {
			String basePath = Environment.getExternalStorageDirectory() + "/ZYREX-TOOLS/data";
			String[] folderNames = {".users", "Database", "Plugins", ".cache", "Installed"};
			
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
		
		
		
		getWindow().setFlags(
		WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
		WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
		);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			startForegroundService(new Intent(this, ProBoosterService.class));
		} else {
			startService(new Intent(this, ProBoosterService.class));
		}
		_sizetetap();
		_realTimePlugins();
		_AksesShizuku();
		edittext1.setVisibility(View.GONE);
		listview1.setClipToPadding(false);
		listview1.setPadding(0, 0, 0, 200);
		
		listview1.post(new Runnable() {
			@Override
			public void run() {
				if (listview1.getAdapter() == null) return;
				
				int totalHeight = 0;
				
				for (int i = 0; i < listview1.getAdapter().getCount(); i++) {
					View listItem = listview1.getAdapter().getView(i, null, listview1);
					listItem.measure(0, 0);
					totalHeight += listItem.getMeasuredHeight();
				}
				
				int listViewHeight = listview1.getHeight();
				
				if (totalHeight <= listViewHeight) {
					
					listview1.setOnTouchListener((v, event) -> true);
				} else {
					
					listview1.setOnTouchListener(null);
				}
			}
		});
		BASE_DIR = "/data/local/tmp/HYPERS/data/Plugins/";
		PLUGINS_DIR = "/data/local/tmp/HYPERS/data/Plugins/";
		linear8.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)30, (int)1, 0xFF00ACC1, 0x5000ACC1));
		
		
		File baseDir = new File("/storage/emulated/0/ZYREX-TOOLS");
		
		if (baseDir.exists()) {
			
			linear9.setVisibility(View.VISIBLE);
			linear10.setVisibility(View.GONE);
			
		} else {
			
			linear10.setVisibility(View.VISIBLE);
			linear9.setVisibility(View.GONE);
		}
		showLinear8();
		listview1.setOnScrollListener(new AbsListView.OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
				
				
				if (totalItemCount == 0) {
					showLinear8();
					return;
				}
				
				
				if (firstVisibleItem == 0 && view.getChildAt(0) != null &&
				view.getChildAt(0).getTop() == 0) {
					
					showLinear8();
					
				} else {
					hideLinear8();
				}
			}
		});
		listview1.setScrollingCacheEnabled(false);
		listview1.setAnimationCacheEnabled(false);
		listview1.setHasTransientState(false);
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffbold.ttf"), 1);
		textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffregular.ttf"), 1);
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_click(imageview3);
				edittext1.setVisibility(View.VISIBLE);
				linear5.setVisibility(View.GONE);
				imageview3.setVisibility(View.GONE);
				searchplug = true;
			}
		});
		edittext1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)30, (int)0, 0x7081E4F6, 0xFF1A1A1A));
		getOnBackPressedDispatcher().addCallback(this,
		new OnBackPressedCallback(true) {
			
			@Override
			public void handleOnBackPressed() {
				
				if (searchplug) {
					edittext1.setVisibility(View.VISIBLE);
					linear5.setVisibility(View.GONE);
					imageview3.setVisibility(View.GONE);
					searchplug = false;
				} else {
					animateExit();
				}
				
			}
		});
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		if (_requestCode == 1001 && _resultCode == RESULT_OK) {
			
			Uri uri = _data.getData();
			
			String zipPath = FileUtil.convertUriToFilePath(getApplicationContext(), uri);
			
			File sourceFile = new File(zipPath);
			
			try {
				
				fileName = sourceFile.getName(); 
				fileSizeBytes = sourceFile.length();
				String nameOnly = fileName.replace(".zip", "");
				
				final com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog =
				new com.google.android.material.bottomsheet.BottomSheetDialog(PluginsActivity.this);
				
				View bottomSheetView = getLayoutInflater().inflate(R.layout.plugzip, null);
				bottomSheetDialog.setContentView(bottomSheetView);
				
				bottomSheetDialog.setOnShowListener(dialog -> {
					View sheet = bottomSheetDialog.findViewById(
					com.google.android.material.R.id.design_bottom_sheet
					);
					
					if (sheet != null) {
						int radius = (int) (28 * getResources().getDisplayMetrics().density);
						
						android.graphics.drawable.GradientDrawable bg =
						new android.graphics.drawable.GradientDrawable();
						
						bg.setColor(0xFF0E0E0E);
						bg.setCornerRadius(radius);
						
						sheet.setBackground(bg);
					}
				});
				LinearLayout linear1 = bottomSheetView.findViewById(R.id.linear1);
				LinearLayout linear3 = bottomSheetView.findViewById(R.id.linear3);
				LinearLayout linear4 = bottomSheetView.findViewById(R.id.linear4);
				LinearLayout linear5 = bottomSheetView.findViewById(R.id.linear5);
				LinearLayout linear7 = bottomSheetView.findViewById(R.id.linear7);
				LinearLayout linear8 = bottomSheetView.findViewById(R.id.linear8);
				
				TextView textview2 = bottomSheetView.findViewById(R.id.textview2);
				TextView textview4 = bottomSheetView.findViewById(R.id.textview4);
				
				Switch switch1 = bottomSheetView.findViewById(R.id.switch1);
				{
					android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
					int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
					SketchUi.setColor(0xFF131313);SketchUi.setCornerRadii(new float[]{
						d*28,d*28,d*28 ,d*28,d*0,d*0 ,d*0,d*0});
					linear1.setElevation(d*5);
					linear1.setBackground(SketchUi);
				}
				linear3.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)1, 0xFFBDBDBD, 0x5000ACC1));
				linear4.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)1, Color.TRANSPARENT, 0xFF212121));
				linear5.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)1, 0xFFBDBDBD, 0x5000ACC1));
				linear7.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)1, 0xFFBDBDBD, 0x80E53935));
				linear8.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)1, 0xFFBDBDBD, 0x5000ACC1));
				
				switch1.setChecked(true);
				
				
				textview4.setText("" + fileName);
				
				double fileSizeKB = fileSizeBytes / 1024.0;
				double fileSizeMB = fileSizeKB / 1024.0;
				
				String sizeText;
				if (fileSizeMB >= 1) {
					sizeText = String.format("%.2f MB", fileSizeMB);
				} else {
					sizeText = String.format("%.2f KB", fileSizeKB);
				}
				
				textview2.setText("" + sizeText);
				textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffbold.ttf"), 1);
				textview4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffbold.ttf"), 1);
				switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton cb, boolean isChecked) {
						
						if (isChecked) {
							zipplug.edit()
							.putString("status", "enabled")
							.apply();
							
						} else {
							
							zipplug.edit()
							.putString("status", "disable")
							.apply();
							
						}
						
					}});
				linear7.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						bottomSheetDialog.dismiss();
					}
				});
				linear8.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						installPluginReal(sourceFile.getAbsolutePath());
						bottomSheetDialog.dismiss();
						
					}
				});
				bottomSheetDialog.setCancelable(false);
				bottomSheetDialog.show();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		showLinear8();
		listview1.setOnScrollListener(new AbsListView.OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
				
				
				if (totalItemCount == 0) {
					showLinear8();
					return;
				}
				
				
				if (firstVisibleItem == 0 && view.getChildAt(0) != null &&
				view.getChildAt(0).getTop() == 0) {
					
					showLinear8();
					
				} else {
					hideLinear8();
				}
			}
		});
		
		
		
		if (virtualConfig != null && virtualMetrics != null) {
			getResources().updateConfiguration(virtualConfig, virtualMetrics);
		}
		_sizetetap();
	}
	
	@Override
	public void onBackPressed() {
		if (searchplug) {
			edittext1.setVisibility(View.VISIBLE);
			linear5.setVisibility(View.GONE);
			imageview3.setVisibility(View.GONE);
			searchplug = false;
		} else {
			animateExit();
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		_sizetetap();
	}
	public void _InstallPlugins() {
	}
	private String fileName;
	private long fileSizeBytes;
	
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
	public boolean validateZyrexxFunction(String zipPath){
		try {
			java.util.zip.ZipInputStream zis =
			new java.util.zip.ZipInputStream(new FileInputStream(zipPath));
			
			java.util.zip.ZipEntry entry;
			
			while((entry = zis.getNextEntry()) != null){
				
				String name = entry.getName();
				String cleanName = name.toLowerCase().trim();
				
				Log.d("ZIP_CHECK", "Entry: " + cleanName);
				
				
				if(cleanName.endsWith("zyrexx.function")){
					zis.close();
					return true;
				}
			}
			
			zis.close();
			
		} catch (Exception e){
			Log.e("VALIDATE", e.toString());
		}
		
		return false;
	}
	
	public void unzipJava(String zipFile, String targetDir){
		try{
			byte[] buffer = new byte[1024];
			java.util.zip.ZipInputStream zis =
			new java.util.zip.ZipInputStream(new FileInputStream(zipFile));
			
			java.util.zip.ZipEntry ze;
			
			while((ze = zis.getNextEntry()) != null){
				
				File newFile = new File(targetDir, ze.getName());
				
				if(ze.isDirectory()){
					newFile.mkdirs();
				} else {
					new File(newFile.getParent()).mkdirs();
					
					FileOutputStream fos = new FileOutputStream(newFile);
					
					int len;
					while((len = zis.read(buffer)) > 0){
						fos.write(buffer, 0, len);
					}
					
					fos.close();
				}
			}
			
			zis.close();
			Log.d("UNZIP","Java unzip success");
			
		}catch(Exception e){
			Log.e("UNZIP_JAVA", e.toString());
		}
	}
	
	public interface HtmlCallback {
		void onResult(String htmlFile);
	}
	
	public void findHtml(String folder, HtmlCallback callback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String result = shizukuExecRead("find " + folder + " -name \"*.html\"");
				
				final String firstFile;
				if(result != null && !result.trim().equals("")){
					firstFile = result.split("\n")[0];
				} else {
					firstFile = null;
				}
				
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						callback.onResult(firstFile);
					}
				});
			}
		}).start();
	}
	
	
	public String formatSize(long size) {
		if (size <= 0) return "0 B";
		
		String[] units = new String[]{"B","KB","MB","GB","TB"};
		
		int digitGroups = (int) (Math.log(size) / Math.log(1024));
		
		if (digitGroups < 0) digitGroups = 0;
		if (digitGroups >= units.length) digitGroups = units.length - 1;
		
		double value = size / Math.pow(1024, digitGroups);
		
		return new java.text.DecimalFormat("#,##0.#").format(value)
		+ " " + units[digitGroups];
	}
	
	private void showLinear8() {
		if (linear8.getVisibility() == View.VISIBLE) return;
		
		linear8.setVisibility(View.VISIBLE);
		linear8.setAlpha(0f);
		linear8.setTranslationY(-50f);
		
		linear8.animate()
		.alpha(1f)
		.translationY(0f)
		.setDuration(200)
		.start();
	}
	
	private void hideLinear8() {
		if (linear8.getVisibility() == View.GONE) return;
		
		linear8.animate()
		.alpha(0f)
		.translationY(-50f)
		.setDuration(200)
		.withEndAction(new Runnable() {
			@Override
			public void run() {
				linear8.setVisibility(View.GONE);
			}
		})
		.start();
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
			return execShizukuRead(cmd);
		}
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
			
			java.io.BufferedReader reader = new java.io.BufferedReader(
			new java.io.InputStreamReader(p.getInputStream())
			);
			
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
	
	
	public void execShizuku(String cmd){
		try{
			ExecEngine.newProcess(new String[]{"sh", "-c", cmd});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String execShizukuRead(String cmd){
		
		StringBuilder output = new StringBuilder();
		
		try {
			Process process = ExecEngine.newProcess(new String[]{"sh", "-c", cmd});
			
			java.io.BufferedReader reader = new java.io.BufferedReader(
			new java.io.InputStreamReader(process.getInputStream())
			);
			
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line).append("\n");
			}
			
			reader.close();
			process.waitFor();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return output.toString();
	}
	public void createPluginDir(){
		exec("mkdir -p /data/local/tmp/HYPERS/data/Plugins");
	}
	
	public String getFileName(String path){
		return new java.io.File(path).getName();
	}
	
	
	public ArrayList<String> getSystemPropCommands(String pluginFolder) {
		
		ArrayList<String> cmds = new ArrayList<>();
		
		try {
			
			String propPath = pluginFolder + "/system.prop";
			File file = new File(propPath);
			
			if (!file.exists()) return cmds;
			
			String content = execRead("cat \"" + propPath + "\"");
			if (content == null) return cmds;
			
			for (String line : content.split("\n")) {
				
				if (line == null) continue;
				
				line = line.trim().replace("\r", "");
				
				if (line.isEmpty() || line.startsWith("#")) continue;
				
				if (line.contains("=")) {
					
					String[] parts = line.split("=", 2);
					if (parts.length < 2) continue;
					
					String key = parts[0].trim();
					String value = parts[1].trim();
					
					if (!key.isEmpty() && !value.isEmpty()) {
						cmds.add("setprop " + key + " " + value);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cmds;
	}
	
	private void runScriptsRecursive(File dir) {
		if (dir == null || !dir.exists()) return;
		
		if (dir.isDirectory()) {
			File postFs = new File(dir, "post-fs-data.sh");
			File service = new File(dir, "service.sh");
			
			// POST-FS-DATA
			if (postFs.exists()) {
				exec("chmod 755 \"" + postFs.getAbsolutePath() + "\"");
				exec("sh \"" + postFs.getAbsolutePath() + "\"");
			}
			
			// SERVICE
			if (service.exists()) {
				String path = service.getAbsolutePath();
				exec("chmod 755 \"" + path + "\"");
				exec("pkill -f \"" + path + "\"");
				exec("nohup sh \"" + path + "\" > /dev/null 2>&1 &");
			}
			
			// RECURSIVE KE SUBFOLDER
			File[] files = dir.listFiles();
			if (files != null) {
				for (File file : files) {
					runScriptsRecursive(file);
				}
			}
		}
	}
	
	public void deleteTmpFolder(String folderPath) {
		new Thread(() -> {
			exec("pkill -f \"" + folderPath + "/service.sh\"");
			exec("rm -rf \"" + folderPath + "\"");
		}).start();
	}
	
	
	public ArrayList<String> getPluginFolders(){
		
		ArrayList<String> folders = new ArrayList<>();
		
		String result = execRead("ls -d " + PLUGINS_DIR + "*/ 2>/dev/null");
		
		if(result == null || result.trim().equals("")) return folders;
		
		for(String line : result.split("\n")){
			line = line.trim();
			if(!line.equals("")){
				folders.add(line);
			}
		}
		
		return folders;
	}
	public void installPluginReal(String zipPath) {
		
		new Thread(() -> {
			
			try {
				
				createPluginDir();
				
				String bb = getFilesDir() + "data/bb";
				
				String zipName = new File(zipPath).getName();
				String tmpZip = BASE_DIR + zipName;
				
				execSmart("mkdir -p '" + BASE_DIR + "'");
				execSmart("mkdir -p '" + PLUGINS_DIR + "'");
				
				execSmart("cat '" + zipPath + "' > '" + tmpZip + "'");
				
				String unzipCmd =
				"if command -v unzip >/dev/null 2>&1; then " +
				"unzip -o '" + tmpZip + "' -d '" + PLUGINS_DIR + "'; " +
				"else " +
				bb + " unzip -o '" + tmpZip + "' -d '" + PLUGINS_DIR + "'; " +
				"fi";
				
				execSmart(unzipCmd);
				
				execSmart("sync");
				
				execSmart("rm '" + tmpZip + "'");
				
				File pluginsRoot = new File(PLUGINS_DIR);
				File[] files = pluginsRoot.listFiles();
				
				if (files == null || files.length == 0) {
					throw new Exception("Extract failed or empty");
				}
				
				File pluginDir = files[files.length - 1];
				String expectedFolder = pluginDir.getAbsolutePath();
				
				execSmart("chmod -R 755 '" + expectedFolder + "'");
				
				for (String cmd : getSystemPropCommands(expectedFolder)) {
					execSmart(cmd);
				}
				
				JSONObject json = new JSONObject();
				json.put("plugin_path", expectedFolder);
				json.put("status", "installed");
				json.put("webui", "enabled");
				
				File dbDir = new File(
				Environment.getExternalStorageDirectory(),
				"ZYREX-TOOLS/data/Database/"
				);
				
				if (!dbDir.exists()) dbDir.mkdirs();
				
				File dbFile = new File(dbDir, pluginDir.getName() + ".json");
				
				FileOutputStream fos = new FileOutputStream(dbFile);
				fos.write(json.toString(2).getBytes());
				fos.close();
				
				runOnUiThread(() -> {
					
					zipplug.edit()
					.putString("nameOnly", pluginDir.getName())
					.apply();
					
					Intent i = new Intent(getApplicationContext(), CmdpluginsActivity.class);
					i.putExtra("plugin_path", expectedFolder);
					startActivity(i);
					
					Toast.makeText(this, "Plugin Installed", Toast.LENGTH_SHORT).show();
					
					isDataInitialized = false;
					listMap.clear();
					_refresh_lost();
				});
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
				runOnUiThread(() ->
				Toast.makeText(this, "Install Error", Toast.LENGTH_LONG).show()
				);
			}
			
		}).start();
	}
	{
	}
	
	
	public void _refresh_lost() {
		if (isLoadingPlugins) return;
		isLoadingPlugins = true;
		
		new Thread(() -> {
			ArrayList<HashMap<String,Object>> newList = new ArrayList<>();
			try {
				String baseDir = "/data/local/tmp/HYPERS/data/Plugins/";
				
				// 1. Ambil list folder pakai Shizuku agar tembus permission
				String foldersOut = ExecEngine.execRead("ls -1 " + baseDir + " 2>/dev/null");
				
				if (foldersOut == null || foldersOut.trim().isEmpty()) {
					runOnUiThread(() -> {
						listMap.clear();
						((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
					});
					return;
				}
				
				String[] folders = foldersOut.split("\n");
				for (String folderName : folders) {
					folderName = folderName.trim();
					if (folderName.isEmpty()) continue;
					
					String folderPath = baseDir + folderName + "/";
					HashMap<String,Object> map = new HashMap<>();
					map.put("folder", folderName);
					map.put("path", folderPath);
					
					// 2. Trik Shizuku: Ambil list semua file beserta sizenya sekaligus
					// Kita pakai ls -lR buat dapet size semua file di subfolder
					String listAllFiles = ExecEngine.execRead("ls -lR " + folderPath + " 2>/dev/null");
					
					long totalBytes = 0;
					boolean hasHtml = false;
					
					if (listAllFiles != null) {
						String[] lines = listAllFiles.split("\n");
						for (String line : lines) {
							// Cek size (kolom ke-5 di output ls -l)
							String[] parts = line.split("\\s+");
							if (parts.length >= 5) {
								try {
									// Cek apakah ini file (bukan direktori/total)
									if (line.startsWith("-")) {
										totalBytes += Long.parseLong(parts[4]);
									}
								} catch (Exception ignored) {}
							}
							
							// Cek HTML/CSS
							String lowerLine = line.toLowerCase();
							if (lowerLine.contains(".html") || lowerLine.contains(".css")) {
								hasHtml = true;
							}
						}
					}
					
					map.put("sizeBytes", totalBytes);
					map.put("size", android.text.format.Formatter.formatFileSize(PluginsActivity.this, totalBytes));
					map.put("hasHtml", hasHtml);
					
					// Di thread loading list awal lo:
					boolean isEnabled = getSharedPreferences("modules", MODE_PRIVATE).getBoolean("plugin_" + folderName, false);
					map.put("enabled", isEnabled);
					
					newList.add(map);
				}
				
				runOnUiThread(() -> {
					listMap.clear();
					listMap.addAll(newList);
					((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
				});
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				isLoadingPlugins = false;
			}
		}).start();
		
	}
	
	
	public void _realTimePlugins() {
		
		Timer pluginTimer = new Timer();
		pluginTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				
				String path = "/data/local/tmp/HYPERS/data/Plugins/";
				
				String output = shizukuExecRead("ls -1 \"" + path + "\" 2>/dev/null");
				
				if(output == null) return;
				
				output = output.trim();
				
				if(!output.equals(lastPluginList)){
					
					lastPluginList = output;
					
					runOnUiThread(new Runnable(){
						@Override
						public void run(){
							
							_countPlugin();
							_refresh_lost();
							
						}
					});
					
				}
				
			}
		}, 0, 2000);
	}
	
	
	public void _copyBannerPlugins() {
	}
	public void copyPluginBanner(String pluginFolder){
		
		try{
			
			// folder cache
			String cacheDir = "/storage/emulated/0/ZYREX-TOOLS/data/.cache/";
			shizukuExec("mkdir -p \"" + cacheDir + "\"");
			
			// cari png di seluruh folder plugin
			String result = shizukuExecRead(
			"find \"" + pluginFolder + "\" -type f -iname \"*.png\" | head -n 1"
			);
			
			if(result == null || result.trim().equals("")){
				Log.d("PLUGIN","PNG tidak ditemukan");
				return;
			}
			
			String pngPath = result.split("\n")[0].trim();
			
			String pluginName = new java.io.File(pluginFolder).getName();
			
			String targetPath = cacheDir + pluginName + ".png";
			
			shizukuExec("cp \"" + pngPath + "\" \"" + targetPath + "\"");
			
			Log.d("PLUGIN","Banner copied: "+targetPath);
			
		}catch(Exception e){
			Log.e("PLUGIN","Banner error "+e.getMessage());
		}
		
	}
	{
	}
	
	
	public void _clickAnimation(final View _view) {
		ScaleAnimation fade_in = new ScaleAnimation(0.9f, 1f, 0.9f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.7f);
		fade_in.setDuration(300);
		fade_in.setFillAfter(true);
		_view.startAnimation(fade_in);
		//aauraparti YouTube channel//
	}
	
	
	public void _countPlugins() {
	}
	public void _countPlugin(){
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				String path = "/data/local/tmp/HYPERS/data/Plugins/";
				
				String output = shizukuExecRead("cd \"" + path + "\" && ls -1d */ 2>/dev/null");
				
				int count = 0;
				
				if(output != null && !output.trim().equals("")){
					String[] lines = output.split("\n");
					count = lines.length;
				}
				
				final int total = count;
				
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						
						
						
					}
				});
				
			}
		}).start();
		
	}
	{
	}
	
	
	public void _variable() {
	}
	private Process process;
	private String lastPluginList;
	
	private boolean isDataInitialized = false; 
	
	private boolean isProcessing = false;
	private boolean searchplug = false;
	private int selectedPosition = -1;
	private PluginManager pm;
	private ArrayList<String> pluginPaths = new ArrayList<>();
	private ArrayList<String> pluginNames = new ArrayList<>();
	private ArrayAdapter<String> adapter;
	private boolean runt = false;
	private boolean isLoadingPlugins = false;
	private Map<String, String> htmlCache = new HashMap<>();
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
	
	
	public void _shizukuRead() {
	}
	public String shizukuExecRead(String cmd) {
		StringBuilder result = new StringBuilder();
		
		try {
			if (!Shizuku.pingBinder()) return "Shizuku tidak aktif";
			if (Shizuku.checkSelfPermission() != PackageManager.PERMISSION_GRANTED)
			return "Permission Shizuku belum diberikan";
			
			Process process = ExecEngine.newProcess(new String[]{"sh", "-c", cmd});
			
			BufferedReader reader = new BufferedReader(
			new InputStreamReader(process.getInputStream())
			);
			BufferedReader errReader = new BufferedReader(
			new InputStreamReader(process.getErrorStream())
			);
			
			String line;
			while ((line = reader.readLine()) != null) result.append(line).append("\n");
			while ((line = errReader.readLine()) != null) result.append(line).append("\n");
			
			process.waitFor();
			
		} catch (Exception e) {
			result.append(e.toString());
		}
		
		return result.toString();
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
		int targetDpi = (int) (scale * 180);
		
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
	
	
	public void _pluginsExec() {
	}
	public void executeFromProp(String folderName, String fileName) {
		
		new Thread(() -> {
			
			try {
				
				String basePath = "/data/local/tmp/HYPERS/data/Plugins/" + folderName;
				
				
				String findCmd =
				"find \"" + basePath + "\" -type f -name \"" + fileName + "\" | head -n 1";
				
				String result = shizukuExecRead(findCmd);
				
				if (result == null || result.trim().equals("")) {
					Log.e("PLUGIN", fileName + " tidak ditemukan");
					return;
				}
				
				String filePath = result.split("\n")[0].trim();
				
				Log.d("PLUGIN_EXEC", filePath);
				
				
				String cmd =
				"chmod +x \"" + filePath + "\" && sh \"" + filePath + "\" 2>&1";
				
				String output = execSmart(cmd);
				
				Log.d("PLUGIN_OUTPUT", output);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}).start();
	}
	{
	}
	
	
	public void _bgPluginsV2() {
	}
	
	public static Bitmap manualFastBlur(Bitmap sentBitmap, int radius) {
		if (sentBitmap == null) return null;
		if (radius < 1) return sentBitmap;
		
		Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
		
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		int[] pixels = new int[w * h];
		bitmap.getPixels(pixels, 0, w, 0, 0, w, h);
		
		int[] temp = new int[pixels.length];
		int wm = w - 1;
		int hm = h - 1;
		int div = radius + radius + 1;
		
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int rsum = 0, gsum = 0, bsum = 0;
				for (int i = -radius; i <= radius; i++) {
					int px = pixels[y * w + Math.min(wm, Math.max(x + i, 0))];
					rsum += (px >> 16) & 0xff;
					gsum += (px >> 8) & 0xff;
					bsum += px & 0xff;
				}
				temp[y * w + x] = (0xff << 24) | ((rsum / div) << 16) | ((gsum / div) << 8) | (bsum / div);
			}
		}
		
		bitmap.setPixels(temp, 0, w, 0, 0, w, h);
		return bitmap;
	}
	
	private static android.util.LruCache<String, Bitmap> imageCache;
	
	static {
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		int cacheSize = maxMemory / 16; 
		imageCache = new android.util.LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount() / 1024;
			}
		};
	}
	public static Bitmap removeWhiteArea(Bitmap src) {
		try {
			if (src == null) return null;
			
			if (!src.isMutable()) {
				src = src.copy(Bitmap.Config.ARGB_8888, true);
			}
			
			int width = src.getWidth();
			int height = src.getHeight();
			
			if (width * height > 4000000) return src;
			
			Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			result.setHasAlpha(true);
			
			int[] pixels = new int[width * height];
			src.getPixels(pixels, 0, width, 0, 0, width, height);
			
			int tolerance = 40;
			
			for (int i = 0; i < pixels.length; i++) {
				int pixel = pixels[i];
				
				int r = (pixel >> 16) & 0xff;
				int g = (pixel >> 8) & 0xff;
				int b = pixel & 0xff;
				
				if (Math.abs(r - 255) < tolerance &&
				Math.abs(g - 255) < tolerance &&
				Math.abs(b - 255) < tolerance) {
					
					pixels[i] = 0x00000000;
				}
			}
			
			result.setPixels(pixels, 0, width, 0, 0, width, height);
			return result;
			
		} catch (OutOfMemoryError e) {
			return src;
		} catch (Exception e) {
			return src;
		}
	}
	public static Bitmap drawableToBitmap(Drawable drawable) {
		try {
			if (drawable == null) return null;
			
			if (drawable instanceof BitmapDrawable) {
				Bitmap bmp = ((BitmapDrawable) drawable).getBitmap();
				if (bmp != null) return bmp;
			}
			
			int width = drawable.getIntrinsicWidth();
			int height = drawable.getIntrinsicHeight();
			
			if (width <= 0) width = 100;
			if (height <= 0) height = 100;
			
			Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			bitmap.setHasAlpha(true);
			
			Canvas canvas;
			try {
				canvas = new Canvas(bitmap);
			} catch (Exception e) {
				return null;
			}
			
			drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			drawable.draw(canvas);
			
			return bitmap;
			
		} catch (OutOfMemoryError e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	public static Bitmap getRoundedBitmap(Bitmap bitmap, float radius) {
		try {
			if (bitmap == null) return null;
			
			if (!bitmap.isMutable()) {
				bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
			}
			
			if (bitmap.getWidth() * bitmap.getHeight() > 4000000) return bitmap;
			
			Bitmap output = Bitmap.createBitmap(
			bitmap.getWidth(),
			bitmap.getHeight(),
			Bitmap.Config.ARGB_8888
			);
			output.setHasAlpha(true);
			
			Canvas canvas = new Canvas(output);
			
			Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setFilterBitmap(true);
			paint.setDither(true);
			paint.setAntiAlias(true);
			
			BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
			paint.setShader(shader);
			
			RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
			canvas.drawRoundRect(rect, radius, radius, paint);
			
			return output;
			
		} catch (OutOfMemoryError e) {
			return bitmap;
		} catch (Exception e) {
			return bitmap;
		}
	}
	public static Bitmap processIcon(Drawable drawable, float radius) {
		try {
			Bitmap bmp = drawableToBitmap(drawable);
			if (bmp == null) return null;
			
			bmp = removeWhiteArea(bmp);
			bmp = getRoundedBitmap(bmp, radius);
			
			return bmp;
			
		} catch (Exception e) {
			return null;
		}
	}
	public static Bitmap removeBackgroundSmart(Bitmap src, int tolerance) {
		try {
			if (src == null) return null;
			
			if (!src.isMutable()) {
				src = src.copy(Bitmap.Config.ARGB_8888, true);
			}
			
			int width = src.getWidth();
			int height = src.getHeight();
			
			if (width * height > 4000000) return src;
			
			Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			result.setHasAlpha(true);
			
			int[] pixels = new int[width * height];
			src.getPixels(pixels, 0, width, 0, 0, width, height);
			
			int bgColor = pixels[0];
			
			int r0 = (bgColor >> 16) & 0xff;
			int g0 = (bgColor >> 8) & 0xff;
			int b0 = bgColor & 0xff;
			
			for (int i = 0; i < pixels.length; i++) {
				int pixel = pixels[i];
				
				int r = (pixel >> 16) & 0xff;
				int g = (pixel >> 8) & 0xff;
				int b = pixel & 0xff;
				
				if (Math.abs(r - r0) < tolerance &&
				Math.abs(g - g0) < tolerance &&
				Math.abs(b - b0) < tolerance) {
					
					pixels[i] = 0x00000000;
				}
			}
			
			result.setPixels(pixels, 0, width, 0, 0, width, height);
			return result;
			
		} catch (OutOfMemoryError e) {
			return src;
		} catch (Exception e) {
			return src;
		}
	}
	{
	}
	
	
	public void _searchPlugins() {
	}
	private void searchPlugins(String query) {
		String filter = query.trim().toLowerCase();
		ArrayList<HashMap<String, Object>> filteredList = new ArrayList<>();
		
		if (!filter.isEmpty()) {
			for (HashMap<String, Object> item : listMap) {
				String appName = item.get("folder").toString().toLowerCase();
				if (appName.contains(filter)) {
					filteredList.add(item);
				}
			}
		} else {
			filteredList.addAll(listMap);
		}
		
		listview1.setAdapter(new Listview1Adapter(filteredList));
		((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
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
	
	
	public void _PluginsManager() {
	}
	public class PluginManager {
		
		private Context ctx;
		private final String BASE = "/data/local/tmp/HYPERS/data/Plugins/";
		
		public PluginManager(Context c){
			this.ctx = c;
		}
		
		public ArrayList<String> getFolders(){
			ArrayList<String> list = new ArrayList<>();
			
			String res = ExecEngine.execRead("ls -d " + BASE + "*/ 2>/dev/null");
			
			if(res == null) return list;
			
			for(String s : res.split("\n")){
				if(!s.trim().isEmpty()){
					list.add(s.trim());
				}
			}
			
			return list;
		}
		
		public boolean isEnabled(String name){
			return ctx.getSharedPreferences("modules",0)
			.getBoolean("plugin_" + name, true);
		}
		
		public void setEnabled(String name, boolean val){
			ctx.getSharedPreferences("modules",0)
			.edit()
			.putBoolean("plugin_" + name, val)
			.apply();
		}
		
		public void enable(String path){
			
			String service = path + "/service.sh";
			
			ExecEngine.exec("chmod 755 \""+service+"\"");
			ExecEngine.exec("pkill -f \""+service+"\"");
			ExecEngine.exec("nohup sh \""+service+"\" > "+path+"/log.txt 2>&1 &");
		}
		
		public void disable(String path){
			ExecEngine.exec("pkill -f \""+path+"/service.sh\"");
		}
		
		public void applyAll(){
			
			for(String p : getFolders()){
				
				String name = p.substring(p.lastIndexOf("/")+1);
				
				if(isEnabled(name)){
					enable(p);
				}else{
					disable(p);
				}
			}
		}
	}
	
	private void updateItem(int pos) {
		if (pos == -1) return;
		
		int first = listview1.getFirstVisiblePosition();
		int last = listview1.getLastVisiblePosition();
		
		if (pos >= first && pos <= last) {
			View view = listview1.getChildAt(pos - first);
			if (view != null) {
				((BaseAdapter) listview1.getAdapter()).getView(pos, view, listview1);
			}
		}
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
	
	public class Listview1Adapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.plugin, null);
			}
			
			final androidx.cardview.widget.CardView cardview2 = _view.findViewById(R.id.cardview2);
			final LinearLayout linear23 = _view.findViewById(R.id.linear23);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final LinearLayout linear21 = _view.findViewById(R.id.linear21);
			final LinearLayout linear31 = _view.findViewById(R.id.linear31);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final LinearLayout linear22 = _view.findViewById(R.id.linear22);
			final LinearLayout linear26 = _view.findViewById(R.id.linear26);
			final TextView textview11 = _view.findViewById(R.id.textview11);
			final LinearLayout linear7 = _view.findViewById(R.id.linear7);
			final LinearLayout linear16 = _view.findViewById(R.id.linear16);
			final LinearLayout linear19 = _view.findViewById(R.id.linear19);
			final LinearLayout linear20 = _view.findViewById(R.id.linear20);
			final ImageView imageview2 = _view.findViewById(R.id.imageview2);
			final TextView textview7 = _view.findViewById(R.id.textview7);
			final ImageView imageview3 = _view.findViewById(R.id.imageview3);
			final TextView textview9 = _view.findViewById(R.id.textview9);
			final ImageView imageview4 = _view.findViewById(R.id.imageview4);
			final TextView textview10 = _view.findViewById(R.id.textview10);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			final Switch switch1 = _view.findViewById(R.id.switch1);
			final TextView textview3 = _view.findViewById(R.id.textview3);
			final LinearLayout linear30 = _view.findViewById(R.id.linear30);
			final LinearLayout linear8 = _view.findViewById(R.id.linear8);
			final LinearLayout linear9 = _view.findViewById(R.id.linear9);
			final LinearLayout linear14 = _view.findViewById(R.id.linear14);
			final TextView textview13 = _view.findViewById(R.id.textview13);
			final TextView textview14 = _view.findViewById(R.id.textview14);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final androidx.cardview.widget.CardView cardview3 = _view.findViewById(R.id.cardview3);
			final LinearLayout linear24 = _view.findViewById(R.id.linear24);
			final ImageView imageview5 = _view.findViewById(R.id.imageview5);
			final LinearLayout linear25 = _view.findViewById(R.id.linear25);
			final TextView textview12 = _view.findViewById(R.id.textview12);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			
			linear8.setEnabled(false);
			linear8.setAlpha(0.6f);
			linear23.setVisibility(View.GONE);
			linear19.setVisibility(View.GONE);
			linear8.setVisibility(View.GONE);
			linear30.setVisibility(View.GONE);
			linear8.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_click(linear8);
					String path = PLUGINS_DIR + _data.get(_position).get("folder").toString();
					
					new Thread(() -> {
						
						String htmlPath = htmlCache.get(path);
						
						if (htmlPath == null || htmlPath.isEmpty()) {
							
							String result = shizukuExecRead(
							"find \"" + path + "\" -name \"*.html\" | head -n 1"
							);
							
							htmlPath = (result != null) ? result.trim() : "";
							
							htmlCache.put(path, htmlPath);
						}
						
						final String finalHtmlPath = htmlPath;
						
						runOnUiThread(() -> {
							
							if (finalHtmlPath != null && !finalHtmlPath.isEmpty()) {
								
								webUi.edit()
								.putString("webUI", finalHtmlPath)
								.apply();
								webUi.edit()
								.putString("folderPath", _data.get(_position).get("folder").toString())
								.apply();
								
								Intent i = new Intent(getApplicationContext(), WebuiActivity.class);
								startActivity(i);
							}
						});
						
					}).start();
				}
			});
			imageview1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_click(linear14);
					
					LoadingBooster.show(PluginsActivity.this).size(180).color(0xFF00ACC1);
					
					new Thread(() -> {
						
						try {
							
							String tmpPlugins = _data.get(_position).get("folder").toString();
							
							
							
							String folderPath = PLUGINS_DIR + tmpPlugins;
							
							String findCmd =
							"find \"" + folderPath + "\" -type f -name uninstall.sh -print -quit 2>/dev/null";
							
							String path = execSmart(findCmd).trim();
							
							String envPath = "/data/local/tmp/HYPERS/env.sh";
							
							String mains = "cd " + folderPath + " && . " + envPath + " && sh ./" + path;
							
							shellExc.edit().putString("shell", path).apply();
							
							final String tmpFinal = tmpPlugins;
							
							runOnUiThread(() -> {
								i.setClass(getApplicationContext(), CmdexecActivity.class);
								startActivity(i);
								deleteTmpFolder("/data/local/tmp/HYPERS/data/Plugins/" + tmpFinal);
								_refresh_lost();
								LoadingBooster.hide();
							});
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}).start();
				}
			});
			linear30.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_click(linear30);
					String folderPath = PLUGINS_DIR + _data.get(_position).get("folder").toString();
					
					String findCmd =
					"find \"" + folderPath + "\" -type f -name action.sh -print -quit 2>/dev/null";
					
					String path = execSmart(findCmd).trim();
					
					String envPath = "/data/local/tmp/HYPERS/env.sh";
					
					String mainAction = "cd " + folderPath + " && . " + envPath + " && sh ./" + path;
					
					shellExc.edit().putString("shell", path).commit();
					shellExc.edit().putString("pathshell", folderPath).commit();
					i.setClass(getApplicationContext(), CmdexecActivity.class);
					startActivity(i);
				}
			});
			switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton cb, boolean isChecked) {
					boolean hasHtml = false;
					
					if (_data.get((int)_position).containsKey("hasHtml")) {
						hasHtml = (boolean) _data.get((int)_position).get("hasHtml");
					}
					
					if (hasHtml) {
						
						new Thread(() -> {
							
							try {
								JSONObject json = new JSONObject();
								json.put("status", isChecked ? "enabled" : "disable");
								
								File dir = new File(Environment.getExternalStorageDirectory(),
								"ZYREX-TOOLS/data/");
								
								if (!dir.exists()) dir.mkdirs();
								
								File file = new File(dir,
								_data.get(_position).get("folder").toString() + ".json");
								
								FileOutputStream fos = new FileOutputStream(file);
								fos.write(json.toString(1).getBytes());
								fos.close();
								
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}).start();
						
						textview9.setText(isChecked ? "WEBUI" : "Authorized");
						
					} else {
						getSharedPreferences("modules", MODE_PRIVATE)
						.edit()
						.putBoolean("plugin_" + _data.get(_position).get("folder").toString(), isChecked)
						.apply();
					}
				}});
			boolean enabled = (boolean) _data.get((int)_position).get("enabled");
			
			if (enabled) {
				switch1.setChecked(true);
			} else {
				switch1.setChecked(false);
			}
			new Thread(() -> {
				try {
					
					File filee = new File(
					Environment.getExternalStorageDirectory(),
					"ZYREX-TOOLS/data/" 
					+ _data.get(_position).get("folder").toString() 
					+ ".json"
					);
					
					if (!filee.exists()) {
						getSharedPreferences("modules", MODE_PRIVATE)
						.edit()
						.putBoolean("plugin_" + _data.get(_position).get("folder").toString(), false)
						.apply();
					}
					
					BufferedReader reader = new BufferedReader(new FileReader(filee));
					StringBuilder sb = new StringBuilder();
					String line;
					
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}
					
					reader.close();
					
					JSONObject json = new JSONObject(sb.toString().trim());
					
					String status = json.optString("status", "")
					.replace("\n", "")
					.replace("\r", "")
					.trim()
					.toLowerCase();
					
					if (status.equals("enabled")) {
						
						getSharedPreferences("modules", MODE_PRIVATE)
						.edit()
						.putBoolean("plugin_" + _data.get(_position).get("folder").toString(), true)
						.apply();
						
					} else if (status.equals("disable")) {
						
						getSharedPreferences("modules", MODE_PRIVATE)
						.edit()
						.putBoolean("plugin_" + _data.get(_position).get("folder").toString(), false)
						.apply();
						
					} else {
						
						getSharedPreferences("modules", MODE_PRIVATE)
						.edit()
						.putBoolean("plugin_" + _data.get(_position).get("folder").toString(), false)
						.apply();
					}
					
				} catch (Exception e) {
					
					
					
				}
			}).start();
			String folderPath = PLUGINS_DIR + _data.get(_position).get("folder").toString();
			
			String cmd =
			"find \"" + folderPath + "\" -type f -name action.sh 2>/dev/null | head -n 1";
			
			String result = execSmart(cmd).trim();
			
			if (!result.isEmpty()) {
				linear30.setVisibility(View.VISIBLE);
			} else {
				linear30.setVisibility(View.GONE);
			}
			new Thread(() -> {
				try {
					// 1. Ambil Path File
					String folderName = _data.get((int)_position).get("folder").toString();
					File filee = new File(Environment.getExternalStorageDirectory(), "ZYREX-TOOLS/data/.users/" + folderName + ".json");
					
					final String statusResult;
					
					if (filee.exists()) {
						// 2. Baca File JSON
						BufferedReader reader = new BufferedReader(new FileReader(filee));
						StringBuilder sb = new StringBuilder();
						String line;
						while ((line = reader.readLine()) != null) { sb.append(line); }
						reader.close();
						
						JSONObject json = new JSONObject(sb.toString().trim());
						statusResult = json.optString("status", "").trim().toLowerCase();
					} else {
						statusResult = "disable";
					}
					
					// 3. Gabungkan Update UI langsung di sini
					new Handler(Looper.getMainLooper()).post(() -> {
						if (statusResult.equals("enabled")) {
							linear8.setEnabled(true);
							linear19.setEnabled(false);
							linear8.setAlpha(1.0f);
							textview9.setText("WebUI");
							textview9.setTextColor(0xFFF5F5F5);
							switch1.setChecked(true);
						} else {
							linear8.setEnabled(false);
							linear19.setEnabled(true);
							linear8.setAlpha(0.6f);
							textview9.setText("Authorized");
							textview9.setTextColor(0xFFF5F5F5);
							switch1.setChecked(false);
						}
					});
					
				} catch (Exception e) {
					new Handler(Looper.getMainLooper()).post(() -> {
						linear8.setEnabled(false);
						switch1.setChecked(false);
						textview9.setText("Error Load");
					});
				}
			}).start();
			
			boolean hasHtml = false;
			
			if (_data.get((int)_position).containsKey("hasHtml")) {
				hasHtml = (boolean) _data.get((int)_position).get("hasHtml");
			}
			
			if (hasHtml) {
				linear19.setVisibility(View.VISIBLE); 
				linear8.setVisibility(View.VISIBLE);
				
				
			} else {
				
				linear19.setVisibility(View.GONE);
				linear8.setVisibility(View.GONE);
				
			}
			String pluginsPath = PLUGINS_DIR;
			File pluginsDir = new File(pluginsPath);
			
			if (pluginsDir.exists() && pluginsDir.isDirectory()) {
				File[] folders = pluginsDir.listFiles(File::isDirectory); 
				if (folders != null && folders.length > 0) {
					cardview2.setVisibility(View.VISIBLE);
				} else {
					cardview2.setVisibility(View.GONE);
				}
			} else {
				cardview2.setVisibility(View.GONE);
			}
			String size = "0 B";
			
			if (_data.get((int)_position).containsKey("size")) {
				size = _data.get((int)_position).get("size").toString();
			}
			
			textview7.setText(size);
			try {
				
				String folder = "";
				if (_data != null && _data.size() > _position) {
					Object folderObj = _data.get((int)_position).get("folder");
					if (folderObj != null) folder = folderObj.toString();
				}
				
				String folderSpath = PLUGINS_DIR + folder;
				
				String banner = "";
				
				
				String propFile = folderSpath + "/hypers.prop";
				
				String content = "";
				
				File propCheck = new File(propFile);
				if (propCheck.exists()) {
					
					String tmp = shizukuExecRead("cat \"" + propFile + "\"");
					if (tmp != null) content = tmp;
					
				}
				
				
				if (!content.isEmpty()) {
					
					for (String line : content.split("\n")) {
						
						if (line == null) continue;
						
						line = line.trim().replace("\r", "");
						if (line.isEmpty() || line.startsWith("#")) continue;
						
						if (line.contains("=")) {
							
							String[] parts = line.split("=", 2);
							if (parts.length < 2) continue;
							
							String key = parts[0].trim();
							String value = parts[1].trim();
							
							if (key.equalsIgnoreCase("banner")) {
								banner = value;
							}
						}
					}
				}
				
				
				if (!banner.isEmpty()) {
					
					String pluginsRoot = PLUGINS_DIR + folder;
					
					String foundPathRaw = shizukuExecRead(
					"find \"" + pluginsRoot + "\" -type f -iname \"" + banner + "\" | head -n 1"
					);
					
					String foundPath = (foundPathRaw != null) ? foundPathRaw.trim() : "";
					
					if (!foundPath.isEmpty()) {
						
						PluginsImage.applyImageBackground(
						getApplicationContext(),
						linear21,
						foundPath
						);
					}
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			new Thread(() -> {
				
				String id = "Unknown";
				String name = "Unknown";
				String version = "0";
				String versionCode = "0";
				String author = "Unknown";
				String description = "No description";
				String main = "";
				
				String folder = "";
				
				if (_data != null && _data.size() > _position) {
					Object folderObj = _data.get((int)_position).get("folder");
					if (folderObj != null) {
						folder = folderObj.toString();
					}
				}
				
				String folderSpath = PLUGINS_DIR + folder;
				
				
				String propFile = folderSpath + "/hypers.prop";
				
				String content = "";
				
				File file = new File(propFile);
				if (file.exists()) {
					String tmp = shizukuExecRead("cat \"" + propFile + "\"");
					if (tmp != null) content = tmp;
				}
				
				
				if (!content.isEmpty()) {
					
					for (String line : content.split("\n")) {
						
						if (line == null) continue;
						
						line = line.trim().replace("\r", "");
						if (line.isEmpty() || line.startsWith("#")) continue;
						
						if (line.contains("=")) {
							
							String[] parts = line.split("=", 2);
							if (parts.length < 2) continue;
							
							String key = parts[0].trim();
							String value = parts[1].trim();
							
							switch (key) {
								
								case "id":
								id = value;
								break;
								
								case "name":
								name = value;
								break;
								
								case "version":
								version = value;
								break;
								
								case "versionCode":
								versionCode = value;
								break;
								
								case "author":
								author = value;
								break;
								
								case "description":
								description = value;
								break;
								
								case "main":
								main = value;
								break;
							}
						}
					}
				}
				
				String finalId = id;
				String finalName = name;
				String finalVersion = version;
				String finalVersionCode = versionCode;
				String finalAuthor = author;
				String finalDescription = description;
				
				runOnUiThread(() -> {
					
					textview1.setText(finalId);
					textview2.setText(finalName);
					
					String combined =
					"ID: " + finalId + "\n" +
					"Version: " + finalVersion + "\n" +
					"VersionCode: " + finalVersionCode + "\n" +
					"Author: " + finalAuthor + "\n" +
					"pluginsBy: HYPERS-MANAGER";
					
					textview3.setText(combined);
					textview11.setText(finalDescription);
					
				});
				
			}).start();
			linear5.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)30, (int)0, Color.TRANSPARENT, 0x70BF360C));
			linear8.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)30, (int)0, Color.TRANSPARENT, 0xFF1A1A1A));
			linear16.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF212121));
			linear19.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF212121));
			linear20.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF212121));
			linear14.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)35, (int)0, Color.TRANSPARENT, 0xFF1A1A1A));
			linear30.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)30, (int)0, Color.TRANSPARENT, 0xFF1A1A1A));
			textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffbold.ttf"), 1);
			textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffregular.ttf"), 1);
			textview11.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffbold.ttf"), 1);
			linear31.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int oldPosition = selectedPosition;
					
					if (selectedPosition == _position) {
						selectedPosition = -1;
					} else {
						selectedPosition = _position;
					}
					
					updateItem(oldPosition);
					updateItem(_position);
					v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
				}
			});
			int targetHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());
			
			if (_position == selectedPosition) {
				if (linear7.getVisibility() == View.GONE || linear7.getLayoutParams().height == 0) {
					linear7.setVisibility(View.VISIBLE);
					ValueAnimator anim = ValueAnimator.ofInt(0, targetHeight);
					anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator valueAnimator) {
							int val = (Integer) valueAnimator.getAnimatedValue();
							ViewGroup.LayoutParams layoutParams = linear7.getLayoutParams();
							layoutParams.height = val;
							linear7.setLayoutParams(layoutParams);
						}
					});
					anim.setDuration(300);
					anim.setInterpolator(new DecelerateInterpolator());
					anim.start();
					
					linear7.animate().alpha(1f).setDuration(300).start();
					cardview2.setCardElevation(20f);
				}
			} else {
				if (linear7.getLayoutParams().height > 0) {
					ValueAnimator anim = ValueAnimator.ofInt(linear7.getLayoutParams().height, 0);
					anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator valueAnimator) {
							int val = (Integer) valueAnimator.getAnimatedValue();
							ViewGroup.LayoutParams layoutParams = linear7.getLayoutParams();
							layoutParams.height = val;
							linear7.setLayoutParams(layoutParams);
						}
					});
					anim.setDuration(250);
					anim.setInterpolator(new AccelerateInterpolator());
					anim.addListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							linear7.setVisibility(View.GONE);
						}
					});
					anim.start();
					
					linear7.animate().alpha(0f).setDuration(200).start();
					cardview2.setCardElevation(2f);
				} else {
					linear7.setVisibility(View.GONE);
					ViewGroup.LayoutParams lp = linear7.getLayoutParams();
					lp.height = 0;
					linear7.setLayoutParams(lp);
					linear7.setAlpha(0f);
				}
			}
			linear19.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_click(linear19);
					LoadingBooster.show(PluginsActivity.this)
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
										
										try {
											
											JSONObject json = new JSONObject();
											json.put("status", "enabled");
											
											File dbDir = new File(
											Environment.getExternalStorageDirectory(),
											"ZYREX-TOOLS/data/.users/"
											);
											
											if (!dbDir.exists()) dbDir.mkdirs();
											
											
											
											File dbFile = new File(dbDir, _data.get(_position).get("folder").toString() + ".json");
											
											FileOutputStream fos = new FileOutputStream(dbFile);
											fos.write(json.toString(1).getBytes());
											fos.close();
											
										} catch (Exception ex) {
											Log.e("DB", "JSON error: " + ex.getMessage());
										}
										LoadingBooster.hide();
										textview9.setText("WebUI");
										textview9.setTextColor(0xFFF5F5F5);
										switch1.setChecked(true);
										((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							});
						}
					};
					
					
					_timer.schedule(timeLaunch, 2000L);
				}
			});
			
			return _view;
		}
	}
}
