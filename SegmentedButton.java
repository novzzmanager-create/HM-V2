package com.hypers.hm;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import java.util.List;

public class SegmentedButton extends FrameLayout {

    private LinearLayout container;
    private View indicator;

    private Drawable trackDrawable;
    private Drawable indicatorDrawable;

    private int selectedIndex = 0;
    private List<SegmentItem> items;
    private OnSegmentClickListener listener;

    private static final long ANIM_DURATION = 250;

    public interface OnSegmentClickListener {
        void onSegmentClick(int index, String text);
    }

    public static class SegmentItem {
        String text;
        int icon;

        public SegmentItem(String t) { text = t; }
        public SegmentItem(String t, @DrawableRes int i) {
            text = t; icon = i;
        }
        public String getText() { return text; }
        public int getIconResId() { return icon; }
    }

    public SegmentedButton(@NonNull Context c) {
        super(c);
        init(c);
    }

    public SegmentedButton(@NonNull Context c, @Nullable AttributeSet a) {
        super(c, a);
        init(c);
    }

    private void init(Context c) {

        // ===== TRACK DRAWABLE =====
        trackDrawable = ContextCompat.getDrawable(c, R.drawable.bg);
        setBackground(trackDrawable);

        setClipToOutline(true);
        setPadding(6, 6, 6, 6);

        // ===== INDICATOR DRAWABLE =====
        indicator = new View(c);
        indicatorDrawable = ContextCompat.getDrawable(c, R.drawable.tengah);
        indicator.setBackground(indicatorDrawable);

        addView(indicator, new LayoutParams(0, 0));

        // ===== CONTAINER =====
        container = new LinearLayout(c);
        container.setOrientation(LinearLayout.HORIZONTAL);
        container.setGravity(Gravity.CENTER);

        addView(container, new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        ));
    }

    public void setSegments(List<SegmentItem> data) {
        this.items = data;
        container.removeAllViews();

        for (int i = 0; i < data.size(); i++) {

            int index = i;

            AppCompatTextView tv = new AppCompatTextView(getContext());
            tv.setText(data.get(i).getText());
            tv.setGravity(Gravity.CENTER);
            tv.setSingleLine(true);
            tv.setEllipsize(TextUtils.TruncateAt.END);

            FrameLayout wrapper = new FrameLayout(getContext());

            wrapper.setOnClickListener(v -> select(index, true));

            wrapper.addView(tv, new FrameLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER
            ));

            container.addView(wrapper, new LinearLayout.LayoutParams(
                    0,
                    LayoutParams.MATCH_PARENT,
                    1f
            ));
        }

        post(() -> select(0, false));
    }

    public void select(int index, boolean animate) {

        if (index < 0 || index >= container.getChildCount()) return;

        selectedIndex = index;

        View target = container.getChildAt(index);

        float x = target.getX() + getPaddingLeft();

        int w = target.getWidth();
        int h = getHeight() - getPaddingTop() - getPaddingBottom();

        indicator.getLayoutParams().width = w;
        indicator.getLayoutParams().height = h;
        indicator.setLayoutParams(indicator.getLayoutParams());
        indicator.setY(getPaddingTop());

        if (animate) {
            ObjectAnimator anim = ObjectAnimator.ofFloat(indicator, "x", indicator.getX(), x);
            anim.setDuration(ANIM_DURATION);
            anim.setInterpolator(new DecelerateInterpolator());
            anim.start();
        } else {
            indicator.setX(x);
        }

        if (listener != null && items != null) {
            listener.onSegmentClick(index, items.get(index).getText());
        }
    }

    public void setOnSegmentClickListener(OnSegmentClickListener l) {
        this.listener = l;
    }
}