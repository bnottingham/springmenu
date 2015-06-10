package com.github.bnottingham.springmenu.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.github.bnottingham.springmenu.interfaces.impl.BaseAnimatorListener;

/**
 * @author Brett Nottingham on 5/10/2015.
 *         Copyright (c) 2015 Nottingham Software, Inc. All rights reserved.
 */
public class TranslateAnimation
{
    public static void startTranslationAnimator(final View view, final float targetX, final float targetY, int duration, BaseAnimatorListener listener)
    {
        final ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "translationX", view.getTranslationX(), targetX);
        final ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), targetY);

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.setInterpolator(new LinearInterpolator());
        animationSet.setDuration(duration);
        animationSet.playTogether(animatorX, animatorY);

        if (listener != null)
        {
            animationSet.addListener(listener);
        }
        animationSet.start();
    }
}
