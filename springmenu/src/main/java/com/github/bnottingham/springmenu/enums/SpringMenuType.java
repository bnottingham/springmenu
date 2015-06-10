package com.github.bnottingham.springmenu.enums;

/**
 * @author Brett Nottingham on 5/10/2015.
 *         Copyright (c) 2015 Nottingham Software, Inc. All rights reserved.
 */
public enum SpringMenuType
{
    FAN,
    FAN_CUSTOM,
    STRAIGHT,
    CURVE;

    public static SpringMenuType toType(int type)
    {
        return SpringMenuType.values()[type];
    }
}
