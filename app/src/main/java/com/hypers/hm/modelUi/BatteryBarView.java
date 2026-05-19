package com.hypers.hm.modelUi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class BatteryBarView extends View {

    private Paint batteryBar;
    private Path path;

    private float batteryLevel = 100f;

    public BatteryBarView(Context context) {
        super(context);
        init();
    }

    public BatteryBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BatteryBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        batteryBar = new Paint(Paint.ANTI_ALIAS_FLAG);
        batteryBar.setStyle(Paint.Style.FILL);

        path = new Path();
    }

    public void setBatteryLevel(float level) {
        this.batteryLevel = level;

        if (this.batteryLevel < 0)
            this.batteryLevel = 0;

        if (this.batteryLevel > 100)
            this.batteryLevel = 100;

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = getWidth() / 2f;
        float topY = 20f;

        float barWidth = 25f;
        float barGap = 12f;
        float barHeight = 55f;

        int totalBars = 8;

        float totalWidth =
                (totalBars * barWidth) +
                ((totalBars - 1) * barGap);

        float startX = centerX - (totalWidth / 2f);

        int activeBars =
                (int) Math.ceil(batteryLevel / 12.5f);

        for (int i = 0; i < totalBars; i++) {

            float x =
                    startX +
                    (i * (barWidth + barGap));

            path.reset();

            path.moveTo(x + 6f, topY);
            path.lineTo(x + barWidth + 6f, topY);
            path.lineTo(x + barWidth - 6f, topY + barHeight);
            path.lineTo(x - 6f, topY + barHeight);
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
    }
}