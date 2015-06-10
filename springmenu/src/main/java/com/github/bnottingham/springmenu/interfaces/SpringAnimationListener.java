package com.github.bnottingham.springmenu.interfaces;

import android.view.View;

/**
 * @author Brett Nottingham on 5/10/2015.
 *         Copyright (c) 2015 Nottingham Software, Inc. All rights reserved.
 */
public interface SpringAnimationListener
{
    public static SpringAnimationListener NO_OP = new SpringAnimationListener()
    {
        @Override
        public void onAnimationCompleted(View view)
        {

        }
    };

    public void onAnimationCompleted(View view);
}
