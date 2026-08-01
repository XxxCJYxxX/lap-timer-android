package com.xxxcjyxxx.laptimer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.animation.doOnEnd;
import androidx.core.splashscreen.SplashScreen;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.splash_logo);
        TextView title = findViewById(R.id.splash_title);
        TextView subtitle = findViewById(R.id.splash_subtitle);
        View ring = findViewById(R.id.splash_ring);

        // Initial states
        logo.setAlpha(0f);
        logo.setScaleX(0.6f);
        logo.setScaleY(0.6f);
        title.setAlpha(0f);
        title.setTranslationY(24f);
        subtitle.setAlpha(0f);
        subtitle.setTranslationY(16f);
        ring.setScaleX(0f);
        ring.setScaleY(0f);
        ring.setAlpha(0.6f);

        // Ring pulse animation (continuous)
        ValueAnimator ringPulse = ValueAnimator.ofFloat(0.6f, 1.4f);
        ringPulse.setDuration(1200);
        ringPulse.setRepeatCount(ValueAnimator.INFINITE);
        ringPulse.setRepeatMode(ValueAnimator.RESTART);
        ringPulse.addUpdateListener(anim -> {
            float v = (float) anim.getAnimatedValue();
            ring.setScaleX(v);
            ring.setScaleY(v);
            ring.setAlpha(0.6f * (1f - (v - 0.6f) / 0.8f));
        });
        ringPulse.start();

        // Staggered entrance sequence
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Logo spring in
            logo.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(700)
                .setInterpolator(new AnticipateInterpolator(1.2f))
                .start();
        }, 200);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            title.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(500)
                .setInterpolator(new AnticipateInterpolator(0.8f))
                .start();
        }, 600);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            subtitle.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(500)
                .setInterpolator(new AnticipateInterpolator(0.8f))
                .start();
        }, 800);

        // Exit to MainActivity
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            ringPulse.cancel();

            // Zoom out + fade
            logo.animate()
                .scaleX(40f)
                .scaleY(40f)
                .alpha(0f)
                .setDuration(500)
                .setInterpolator(new AnticipateInterpolator(0.5f))
                .withEndAction(() -> {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                })
                .start();

            title.animate().alpha(0f).setDuration(300).start();
            subtitle.animate().alpha(0f).setDuration(300).start();

        }, 2500);
    }
}
