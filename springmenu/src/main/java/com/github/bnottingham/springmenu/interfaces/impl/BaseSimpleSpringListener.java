package com.github.bnottingham.springmenu.interfaces.impl;

import android.view.View;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.github.bnottingham.springmenu.interfaces.SpringAnimationListener;

/**
 * @author Brett Nottingham on 6/9/15
 *         Copyright (c) 2015 Nottingham Software, Inc. All rights reserved.
 */
public abstract class BaseSimpleSpringListener extends SimpleSpringListener
{
    public abstract void postSpringUpdate(Spring spring, double value);

    private final View mView;
    private final SpringAnimationListener mListener;

    public BaseSimpleSpringListener(final View view, final SpringAnimationListener listener)
    {
        mView = view;
        mListener = listener;
    }

    @Override
    public void onSpringUpdate(Spring spring)
    {
        super.onSpringUpdate(spring);
        postSpringUpdate(spring, spring.getCurrentValue());
    }

    @Override
    public void onSpringAtRest(Spring spring)
    {
        if (mListener != null)
        {
            mListener.onAnimationCompleted(mView);
        }
    }
}
