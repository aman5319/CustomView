package com.amidezcod.customanimations.scalingLayout.scalingLayoutLibrary;


import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Avoid using enum it's is ravenous for memory
 * https://android.jlelse.eu/android-performance-avoid-using-enum-on-android-326be0794dc3
 */
public class States {
    public static final int EXPANDED = 1;
    public static final int PROGRESSING = 3;
    public static final int COLLAPSED = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({EXPANDED, COLLAPSED, PROGRESSING})
    @interface StateAnnotation {
    }

    private int state;

    public int getState() {
        return state;
    }

    public void setState(@StateAnnotation int state) {
        this.state = state;
    }
}
