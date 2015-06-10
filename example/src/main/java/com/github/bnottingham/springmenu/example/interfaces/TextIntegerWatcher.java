package com.github.bnottingham.springmenu.example.interfaces;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

/**
 * @author Brett Nottingham on 6/09/15
 *         Copyright (c) 2015 Nottingham Software, Inc. All rights reserved.
 */
public abstract class TextIntegerWatcher implements TextWatcher
{
    public abstract void onIntegerChanged(int value);

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
    }

    @Override
    public void afterTextChanged(Editable s)
    {
        String value = s.toString();
        if (TextUtils.isEmpty(value))
        {
            onIntegerChanged(0);
            return;
        }

        try
        {
            onIntegerChanged(Integer.parseInt(value));
        }
        catch (Exception ex)
        {

        }
    }
}
