package com.github.bnottingham.springmenu;

import java.util.List;

import android.content.Context;

import com.github.bnottingham.springmenu.enums.SpringMenuDirection;
import com.github.bnottingham.springmenu.enums.SpringMenuType;
import com.github.bnottingham.springmenu.util.MathUtil;

/**
 * @author Brett Nottingham on 5/10/2015.
 *         Copyright (c) 2015 Nottingham Software, Inc. All rights reserved.
 */
public class SpringMenuConfig
{
    public static final String TAG = SpringMenuConfig.class.getCanonicalName();

    private Context mContext;
    private SpringMenuType mMenuType;
    private SpringMenuDirection mDirection;
    private List<SpringMenuItem> mItems;

    //Used for all types
    private double mTotalSize;
    private double mSize;

    //Used for STRAIGHT menu type
    private int mAngle;

    //Used for FAN_CUSTOM menu type
    private int mAngleStart;
    private int mAngleEnd;

    public SpringMenuConfig(Context context, SpringMenuType type, SpringMenuDirection direction, int size)
    {
        mContext = context;
        mMenuType = type;
        mDirection = direction;
        mSize = size;
        mTotalSize = size;
    }

    public SpringMenuConfig(Context context, SpringMenuType type, int angle, int size)
    {
        mContext = context;
        mMenuType = type;
        mSize = size;
        mTotalSize = size;
        mAngle = angle;
    }

    public SpringMenuConfig(Context context, SpringMenuType type, int angleStart, int angleEnd, int size)
    {
        mContext = context;
        mMenuType = type;
        mSize = size;
        mTotalSize = size;
        mAngleStart = angleStart;
        mAngleEnd = angleEnd;
    }

    public void setType(SpringMenuType type)
    {
        mMenuType = type;
        initializeItems(mItems);
    }

    public void setMenuSize(int size)
    {
        mSize = size;
        mTotalSize = size;
        initializeItems(mItems);
    }

    public void setDirection(SpringMenuDirection direction)
    {
        mDirection = direction;
        initializeItems(mItems);
    }

    public void setMenuAngle(int angle)
    {
        mAngle = angle;
        initializeItems(mItems);
    }

    public void setFanStartAngle(int angle)
    {
        mAngleStart = angle;
        initializeItems(mItems);
    }

    public void setFanEndAngle(int angle)
    {
        mAngleEnd = angle;
        initializeItems(mItems);
    }

    public void initializeItems(final List<SpringMenuItem> items)
    {
        mItems = items;
        switch (mMenuType)
        {
            case FAN:
            case FAN_CUSTOM:
                initFan();
                break;
            case STRAIGHT:
                initStraight();
                break;
            case CURVE:
                initCurve();
                break;
        }

        for (SpringMenuItem item : items)
        {
            item.mTotalSize = mTotalSize;
            item.mType = mMenuType;
            item.mDirection = mDirection;
        }
    }

    private void initFan()
    {
        float deltaAngle = 90f;

        if (mMenuType == SpringMenuType.FAN)
        {
            switch (mDirection)
            {
                case TOP_RIGHT:
                    mAngleStart = 270;
                    break;
                case TOP_LEFT:
                    mAngleStart = 180;
                    break;
                case BOTTOM_LEFT:
                    mAngleStart = 90;
                    break;
                case BOTTOM_RIGHT:
                    mAngleStart = 0;
                    break;
            }
        }
        else if (mMenuType == SpringMenuType.FAN_CUSTOM)
        {
            deltaAngle = mAngleStart - mAngleEnd;
        }

        if (mItems.size() > 1)
        {
            deltaAngle = deltaAngle / (mItems.size() - 1);
        }

        float currentAngle = mAngleStart;
        for (SpringMenuItem item : mItems)
        {
            item.mAngle = (int) currentAngle;
            item.mRadius = mSize;
            currentAngle += deltaAngle;
        }
    }

    private void initStraight()
    {
        double count = 1;
        double size = mItems.size();

        for (SpringMenuItem item : mItems)
        {
            item.mAngle = mAngle;
            item.mRatio = count / size;
            item.mRadius = mSize * item.mRatio;
            count++;
        }
    }

    private void initCurve()
    {
        final double itemCount = mItems.size();
        final double size = mSize / itemCount;
        int position = 1;

        for (SpringMenuItem item : mItems)
        {
            double itemRadius = size * position++;
            item.mQuadFinalX = Math.abs(MathUtil.getCurveXGivenDistance(itemRadius, mTotalSize));
            item.mQuadFinalY = MathUtil.getCurveY(item.mQuadFinalX, mTotalSize);

            // Negative-Y means it will move up
            // Negative-X means it will move left
            switch (mDirection)
            {
                case TOP_LEFT:
                    item.mQuadFinalX = -item.mQuadFinalX;
                    item.mQuadFinalY = -item.mQuadFinalY;
                    break;
                case TOP_RIGHT:
                    item.mQuadFinalY = -item.mQuadFinalY;
                    break;
                case BOTTOM_LEFT:
                    item.mQuadFinalX = -item.mQuadFinalX;
                    break;
                case BOTTOM_RIGHT:
                    break;
            }
        }
    }
}
