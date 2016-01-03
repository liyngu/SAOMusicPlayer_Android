package com.henu.smp;

/**
 * Created by leen on 10/31/15.
 */
public class Constants {
    public static final int EMPTY_MENU_ID = 0;
    public static final int EMPTY_INTEGER = 0;
    public static final int EMPTY_OPERATION = 0;
    public static final String EMPTY_STRING = "";
    public static final String CONNECTOR = ",";

    public static final String ACTIVITY_PACKAGE = "com.henu.smp.activity";
    public static final String ALERT_DIALOG_PARAMS = "ALERT_DIALOG_PARAMS";
    public static final String ALERT_DIALOG_TYPE = "ALERT_DIALOG_TYPE";
    public static final int ALERT_DIALOG_TYPE_EXIT = 1;

    public static final String CLICKED_POINT_X = "CLICKED_POINT_X";
    public static final String CLICKED_POINT_Y = "CLICKED_POINT_Y";

    public static final int MENU_OPERATION_ADD = 1;
    public static final int MENU_OPERATION_DEL = 2;
    public static final int MENU_OPERATION_UPPER = 3;
    public static final int MENU_OPERATION_LOWER = 4;

    public static final int MUSIC_MODE_ORDER = 1;
    public static final int MUSIC_MODE_SINGLE = 2;
    public static final int MUSIC_MODE_CIRCLE = 3;
    public static final int MUSIC_MODE_RANDOM = 4;
    public static final int MUSIC_MODE_FIRST = MUSIC_MODE_ORDER;
    public static final int MUSIC_MODE_END = MUSIC_MODE_RANDOM;
    public static final int MUSIC_MODE_NEXT = 1;

    public static final String MUSIC_OPERATION = "MUSIC_OPERATION";
    public static final int MUSIC_START = 1;
    public static final int MUSIC_STOP = 2;
    public static final int MUSIC_PAUSE = 3;
    public static final int MUSIC_CHANGE = 4;
    public static final int MUSIC_COLLECTE = 5;

    public static final String ACTION_NAME = "com.henu.smp.ACTION";
    public static final String ACTION_OPERATION = "ACTION_OPERATION";
    public static final int ACTION_PLAYED = 1;
    public static final int ACTION_PAUSED = 2;
    public static final int ACTION_EXIT = 3;
}
