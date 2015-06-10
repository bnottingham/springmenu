package com.github.bnottingham.springmenu.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.github.bnottingham.springmenu.R;
import com.github.bnottingham.springmenu.SpringMenuConfig;
import com.github.bnottingham.springmenu.SpringMenuItem;
import com.github.bnottingham.springmenu.enums.SpringMenuDirection;
import com.github.bnottingham.springmenu.enums.SpringMenuType;
import com.github.bnottingham.springmenu.interfaces.SpringAnimationListener;
import com.github.bnottingham.springmenu.interfaces.SpringHiddenListener;

/**
 * @author Brett Nottingham on 5/10/2015.
 *         Copyright (c) 2015 Nottingham Software, Inc. All rights reserved.
 */
public class SpringMenu extends FrameLayout implements View.OnClickListener,
        View.OnLongClickListener
{
    private final static SpringMenuType DEFAULT_MENU_TYPE = SpringMenuType.FAN;
    private final static SpringMenuDirection DEFAULT_MENU_DIRECTION = SpringMenuDirection.TOP_RIGHT;

    private SpringMenuConfig mMenuConfig;
    private SpringAnimationListener mAnimationListener = SpringAnimationListener.NO_OP;
    private SpringHiddenListener mHideListener = SpringHiddenListener.NO_OP;

    private List<SpringMenuItem> mItems = new ArrayList<>();
    private View mMenuButton;
    private View mFadeView;
    private OnClickListener mOnClickListener;
    private OnLongClickListener mOnLongClickListener;

    private boolean mIsAnimating;
    private boolean mIsMenuVisible;
    private int mAnimationCount;

    private SpringMenuType mMenuType;
    private SpringMenuDirection mMenuDirection;
    private int mSpringAngle;
    private int mSpringSize;
    private int mSpringFanAngleStart;
    private int mSpringFanAngleEnd;

    public SpringMenu(final Context context)
    {
        super(context);
    }

    public SpringMenu(final Context context, final AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs);
    }

    public SpringMenu(final Context context, final AttributeSet attrs, final int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(final Context context, final AttributeSet attrs)
    {
        if (isInEditMode())
        {
            return;
        }

        final TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.SpringMenu, 0, 0);

        mMenuType = SpringMenuType.toType(attr.getInt(R.styleable.SpringMenu_spring_menuType, DEFAULT_MENU_TYPE.ordinal()));
        mMenuDirection = SpringMenuDirection.toType(attr.getInt(R.styleable.SpringMenu_spring_expandDirection, DEFAULT_MENU_DIRECTION.ordinal()));
        mSpringAngle = attr.getInt(R.styleable.SpringMenu_spring_menuAngle, 0);
        mSpringSize = attr.getDimensionPixelSize(R.styleable.SpringMenu_spring_menuSize, (int) getContext().getResources().getDimension(R.dimen.spring_menu_width));

        if (mMenuType == SpringMenuType.FAN_CUSTOM)
        {
            mSpringFanAngleStart = attr.getInt(R.styleable.SpringMenu_spring_menuFanStartAngle, 0);
            mSpringFanAngleEnd = attr.getInt(R.styleable.SpringMenu_spring_menuFanEndAngle, 90);
        }
        attr.recycle();
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        if (isInEditMode())
        {
            return;
        }

        final int childCount = getChildCount();
        for (int n = 0; n < childCount; n++)
        {
            View child = getChildAt(n);
            if (child.getId() != R.id.spring_menu_button)
            {
                mItems.add(new SpringMenuItem(child));
            }
        }
        mMenuButton = findViewById(R.id.spring_menu_button);
        if (mMenuButton != null)
        {
            mMenuButton.setOnClickListener(this);
            mMenuButton.setOnLongClickListener(this);
        }
        setChildrenVisibility(false);

        switch (mMenuType)
        {
            case FAN:
                setMenuConfig(new SpringMenuConfig(getContext(), mMenuType, mMenuDirection, mSpringSize));
                break;
            case FAN_CUSTOM:
                setMenuConfig(new SpringMenuConfig(getContext(), mMenuType, mSpringFanAngleStart, mSpringFanAngleEnd, mSpringSize));
                break;
            case STRAIGHT:
                setMenuConfig(new SpringMenuConfig(getContext(), mMenuType, mSpringAngle, mSpringSize));
                break;
            case CURVE:
                setMenuConfig(new SpringMenuConfig(getContext(), mMenuType, mMenuDirection, mSpringSize));
                break;
        }
    }

    public void setMenuConfig(final SpringMenuConfig config)
    {
        mMenuConfig = config;
        mMenuConfig.initializeItems(mItems);
    }

    public void setFadeView(final View view)
    {
        mFadeView = view;
        if (mFadeView != null)
        {
            mFadeView.setClickable(true);
            mFadeView.setOnClickListener(mOnFadeClicked);
        }
    }

    @Override
    public void setOnClickListener(final OnClickListener listener)
    {
        mOnClickListener = listener;
    }

    @Override
    public void setOnLongClickListener(final OnLongClickListener listener)
    {
        mOnLongClickListener = listener;
    }

    public void setAnimationListener(final SpringAnimationListener listener)
    {
        mAnimationListener = listener;
    }

    public void setHideListener(final SpringHiddenListener listener)
    {
        mHideListener = listener;
    }

    public void setMenuButton(final View menuButton)
    {
        mMenuButton = menuButton;
    }

    public void setMenuDirection(SpringMenuDirection direction)
    {
        mMenuConfig.setDirection(direction);
    }

    public void setMenuAngle(int angle)
    {
        mMenuConfig.setMenuAngle(angle);
    }

    public void setMenuSize(int value)
    {
        mMenuConfig.setMenuSize(value);
    }

    public void setFanStartAngle(int angle)
    {
        mMenuConfig.setFanStartAngle(angle);
    }

    public void setFanEndAngle(int angle)
    {
        mMenuConfig.setFanEndAngle(angle);
    }

    public boolean isMenuVisible()
    {
        return mIsMenuVisible;
    }

    public void showMenu()
    {
        if (mIsMenuVisible || mIsAnimating)
        {
            return;
        }

        mIsAnimating = true;
        mIsMenuVisible = true;
        mAnimationCount = 0;
        setChildrenVisibility(true);
        setFadeVisibility(true);
        bringMenuButtonToFront();
        for (SpringMenuItem item : mItems)
        {
            item.show(new SpringAnimationListener()
            {
                @Override
                public void onAnimationCompleted(View view)
                {
                    mAnimationCount++;
                    if (mAnimationCount == mItems.size())
                    {
                        mIsAnimating = false;
                        mAnimationListener.onAnimationCompleted(SpringMenu.this);
                    }
                }
            });
        }

        if (mMenuButton != null)
        {
            mMenuButton.setSelected(true);
        }
    }

    public void hideMenu()
    {
        if (!mIsMenuVisible)
        {
            return;
        }
        else if (mIsAnimating)
        {
            stopAnimation();
        }

        mIsAnimating = true;
        mIsMenuVisible = false;
        mAnimationCount = 0;
        for (SpringMenuItem item : mItems)
        {
            item.hide(new SpringAnimationListener()
            {
                @Override
                public void onAnimationCompleted(final View view)
                {
                    mAnimationCount++;
                    if (mAnimationCount == mItems.size())
                    {
                        mIsAnimating = false;
                        setChildrenVisibility(false);
                        setFadeVisibility(false);
                        mAnimationListener.onAnimationCompleted(SpringMenu.this);
                        mHideListener.onHide();
                    }
                }
            });
        }

        if (mMenuButton != null)
        {
            mMenuButton.setSelected(false);
        }
    }

    private void bringMenuButtonToFront()
    {
        if (mMenuButton != null)
        {
            mMenuButton.bringToFront();
        }
    }

    private void stopAnimation()
    {
        for (SpringMenuItem item : mItems)
        {
            item.stopAnimation();
        }
        mIsAnimating = false;
    }

    private void setFadeVisibility(final boolean isVisible)
    {
        if (mFadeView != null)
        {
            if (isVisible)
            {
                mFadeView.setVisibility(View.VISIBLE);
            }
            else
            {
                mFadeView.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void setChildrenVisibility(final boolean isVisible)
    {
        for (SpringMenuItem item : mItems)
        {
            if (isVisible)
            {
                item.mIcon.setVisibility(View.VISIBLE);
            }
            else
            {
                item.mIcon.setVisibility(View.INVISIBLE);
            }
        }
    }

    public boolean onBackKeyPressed()
    {
        if (mIsMenuVisible)
        {
            hideMenu();
            return true;
        }
        return false;
    }

    public void toggleMenuVisible()
    {
        if (mIsMenuVisible)
        {
            hideMenu();
        }
        else
        {
            showMenu();
        }
    }

    @Override
    public void onClick(final View view)
    {
        if (mOnClickListener != null)
        {
            mOnClickListener.onClick(view);
        }

        if (mItems.isEmpty())
        {
            return;
        }

        toggleMenuVisible();
    }

    @Override
    public boolean onLongClick(final View v)
    {
        if (mOnLongClickListener != null)
        {
            return mOnLongClickListener.onLongClick(v);
        }
        return false;
    }

    private OnClickListener mOnFadeClicked = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            hideMenu();
        }
    };
}
