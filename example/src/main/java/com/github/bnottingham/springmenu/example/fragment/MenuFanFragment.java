package com.github.bnottingham.springmenu.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.bnottingham.springmenu.example.R;

/**
 * @author Brett Nottingham on 5/12/15
 *         Copyright (c) 2015 Nottingham Software, Inc. All rights reserved.
 */
public class MenuFanFragment extends BaseMenuFragment
{
    @Override
    public String getFragmentTitle()
    {
        return "Fan";
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_menu_fan, container, false);
    }
}
