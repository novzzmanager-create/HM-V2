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
import android.widget.ProgressBar;
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
import rikka.shizuku.api.*;
import rikka.shizuku.provider.*;
import rikka.shizuku.Shizuku;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import java.lang.Process;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.content.pm.ApplicationInfo;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import android.content.Context;
import android.app.NotificationManager;
import android.Manifest;
import android.provider.Settings;

import android.os.CountDownTimer;

import com.hypers.hm.service.FloatingService;
import com.hypers.hm.service.ProBoosterService;
import com.hypers.hm.debug.ADB;



public class GamesFragmentActivity extends Fragment {
	
	private Timer _timer = new Timer();
	
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
	private HashMap<String, Object> map = new HashMap<>();
	private String packag = "";
	private String pkg = "";
	
	private ArrayList<String> list_app_name = new ArrayList<>();
	private ArrayList<String> list_app_package = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	
	private LinearLayout Apps;
	private LinearLayout linear100;
	private LinearLayout linear45;
	private LinearLayout linear98;
	private LinearLayout linear102;
	private SwipeRefreshLayout swiperefreshlayout1;
	private ProgressBar progressbar;
	private LinearLayout linear101;
	private ImageView imageview1;
	private EditText edittext1;
	private LinearLayout linear103;
	private TextView textview58;
	private LinearLayout linear107;
	private LinearLayout linear106;
	private TextView textview59;
	private ImageView imageview3;
	private LinearLayout linear104;
	private LinearLayout linear105;
	private ListView listview1;
	private ImageView imageview2;
	private TextView textview60;
	
	private Intent i = new Intent();
	private SharedPreferences user;
	private SharedPreferences ppkg;
	private TimerTask timeLaunch;
	private SharedPreferences totalApp;
	private SharedPreferences modes;
    private Handler _mainHandler = new Handler(Looper.getMainLooper());
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.games_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		Apps = _view.findViewById(R.id.Apps);
		linear100 = _view.findViewById(R.id.linear100);
		linear45 = _view.findViewById(R.id.linear45);
		linear98 = _view.findViewById(R.id.linear98);
		linear102 = _view.findViewById(R.id.linear102);
		swiperefreshlayout1 = _view.findViewById(R.id.swiperefreshlayout1);
		progressbar = _view.findViewById(R.id.progressbar);
		linear101 = _view.findViewById(R.id.linear101);
		imageview1 = _view.findViewById(R.id.imageview1);
		edittext1 = _view.findViewById(R.id.edittext1);
		linear103 = _view.findViewById(R.id.linear103);
		textview58 = _view.findViewById(R.id.textview58);
		linear107 = _view.findViewById(R.id.linear107);
		linear106 = _view.findViewById(R.id.linear106);
		textview59 = _view.findViewById(R.id.textview59);
		imageview3 = _view.findViewById(R.id.imageview3);
		linear104 = _view.findViewById(R.id.linear104);
		linear105 = _view.findViewById(R.id.linear105);
		listview1 = _view.findViewById(R.id.listview1);
		imageview2 = _view.findViewById(R.id.imageview2);
		textview60 = _view.findViewById(R.id.textview60);
		user = getContext().getSharedPreferences("user", Activity.MODE_PRIVATE);
		ppkg = getContext().getSharedPreferences("ppkg", Activity.MODE_PRIVATE);
		totalApp = getContext().getSharedPreferences("totalApp", Activity.MODE_PRIVATE);
		modes = getContext().getSharedPreferences("modes", Activity.MODE_PRIVATE);
		
		swiperefreshlayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				_$Load$();
				swiperefreshlayout1.setRefreshing(false);
			}
		});
		
		linear106.setOnClickListener(_v -> {
			_click(linear106);
			i.setClass(getContext().getApplicationContext(), AddedActivity.class);
			startActivity(i);
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
	}
	
	private void initializeLogic() {
		progressbar.setVisibility(View.GONE);
		linear105.setVisibility(View.VISIBLE);
		final Context ctx = getContext();
		final Activity act = getActivity();
		new Thread(new Runnable() {
			@Override
			public void run() {
				_$Load$();
			}
		}).start();
		edittext1.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// Tidak perlu pakai ini
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// Ini mirip onQueryTextChange
				filterApps(s.toString());
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// Tidak perlu pakai ini
			}
		});
		listview1.setScrollingCacheEnabled(false);
		listview1.setAnimationCacheEnabled(false);
		listview1.setHasTransientState(false);
		try {
			java.io.File folder = new File(Environment.getExternalStorageDirectory(),
			"ZYREX-TOOLS/data/Installed/");
			
			if (!folder.exists() || !folder.isDirectory()) {
				textview59.setVisibility(View.GONE);
				linear105.setVisibility(View.VISIBLE);
				return;
			}
			
			File[] files = folder.listFiles();
			
			if (files == null) {
				textview59.setVisibility(View.VISIBLE);
				linear105.setVisibility(View.GONE);
				textview59.setText("0 Active");
				return;
			}
			
			int count = 0;
			
			for (File file : files) {
				if (file.isFile() && file.getName().toLowerCase().endsWith(".json")) {
					count++;
				}
			}
			textview59.setVisibility(View.VISIBLE);
			linear105.setVisibility(View.GONE);
			textview59.setText(count + " Active");
			
		} catch (Exception e) {
			linear105.setVisibility(View.VISIBLE);
			textview59.setVisibility(View.GONE);
		}
		_apps();
		_font();
		_sizetetap();
		int status;
		
		if (!Shizuku.pingBinder()) {
			status = 0;
		} else if (Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
			status = 1;
		} else {
			status = 2;
		}
		
		if (status == 0) {
			
			
			
		} else if (status == 1) {
			
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					_$Load$();
				}
			}).start();
			
			
			
		} else {
			
			
		}
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		try {
			java.io.File folder = new File(Environment.getExternalStorageDirectory(),
			"ZYREX-TOOLS/data/Installed/");
			
			if (!folder.exists() || !folder.isDirectory()) {
				textview59.setVisibility(View.GONE);
				linear105.setVisibility(View.VISIBLE);
				return;
			}
			
			File[] files = folder.listFiles();
			
			if (files == null) {
				textview59.setVisibility(View.VISIBLE);
				linear105.setVisibility(View.GONE);
				textview59.setText("0 Active");
				return;
			}
			
			int count = 0;
			
			for (File file : files) {
				if (file.isFile() && file.getName().toLowerCase().endsWith(".json")) {
					count++;
				}
			}
			textview59.setVisibility(View.VISIBLE);
			linear105.setVisibility(View.GONE);
			textview59.setText(count + " Active");
			
		} catch (Exception e) {
			linear105.setVisibility(View.VISIBLE);
			textview59.setVisibility(View.GONE);
		}
		int status;
		
		if (!Shizuku.pingBinder()) {
			status = 0;
		} else if (Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
			status = 1;
		} else {
			status = 2;
		}
		
		if (status == 0) {
			
			
			
		} else if (status == 1) {
			
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					_$Load$();
				}
			}).start();
			
			
			
		} else {
			
			
		}
	}
	public void _$Load$() {
		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
				if (!Environment.isExternalStorageManager()) {
					getActivity().runOnUiThread(() -> {
						SketchwareUtil.showMessage(getContext(), "Butuh Izin Akses Semua File!");
					});
					return; 
				}
			}
			
			File dirs = new File(Environment.getExternalStorageDirectory() + "/ZYREX-TOOLS/data/Installed/");
			if (!dirs.exists()) dirs.mkdirs();
			
			listmap.clear();
			File[] files = dirs.listFiles();
			
			if (files == null || files.length == 0) {
				getActivity().runOnUiThread(() -> {
					LoadingBooster.show(requireActivity()).size(180).color(0xFF00ACC1);
				});
				
				PackageManager pm = getContext().getApplicationContext().getPackageManager();
				List<ApplicationInfo> apps;
				
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
					apps = pm.getInstalledApplications(PackageManager.ApplicationInfoFlags.of(PackageManager.GET_META_DATA));
				} else {
					apps = pm.getInstalledApplications(PackageManager.GET_META_DATA);
				}
				
				for (ApplicationInfo app : apps) {
					if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) continue;
					
					boolean isGame = false;
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
						if (app.category == ApplicationInfo.CATEGORY_GAME) isGame = true;
					}
					if (!isGame && (app.flags & ApplicationInfo.FLAG_IS_GAME) != 0) isGame = true;
					if (!isGame && app.metaData != null) {
						if (app.metaData.getBoolean("isGame", false)) isGame = true;
					}
					
					if (isGame) {
						try {
							File fileh = new File(dirs, app.packageName + ".json");
							JSONObject objk = new JSONObject();
							objk.put("name", app.loadLabel(pm).toString());
							objk.put("package", app.packageName);
							objk.put("game", true);
							
							java.io.FileWriter writer = new java.io.FileWriter(fileh);
							writer.write(objk.toString());
							writer.flush();
							writer.close();
							
							HashMap<String, Object> map = new HashMap<>();
							map.put("app name", objk.getString("name"));
							map.put("package", objk.getString("package"));
							map.put("isGame", true);
							listmap.add(map);
						} catch (Exception e) { e.printStackTrace(); }
					}
				}
				getActivity().runOnUiThread(() -> LoadingBooster.hide());
				
			} else {
				for (File f : files) {
					if (f.isFile() && f.getName().endsWith(".json")) {
						try {
							String content = FileUtil.readFile(f.getAbsolutePath());
							if (!content.isEmpty()) {
								JSONObject obj = new JSONObject(content);
								HashMap<String, Object> map = new HashMap<>();
								
								map.put("app name", obj.getString("name"));
								map.put("package", obj.getString("package"));
								map.put("isGame", true);
								
								listmap.add(map);
							}
						} catch (Exception e) { e.printStackTrace(); }
					}
				}
			}
			
			getActivity().runOnUiThread(() -> {
				listview1.setAdapter(new Listview1Adapter(listmap));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			});
			
			new Thread(() -> {
				PackageManager pmThread = getContext().getApplicationContext().getPackageManager();
				for (int i = 0; i < listmap.size(); i++) {
					try {
						String pkg = listmap.get(i).get("package").toString();
						if (ImageHelper.getCache(pkg) != null) continue;
						Drawable icon = pmThread.getApplicationIcon(pkg);
						Bitmap result = ImageHelper.processIcon(icon);
						ImageHelper.putCache(pkg, result);
						
						getActivity().runOnUiThread(() -> {
							((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
						});
					} catch (Exception e) {}
				}
			}).start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void _filtered() {
	}
	private void filterApps(String query) {
		String filter = query.trim().toLowerCase();
		ArrayList<HashMap<String, Object>> filteredList = new ArrayList<>();
		
		if (!filter.isEmpty()) {
			for (HashMap<String, Object> item : listmap) {
				String appName = item.get("app name").toString().toLowerCase();
				if (appName.contains(filter)) {
					filteredList.add(item);
				}
			}
		} else {
			filteredList.addAll(listmap);
		}
		
		listview1.setAdapter(new Listview1Adapter(filteredList));
		((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
	}
	{
	}
	
	
	public void _pack() {
	}
	private class LoadApplications implements Runnable {
		private int progressStatus = 0;
		
		private void onPreExecute() {
			// super.onPreExecute();
			listview1.setVisibility(View.GONE);
			LoadingBooster.show(requireActivity())
			.size(180)
			.color(0xFF00ACC1);
			progressbar.setVisibility(View.GONE);
			progressbar.setProgress(0); // mulai dari 0
		}
		
		@Override
		public void run() {
			for (int i = 0; i <= 100; i++) {
				try {
					Thread.sleep(20); // delay agar terlihat animasinya
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				final int _prog = i; new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> onProgressUpdate(_prog)); // kirim nilai ke onProgressUpdate
			}
			
			
			
			// done
			_mainHandler.post(() -> onPostExecute(null));
		}
		
		private void onProgressUpdate(int value) {
			// super.onProgressUpdate(values);
			progressbar.setProgress(value);
		}
		
		protected void onPostExecute(Void result) {
			onPostExecuteImpl(result);
			_$Load$();
			
			listview1.setAdapter(new Listview1Adapter(listmap));
			listview1.setVisibility(View.VISIBLE);
			progressbar.setVisibility(View.GONE);
			LoadingBooster.hide();
		}
	}
	private ProgressDialog progress;
	{
	}
	
	
	public void _apps() {
		linear101.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear103.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)60, 0xFF7EF0E1));
		linear105.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)1, 0xFF7EF0E1, 0x5000ACC1));
		linear106.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)1, 0xFF7EF0E1, 0x5000ACC1));
	}
	
	
	public void _variable() {
	}
	private Process process;
	private int jumlah = 0;
	private int _position = 0;
	private Boolean game = true;
	private boolean runnings = false;
	{
	}
	private ValueAnimator animator;
	private LinearGradient gradient;
	private Matrix matrix = new Matrix();
	private Paint paint = new Paint();
	private float translate = 0f;
	private ArrayList<String> commandHistory = new ArrayList<>();
	private int historyIndex = -1;
	private int sdk = 0;
	private int hz = 60;
	private NotificationManager nm;
	private Future<?> taskFuture;
	private Listview1Adapter adapter;
	private boolean isDataLoaded = false;
	private ExecutorService executor;
	{
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
	
	
	public void _bgApps() {
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
	
	public File getCacheFile(String pkg) {
		File dir = new File(requireContext().getExternalFilesDir(null), "icon_cache");
		if (!dir.exists()) dir.mkdirs();
		return new File(dir, pkg + ".png");
	}
	
	public void saveBitmapToCache(Bitmap bmp, File file) {
		try {
			FileOutputStream out = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Bitmap loadBitmapFromCache(File file) {
		try {
			return BitmapFactory.decodeFile(file.getAbsolutePath());
		} catch (Exception e) {
			// done
			
		}
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
	
	
	public void _$SortListMap$(final ArrayList<HashMap<String, Object>> _listmap, final String _key, final boolean _isNumber, final boolean _ascending) {
		Collections.sort(_listmap, new Comparator<HashMap<String, Object>>() {
			public int compare(HashMap<String, Object> _compareMap1, HashMap<String, Object> _compareMap2) {
				if (_isNumber) {
					int _count1 = Integer.valueOf(_compareMap1.get(_key).toString());
					int _count2 = Integer.valueOf(_compareMap2.get(_key).toString());
					if (_ascending) {
						return _count1 < _count2 ? -1 : _count1 < _count2 ? 1 : 0;
					}
					else {
						return _count1 > _count2 ? -1 : _count1 > _count2 ? 1 : 0;
					}
				}
				else {
					if (_ascending) {
						return (_compareMap1.get(_key).toString()).compareTo(_compareMap2.get(_key).toString());
					}
					else {
						return (_compareMap2.get(_key).toString()).compareTo(_compareMap1.get(_key).toString());
					}
				}
			}
		});
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
	
	
	private void showToast(Context ctx, String msg) {
		if (ctx == null) return;
		
		new Handler(Looper.getMainLooper()).post(() -> {
			try {
				Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
			} catch (Exception ignored) {}
		});
	}
	
	{
	}
	
	
	public void _ICC(final ImageView _img, final String _c1, final String _c2) {
		_img.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor(_c1), Color.parseColor(_c2)}));
	}
	
	
	public void _font() {
		textview58.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview59.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
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
		int densityDpi = (int) (density * 250);
		
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
	
	
	public void _games() {
	}
	private int selectedPosition = -1;
	
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
	
	
	public void _mode() {
	}
	int currentMode = 1;
	int selectedMode = 1;
	{
	}
	
	
	public void _apply() {
	}
	public void applySensi(String packageName, float scale) {
		
		String baseCmd = "cmd game set --mode 2 --downscale " + scale + " " + packageName;
		
		
		String brandTweak = "";
		String brand = android.os.Build.MANUFACTURER.toLowerCase();
		
		if (brand.contains("xiaomi")) {
			
			brandTweak = "cmd device_config put game_overlay all_packages_enabled true && " +
			"cmd device_config put game_overlay " + packageName + " mode=2,downscale=" + scale;
		} 
		else if (brand.contains("samsung")) {
			
			brandTweak = "settings put global n_p_m 1 && " +
			"cmd device_config put game_overlay " + packageName + " mode=2,downscale=" + scale;
		}
		else if (brand.contains("oppo") || brand.contains("realme")) {
			
			brandTweak = "cmd thermal-service override-status 0 && " + baseCmd;
		}
		else {
			
			brandTweak = baseCmd;
		}
		
		
		try {
			ExecEngine.newProcess(new String[]{"sh", "-c", brandTweak});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.apps, null);
			}
			
			final androidx.cardview.widget.CardView cardview1 = _view.findViewById(R.id.cardview1);
			final FrameLayout linear12 = _view.findViewById(R.id.linear12);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final LinearLayout linear26 = _view.findViewById(R.id.linear26);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final LinearLayout linear13 = _view.findViewById(R.id.linear13);
			final ImageView imageview6 = _view.findViewById(R.id.imageview6);
			final LinearLayout linear27 = _view.findViewById(R.id.linear27);
			final LinearLayout linear14 = _view.findViewById(R.id.linear14);
			final LinearLayout linear15 = _view.findViewById(R.id.linear15);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final TextView textview4 = _view.findViewById(R.id.textview4);
			final ImageView imageview7 = _view.findViewById(R.id.imageview7);
			
			try
			{
				Drawable app_icon = requireContext().getPackageManager().getApplicationIcon(_data.get(_position).get("package").toString());
				
				imageview1.setImageDrawable(app_icon);
			}
			catch (android.content.pm.PackageManager.NameNotFoundException e) {
				
			}
			textview1.setText(_data.get((int) _position).get("app name").toString());
			textview4.setText(_data.get((int) _position).get("package").toString());
			pkg = _data.get((int) _position).get("package").toString();
			imageview7.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					final com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog =
					new com.google.android.material.bottomsheet.BottomSheetDialog(requireContext());
					
					View bottomSheetView = getActivity().getLayoutInflater().inflate(R.layout.floatingboost, null);
					
					bottomSheetDialog.setContentView(bottomSheetView);
					
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
						bottomSheetDialog.getWindow().setBackgroundBlurRadius(40);
					}
					
					bottomSheetDialog.setOnShowListener(dialog -> {
						com.google.android.material.bottomsheet.BottomSheetDialog d =
						(com.google.android.material.bottomsheet.BottomSheetDialog) dialog;
						
						View bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
						if (bottomSheet != null) {
							bottomSheet.setBackgroundResource(android.R.color.transparent);
						}
					});
					
					// ===== LINEAR =====
					LinearLayout linear1 = bottomSheetView.findViewById(R.id.linear1);
					LinearLayout linear2 = bottomSheetView.findViewById(R.id.linear2);
					LinearLayout linear3 = bottomSheetView.findViewById(R.id.linear3);
					LinearLayout linear4 = bottomSheetView.findViewById(R.id.linear4);
					LinearLayout linear5 = bottomSheetView.findViewById(R.id.linear5);
					LinearLayout linear6 = bottomSheetView.findViewById(R.id.linear6);
					LinearLayout linear7 = bottomSheetView.findViewById(R.id.linear7);
					LinearLayout linear267 = bottomSheetView.findViewById(R.id.linear267);
					LinearLayout linear268 = bottomSheetView.findViewById(R.id.linear268);
					LinearLayout linear270 = bottomSheetView.findViewById(R.id.linear270);
					LinearLayout linear271 = bottomSheetView.findViewById(R.id.linear271);
					LinearLayout linear279 = bottomSheetView.findViewById(R.id.linear279);
					LinearLayout linear280 = bottomSheetView.findViewById(R.id.linear280);
					LinearLayout linearload = bottomSheetView.findViewById(R.id.linearload);
					
					// ===== CUSTOM =====
					LinearLayout linear8 = bottomSheetView.findViewById(R.id.linear8);
					
					// ===== TEXT =====
					TextView uid = bottomSheetView.findViewById(R.id.uid);
					TextView title = bottomSheetView.findViewById(R.id.title);
					TextView packagee = bottomSheetView.findViewById(R.id.packagee);
					TextView versionName = bottomSheetView.findViewById(R.id.versionName);
					TextView versionCode = bottomSheetView.findViewById(R.id.versionCode);
					
					TextView textview143 = bottomSheetView.findViewById(R.id.textview143);
					TextView textview144 = bottomSheetView.findViewById(R.id.textview144);
					TextView textview133 = bottomSheetView.findViewById(R.id.textview133);
					TextView textview135 = bottomSheetView.findViewById(R.id.textview135);
					TextView textview137 = bottomSheetView.findViewById(R.id.textview137);
					TextView textview138 = bottomSheetView.findViewById(R.id.textview138);
					TextView textview145 = bottomSheetView.findViewById(R.id.textview145);
					TextView textview149 = bottomSheetView.findViewById(R.id.textview149);
					TextView textview150 = bottomSheetView.findViewById(R.id.textview150);
					
					// ===== IMAGE =====
					ImageView imageview60 = bottomSheetView.findViewById(R.id.imageview60);
					ImageView imageview56 = bottomSheetView.findViewById(R.id.imageview56);
					ImageView imageview57 = bottomSheetView.findViewById(R.id.imageview57);
					
					// ===== RELATIVE =====
					RelativeLayout relativelayout7 = bottomSheetView.findViewById(R.id.relativelayout7);
					RelativeLayout relativelayout3 = bottomSheetView.findViewById(R.id.relativelayout3);
					RelativeLayout relativelayout4 = bottomSheetView.findViewById(R.id.relativelayout4);
					
					// ===== MODE =====
					LinearLayout mode1 = bottomSheetView.findViewById(R.id.mode1);
					LinearLayout mode2 = bottomSheetView.findViewById(R.id.mode2);
					LinearLayout mode3 = bottomSheetView.findViewById(R.id.mode3);
					
					// ===== SCROLL =====
					HorizontalScrollView hscroll1 = bottomSheetView.findViewById(R.id.hscroll1);
					
					// ===== BUTTON =====
					com.hypers.hm.SlideButton button1 = bottomSheetView.findViewById(R.id.button1);
					title.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
					versionName.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
					uid.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
					packagee.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
					versionCode.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
					textview143.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
					textview144.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
					textview133.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
					textview135.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
					textview137.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
					textview138.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
					textview145.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
					textview149.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
					uid.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0xFF000000));
					packagee.setText(_data.get((int) _position).get("package").toString());
					title.setText(_data.get((int) _position).get("app name").toString());
					int initialHeight = button1.getMeasuredHeight();
					
					ValueAnimator anims = ValueAnimator.ofInt(initialHeight, 0);
					anims.addUpdateListener(valueAnimator -> {
						int val = (int) valueAnimator.getAnimatedValue();
						ViewGroup.LayoutParams params = button1.getLayoutParams();
						params.height = val;
						button1.setLayoutParams(params);
					});
					
					anims.setDuration(300);
					anims.setInterpolator(new DecelerateInterpolator());
					anims.start();
					
					anims.addListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							button1.setVisibility(View.GONE);
						}
					});
					
					linearload.setVisibility(View.VISIBLE);
					
					// ukur height asli (wrap_content)
					linearload.measure(
					View.MeasureSpec.makeMeasureSpec(((View) linearload.getParent()).getWidth(), View.MeasureSpec.EXACTLY),
					View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
					);
					
					int targetHeight = linearload.getMeasuredHeight();
					
					ViewGroup.LayoutParams lp = linearload.getLayoutParams();
					lp.height = 0;
					linearload.setLayoutParams(lp);
					
					ValueAnimator anim = ValueAnimator.ofInt(0, targetHeight);
					anim.addUpdateListener(valueAnimator -> {
						int val = (int) valueAnimator.getAnimatedValue();
						ViewGroup.LayoutParams params = linearload.getLayoutParams();
						params.height = val;
						linearload.setLayoutParams(params);
					});
					
					anim.setDuration(300);
					anim.setInterpolator(new DecelerateInterpolator());
					anim.start();
					
					// fade in
					linearload.setAlpha(0f);
					linearload.animate().alpha(1f).setDuration(300).start();
					
					
					new CountDownTimer(10000, 1000) {
						
						int t = 10;
						
						@Override
						public void onTick(long l) {
							t--;
							if (textview150 != null) {                textview150.setText("Boosting : " + t + "s");
							}
						}
						
						@Override
						public void onFinish() {
							
							int initialHeight = linearload.getMeasuredHeight();
							
							ValueAnimator animm = ValueAnimator.ofInt(initialHeight, 0);
							animm.addUpdateListener(valueAnimator -> {
								int val = (int) valueAnimator.getAnimatedValue();
								ViewGroup.LayoutParams params = linearload.getLayoutParams();
								params.height = val;
								linearload.setLayoutParams(params);
							});
							
							animm.setDuration(300);
							animm.setInterpolator(new DecelerateInterpolator());
							animm.start();
							
							animm.addListener(new AnimatorListenerAdapter() {
								@Override
								public void onAnimationEnd(Animator animation) {
									linearload.setVisibility(View.GONE);
								}
							});
							
							button1.setVisibility(View.VISIBLE);
							
							// ukur height asli (wrap_content)
							button1.measure(
							View.MeasureSpec.makeMeasureSpec(((View) button1.getParent()).getWidth(), View.MeasureSpec.EXACTLY),
							View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
							);
							
							int targetHeight = button1.getMeasuredHeight();
							
							ViewGroup.LayoutParams lp = button1.getLayoutParams();
							lp.height = 0;
							button1.setLayoutParams(lp);
							
							ValueAnimator anim = ValueAnimator.ofInt(0, targetHeight);
							anim.addUpdateListener(valueAnimator -> {
								int val = (int) valueAnimator.getAnimatedValue();
								ViewGroup.LayoutParams params = button1.getLayoutParams();
								params.height = val;
								button1.setLayoutParams(params);
							});
							
							anim.setDuration(300);
							anim.setInterpolator(new DecelerateInterpolator());
							anim.start();
							
							// fade in
							button1.setAlpha(0f);
							button1.animate().alpha(1f).setDuration(300).start();
							
							
						}
						
					}.start();
					button1.setLabel("Execute...");
					button1.setOnSlideCompleteListener(() -> {
						
						
						String prefes = modes.getString("moded", "").trim();
						
						if (prefes.equalsIgnoreCase("hypers")) {
							
							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
								
								if (!Environment.isExternalStorageManager()) {
									
									Toast.makeText(getContext(), "IZIN STORAGE BELUM AKTIF", Toast.LENGTH_SHORT).show();
									
									Intent intent = new Intent(
									Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
									Uri.parse("package:" + requireContext().getPackageName())
									);
									
									startActivity(intent);
									return; 
								}
								
							} else {
								
								if (ContextCompat.checkSelfPermission(requireContext(),
								android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
								||
								ContextCompat.checkSelfPermission(requireContext(),
								android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
									
									Toast.makeText(getContext(), "MINTA PERMISSION STORAGE", Toast.LENGTH_SHORT).show();
									
									requestPermissions(new String[]{
										android.Manifest.permission.READ_EXTERNAL_STORAGE,
										android.Manifest.permission.WRITE_EXTERNAL_STORAGE
									}, 999);
									
									return; 
								}
							}
							
							final Context ctx = getContext();
							final Activity act = getActivity();
							final ExecutorService executor = Executors.newSingleThreadExecutor();
							LoadingBooster.show(ctx).size(180).color(0xFF00ACC1);
							
							executor.execute(() -> {
								try {
									
									showToast(ctx, "START SESSION");
									
									
									/* ================= JSON ================= */
									File filee = new File(Environment.getExternalStorageDirectory(),
									"ZYREX-TOOLS/data/.cache/" + _data.get((int) _position).get("package").toString() + ".json");
									
									showToast(ctx, "Applications Access: " + _data.get((int) _position).get("package").toString());
									
									if (!filee.exists()) {
										
										return;
									}
									
									BufferedReader reader = new BufferedReader(new FileReader(filee));
									StringBuilder sb = new StringBuilder();
									String line;
									while ((line = reader.readLine()) != null) sb.append(line);
									reader.close();
									
									JSONObject json = new JSONObject(sb.toString().trim());
									showToast(ctx, "HYPERS-MODE LOADED");
									
									execSmart("am force-stop " + _data.get((int) _position).get("package").toString());
									execSmart("am force-stop " + _data.get((int) _position).get("package").toString());
									execSmart("am kill all");
									showToast(ctx, "BOOSTER GAME SUCCES");
									
									/* ================= BLOCK ================= */
									
									String block = json.optString("block", "")
									.replace("\n","")
									.replace("\r","")
									.trim()
									.toLowerCase();
									
									if (!block.isEmpty()) {
										NotificationManager nm =
										(NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
										
										if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && nm != null) {
											if (nm.isNotificationPolicyAccessGranted()) {
												if (block.equals("on")) {
													nm.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
													showToast(ctx, "Block Notifikasi : ON");
												} else {
													nm.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL);
													showToast(ctx, "Block Notifikasi : OFF");
												}
											} else {
												
											}
										}
									}
									
									/* ================= NETWORK ================= */
									String network = json.optString("network", "")
									.replace("\n","")
									.replace("\r","")
									.trim()
									.toLowerCase();
									
									if (network.equals("on")) {
										
										execSmart("settings put global auto_time 0");
										execSmart("settings put global auto_time_zone 0");
										execSmart("dumpsys deviceidle disable");
										execSmart("settings put global tcp_default_init_rwnd 60");
										execSmart("settings put global mobile_data_always_on 1");
										execSmart("settings put global wifi_sleep_policy 2");
										execSmart("cmd netpolicy set restrict-background false");
										execSmart("ndc resolver flushdefaultif");
										
										showToast(ctx, "Network Optimize : ON");
										
									} else if (network.equals("off")) {
										
										execSmart("settings put global auto_time 1");
										execSmart("settings put global auto_time_zone 1");
										execSmart("dumpsys deviceidle enable");
										execSmart("settings delete global tcp_default_init_rwnd");
										execSmart("settings put global mobile_data_always_on 0");
										execSmart("settings put global wifi_sleep_policy 0");
										execSmart("cmd netpolicy set restrict-background true");
										execSmart("ndc resolver flushdefaultif");
										showToast(ctx, "Network Optimize : OFF");
									} else {
										
									}
									
									/* ================= SENSI ================= */
									
									String sensi = json.optString("sensi", "")
									.replace("\n","")
									.replace("\r","")
									.trim()
									.toLowerCase();
									
									showToast(ctx, "SENSI: " + sensi);
									
									ArrayList<String> cmds = new ArrayList<>();
									String pkgName = _data.get((int) _position).get("package").toString();
									
									switch (sensi) {
										case "lowest": 
										cmds.addAll(hypersExecute.getLowest(pkgName)); 
										applySensi(pkgName, 0.6f);
										break;
										case "low": 
										cmds.addAll(hypersExecute.getLow(pkgName));
										applySensi(pkgName, 0.8f);
										break;
										case "default": 
										cmds.addAll(hypersExecute.getDefault(pkgName)); 
										applySensi(pkgName, 1.0f);
										break;
										case "smooth": 
										cmds.addAll(hypersExecute.getSmooth(pkgName));
										applySensi(pkgName, 1.8f);
										break;
										case "highest": 
										cmds.addAll(hypersExecute.getHighest(pkgName));
										applySensi(pkgName, 3.0f);
										break;
										case "ultra": 
										cmds.addAll(hypersExecute.getUltra(pkgName)); 
										applySensi(pkgName, 3.5f);
										
										break;
									}
									
									if (!cmds.isEmpty()) {
										runCmdList(cmds);
										showToast(ctx, "SENSI APPLIED");
									} else {
										showToast(ctx, "SENSI FAILED: Mode tidak dikenal");
									}
									
									/* ================= FPS ================= */
									String fps = json.optString("fps", "")
									.replace("\n","")
									.replace("\r","")
									.trim()
									.toLowerCase();
									
									
									if (fps.contains("60")) {
										execSmart("settings put system user_refresh_rate 1");
										execSmart("settings put global peak_refresh_rate 60.0");
										execSmart("settings put global min_refresh_rate 60.0");
										execSmart("settings put system peak_refresh_rate 60.0");
										execSmart("settings put system min_refresh_rate 60.0");
										execSmart("settings put global speed_mode 1");
										execSmart("sh -c 'service call SurfaceFlinger 1035 i32 60'");
										execSmart("sh -c 'service call SurfaceFlinger 1011 i32 60'");
										showToast(ctx, "HYPERS MANAGER: 60 FPS Locked");
									} else if (fps.contains("90")) {
										execSmart("settings put system user_refresh_rate 1");
										execSmart("settings put global peak_refresh_rate 90.0");
										execSmart("settings put global min_refresh_rate 90.0");
										execSmart("settings put system peak_refresh_rate 90.0");
										execSmart("settings put system min_refresh_rate 90.0");
										execSmart("settings put global speed_mode 1");
										execSmart("sh -c 'service call SurfaceFlinger 1035 i32 90'");
										execSmart("sh -c 'service call SurfaceFlinger 1011 i32 90'");
										showToast(ctx, "HYPERS MANAGER: 90 FPS Locked");
									} else if (fps.contains("120")) {
										execSmart("settings put system user_refresh_rate 1");
										execSmart("settings put global peak_refresh_rate 120.0");
										execSmart("settings put global min_refresh_rate 120.0");
										execSmart("settings put system peak_refresh_rate 120.0");
										execSmart("settings put system min_refresh_rate 120.0");
										execSmart("settings put global speed_mode 1");
										execSmart("sh -c 'service call SurfaceFlinger 1035 i32 120'");
										execSmart("sh -c 'service call SurfaceFlinger 1011 i32 120'");
										showToast(ctx, "HYPERS MANAGER: 120 FPS Locked");
									} else {
										execSmart("settings delete system user_refresh_rate");
										execSmart("settings delete global peak_refresh_rate");
										execSmart("settings delete global min_refresh_rate");
										execSmart("settings delete system peak_refresh_rate");
										execSmart("settings delete system min_refresh_rate");
										execSmart("settings put global speed_mode 0");
										execSmart("sh -c 'service call SurfaceFlinger 1035 i32 0'");
										showToast(ctx, "HYPERS MANAGER: FPS Default");
									}
									
									String chip = json.optString("elixirSOC", "")
									.replace("\n","")
									.replace("\r","")
									.trim()
									.toLowerCase();
									
									if (chip.equals("on")) {
										new Thread(new Runnable() {
											@Override
											public void run() {
												shizukuExec("setprop debug.generate-debug-info false");
												shizukuExec("setprop debug.atrace.tags.enableflags 0");
												shizukuExec("setprop debug.sf.latch_unsignaled 1");
												shizukuExec("setprop debug.sf.enable_gl_backpressure 1");
												shizukuExec("setprop debug.hwui.renderer skiagl");
												shizukuExec("setprop debug.hwui.use_buffer_age true");
												shizukuExec("setprop debug.hwui.disable_vsync false");
												
												shizukuExec("settings put global fstrim_mandatory 1");
												shizukuExec("settings put global activity_manager_constants max_cached_processes=64");
												shizukuExec("settings put global ram_expand_size 0");
												
												shizukuExec("cmd power set-fixed-performance-mode-enabled true");
												shizukuExec("cmd looper_stats disable");
												shizukuExec("cmd package compile -m speed-profile -a");
												
												shizukuExec("sh -c 'for gpu in /sys/class/kgsl/kgsl-3d0 /sys/devices/platform/*.mali; do if [ -d $gpu ]; then echo performance > $gpu/devfreq/governor; fi; done'");
												
												showToast(ctx, "Chipset Optimized : ON");
											}
										}).start();
									} else {
										new Thread(new Runnable() {
											@Override
											public void run() {
												shizukuExec("setprop debug.sf.latch_unsignaled ''");
												shizukuExec("setprop debug.hwui.renderer ''");
												shizukuExec("settings delete global activity_manager_constants");
												shizukuExec("cmd power set-fixed-performance-mode-enabled false");
												shizukuExec("cmd looper_stats enable");
												showToast(ctx, "Chipset Optimization : OFF");
											}
										}).start();
									}
									
									/* ================= AIM TRICK ================= */
									String trick = json.optString("aimtrick", "")
									.replace("\n","")
									.replace("\r","")
									.trim()
									.toLowerCase();
									
									if (trick.equals("on")) {
										new Thread(new Runnable() {
											@Override
											public void run() {
												ArrayList<String> cmdd = new ArrayList<>(Arrays.asList(
												
												"settings put global low_power 0",
												"settings put global device_pwr_mode 0",
												"content insert --uri content://settings/system --bind name:s:touch_blocking_period --bind value:s:0",
												"content insert --uri content://settings/system --bind name:s:tap_duration_threshold --bind value:s:0",
												"content insert --uri content://settings/system --bind name:s:touch_slop --bind value:s:1",
												"content insert --uri content://settings/system --bind name:s:input_pointer_speed --bind value:s:7",
												"content insert --uri content://settings/system --bind name:s:mouse_cursor_speed --bind value:s:7",
												"content insert --uri content://settings/system --bind name:s:mouse_cursor_acceleration --bind value:s:0",
												"content insert --uri content://settings/system --bind name:s:mouse_cursor_latency --bind value:s:0",
												"content insert --uri content://settings/system --bind name:s:pointer_gesture_enable --bind value:s:0",
												"content insert --uri content://settings/system --bind name:s:pointer_gesture_zoom_combined_slop --bind value:s:10",
												"content insert --uri content://settings/system --bind name:s:pointer_gesture_multifinger_min_speed --bind value:s:150",
												"settings put system mouse_reverse_scroll 0",
												"settings put system mouse_natural_scroll 0",
												"settings put system mouse_primary_button 0",
												"settings put system show_mouse_pointer 1",
												"settings put system pointer_fill_style 0",
												"settings put system show_touches 0",
												"settings put system pointer_acceleration 1",
												"settings put system pointer_sensitivity 1.0",
												"settings put system mouse_pointer_speed 7",
												"settings put system mouse_scroll_speed 5",
												"settings put global window_animation_scale 0.5",
												"settings put global transition_animation_scale 0.5",
												"settings put global animator_duration_scale 0.5",
												"settings put system pointer_speed 7",
												"settings put system scroll_friction 0.006",
												"setprop debug.hwui.renderer skiagl",
												"setprop debug.hwui.use_buffer_age true",
												"setprop debug.hwui.disable_vsync false",
												"setprop debug.egl.swapinterval 1",
												"setprop debug.egl.hw 1",
												"setprop debug.sf.latch_unsignaled 1",
												"setprop debug.sf.enable_gl_backpressure 1",
												"setprop debug.sf.early.app.duration 8000000",
												"setprop debug.sf.early.sf.duration 8000000",
												"setprop debug.sf.early_phase_offset_ns 1000000",
												"setprop debug.sf.late.app.duration 8000000",
												"setprop debug.sf.late.sf.duration 8000000",
												"cmd power set-fixed-performance-mode-enabled false",
												
												"sh -c \"for cpu in /sys/devices/system/cpu/cpu[0-9]*; do if [ -f $cpu/cpufreq/scaling_governor ]; then echo schedutil > $cpu/cpufreq/scaling_governor; fi; done\"",
												
												"settings put global activity_manager_constants max_cached_processes=32",
												"setprop persist.sys.scrollingcache 3"
												
												));
												
												runCmdList(cmdd);
												if (sdk >= 30) {
													String sensii = json.optString("sensi", "")
													.replace("\n","")
													.replace("\r","")
													.trim()
													.toLowerCase();
													
													showToast(ctx, "SENSI: " + sensi);
													
													ArrayList<String> cmds = new ArrayList<>();
													String pkgName = _data.get((int) _position).get("package").toString();
													
													switch (sensi) {
														case "lowest": 
														cmds.addAll(hypersExecute.getLowest(pkgName)); 
														applySensi(pkgName, 0.6f);
														break;
														case "low": 
														cmds.addAll(hypersExecute.getLow(pkgName));
														applySensi(pkgName, 0.8f);
														break;
														case "default": 
														cmds.addAll(hypersExecute.getDefault(pkgName)); 
														applySensi(pkgName, 1.0f);
														break;
														case "smooth": 
														cmds.addAll(hypersExecute.getSmooth(pkgName));
														applySensi(pkgName, 1.8f);
														break;
														case "highest": 
														cmds.addAll(hypersExecute.getHighest(pkgName));
														applySensi(pkgName, 3.0f);
														break;
														case "ultra": 
														cmds.addAll(hypersExecute.getUltra(pkgName)); 
														applySensi(pkgName, 3.5f);
														
														break;
													}
													
													if (!cmds.isEmpty()) {
														runCmdList(cmds);
														
													} else {
														
													}
												} else {
													
													execSmart("settings put secure long_press_timeout 180");
													execSmart("settings put secure multi_press_timeout 120");
													execSmart("settings put system scroll_friction 0.006");
													execSmart("settings put global window_animation_scale 0.5");
													execSmart("settings put global transition_animation_scale 0.5");
													execSmart("settings put global animator_duration_scale 0.5");
													execSmart("settings put system min_fling_velocity 50");
													execSmart("settings put system max_fling_velocity 12000");
												}
											}
										}).start();
										
										showToast(ctx, "AimTrick : ON");
									} else {
										new Thread(new Runnable() {
											@Override
											public void run() {
												try {
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
													
													if (sdk >= 31) {
														String pkg = _data.get((int) _position).get("package").toString();
														execSmart("cmd game reset " + pkg);
													} else {
														
													}
													
													getActivity().runOnUiThread(() -> {
														SketchwareUtil.showMessage(getContext(), "AimTrick : OFF");
													});
													
												} catch (Exception e) {
													e.printStackTrace();
												}
											}
										}).start();
										
									}
									
									/* ================= AIMING ================= */
									String aiming = json.optString("aiming", "")
									.replace("\n","")
									.replace("\r","")
									.trim()
									.toLowerCase();
									
									if (aiming.equals("on")) {
										runCmdList(hypersExecute.getJitter());
										showToast(ctx, "AIMING : ON");
									} else if (aiming.equals("off")) {
										runCmdList(hypersExecute.getJitterDefault());
										showToast(ctx, "AIMING : OFF");
									}
									
									
									String perfor = json.optString("Perform", "")
									.replace("\n","")
									.replace("\r","")
									.trim()
									.toLowerCase();
									
									if (perfor.equals("enable")) {
										
										execSmart("cmd package compile -m speed-profile -f " + _data.get((int) _position).get("package").toString());
										execSmart("stop logd");
										execSmart("settings put global power_check_max_cpu_1 100");
										execSmart("settings put global power_check_max_cpu_150 100");
										execSmart("settings put global power_check_max_cpu_2 100");
										execSmart("settings put global power_check_max_cpu_2 100");
										execSmart("settings put global power_check_max_cpu_3 100");
										execSmart("settings put global power_check_max_cpu_375 100");
										execSmart("settings put global power_check_max_cpu_4 100");
										execSmart("settings put global power_check_max_cpu_475 100");
										execSmart("settings put secure tap_duration_threshold 0.0");
										execSmart("settings put secure touch_blocking_period 0.0");
										execSmart("settings put secure high_priority 1");
										execSmart("setprop debug.hwui.fps_divisor 1");
										execSmart("setprop debug.cpurendertiming 1");
										execSmart("setprop debug.sf.latch_unsignaled 1");
										execSmart("setprop debug.sf.showupdates 1");
										showToast(ctx, "PERFORMANCE GAME ON");
										
									} else if (network.equals("disable")) {
										
										execSmart("settings delete global power_check_max_cpu_1");
										execSmart("settings delete global power_check_max_cpu_150");
										execSmart("settings delete global power_check_max_cpu_2");
										execSmart("settings delete global power_check_max_cpu_2");
										execSmart("settings delete global power_check_max_cpu_3");
										execSmart("settings delete global power_check_max_cpu_375");
										execSmart("settings delete global power_check_max_cpu_4");
										execSmart("settings delete global power_check_max_cpu_475");
										execSmart("settings delete secure tap_duration_threshold");
										execSmart("settings delete secure touch_blocking_period");
										execSmart("settings delete secure high_priority");
										execSmart("setprop debug.hwui.fps_divisor 0");
										execSmart("setprop debug.sf.latch_unsignaled 0");
										execSmart("setprop debug.sf.showupdates 0");
										showToast(ctx, "PERFORMANCE GAME OFF");
									} else {
										
									}
									
									new Handler(Looper.getMainLooper()).post(() -> {
										try {
											
											showToast(ctx, "LAUNCHING...");
											
											Intent i = new Intent(ctx, ProBoosterService.class);
											i.putExtra("GAME_PKG", _data.get((int) _position).get("package").toString());
											
											if (Build.VERSION.SDK_INT >= 26) {
												ctx.startForegroundService(i);
											} else {
												ctx.startService(i);
											}
											
											Intent launch = ctx.getPackageManager()
											.getLaunchIntentForPackage(_data.get((int) _position).get("package").toString());
											
											if (launch != null) {
												launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
												ctx.startActivity(launch);
												showToast(ctx, "GAME LAUNCHED");
											} else {
												showToast(ctx, "LAUNCH FAILED");
											}
											
											LoadingBooster.hide();
											
										} catch (Exception e) {
											e.printStackTrace();
											showToast(ctx, "ERROR LAUNCH");
										}
									});
									
								} catch (Exception e) {
									e.printStackTrace();
									showToast(ctx, "CRASH: " + e.getMessage());
								}
							});
							
						} else if (prefes.equalsIgnoreCase("balanced")) {
							final Context ctx = getContext();
							final Activity act = getActivity();
							final ExecutorService executor = Executors.newSingleThreadExecutor();
							LoadingBooster.show(ctx).size(180).color(0xFF00ACC1);
							
							new Thread(() -> {
								String[] cmds = new String[]{
									// System Settings - Kembalikan ke default (biasanya 0 atau mode standar)
									"settings put system POWER_PERFORMANCE_MODE_OPEN 0",
									"settings put system multicore_packet_scheduler 0",
									"settings put system high_performance_mode_on 0",
									"settings put system sem_performance_mode 0",
									"settings put system speed_mode 0",
									
									// Secure & Global - Aktifkan kembali manajemen daya adaptif
									"settings put secure high_priority 0",
									"settings put global sem_enchanced_cpu_responsiveness 0",
									"settings put global cached_apps_freezer \"enabled\"",
									"settings put global restricted_device_performance \"1,1\"", // Batasi jika perlu
									"settings put global adaptive_battery_management_enabled 1", // Aktifkan manajemen baterai
									"settings put global game_auto_temperature_control 1",      // Aktifkan kontrol suhu
									
									// Perf Shielder - Kembalikan ke mode pintar/normal
									"settings put system perf_shielder_SF 1",
									"settings put system perf_shielder_RTMODE 0",
									"settings put system perf_shielder_smartpower 1",
									
									// Power Save - Biarkan sistem mengatur secara otomatis (tidak dipaksa mati)
									"settings put global automatic_power_save_mode 1",
									
									// Debug Properties - Hapus atau setel ke nilai standar
									"setprop debug.performance.tuning 0",
									"setprop debug.sf.hw 1",
									"setprop debug.hwui.force_draw_frame false",
									
									// Power Command - Matikan mode performa tetap
									"cmd power set-fixed-performance-mode-enabled false",
									"cmd power set-adaptive-power-saver-enabled true",
									"cmd power set-mode 1" // Mode 1 biasanya merepresentasikan 'Normal' atau 'Balanced'
								};
								
								for (String cmd : cmds) {
									try {
										shizukuExec(cmd);
										try {
											
											showToast(ctx, "LAUNCHING...");
											
											Intent i = new Intent(ctx, ProBoosterService.class);
											i.putExtra("GAME_PKG", _data.get((int) _position).get("package").toString());
											
											if (Build.VERSION.SDK_INT >= 26) {
												ctx.startForegroundService(i);
											} else {
												ctx.startService(i);
											}
											
											Intent launch = ctx.getPackageManager()
											.getLaunchIntentForPackage(_data.get((int) _position).get("package").toString());
											
											if (launch != null) {
												launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
												ctx.startActivity(launch);
												showToast(ctx, "GAME LAUNCHED");
											} else {
												showToast(ctx, "LAUNCH FAILED");
											}
											
											LoadingBooster.hide();
											
										} catch (Exception e) {
											e.printStackTrace();
											showToast(ctx, "ERROR LAUNCH");
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}).start();
							
						} else if (prefes.equalsIgnoreCase("performa")) {
							final Context ctx = getContext();
							final Activity act = getActivity();
							final ExecutorService executor = Executors.newSingleThreadExecutor();
							LoadingBooster.show(ctx).size(180).color(0xFF00ACC1);
							new Thread(() -> {
								String[] cmds = new String[]{
									"settings put system POWER_PERFORMANCE_MODE_OPEN 1",
									"settings put system multicore_packet_scheduler 1",
									"settings put system high_performance_mode_on 1",
									"settings put system sem_performance_mode 4",
									"settings put system speed_mode 1",
									"settings put secure high_priority 1",
									"settings put secure speed_mode_enable 1",
									"settings put global sem_enchanced_cpu_responsiveness 1",
									"settings put global cached_apps_freezer \"enabled\"",
									"settings put global restricted_device_performance \"0,0\"",
									"settings put global adaptive_battery_management_enabled 0",
									"settings put global game_auto_temperature_control 0",
									"settings put system perf_shielder_SF 0",
									"settings put system perf_shielder_RTMODE 1",
									"settings put system perf_shielder_GESTURE 0",
									"settings put system perf_shielder_smartpower 0",
									"settings put system POWER_SAVE_MODE_OPEN 0",
									"settings put global automatic_power_save_mode 0",
									"settings put global low_power 0",
									"setprop debug.performance.tuning 1",
									"setprop debug.sf.hw 1",
									"setprop debug.egl.hw 1",
									"setprop debug.egl.buffercount 2",
									"setprop debug.gralloc.enable_fb_ubwc 1",
									"setprop debug.gralloc.gfx_ubwc_disable 0",
									"setprop debug.gralloc.map_fb_memory 0",
									"setprop debug.sf.multithreaded_present 1",
									"setprop debug.sf.enable_layer_caching true",
									"setprop debug.sf.predict_hwc_composition_strategy 1",
									"setprop debug.hwui.force_draw_frame true",
									"setprop debug.hwui.skip_empty_damage true",
									"setprop debug.hwui.use_gpu_pixel_buffers true",
									"cmd power set-fixed-performance-mode-enabled true",
									"cmd power set-adaptive-power-saver-enabled false",
									"cmd power set-mode 0"
								};
								
								for (String cmd : cmds) {
									try {
										shizukuExec(cmd);
										try {
											
											showToast(ctx, "LAUNCHING...");
											
											Intent i = new Intent(ctx, ProBoosterService.class);
											i.putExtra("GAME_PKG", _data.get((int) _position).get("package").toString());
											
											if (Build.VERSION.SDK_INT >= 26) {
												ctx.startForegroundService(i);
											} else {
												ctx.startService(i);
											}
											
											Intent launch = ctx.getPackageManager()
											.getLaunchIntentForPackage(_data.get((int) _position).get("package").toString());
											
											if (launch != null) {
												launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
												ctx.startActivity(launch);
												showToast(ctx, "GAME LAUNCHED");
											} else {
												showToast(ctx, "LAUNCH FAILED");
											}
											
											LoadingBooster.hide();
											
										} catch (Exception e) {
											e.printStackTrace();
											showToast(ctx, "ERROR LAUNCH");
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}).start();
						}
						
						button1.postDelayed(button1::reset, 1500);
					});
					try {
						// আপনার কোড এখানে দিন
						Drawable app_icon = requireContext().getPackageManager().getApplicationIcon(_data.get((int)_position).get("package").toString());
						
						imageview1.setImageDrawable(app_icon);
						String pakg = _data.get((int)_position).get("package").toString();
						
						Bitmap bmp = ImageHelper.getCache(pakg);
						
						if (bmp != null) {
							linear8.setBackground(
							new BitmapDrawable(requireContext().getResources(), bmp)
							);
						} else {
							linear12.setBackgroundColor(0x22000000);
						}
					} catch (Exception e) {
						SketchwareUtil.showMessage(getContext().getApplicationContext(), "error: " + e.getMessage());
					}
					
					try {
						if (getActivity() == null) return;
						
						PackageManager pm = getActivity().getPackageManager();
						PackageInfo pi = pm.getPackageInfo(_data.get(_position).get("package").toString(), 0);
						
						uid.setText(String.valueOf(pi.applicationInfo.uid));
						versionName.setText(pi.versionName);
						
						if (android.os.Build.VERSION.SDK_INT >= 28) {
							versionCode.setText(String.valueOf(pi.getLongVersionCode()));
						} else {
							versionCode.setText(String.valueOf(pi.versionCode));
						}
						
					} catch (Exception e) {
						uid.setText("Not Installed");
						versionName.setText("-");
						versionCode.setText("-");
					}
					{
						android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
						int d = (int) getContext().getApplicationContext().getResources().getDisplayMetrics().density;
						SketchUi.setColor(0x70212121);SketchUi.setCornerRadii(new float[]{
							d*18,d*18,d*18 ,d*18,d*0,d*0 ,d*0,d*0});
						linear1.setElevation(d*4);
						linear1.setBackground(SketchUi);
					}
					
					
					int d = (int) getResources().getDisplayMetrics().density;
					float r = d * 20;
					
					currentMode = 1;
					selectedMode = 1;
					
					hscroll1.setClipToPadding(false);
					hscroll1.setClipChildren(false);
					((ViewGroup) hscroll1.getParent()).setClipChildren(false);
					
					hscroll1.setHorizontalScrollBarEnabled(false);
					hscroll1.setOverScrollMode(View.OVER_SCROLL_NEVER);
					hscroll1.setPadding(d * 32, 0, d * 32, 0);
					
					ViewOutlineProvider outline = new ViewOutlineProvider() {
						@Override
						public void getOutline(View view, android.graphics.Outline outline) {
							outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), r);
						}
					};
					
					View[] views = {mode1, mode2, mode3};
					
					for (View v : views) {
						v.setOutlineProvider(outline);
						v.setClipToOutline(true);
						v.setPadding(d * 2, d * 2, d * 2, d * 2);
					}
					
					GradientDrawable base = new GradientDrawable();
					base.setCornerRadius(r);
					base.setColor(0xFF1A1A1A);
					
					GradientDrawable fill = new GradientDrawable();
					fill.setCornerRadius(r);
					fill.setColor(0x6000ACC1);
					
					GradientDrawable stroke = new GradientDrawable();
					stroke.setCornerRadius(r);
					stroke.setColor(0x00000000);
					stroke.setStroke(d * 2, 0x80BDBDBD);
					
					mode1.setBackground(base);
					mode2.setBackground(fill);
					mode3.setBackground(base);
					
					mode1.setForeground(null);
					mode2.setForeground(stroke);
					mode3.setForeground(null);
					
					LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) mode1.getLayoutParams();
					lp1.setMargins(d * 6, 0, d * 6, 0);
					mode1.setLayoutParams(lp1);
					
					LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) mode2.getLayoutParams();
					lp2.setMargins(d * 6, 0, d * 6, 0);
					mode2.setLayoutParams(lp2);
					
					LinearLayout.LayoutParams lp3 = (LinearLayout.LayoutParams) mode3.getLayoutParams();
					lp3.setMargins(d * 6, 0, d * 6, 0);
					mode3.setLayoutParams(lp3);
					
					Runnable snapRunnable = new Runnable() {
						@Override
						public void run() {
							
							int centerX = hscroll1.getScrollX() + hscroll1.getWidth() / 2;
							
							View closest = null;
							float minDist = Float.MAX_VALUE;
							
							for (View v : views) {
								int vc = v.getLeft() + v.getWidth() / 2;
								float dist = Math.abs(centerX - vc);
								
								if (dist < minDist) {
									minDist = dist;
									closest = v;
								}
							}
							
							if (closest != null) {
								int target = closest.getLeft() - (hscroll1.getWidth() - closest.getWidth()) / 2;
								hscroll1.smoothScrollTo(target, 0);
							}
						}
					};
					
					ViewTreeObserver.OnScrollChangedListener scrollListener =
					new ViewTreeObserver.OnScrollChangedListener() {
						@Override
						public void onScrollChanged() {
							
							hscroll1.removeCallbacks(snapRunnable);
							hscroll1.postDelayed(snapRunnable, 120);
							
							int centerX = hscroll1.getScrollX() + hscroll1.getWidth() / 2;
							
							View selectedView = null;
							float minDist = Float.MAX_VALUE;
							
							for (View v : views) {
								
								int vc = v.getLeft() + v.getWidth() / 2;
								float dist = Math.abs(centerX - vc);
								
								if (dist < minDist) {
									minDist = dist;
									selectedView = v;
								}
								
								float maxDist = hscroll1.getWidth() / 2f;
								float ratio = dist / maxDist;
								if (ratio > 1f) ratio = 1f;
								
								float scale = 1f - (ratio * 0.15f);
								float alpha = 0.85f + (1f - ratio) * 0.15f;
								
								v.setScaleX(scale);
								v.setScaleY(scale);
								v.setAlpha(alpha);
							}
							
							if (selectedView == mode1) modes.edit().putString("moded", "hypers").apply();
							else if (selectedView == mode2) modes.edit().putString("moded", "balanced").apply();
							else if (selectedView == mode3) modes.edit().putString("moded", "performa").apply();
							
							for (View v : views) {
								if (v == selectedView) {
									v.setBackground(fill);
									v.setForeground(stroke);
									v.setElevation(d * 12);
								} else {
									v.setBackground(base);
									v.setForeground(null);
									v.setElevation(d * 2);
								}
							}
						}
					};
					
					hscroll1.getViewTreeObserver().addOnScrollChangedListener(scrollListener);
					
					hscroll1.post(new Runnable() {
						@Override
						public void run() {
							int x = mode2.getLeft() - (hscroll1.getWidth() - mode2.getWidth()) / 2;
							hscroll1.scrollTo(x, 0);
						}
					});
					
					View.OnClickListener click = new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							
							int x = v.getLeft() - (hscroll1.getWidth() - v.getWidth()) / 2;
							hscroll1.smoothScrollTo(x, 0);
							
							if (v == mode1) modes.edit().putString("moded", "hypers").apply();
							else if (v == mode2) modes.edit().putString("moded", "balanced").apply();
							else if (v == mode3) modes.edit().putString("moded", "performa").apply();
						}
					};
					
					mode1.setOnClickListener(click);
					mode2.setOnClickListener(click);
					mode3.setOnClickListener(click);
					
					bottomSheetDialog.setCancelable(true);
					bottomSheetDialog.show();
				}
			});
			try {
				String uri = _data.get((int)_position).get("package").toString();
				android.content.pm.PackageManager pm = requireContext().getPackageManager();
				try {
					android.content.pm.PackageInfo pInfo = pm.getPackageInfo(uri, android.content.pm.PackageManager.GET_ACTIVITIES);
					String version = pInfo.versionName;
				}
				catch (android.content.pm.PackageManager.NameNotFoundException e) {
				}
			}
			catch (Exception e) {
			}
			textview1.setEllipsize(android.text.TextUtils.TruncateAt.MARQUEE);
			textview1.setMarqueeRepeatLimit(-1); 
			textview1.setSingleLine(true);
			textview1.setHorizontallyScrolling(true);
			textview1.setSelected(true);
			textview1.setHorizontalFadingEdgeEnabled(true);
			textview1.setFadingEdgeLength(50);
			try {
				// আপনার কোড এখানে দিন
				Drawable app_icon = requireContext().getPackageManager().getApplicationIcon(_data.get((int)_position).get("package").toString());
				
				imageview1.setImageDrawable(app_icon);
				String pakg = _data.get((int)_position).get("package").toString();
				
				Bitmap bmp = ImageHelper.getCache(pakg);
				
				if (bmp != null) {
					linear12.setBackground(
					new BitmapDrawable(requireContext().getResources(), bmp)
					);
				} else {
					linear12.setBackgroundColor(0x22000000);
				}
			} catch (Exception e) {
				SketchwareUtil.showMessage(getContext().getApplicationContext(), "error: " + e.getMessage());
			}
			
			imageview6.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					user.edit().putString("us", _data.get((int) _position).get("app name").toString()).commit();
					user.edit().putString("pc", _data.get((int) _position).get("package").toString()).commit();
					i.setClass(requireContext(), SetpackageActivity.class);
					requireContext().startActivity(i);
				}
			});
			try {
				
				File file = new File(Environment.getExternalStorageDirectory(), "ZYREX-TOOLS/data/.cache/" + pkg + ".json");
				
				FileInputStream fiis = new FileInputStream(file);
				byte[] data = new byte[(int) file.length()];
				fiis.read(data);
				fiis.close();
				
				
				JSONObject obj = new JSONObject(new String(data));
				_ICC(imageview6, "#1976D2", "#1976D2");
				
			} catch (Exception e) {
				_ICC(imageview6, "#E0E0E0", "#E0E0E0");
				
			}
			linear27.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0x8026A69A));
			textview1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
			textview4.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
			listview1.setSelector(new ColorDrawable(Color.TRANSPARENT));
			cardview1.setOnTouchListener(new View.OnTouchListener(){
				@Override
				public boolean onTouch(View _view, MotionEvent _motionEvent){
					
					return true;
				}
			});
			
			return _view;
		}
	}
}
