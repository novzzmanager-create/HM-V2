package com.hypers.hm;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

public class LoadingBooster {
    
    private static Dialog dialog;
    private static KztView kztView;

    public static LoadingBooster show(Context context) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.setCancelable(false);

        int defaultSize = (int) (200 * context.getResources().getDisplayMetrics().density);
        kztView = new KztView(context);

        LinearLayout layout = new LinearLayout(context);
        layout.setGravity(android.view.Gravity.CENTER);
        layout.addView(kztView, new LinearLayout.LayoutParams(defaultSize, defaultSize));

        dialog.setContentView(layout);
        dialog.show();

        return new LoadingBooster();
    }

    public LoadingBooster size(int dp) {
        float density = kztView.getContext().getResources().getDisplayMetrics().density;
        int px = (int) (dp * density);
        kztView.setLayoutParams(new LinearLayout.LayoutParams(px, px));
        return this;
    }

    public LoadingBooster color(int color) {
        kztView.setMainColor(color);
        return this;
    }

    public static void hide() {
        if (kztView != null) {
            kztView.stop(); // 🔥 stop animasi
        }

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    private static class KztView extends View {

        private Paint paint;
        private RectF rectF1, rectF2, rectF3;
        private float angle1 = 0, angle2 = 0, angle3 = 0;
        private float pulseScale = 0f;
        private boolean growing = true;
        private boolean running = true;
        private Path boltPath;
        private int mainColor = Color.parseColor("#FF6F00");

        public KztView(Context context) {
            super(context);
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            rectF1 = new RectF();
            rectF2 = new RectF();
            rectF3 = new RectF();
            boltPath = new Path();
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        public void setMainColor(int color) {
            this.mainColor = color;
            invalidate();
        }

        public void stop() {
            running = false;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            if (!running) return; // 🔥 stop loop

            float w = getWidth();
            float h = getHeight();
            float cx = w / 2f;
            float cy = h / 2f;

            float r1 = w * 0.38f;
            float r2 = w * 0.27f;
            float r3 = w * 0.16f;

            float st1 = w * 0.015f;
            float st2 = w * 0.05f;
            float st3 = w * 0.012f;

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(mainColor);
            paint.setAlpha((int) (pulseScale * 60));
            canvas.drawCircle(cx, cy, r3 * pulseScale, paint);
            paint.setAlpha(255);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setColor(mainColor);
            paint.setShadowLayer(10f, 0, 0, mainColor);

            paint.setStrokeWidth(st1);
            rectF1.set(cx - r1, cy - r1, cx + r1, cy + r1);
            canvas.drawArc(rectF1, angle1, 100, false, paint);

            paint.setStrokeWidth(st2);
            rectF2.set(cx - r2, cy - r2, cx + r2, cy + r2);
            canvas.drawArc(rectF2, angle2, 130, false, paint);

            paint.setStrokeWidth(st3);
            rectF3.set(cx - r3, cy - r3, cx + r3, cy + r3);
            canvas.drawArc(rectF3, angle3, 170, false, paint);

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(mainColor);

            float bS = r3 * 0.52f;
            boltPath.reset();
            boltPath.moveTo(cx + bS * 0.2f, cy - bS);
            boltPath.lineTo(cx - bS * 0.7f, cy + bS * 0.1f);
            boltPath.lineTo(cx - bS * 0.1f, cy + bS * 0.1f);
            boltPath.lineTo(cx - bS * 0.2f, cy + bS);
            boltPath.lineTo(cx + bS * 0.7f, cy - bS * 0.1f);
            boltPath.lineTo(cx + bS * 0.1f, cy - bS * 0.1f);
            boltPath.close();
            canvas.drawPath(boltPath, paint);

            paint.clearShadowLayer();

            angle1 += 11f;
            angle2 += 7f;
            angle3 -= 4f;

            if (growing) {
                pulseScale += 0.018f;
                if (pulseScale >= 1f) growing = false;
            } else {
                pulseScale -= 0.015f;
                if (pulseScale <= 0f) growing = true;
            }

            postInvalidateOnAnimation(); // 🔥 infinite loop
        }
    }
}