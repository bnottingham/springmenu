package com.github.bnottingham.springmenu.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ToggleButton;

/**
 * @author Brett Nottingham on 5/10/2015.
 *         Copyright (c) 2015 Nottingham Software, Inc. All rights reserved.
 */
public class ViewUtil
{
    private static double sDpi = -1;

    public static DisplayMetrics getDisplayMetrics(final Context context)
    {
        return context.getResources().getDisplayMetrics();
    }

    public static double getDpi(final Context context)
    {
        if (sDpi == -1)
        {
            DisplayMetrics dm = getDisplayMetrics(context);
            sDpi = dm.density;
        }
        return sDpi;
    }

    public static int dpToPx(final Context context, final int dp)
    {
        return (int) (dp * getDpi(context));
    }

    public static void setViewClickListener(final View view, final View.OnClickListener listener)
    {
        if (view == null || listener == null)
        {
            return;
        }

        view.setOnClickListener(listener);
    }

    public static void setToggleChecked(final ToggleButton view, boolean isChecked)
    {
        if (view == null)
        {
            return;
        }
        view.setChecked(isChecked);
    }
}
