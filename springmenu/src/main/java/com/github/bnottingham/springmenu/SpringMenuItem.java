package com.github.bnottingham.springmenu;

import android.animation.Animator;
import android.view.View;

import com.facebook.rebound.Spring;
import com.github.bnottingham.springmenu.anim.ReboundAnimation;
import com.github.bnottingham.springmenu.anim.TranslateAnimation;
import com.github.bnottingham.springmenu.enums.SpringMenuDirection;
import com.github.bnottingham.springmenu.enums.SpringMenuType;
import com.github.bnottingham.springmenu.interfaces.SpringAnimationListener;
import com.github.bnottingham.springmenu.interfaces.impl.BaseAnimatorListener;
import com.github.bnottingham.springmenu.util.MathUtil;

/**
 * @author Brett Nottingham on 5/10/2015.
 *         Copyright (c) 2015 Nottingham Software, Inc. All rights reserved.
 */
public class SpringMenuItem
{
    public static final int HIDE_ANIMATION_DURATION = 150;

    public View mIcon;
    public Spring mCurrentSpring;
    public SpringMenuType mType;
    public SpringMenuDirection mDirection;

    public double mAngle;
    public double mTotalSize;
    public double mRadius;
    public double mRatio;
    public double mQuadFinalX;
    public double mQuadFinalY;

    public SpringMenuItem(View icon)
    {
        mIcon = icon;
    }

    public void stopAnimation()
    {
        if (mCurrentSpring != null && !mCurrentSpring.isAtRest())
        {
            mCurrentSpring.removeAllListeners();
            mCurrentSpring.setAtRest();
            mCurrentSpring.destroy();
            mCurrentSpring = null;
        }
    }

    public void show(final SpringAnimationListener listener)
    {
        switch (mType)
        {
            case FAN:
            case FAN_CUSTOM:
                showFanType(listener);
                break;
            case STRAIGHT:
                showStraightType(listener);
                break;
            case CURVE:
                showCurveType(listener);
                break;
        }
    }

    public void hide(final SpringAnimationListener listener)
    {
        switch (mType)
        {
            case CURVE:
                hideCurveType(listener);
                break;
            default:
                TranslateAnimation.startTranslationAnimator(mIcon, 0, 0, HIDE_ANIMATION_DURATION, new BaseAnimatorListener()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        listener.onAnimationCompleted(mIcon);
                    }
                });
                break;
        }
    }

    private void showFanType(final SpringAnimationListener listener)
    {
        int finalX = (int) (MathUtil.getX(mAngle) * mRadius);
        int finalY = (int) (MathUtil.getY(mAngle) * mRadius);

        mCurrentSpring = ReboundAnimation.startAnimation(mIcon, 0, 0, finalX, finalY, true, listener);
    }

    private void showStraightType(final SpringAnimationListener listener)
    {
        int finalX = (int) (MathUtil.getX(mAngle) * mRadius);
        int finalY = (int) (MathUtil.getY(mAngle) * mRadius);
        mCurrentSpring = ReboundAnimation.startAnimation(mIcon, 0, 0, finalX, finalY, true, listener);
    }

    private void hideCurveType(final SpringAnimationListener listener)
    {
        mCurrentSpring = ReboundAnimation.startCurveAnimation(mIcon, (int) mIcon.getTranslationX(), (int) mIcon.getTranslationY(), 0, 0, mTotalSize, false, listener);
    }

    private void showCurveType(final SpringAnimationListener listener)
    {
        mCurrentSpring = ReboundAnimation.startCurveAnimation(mIcon, 0, 0, (int) mQuadFinalX, (int) mQuadFinalY, mTotalSize, true, listener);
    }
}
