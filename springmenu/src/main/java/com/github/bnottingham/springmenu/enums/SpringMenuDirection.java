package com.github.bnottingham.springmenu.enums;

/**
 * @author Brett Nottingham on 5/10/2015.
 *         Copyright (c) 2015 Nottingham Software, Inc. All rights reserved.
 */
public enum SpringMenuDirection
{
    //For fan and curve menus
    TOP_RIGHT,
    TOP_LEFT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT;

    public static SpringMenuDirection toType(int type)
    {
        return SpringMenuDirection.values()[type];
    }
}
