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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
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
import com.hypers.hm.MultiCoreGraphView;
import com.hypers.hm.SegmentedButton;
import java.io.*;
import java.io.InputStream;
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
import android.view.*;
import android.graphics.*;
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

import android.view.Choreographer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import java.io.RandomAccessFile;

import com.hypers.hm.service.CrosshairService;
import com.hypers.hm.service.FloatingService;
import com.hypers.hm.service.GyroService;
import com.hypers.hm.debug.ADB;

import android.provider.Settings;



public class SettingsFragmentActivity extends Fragment {
	
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
	private double opsi = 0;
	
	private ScrollView vscroll1;
	private LinearLayout linear5;
	private LinearLayout linear326;
	private LinearLayout linear123;
	private LinearLayout settings;
	private LinearLayout list;
	private ImageView imageview38;
	private TextView textview70;
	private SegmentedButton segmented;
	private LinearLayout linear35;
	private LinearLayout linear328;
	private LinearLayout linear49;
	private LinearLayout linear329;
	private TextView textview67;
	private CardView cardview1;
	private LinearLayout linear122;
	private TextView textview24;
	private LinearLayout linear45;
	private RelativeLayout relativelayout2;
	private ImageView imageview10;
	private LinearLayout linear36;
	private LinearLayout linear37;
	private LinearLayout linear39;
	private LinearLayout linear40;
	private LinearLayout linear41;
	private LinearLayout linear42;
	private ImageView imageview11;
	private ImageView imageview12;
	private TextView textview18;
	private TextView textview19;
	private LinearLayout linear43;
	private LinearLayout linear44;
	private TextView textview20;
	private TextView textview21;
	private TextView textview25;
	private LinearLayout linear332;
	private TextView textview92;
	private ImageView imageview76;
	private LinearLayout linear50;
	private LinearLayout linear51;
	private ImageView imageview17;
	private TextView textview26;
	private TextView textview27;
	private LinearLayout linear330;
	private LinearLayout linear331;
	private ImageView imageview75;
	private TextView textview90;
	private TextView textview91;
	private FrameLayout linear119;
	private MultiCoreGraphView linear120;
	private LinearLayout linear121;
	private TextView textview69;
	private TextView textview68;
	private LinearLayout linear46;
	private LinearLayout linear47;
	private LinearLayout linear48;
	private LinearLayout linear53;
	private LinearLayout linear54;
	private ImageView imageview13;
	private TextView textview22;
	private ImageView imageview14;
	private ImageView imageview15;
	private TextView textview23;
	private ImageView imageview16;
	private ImageView imageview18;
	private TextView textview28;
	private ImageView imageview19;
	private LinearLayout linear79;
	private ScrollView vscroll3;
	private TextView textview45;
	private LinearLayout linear81;
	private LinearLayout linear82;
	private LinearLayout linear85;
	private LinearLayout linear88;
	private LinearLayout linear91;
	private LinearLayout linear129;
	private LinearLayout linear139;
	private LinearLayout linear148;
	private LinearLayout linear124;
	private LinearLayout linear325;
	private LinearLayout linear83;
	private LinearLayout linear106;
	private LinearLayout linear112;
	private ImageView imageview29;
	private TextView textview46;
	private TextView textview47;
	private LinearLayout linear107;
	private LinearLayout linear108;
	private TextView textview61;
	private TextView textview62;
	private LinearLayout linear86;
	private LinearLayout linear87;
	private LinearLayout linear113;
	private ImageView imageview31;
	private TextView textview49;
	private TextView textview50;
	private ImageView imageview30;
	private TextView textview51;
	private ImageView imageview36;
	private LinearLayout linear89;
	private LinearLayout linear116;
	private LinearLayout linear114;
	private ImageView imageview33;
	private TextView textview52;
	private TextView textview53;
	private LinearLayout linear117;
	private LinearLayout linear118;
	private TextView textview65;
	private TextView textview66;
	private LinearLayout linear92;
	private LinearLayout linear93;
	private LinearLayout linear115;
	private ImageView imageview35;
	private TextView textview55;
	private TextView textview56;
	private ImageView imageview34;
	private TextView textview57;
	private ImageView imageview37;
	private LinearLayout linear130;
	private LinearLayout opsippi;
	private LinearLayout linear132;
	private ImageView imageview40;
	private TextView textview73;
	private TextView textview74;
	private LinearLayout linear135;
	private LinearLayout linear143;
	private LinearLayout linear133;
	private LinearLayout linear136;
	private LinearLayout linear137;
	private LinearLayout linear138;
	private EditText edittext1;
	private ImageView imageview41;
	private EditText edittext2;
	private TextView textview78;
	private TextView textview75;
	private LinearLayout linear140;
	private LinearLayout linear141;
	private LinearLayout linear142;
	private ImageView imageview42;
	private TextView textview76;
	private TextView textview77;
	private ImageView imageview43;
	private LinearLayout linear149;
	private LinearLayout linear150;
	private LinearLayout linear151;
	private ImageView imageview45;
	private TextView textview81;
	private TextView textview82;
	private Switch switch7;
	private LinearLayout linear125;
	private LinearLayout linear128;
	private LinearLayout linear127;
	private ImageView imageview39;
	private TextView textview71;
	private TextView textview72;
	private Switch switch5;
	private LinearLayout linear152;
	private LinearLayout bggyro;
	private LinearLayout linear327;
	private LinearLayout linear283;
	private LinearLayout bgcrs;
	private LinearLayout linear153;
	private LinearLayout linear282;
	private LinearLayout linear155;
	private ImageView imageview46;
	private TextView textview83;
	private TextView textview84;
	private Switch switch9;
	private LinearLayout crosh;
	private LinearLayout linear156;
	private LinearLayout linear276;
	private LinearLayout linear157;
	private LinearLayout linear158;
	private LinearLayout linear159;
	private SeekBar seekbar1;
	private TextView textview44;
	private TextView txt_percent;
	private HorizontalScrollView hscroll1;
	private LinearLayout linear56;
	private LinearLayout linear57;
	private LinearLayout linear58;
	private LinearLayout linear59;
	private LinearLayout linear60;
	private LinearLayout linear61;
	private LinearLayout linear279;
	private LinearLayout linear280;
	private ImageView imageview48;
	private ImageView imageview49;
	private ImageView imageview50;
	private ImageView imageview51;
	private ImageView imageview52;
	private ImageView imageview59;
	private ImageView imageview60;
	private LinearLayout linear160;
	private LinearLayout linear161;
	private TextView textview85;
	private LinearLayout red;
	private LinearLayout pink;
	private LinearLayout ungu;
	private LinearLayout blue;
	private LinearLayout black;
	private LinearLayout white;
	private LinearLayout linear55;
	private LinearLayout linear62;
	private LinearLayout linear63;
	private LinearLayout linear64;
	private TextView textview86;
	private ImageView imageview47;
	private LinearLayout linear65;
	private LinearLayout linear67;
	private LinearLayout linear68;
	private ImageView atas;
	private ImageView kiri;
	private LinearLayout linear69;
	private ImageView kanan;
	private ImageView bawah;
	private LinearLayout linear71;
	private LinearLayout linear72;
	private TextView textview87;
	private TextView nilaix;
	private TextView textview48;
	private TextView nilaiy;
	private LinearLayout linear322;
	private LinearLayout linear323;
	private LinearLayout linear324;
	private ImageView imageview74;
	private TextView textview88;
	private TextView textview89;
	private Switch switch12;
	
	private SharedPreferences listmanager;
	private TimerTask time;
	private Intent i = new Intent();
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.settings_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		vscroll1 = _view.findViewById(R.id.vscroll1);
		linear5 = _view.findViewById(R.id.linear5);
		linear326 = _view.findViewById(R.id.linear326);
		linear123 = _view.findViewById(R.id.linear123);
		settings = _view.findViewById(R.id.settings);
		list = _view.findViewById(R.id.list);
		imageview38 = _view.findViewById(R.id.imageview38);
		textview70 = _view.findViewById(R.id.textview70);
		segmented = _view.findViewById(R.id.segmented);
		linear35 = _view.findViewById(R.id.linear35);
		linear328 = _view.findViewById(R.id.linear328);
		linear49 = _view.findViewById(R.id.linear49);
		linear329 = _view.findViewById(R.id.linear329);
		textview67 = _view.findViewById(R.id.textview67);
		cardview1 = _view.findViewById(R.id.cardview1);
		linear122 = _view.findViewById(R.id.linear122);
		textview24 = _view.findViewById(R.id.textview24);
		linear45 = _view.findViewById(R.id.linear45);
		relativelayout2 = _view.findViewById(R.id.relativelayout2);
		imageview10 = _view.findViewById(R.id.imageview10);
		linear36 = _view.findViewById(R.id.linear36);
		linear37 = _view.findViewById(R.id.linear37);
		linear39 = _view.findViewById(R.id.linear39);
		linear40 = _view.findViewById(R.id.linear40);
		linear41 = _view.findViewById(R.id.linear41);
		linear42 = _view.findViewById(R.id.linear42);
		imageview11 = _view.findViewById(R.id.imageview11);
		imageview12 = _view.findViewById(R.id.imageview12);
		textview18 = _view.findViewById(R.id.textview18);
		textview19 = _view.findViewById(R.id.textview19);
		linear43 = _view.findViewById(R.id.linear43);
		linear44 = _view.findViewById(R.id.linear44);
		textview20 = _view.findViewById(R.id.textview20);
		textview21 = _view.findViewById(R.id.textview21);
		textview25 = _view.findViewById(R.id.textview25);
		linear332 = _view.findViewById(R.id.linear332);
		textview92 = _view.findViewById(R.id.textview92);
		imageview76 = _view.findViewById(R.id.imageview76);
		linear50 = _view.findViewById(R.id.linear50);
		linear51 = _view.findViewById(R.id.linear51);
		imageview17 = _view.findViewById(R.id.imageview17);
		textview26 = _view.findViewById(R.id.textview26);
		textview27 = _view.findViewById(R.id.textview27);
		linear330 = _view.findViewById(R.id.linear330);
		linear331 = _view.findViewById(R.id.linear331);
		imageview75 = _view.findViewById(R.id.imageview75);
		textview90 = _view.findViewById(R.id.textview90);
		textview91 = _view.findViewById(R.id.textview91);
		linear119 = _view.findViewById(R.id.linear119);
		linear120 = _view.findViewById(R.id.linear120);
		linear121 = _view.findViewById(R.id.linear121);
		textview69 = _view.findViewById(R.id.textview69);
		textview68 = _view.findViewById(R.id.textview68);
		linear46 = _view.findViewById(R.id.linear46);
		linear47 = _view.findViewById(R.id.linear47);
		linear48 = _view.findViewById(R.id.linear48);
		linear53 = _view.findViewById(R.id.linear53);
		linear54 = _view.findViewById(R.id.linear54);
		imageview13 = _view.findViewById(R.id.imageview13);
		textview22 = _view.findViewById(R.id.textview22);
		imageview14 = _view.findViewById(R.id.imageview14);
		imageview15 = _view.findViewById(R.id.imageview15);
		textview23 = _view.findViewById(R.id.textview23);
		imageview16 = _view.findViewById(R.id.imageview16);
		imageview18 = _view.findViewById(R.id.imageview18);
		textview28 = _view.findViewById(R.id.textview28);
		imageview19 = _view.findViewById(R.id.imageview19);
		linear79 = _view.findViewById(R.id.linear79);
		vscroll3 = _view.findViewById(R.id.vscroll3);
		textview45 = _view.findViewById(R.id.textview45);
		linear81 = _view.findViewById(R.id.linear81);
		linear82 = _view.findViewById(R.id.linear82);
		linear85 = _view.findViewById(R.id.linear85);
		linear88 = _view.findViewById(R.id.linear88);
		linear91 = _view.findViewById(R.id.linear91);
		linear129 = _view.findViewById(R.id.linear129);
		linear139 = _view.findViewById(R.id.linear139);
		linear148 = _view.findViewById(R.id.linear148);
		linear124 = _view.findViewById(R.id.linear124);
		linear325 = _view.findViewById(R.id.linear325);
		linear83 = _view.findViewById(R.id.linear83);
		linear106 = _view.findViewById(R.id.linear106);
		linear112 = _view.findViewById(R.id.linear112);
		imageview29 = _view.findViewById(R.id.imageview29);
		textview46 = _view.findViewById(R.id.textview46);
		textview47 = _view.findViewById(R.id.textview47);
		linear107 = _view.findViewById(R.id.linear107);
		linear108 = _view.findViewById(R.id.linear108);
		textview61 = _view.findViewById(R.id.textview61);
		textview62 = _view.findViewById(R.id.textview62);
		linear86 = _view.findViewById(R.id.linear86);
		linear87 = _view.findViewById(R.id.linear87);
		linear113 = _view.findViewById(R.id.linear113);
		imageview31 = _view.findViewById(R.id.imageview31);
		textview49 = _view.findViewById(R.id.textview49);
		textview50 = _view.findViewById(R.id.textview50);
		imageview30 = _view.findViewById(R.id.imageview30);
		textview51 = _view.findViewById(R.id.textview51);
		imageview36 = _view.findViewById(R.id.imageview36);
		linear89 = _view.findViewById(R.id.linear89);
		linear116 = _view.findViewById(R.id.linear116);
		linear114 = _view.findViewById(R.id.linear114);
		imageview33 = _view.findViewById(R.id.imageview33);
		textview52 = _view.findViewById(R.id.textview52);
		textview53 = _view.findViewById(R.id.textview53);
		linear117 = _view.findViewById(R.id.linear117);
		linear118 = _view.findViewById(R.id.linear118);
		textview65 = _view.findViewById(R.id.textview65);
		textview66 = _view.findViewById(R.id.textview66);
		linear92 = _view.findViewById(R.id.linear92);
		linear93 = _view.findViewById(R.id.linear93);
		linear115 = _view.findViewById(R.id.linear115);
		imageview35 = _view.findViewById(R.id.imageview35);
		textview55 = _view.findViewById(R.id.textview55);
		textview56 = _view.findViewById(R.id.textview56);
		imageview34 = _view.findViewById(R.id.imageview34);
		textview57 = _view.findViewById(R.id.textview57);
		imageview37 = _view.findViewById(R.id.imageview37);
		linear130 = _view.findViewById(R.id.linear130);
		opsippi = _view.findViewById(R.id.opsippi);
		linear132 = _view.findViewById(R.id.linear132);
		imageview40 = _view.findViewById(R.id.imageview40);
		textview73 = _view.findViewById(R.id.textview73);
		textview74 = _view.findViewById(R.id.textview74);
		linear135 = _view.findViewById(R.id.linear135);
		linear143 = _view.findViewById(R.id.linear143);
		linear133 = _view.findViewById(R.id.linear133);
		linear136 = _view.findViewById(R.id.linear136);
		linear137 = _view.findViewById(R.id.linear137);
		linear138 = _view.findViewById(R.id.linear138);
		edittext1 = _view.findViewById(R.id.edittext1);
		imageview41 = _view.findViewById(R.id.imageview41);
		edittext2 = _view.findViewById(R.id.edittext2);
		textview78 = _view.findViewById(R.id.textview78);
		textview75 = _view.findViewById(R.id.textview75);
		linear140 = _view.findViewById(R.id.linear140);
		linear141 = _view.findViewById(R.id.linear141);
		linear142 = _view.findViewById(R.id.linear142);
		imageview42 = _view.findViewById(R.id.imageview42);
		textview76 = _view.findViewById(R.id.textview76);
		textview77 = _view.findViewById(R.id.textview77);
		imageview43 = _view.findViewById(R.id.imageview43);
		linear149 = _view.findViewById(R.id.linear149);
		linear150 = _view.findViewById(R.id.linear150);
		linear151 = _view.findViewById(R.id.linear151);
		imageview45 = _view.findViewById(R.id.imageview45);
		textview81 = _view.findViewById(R.id.textview81);
		textview82 = _view.findViewById(R.id.textview82);
		switch7 = _view.findViewById(R.id.switch7);
		linear125 = _view.findViewById(R.id.linear125);
		linear128 = _view.findViewById(R.id.linear128);
		linear127 = _view.findViewById(R.id.linear127);
		imageview39 = _view.findViewById(R.id.imageview39);
		textview71 = _view.findViewById(R.id.textview71);
		textview72 = _view.findViewById(R.id.textview72);
		switch5 = _view.findViewById(R.id.switch5);
		linear152 = _view.findViewById(R.id.linear152);
		bggyro = _view.findViewById(R.id.bggyro);
		linear327 = _view.findViewById(R.id.linear327);
		linear283 = _view.findViewById(R.id.linear283);
		bgcrs = _view.findViewById(R.id.bgcrs);
		linear153 = _view.findViewById(R.id.linear153);
		linear282 = _view.findViewById(R.id.linear282);
		linear155 = _view.findViewById(R.id.linear155);
		imageview46 = _view.findViewById(R.id.imageview46);
		textview83 = _view.findViewById(R.id.textview83);
		textview84 = _view.findViewById(R.id.textview84);
		switch9 = _view.findViewById(R.id.switch9);
		crosh = _view.findViewById(R.id.crosh);
		linear156 = _view.findViewById(R.id.linear156);
		linear276 = _view.findViewById(R.id.linear276);
		linear157 = _view.findViewById(R.id.linear157);
		linear158 = _view.findViewById(R.id.linear158);
		linear159 = _view.findViewById(R.id.linear159);
		seekbar1 = _view.findViewById(R.id.seekbar1);
		textview44 = _view.findViewById(R.id.textview44);
		txt_percent = _view.findViewById(R.id.txt_percent);
		hscroll1 = _view.findViewById(R.id.hscroll1);
		linear56 = _view.findViewById(R.id.linear56);
		linear57 = _view.findViewById(R.id.linear57);
		linear58 = _view.findViewById(R.id.linear58);
		linear59 = _view.findViewById(R.id.linear59);
		linear60 = _view.findViewById(R.id.linear60);
		linear61 = _view.findViewById(R.id.linear61);
		linear279 = _view.findViewById(R.id.linear279);
		linear280 = _view.findViewById(R.id.linear280);
		imageview48 = _view.findViewById(R.id.imageview48);
		imageview49 = _view.findViewById(R.id.imageview49);
		imageview50 = _view.findViewById(R.id.imageview50);
		imageview51 = _view.findViewById(R.id.imageview51);
		imageview52 = _view.findViewById(R.id.imageview52);
		imageview59 = _view.findViewById(R.id.imageview59);
		imageview60 = _view.findViewById(R.id.imageview60);
		linear160 = _view.findViewById(R.id.linear160);
		linear161 = _view.findViewById(R.id.linear161);
		textview85 = _view.findViewById(R.id.textview85);
		red = _view.findViewById(R.id.red);
		pink = _view.findViewById(R.id.pink);
		ungu = _view.findViewById(R.id.ungu);
		blue = _view.findViewById(R.id.blue);
		black = _view.findViewById(R.id.black);
		white = _view.findViewById(R.id.white);
		linear55 = _view.findViewById(R.id.linear55);
		linear62 = _view.findViewById(R.id.linear62);
		linear63 = _view.findViewById(R.id.linear63);
		linear64 = _view.findViewById(R.id.linear64);
		textview86 = _view.findViewById(R.id.textview86);
		imageview47 = _view.findViewById(R.id.imageview47);
		linear65 = _view.findViewById(R.id.linear65);
		linear67 = _view.findViewById(R.id.linear67);
		linear68 = _view.findViewById(R.id.linear68);
		atas = _view.findViewById(R.id.atas);
		kiri = _view.findViewById(R.id.kiri);
		linear69 = _view.findViewById(R.id.linear69);
		kanan = _view.findViewById(R.id.kanan);
		bawah = _view.findViewById(R.id.bawah);
		linear71 = _view.findViewById(R.id.linear71);
		linear72 = _view.findViewById(R.id.linear72);
		textview87 = _view.findViewById(R.id.textview87);
		nilaix = _view.findViewById(R.id.nilaix);
		textview48 = _view.findViewById(R.id.textview48);
		nilaiy = _view.findViewById(R.id.nilaiy);
		linear322 = _view.findViewById(R.id.linear322);
		linear323 = _view.findViewById(R.id.linear323);
		linear324 = _view.findViewById(R.id.linear324);
		imageview74 = _view.findViewById(R.id.imageview74);
		textview88 = _view.findViewById(R.id.textview88);
		textview89 = _view.findViewById(R.id.textview89);
		switch12 = _view.findViewById(R.id.switch12);
		listmanager = getContext().getSharedPreferences("listmanager", Activity.MODE_PRIVATE);
		
		linear49.setOnClickListener(_v -> {
			if (isRootAvailable()) {
				try {
					String packageName = "com.hypers.hm";
					String cmd = "pm enable " + packageName;
					
					Process p = Runtime.getRuntime().exec("su");
					java.io.DataOutputStream os = new java.io.DataOutputStream(p.getOutputStream());
					
					os.writeBytes(cmd + "\n");
					os.writeBytes("exit\n");
					os.flush();
					
					int exitVal = p.waitFor();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} else if (isShizukuAvailable()) {
				if (!Shizuku.pingBinder()) {
					Intent i = requireContext().getPackageManager().getLaunchIntentForPackage("moe.shizuku.privileged.api");
					if (i != null) {
						requireContext().startActivity(i);
					} else {
						requireContext().startActivity(new Intent(Intent.ACTION_VIEW,
						Uri.parse("market://details?id=moe.shizuku.privileged.api")));
					}
				} else {
					if (Shizuku.checkSelfPermission() != PackageManager.PERMISSION_GRANTED) {
						Shizuku.requestPermission(1001);
					}
				}
				
			} else {
				new androidx.appcompat.app.AlertDialog.Builder(requireContext())
				.setTitle("Akses Not Granted")
				.setMessage("Please activate shizuku permission first/root superuser to get system level privileged access!!!!")
				.setPositiveButton("CANCEL", (dialog, which) -> dialog.dismiss())
				.setCancelable(false)
				.show();
			}
			
			_click(linear49);
		});
		
		linear329.setOnClickListener(_v -> {
			File script =
			new File(
			getActivity().getExternalFilesDir(null),
			"start.sh"
			);
			
			String content =
			
			"#!/system/bin/sh\n"
			
			+
			
			"CLASSPATH="
			+ getActivity().getPackageCodePath()
			+ "\n"
			
			+
			
			"exec app_process / com.hypers.hm.Starter\n";
			
			try {
				
				java.io.FileOutputStream fos =
				new java.io.FileOutputStream(
				script
				);
				
				fos.write(
				content.getBytes()
				);
				
				fos.close();
				
				script.setExecutable(true);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			i.setClass(getContext().getApplicationContext(), AdbpairActivity.class);
			startActivity(i);
		});
		
		//OnTouch
		linear332.setOnTouchListener(new View.OnTouchListener(){
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
		
		imageview76.setOnClickListener(_v -> {
			DisplayMetrics dm = requireContext().getResources().getDisplayMetrics();
			float scale = dm.density;
			
			Typeface bold = Typeface.createFromAsset(requireContext().getAssets(), "fonts/gffbold.ttf");
			Typeface regular = Typeface.createFromAsset(requireContext().getAssets(), "fonts/gffregular.ttf");
			
			// ROOT LAYOUT
			LinearLayout root = new LinearLayout(requireContext());
			root.setOrientation(LinearLayout.VERTICAL);
			root.setPadding(dp(26), dp(26), dp(26), dp(26));
			
			// BACKGROUND DIALOG
			GradientDrawable bg = new GradientDrawable();
			bg.setColor(0xFF181818);
			bg.setCornerRadius(dp(28));
			root.setBackground(bg);
			
			// TITLE & SUBTITLE
			TextView title = new TextView(requireContext());
			title.setText("Select Service");
			title.setTextSize(18);
			title.setTypeface(bold);
			title.setTextColor(0xFFFFFFFF);
			root.addView(title);
			
			TextView sub = new TextView(requireContext());
			sub.setText("Selecting Service Type for HM App");
			sub.setTextSize(12);
			sub.setTypeface(regular);
			sub.setTextColor(0xFF9E9E9E);
			sub.setPadding(dp(4), dp(4), dp(4), dp(20));
			root.addView(sub);
			
			// CONTAINER LIST
			LinearLayout container = new LinearLayout(requireContext());
			container.setOrientation(LinearLayout.VERTICAL);
			
			// LOGIC OPTIONS (Sesuai permintaanmu: Shizuku & Adb)
			String[] options = {"Shizuku", "Adb/Wireless"};
			String[] descriptions = {"Use Shizuku API (Safe & Fast)", "Native ADB Engine (No PC needed)"};
			final int[] selectedIndex = {0};
			
			ArrayList<LinearLayout> items = new ArrayList<>();
			ArrayList<RadioButton> radios = new ArrayList<>();
			
			for (int i = 0; i < options.length; i++) {
				LinearLayout item = new LinearLayout(requireContext());
				item.setOrientation(LinearLayout.HORIZONTAL);
				item.setGravity(Gravity.CENTER_VERTICAL);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
				lp.setMargins(dp(4), dp(4), dp(4), dp(12));
				item.setLayoutParams(lp);
				item.setPadding(dp(16), dp(14), dp(16), dp(14));
				
				GradientDrawable normal = new GradientDrawable();
				normal.setCornerRadius(dp(20));
				normal.setColor(0xFF1F1F1F);
				normal.setStroke(dp(1), 0xFF2E2E2E);
				item.setBackground(normal);
				
				LinearLayout txtLayout = new LinearLayout(requireContext());
				txtLayout.setOrientation(LinearLayout.VERTICAL);
				txtLayout.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1));
				
				TextView tvMain = new TextView(requireContext());
				tvMain.setText(options[i]);
				tvMain.setTextSize(14);
				tvMain.setTypeface(bold);
				tvMain.setTextColor(0xFF9E9E9E);
				
				TextView tvDesc = new TextView(requireContext());
				tvDesc.setText(descriptions[i]);
				tvDesc.setTextSize(10);
				tvDesc.setTypeface(regular);
				tvDesc.setTextColor(0xFF757575);
				
				txtLayout.addView(tvMain);
				txtLayout.addView(tvDesc);
				
				RadioButton rb = new RadioButton(requireContext());
				rb.setClickable(false);
				rb.setButtonTintList(ColorStateList.valueOf(0xFF4DB6AC));
				
				item.addView(txtLayout);
				item.addView(rb);
				container.addView(item);
				items.add(item);
				radios.add(rb);
				
				int index = i;
				item.setOnClickListener(v -> {
					selectedIndex[0] = index;
					for (int j = 0; j < items.size(); j++) {
						GradientDrawable n = new GradientDrawable();
						n.setCornerRadius(dp(20));
						n.setColor(0xFF1F1F1F);
						n.setStroke(dp(1), 0xFF2E2E2E);
						items.get(j).setBackground(n);
						radios.get(j).setChecked(false);
					}
					GradientDrawable sel = new GradientDrawable();
					sel.setCornerRadius(dp(20));
					sel.setColor(0xFF1E3A38);
					sel.setStroke(dp(1), 0xFF4DB6AC);
					item.setBackground(sel);
					rb.setChecked(true);
				});
			}
			root.addView(container);
			
			// BUTTON
			TextView apply = new TextView(requireContext());
			apply.setText("APPLY");
			apply.setTypeface(bold);
			apply.setGravity(Gravity.CENTER);
			apply.setTextColor(0xFF181818);
			apply.setPadding(0, dp(12), 0, dp(12));
			LinearLayout.LayoutParams abp = new LinearLayout.LayoutParams(-1, -2);
			abp.setMargins(0, dp(12), 0, 0);
			apply.setLayoutParams(abp);
			
			GradientDrawable applyBg = new GradientDrawable();
			applyBg.setCornerRadius(dp(25));
			applyBg.setColor(0xFF4DB6AC);
			apply.setBackground(applyBg);
			root.addView(apply);
			
			// --- TAHAP FIXING DISPLAY ---
			AlertDialog dialog = new AlertDialog.Builder(requireContext()).create();
			
			// 1. Panggil show dulu!
			dialog.show();
			
			// 2. Set content setelah show agar tidak bug size
			dialog.setContentView(root);
			
			// 3. Atur Window secara manual
			Window window = dialog.getWindow();
			if (window != null) {
				window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				// Paksa lebar ke 90% dari pixel layar
				window.setLayout((int)(dm.widthPixels * 0.90f), ViewGroup.LayoutParams.WRAP_CONTENT);
			}
			
			// RESTORE & APPLY LOGIC
			String saved = listmanager.getString("aktifitas", "Adb");
			for (int i = 0; i < options.length; i++) {
				if (options[i].equalsIgnoreCase(saved)) {
					items.get(i).performClick();
					break;
				}
			}
			
			apply.setOnClickListener(v -> {
				int choice = selectedIndex[0];
				listmanager.edit().putString("aktifitas", options[choice]).apply();
				
				// Switch Visibility sesuai yang kamu mau
				if (choice == 0) {
					linear49.setVisibility(View.VISIBLE);
					linear329.setVisibility(View.GONE);
				} else {
					linear49.setVisibility(View.GONE);
					linear329.setVisibility(View.VISIBLE);
				}
				dialog.dismiss();
			});
			
		});
		
		imageview14.setOnClickListener(_v -> {
			final com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog =
			new com.google.android.material.bottomsheet.BottomSheetDialog(requireActivity());
			
			View bottomSheetView = getActivity().getLayoutInflater().inflate(R.layout.dev, null);
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
			LinearLayout linear2 = bottomSheetView.findViewById(R.id.linear2);
			LinearLayout linear3 = bottomSheetView.findViewById(R.id.linear3);
			LinearLayout linear4 = bottomSheetView.findViewById(R.id.linear4);
			LinearLayout linear5 = bottomSheetView.findViewById(R.id.linear5);
			LinearLayout linear6 = bottomSheetView.findViewById(R.id.linear6);
			LinearLayout linear7 = bottomSheetView.findViewById(R.id.linear7);
			LinearLayout linear8 = bottomSheetView.findViewById(R.id.linear8);
			LinearLayout linear9 = bottomSheetView.findViewById(R.id.linear9);
			LinearLayout linear10 = bottomSheetView.findViewById(R.id.linear10);
			LinearLayout linear11 = bottomSheetView.findViewById(R.id.linear11);
			LinearLayout linear12 = bottomSheetView.findViewById(R.id.linear12);
			LinearLayout linear13 = bottomSheetView.findViewById(R.id.linear13);
			LinearLayout linear14 = bottomSheetView.findViewById(R.id.linear14);
			LinearLayout linear15 = bottomSheetView.findViewById(R.id.linear15);
			LinearLayout linear17 = bottomSheetView.findViewById(R.id.linear17);
			LinearLayout linear18 = bottomSheetView.findViewById(R.id.linear18);
			
			ImageView imageview1 = bottomSheetView.findViewById(R.id.imageview1);
			ImageView imageview2 = bottomSheetView.findViewById(R.id.imageview2);
			ImageView imageview3 = bottomSheetView.findViewById(R.id.imageview3);
			ImageView imageview4 = bottomSheetView.findViewById(R.id.imageview4);
			ImageView imageview5 = bottomSheetView.findViewById(R.id.imageview5);
			ImageView imageview6 = bottomSheetView.findViewById(R.id.imageview6);
			ImageView imageview7 = bottomSheetView.findViewById(R.id.imageview7);
			ImageView imageview8 = bottomSheetView.findViewById(R.id.imageview7);
			
			TextView textview1 = bottomSheetView.findViewById(R.id.textview1);
			TextView textview2 = bottomSheetView.findViewById(R.id.textview2);
			TextView textview3 = bottomSheetView.findViewById(R.id.textview3);
			TextView textview4 = bottomSheetView.findViewById(R.id.textview4);
			TextView textview5 = bottomSheetView.findViewById(R.id.textview5);
			TextView textview6 = bottomSheetView.findViewById(R.id.textview6);
			TextView textview7 = bottomSheetView.findViewById(R.id.textview7);
			TextView textview8 = bottomSheetView.findViewById(R.id.textview8);
			TextView textview9 = bottomSheetView.findViewById(R.id.textview9);
			TextView textview10 = bottomSheetView.findViewById(R.id.textview10);
			TextView textview11 = bottomSheetView.findViewById(R.id.textview11);
			TextView textview12 = bottomSheetView.findViewById(R.id.textview12);
			{
				android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
				int d = (int) getContext().getApplicationContext().getResources().getDisplayMetrics().density;
				SketchUi.setColor(0xFF131313);SketchUi.setCornerRadii(new float[]{
					d*28,d*28,d*28 ,d*28,d*0,d*0 ,d*0,d*0});
				linear1.setElevation(d*5);
				linear1.setBackground(SketchUi);
			}
			textview1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
			textview2.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
			textview3.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
			textview4.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
			textview5.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
			textview6.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
			textview7.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
			textview8.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
			textview9.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
			textview10.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
			textview11.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
			textview12.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
			linear15.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)60, 0xFF05C7C2));
			linear6.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
			linear7.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
			linear8.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
			linear17.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
			linear9.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0x800C272A));
			linear11.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0x800C272A));
			linear13.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0x800C272A));
			linear18.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0x800C272A));
			bottomSheetDialog.setCancelable(true);
			bottomSheetDialog.show();
		});
		
		imageview16.setOnClickListener(_v -> {
			final com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog =
			new com.google.android.material.bottomsheet.BottomSheetDialog(requireActivity());
			
			View bottomSheetView = getActivity().getLayoutInflater().inflate(R.layout.ppc, null);
			bottomSheetDialog.setContentView(bottomSheetView);
			
			bottomSheetDialog.setOnShowListener(dialog -> {
				View sheet = bottomSheetDialog.findViewById(
				com.google.android.material.R.id.design_bottom_sheet
				);
				
				if (sheet != null) {
					int radius = (int) (28 * getResources().getDisplayMetrics().density);
					
					android.graphics.drawable.GradientDrawable bg =
					new android.graphics.drawable.GradientDrawable();
					
					bg.setColor(0xFF131313);
					bg.setCornerRadius(radius);
					
					sheet.setBackground(bg);
				}
			});
			
			bottomSheetDialog.show();
			LinearLayout linear1 = bottomSheetView.findViewById(R.id.linear1);
			LinearLayout linear2 = bottomSheetView.findViewById(R.id.linear2);
			
			ImageView imageview1 = bottomSheetView.findViewById(R.id.imageview1);
			
			TextView textview1 = bottomSheetView.findViewById(R.id.textview1);
			TextView textview2 = bottomSheetView.findViewById(R.id.textview2);
			
			WebView webview1 = bottomSheetView.findViewById(R.id.webview1);
			webview1.getSettings().setJavaScriptEnabled(false);
			webview1.getSettings().setDomStorageEnabled(true);
			webview1.getSettings().setLoadWithOverviewMode(true);
			webview1.getSettings().setUseWideViewPort(true);
			webview1.setVerticalScrollBarEnabled(false);
			webview1.setHorizontalScrollBarEnabled(false);
			
			String html = "<!DOCTYPE html>" +
			"<html><head>" +
			"<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
			
			"<style>" +
			
			"body{" +
			"margin:0;" +
			"padding:20px 16px;" +
			"font-family:sans-serif;" +
			"background:#131313;" +
			"color:#BDD4E0;" +
			"line-height:1.7;" +
			"}" +
			
			"h1{" +
			"font-size:22px;" +
			"font-weight:bold;" +
			"color:#FFFFFF;" +
			"margin-bottom:6px;" +
			"}" +
			
			".date{" +
			"font-size:13px;" +
			"color:#767575;" +
			"margin-bottom:20px;" +
			"}" +
			
			".divider{" +
			"height:1px;" +
			"background:#2C2C2C;" +
			"margin:16px 0 24px 0;" +
			"}" +
			
			"p{" +
			"font-size:14px;" +
			"margin-bottom:16px;" +
			"}" +
			
			"strong{" +
			"color:#FFFFFF;" +
			"font-weight:bold;" +
			"}" +
			
			".section{" +
			"margin-bottom:28px;" +
			"}" +
			
			"h2{" +
			"font-size:16px;" +
			"font-weight:bold;" +
			"color:#FFFFFF;" +
			"margin-bottom:12px;" +
			"padding-left:12px;" +
			"border-left:3px solid #38B2A5;" +
			"}" +
			
			"ul{" +
			"padding-left:18px;" +
			"}" +
			
			"li{" +
			"margin-bottom:12px;" +
			"font-size:14px;" +
			"}" +
			
			"a{" +
			"color:#7EF0E2;" +
			"text-decoration:none;" +
			"}" +
			
			".footer{" +
			"text-align:center;" +
			"font-size:12px;" +
			"color:#767575;" +
			"margin-top:40px;" +
			"}" +
			
			".note{" +
			"background:#1E1E1E;" +
			"padding:12px 16px;" +
			"border-radius:8px;" +
			"margin:16px 0;" +
			"font-size:13px;" +
			"border-left:3px solid #F59E0B;" +
			"}" +
			
			"</style>" +
			"</head><body>" +
			
			"<h1>Privacy Policy</h1>" +
			"<div class='date'>Last updated: April 2026 • Hypers-Manager</div>" +
			
			"<div class='divider'></div>" +
			
			"<p>Thank you for using our application <strong>Hypers-Manager</strong>. Your privacy is important to us. This Privacy Policy explains how we collect, use, and protect your information when you use our App.</p>" +
			
			"<div class='section'>" +
			"<h2>1. Information We Collect</h2>" +
			"<p>We may collect the following types of information:</p>" +
			"<ul>" +
			"<li><strong>Device Information:</strong> Device model, Android version, system performance data (CPU usage, memory usage, network latency/ping). Used strictly for performance monitoring and optimization features.</li>" +
			"<li><strong>App Usage Data:</strong> Features used inside the App, plugin usage activity. Helps improve app functionality and stability.</li>" +
			"<li><strong>Files and Storage Access:</strong> If you use plugin installation (ZIP files) or local file reading, the App may access storage only when you explicitly choose files. We do NOT access your files without your permission.</li>" +
			"</ul>" +
			"</div>" +
			
			"<div class='section'>" +
			"<h2>2. How We Use Your Information</h2>" +
			"<p>We use collected information to:</p>" +
			"<ul>" +
			"<li>Provide core app functionality</li>" +
			"<li>Display real-time performance metrics</li>" +
			"<li>Execute user-requested commands (e.g., shell commands)</li>" +
			"<li>Manage and run plugins</li>" +
			"<li>Improve stability and performance</li>" +
			"</ul>" +
			"</div>" +
			
			"<div class='section'>" +
			"<h2>3. Sensitive Permissions & User Control</h2>" +
			"<p>Our App may request permissions such as:</p>" +
			"<ul>" +
			"<li>Storage access (for plugins/files)</li>" +
			"<li>System-level access (via Shizuku or similar tools)</li>" +
			"</ul>" +
			"<p>These are used ONLY when required by features you choose.</p>" +
			"<p><strong>We do NOT:</strong></p>" +
			"<ul>" +
			"<li>Collect personal data like contacts, messages, or photos</li>" +
			"<li>Track your location</li>" +
			"<li>Access data without your consent</li>" +
			"</ul>" +
			"</div>" +
			
			"<div class='section'>" +
			"<h2>4. Third-Party Services</h2>" +
			"<p>The App may optionally connect to custom plugin servers (for downloading plugins). We do NOT sell or share your personal data with third parties.</p>" +
			"</div>" +
			
			"<div class='section'>" +
			"<h2>5. Data Security</h2>" +
			"<p>We take reasonable steps to protect your data:</p>" +
			"<ul>" +
			"<li>No unnecessary data storage</li>" +
			"<li>No background data collection without user action</li>" +
			"<li>Local processing whenever possible</li>" +
			"</ul>" +
			"</div>" +
			
			"<div class='section'>" +
			"<h2>6. User Responsibility</h2>" +
			"<p>Some features (such as shell commands or plugins) allow advanced system interaction. You agree that:</p>" +
			"<ul>" +
			"<li>You use these features at your own risk</li>" +
			"<li>You understand the commands/plugins you execute</li>" +
			"</ul>" +
			"<p>We are not responsible for damage caused by misuse.</p>" +
			"</div>" +
			
			"<div class='section'>" +
			"<h2>7. Children's Privacy</h2>" +
			"<p>This App is not intended for users under 13 years old.</p>" +
			"</div>" +
			
			"<div class='section'>" +
			"<h2>8. Changes to This Policy</h2>" +
			"<p>We may update this Privacy Policy. Changes will be reflected in this page.</p>" +
			"</div>" +
			
			"<div class='section'>" +
			"<h2>9. Contact Us</h2>" +
			"<p>If you have questions, contact us at: <a href='mailto:hypers-manager@gmail.com'>hypers-manager@gmail.com</a></p>" +
			"</div>" +
			
			"<div class='divider'></div>" +
			
			"<h2>Feature Explanation (Legal & Transparency)</h2>" +
			
			"<div class='section'>" +
			"<h2>1. Performance Monitor</h2>" +
			"<ul>" +
			"<li>Displays real-time CPU usage, RAM usage, and network ping</li>" +
			"<li>Helps users understand device performance</li>" +
			"<li>Does NOT collect or send personal data</li>" +
			"</ul>" +
			"</div>" +
			
			"<div class='section'>" +
			"<h2>2. Game Booster / Optimization</h2>" +
			"<ul>" +
			"<li>Adjusts system parameters locally to improve performance</li>" +
			"<li>Runs only when activated by user</li>" +
			"<li>No background tracking</li>" +
			"</ul>" +
			"</div>" +
			
			"<div class='section'>" +
			"<h2>3. Shell Command Execution</h2>" +
			"<ul>" +
			"<li>Allows user to run system commands manually</li>" +
			"<li>Commands are executed locally on the device</li>" +
			"<li>The App does NOT send commands to any external server</li>" +
			"</ul>" +
			"</div>" +
			
			"<div class='section'>" +
			"<h2>4. Plugin System</h2>" +
			"<ul>" +
			"<li>Users can install plugins in ZIP format</li>" +
			"<li>Plugins are extracted locally (e.g., /data/local/tmp)</li>" +
			"<li>Each plugin includes metadata (name, version, author, description)</li>" +
			"</ul>" +
			"<div class='note'>" +
			"<strong>Important:</strong> Plugins are created by users or third parties. We do NOT guarantee safety of external plugins. Users must review plugins before installing." +
			"</div>" +
			"</div>" +
			
			"<div class='section'>" +
			"<h2>5. Custom Server (Optional)</h2>" +
			"<ul>" +
			"<li>Used to fetch plugin lists (if enabled)</li>" +
			"<li>No personal data is required to access plugins</li>" +
			"</ul>" +
			"</div>" +
			
			"<div class='section'>" +
			"<h2>6. No Remote Control / No Hidden Access</h2>" +
			"<p>The App:</p>" +
			"<ul>" +
			"<li>Does NOT control your device remotely</li>" +
			"<li>Does NOT access your device without permission</li>" +
			"<li>Does NOT act as spyware or RAT</li>" +
			"</ul>" +
			"<p>All actions are initiated by the user.</p>" +
			"</div>" +
			
			"<div class='section'>" +
			"<h2>7. Transparency Commitment</h2>" +
			"<p>We are committed to:</p>" +
			"<ul>" +
			"<li>No hidden features</li>" +
			"<li>No background spying</li>" +
			"<li>Clear explanation of all functionalities</li>" +
			"</ul>" +
			"</div>" +
			
			"<div class='footer'>© 2026 Hypers-Manager. All rights reserved.</div>" +
			
			"</body></html>";
			
			webview1.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", null);
			{
				android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
				int d = (int) getContext().getApplicationContext().getResources().getDisplayMetrics().density;
				SketchUi.setColor(0xFF131313);SketchUi.setCornerRadii(new float[]{
					d*28,d*28,d*28 ,d*28,d*0,d*0 ,d*0,d*0});
				linear1.setElevation(d*5);
				linear1.setBackground(SketchUi);
			}
			_colorTransform(imageview1, "#FF26A69A");
			textview1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
			textview2.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
			imageview1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					bottomSheetDialog.dismiss();
				}
			});
			bottomSheetDialog.setCancelable(false);
			bottomSheetDialog.show();
		});
		
		imageview19.setOnClickListener(_v -> {
			settings.setVisibility(View.GONE);
			list.setVisibility(View.VISIBLE);
		});
		
		linear129.setOnClickListener(_v -> {
			if (opsi == 0) {
				opsi++;
				_click(linear129);
				opsippi.setVisibility(View.VISIBLE);
				
				// ukur height asli (wrap_content)
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
		
		imageview36.setOnClickListener(_v -> {
			DisplayMetrics dm = requireContext().getResources().getDisplayMetrics();
			
			scale = dm.density;
			
			Typeface bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/gffbold.ttf");
			Typeface regular = Typeface.createFromAsset(getContext().getAssets(), "fonts/gffregular.ttf");
			
			LinearLayout root = new LinearLayout(requireContext());
			root.setOrientation(LinearLayout.VERTICAL);
			
			// padding stabil
			root.setPadding(dp(18), dp(16), dp(18), dp(16));
			
			// WIDTH FIX (90%)
			int width = (int)(dm.widthPixels * 0.90f);
			root.setLayoutParams(new LinearLayout.LayoutParams(
			width,
			LinearLayout.LayoutParams.WRAP_CONTENT
			));
			
			// BACKGROUND
			GradientDrawable bg = new GradientDrawable();
			bg.setColor(0xFF181818);
			bg.setCornerRadius(dp(20));
			root.setBackground(bg);
			
			// TITLE
			TextView title = new TextView(requireContext());
			title.setText("Select Renderer");
			title.setTextSize(16);
			title.setTypeface(bold);
			title.setTextColor(0xFFFFFFFF);
			root.addView(title);
			
			// SUBTITLE
			TextView sub = new TextView(requireContext());
			sub.setText("Choose the API for visuals");
			sub.setTextSize(12);
			sub.setTypeface(regular);
			sub.setTextColor(0xFF9E9E9E);
			sub.setPadding(0, dp(6), 0, dp(16));
			root.addView(sub);
			
			// CONTAINER
			LinearLayout container = new LinearLayout(requireContext());
			container.setOrientation(LinearLayout.VERTICAL);
			
			String[] options = {"SkiaVK", "Vulkan", "SkiaGL", "OpenGL"};
			
			final int[] selectedIndex = {0};
			
			ArrayList<LinearLayout> items = new ArrayList<>();
			ArrayList<RadioButton> radios = new ArrayList<>();
			ArrayList<TextView> texts = new ArrayList<>();
			
			for (int i = 0; i < options.length; i++) {
				
				LinearLayout item = new LinearLayout(requireContext());
				item.setOrientation(LinearLayout.HORIZONTAL);
				item.setGravity(Gravity.CENTER_VERTICAL);
				
				LinearLayout.LayoutParams lp =
				new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				dp(54)
				);
				
				lp.setMargins(0, dp(6), 0, 0);
				item.setLayoutParams(lp);
				
				item.setPadding(dp(16), dp(10), dp(16), dp(10));
				
				GradientDrawable normal = new GradientDrawable();
				normal.setCornerRadius(dp(18));
				normal.setColor(0xFF1F1F1F);
				normal.setStroke(dp(1), 0xFF2E2E2E);
				item.setBackground(normal);
				
				TextView tv = new TextView(requireContext());
				tv.setText(options[i]);
				tv.setTextSize(13);
				tv.setTypeface(regular);
				tv.setTextColor(0xFF9E9E9E);
				
				LinearLayout.LayoutParams tvp =
				new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
				tv.setLayoutParams(tvp);
				
				RadioButton rb = new RadioButton(requireContext());
				rb.setClickable(false);
				rb.setScaleX(0.8f);
				rb.setScaleY(0.8f);
				rb.setButtonTintList(ColorStateList.valueOf(0xFF80CBC4));
				
				item.addView(tv);
				item.addView(rb);
				container.addView(item);
				
				items.add(item);
				radios.add(rb);
				texts.add(tv);
				
				int index = i;
				
				item.setOnClickListener(v -> {
					
					selectedIndex[0] = index;
					
					for (int j = 0; j < items.size(); j++) {
						
						GradientDrawable n = new GradientDrawable();
						n.setCornerRadius(dp(18));
						n.setColor(0xFF1F1F1F);
						n.setStroke(dp(1), 0xFF2E2E2E);
						
						items.get(j).setBackground(n);
						radios.get(j).setChecked(false);
						
						texts.get(j).setTypeface(regular);
						texts.get(j).setTextColor(0xFF9E9E9E);
					}
					
					GradientDrawable sel = new GradientDrawable();
					sel.setCornerRadius(dp(18));
					sel.setColor(0xFF1E3A38);
					sel.setStroke(dp(1), 0xFF4DB6AC);
					
					item.setBackground(sel);
					rb.setChecked(true);
					
					tv.setTypeface(bold);
					tv.setTextColor(0xFF80CBC4);
				});
			}
			
			// RESTORE VALUE
			String saved = listmanager.getString("render", "SkiaVK");
			
			for (int i = 0; i < options.length; i++) {
				if (options[i].equalsIgnoreCase(saved)) {
					selectedIndex[0] = i;
					items.get(i).performClick();
				}
			}
			
			root.addView(container);
			
			// BUTTON ROW
			LinearLayout btn = new LinearLayout(requireContext());
			btn.setOrientation(LinearLayout.HORIZONTAL);
			btn.setGravity(Gravity.END);
			btn.setPadding(0, dp(18), 0, 0);
			
			// CANCEL
			TextView cancel = new TextView(requireContext());
			cancel.setText("CANCEL");
			cancel.setTypeface(bold);
			cancel.setTextColor(0xFF9E9E9E);
			cancel.setPadding(dp(18), dp(10), dp(18), dp(10));
			
			// APPLY
			TextView apply = new TextView(requireContext());
			apply.setText("APPLY");
			apply.setTypeface(bold);
			apply.setTextColor(0xFFFFFFFF);
			apply.setPadding(dp(28), dp(12), dp(28), dp(12));
			
			GradientDrawable applyBg = new GradientDrawable();
			applyBg.setCornerRadius(dp(20));
			applyBg.setColor(0xFF4DB6AC);
			apply.setBackground(applyBg);
			
			btn.addView(cancel);
			btn.addView(apply);
			root.addView(btn);
			
			// DIALOG
			AlertDialog dialog = new AlertDialog.Builder(requireContext()).create();
			dialog.setView(root);
			
			cancel.setOnClickListener(v -> dialog.dismiss());
			
			apply.setOnClickListener(v -> {
				
				// LOADING
				LoadingBooster.show(requireContext())
				.size(180)
				.color(0xFF00ACC1);
				
				TimerTask time = new TimerTask() {
					@Override
					public void run() {
						getActivity().runOnUiThread(() -> LoadingBooster.hide());
					}
				};
				_timer.schedule(time, 1000);
				
				String selected = options[selectedIndex[0]];
				
				listmanager.edit().putString("render", selected).apply();
				textview57.setText(selected);
				
				// SHELL / SHIZUKU EXEC (FULL BALIK)
				new Thread(() -> {
					try {
						shizukuExec("setprop debug.composition.type " + selected);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}).start();
				
				dialog.dismiss();
			});
			
			dialog.show();
			
			// WINDOW FIX
			Window window = dialog.getWindow();
			if (window != null) {
				window.setDimAmount(0.6f);
				
				window.setLayout(
				(int)(dm.widthPixels * 0.90f),
				LinearLayout.LayoutParams.WRAP_CONTENT
				);
				
				window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			}
		});
		
		imageview37.setOnClickListener(_v -> {
			DisplayMetrics dm = requireContext().getResources().getDisplayMetrics();
			
			scale = dm.density;
			
			Typeface bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/gffbold.ttf");
			Typeface regular = Typeface.createFromAsset(getContext().getAssets(), "fonts/gffregular.ttf");
			
			LinearLayout root = new LinearLayout(requireContext());
			root.setOrientation(LinearLayout.VERTICAL);
			
			int padH = dp(18);
			int padV = dp(16);
			
			root.setPadding(padH, padV, padH, padV);
			
			// WIDTH STABIL (kayak versi pertama)
			int width = (int)(dm.widthPixels * 0.90f);
			root.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT));
			
			// BACKGROUND
			GradientDrawable bg = new GradientDrawable();
			bg.setColor(0xFF181818);
			bg.setCornerRadius(dp(20));
			root.setBackground(bg);
			
			// TITLE
			TextView title = new TextView(requireContext());
			title.setText("Select Composition");
			title.setTextSize(16);
			title.setTypeface(bold);
			title.setTextColor(0xFFFFFFFF);
			root.addView(title);
			
			// SUBTITLE
			TextView sub = new TextView(requireContext());
			sub.setText("Choose rendering composition type");
			sub.setTextSize(12);
			sub.setTypeface(regular);
			sub.setTextColor(0xFF9E9E9E);
			sub.setPadding(0, dp(6), 0, dp(16));
			root.addView(sub);
			
			// CONTAINER
			LinearLayout container = new LinearLayout(requireContext());
			container.setOrientation(LinearLayout.VERTICAL);
			
			String[] options = {"CPU", "GPU", "dyn", "c2d", "mdp"};
			
			final int[] selectedIndex = {0};
			
			ArrayList<LinearLayout> items = new ArrayList<>();
			ArrayList<RadioButton> radios = new ArrayList<>();
			ArrayList<TextView> texts = new ArrayList<>();
			
			for (int i = 0; i < options.length; i++) {
				
				LinearLayout item = new LinearLayout(requireContext());
				item.setOrientation(LinearLayout.HORIZONTAL);
				item.setGravity(Gravity.CENTER_VERTICAL);
				
				LinearLayout.LayoutParams lp =
				new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				dp(54)
				);
				
				lp.setMargins(0, dp(6), 0, 0);
				item.setLayoutParams(lp);
				
				item.setPadding(dp(16), dp(10), dp(16), dp(10));
				
				GradientDrawable normal = new GradientDrawable();
				normal.setCornerRadius(dp(18));
				normal.setColor(0xFF1F1F1F);
				normal.setStroke(dp(1), 0xFF2E2E2E);
				item.setBackground(normal);
				
				TextView tv = new TextView(requireContext());
				tv.setText(options[i]);
				tv.setTextSize(13);
				tv.setTypeface(regular);
				tv.setTextColor(0xFF9E9E9E);
				
				LinearLayout.LayoutParams tvp =
				new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
				tv.setLayoutParams(tvp);
				
				RadioButton rb = new RadioButton(requireContext());
				rb.setClickable(false);
				rb.setScaleX(0.8f);
				rb.setScaleY(0.8f);
				rb.setButtonTintList(ColorStateList.valueOf(0xFF80CBC4));
				
				item.addView(tv);
				item.addView(rb);
				container.addView(item);
				
				items.add(item);
				radios.add(rb);
				texts.add(tv);
				
				int index = i;
				
				item.setOnClickListener(v -> {
					
					selectedIndex[0] = index;
					
					for (int j = 0; j < items.size(); j++) {
						
						GradientDrawable n = new GradientDrawable();
						n.setCornerRadius(dp(18));
						n.setColor(0xFF1F1F1F);
						n.setStroke(dp(1), 0xFF2E2E2E);
						
						items.get(j).setBackground(n);
						radios.get(j).setChecked(false);
						
						texts.get(j).setTypeface(regular);
						texts.get(j).setTextColor(0xFF9E9E9E);
					}
					
					GradientDrawable sel = new GradientDrawable();
					sel.setCornerRadius(dp(18));
					sel.setColor(0xFF1E3A38);
					sel.setStroke(dp(1), 0xFF4DB6AC);
					
					item.setBackground(sel);
					rb.setChecked(true);
					
					tv.setTypeface(bold);
					tv.setTextColor(0xFF80CBC4);
				});
			}
			
			// RESTORE
			String saved = listmanager.getString("gpu", "CPU");
			
			for (int i = 0; i < options.length; i++) {
				if (options[i].equals(saved)) {
					selectedIndex[0] = i;
					items.get(i).performClick();
				}
			}
			
			root.addView(container);
			
			// BUTTON ROW
			LinearLayout btn = new LinearLayout(requireContext());
			btn.setOrientation(LinearLayout.HORIZONTAL);
			btn.setGravity(Gravity.END);
			btn.setPadding(0, dp(18), 0, 0);
			
			TextView cancel = new TextView(requireContext());
			cancel.setText("CANCEL");
			cancel.setTypeface(bold);
			cancel.setTextColor(0xFF9E9E9E);
			cancel.setPadding(dp(18), dp(10), dp(18), dp(10));
			
			TextView apply = new TextView(requireContext());
			apply.setText("APPLY");
			apply.setTypeface(bold);
			apply.setTextColor(0xFFFFFFFF);
			apply.setPadding(dp(28), dp(12), dp(28), dp(12));
			
			GradientDrawable applyBg = new GradientDrawable();
			applyBg.setCornerRadius(dp(20));
			applyBg.setColor(0xFF4DB6AC);
			apply.setBackground(applyBg);
			
			btn.addView(cancel);
			btn.addView(apply);
			root.addView(btn);
			
			// DIALOG
			AlertDialog dialog = new AlertDialog.Builder(requireContext()).create();
			dialog.setView(root);
			
			cancel.setOnClickListener(v -> dialog.dismiss());
			
			apply.setOnClickListener(v -> {
				
				LoadingBooster.show(requireContext()).size(180).color(0xFF00ACC1);
				
				TimerTask time = new TimerTask() {
					@Override
					public void run() {
						getActivity().runOnUiThread(() -> LoadingBooster.hide());
					}
				};
				_timer.schedule(time, 1000);
				
				String selected = options[selectedIndex[0]];
				
				listmanager.edit().putString("gpu", selected).apply();
				textview57.setText(selected);
				
				new Thread(() -> {
					try {
						shizukuExec("setprop debug.composition.type " + selected);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}).start();
				
				dialog.dismiss();
			});
			
			dialog.show();
			
			Window window = dialog.getWindow();
			if (window != null) {
				window.setDimAmount(0.6f);
				window.setLayout(
				(int)(dm.widthPixels * 0.90f),
				LinearLayout.LayoutParams.WRAP_CONTENT
				);
				window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			}
		});
		
		linear143.setOnClickListener(_v -> {
			_click(linear143);
			execSmart("wm size reset");
		});
		
		linear133.setOnClickListener(_v -> {
			_click(linear133);
			
			String w = edittext1.getText().toString().trim();
			String h = edittext2.getText().toString().trim();
			
			if (w.equals("") || h.equals("")) {
				SketchwareUtil.showMessage(getContext().getApplicationContext(), "input kosong!!");
			} else {
				
				try {
					int width = Integer.parseInt(w);
					int height = Integer.parseInt(h);
					
					if (width < 200 || height < 200) {
						SketchwareUtil.showMessage(getContext().getApplicationContext(), "terlalu kecil!");
						return;
					}
					
					String cmd = "wm size " + width + "x" + height;
					
					ExecEngine.newProcess(new String[]{"sh", "-c", cmd});
					
					SketchwareUtil.showMessage(getContext().getApplicationContext(), "berhasil apply!");
					
				} catch (Exception e) {
					SketchwareUtil.showMessage(getContext().getApplicationContext(), "format salah!");
				}
			}
		});
		
		imageview43.setOnClickListener(_v -> {
			_click(imageview43);
			i.setClass(getContext().getApplicationContext(), TaskActivity.class);
			startActivity(i);
		});
		
		switch7.setOnCheckedChangeListener((_buttonView, _isChecked) -> {
			if (_isChecked) {
				new Thread(() -> {
					execSmart("settings put system rt_enable_templimit true");
					execSmart("settings put system oplus_settings_hightemp_protect 0");
					execSmart("settings put secure oppo_high_temperature_protection_status 1");
					
					execSmart("settings put system tran_default_temperature_index 1");
					execSmart("settings put system tran_temp_battery_warning 1");
				}).start();
				listmanager.edit().putString("thermal", "enabled").apply();
			} else {
				new Thread(() -> {
					execSmart("settings put system rt_enable_templimit false");
					execSmart("settings put system oplus_settings_hightemp_protect -1");
					execSmart("settings put secure oppo_high_temperature_protection_status 0");
					
					execSmart("settings put system tran_default_temperature_index 0");
					execSmart("settings put system tran_temp_battery_warning 0");
				}).start();
				listmanager.edit().putString("thermal", "disabled").apply();
			}
		});
		
		switch5.setOnCheckedChangeListener((_buttonView, _isChecked) -> {
			if (_isChecked) {
				if (!android.provider.Settings.canDrawOverlays(requireContext())) {
					
					Intent intent = new Intent(
					android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
					android.net.Uri.parse("package:" + requireActivity().getPackageName())
					);
					
					startActivity(intent);
					
					Toast.makeText(requireContext(),
					"Izinkan overlay dulu",
					Toast.LENGTH_SHORT).show();
					
					return;
				}
				
				requireContext().startService(
				new Intent(requireContext(), FloatingService.class)
				);
			} else {
				requireContext().stopService(
				new Intent(requireContext(), FloatingService.class)
				);
			}
		});
		
		switch9.setOnCheckedChangeListener((_buttonView, _isChecked) -> {
			if (_isChecked) {
				if (isServiceRunning(CrosshairService.class)) {
					
				} else {
					Intent i = new Intent(requireContext(), CrosshairService.class);
					
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
						requireContext().startForegroundService(i);
					} else {
						requireContext().startService(i);
					}
				}
				bgcrs.setVisibility(View.VISIBLE);
				bgcrs.post(() -> {
					ViewGroup.LayoutParams lp = bgcrs.getLayoutParams();
					lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
					bgcrs.setLayoutParams(lp);
					
					int widthSpec = View.MeasureSpec.makeMeasureSpec(
					((View) bgcrs.getParent()).getWidth(),
					View.MeasureSpec.EXACTLY
					);
					
					int heightSpec = View.MeasureSpec.makeMeasureSpec(
					0,
					View.MeasureSpec.UNSPECIFIED
					);
					
					bgcrs.measure(widthSpec, heightSpec);
					int targetHeight = bgcrs.getMeasuredHeight();
					
					lp.height = 0;
					bgcrs.setLayoutParams(lp);
					
					ValueAnimator anim = ValueAnimator.ofInt(0, targetHeight);
					anim.addUpdateListener(animation -> {
						ViewGroup.LayoutParams params = bgcrs.getLayoutParams();
						params.height = (int) animation.getAnimatedValue();
						bgcrs.setLayoutParams(params);
					});
					
					anim.setDuration(300);
					anim.setInterpolator(new DecelerateInterpolator());
					anim.start();
					
					bgcrs.setAlpha(0f);
					bgcrs.animate().alpha(1f).setDuration(300).start();
					
					anim.addListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							ViewGroup.LayoutParams params = bgcrs.getLayoutParams();
							params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
							bgcrs.setLayoutParams(params);
						}
					});
				});
				
				bggyro.setVisibility(View.VISIBLE);
				bggyro.measure(
				View.MeasureSpec.makeMeasureSpec(((View) bggyro.getParent()).getWidth(), View.MeasureSpec.EXACTLY),
				View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
				);
				
				int targetHeights = bggyro.getMeasuredHeight();
				ViewGroup.LayoutParams lpp = bggyro.getLayoutParams();
				lpp.height = 0;
				bggyro.setLayoutParams(lpp);
				
				ValueAnimator anims = ValueAnimator.ofInt(0, targetHeights);
				anims.addUpdateListener(valueAnimator -> {
					int val = (int) valueAnimator.getAnimatedValue();
					ViewGroup.LayoutParams params = bggyro.getLayoutParams();
					params.height = val;
					bggyro.setLayoutParams(params);
				});
				
				anims.setDuration(300);
				anims.setInterpolator(new DecelerateInterpolator());
				anims.start();
				
				bggyro.setAlpha(0f);
				bggyro.animate().alpha(1f).setDuration(300).start();
				linear57.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)3, 0xFF22B4FF, 0xFF0D6F82));
				linear58.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
				linear59.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
				linear60.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
				linear61.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
				linear279.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
				linear280.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
				vscroll3.fullScroll(View.FOCUS_DOWN);
			} else {
				if (isServiceRunning(CrosshairService.class)) {
					requireContext().stopService(
					new Intent(requireContext(), CrosshairService.class)
					);
					Intent i = new Intent(requireContext(), CrosshairService.class);
					i.setAction(CrosshairService.ACTION_STOP);
					requireContext().startService(i);
				} else {
					
				}
				
				if (isServiceRunning(GyroService.class)) {
					requireContext().stopService(new Intent(requireContext(), GyroService.class));
				} else {
					
				}
				int initialHeight = bgcrs.getMeasuredHeight();
				ValueAnimator anim = ValueAnimator.ofInt(initialHeight, 0);
				anim.addUpdateListener(valueAnimator -> {
					int val = (int) valueAnimator.getAnimatedValue();
					ViewGroup.LayoutParams params = bgcrs.getLayoutParams();
					params.height = val;
					bgcrs.setLayoutParams(params);
				});
				
				anim.setDuration(300);
				anim.setInterpolator(new DecelerateInterpolator());
				anim.start();
				
				anim.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						bgcrs.setVisibility(View.GONE);
					}
				});
				
				int initialHeighht = bggyro.getMeasuredHeight();
				ValueAnimator anims = ValueAnimator.ofInt(initialHeighht, 0);
				anims.addUpdateListener(valueAnimator -> {
					int val = (int) valueAnimator.getAnimatedValue();
					ViewGroup.LayoutParams params = bggyro.getLayoutParams();
					params.height = val;
					bggyro.setLayoutParams(params);
				});
				
				anims.setDuration(300);
				anims.setInterpolator(new DecelerateInterpolator());
				anims.start();
				
				anims.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						bggyro.setVisibility(View.GONE);
					}
				});
				nilaix.setText("0");
				nilaiy.setText("0");
				seekbar1.setProgress(10);
			}
		});
		
		linear57.setOnClickListener(_v -> {
			_click(linear57);
			Intent i = new Intent(requireContext(), CrosshairService.class);
			i.putExtra("icon", R.drawable.cross1);
			
			requireContext().startService(i);
			linear57.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)3, 0xFF22B4FF, 0xFF0D6F82));
			linear58.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear59.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear60.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear61.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear279.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear280.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
		});
		
		linear58.setOnClickListener(_v -> {
			_click(linear58);
			Intent i = new Intent(requireContext(), CrosshairService.class);
			i.putExtra("icon", R.drawable.cross2);
			
			requireContext().startService(i);
			linear57.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear58.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)3, 0xFF22B4FF, 0xFF0D6F82));
			linear59.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear60.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear61.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear279.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear280.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
		});
		
		linear59.setOnClickListener(_v -> {
			_click(linear59);
			Intent i = new Intent(requireContext(), CrosshairService.class);
			i.putExtra("icon", R.drawable.cross3);
			
			requireContext().startService(i);
			linear57.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear58.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear59.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)3, 0xFF22B4FF, 0xFF0D6F82));
			linear60.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear61.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear279.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear280.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
		});
		
		linear60.setOnClickListener(_v -> {
			_click(linear60);
			Intent i = new Intent(requireContext(), CrosshairService.class);
			i.putExtra("icon", R.drawable.cross4);
			
			requireContext().startService(i);
			linear57.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear58.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear59.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear60.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)3, 0xFF22B4FF, 0xFF0D6F82));
			linear61.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear279.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear280.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
		});
		
		linear61.setOnClickListener(_v -> {
			_click(linear61);
			Intent i = new Intent(requireContext(), CrosshairService.class);
			i.putExtra("icon", R.drawable.cross5);
			
			requireContext().startService(i);
			linear57.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear58.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear59.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear60.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear61.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)3, 0xFF22B4FF, 0xFF0D6F82));
			linear279.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear280.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
		});
		
		linear279.setOnClickListener(_v -> {
			_click(linear279);
			Intent i = new Intent(requireContext(), CrosshairService.class);
			i.putExtra("icon", R.drawable.cross6);
			
			requireContext().startService(i);
			linear57.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear58.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear59.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear60.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear61.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear279.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)3, 0xFF22B4FF, 0xFF0D6F82));
			linear280.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
		});
		
		linear280.setOnClickListener(_v -> {
			_click(linear280);
			Intent i = new Intent(requireContext(), CrosshairService.class);
			i.putExtra("icon", R.drawable.cross7);
			
			requireContext().startService(i);
			linear57.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear58.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear59.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear60.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear61.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear279.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
			linear280.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)3, 0xFF22B4FF, 0xFF0D6F82));
		});
		
		red.setOnClickListener(_v -> {
			_click(red);
			_setCrosshairColor("#D50000");
		});
		
		pink.setOnClickListener(_v -> {
			_click(pink);
			_setCrosshairColor("#C51162");
		});
		
		ungu.setOnClickListener(_v -> {
			_click(ungu);
			_setCrosshairColor("#AA00FF");
		});
		
		blue.setOnClickListener(_v -> {
			_click(blue);
			_setCrosshairColor("#2962FF");
		});
		
		black.setOnClickListener(_v -> {
			_click(black);
			_setCrosshairColor("#000000");
		});
		
		white.setOnClickListener(_v -> {
			_click(white);
			_setCrosshairColor("#FFFFFE");
		});
		
		switch12.setOnCheckedChangeListener((_buttonView, _isChecked) -> {
			if (_isChecked) {
				Intent intent = new Intent(getActivity(), GyroService.class);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					getActivity().startForegroundService(intent);
				} else {
					getActivity().startService(intent);
				}
			} else {
				getActivity().stopService(new Intent(getActivity(), GyroService.class));
			}
		});
	}
	
	private void initializeLogic() {
		new Handler(Looper.getMainLooper()).postDelayed(() -> {
			
			textview51.setText(listmanager.getString("render", ""));
			textview57.setText(listmanager.getString("gpu", ""));
			
			
			String mode = listmanager.getString("mode", "").trim();
			String vsync = listmanager.getString("vsync", "").trim();
			String thmr = listmanager.getString("thermal", "").trim();
			String services = listmanager.getString("aktifitas", "Adb");
			
			if (thmr.equalsIgnoreCase("enabled")) {
				switch7.setChecked(true);
			} else if (thmr.equalsIgnoreCase("disabled")) {
				switch7.setChecked(false);
			}
			
			if (mode.equalsIgnoreCase("Performance")) {
				selectTab(0, linear107, linear108, textview61, textview62);
			} else if (mode.equalsIgnoreCase("Power Saver")) {
				selectTab(1, linear107, linear108, textview61, textview62);
			}
			
			if (vsync.equalsIgnoreCase("Enable")) {
				selectTab(0, linear117, linear118, textview65, textview66);
			} else if (vsync.equalsIgnoreCase("Disable")) {
				selectTab(1, linear117, linear118, textview65, textview66);
			}
			
			if (mode.equalsIgnoreCase("Shizuku")) {
				
				linear49.setVisibility(View.VISIBLE);
				linear329.setVisibility(View.GONE);
				
			} else if (mode.equalsIgnoreCase("Adb/Wireless") || mode.contains("Wireless")) {
				
				linear49.setVisibility(View.GONE);
				linear329.setVisibility(View.VISIBLE);
				
			}
			
		}, 200);
		_shizuku_access();
		_LIST();
		_setting();
		_font();
		_sizetetap();
		_c();
		list.setVisibility(View.GONE);
		linear49.setVisibility(View.GONE);
		linear329.setVisibility(View.VISIBLE);
		if (isServiceRunning(CrosshairService.class)) {
			switch12.setChecked(true);
		} else {
			switch12.setChecked(false);
		}
		if (isServiceRunning(GyroService.class)) {
			switch12.setChecked(true);
		} else {
			switch12.setChecked(false);
		}
		new Handler(Looper.getMainLooper()).post(() -> {
			if (isAdded()) {
				edittext1.setText(finalWidth);
				edittext2.setText(finalHeight);
			}
		});
		startMonitor();
		ADB adb = ADB.getInstance(requireContext());
		
		adb.started.observe(getActivity(), isRunning -> {
			
			if (isRunning) {
				int pid = adb.getShellPid();
				textview91.setText("HYPERS is running");
				textview91.setTextColor(Color.parseColor("#4CAF50")); // Hijau Shizuku
			} else {
				textview91.setText("HYPERS is not running");
				textview91.setTextColor(Color.parseColor("#F44336"));
				
			}
		});
		if (isRootDevice()) {
			textview26.setText("Root Service Access");
			boolean granted = false;
			
			try {
				Process p = Runtime.getRuntime().exec(new String[]{"su", "-c", "id"});
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				
				String line = br.readLine();
				if (line != null && line.contains("uid=0")) {
					granted = true;
				}
				
				p.waitFor();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			boolean finalGranted = granted;
			
			getActivity().runOnUiThread(() -> {
				if (finalGranted) {
					textview27.setText("Root Access granted");
					textview27.setTextColor(0xFFEF6965);
				} else {
					textview27.setText("Root Access not granted");
					textview27.setTextColor(0xFFEF6965);
				}
			});
			
			
		} else if (isShizukuAvailable()) {
			textview26.setText("Shizuku Service Access");
			int status;
			
			if (!Shizuku.pingBinder()) {
				status = 0;
			} else if (Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
				status = 1;
			} else {
				status = 2;
			}
			
			if (status == 0) {
				
				textview27.setText("Shizuku app is not running");
				textview27.setTextColor(0xFFEF6965);
				
			} else if (status == 1) {
				
				textview27.setText("Shizuku app is running");
				textview27.setTextColor(0xFF65EF6C);
				
			} else {
				
				textview27.setText("Shizuku permission not granted");
				textview27.setTextColor(0xFFEF6965);
			}
		} else {
			textview26.setText("Shizuku Service Access");
			int status;
			
			if (!Shizuku.pingBinder()) {
				status = 0;
			} else if (Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
				status = 1;
			} else {
				status = 2;
			}
			
			if (status == 0) {
				
				textview27.setText("Shizuku app is not running");
				textview27.setTextColor(0xFFEF6965);
				
			} else if (status == 1) {
				
				textview27.setText("Shizuku app is running");
				textview27.setTextColor(0xFF65EF6C);
				
			} else {
				
				textview27.setText("Shizuku permission not granted");
				textview27.setTextColor(0xFFEF6965);
			}
		}
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		new Handler(Looper.getMainLooper()).post(() -> {
			if (isAdded()) {
				edittext1.setText(finalWidth);
				edittext2.setText(finalHeight);
			}
		});
		if (isRootDevice()) {
			textview26.setText("Root Service Access");
			boolean granted = false;
			
			try {
				Process p = Runtime.getRuntime().exec(new String[]{"su", "-c", "id"});
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				
				String line = br.readLine();
				if (line != null && line.contains("uid=0")) {
					granted = true;
				}
				
				p.waitFor();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			boolean finalGranted = granted;
			
			getActivity().runOnUiThread(() -> {
				if (finalGranted) {
					textview27.setText("Root Access granted");
					textview27.setTextColor(0xFFEF6965);
				} else {
					textview27.setText("Root Access not granted");
					textview27.setTextColor(0xFFEF6965);
				}
			});
			
			
		} else if (isShizukuAvailable()) {
			textview26.setText("Shizuku Service Access");
			int status;
			
			if (!Shizuku.pingBinder()) {
				status = 0;
			} else if (Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
				status = 1;
			} else {
				status = 2;
			}
			
			if (status == 0) {
				
				textview27.setText("Shizuku app is not running");
				textview27.setTextColor(0xFFEF6965);
				
			} else if (status == 1) {
				
				textview27.setText("Shizuku app is running");
				textview27.setTextColor(0xFF65EF6C);
				
			} else {
				
				textview27.setText("Shizuku permission not granted");
				textview27.setTextColor(0xFFEF6965);
			}
		} else {
			textview26.setText("Shizuku Service Access");
			int status;
			
			if (!Shizuku.pingBinder()) {
				status = 0;
			} else if (Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
				status = 1;
			} else {
				status = 2;
			}
			
			if (status == 0) {
				
				textview27.setText("Shizuku app is not running");
				textview27.setTextColor(0xFFEF6965);
				
			} else if (status == 1) {
				
				textview27.setText("Shizuku app is running");
				textview27.setTextColor(0xFF65EF6C);
				
			} else {
				
				textview27.setText("Shizuku permission not granted");
				textview27.setTextColor(0xFFEF6965);
			}
		}
		ADB adb = ADB.getInstance(requireContext());
		
		adb.started.observe(getActivity(), isRunning -> {
			
			if (isRunning) {
				int pid = adb.getShellPid();
				textview91.setText("HYPERS is running");
				textview91.setTextColor(Color.parseColor("#4CAF50")); // Hijau Shizuku
			} else {
				textview91.setText("HYPERS is not running");
				textview91.setTextColor(Color.parseColor("#F44336"));
				
			}
		});
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		isRunning = false;
		uiHandler.removeCallbacksAndMessages(null);
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
	
	
	public void _LIST() {
		linear82.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear85.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear88.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear91.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear106.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF0E0E0E));
		linear116.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF0E0E0E));
		linear93.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF0E0E0E));
		linear87.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF0E0E0E));
		linear124.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear129.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear136.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF0E0E0E));
		linear138.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF0E0E0E));
		linear133.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)1, 0xFF00ACC1, 0x5000ACC1));
		linear143.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)1, 0xFF00ACC1, 0x70E53935));
		linear139.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear148.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear152.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		bggyro.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear107.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectTab(0, linear107, linear108, textview61, textview62);
				
				listmanager.edit().putString("mode", "Performance").apply();
				
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
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
		
		linear108.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectTab(1, linear107, linear108, textview61, textview62);
				
				listmanager.edit().putString("mode", "Power Saver").apply();
				
				new Thread(() -> {
					String[] cmds = new String[]{
						"settings put system POWER_PERFORMANCE_MODE_OPEN 0",
						"settings put system multicore_packet_scheduler 0",
						"settings put system high_performance_mode_on 0",
						"settings put system sem_performance_mode 1",
						"settings put system speed_mode 0",
						"settings put secure high_priority 0",
						"settings put secure speed_mode_enable 0",
						"settings put global sem_enchanced_cpu_responsiveness 0",
						"settings put global cached_apps_freezer \"disabled\"",
						"settings put global restricted_device_performance \"0,0\"",
						"settings put global adaptive_battery_management_enabled 1",
						"settings put global game_auto_temperature_control 1",
						"settings put system perf_shielder_SF 1",
						"settings put system perf_shielder_RTMODE 0",
						"settings put system perf_shielder_GESTURE 1",
						"settings put system perf_shielder_smartpower 1",
						"settings put system POWER_SAVE_MODE_OPEN 1",
						"settings put global automatic_power_save_mode 1",
						"settings put global low_power 1",
						"setprop debug.performance.tuning 0",
						"setprop debug.sf.hw 0",
						"setprop debug.egl.hw 0",
						"setprop debug.egl.buffercount 1",
						"setprop debug.gralloc.enable_fb_ubwc 0",
						"setprop debug.gralloc.gfx_ubwc_disable 1",
						"setprop debug.gralloc.map_fb_memory 1",
						"setprop debug.sf.multithreaded_present 0",
						"setprop debug.sf.enable_layer_caching false",
						"setprop debug.sf.predict_hwc_composition_strategy 0",
						"setprop debug.hwui.force_draw_frame false",
						"setprop debug.hwui.skip_empty_damage false",
						"setprop debug.hwui.use_gpu_pixel_buffers false",
						"cmd power set-fixed-performance-mode-enabled false",
						"cmd power set-adaptive-power-saver-enabled true",
						"cmd power set-mode 1"
					};
					
					for (String cmd : cmds) {
						try {
							shizukuExec(cmd);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
		
		selectTab(-1, linear107, linear108, textview61, textview62);
		linear117.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectTab(0, linear117, linear118, textview65, textview66);
				
				listmanager.edit().putString("vsync", "Enable").apply();
				
				new Thread(() -> {
					try {
						shizukuExec("setprop debug.gr.swapinterval 1");
						shizukuExec("setprop debug.sf.swapinterval 1");
						shizukuExec("setprop debug.egl.swapinterval 1");
						shizukuExec("setprop debug.gl.swapinterval 1");
						shizukuExec("setprop debug.hwui.disable_vsync false");
						shizukuExec("setprop debug.hwc.force_gpu_vsync 1");
						shizukuExec("setprop debug.sf.no_hw_vsync 0");
						shizukuExec("setprop debug.hwc.fakevsync 1");
						shizukuExec("setprop debug.logvsync 1");
						shizukuExec("setprop debug.choreographer.vsync true");
						shizukuExec("setprop debug.cpurend.vsync true");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}).start();
			}
		});
		
		linear118.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectTab(1, linear117, linear118, textview65, textview66);
				
				listmanager.edit().putString("vsync", "Disable").apply();
				
				new Thread(() -> {
					try {
						shizukuExec("setprop debug.gr.swapinterval 0");
						shizukuExec("setprop debug.sf.swapinterval 0");
						shizukuExec("setprop debug.egl.swapinterval 0");
						shizukuExec("setprop debug.gl.swapinterval 0");
						shizukuExec("setprop debug.hwui.disable_vsync true");
						shizukuExec("setprop debug.hwc.force_gpu_vsync 0");
						shizukuExec("setprop debug.sf.no_hw_vsync 1");
						shizukuExec("setprop debug.hwc.fakevsync 0");
						shizukuExec("setprop debug.logvsync 0");
						shizukuExec("setprop debug.choreographer.vsync false");
						shizukuExec("setprop debug.cpurend.vsync false");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}).start();
			}
		});
		
		selectTab(-1, linear117, linear118, textview65, textview66);
	}
	
	
	public void _v() {
	}
	private void selectTab(int pos, LinearLayout tab1, LinearLayout tab2, TextView txt1, TextView txt2) {
		
		if (pos == -1) {
			
			tab1.setBackground(null);
			tab2.setBackground(null);
			
			txt1.setTextColor(0xFFAAAAAA);
			txt2.setTextColor(0xFFAAAAAA);
			
			return;
		}
		
		if (pos == 0) {
			
			tab1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)1, 0xFF00ACC1, 0x7000ACC1));
			tab2.setBackground(null);
			
			txt1.setTextColor(0xFF000000);
			txt2.setTextColor(0xFFAAAAAA);
			
			animClick(tab1);
			
		} else {
			
			tab2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)1, 0xFF00ACC1, 0x7000ACC1));
			tab1.setBackground(null);
			
			txt1.setTextColor(0xFFAAAAAA);
			txt2.setTextColor(0xFF000000);
			
			animClick(tab2);
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
	
	
	public void _setting() {
		linear35.setBackground(new android.graphics.drawable.GradientDrawable(
		android.graphics.drawable.GradientDrawable.Orientation.TL_BR,
		new int[]{0xFF1A1A1A, 0xFF131313}
		) {{
				setCornerRadius(28);
			}});
		linear43.setBackground(new GradientDrawable() {
			public GradientDrawable getIns(int a, int b) {
				this.setCornerRadius(a);
				this.setColor(b);
				this.setStroke(3, 0xFF1D2020);
				return this;
			}
		}.getIns((int)60, 0x801C1C1C));
		linear44.setBackground(new GradientDrawable() {
			public GradientDrawable getIns(int a, int b) {
				this.setCornerRadius(a);
				this.setColor(b);
				this.setStroke(3, 0xFF1D2020);
				return this;
			}
		}.getIns((int)60, 0x801C1C1C));
		linear50.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0x801D2928));
		linear40.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)100, (int)3, 0xFF23413D, Color.TRANSPARENT));
		linear41.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0xFF81E4F6));
		linear45.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear47.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)60, 0xFF484847));
		linear49.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear329.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear332.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0x5000ACC1));
		settings.setVisibility(View.VISIBLE);
		list.setVisibility(View.GONE);
		segmented.setBackgroundResource(R.drawable.bg);
		List<SegmentedButton.SegmentItem> buttons = Arrays.asList(
		new SegmentedButton.SegmentItem("Information"),
		new SegmentedButton.SegmentItem("Configs")
		);
		
		segmented.setSegments(buttons);
		
		segmented.setOnSegmentClickListener((index, text) -> {
			
			if (index == 0) {
				settings.setVisibility(View.VISIBLE);
				list.setVisibility(View.GONE);
			} else {
				settings.setVisibility(View.GONE);
				list.setVisibility(View.VISIBLE);
			}
			
		});
		segmented.select(0, false);
	}
	
	
	public void _font() {
		textview18.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview19.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
		textview20.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview21.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview24.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview22.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
		textview23.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
		textview25.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview26.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview27.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
		textview45.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview46.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview47.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
		textview61.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview62.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview49.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview50.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
		textview51.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview52.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview53.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
		textview65.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview66.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview55.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview56.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
		textview57.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview90.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview91.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
	}
	
	
	public void _variable() {
	}
	private Process process;
	private Choreographer choreographer;
	private Choreographer.FrameCallback frameCallback;
	private Handler handler = new Handler(Looper.getMainLooper());
	
	private boolean isRunning = false;
	
	private float refreshRate = 60f;
	private long logicInterval = 400;
	
	private long lastUiUpdate = 0;
	
	private float latestGips = 0;
	private float lastGips = 0;
	
	private float baseline = 0;
	private float max = 0;
	
	private float total = 0;
	private float count = 0;
	
	private float performance = 0;
	private float stability = 0;
	
	private float cpuBias = 0;
	
	private float[] latestCores = null;
	private boolean latestThrottle = false;
	
	// GRAPH BUFFER (FIXED)
	private final float[] graphBuffer = new float[60];
	private int index = 0;
	private int size = 0;
	
	// THREAD CONTROL (NO ExecutorService)
	private Thread samplerThread;
	private Thread workerThread;
	
	private static final int COLOR_OK = 0xFF00ACC1;
	private static final int COLOR_THROTTLE = 0xFFFF3B30;
	
	private String finalWidth = "";
	private String finalHeight = "";
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
	
	
	public void _gipps() {
	}
	String format(double v) {
		return String.format(java.util.Locale.US, "%.2f", v).replace(".", ",");
	}
	
	private float lastTotal = 0;
	private float lastIdle = 0;
	
	private float getCpuUsage() {
		try {
			String output = ShizukuUtil.exec("cat /proc/stat");
			if (output == null || output.isEmpty()) return 0;
			
			String load = output.split("\n")[0];
			String[] toks = load.trim().split("\\s+");
			
			float idle = Float.parseFloat(toks[4]);
			float total = 0;
			
			for (int i = 1; i < toks.length; i++) {
				total += Float.parseFloat(toks[i]);
			}
			
			float diffTotal = total - lastTotal;
			float diffIdle = idle - lastIdle;
			
			lastTotal = total;
			lastIdle = idle;
			
			if (diffTotal <= 0) return 0;
			
			return (diffTotal - diffIdle) / diffTotal * 100f;
			
		} catch (Exception e) {
			return 0;
		}
	}
	
	private float getRamUsage() {
		
		try {
			RandomAccessFile reader = new RandomAccessFile("/proc/meminfo", "r");
			
			long total = 0;
			long available = 0;
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				
				if (line.startsWith("MemTotal")) {
					total = Long.parseLong(line.replaceAll("\\D+", ""));
				}
				
				if (line.startsWith("MemAvailable")) {
					available = Long.parseLong(line.replaceAll("\\D+", ""));
				}
			}
			
			return (float)((total - available) * 100f / total);
			
		} catch (Exception e) {
			return 0;
		}
	}
	
	private float calculateGIPS(float cpuUsage, float ramUsage) {
		
		float cpuScore = 100f - cpuUsage;
		float ramScore = 100f - ramUsage;
		
		float raw = (cpuScore * 0.7f) + (ramScore * 0.3f);
		
		float smooth = (raw * 0.6f) + (lastGips * 0.4f);
		
		lastGips = smooth;
		
		return smooth;
	}
	
	private final ExecutorService sampler = Executors.newSingleThreadExecutor();
	private final ExecutorService worker = Executors.newSingleThreadExecutor();
	private final Handler uiHandler = new Handler(Looper.getMainLooper());
	private float cpu = 0;
	private float ram = 0;
	private float gips = 0;
	
	
	private void startMonitor() {
		
		isRunning = true;
		
		detectRefreshRate();
		
		scheduleTick();
	}
	
	private void detectRefreshRate() {
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		if (display != null) {
			refreshRate = display.getRefreshRate();
		}
		
		refreshRate = Math.max(60f, Math.min(refreshRate, 120f));
		
		if (refreshRate >= 120f) {
			logicInterval = 300;
		} else if (refreshRate >= 90f) {
			logicInterval = 350;
		} else {
			logicInterval = 400;
		}
	}
	
	private void startSampler() {
		
		sampler.execute(() -> {
			
			while (isRunning) {
				
				float[] cores = CpuInfo.getCoreUsage();
				
				if (cores != null) {
					latestCores = cores;
				}
				
				try {
					Thread.sleep(100); // 10 FPS sampling
				} catch (Exception ignored) {}
			}
		});
	}
	
	private void runTick() {
		
		if (!isRunning) return;
		
		// ================= REAL DATA =================
		cpu = getCpuUsage();
		ram = getRamUsage();
		
		// ================= GIPS =================
		gips = calculateGIPS(cpu, ram);
		
		// ================= STATS =================
		if (baseline == 0) baseline = gips;
		
		max = Math.max(max, gips);
		
		total += gips;
		count++;
		
		if (count > 1000) {
			total *= 0.5f;
			count *= 0.5f;
		}
		
		performance = (baseline > 0)
		? (gips / baseline) * 100f
		: 0f;
		
		stability = (max > 0)
		? (total / count) / max * 100f
		: 0f;
		
		cpuBias = (max > 0)
		? (gips / max) * 100f
		: 0f;
		
		latestGips = gips;
		latestThrottle = gips < (baseline * 0.8f);
		
		// ================= BUILD CORE DATA =================
		float[] graphData = new float[]{ cpu };
		
		// ================= UI =================
		uiHandler.post(() -> {
			
			textview69.setText(format(gips) + " GIPS");
			
			textview68.setText(
			"CPU: " + format(cpu) +
			" | RAM: " + format(ram) +
			" | PERF: " + format(performance)
			);
			
			textview69.setTextColor(
			latestThrottle ? COLOR_THROTTLE : COLOR_OK
			);
			
			if (linear120 != null) {
				linear120.setThrottleState(
				latestThrottle,
				COLOR_OK,
				COLOR_THROTTLE
				);
				linear120.addValues(graphData, latestThrottle);
			}
		});
	}
	
	private void scheduleTick() {
		
		if (!isRunning) return;
		
		uiHandler.postDelayed(() -> {
			
			runTick();
			
			scheduleTick();
			
		}, logicInterval);
	}
	{
	}
	
	
	public void _Activated() {
	}
	
	public boolean isServiceRunning(Class<?> serviceClass) {
		Context context = getContext();
		if (context == null) return false;
		
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		if (manager == null) return false;
		
		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (serviceClass.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}
	
	
	{
	}
	
	
	public void _getReso() {
	}
	public void getWmSize() {
		new Thread(() -> {
			try {
				
				Process process = ExecEngine.newProcess(new String[]{"sh", "-c", "wm size"});
				
				BufferedReader reader = new BufferedReader(
				new InputStreamReader(process.getInputStream())
				);
				
				StringBuilder result = new StringBuilder();
				String line;
				
				while ((line = reader.readLine()) != null) {
					result.append(line).append("\n");
				}
				
				String output = result.toString();
				
				String width = "";
				String height = "";
				
				String sizeLine = null;
				
				if (output.contains("Override size:")) {
					sizeLine = output.split("Override size:")[1].trim();
				} else if (output.contains("Physical size:")) {
					sizeLine = output.split("Physical size:")[1].trim();
				}
				
				if (sizeLine != null && sizeLine.contains("x")) {
					String[] wh = sizeLine.split("x");
					
					if (wh.length >= 2) {
						width = wh[0].trim();
						height = wh[1].trim();
					}
				}
				
				finalWidth = width;
				finalHeight = height;
				
				if (getActivity() != null) {
					getActivity().runOnUiThread(() -> {
						edittext1.setText(finalWidth);
						edittext2.setText(finalHeight);
					});
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				
				if (getActivity() != null) {
					getActivity().runOnUiThread(() -> {
						edittext1.setText("error");
						edittext2.setText("error");
					});
				}
			}
		}).start();
	}
	{
	}
	private float scale = 0;
	
	private int dp(int v) {
		return (int) (v * scale);
	}
	{
	}
	
	
	public void _cros() {
	}
	int d = 10;
	{
	}
	WindowManager.LayoutParams params;
	View floatView;
	
	int posX = 0;
	int posY = 0;
	
	boolean isMoving = false;
	
	Handler moveHandler = new Handler();
	Runnable moveRunnable;
	{
	}
	
	
	public void _setCrosshairColor(final String _hex) {
		Intent i = new Intent(requireContext(), CrosshairService.class);
		i.putExtra("color", Color.parseColor(_hex.toString()));
		
		requireContext().startService(i);
	}
	
	
	public void _c() {
		textview83.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview84.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffregular.ttf"), 0);
		textview44.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		txt_percent.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview45.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview46.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview47.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		textview48.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		nilaix.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		nilaiy.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/gffbold.ttf"), 1);
		
		
		final int moveStep = 2;
		
		View.OnTouchListener moveTouchListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				switch (event.getAction()) {
					
					case MotionEvent.ACTION_DOWN:
					
					if (v.getId() == R.id.atas) {
						CrosshairService.moveX = 0;
						CrosshairService.moveY = -moveStep;
					}
					
					if (v.getId() == R.id.bawah) {
						CrosshairService.moveX = 0;
						CrosshairService.moveY = moveStep;
					}
					
					if (v.getId() == R.id.kiri) {
						CrosshairService.moveX = -moveStep;
						CrosshairService.moveY = 0;
					}
					
					if (v.getId() == R.id.kanan) {
						CrosshairService.moveX = moveStep;
						CrosshairService.moveY = 0;
					}
					
					break;
					
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_CANCEL:
					
					CrosshairService.moveX = 0;
					CrosshairService.moveY = 0;
					
					break;
				}
				
				nilaix.setText(String.valueOf(CrosshairService.posX));
				nilaiy.setText(String.valueOf(CrosshairService.posY));
				
				return true;
			}
		};
		
		atas.setOnTouchListener(moveTouchListener);
		bawah.setOnTouchListener(moveTouchListener);
		kiri.setOnTouchListener(moveTouchListener);
		kanan.setOnTouchListener(moveTouchListener);
		seekbar1.setMax(100);
		seekbar1.setProgress(10);
		
		GradientDrawable thumb = new GradientDrawable();
		thumb.setShape(GradientDrawable.OVAL);
		thumb.setSize(1, 1);
		thumb.setColor(Color.TRANSPARENT);
		seekbar1.setThumb(thumb);
		
		seekbar1.setPadding(0, 0, 0, 0);
		
		GradientDrawable bg = new GradientDrawable();
		bg.setShape(GradientDrawable.RECTANGLE);
		bg.setCornerRadius(100f);
		bg.setColor(Color.parseColor("#181E1E"));
		
		GradientDrawable progress = new GradientDrawable();
		progress.setShape(GradientDrawable.RECTANGLE);
		progress.setCornerRadius(100f);
		progress.setColor(Color.parseColor("#22B4FF"));
		
		ClipDrawable clip = new ClipDrawable(progress, Gravity.START, ClipDrawable.HORIZONTAL);
		
		LayerDrawable layer = new LayerDrawable(new Drawable[]{bg, clip});
		layer.setId(0, android.R.id.background);
		layer.setId(1, android.R.id.progress);
		seekbar1.setProgressDrawable(layer);
		
		seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
				
				txt_percent.setText(progressValue + ".0%");
				
				int minSizeDp = 10;
				int maxSizeDp = 150;
				
				float percent = progressValue / 100f;
				int sizeDp = (int)(minSizeDp + (percent * (maxSizeDp - minSizeDp)));
				
				int sizePx = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP,
				sizeDp,
				getResources().getDisplayMetrics()
				);
				
				Intent i = new Intent(getActivity(), CrosshairService.class);
				i.putExtra("size", sizePx);
				
				getActivity().startService(i);
			}
			
			@Override public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override public void onStopTrackingTouch(SeekBar seekBar) {}
		});
		red.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)18, 0xFFD50000));
		pink.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)18, 0xFFC51162));
		ungu.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)18, 0xFFAA00FF));
		blue.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)18, 0xFF2962FF));
		black.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)18, 0xFF000000));
		white.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)18, 0xFFFFFFFF));
		atas.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF181E1E));
		bawah.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF181E1E));
		kiri.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF181E1E));
		kanan.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF181E1E));
		linear71.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)60, 0xFF181E1E));
		linear72.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)60, 0xFF181E1E));
		linear57.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)3, 0xFF22B4FF, 0xFF0D6F82));
		linear58.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
		linear59.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
		linear60.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
		linear61.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
		linear279.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
		linear280.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)0, Color.TRANSPARENT, 0xFF292F2F));
		_colorTransform(imageview14, "#FFFFFF");
		_colorTransform(imageview15, "#FFFFFF");
		_colorTransform(imageview16, "#FFFFFF");
		_colorTransform(imageview17, "#FFFFFF");
		_colorTransform(imageview18, "#FFFFFF");
		_colorTransform(imageview59, "#FFFFFF");
		_colorTransform(imageview60, "#FFFFFF");
		
	}
	
}
