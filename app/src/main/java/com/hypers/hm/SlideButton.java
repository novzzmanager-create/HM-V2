package com.hypers.hm;

import android.content.Context;
import android.util.AttributeSet;
import android.graphics.*;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.animation.ValueAnimator;

public class SlideButton extends FrameLayout {

    private Paint progressPaint;
    private Paint textPaint;
    private ImageView thumbButton;
    private Paint outerStrokePaint;
    private Paint glowPaint;

    private float downX;
    private boolean isSliding = false;
    private float thumbPosX = 0;
    private String label = "SLIDE TO START";
    
    private float cornerRadius;
    private OnSlideCompleteListener listener;

    private final int THUMB_WIDTH_DP = 64; 
    private final int THUMB_HEIGHT_DP = 56;
    private final int MARGIN = (int) dpToPx(4);

    // Glow Variables
    private ValueAnimator glowAnimator;
    private float glowAlpha = 0f;
    private boolean glowActive = false;

    public SlideButton(Context context) { super(context); init(context); }
    public SlideButton(Context context, AttributeSet attrs) { super(context, attrs); init(context); }

    private void init(Context ctx) {
        setWillNotDraw(false);
        cornerRadius = dpToPx(15);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setColor(Color.parseColor("#5000ACC1"));
        progressPaint.setStyle(Paint.Style.FILL);

        outerStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outerStrokePaint.setStyle(Paint.Style.STROKE);
        outerStrokePaint.setStrokeWidth(dpToPx(1));
        outerStrokePaint.setColor(Color.parseColor("#60BDBDBD"));

        // Setup Glow Paint
        glowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        glowPaint.setStyle(Paint.Style.STROKE);
        glowPaint.setStrokeWidth(dpToPx(4)); // Tebal glow luar
        glowPaint.setColor(Color.parseColor("#FF00ACC1")); // Warna glow biru toska

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.LTGRAY);
        textPaint.setTextSize(spToPx(14));

        thumbButton = new ImageView(ctx);
        thumbButton.setImageResource(R.drawable.icon_double_arrow_hm);
        thumbButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
        thumbButton.setPadding((int)dpToPx(12), (int)dpToPx(12), (int)dpToPx(12), (int)dpToPx(12));
        thumbButton.setBackgroundResource(R.drawable.thumb_bg);

        LayoutParams params = new LayoutParams((int)dpToPx(THUMB_WIDTH_DP), (int)dpToPx(THUMB_HEIGHT_DP));
        params.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
        params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
        addView(thumbButton, params);

        thumbButton.setOnTouchListener((v, event) -> {
            float maxX = getWidth() - thumbButton.getWidth() - (MARGIN * 2);
            
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getRawX() - thumbButton.getTranslationX();
                    isSliding = true;
                    return true;

                case MotionEvent.ACTION_MOVE:
                    if (isSliding) {
                        float moveX = event.getRawX();
                        float newX = Math.max(0, Math.min(moveX - downX, maxX));
                        thumbButton.setTranslationX(newX);
                        thumbPosX = newX;
                        
                        if (newX >= (maxX - 5) && !glowActive) {
                            startGlow();
                        } else if (newX < (maxX - 5) && glowActive) {
                            stopGlow();
                        }
                        
                        invalidate();
                    }
                    return true;

                case MotionEvent.ACTION_UP:
                    if (isSliding) {
                        if (thumbButton.getTranslationX() >= (maxX - 10)) {
                            if (listener != null) listener.onSlideComplete();
                        } else {
                            reset();
                        }
                        isSliding = false;
                    }
                    return true;
            }
            return false;
        });
    }
    
    private void startGlow() {
        glowActive = true;
        if (glowAnimator != null) glowAnimator.cancel();
        
        glowAnimator = ValueAnimator.ofFloat(0.3f, 1.0f);
        glowAnimator.setDuration(500);
        glowAnimator.setRepeatMode(ValueAnimator.REVERSE);
        glowAnimator.setRepeatCount(ValueAnimator.INFINITE);
        glowAnimator.addUpdateListener(animation -> {
            glowAlpha = (float) animation.getAnimatedValue();
            invalidate();
        });
        glowAnimator.start();
    }

    private void stopGlow() {
        glowActive = false;
        if (glowAnimator != null) {
            glowAnimator.cancel();
            glowAnimator = null;
        }
        glowAlpha = 0f;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float w = getWidth();
        float h = getHeight();
        float radius = Math.min(cornerRadius, h / 2f);
        float stroke = outerStrokePaint.getStrokeWidth();

        // 1. Gambar GLOW (Di bawah border)
        if (glowActive) {
            glowPaint.setAlpha((int) (255 * glowAlpha));
            RectF glowRect = new RectF(stroke, stroke, w - stroke, h - stroke);
            canvas.drawRoundRect(glowRect, radius, radius, glowPaint);
        }

        // 2. Gambar Border
        RectF outerRect = new RectF(stroke/2, stroke/2, w - stroke/2, h - stroke/2);
        canvas.drawRoundRect(outerRect, radius, radius, outerStrokePaint);

        // 3. Gambar Progress Fill
        canvas.save();
        Path clipPath = new Path();
        clipPath.addRoundRect(new RectF(0, 0, w, h), radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);

        if (thumbPosX > 0) {
            float maxX = w - thumbButton.getWidth() - (MARGIN * 2);
            float progress = (maxX <= 0) ? 0 : thumbPosX / maxX;
            float right = (progress >= 0.98f) ? w : MARGIN + thumbButton.getWidth() + thumbPosX;
            canvas.drawRect(0, 0, right, h, progressPaint);
        }
        canvas.restore();

        // 4. Teks Fade
        float maxX = w - thumbButton.getWidth() - (MARGIN * 2);
        float progress = (maxX <= 0) ? 0 : Math.min(1f, thumbPosX / maxX);
        textPaint.setAlpha((int) ((1f - progress) * 255));
        
        float textWidth = textPaint.measureText(label);
        float x = (w - textWidth) / 2f;
        float y = h / 2f - (textPaint.descent() + textPaint.ascent()) / 2;
        canvas.drawText(label, x, y, textPaint);

        super.onDraw(canvas);
    }

    public void reset() {
        stopGlow();
        ValueAnimator anim = ValueAnimator.ofFloat(thumbButton.getTranslationX(), 0);
        anim.setDuration(250);
        anim.addUpdateListener(a -> {
            float val = (float) a.getAnimatedValue();
            thumbButton.setTranslationX(val);
            thumbPosX = val;
            invalidate();
        });
        anim.start();
    }

    public void setLabel(String t) { this.label = t; invalidate(); }
    public void setOnSlideCompleteListener(OnSlideCompleteListener l) { this.listener = l; }
    private float dpToPx(float dp) { return dp * getResources().getDisplayMetrics().density; }
    private float spToPx(float sp) { return sp * getResources().getDisplayMetrics().scaledDensity; }
    public interface OnSlideCompleteListener { void onSlideComplete(); }
}
