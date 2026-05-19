package com.hypers.hm.modelUi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.RadialGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Typeface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

public class ROGPanel extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();
    private Matrix matrix = new Matrix();
    private float cpuLevel = 0f;
    private float ramLevel = 0f;
    private float targetCpu = 0f;
    private float targetRam = 0f;
    private int batteryLevel = 0;
    private android.graphics.Bitmap rogBitmap;

    public void setLevels(float cpu, float ram) {
        targetCpu = cpu;
        targetRam = ram;

        removeCallbacks(animatorRunnable);
        post(animatorRunnable);
    }

    private final Runnable animatorRunnable = new Runnable() {
        @Override
        public void run() {
            cpuLevel += (targetCpu - cpuLevel) * 0.10f;
            ramLevel += (targetRam - ramLevel) * 0.10f;

            if (Math.abs(targetCpu - cpuLevel) < 0.3f) cpuLevel = targetCpu;
            if (Math.abs(targetRam - ramLevel) < 0.3f) ramLevel = targetRam;

            invalidate();

            if (cpuLevel != targetCpu || ramLevel != targetRam) {
                postDelayed(this, 16);
            }
        }
    };


    public ROGPanel(Context context) {
        super(context);
        init();
    }

    public ROGPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ROGPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
Intent batteryStatus = getContext().registerReceiver(null, ifilter);

if (batteryStatus != null) {
    int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

    if (scale > 0) {
        batteryLevel = (int) ((level / (float) scale) * 100);
    }
}

        float scaleX = getWidth() / 780f;
        float scaleY = getHeight() / 180f;
        canvas.save();
        canvas.scale(scaleX, scaleY);

        drawAllPaths(canvas);

        canvas.restore();
    }


    private int getSmoothAlpha(float value, float start, float end) {
    if (value <= start)
        return 0;
    if (value >= end)
        return 255;
    float x = (value - start) / (end - start);
    x = x * x * (3f - 2f * x);
    return (int)(x * 255f);
}

    private void drawAllPaths(Canvas canvas) {

        // --- LAYER TRANSPARAN ATAS (VERSI TINGGI SESUAI XML) ---
path.reset();
// Titik awal kiri bawah
path.moveTo(110.00f, 150.00f); 
// Garis miring naik ke pundak kiri
path.lineTo(165.00f, 50.00f);
// Garis miring ke puncak landai
path.lineTo(210.00f, 30.00f);
// Garis datar puncak (Dibuat agak lebar)
path.lineTo(590.00f, 30.00f);
// Garis miring turun ke pundak kanan
path.lineTo(635.00f, 50.00f);
// Garis miring ke bawah kanan
path.lineTo(690.00f, 150.00f);
path.close();

paint.reset();
paint.setAntiAlias(true);

// MENGGUNAKAN RADIAL GRADIENT (Sesuai XML: Merah ke Transparan)
// centerX, centerY, radius
RadialGradient rg = new RadialGradient(390, 40, 300, 
    0xCC460606, 0x00FFFFFF, Shader.TileMode.CLAMP);
paint.setShader(rg);
paint.setStyle(Paint.Style.FILL);
// Gambar path dengan gradient radial
canvas.drawPath(path, paint);
paint.setShader(null); // Reset agar tidak mengganggu objek lain
        
        // bg merah
        path.reset();
        path.moveTo(300.89f, 60.85f);
        path.quadTo(323.56f, 77.50f, 327.34f, 78.30f);
        path.quadTo(456.68f, 78.30f, 461.40f, 76.33f);
        path.quadTo(474.69f, 65.99f, 480.03f, 61.37f);
        path.quadTo(474.06f, 60.46f, 461.77f, 58.49f);
        path.quadTo(443.11f, 55.93f, 431.64f, 55.25f);
        path.quadTo(423.49f, 54.45f, 416.22f, 54.11f);
        path.quadTo(408.90f, 53.82f, 402.61f, 53.77f);
        path.quadTo(390.04f, 53.26f, 381.35f, 53.61f);
        path.quadTo(368.08f, 53.88f, 361.68f, 54.47f);
        path.quadTo(355.96f, 54.71f, 351.82f, 55.13f);
        path.quadTo(347.12f, 55.42f, 344.33f, 55.60f);
        path.quadTo(340.78f, 55.90f, 336.67f, 56.11f);
        path.quadTo(330.73f, 56.54f, 326.70f, 57.22f);
        path.quadTo(313.01f, 59.00f, 300.89f, 60.85f);
        path.close();

        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(0xFF460606);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(1.9711698f);
        paint.setColor(0xFF460606);
        canvas.drawPath(path, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xFF460606);
        canvas.drawPath(path, paint);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);

        textPaint.setTextSize(15f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        Typeface typeface = Typeface.createFromAsset(
                getContext().getAssets(),
                "fonts/gffbold.ttf"
        );

        textPaint.setTypeface(typeface);
        canvas.drawText(
                "HM - MODE",
                390f,
                71f,
                textPaint
        );
        

        // bg utama
                // ==========================================
        // BG UTAMA (HITAM & STROKE MERAH GLOW)
        // ==========================================
        path.reset();
        path.moveTo(59.79f, 180.20f);
        path.quadTo(54.75f, 175.51f, 46.51f, 169.04f);
        path.quadTo(41.14f, 164.95f, 38.26f, 162.30f);
        path.quadTo(34.62f, 159.42f, 32.88f, 157.41f);
        path.quadTo(30.28f, 153.96f, 29.70f, 151.12f);
        path.quadTo(29.70f, 146.84f, 32.44f, 143.60f);
        path.lineTo(40.54f, 133.79f);
        path.lineTo(54.42f, 115.97f);
        path.lineTo(71.16f, 95.27f);
        path.lineTo(88.55f, 73.16f);
        path.quadTo(95.30f, 64.97f, 99.80f, 62.22f);
        path.quadTo(104.99f, 59.14f, 108.77f, 58.72f);
        path.lineTo(113.83f, 58.33f);
        path.lineTo(154.62f, 56.99f);
        path.lineTo(188.82f, 55.84f);
        path.lineTo(220.00f, 54.91f);
        path.lineTo(243.94f, 54.28f);
        path.lineTo(266.72f, 54.51f); 
        path.lineTo(288.87f, 54.06f);
        path.quadTo(291.29f, 53.86f, 294.07f, 55.52f);
        path.lineTo(320.77f, 75.87f);
        path.quadTo(325.40f, 78.77f, 331.36f, 78.98f);
        path.lineTo(450.60f, 78.92f);
        path.quadTo(453.22f, 78.92f, 456.18f, 78.42f);
        path.quadTo(459.02f, 77.80f, 461.61f, 76.36f);
        path.lineTo(487.90f, 55.42f);
        path.quadTo(490.43f, 53.74f, 495.10f, 53.40f);
        path.lineTo(672.25f, 58.61f);
        path.quadTo(676.46f, 58.61f, 678.59f, 59.64f);
        path.quadTo(682.93f, 61.97f, 685.71f, 64.40f);
        path.lineTo(691.19f, 70.02f);
        path.lineTo(746.20f, 138.69f);
        path.quadTo(750.60f, 144.43f, 751.92f, 147.06f);
        path.quadTo(752.44f, 149.39f, 752.07f, 151.49f);
        path.quadTo(751.41f, 154.47f, 748.07f, 158.57f);
        path.quadTo(745.05f, 161.46f, 738.15f, 166.93f);
        path.quadTo(734.10f, 170.28f, 722.34f, 179.79f);
        path.close();

        // 1. Gambar Isi (Fill) - Hitam Transparan
        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xF8000000); 
        canvas.drawPath(path, paint);

        // 2. Gambar Garis Tepi (Stroke) - Merah Menyala
        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.2f); 
        paint.setColor(0xFFFF0000); 
        paint.setStrokeJoin(Paint.Join.ROUND); // Mengatasi sudut "patah"
        paint.setStrokeCap(Paint.Cap.ROUND);
        
        // Efek Glow
        paint.setShadowLayer(10.0f, 0.0f, 0.0f, 0xFFFF0000);
        canvas.drawPath(path, paint);
        
        // Reset Shadow agar tidak bocor ke elemen lain
        paint.clearShadowLayer();
        
        
        path.reset();
        path.moveTo(126.10f, 60.39f);
        path.quadTo(120.16f, 65.46f, 117.90f, 67.39f);
        path.quadTo(114.47f, 71.79f, 108.42f, 79.03f);
        path.quadTo(96.32f, 93.78f, 90.26f, 101.23f);
        path.quadTo(76.20f, 118.76f, 70.14f, 126.08f);
        path.quadTo(60.63f, 137.27f, 55.74f, 143.65f);
        path.quadTo(52.99f, 147.41f, 54.43f, 153.22f);
        path.quadTo(60.42f, 160.72f, 65.04f, 167.57f);
        path.lineTo(75.19f, 178.64f);
        path.quadTo(69.76f, 178.58f, 64.95f, 178.58f);
        path.quadTo(47.24f, 164.77f, 42.83f, 160.99f);
        path.quadTo(37.50f, 156.71f, 35.75f, 153.55f);
        path.quadTo(33.61f, 148.21f, 36.12f, 144.88f);
        path.quadTo(39.78f, 140.41f, 43.34f, 136.07f);
        path.quadTo(50.92f, 126.78f, 56.23f, 120.34f);
        path.quadTo(61.48f, 113.54f, 66.02f, 108.16f);
        path.quadTo(78.02f, 93.52f, 83.68f, 86.23f);
        path.quadTo(89.22f, 79.24f, 95.36f, 72.06f);
        path.quadTo(102.41f, 63.18f, 109.43f, 61.00f);
        path.lineTo(126.10f, 60.39f);
        path.close();

        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(0xF2290000);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeWidth(2.8460588f);
        canvas.drawPath(path, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xFF290404);
        canvas.drawPath(path, paint);
        
        // --- CPU BOX 1 (PALING ATAS) ---
        path.reset();
        path.moveTo(96.20f, 71.20f);
        path.lineTo(114.80f, 71.20f);
        path.lineTo(120.40f, 64.30f);
        path.lineTo(125.60f, 58.10f);
        path.lineTo(107.10f, 58.00f);
        path.quadTo(101.30f, 64.90f, 96.20f, 71.20f);
        path.close();

        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        // 1. Gambar Background (Agar tidak kosong saat alpha 0)
        paint.setColor(0xFF460605); 
        canvas.drawPath(path, paint);
        // 2. Gambar Warna Aktif (Naik-Turun)
        paint.setColor(0xFFFF2A2A);
        paint.setAlpha(getSmoothAlpha(cpuLevel, 80f, 100f));
        canvas.drawPath(path, paint);


        // --- CPU BOX 2 ---
        path.reset();
        path.moveTo(78.54f, 92.75f);
        path.lineTo(97.33f, 92.57f);
        path.lineTo(104.05f, 84.37f);
        path.lineTo(109.63f, 77.48f);
        path.lineTo(90.93f, 77.36f);
        path.quadTo(83.68f, 86.23f, 78.54f, 92.75f);
        path.close();

        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        // 1. Gambar Background
        paint.setColor(0xFF460605);
        canvas.drawPath(path, paint);
        // 2. Gambar Warna Aktif
        paint.setColor(0xFFFF2A2A);
        paint.setAlpha(getSmoothAlpha(cpuLevel, 64f, 80f));
        canvas.drawPath(path, paint);


        // --- CPU BOX 3 ---
        path.reset();
        path.moveTo(60.97f, 114.42f);
        path.lineTo(79.54f, 114.49f);
        path.lineTo(86.86f, 105.66f);
        path.lineTo(92.55f, 98.45f);
        path.lineTo(73.74f, 98.66f);
        path.quadTo(66.49f, 107.52f, 60.97f, 114.42f);
        path.close();
   
        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        // 1. Gambar Background
        paint.setColor(0xFF460605);
        canvas.drawPath(path, paint);
        // 2. Gambar Gradasi Aktif
        Shader fillShader = new LinearGradient(70.085f, 114.826f, 81.803f, 97.690f, new int[]{0xFFFA0092, 0xFFFA1718}, null, Shader.TileMode.CLAMP);
        paint.setShader(fillShader);
        paint.setAlpha(getSmoothAlpha(cpuLevel, 48f, 64f));
        canvas.drawPath(path, paint);
        paint.setShader(null); // Reset shader


        // --- CPU BOX 4 ---
        path.reset();
        path.moveTo(43.81f, 135.42f);
        path.lineTo(62.23f, 135.49f);
        path.lineTo(69.04f, 127.28f);
        path.lineTo(75.06f, 120.08f);
        path.lineTo(56.44f, 120.14f);
        path.quadTo(48.80f, 129.13f, 43.81f, 135.42f);
        path.close();

        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        // 1. Gambar Background
        paint.setColor(0xFF460605);
        canvas.drawPath(path, paint);
        // 2. Gambar Gradasi Aktif
        Shader fillShader2 = new LinearGradient(64.303f, 120.390f, 52.627f, 135.288f, new int[]{0xFFFA0092, 0xFFC000FA}, null, Shader.TileMode.CLAMP);
        paint.setShader(fillShader2);
        paint.setAlpha(getSmoothAlpha(cpuLevel, 32f, 48f));
        canvas.drawPath(path, paint);
        paint.setShader(null);


        // --- CPU BOX 5 ---
        path.reset();
        path.moveTo(38.19f, 156.71f);
        path.quadTo(30.62f, 149.40f, 39.26f, 141.07f);
        path.lineTo(57.56f, 141.32f);
        path.lineTo(55.93f, 143.35f);
        path.quadTo(52.93f, 146.97f, 54.43f, 153.22f);
        path.lineTo(57.09f, 156.64f);
        path.lineTo(38.19f, 156.71f);
        path.close();

        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        // 1. Gambar Background
        paint.setColor(0xFF460605);
        canvas.drawPath(path, paint);
        // 2. Gambar Gradasi Aktif
        Shader fillShader3 = new LinearGradient(46.312f, 156.737f, 49.490f, 140.974f, new int[]{0xFF8400FA, 0xFFC000FA}, null, Shader.TileMode.CLAMP);
        paint.setShader(fillShader3);
        paint.setAlpha(getSmoothAlpha(cpuLevel, 16f, 32f));
        canvas.drawPath(path, paint);
        paint.setShader(null);


        // --- CPU BOX 6 (PALING BAWAH) ---
        path.reset();
        path.moveTo(65.04f, 178.69f);
        path.quadTo(54.97f, 170.79f, 45.15f, 162.91f);
        path.lineTo(61.39f, 162.34f);
        path.lineTo(63.85f, 165.48f);
        path.quadTo(66.86f, 168.74f, 70.67f, 173.84f);
        path.lineTo(75.09f, 178.67f);
        path.lineTo(65.04f, 178.69f);
        path.close();

        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        // 1. Gambar Background
        paint.setColor(0xFF460605);
        canvas.drawPath(path, paint);
        // 2. Gambar Gradasi Aktif
        Shader fillShader4 = new LinearGradient(52.666f, 162.284f, 69.916f, 177.877f, new int[]{0xFF8400FA, 0xFF6300FA}, null, Shader.TileMode.CLAMP);
        paint.setShader(fillShader4);
        paint.setAlpha(getSmoothAlpha(cpuLevel, 0f, 16f));
        canvas.drawPath(path, paint);
        paint.setShader(null);

        path.reset();
        path.moveTo(291.36f, 182.03f);
        path.quadTo(300.24f, 152.80f, 306.39f, 137.02f);
        path.quadTo(310.37f, 124.94f, 320.51f, 95.28f);
        path.quadTo(320.86f, 94.08f, 322.16f, 91.52f);
        path.quadTo(323.71f, 89.91f, 325.51f, 89.24f);
        path.quadTo(327.10f, 88.75f, 329.17f, 88.75f);

        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeWidth(1.9752917f);
        paint.setColor(0x00000000);
        paint.setShader(null);
        canvas.drawPath(path, paint);
        paint.setColor(0xFFFF0000);
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(331.49f, 88.77f);
        path.lineTo(338.76f, 88.77f);

        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeWidth(2.8671365f);
        paint.setColor(0xFF000000);
        paint.setShader(null);
        canvas.drawPath(path, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xFFFF0000);
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(487.44f, 182.27f);
        path.quadTo(478.56f, 153.04f, 472.41f, 137.25f);
        path.quadTo(468.42f, 125.18f, 458.29f, 95.52f);
        path.quadTo(457.93f, 94.31f, 456.63f, 91.75f);
        path.quadTo(455.08f, 90.15f, 453.29f, 89.48f);
        path.quadTo(451.69f, 88.99f, 449.63f, 88.99f);

        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeWidth(1.9752922f);
        paint.setColor(0x00000000);
        paint.setShader(null);
        canvas.drawPath(path, paint);
        paint.setColor(0xFFFF0000);
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(447.30f, 89.01f);
        path.lineTo(440.04f, 89.01f);

        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeWidth(2.8671372f);
        paint.setColor(0xFF000000);
        paint.setShader(null);
        canvas.drawPath(path, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xFFFF0000);
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(125.93f, 60.36f);
        path.quadTo(120.03f, 64.80f, 117.45f, 68.18f);
        path.lineTo(113.85f, 72.42f);
        path.lineTo(94.77f, 72.43f);
        path.quadTo(100.00f, 65.14f, 108.92f, 61.07f);
        path.lineTo(117.95f, 60.36f);
        path.quadTo(121.07f, 60.36f, 125.93f, 60.36f);
        path.close();

        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeWidth(2.8460588f);
        paint.setColor(0xFF460605);
        paint.setShader(null);
        canvas.drawPath(path, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xFF460605);
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(656.34f, 60.38f);
        path.quadTo(662.27f, 65.43f, 664.53f, 67.34f);
        path.quadTo(667.96f, 71.73f, 674.01f, 78.94f);
        path.quadTo(686.12f, 93.64f, 692.17f, 101.06f);
        path.quadTo(706.24f, 118.53f, 712.30f, 125.82f);
        path.quadTo(721.80f, 136.97f, 726.70f, 143.33f);
        path.quadTo(729.44f, 147.07f, 728.01f, 152.86f);
        path.quadTo(722.02f, 160.34f, 717.40f, 167.17f);
        path.lineTo(707.25f, 178.19f);
        path.quadTo(712.68f, 178.14f, 717.48f, 178.14f);
        path.quadTo(735.20f, 164.37f, 739.60f, 160.61f);
        path.quadTo(744.93f, 156.34f, 746.69f, 153.19f);
        path.quadTo(748.83f, 147.88f, 746.31f, 144.55f);
        path.quadTo(742.65f, 140.10f, 739.10f, 135.78f);
        path.quadTo(731.52f, 126.52f, 726.21f, 120.10f);
        path.quadTo(720.96f, 113.33f, 716.41f, 107.97f);
        path.quadTo(704.41f, 93.38f, 698.76f, 86.11f);
        path.quadTo(693.21f, 79.15f, 687.08f, 72.00f);
        path.quadTo(680.02f, 63.15f, 673.01f, 60.98f);
        path.lineTo(656.34f, 60.38f);
        path.close();

        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(0xF2290000);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeWidth(2.8409016f);
        paint.setShader(null);
        canvas.drawPath(path, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xFF290404);
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(703.89f, 92.61f);
        path.lineTo(685.10f, 92.43f);
        path.lineTo(678.39f, 84.27f);
        path.lineTo(672.80f, 77.40f);
        path.lineTo(691.50f, 77.28f);
        path.quadTo(698.76f, 86.11f, 703.89f, 92.61f);
        path.close();

        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(0xFF460605);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeWidth(2.8409016f);
        paint.setShader(null);
        canvas.drawPath(path, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xFF460605);
        canvas.drawPath(path, paint);

        path.reset();
path.moveTo(703.20f, 92.61f);
path.lineTo(685.90f, 92.43f);
path.lineTo(678.39f, 84.27f);
path.lineTo(672.80f, 77.40f);
path.lineTo(691.50f, 77.28f);
path.quadTo(698.76f, 86.11f, 703.20f, 92.61f);
path.close();

paint.reset();
paint.setAntiAlias(true);
paint.setStyle(Paint.Style.FILL);
paint.setStrokeJoin(Paint.Join.MITER);
paint.setStrokeCap(Paint.Cap.SQUARE);
paint.setStrokeWidth(2.8409016f);
paint.setColor(0xFFFF2A2A);
// kotak 6
paint.setAlpha(getSmoothAlpha(ramLevel, 80f, 100f));
paint.setShader(null);
canvas.drawPath(path, paint);


// kotak tambahan RAM ke-6
path.reset();
path.moveTo(686.10f, 71.05f);
path.lineTo(667.40f, 70.90f);
path.lineTo(661.90f, 64.10f);
path.lineTo(656.80f, 58.00f);
path.lineTo(675.40f, 57.90f);
path.quadTo(680.90f, 64.70f, 686.10f, 71.05f);
path.close();

paint.reset();
paint.setAntiAlias(true);
paint.setColor(0xFFFF2A2A);
paint.setStyle(Paint.Style.FILL);
paint.setStrokeJoin(Paint.Join.MITER);
paint.setStrokeCap(Paint.Cap.SQUARE);
paint.setStrokeWidth(2.8409016f);
paint.setShader(null);
// kotak 5
paint.setAlpha(getSmoothAlpha(ramLevel, 64f, 80f));
canvas.drawPath(path, paint);


// RAM kotak tengah
path.reset();
path.moveTo(721.47f, 114.20f);
path.lineTo(702.90f, 114.28f);
path.lineTo(695.58f, 105.48f);
path.lineTo(689.89f, 98.29f);
path.lineTo(708.69f, 98.50f);
path.quadTo(715.95f, 107.33f, 721.47f, 114.20f);
path.close();

paint.reset();
paint.setAntiAlias(true);
paint.setStyle(Paint.Style.FILL);

Shader fillShaderR4 = new LinearGradient(
        720f, 114f,
        700f, 98f,
        new int[]{0xFFFA0092, 0xFFFA1718},
        null,
        Shader.TileMode.CLAMP
);

paint.setShader(fillShaderR4);
// kotak 4
paint.setAlpha(getSmoothAlpha(ramLevel, 48f, 64f));

canvas.drawPath(path, paint);


// RAM kotak bawah tengah
path.reset();
path.moveTo(738.62f, 135.13f);
path.lineTo(720.21f, 135.20f);
path.lineTo(713.39f, 127.02f);
path.lineTo(707.38f, 119.84f);
path.lineTo(726.00f, 119.90f);
path.quadTo(733.63f, 128.86f, 738.62f, 135.13f);
path.close();

paint.reset();
paint.setAntiAlias(true);
paint.setStyle(Paint.Style.FILL);

Shader fillShaderR3 = new LinearGradient(
        736f, 135f,
        712f, 120f,
        new int[]{0xFFFA0092, 0xFFC000FA},
        null,
        Shader.TileMode.CLAMP
);

paint.setShader(fillShaderR3);
// kotak 3
paint.setAlpha(getSmoothAlpha(ramLevel, 32f, 48f));

canvas.drawPath(path, paint);


// RAM kotak ungu bawah
path.reset();
path.moveTo(744.24f, 156.34f);
path.quadTo(751.82f, 149.06f, 743.18f, 140.76f);
path.lineTo(724.88f, 141.01f);
path.lineTo(726.50f, 143.04f);
path.quadTo(729.51f, 146.64f, 728.01f, 152.86f);
path.lineTo(725.34f, 156.28f);
path.lineTo(744.24f, 156.34f);
path.close();

paint.reset();
paint.setAntiAlias(true);
paint.setStyle(Paint.Style.FILL);
paint.setStrokeJoin(Paint.Join.MITER);
paint.setStrokeCap(Paint.Cap.SQUARE);
paint.setStrokeWidth(2.8409016f);

Shader fillShaderR = new LinearGradient(
        736.124f, 156.369f,
        732.946f, 140.664f,
        new int[]{0xFF8400FA, 0xFFC000FA},
        null,
        Shader.TileMode.CLAMP
);

paint.setShader(fillShaderR);
// kotak 2
paint.setAlpha(getSmoothAlpha(ramLevel, 16f, 32f));

canvas.drawPath(path, paint);


// RAM kotak paling bawah
path.reset();
path.moveTo(717.40f, 178.25f);
path.quadTo(727.46f, 170.38f, 737.29f, 162.52f);
path.lineTo(721.04f, 161.96f);
path.lineTo(718.59f, 165.08f);
path.quadTo(715.57f, 168.33f, 711.76f, 173.41f);
path.lineTo(707.35f, 178.22f);
path.lineTo(717.40f, 178.25f);
path.close();

paint.reset();
paint.setAntiAlias(true);
paint.setStyle(Paint.Style.FILL);
paint.setStrokeJoin(Paint.Join.MITER);
paint.setStrokeCap(Paint.Cap.SQUARE);
paint.setStrokeWidth(2.8409016f);

Shader fillShaderR2 = new LinearGradient(
        729.769f, 161.897f,
        712.520f, 177.433f,
        new int[]{0xFF8400FA, 0xFF6300FA},
        null,
        Shader.TileMode.CLAMP
);

paint.setShader(fillShaderR2);
// kotak 1
paint.setAlpha(getSmoothAlpha(ramLevel, 0f, 16f));

canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(656.51f, 60.35f);
        path.quadTo(662.40f, 64.76f, 664.99f, 68.13f);
        path.lineTo(668.59f, 72.36f);
        path.lineTo(687.66f, 72.37f);
        path.quadTo(682.44f, 65.10f, 673.52f, 61.05f);
        path.lineTo(664.49f, 60.35f);
        path.quadTo(661.37f, 60.35f, 656.51f, 60.35f);
        path.close();

        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeWidth(2.8409016f);
        paint.setColor(0xFF460605);
        paint.setShader(null);
        canvas.drawPath(path, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xFF460605);
        canvas.drawPath(path, paint);
        
        Paint leftText = new Paint(Paint.ANTI_ALIAS_FLAG);
        leftText.setColor(0xFFFF0000);
        leftText.setTextSize(20f);
        leftText.setTextAlign(Paint.Align.CENTER);
        leftText.setTypeface(typeface);
        leftText.setFakeBoldText(true);
        canvas.drawText(
                "CPU",
                90f,
                159f,
                leftText
        );

        Paint rightText = new Paint(Paint.ANTI_ALIAS_FLAG);
        rightText.setColor(0xFFFF0000);
        rightText.setTextSize(20f);
        rightText.setTextAlign(Paint.Align.CENTER);
        rightText.setTypeface(typeface);
        rightText.setFakeBoldText(true);
        canvas.drawText(
                "RAM",
                690f,
                159f,
                rightText
        );

        Paint axeronText = new Paint(Paint.ANTI_ALIAS_FLAG);
        axeronText.setColor(0xFFEAEAEA);
        axeronText.setTextSize(8.5f);
        axeronText.setTextAlign(Paint.Align.CENTER);
        axeronText.setTypeface(typeface);
        axeronText.setLetterSpacing(0.35f);
        canvas.drawText(
                "HYPERS",
                390f,
                116f,
                axeronText
        );

        Paint gameText = new Paint(Paint.ANTI_ALIAS_FLAG);
        android.graphics.Typeface typeFace = android.graphics.Typeface.createFromAsset(getContext().getAssets(), "gamepix.ttf");
        gameText.setColor(Color.WHITE);
        gameText.setTextSize(18f);
        gameText.setTextAlign(Paint.Align.CENTER);
        gameText.setTypeface(typeFace);
        gameText.setLetterSpacing(0.02f);
        gameText.setFakeBoldText(true);
        gameText.setShadowLayer(12f, 0f, 0f, 0xFFFF2A2A);
        canvas.drawText(
                "GAME ENGINE",
                390f,
                145f,
                gameText
        );

        Paint pmTopText = new Paint(Paint.ANTI_ALIAS_FLAG);
        pmTopText.setColor(0xFF4F4F4F);
        pmTopText.setTextSize(7f);
        pmTopText.setTextAlign(Paint.Align.CENTER);
        pmTopText.setTypeface(typeface);
        pmTopText.setLetterSpacing(0.08f);
        java.text.SimpleDateFormat sdf =
        new java.text.SimpleDateFormat("a hh:mm", java.util.Locale.getDefault());
        String realTime = sdf.format(new java.util.Date());
        canvas.drawText(
        realTime,
        360f,
        91f,
        pmTopText
        );

    Paint batteryBar = new Paint(Paint.ANTI_ALIAS_FLAG);
    batteryBar.setStyle(Paint.Style.FILL);

    float centerX = 400f; 
    float topY = 85f;
    float barWidth = 3.3f;
    float barGap = 2.0f;
    float barHeight = 6.5f;
    int totalBars = 8;

    float totalWidth = (totalBars * barWidth) + ((totalBars - 1) * barGap);
    float startX = centerX - (totalWidth / 2f); 

    int activeBars = (int) Math.ceil(batteryLevel / 12.5f);

    for (int i = 0; i < totalBars; i++) {
    float x = startX + (i * (barWidth + barGap));
    path.reset();
    path.moveTo(x + 1.5f, topY);
    path.lineTo(x + barWidth + 1.5f, topY);
    path.lineTo(x + barWidth - 1.5f, topY + barHeight);
    path.lineTo(x - 1.5f, topY + barHeight);
    path.close();

    if (i < activeBars) {
        if (batteryLevel <= 15) {
            batteryBar.setColor(0xFFFF0000);
        } else if (batteryLevel <= 30) {
            batteryBar.setColor(0xFFFF4500);
        } else if (batteryLevel <= 45) {
            batteryBar.setColor(0xFFFFA500);
        } else if (batteryLevel <= 60) {
            batteryBar.setColor(0xFFFFFF00);
        } else if (batteryLevel <= 80) {
            batteryBar.setColor(0xFFADFF2F);
        } else {
            batteryBar.setColor(0xFF00FF00);
        }
        batteryBar.setAlpha(255);
    } else {
        batteryBar.setColor(0xFF444444);
        batteryBar.setAlpha(100);
    }

    canvas.drawPath(path, batteryBar);
        }

        Paint batteryText = new Paint(Paint.ANTI_ALIAS_FLAG);
        batteryText.setColor(0xFF707070);
        batteryText.setTextSize(7f);
        batteryText.setTextAlign(Paint.Align.CENTER);
        batteryText.setTypeface(typeface);
        batteryText.setLetterSpacing(0.04f);
        canvas.drawText(
        batteryLevel + "%",
        429f,
        91f,
        batteryText
        );
    }

}