package com.henu.smp.util;

import android.content.res.Resources;
import android.graphics.Point;
import android.view.View;

import org.xutils.x;

/**
 * Created by liyngu on 10/31/15.
 */
public class WidgetUtil {
    public static Point getViewPoint(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        Point p = new Point(location[0], location[1]);
        return p;
    }

    public static int getResourceDimen(int resource) {
        Resources rs = x.app().getResources();
        return rs.getDimensionPixelSize(resource);
    }
}
