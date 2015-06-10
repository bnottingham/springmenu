package com.github.bnottingham.springmenu.example.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

import com.github.bnottingham.springmenu.example.R;
import com.github.bnottingham.springmenu.example.fragment.BaseMenuFragment;
import com.github.bnottingham.springmenu.example.fragment.MenuCurveFragment;
import com.github.bnottingham.springmenu.example.fragment.MenuFanCustomFragment;
import com.github.bnottingham.springmenu.example.fragment.MenuFanFragment;
import com.github.bnottingham.springmenu.example.fragment.MenuStraightFragment;

/**
 * @author Brett Nottingham on 5/12/15
 *         Copyright (c) 2015 Nottingham Software, Inc. All rights reserved.
 */
public class MainActivity extends AppCompatActivity
{
    private CustomPagerAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCustomPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
    }

    private class CustomPagerAdapter extends FragmentPagerAdapter
    {
        private final BaseMenuFragment[] PAGES = new BaseMenuFragment[] { new MenuStraightFragment(), new MenuFanFragment(), new MenuFanCustomFragment(), new MenuCurveFragment() };

        public CustomPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            return PAGES[position];
        }

        @Override
        public int getCount()
        {
            return PAGES.length;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return PAGES[position].getFragmentTitle();
        }
    }
}
