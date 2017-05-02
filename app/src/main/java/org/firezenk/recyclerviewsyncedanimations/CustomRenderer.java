package org.firezenk.recyclerviewsyncedanimations;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.pedrogomez.renderers.Renderer;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.subjects.PublishSubject;

class CustomRenderer extends Renderer<Item> {

    @Bind(R.id.city) TextView city;
    @Bind(R.id.hours) TextView hours;
    @Bind(R.id.colon) TextView colon;
    @Bind(R.id.minutes) TextView minutes;

    private final PublishSubject<Long> animationTimer;
    private ObjectAnimator animator;

    CustomRenderer(PublishSubject<Long> animationTimer) {
        this.animationTimer = animationTimer;
    }

    @Override protected void setUpView(View rootView) {}

    @Override protected void hookListeners(View rootView) {}

    @Override protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.custom_renderer, parent, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override public void render() {
        city.setText(getContent().city);
        hours.setText(getContent().hours);
        minutes.setText(getContent().minutes);

        setupAnimation();
        animationTimer.subscribe(timerValue -> startAnimation());
    }

    private void setupAnimation() {
        animator = ObjectAnimator.ofFloat(colon, "alpha", 0f, 1f, 0f);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        startAnimation();
    }

    private void startAnimation() {
        stopAnimation();
        colon.setVisibility(View.VISIBLE);
        animator.start();
    }

    private void stopAnimation() {
        if (animator.isStarted()) {
            animator.end();
        }
        colon.setVisibility(View.INVISIBLE);
    }
}
