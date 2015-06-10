# Versatile Spring Menu built on the Facebook rebound library

[![Build Status](https://travis-ci.org/bnottingham/springmenu.svg?branch=master)](https://travis-ci.org/bnottingham/springmenu) 
[![Maven Central](https://img.shields.io/maven-central/v/com.github.bnottingham/springmenu.svg)](http://search.maven.org/#search|gav|1|g%3A%22com.github.bnottingham%22%20AND%20a%3A%22springmenu%22)

## Description

This Library contains an implementation of the [**Facebook Rebound Library**](http://facebook.github.io/rebound/) for Android.

The Facebook Rebound library is a special library for creating realistic spring effects for widgets.  Utilizing this, the SpringMenu creates various types of icon based menus, similar to what is seen currently in applications like Evernote.

## Requirements

The Library requires **Android SDK version 14 (ICS)** and higher.

## Gradle Dependency

```java
dependencies {
    compile 'com.github.bnottingham:springmenu:0.3.0'
}
```

##Sample

<a href="https://play.google.com/store/apps/details?id=com.github.bnottingham.springmenu.example">
  <img alt="Get it on Google Play"
       src="https://developer.android.com/images/brand/en_generic_rgb_wo_60.png" />
</a>

## Usage

### Creation

#### Declaration inside XML resource

**SpringMenu** needs to be declared in the XML resource.  Below would create a SpringMenu with a main menu button and one hidden button that will "spring" out when the menu button is clicked.  The spring_menu_button can be set programatically later, but the child buttons must be declared as children of the widget.SpringMenu.

```xml
    <com.github.bnottingham.springmenu.widget.SpringMenu
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/spring_menu"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:spring_menuType="straight"
        app:spring_menuAngle="@integer/angle_up"
        app:spring_menuSize="200dp">

        <ImageView
            android:id="@+id/menu_button_1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/icn_1" />
            
        <ImageButton
            android:id="@id/spring_menu_button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/abc_ic_menu_moreoverflow_mtrl_alpha" />
    </com.github.bnottingham.springmenu.widget.SpringMenu>
```

#### XML Attribute Spring Menu Types

```xml
        <attr name="spring_menuType" format="enum">
            <enum name="fan" value="0" />
            <enum name="fan_custom" value="1" />
            <enum name="straight" value="2" />
            <enum name="curve" value="3" />
        </attr>
```

#### XML Attribute Spring Menu Expand Direction

```xml
        <attr name="spring_expandDirection" format="enum">
            <enum name="top_right" value="0" />
            <enum name="top_left" value="1" />
            <enum name="bottom_left" value="2" />
            <enum name="bottom_right" value="3" />
        </attr>
```

#### XML Attribute Spring Menu Config Options
```xml
        <attr name="spring_menuSize" format="dimension" />
        <attr name="spring_menuAngle" format="dimension" />
        <attr name="spring_menuFanStartAngle" format="integer" />
        <attr name="spring_menuFanEndAngle" format="integer" />
```

## Menu Attributes

### spring_menuSize
This is used by all of the menu types to declare the distance to the origin of the furthest menu button

### spring_menuAngle
This is used by the straight menu type to declare the angle from the origin to spring out the menu

### spring_menuFanStartAngle, spring_menuFanEndAngle
This is used by the fan_custom menu type to declare the start and end points for the fan

## Menu Types

### Fan
Each button springs out in a circular pattern, which each being the same distance away from the origin, you can use expandDirection to declare which quadrant the fan occurs.  Uses a 90 degree arch, first item in the menu is displayed at angle = x, last menu item is displayed at angle = x + 90.

### Fan Custom
Same as fan, but instead of a 90 degree arch, you can specify the start and end angles for the fan, use  spring_menuFanStartAngle and spring_menuFanEndAngle.  Expand direction is ignored for this type.

![Fan](https://github.com/bnottingham/springmenu/blob/master/screen_shot_2.png)

### Straight
Extends in a straight line out from the origin, uses spring_menuAngle to set the direction of the spring.
![Straight](https://github.com/bnottingham/springmenu/blob/master/screen_shot_1.png)

### Curve
Extends the menu out from the origin on a curved plane (using the equation x^(1/2)
![Curve](https://github.com/bnottingham/springmenu/blob/master/screen_shot_3.png)

## License

```
  Copyright 2015 Nottingham Software Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
```

