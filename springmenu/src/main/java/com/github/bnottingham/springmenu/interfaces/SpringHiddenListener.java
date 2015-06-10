package com.github.bnottingham.springmenu.interfaces;

/**
 * @author Brett Nottingham on 6/8/15
 *         Copyright (c) 2015 Nottingham Software, Inc. All rights reserved.
 */
public interface SpringHiddenListener
{
    public static SpringHiddenListener NO_OP = new SpringHiddenListener()
    {
        @Override
        public void onHide()
        {
        }
    };

    public void onHide();
}
