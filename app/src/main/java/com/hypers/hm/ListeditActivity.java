package com.hypers.hm;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
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
import com.bumptech.glide.*;
import com.cocode.focora.*;
import com.droidx.*;
import com.facebook.shimmer.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
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
import android.content.ClipboardManager;
import android.content.ClipData;

public class ListeditActivity extends AppCompatActivity {
	
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
	private String ceemde = "";
	private String val = "";
	private String property = "";
	private String delproperty = "";
	
	private ArrayList<HashMap<String, Object>> listSystem = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> listGlobal = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> listSecure = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> listProp = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private ImageView imageview1;
	private TextView textview1;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private ImageView imageview8;
	private EditText edittext1;
	private LinearLayout linear7;
	private LinearLayout linear10;
	private TextView textview2;
	private ImageView imageview9;
	private TextView textview4;
	private ImageView imageview11;
	private LinearLayout l1;
	private LinearLayout l2;
	private LinearLayout l3;
	private LinearLayout l4;
	private ListView listview1;
	private ListView listview2;
	private ListView listview3;
	private ListView listview4;
	
	private SharedPreferences setEdit;
	private SharedPreferences cmddd;
	private SharedPreferences commandSet;
	private AlertDialog.Builder d;
	private SharedPreferences xmd;
	private AlertDialog.Builder bt;
	private Intent i = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.listedit);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
		linear5 = findViewById(R.id.linear5);
		linear6 = findViewById(R.id.linear6);
		imageview8 = findViewById(R.id.imageview8);
		edittext1 = findViewById(R.id.edittext1);
		linear7 = findViewById(R.id.linear7);
		linear10 = findViewById(R.id.linear10);
		textview2 = findViewById(R.id.textview2);
		imageview9 = findViewById(R.id.imageview9);
		textview4 = findViewById(R.id.textview4);
		imageview11 = findViewById(R.id.imageview11);
		l1 = findViewById(R.id.l1);
		l2 = findViewById(R.id.l2);
		l3 = findViewById(R.id.l3);
		l4 = findViewById(R.id.l4);
		listview1 = findViewById(R.id.listview1);
		listview2 = findViewById(R.id.listview2);
		listview3 = findViewById(R.id.listview3);
		listview4 = findViewById(R.id.listview4);
		setEdit = getSharedPreferences("setEdit", Activity.MODE_PRIVATE);
		cmddd = getSharedPreferences("cmddd", Activity.MODE_PRIVATE);
		commandSet = getSharedPreferences("commandSet", Activity.MODE_PRIVATE);
		d = new AlertDialog.Builder(this);
		xmd = getSharedPreferences("xmd", Activity.MODE_PRIVATE);
		bt = new AlertDialog.Builder(this);
		
		imageview1.setOnClickListener(_v -> finish());
		
		linear7.setOnClickListener(_v -> {
			String[] options = {"Root", "Non Root"};
			
			AlertDialog.Builder builder = new AlertDialog.Builder(ListeditActivity.this);
			builder.setTitle("Select Akses (Hypers Manager)");
			
			builder.setItems(options, (dialog, which) -> {
				String selected = options[which];
				
				commandSet.edit().putString("akses", selected).apply();
				
				
				textview2.setText(selected);
				
				
				switch (selected) {
					case "Root":
					new Thread(() -> {
						access = false;
					}).start();
					break;
					case "Non Root":
					new Thread(() -> {
						access = true;
					}).start();
					break;
				}
				
				dialog.dismiss();
			});
			
			builder.show();
		});
		
		linear10.setOnClickListener(_v -> {
			String[] options = {"SystemTABLE", "GlobalTABLE", "SecureTABLE", "PropTABLE"};
			
			AlertDialog.Builder builder = new AlertDialog.Builder(ListeditActivity.this);
			builder.setTitle("Select Table COMMANDS SETTINGS (Hypers Manager)");
			
			builder.setItems(options, (dialog, which) -> {
				String selected = options[which];
				
				commandSet.edit().putString("mode", selected).apply();
				
				
				textview4.setText(selected);
				
				
				switch (selected) {
					case "SystemTABLE":
					l1.setVisibility(View.VISIBLE);
					l2.setVisibility(View.GONE);
					l3.setVisibility(View.GONE);
					l4.setVisibility(View.GONE);
					break;
					case "GlobalTABLE":
					l2.setVisibility(View.VISIBLE);
					l1.setVisibility(View.GONE);
					l3.setVisibility(View.GONE);
					l4.setVisibility(View.GONE);
					break;
					case "SecureTABLE":
					l3.setVisibility(View.VISIBLE);
					l2.setVisibility(View.GONE);
					l1.setVisibility(View.GONE);
					l4.setVisibility(View.GONE);
					break;
					case "PropTABLE":
					l4.setVisibility(View.VISIBLE);
					l2.setVisibility(View.GONE);
					l3.setVisibility(View.GONE);
					l1.setVisibility(View.GONE);
					break;
				}
				
				dialog.dismiss();
			});
			
			builder.show();
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
		
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#131313")));
		linear5.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)0, Color.TRANSPARENT, 0xFF1A1A1A));
		linear7.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)30, (int)0, Color.TRANSPARENT, 0xFF212121));
		linear10.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)30, (int)0, Color.TRANSPARENT, 0xFF212121));
		_sizetetap();
		_AksesShizuku();
		listview1.setAdapter(new Listview1Adapter(listSystem));
		listview2.setAdapter(new Listview2Adapter(listGlobal));
		listview3.setAdapter(new Listview3Adapter(listSecure));
		listview4.setAdapter(new Listview4Adapter(listProp));
		_listsystem();
		_listglobal();
		_listsecure();
		_listprop();
		textview2.setText(commandSet.getString("akses", "Non Root"));
		textview4.setText(commandSet.getString("mode", ""));
		
		
		
		String mode = commandSet.getString("mode", "");
		
		TextWatcher searchWatcher = new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				String mode = commandSet.getString("mode", "");
				String filter = s.toString().toLowerCase().trim();
				
				ArrayList<HashMap<String, Object>> sourceList = new ArrayList<>();
				ArrayList<HashMap<String, Object>> originalList = new ArrayList<>();
				BaseAdapter adapter = null;
				
				if (mode.equals("SystemTABLE")) {
					originalList = listSystemFull;   
					sourceList = listSystem;        
					adapter = (BaseAdapter) listview1.getAdapter();
					
				} else if (mode.equals("GlobalTABLE")) {
					originalList = listGlobalFull;
					sourceList = listGlobal;
					adapter = (BaseAdapter) listview2.getAdapter();
					
				} else if (mode.equals("SecureTABLE")) {
					originalList = listSecureFull;
					sourceList = listSecure;
					adapter = (BaseAdapter) listview3.getAdapter();
					
				} else if (mode.equals("PropTABLE")) {
					originalList = listPropFull;
					sourceList = listProp;
					adapter = (BaseAdapter) listview4.getAdapter();
				}
				
				ArrayList<HashMap<String, Object>> filtered = new ArrayList<>();
				
				if (filter.isEmpty()) {
					filtered.addAll(originalList);
				} else {
					for (HashMap<String, Object> item : originalList) {
						
						Object obj = item.get("name");
						if (obj != null) {
							String name = obj.toString().toLowerCase();
							
							if (name.contains(filter)) {
								filtered.add(item);
							}
						}
					}
				}
				
				sourceList.clear();
				sourceList.addAll(filtered);
				
				if (adapter != null) {
					adapter.notifyDataSetChanged();
				}
			}
			
			@Override
			public void afterTextChanged(Editable s) {}
		};
		
		edittext1.addTextChangedListener(searchWatcher);
		if (commandSet.getString("akses", "").equals("Root")) access = false; else access = true;
		if (commandSet.getString("akses", "").equals("Non Root")) access = true; else access = false;
		if (commandSet.getString("mode", "").equals("SystemTABLE")) {
			l1.setVisibility(View.VISIBLE);
			l2.setVisibility(View.GONE);
			l3.setVisibility(View.GONE);
			l4.setVisibility(View.GONE);
		} else {
			if (commandSet.getString("mode", "").equals("GlobalTABLE")) {
				l2.setVisibility(View.VISIBLE);
				l1.setVisibility(View.GONE);
				l3.setVisibility(View.GONE);
				l4.setVisibility(View.GONE);
			} else {
				if (commandSet.getString("mode", "").equals("SecureTABLE")) {
					l3.setVisibility(View.VISIBLE);
					l1.setVisibility(View.GONE);
					l2.setVisibility(View.GONE);
					l4.setVisibility(View.GONE);
				} else {
					if (commandSet.getString("mode", "").equals("PropTABLE")) {
						l4.setVisibility(View.VISIBLE);
						l1.setVisibility(View.GONE);
						l2.setVisibility(View.GONE);
						l3.setVisibility(View.GONE);
					}
				}
			}
		}
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		_listsystem();
		_listglobal();
		_listsecure();
		_listprop();
		if (virtualConfig != null && virtualMetrics != null) {
			getResources().updateConfiguration(virtualConfig, virtualMetrics);
		}
		textview2.setText(commandSet.getString("akses", "Non Root"));
		textview4.setText(commandSet.getString("mode", ""));
		if (commandSet.getString("akses", "").equals("Root")) access = false; else access = true;
		if (commandSet.getString("akses", "").equals("Non Root")) access = true; else access = false;
		if (commandSet.getString("mode", "").equals("SystemTABLE")) {
			l1.setVisibility(View.VISIBLE);
			l2.setVisibility(View.GONE);
			l3.setVisibility(View.GONE);
			l4.setVisibility(View.GONE);
		} else {
			if (commandSet.getString("mode", "").equals("GlobalTABLE")) {
				l2.setVisibility(View.VISIBLE);
				l1.setVisibility(View.GONE);
				l3.setVisibility(View.GONE);
				l4.setVisibility(View.GONE);
			} else {
				if (commandSet.getString("mode", "").equals("SecureTABLE")) {
					l3.setVisibility(View.VISIBLE);
					l1.setVisibility(View.GONE);
					l2.setVisibility(View.GONE);
					l4.setVisibility(View.GONE);
				} else {
					if (commandSet.getString("mode", "").equals("PropTABLE")) {
						l4.setVisibility(View.VISIBLE);
						l1.setVisibility(View.GONE);
						l2.setVisibility(View.GONE);
						l3.setVisibility(View.GONE);
					}
				}
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		animateExit();
	}
	public void _listsystem() {
		
		new Thread(() -> {
			String system = shizukuExec("settings list system");
			String[] lines = system.split("\n");
			ArrayList<HashMap<String,Object>> newListSystem = new ArrayList<>();
			for (String line : lines) {
				
				try {
					
					if (line.contains("=")) {
						String[] parts = line.split("=", 2);
						
						HashMap<String, Object> map = new HashMap<>();
						map.put("name", parts[0]);
						map.put("value", parts.length > 1 ? parts[1] : "");
						
						newListSystem.add(map);
						
					}
				} catch (Exception ignored) {}
			}
			
			runOnUiThread(() -> {
				listSystem.clear();
				listSystem.addAll(newListSystem); ((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			});
			
		}).start();
	}
	
	
	public void _listglobal() {
		
		new Thread(() -> {
			String global = shizukuExec("settings list global");
			String[] lines = global.split("\n");
			
			ArrayList<HashMap<String,Object>> newListGlobal = new ArrayList<>();
			
			for (String line : lines) {
				
				try {
					
					if (line.contains("=")) {
						String[] parts = line.split("=", 2);
						
						HashMap<String, Object> map = new HashMap<>();
						map.put("name", parts[0]);
						map.put("value", parts.length > 1 ? parts[1] : "");
						
						newListGlobal.add(map);
						
					}
				} catch (Exception ignored) {}
			}
			
			runOnUiThread(() -> {
				listGlobal.clear();
				listGlobal.addAll(newListGlobal); ((BaseAdapter)listview2.getAdapter()).notifyDataSetChanged();
			});
			
		}).start();
	}
	
	
	public void _listsecure() {
		
		
		new Thread(() -> {
			String secure = shizukuExec("settings list secure");
			String[] lines = secure.split("\n");
			ArrayList<HashMap<String,Object>> newListSecure = new ArrayList<>();
			for (String line : lines) {
				
				try {
					
					if (line.contains("=")) {
						String[] parts = line.split("=", 2);
						
						HashMap<String, Object> map = new HashMap<>();
						map.put("name", parts[0]);
						map.put("value", parts.length > 1 ? parts[1] : "");
						
						newListSecure.add(map);
						
					}
				} catch (Exception ignored) {}
			}
			
			runOnUiThread(() -> {
				listSecure.clear();
				listSecure.addAll(newListSecure);
				((BaseAdapter)listview3.getAdapter()).notifyDataSetChanged();
			});
			
		}).start();
	}
	
	
	public void _listprop() {
		
		
		new Thread(() -> {
			String prop = shizukuExec("getprop");
			String[] lines = prop.split("\n");
			ArrayList<HashMap<String,Object>> newListProp = new ArrayList<>();
			for (String line : lines) {
				
				try {
					
					if (line.contains("[") && line.contains("]")) {
						String key = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
						String value = line.substring(line.lastIndexOf("[") + 1, line.lastIndexOf("]"));
						
						HashMap<String, Object> map = new HashMap<>();
						map.put("name", key);
						map.put("value", value);
						
						newListProp.add(map);
					}
				} catch (Exception ignored) {}
			}
			
			runOnUiThread(() -> {
				listProp.clear();
				listProp.addAll(newListProp);
				((BaseAdapter)listview4.getAdapter()).notifyDataSetChanged();
			});
			
		}).start();
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
	private boolean access = true;
	private ArrayList<HashMap<String, Object>> listSystemFull = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> listGlobalFull = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> listSecureFull = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> listPropFull = new ArrayList<>();
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
	
	
	public void _clickItem() {
		final com.google.android.material.bottomsheet.BottomSheetDialog bt = new com.google.android.material.bottomsheet.BottomSheetDialog(ListeditActivity.this);
		View inflate = getLayoutInflater().inflate(R.layout.set, null);
		bt.setContentView(inflate);
		bt.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
		LinearLayout blinear1 = (LinearLayout)
		inflate.findViewById(R.id.blinear1);
		LinearLayout blinear2 = (LinearLayout)
		inflate.findViewById(R.id.blinear2);
		LinearLayout blinear4 = (LinearLayout)
		inflate.findViewById(R.id.blinear4);
		LinearLayout blinear5 = (LinearLayout)
		inflate.findViewById(R.id.blinear5);
		LinearLayout blinear7 = (LinearLayout)
		inflate.findViewById(R.id.blinear7);
		blinear1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)0, 0x701976D2, 0xFF1A1A1A));
		blinear7.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)50, (int)0, 0x701976D2, 0xFF00ACC1));
		ceemde = xmd.getString("cm-d", "");
		val = xmd.getString("values", "");
		property = xmd.getString("properties", "");
		delproperty = xmd.getString("delproperties", "");
		blinear2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
				ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				String text = property + " " + ceemde + val;
				clipboard.setPrimaryClip(ClipData.newPlainText("clipboard", text));
			}
		});
		blinear4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				d.setTitle("change system command settings");
				d.setMessage(ceemde);
				final EditText 
				
				
				edittext
				
				
				= new EditText(
				
				
				ListeditActivity
				
				
				.this);
				LinearLayout.LayoutParams lpar = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				
				
				
				edittext.setLayoutParams(lpar);
				edittext.setText(val);
				
				
				d.setView(edittext);
				d.setPositiveButton("Set", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
						execSmart(property + " " + ceemde + " " +  edittext.getText().toString());
						SketchwareUtil.showMessage(getApplicationContext(), "Success Set " + ceemde);
					}
				});
				d.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				d.create().show();
			}
		});
		blinear5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				execSmart(delproperty);
				SketchwareUtil.showMessage(getApplicationContext(), "Success Set " + ceemde);
			}
		});
		
		bt.setCancelable(true);
		bt.show();
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
		} else if (isShizukuAvailable()) {
			return shizukuExec(cmd);
		} else {
			return "No root / Shizuku not available";
		}
	}
	{
	}
	
	
	public void _onBack() {
	}
	private void animateExit() {
		final View decor = getWindow().getDecorView();
		
		decor.animate()
		.alpha(0.5f)         
		.translationX(300f)   
		.setDuration(150)    
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
				_view = _inflater.inflate(R.layout.setedit, null);
			}
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			
			linear1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)0, Color.TRANSPARENT, 0xFF1A1A1A));
			linear1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					xmd.edit().putString("cm-d", _data.get((int) _position).get("name").toString()).commit();
					xmd.edit().putString("values", _data.get((int) _position).get("value").toString()).commit();
					xmd.edit().putString("properties", "settings put global").commit();
					xmd.edit().putString("delproperties", "settings delete system " + _data.get((int)_position).get("name").toString()).commit();
					
					i.setClass(getApplicationContext(), EditActivity.class);
					startActivity(i);
				}
			});
			textview1.setText("\"" + _data.get((int)_position).get("name").toString() + "\"");
			textview2.setText("\"" + _data.get((int)_position).get("value").toString() + "\"");
			
			return _view;
		}
	}
	
	public class Listview2Adapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Listview2Adapter(ArrayList<HashMap<String, Object>> _arr) {
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
				_view = _inflater.inflate(R.layout.setedit, null);
			}
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			
			linear1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)0, Color.TRANSPARENT, 0xFF1A1A1A));
			linear1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					xmd.edit().putString("cm-d", _data.get((int) _position).get("name").toString()).commit();
					xmd.edit().putString("values", _data.get((int) _position).get("value").toString()).commit();
					xmd.edit().putString("properties", "settings put global").commit();
					xmd.edit().putString("delproperties", "settings delete global " + _data.get((int)_position).get("name").toString()).commit();
					
					i.setClass(getApplicationContext(), EditActivity.class);
					startActivity(i);
				}
			});
			textview1.setText("\"" + _data.get((int)_position).get("name").toString() + "\"");
			textview2.setText("\"" + _data.get((int)_position).get("value").toString() + "\"");
			
			return _view;
		}
	}
	
	public class Listview3Adapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Listview3Adapter(ArrayList<HashMap<String, Object>> _arr) {
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
				_view = _inflater.inflate(R.layout.setedit, null);
			}
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			
			linear1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)0, Color.TRANSPARENT, 0xFF1A1A1A));
			linear1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					xmd.edit().putString("cm-d", _data.get((int) _position).get("name").toString()).commit();
					xmd.edit().putString("values", _data.get((int) _position).get("value").toString()).commit();
					xmd.edit().putString("properties", "settings put global").commit();
					xmd.edit().putString("delproperties", "settings delete secure " + _data.get((int)_position).get("name").toString()).commit();
					
					i.setClass(getApplicationContext(), EditActivity.class);
					startActivity(i);
				}
			});
			textview1.setText("\"" + _data.get((int)_position).get("name").toString() + "\"");
			textview2.setText("\"" + _data.get((int)_position).get("value").toString() + "\"");
			
			return _view;
		}
	}
	
	public class Listview4Adapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Listview4Adapter(ArrayList<HashMap<String, Object>> _arr) {
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
				_view = _inflater.inflate(R.layout.setedit, null);
			}
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			
			linear1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)0, Color.TRANSPARENT, 0xFF1A1A1A));
			linear1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					xmd.edit().putString("cm-d", _data.get((int) _position).get("name").toString()).commit();
					xmd.edit().putString("values", _data.get((int) _position).get("value").toString()).commit();
					xmd.edit().putString("properties", "setprop").commit();
					xmd.edit().putString("delproperties", "setprop " + _data.get((int)_position).get("name").toString() + " " + "").commit();
					
					i.setClass(getApplicationContext(), EditActivity.class);
					startActivity(i);
				}
			});
			textview1.setText("\"" + _data.get((int)_position).get("name").toString() + "\"");
			textview2.setText("\"" + _data.get((int)_position).get("value").toString() + "\"");
			
			return _view;
		}
	}
}