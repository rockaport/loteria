package com.asesolutions.mobile.loteria.utils;

import android.view.View;

public class AnimUtil {
    /**
     * Sets a view to a hidden state by setting alpha and scale params to 0f
     *
     * @param view The view to hide
     */
    private static void setViewHidden(View view) {
        view.setAlpha(0f);
        view.setScaleX(0f);
        view.setScaleY(0f);

        view.setVisibility(View.VISIBLE);
    }

    /**
     * Sets a view to a visible state by setting alpha and scale params to 1f
     *
     * @param view The view show
     */
    private static void setViewVisible(View view) {
        view.setAlpha(1f);
        view.setScaleX(1f);
        view.setScaleY(1f);

        view.setVisibility(View.VISIBLE);
    }

    /**
     * Animates a view to a hidden state using transparency and scale parameters
     *
     * @param view The view to animate
     */
    public static void animateOut(final View view) {
        view.animate().cancel();
        view.animate().alpha(0f).scaleX(0f).scaleY(0f)
                .withStartAction(() -> setViewVisible(view))
                .withEndAction(() -> setViewHidden(view));
    }

    /**
     * Animates a view to a visible state using transparency and scale parameters
     *
     * @param view The view to animate
     */
    public static void animateIn(final View view) {
        view.animate().cancel();
        view.animate().alpha(1f).scaleX(1f).scaleY(1f)
                .withStartAction(() -> setViewHidden(view));
    }
}
