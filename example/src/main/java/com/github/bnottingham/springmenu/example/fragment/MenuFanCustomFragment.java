package com.github.bnottingham.springmenu.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.github.bnottingham.springmenu.example.R;
import com.github.bnottingham.springmenu.example.interfaces.TextIntegerWatcher;

/**
 * @author Brett Nottingham on 6/09/15
 *         Copyright (c) 2015 Nottingham Software, Inc. All rights reserved.
 */
public class MenuFanCustomFragment extends BaseMenuFragment
{
    @Override
    public String getFragmentTitle()
    {
        return "Fan Custom";
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_menu_fan_custom, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        intiUi(view);
    }

    private void intiUi(View view)
    {
        EditText textStartAngle = (EditText) view.findViewById(R.id.angleStart);
        EditText textEndAngle = (EditText) view.findViewById(R.id.angleEnd);

        textStartAngle.addTextChangedListener(new TextIntegerWatcher()
        {
            @Override
            public void onIntegerChanged(int value)
            {
                mSpringMenu.setFanStartAngle(value);
            }
        });
        textEndAngle.addTextChangedListener(new TextIntegerWatcher()
        {
            @Override
            public void onIntegerChanged(int value)
            {
                mSpringMenu.setFanEndAngle(value);
            }
        });

        textStartAngle.setText("0");
        textEndAngle.setText("90");
    }
}
