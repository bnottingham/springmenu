package com.github.bnottingham.springmenu.anim;

import android.view.View;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.github.bnottingham.springmenu.interfaces.SpringAnimationListener;
import com.github.bnottingham.springmenu.interfaces.impl.BaseSimpleSpringListener;
import com.github.bnottingham.springmenu.util.MathUtil;

/**
 * @author Brett Nottingham on 5/10/2015.
 *         Copyright (c) 2015 Nottingham Software, Inc. All rights reserved.
 */
public class ReboundAnimation
{
    public static Spring startAnimation(final View view, final int startTranslationX, final int startTranslationY, final int finalTranslationX, final int finalTranslationY, final boolean overshoot, final SpringAnimationListener listener)
    {
        // Initialize the spring
        Spring spring = initializeSpring(view, startTranslationX, startTranslationY, overshoot);

        // Add a listener to observe the motion of the spring.
        spring.addListener(new BaseSimpleSpringListener(view, listener)
        {
            @Override
            public void postSpringUpdate(Spring spring, double value)
            {
                //Calculate the newX and newY values based on the current spring value
                double newX = startTranslationX - (startTranslationX - finalTranslationX) * (value);
                double newY = startTranslationY - (startTranslationY - finalTranslationY) * (value);

                view.setTranslationX((int) newX);
                view.setTranslationY((int) newY);
            }
        });

        // Set the spring in motion; moving from 0 to 1
        spring.setEndValue(1);
        return spring;
    }

    public static Spring startCurveAnimation(final View view, final int startTranslationX, final int startTranslationY, final int finalTranslationX, final int finalTranslationY, final double size, final boolean overshoot, final SpringAnimationListener listener)
    {
        // Initialize the spring
        Spring spring = initializeSpring(view, startTranslationX, startTranslationY, overshoot);

        // Add a listener to observe the motion of the spring.
        spring.addListener(new BaseSimpleSpringListener(view, listener)
        {
            @Override
            public void postSpringUpdate(Spring spring, double value)
            {
                //Calculate the newX and newY values based on the current spring value
                double newX = startTranslationX - (startTranslationX - finalTranslationX) * (value);
                double newY = MathUtil.getCurveY(Math.abs(newX), size);
                if (finalTranslationY < 0)
                {
                    newY *= -1;
                }
                else if (finalTranslationY == 0 && view.getTranslationY() < 0)
                {
                    newY *= -1;
                }

                view.setTranslationX((int) newX);
                view.setTranslationY((int) newY);
            }
        });

        // Set the spring in motion; moving from 0 to 1
        spring.setEndValue(1);
        return spring;
    }

    private static Spring initializeSpring(final View view, final int startTranslationX, final int startTranslationY, boolean overshoot)
    {
        view.setTranslationX(startTranslationX);
        view.setTranslationY(startTranslationY);
        view.setVisibility(View.VISIBLE);

        // Create a system to run the physics loop for a set of springs.
        SpringSystem springSystem = SpringSystem.create();

        // Add a spring to the system.
        Spring spring = springSystem.createSpring();
        spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(80, 7));
        spring.setOvershootClampingEnabled(!overshoot);
        return spring;
    }
}
