package com.hypers.hm.service;

import android.app.*;
import android.content.*;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.os.*;
import android.view.*;
import android.widget.*;
import androidx.core.app.NotificationCompat;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.util.DisplayMetrics;
import android.content.res.Configuration;
import android.view.Choreographer;
import android.view.ViewTreeObserver;
import android.graphics.Region;
import com.hypers.hm.R;
import com.hypers.hm.ShizukuUtil;

import android.content.pm.ServiceInfo;

public class FloatingService extends Service {
    
    private static boolean isRunning = false;

    private int fpsCount = 0;
    private long lastFpsTime = 0;
    
    private static final int VIRTUAL_HEIGHT = 450;
    private static final int VIRTUAL_WIDTH = 1600;

    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private boolean isMonitorRunning = false;
    
    private static final String CHANNEL_ID = "float_channel";
    private static final int NOTIF_ID = 99;
    
    private float lastTotal = 0;
    private float lastIdle = 0;
    private final long logicInterval = 2000;

    private WindowManager windowManager;
    private View floatingView;
    private WindowManager.LayoutParams params;

    private LinearLayout l1;
    private View l2;
    private ImageView batteryIcon;
    private TextView cpuTv, gpuTv, ramTv, suhuTv, fpsTv, timeTv;

    private BroadcastReceiver batteryReceiver;
    private BroadcastReceiver thermalReceiver;
    
    private Context wrappedContext;

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    private final Date date = new Date();
    private final StringBuilder stringBuilder = new StringBuilder();

    @Override
    public void onCreate() {
        super.onCreate();
        
        wrappedContext = wrapContext(this);
        
        isRunning = true;
        createChannel(); 
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        startForeground(NOTIF_ID, buildNotification(), 
            android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE);
    } else {
        
        startForeground(NOTIF_ID, buildNotification());
    }

        windowManager = (WindowManager) wrappedContext.getSystemService(WINDOW_SERVICE);
        LayoutInflater inflater = LayoutInflater.from(wrappedContext);
        floatingView = inflater.inflate(R.layout.float2, null);

        int type = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                WindowManager.LayoutParams.TYPE_PHONE;

        params = new WindowManager.LayoutParams(
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        type,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
        PixelFormat.TRANSLUCENT
);

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 100;

        windowManager.addView(floatingView, params);

        l1 = floatingView.findViewById(R.id.l1);
        l2 = floatingView.findViewById(R.id.l2);
        batteryIcon = floatingView.findViewById(R.id.battery_view);
        cpuTv = floatingView.findViewById(R.id.cpu);
        gpuTv = floatingView.findViewById(R.id.gpu);
        ramTv = floatingView.findViewById(R.id.ram);
        suhuTv = floatingView.findViewById(R.id.suhu);
        fpsTv = floatingView.findViewById(R.id.FPS);
        timeTv = floatingView.findViewById(R.id.time);
        
        l2.setClickable(false);
l2.setFocusable(false);
l2.setFocusableInTouchMode(false);
l2.setEnabled(false);

        applyDesign();
        setupDrag();
        registerReceivers();
        
        isMonitorRunning = true;
        startUpdating();
        setupFpsCounter();
    }

    private void setupFpsCounter() {
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                if (!isMonitorRunning) return;
                
                fpsCount++;
                if (lastFpsTime == 0) lastFpsTime = frameTimeNanos;
                
                long elapsed = frameTimeNanos - lastFpsTime;
                if (elapsed >= 1000000000L) {
                    stringBuilder.setLength(0);
                    stringBuilder.append(fpsCount).append("");
                    fpsTv.setText(stringBuilder.toString());
                    fpsCount = 0;
                    lastFpsTime = frameTimeNanos;
                }
                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }

    private void startUpdating() {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!isMonitorRunning) return;

                date.setTime(System.currentTimeMillis());
                timeTv.setText(timeFormat.format(date));

                stringBuilder.setLength(0);
                stringBuilder.append(format(getCpuUsage())).append("%");
                cpuTv.setText(stringBuilder.toString());

                stringBuilder.setLength(0);
                stringBuilder.append(format(getRamUsage())).append("%");
                ramTv.setText(stringBuilder.toString());
                
                gpuTv.setText(getCurrentFreq());

                mainHandler.postDelayed(this, logicInterval);
            }
        });
    }

    private void applyDesign() {
        GradientDrawable bg1 = new GradientDrawable();
        bg1.setColor(0x50C62828);
        bg1.setCornerRadius(dp(9));
        bg1.setStroke(dp(1), 0x66FFFFFF);
        l1.setBackground(bg1);

        GradientDrawable bg2 = new GradientDrawable();
        bg2.setColor(0x22000000);
        bg2.setCornerRadius(dp(8));
        bg2.setStroke(dp(0), 0x33FFFFFF);
        l2.setBackground(bg2);
        
        l1.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    private void registerReceivers() {
        batteryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent i) {
                int level = i.getIntExtra("level", 0);
                int scale = i.getIntExtra("scale", 100);
                float percent = (level * 100f) / scale;
                batteryIcon.setImageResource(getBatteryIcon(percent));
            }
        };
        registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        thermalReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                float temp = (float)(intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)) / 10;
                stringBuilder.setLength(0);
                stringBuilder.append(temp).append(" °C");
                suhuTv.setText(stringBuilder.toString());
            }
        };
        registerReceiver(thermalReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private int getBatteryIcon(float p) {
        if (p <= 10) return R.drawable.ic_battery_0;
        else if (p <= 35) return R.drawable.ic_battery_25;
        else if (p <= 60) return R.drawable.ic_battery_50;
        else if (p <= 85) return R.drawable.ic_battery_75;
        else return R.drawable.ic_battery_100;
    }

    private void setupDrag() {
        l1.setOnTouchListener(new View.OnTouchListener() {
            int x, y;
            float tx, ty;
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = params.x; y = params.y;
                        tx = e.getRawX(); ty = e.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = x + (int)(e.getRawX() - tx);
                        params.y = y + (int)(e.getRawY() - ty);
                        windowManager.updateViewLayout(floatingView, params);
                        return true;
                }
                return false;
            }
        });
    }
    
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
        try (RandomAccessFile reader = new RandomAccessFile("/proc/meminfo", "r")) {
            long total = 0, available = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("MemTotal")) total = Long.parseLong(line.replaceAll("\\D+", ""));
                if (line.startsWith("MemAvailable")) available = Long.parseLong(line.replaceAll("\\D+", ""));
                if (total > 0 && available > 0) break;
            }
            return (total == 0) ? 0 : (float)((total - available) * 100f / total);
        } catch (Exception e) { return 0; }
    }

    public String getCurrentFreq() {
        try (RandomAccessFile reader = new RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq", "r")) {
            String line = reader.readLine();
            if (line != null) {
                double freqGHz = Double.parseDouble(line) / 1000000.0;
                return String.format(Locale.getDefault(), "%.2f", freqGHz);
            }
        } catch (Exception e) { return "Dynamic"; }
        return "N/A";
    }

    String format(double v) {
        return String.format(Locale.US, "%.0f", v);
    }

    private int dp(int v) {
        return (int)(v * getResources().getDisplayMetrics().density);
    }

private static Context wrapContext(Context base) {
    DisplayMetrics dm = base.getResources().getDisplayMetrics();
    Configuration config = new Configuration(base.getResources().getConfiguration());
    WindowManager wm = (WindowManager) base.getSystemService(Context.WINDOW_SERVICE);
    int rotation = wm.getDefaultDisplay().getRotation();
    
    int targetWidth = (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) ? VIRTUAL_WIDTH : VIRTUAL_HEIGHT;
    
    float density = (float) dm.widthPixels / targetWidth;
    int densityDpi = (int) (density * 150); 
    
    config.densityDpi = densityDpi;
    Context context = base.createConfigurationContext(config);
    
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    metrics.density = densityDpi / 160f;
    metrics.densityDpi = densityDpi;
    metrics.scaledDensity = metrics.density;
    return context;
}

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel ch = new NotificationChannel(CHANNEL_ID, "Floating Service", NotificationManager.IMPORTANCE_LOW);
            NotificationManager nm = getSystemService(NotificationManager.class);
            if (nm != null) nm.createNotificationChannel(ch);
        }
    }

    private Notification buildNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("HM Running")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && "STOP".equals(intent.getAction())) stopSelf();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isMonitorRunning = false;
        isRunning = false;
        mainHandler.removeCallbacksAndMessages(null);
        if (batteryReceiver != null) unregisterReceiver(batteryReceiver);
        if (thermalReceiver != null) unregisterReceiver(thermalReceiver);
        if (floatingView != null && windowManager != null) windowManager.removeView(floatingView);
    }

    @Override
    public IBinder onBind(Intent intent) { return null; }
}
