package com.hypers.hm;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.ClipData;
import android.content.ClipboardManager;
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
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;
import org.lsposed.hiddenapibypass.library.*;
import rikka.shizuku.api.*;
import rikka.shizuku.provider.*;

public class PrivacypluginsActivity extends AppCompatActivity {
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private ScrollView vscroll1;
	private TextView textview1;
	private TextView textview2;
	private LinearLayout linear6;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear7;
	private LinearLayout linear3;
	private TextView textview5;
	private TextView textview4;
	private TextView textview3;
	private ImageView imageview1;
	private LinearLayout linear8;
	private LinearLayout linear9;
	private LinearLayout linear12;
	private LinearLayout linear13;
	private LinearLayout linear14;
	private TextView textview8;
	private LinearLayout linear15;
	private TextView textview9;
	private LinearLayout linear16;
	private TextView textview6;
	private CircleImageView circleimageview1;
	
	private Intent i = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.privacyplugins);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		vscroll1 = findViewById(R.id.vscroll1);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		linear6 = findViewById(R.id.linear6);
		linear4 = findViewById(R.id.linear4);
		linear5 = findViewById(R.id.linear5);
		linear7 = findViewById(R.id.linear7);
		linear3 = findViewById(R.id.linear3);
		textview5 = findViewById(R.id.textview5);
		textview4 = findViewById(R.id.textview4);
		textview3 = findViewById(R.id.textview3);
		imageview1 = findViewById(R.id.imageview1);
		linear8 = findViewById(R.id.linear8);
		linear9 = findViewById(R.id.linear9);
		linear12 = findViewById(R.id.linear12);
		linear13 = findViewById(R.id.linear13);
		linear14 = findViewById(R.id.linear14);
		textview8 = findViewById(R.id.textview8);
		linear15 = findViewById(R.id.linear15);
		textview9 = findViewById(R.id.textview9);
		linear16 = findViewById(R.id.linear16);
		textview6 = findViewById(R.id.textview6);
		circleimageview1 = findViewById(R.id.circleimageview1);
		
		imageview1.setOnClickListener(_v -> ((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", textview3.getText().toString())));
		
		linear13.setOnClickListener(_v -> {
			linear9.setVisibility(View.VISIBLE);
			linear12.setVisibility(View.GONE);
			textview8.setTextColor(0xFF7EF0E1);
			textview9.setTextColor(0xFF868584);
			linear15.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)60, 0xFF7EF0E1));
			linear16.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)60, Color.TRANSPARENT));
		});
		
		linear14.setOnClickListener(_v -> {
			linear9.setVisibility(View.GONE);
			linear12.setVisibility(View.VISIBLE);
			textview8.setTextColor(0xFF868584);
			textview9.setTextColor(0xFF7EF0E1);
			linear15.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)60, Color.TRANSPARENT));
			linear16.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)60, 0xFF7EF0E1));
		});
		
		circleimageview1.setOnClickListener(_v -> {
			i.setAction(Intent.ACTION_VIEW);
			i.setData(Uri.parse("https://wa.me/6285892643900"));
			startActivity(i);
		});
	}
	
	private void initializeLogic() {
		getWindow().setDecorFitsSystemWindows(false);
		
		if (Build.VERSION.SDK_INT >= 30) {
			getWindow().setDecorFitsSystemWindows(true);
		}
		
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		
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
		vscroll1.setVerticalScrollBarEnabled(false);
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffbold.ttf"), 1);
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffregular.ttf"), 0);
		textview4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffbold.ttf"), 1);
		textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffregular.ttf"), 0);
		textview5.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffregular.ttf"), 0);
		textview8.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffbold.ttf"), 1);
		textview9.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffbold.ttf"), 1);
		textview6.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gffregular.ttf"), 0);
		linear4.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear7.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear5.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF1A1A1A));
		linear9.setVisibility(View.VISIBLE);
		linear12.setVisibility(View.GONE);
		textview8.setTextColor(0xFF7EF0E1);
		textview9.setTextColor(0xFF868584);
		linear15.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)60, 0xFF7EF0E1));
		linear16.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)60, Color.TRANSPARENT));
		_sizetetap();
	}
	
	@Override
	public void onBackPressed() {
		animateExit();
	}
	public void _rippleRoundStroke(final View _view, final String _focus, final String _pressed, final double _round, final double _stroke, final String _strokeclr) {
		android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
		GG.setColor(Color.parseColor(_focus));
		GG.setCornerRadius((float)_round);
		GG.setStroke((int) _stroke,
		Color.parseColor("#" + _strokeclr.replace("#", "")));
		android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.parseColor("#FF1E2A28")}), GG, null);
		_view.setBackground(RE);
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
		int targetDpi = (int) (scale * 160);
		
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
	
}