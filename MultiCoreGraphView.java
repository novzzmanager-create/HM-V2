package com.hypers.hm;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

public class MultiCoreGraphView extends View {

    private final float[] cpuHistory = new float[60];
    private int index = 0;
    private int size = 0;

    private Paint bgPaint;
    private Paint linePaint;
    private Paint fillPaint;
    private Paint glowPaint;

    private Path linePath = new Path();
    private Path fillPath = new Path();

    private boolean bootFlat = true;

    private long lastDrawTime = 0;
    private static final long FRAME_INTERVAL = 66;

    // =========================
    // COLOR STATE
    // =========================
    private int lineColor = Color.parseColor("#00ACC1");
    private int glowColor = Color.parseColor("#00ACC1");
    private int fillBaseColor = Color.parseColor("#00ACC1");

    private LinearGradient fillShader;

    public MultiCoreGraphView(Context c, AttributeSet a) {
        super(c, a);
        init();
        startBoot();
    }

    // ================= INIT =================
    private void init() {

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setColor(Color.parseColor("#1A1A1A"));

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(3.2f);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeJoin(Paint.Join.ROUND);
        linePaint.setColor(lineColor);

        glowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        glowPaint.setStyle(Paint.Style.STROKE);
        glowPaint.setStrokeWidth(10f);
        glowPaint.setStrokeCap(Paint.Cap.ROUND);
        glowPaint.setStrokeJoin(Paint.Join.ROUND);
        glowPaint.setColor(glowColor);
        glowPaint.setMaskFilter(new BlurMaskFilter(16, BlurMaskFilter.Blur.NORMAL));

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);
    }

    // ================= COLOR CONTROL =================

    public void setLineColor(int color) {

        this.lineColor = color;
        this.fillBaseColor = color;

        linePaint.setColor(color);
        glowPaint.setColor(color);

        updateFillShader();

        invalidate();
    }

    public void setGlowColor(int color) {
        this.glowColor = color;
        glowPaint.setColor(color);
        invalidate();
    }

    public void setThrottleState(boolean throttle, int normalColor, int throttleColor) {

        int color = throttle ? throttleColor : normalColor;

        setLineColor(color);
        setGlowColor(color);
    }

    // ================= GRADIENT FILL =================
    private void updateFillShader() {

        int top = adjustAlpha(fillBaseColor, 75);
        int mid = adjustAlpha(fillBaseColor, 30);
        int bottom = Color.TRANSPARENT;

        fillShader = new LinearGradient(
                0, 0,
                0, getHeight() == 0 ? 1 : getHeight(),
                new int[]{ top, mid, bottom },
                new float[]{ 0f, 0.7f, 1f },
                Shader.TileMode.CLAMP
        );

        fillPaint.setShader(fillShader);
    }

    private int adjustAlpha(int color, int alphaPercent) {
        return Color.argb(
                (int)(255 * (alphaPercent / 100f)),
                Color.red(color),
                Color.green(color),
                Color.blue(color)
        );
    }

    // ================= SIZE =================
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        updateFillShader();
    }

    // ================= BOOT =================
    private void startBoot() {
        postDelayed(() -> bootFlat = false, 1200);
    }

    // ================= DATA =================
    public void addValues(float[] values, boolean throttling) {

        float sum = 0f;
        for (float v : values) sum += v;

        float avg = sum / values.length;

        float last = size > 0
                ? cpuHistory[(index - 1 + 60) % 60]
                : avg;

        avg = (avg * 0.65f) + (last * 0.35f);

        cpuHistory[index] = avg;

        index = (index + 1) % 60;

        if (size < 60) size++;

        requestRender();
    }

    // ================= FRAME LIMIT =================
    private void requestRender() {

        long now = System.currentTimeMillis();

        if (now - lastDrawTime < FRAME_INTERVAL) return;

        lastDrawTime = now;

        invalidate();
    }

    // ================= DRAW =================
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float w = getWidth();
        float h = getHeight();

        canvas.drawRect(0, 0, w, h, bgPaint);

        float stepX = w / 59f;
        float baseY = h;

        linePath.reset();
        fillPath.reset();

        // ================= BOOT =================
        if (bootFlat) {

            float y = baseY - (50f / 100f) * h;

            linePath.moveTo(w, y);
            fillPath.moveTo(w, h);

            for (int i = 1; i < 60; i++) {
                float x = w - (i * stepX);
                linePath.lineTo(x, y);
                fillPath.lineTo(x, h);
            }

            fillPath.lineTo(0, h);
            fillPath.close();

            canvas.drawPath(fillPath, fillPaint);
            canvas.drawPath(linePath, linePaint);
            canvas.drawPath(linePath, glowPaint);

            return;
        }

        // ================= LIVE =================
        if (size < 2) return;

        int i = index;
        boolean first = true;

        for (int c = 0; c < size; c++) {

            float v = clamp(cpuHistory[i]);

            float x = c * stepX;
            float y = baseY - (v / 100f) * h;

            if (first) {
                linePath.moveTo(x, y);
                fillPath.moveTo(x, h);
                first = false;
            } else {
                linePath.lineTo(x, y);
            }

            fillPath.lineTo(x, y);

            i = (i + 1) % 60;
        }

        fillPath.lineTo((size - 1) * stepX, h);
        fillPath.close();

        canvas.drawPath(fillPath, fillPaint);
        canvas.drawPath(linePath, linePaint);
        canvas.drawPath(linePath, glowPaint);
    }

    // ================= UTIL =================
    private float clamp(float v) {
        return Math.max(0f, Math.min(v, 100f));
    }
}