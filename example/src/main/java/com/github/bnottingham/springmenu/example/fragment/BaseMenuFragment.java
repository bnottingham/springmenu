package com.github.bnottingham.springmenu.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ToggleButton;

import com.github.bnottingham.springmenu.enums.SpringMenuDirection;
import com.github.bnottingham.springmenu.example.R;
import com.github.bnottingham.springmenu.util.ViewUtil;
import com.github.bnottingham.springmenu.widget.SpringMenu;

/**
 * @author Brett Nottingham on 5/12/15
 *         Copyright (c) 2015 Nottingham Software, Inc. All rights reserved.
 */
public abstract class BaseMenuFragment extends Fragment implements
        View.OnClickListener
{
    public abstract String getFragmentTitle();

    private View mViewRoot;
    private ToggleButton mButtonDirectionTopLeft;
    private ToggleButton mButtonDirectionTopRight;
    private ToggleButton mButtonDirectionBottomLeft;
    private ToggleButton mButtonDirectionBottomRight;

    protected SpringMenu mSpringMenu;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mViewRoot = view;
        initUi();
    }

    private void initUi()
    {
        mSpringMenu = (SpringMenu) mViewRoot.findViewById(R.id.spring_menu);
        mSpringMenu.setFadeView(mViewRoot.findViewById(R.id.fade));

        mButtonDirectionBottomLeft = (ToggleButton) mViewRoot.findViewById(R.id.btn_direction_bottom_left);
        mButtonDirectionBottomRight = (ToggleButton) mViewRoot.findViewById(R.id.btn_direction_bottom_right);
        mButtonDirectionTopLeft = (ToggleButton) mViewRoot.findViewById(R.id.btn_direction_top_left);
        mButtonDirectionTopRight = (ToggleButton) mViewRoot.findViewById(R.id.btn_direction_top_right);

        ViewUtil.setViewClickListener(mButtonDirectionBottomLeft, this);
        ViewUtil.setViewClickListener(mButtonDirectionBottomRight, this);
        ViewUtil.setViewClickListener(mButtonDirectionTopLeft, this);
        ViewUtil.setViewClickListener(mButtonDirectionTopRight, this);
    }

    @Override
    public void onClick(View view)
    {
        setToggleButtonOff();
        switch (view.getId())
        {
            case R.id.btn_direction_bottom_left:
                ((ToggleButton) view).setChecked(true);
                mSpringMenu.setMenuDirection(SpringMenuDirection.BOTTOM_LEFT);
                break;
            case R.id.btn_direction_bottom_right:
                ((ToggleButton) view).setChecked(true);
                mSpringMenu.setMenuDirection(SpringMenuDirection.BOTTOM_RIGHT);
                break;
            case R.id.btn_direction_top_left:
                ((ToggleButton) view).setChecked(true);
                mSpringMenu.setMenuDirection(SpringMenuDirection.TOP_LEFT);
                break;
            case R.id.btn_direction_top_right:
                ((ToggleButton) view).setChecked(true);
                mSpringMenu.setMenuDirection(SpringMenuDirection.TOP_RIGHT);
                break;
        }
    }

    private void setToggleButtonOff()
    {
        ViewUtil.setToggleChecked(mButtonDirectionBottomLeft, false);
        ViewUtil.setToggleChecked(mButtonDirectionBottomRight, false);
        ViewUtil.setToggleChecked(mButtonDirectionTopLeft, false);
        ViewUtil.setToggleChecked(mButtonDirectionTopRight, false);
    }
}
