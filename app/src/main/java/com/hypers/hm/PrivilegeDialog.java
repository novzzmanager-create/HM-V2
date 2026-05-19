package com.hypers.hm;

// =====================================================================
//  PrivilegeDialog.java — Dialog Kelola Privilege Hypers
//  UI kustom modern, tanpa library tambahan (pure Android Views)
//
//  Cara pakai:
//    PrivilegeDialog.show(this, hypersReady, permissionGranted,
//        (action) -> {
//            if (action == PrivilegeDialog.ACTION_REVOKE) {
//                // cabut izin, binder tetap hidup
//            } else if (action == PrivilegeDialog.ACTION_EXIT) {
//                // disconnect total
//            } else if (action == PrivilegeDialog.ACTION_RESTORE) {
//                // restore privilege
//            }
//        });
// =====================================================================

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;

public class PrivilegeDialog {

    public static final int ACTION_REVOKE  = 1;
    public static final int ACTION_EXIT    = 2;
    public static final int ACTION_RESTORE = 3;

    public interface OnActionListener {
        void onAction(int action);
    }

    // ── dp → px ──────────────────────────────────────────────────────
    private static int dp(Context ctx, float dp) {
        return Math.round(dp * ctx.getResources().getDisplayMetrics().density);
    }

    // ── sp → px ──────────────────────────────────────────────────────
    private static int sp(Context ctx, float sp) {
        return Math.round(sp * ctx.getResources().getDisplayMetrics().scaledDensity);
    }

    // ================================================================
    //  SHOW
    // ================================================================

    public static void show(Context ctx, boolean hypersReady,
                            boolean privilegeGranted, OnActionListener listener) {

        Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);

        // dim background
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().setDimAmount(0.75f);
        }

        // root
        FrameLayout root = new FrameLayout(ctx);
        root.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        root.setPadding(dp(ctx, 20), dp(ctx, 20), dp(ctx, 20), dp(ctx, 20));
        root.setClickable(true);
        root.setOnClickListener(v -> dialog.dismiss());

        // card
        LinearLayout card = buildCard(ctx, dialog, hypersReady,
                privilegeGranted, listener);
        card.setClickable(true);
        card.setOnClickListener(v -> {}); // consume click
        FrameLayout.LayoutParams cardLp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        cardLp.gravity = Gravity.CENTER;
        card.setLayoutParams(cardLp);

        root.addView(card);
        dialog.setContentView(root);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }

        dialog.show();

        // enter animation
        card.setAlpha(0f);
        card.setScaleX(0.88f);
        card.setScaleY(0.88f);
        card.setTranslationY(dp(ctx, 24));

        AnimatorSet anim = new AnimatorSet();
        anim.playTogether(
                ObjectAnimator.ofFloat(card, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(card, "scaleX", 0.88f, 1f),
                ObjectAnimator.ofFloat(card, "scaleY", 0.88f, 1f),
                ObjectAnimator.ofFloat(card, "translationY", dp(ctx, 24), 0f)
        );
        anim.setDuration(380);
        anim.setInterpolator(new OvershootInterpolator(0.8f));
        anim.start();
    }

    // ================================================================
    //  BUILD CARD
    // ================================================================

    private static LinearLayout buildCard(Context ctx, Dialog dialog,
                                          boolean hypersReady, boolean privilegeGranted,
                                          OnActionListener listener) {
        LinearLayout card = new LinearLayout(ctx);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(ctx, 22), dp(ctx, 24), dp(ctx, 22), dp(ctx, 22));

        // background: dark card
        GradientDrawable bg = new GradientDrawable();
        bg.setShape(GradientDrawable.RECTANGLE);
        bg.setCornerRadius(dp(ctx, 22));
        bg.setColor(Color.parseColor("#0D1424"));
        bg.setStroke(dp(ctx, 1), Color.parseColor("#1E293B"));
        card.setBackground(bg);

        // elevation shadow
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            card.setElevation(dp(ctx, 32));
        }

        // ── Header ─────────────────────────────────────────────────
        card.addView(buildHeader(ctx, privilegeGranted));

        // ── Divider ─────────────────────────────────────────────────
        card.addView(buildDivider(ctx));

        // ── 2 Option Cards ──────────────────────────────────────────
        LinearLayout row = new LinearLayout(ctx);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setWeightSum(2f);

        LinearLayout.LayoutParams cardLp = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        cardLp.setMargins(0, 0, dp(ctx, 6), 0);

        LinearLayout.LayoutParams cardLp2 = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        cardLp2.setMargins(dp(ctx, 6), 0, 0, 0);

        View revokeCard = buildOptionCard(ctx,
                "🔒", "Cabut Izin",
                "SOFT REVOKE",
                "Binder tetap\nhidup. Bisa\nrestore kapan\nsaja.",
                "#FBBF24", "#78350F",
                () -> {
                    dialog.dismiss();
                    listener.onAction(ACTION_REVOKE);
                });
        revokeCard.setLayoutParams(cardLp);

        View exitCard = buildOptionCard(ctx,
                "⚡", "Disconnect",
                "HARD STOP",
                "Service exit.\nSemua sesi\nterputus. Perlu\npairing ulang.",
                "#EF4444", "#7F1D1D",
                () -> {
                    dialog.dismiss();
                    listener.onAction(ACTION_EXIT);
                });
        exitCard.setLayoutParams(cardLp2);

        row.addView(revokeCard);
        row.addView(exitCard);

        LinearLayout.LayoutParams rowLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rowLp.setMargins(0, dp(ctx, 16), 0, 0);
        row.setLayoutParams(rowLp);
        card.addView(row);

        // ── Restore button (jika belum granted) ─────────────────────
        if (!privilegeGranted) {
            card.addView(buildRestoreButton(ctx, dialog, listener));
        }

        // ── Footer tip ───────────────────────────────────────────────
        card.addView(buildFooter(ctx));

        return card;
    }

    // ================================================================
    //  HEADER
    // ================================================================

    private static View buildHeader(Context ctx, boolean privilegeGranted) {
        LinearLayout header = new LinearLayout(ctx);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);

        // left: title block
        LinearLayout titleBlock = new LinearLayout(ctx);
        titleBlock.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams tbLp = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        titleBlock.setLayoutParams(tbLp);

        // eyebrow
        TextView eyebrow = new TextView(ctx);
        eyebrow.setText("HYPERS MANAGER");
        eyebrow.setTextSize(9f);
        eyebrow.setLetterSpacing(0.14f);
        eyebrow.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
        eyebrow.setTextColor(Color.parseColor("#92733A"));
        titleBlock.addView(eyebrow);

        // title
        TextView title = new TextView(ctx);
        title.setText("Kelola Privilege");
        title.setTextSize(19f);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextColor(Color.parseColor("#F1F5F9"));
        title.setLetterSpacing(-0.02f);
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        titleLp.setMargins(0, dp(ctx, 3), 0, 0);
        title.setLayoutParams(titleLp);
        titleBlock.addView(title);

        header.addView(titleBlock);

        // right: status badge
        header.addView(buildStatusBadge(ctx, privilegeGranted));

        return header;
    }

    // ================================================================
    //  STATUS BADGE
    // ================================================================

    private static View buildStatusBadge(Context ctx, boolean granted) {
        TextView badge = new TextView(ctx);
        badge.setText(granted ? "● ACTIVE" : "● REVOKED");
        badge.setTextSize(9.5f);
        badge.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        badge.setLetterSpacing(0.06f);
        badge.setTextColor(Color.parseColor(granted ? "#4ADE80" : "#F87171"));
        badge.setPadding(dp(ctx, 10), dp(ctx, 5), dp(ctx, 10), dp(ctx, 5));

        GradientDrawable d = new GradientDrawable();
        d.setShape(GradientDrawable.RECTANGLE);
        d.setCornerRadius(dp(ctx, 99));
        d.setColor(Color.parseColor(granted ? "#0F2F1A" : "#2F0F0F"));
        d.setStroke(dp(ctx, 1), Color.parseColor(granted ? "#16532D" : "#7F1D1D"));
        badge.setBackground(d);

        // pulse animation for REVOKED
        if (!granted) {
            ValueAnimator pulse = ValueAnimator.ofFloat(0.6f, 1f);
            pulse.setDuration(900);
            pulse.setRepeatCount(ValueAnimator.INFINITE);
            pulse.setRepeatMode(ValueAnimator.REVERSE);
            pulse.addUpdateListener(a ->
                    badge.setAlpha((Float) a.getAnimatedValue()));
            pulse.start();
        }

        return badge;
    }

    // ================================================================
    //  OPTION CARD
    // ================================================================

    private static View buildOptionCard(Context ctx, String emoji, String label,
                                        String sublabel, String desc,
                                        String accentHex, String darkHex,
                                        Runnable onClick) {
        LinearLayout card = new LinearLayout(ctx);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(ctx, 14), dp(ctx, 14), dp(ctx, 14), dp(ctx, 14));

        GradientDrawable bg = new GradientDrawable();
        bg.setShape(GradientDrawable.RECTANGLE);
        bg.setCornerRadius(dp(ctx, 14));
        bg.setColor(Color.parseColor("#111827"));
        bg.setStroke(dp(ctx, 1), Color.parseColor("#1F2937"));
        card.setBackground(bg);

        // icon circle
        FrameLayout iconCircle = new FrameLayout(ctx);
        int iconSize = dp(ctx, 42);
        FrameLayout.LayoutParams icLp = new FrameLayout.LayoutParams(iconSize, iconSize);
        iconCircle.setLayoutParams(icLp);

        GradientDrawable icBg = new GradientDrawable();
        icBg.setShape(GradientDrawable.RECTANGLE);
        icBg.setCornerRadius(dp(ctx, 10));
        icBg.setColor(Color.parseColor(darkHex + "55")); // semi transparent
        icBg.setStroke(dp(ctx, 1), Color.parseColor(accentHex + "55"));
        iconCircle.setBackground(icBg);

        TextView emojiTv = new TextView(ctx);
        emojiTv.setText(emoji);
        emojiTv.setTextSize(18f);
        emojiTv.setGravity(Gravity.CENTER);
        FrameLayout.LayoutParams eLp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        emojiTv.setLayoutParams(eLp);
        iconCircle.addView(emojiTv);

        card.addView(iconCircle);

        // label
        TextView labelTv = new TextView(ctx);
        labelTv.setText(label);
        labelTv.setTextSize(14f);
        labelTv.setTypeface(Typeface.DEFAULT_BOLD);
        labelTv.setTextColor(Color.parseColor(accentHex));
        labelTv.setLetterSpacing(-0.01f);
        LinearLayout.LayoutParams llLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        llLp.setMargins(0, dp(ctx, 10), 0, dp(ctx, 2));
        labelTv.setLayoutParams(llLp);
        card.addView(labelTv);

        // sublabel
        TextView subTv = new TextView(ctx);
        subTv.setText(sublabel);
        subTv.setTextSize(8.5f);
        subTv.setTypeface(Typeface.MONOSPACE);
        subTv.setLetterSpacing(0.08f);
        subTv.setTextColor(Color.parseColor(accentHex + "99"));
        card.addView(subTv);

        // desc
        TextView descTv = new TextView(ctx);
        descTv.setText(desc);
        descTv.setTextSize(10.5f);
        descTv.setTypeface(Typeface.MONOSPACE);
        descTv.setTextColor(Color.parseColor("#64748B"));
        descTv.setLineSpacing(dp(ctx, 2), 1f);
        LinearLayout.LayoutParams dLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dLp.setMargins(0, dp(ctx, 6), 0, 0);
        descTv.setLayoutParams(dLp);
        card.addView(descTv);

        // touch feedback
        card.setOnClickListener(v -> {
            // press animation
            AnimatorSet pressAnim = new AnimatorSet();
            pressAnim.playTogether(
                    ObjectAnimator.ofFloat(card, "scaleX", 1f, 0.95f),
                    ObjectAnimator.ofFloat(card, "scaleY", 1f, 0.95f)
            );
            pressAnim.setDuration(100);
            pressAnim.addListener(new AnimatorListenerAdapter() {
                @Override public void onAnimationEnd(Animator animation) {
                    AnimatorSet releaseAnim = new AnimatorSet();
                    releaseAnim.playTogether(
                            ObjectAnimator.ofFloat(card, "scaleX", 0.95f, 1f),
                            ObjectAnimator.ofFloat(card, "scaleY", 0.95f, 1f)
                    );
                    releaseAnim.setDuration(180);
                    releaseAnim.setInterpolator(new OvershootInterpolator(2f));
                    releaseAnim.addListener(new AnimatorListenerAdapter() {
                        @Override public void onAnimationEnd(Animator a) {
                            showConfirmSheet(ctx, emoji, label, desc,
                                    accentHex, darkHex, onClick);
                        }
                    });
                    releaseAnim.start();
                }
            });
            pressAnim.start();
        });

        // hover highlight via state change
        card.setOnHoverListener((v, event) -> {
            bg.setColor(Color.parseColor(
                    event.getAction() == android.view.MotionEvent.ACTION_HOVER_ENTER
                            ? "#1A2433" : "#111827"));
            bg.setStroke(dp(ctx, 1), Color.parseColor(
                    event.getAction() == android.view.MotionEvent.ACTION_HOVER_ENTER
                            ? accentHex + "66" : "#1F2937"));
            return false;
        });

        return card;
    }

    // ================================================================
    //  CONFIRM BOTTOM SHEET (inline in same dialog)
    // ================================================================

    private static void showConfirmSheet(Context ctx, String emoji, String label,
                                         String desc, String accentHex, String darkHex,
                                         Runnable onConfirm) {
        Dialog sheet = new Dialog(ctx);
        sheet.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sheet.setCancelable(true);
        if (sheet.getWindow() != null) {
            sheet.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            sheet.getWindow().setDimAmount(0.6f);
            // bottom sheet gravity
            sheet.getWindow().setGravity(Gravity.BOTTOM);
            sheet.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        LinearLayout root = new LinearLayout(ctx);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(ctx, 20), dp(ctx, 24), dp(ctx, 20), dp(ctx, 32));
        root.setGravity(Gravity.CENTER_HORIZONTAL);

        GradientDrawable sheetBg = new GradientDrawable();
        sheetBg.setShape(GradientDrawable.RECTANGLE);
        float[] radii = new float[]{
                dp(ctx, 20), dp(ctx, 20), dp(ctx, 20), dp(ctx, 20), 0, 0, 0, 0};
        sheetBg.setCornerRadii(radii);
        sheetBg.setColor(Color.parseColor("#0D1424"));
        sheetBg.setStroke(dp(ctx, 1), Color.parseColor("#1E293B"));
        root.setBackground(sheetBg);

        // handle bar
        View handle = new View(ctx);
        LinearLayout.LayoutParams hLp = new LinearLayout.LayoutParams(
                dp(ctx, 36), dp(ctx, 4));
        hLp.gravity = Gravity.CENTER_HORIZONTAL;
        hLp.setMargins(0, 0, 0, dp(ctx, 20));
        handle.setLayoutParams(hLp);
        GradientDrawable hBg = new GradientDrawable();
        hBg.setShape(GradientDrawable.RECTANGLE);
        hBg.setCornerRadius(dp(ctx, 99));
        hBg.setColor(Color.parseColor("#334155"));
        handle.setBackground(hBg);
        root.addView(handle);

        // icon
        TextView iconTv = new TextView(ctx);
        iconTv.setText(emoji);
        iconTv.setTextSize(36f);
        iconTv.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams iLp = new LinearLayout.LayoutParams(
                dp(ctx, 64), dp(ctx, 64));
        iLp.gravity = Gravity.CENTER_HORIZONTAL;
        iLp.setMargins(0, 0, 0, dp(ctx, 14));
        iconTv.setLayoutParams(iLp);

        GradientDrawable iconBg = new GradientDrawable();
        iconBg.setShape(GradientDrawable.OVAL);
        iconBg.setColor(Color.parseColor(darkHex + "66"));
        iconBg.setStroke(dp(ctx, 2), Color.parseColor(accentHex + "55"));
        iconTv.setBackground(iconBg);
        root.addView(iconTv);

        // confirm title
        TextView confirmTitle = new TextView(ctx);
        confirmTitle.setText("Konfirmasi " + label + "?");
        confirmTitle.setTextSize(17f);
        confirmTitle.setTypeface(Typeface.DEFAULT_BOLD);
        confirmTitle.setTextColor(Color.parseColor("#F1F5F9"));
        confirmTitle.setGravity(Gravity.CENTER);
        root.addView(confirmTitle);

        // confirm desc
        TextView confirmDesc = new TextView(ctx);
        confirmDesc.setText(desc.replace("\n", " "));
        confirmDesc.setTextSize(11.5f);
        confirmDesc.setTypeface(Typeface.MONOSPACE);
        confirmDesc.setTextColor(Color.parseColor("#94A3B8"));
        confirmDesc.setGravity(Gravity.CENTER);
        confirmDesc.setLineSpacing(dp(ctx, 3), 1f);
        LinearLayout.LayoutParams cdLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        cdLp.setMargins(0, dp(ctx, 8), 0, dp(ctx, 24));
        confirmDesc.setLayoutParams(cdLp);
        root.addView(confirmDesc);

        // button row
        LinearLayout btnRow = new LinearLayout(ctx);
        btnRow.setOrientation(LinearLayout.HORIZONTAL);
        btnRow.setWeightSum(2f);
        btnRow.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        // cancel
        TextView cancelBtn = new TextView(ctx);
        cancelBtn.setText("Batal");
        cancelBtn.setGravity(Gravity.CENTER);
        cancelBtn.setTextSize(13.5f);
        cancelBtn.setTypeface(Typeface.DEFAULT_BOLD);
        cancelBtn.setTextColor(Color.parseColor("#94A3B8"));
        cancelBtn.setPadding(0, dp(ctx, 13), 0, dp(ctx, 13));
        LinearLayout.LayoutParams cancelLp = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        cancelLp.setMargins(0, 0, dp(ctx, 6), 0);
        cancelBtn.setLayoutParams(cancelLp);
        GradientDrawable cancelBg = new GradientDrawable();
        cancelBg.setShape(GradientDrawable.RECTANGLE);
        cancelBg.setCornerRadius(dp(ctx, 12));
        cancelBg.setColor(Color.parseColor("#1E293B"));
        cancelBg.setStroke(dp(ctx, 1), Color.parseColor("#334155"));
        cancelBtn.setBackground(cancelBg);
        cancelBtn.setOnClickListener(v -> {
            animateDismiss(root, sheet);
        });

        // confirm
        TextView confirmBtn = new TextView(ctx);
        confirmBtn.setText("Ya, Lanjutkan");
        confirmBtn.setGravity(Gravity.CENTER);
        confirmBtn.setTextSize(13.5f);
        confirmBtn.setTypeface(Typeface.DEFAULT_BOLD);
        confirmBtn.setTextColor(Color.parseColor(accentHex));
        confirmBtn.setPadding(0, dp(ctx, 13), 0, dp(ctx, 13));
        LinearLayout.LayoutParams confirmLp = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        confirmLp.setMargins(dp(ctx, 6), 0, 0, 0);
        confirmBtn.setLayoutParams(confirmLp);
        GradientDrawable confirmBg = new GradientDrawable();
        confirmBg.setShape(GradientDrawable.RECTANGLE);
        confirmBg.setCornerRadius(dp(ctx, 12));
        confirmBg.setColor(Color.parseColor(darkHex + "55"));
        confirmBg.setStroke(dp(ctx, 1), Color.parseColor(accentHex + "88"));
        confirmBtn.setBackground(confirmBg);
        confirmBtn.setOnClickListener(v -> {
            sheet.dismiss();
            onConfirm.run();
        });

        btnRow.addView(cancelBtn);
        btnRow.addView(confirmBtn);
        root.addView(btnRow);

        sheet.setContentView(root);
        sheet.show();

        // slide up animation
        root.setTranslationY(dp(ctx, 200));
        root.animate()
                .translationY(0)
                .setDuration(320)
                .setInterpolator(new DecelerateInterpolator(2f))
                .start();
    }

    private static void animateDismiss(View root, Dialog dialog) {
        root.animate()
                .translationY(dp(root.getContext(), 200))
                .alpha(0f)
                .setDuration(220)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(dialog::dismiss)
                .start();
    }

    // ================================================================
    //  RESTORE BUTTON
    // ================================================================

    private static View buildRestoreButton(Context ctx, Dialog dialog,
                                           OnActionListener listener) {
        TextView btn = new TextView(ctx);
        btn.setText("↺  Restore Privilege");
        btn.setGravity(Gravity.CENTER);
        btn.setTextSize(13f);
        btn.setTypeface(Typeface.DEFAULT_BOLD);
        btn.setTextColor(Color.parseColor("#4ADE80"));
        btn.setPadding(0, dp(ctx, 13), 0, dp(ctx, 13));

        GradientDrawable bg = new GradientDrawable();
        bg.setShape(GradientDrawable.RECTANGLE);
        bg.setCornerRadius(dp(ctx, 12));
        bg.setColor(Color.parseColor("#0F2F1A"));
        bg.setStroke(dp(ctx, 1), Color.parseColor("#166534"));
        btn.setBackground(bg);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, dp(ctx, 12), 0, 0);
        btn.setLayoutParams(lp);

        btn.setOnClickListener(v -> {
            dialog.dismiss();
            listener.onAction(ACTION_RESTORE);
        });

        // slide in animation
        btn.setAlpha(0f);
        btn.setTranslationY(dp(ctx, 10));
        new Handler(Looper.getMainLooper()).postDelayed(() ->
                btn.animate().alpha(1f).translationY(0f)
                        .setDuration(280)
                        .setInterpolator(new DecelerateInterpolator())
                        .start(), 120);

        return btn;
    }

    // ================================================================
    //  DIVIDER
    // ================================================================

    private static View buildDivider(Context ctx) {
        View div = new View(ctx);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(ctx, 1));
        lp.setMargins(0, dp(ctx, 16), 0, 0);
        div.setLayoutParams(lp);
        div.setBackgroundColor(Color.parseColor("#1E293B"));
        return div;
    }

    // ================================================================
    //  FOOTER TIP
    // ================================================================

    private static View buildFooter(Context ctx) {
        LinearLayout footer = new LinearLayout(ctx);
        footer.setOrientation(LinearLayout.HORIZONTAL);
        footer.setGravity(Gravity.CENTER_VERTICAL);
        footer.setPadding(dp(ctx, 12), dp(ctx, 10), dp(ctx, 12), dp(ctx, 10));

        GradientDrawable fBg = new GradientDrawable();
        fBg.setShape(GradientDrawable.RECTANGLE);
        fBg.setCornerRadius(dp(ctx, 10));
        fBg.setColor(Color.parseColor("#0A1020"));
        fBg.setStroke(dp(ctx, 1), Color.parseColor("#1E293B"));
        footer.setBackground(fBg);

        LinearLayout.LayoutParams fLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        fLp.setMargins(0, dp(ctx, 14), 0, 0);
        footer.setLayoutParams(fLp);

        TextView icon = new TextView(ctx);
        icon.setText("💡");
        icon.setTextSize(13f);
        LinearLayout.LayoutParams iLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        iLp.setMargins(0, 0, dp(ctx, 8), 0);
        icon.setLayoutParams(iLp);
        footer.addView(icon);

        TextView tip = new TextView(ctx);
        tip.setText("Setelah Binder aktif, WiFi bisa dimatikan.\nKomunikasi via Android IPC kernel — bukan TCP.");
        tip.setTextSize(10f);
        tip.setTypeface(Typeface.MONOSPACE);
        tip.setTextColor(Color.parseColor("#475569"));
        tip.setLineSpacing(dp(ctx, 2), 1f);
        footer.addView(tip);

        return footer;
    }
}
