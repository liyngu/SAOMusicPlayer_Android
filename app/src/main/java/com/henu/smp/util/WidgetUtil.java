package com.henu.smp.util;

import android.graphics.Point;
import android.view.View;

/**
 * Created by leen on 10/31/15.
 */
public class WidgetUtil {
    public static Point getViewPoint(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        Point p = new Point(location[0], location[1]);
        return p;
    }
}
