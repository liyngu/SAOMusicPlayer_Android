package com.henu.smp;

/**
 * Created by leen on 10/31/15.
 */
public class Constants {
    public static final String SERVICE_HOST = "http://192.168.1.121:8080/sao-player-api/ws/";
    public static final String MUSIC_HOST = "http://192.168.1.121:8080/sao-player-api/";

    public static final int EMPTY_MENU_ID = 0;
    public static final int EMPTY_INTEGER = 0;
    public static final int EMPTY_OPERATION = 0;
    public static final String EMPTY_STRING = "";
    public static final String CONNECTOR = ",";


    public static final String ACTIVITY_PACKAGE = "com.henu.smp.activity";
    public static final String ALERT_DIALOG_PARAMS = "ALERT_DIALOG_PARAMS";
    public static final String ALERT_DIALOG_TYPE = "ALERT_DIALOG_TYPE";
    public static final String MENU_NAME = "MENU_NAME";
    public static final int ALERT_DIALOG_TYPE_EXIT = 0;
    public static final int ALERT_DIALOG_TYPE_DELETE_ALL = 1;
    public static final int ALERT_DIALOG_TYPE_DELETE_MENU = 2;

    public static final String CLICKED_POINT_X = "CLICKED_POINT_X";
    public static final String CLICKED_POINT_Y = "CLICKED_POINT_Y";

    public static final String SHOW_SONGS_MENU_ID = "SHOW_SONGS_MENU_ID";
    public static final int MENU_OPERATION_ADD = 1;
    public static final int MENU_OPERATION_DEL = 2;
    public static final int MENU_OPERATION_UPPER = 3;
    public static final int MENU_OPERATION_LOWER = 4;
    public static final int COLLECTED_MENU_ID = 1;
    public static final int HISTORY_MENU_ID = 2;

    public static final int[] PLAY_MODE_MAPPING = new int[] {
            R.drawable.music_order_btn, R.drawable.music_single_btn,
            R.drawable.music_cycle_btn, R.drawable.music_random_btn};
    public static final String MUSIC_PLAY_MODE = "MUSIC_PLAY_MODE";
    public static final int MUSIC_MODE_ORDER = 0;
    public static final int MUSIC_MODE_SINGLE = 1;
    public static final int MUSIC_MODE_CIRCLE = 2;
    public static final int MUSIC_MODE_RANDOM = 3;
    public static final int MUSIC_MODE_FIRST = MUSIC_MODE_ORDER;
    public static final int MUSIC_MODE_COUNT = 4;

    public static final int MUSIC_MODE_NEXT = 1;
    public static final String MUSIC_OPERATION = "MUSIC_OPERATION";
    public static final int MUSIC_START = 1;
    public static final int MUSIC_STOP = 2;
    public static final int MUSIC_PAUSE = 3;
    public static final int MUSIC_CONTINUE = 4;
    public static final int MUSIC_COLLECTE = 5;
    public static final int MUSIC_NEXT = 6;
    public static final int MUSIC_PREVIOUS = 7;
    public static final int MUSIC_RESTART = 8;
    public static final String PLAYED_MUSIC_NAME = "PLAYED_MUSIC_NAME";
    public static final String MUSIC_PROGRESS_PERCENT = "MUSIC_PROGRESS_PERCENT";
    public static final String MUSIC_DISPLAY_TIME = "MUSIC_DISPLAY_TIME";

    public static final String ACTION_NAME = "com.henu.smp.ACTION";
    public static final String ACTION_OPERATION = "ACTION_OPERATION";
    public static final int ACTION_PLAYED = 1;
    public static final int ACTION_PAUSED = 2;
    public static final int ACTION_STOPPED = 3;
    public static final int ACTION_MODE_CHANGED = 4;
    public static final int ACTION_PROGRESS_CHANGED = 5;
    public static final int ACTION_MUSIC_CHANGED = 6;
    public static final int ACTION_EXIT = 10;
    public static final int ACTION_CREATE_LIST = 11;
    public static final int ACTION_DELETE_ALL = 12;
    public static final int ACTION_DELETE_MENU = 13;

    public static final String CREATE_LIST_NAME = "CREATE_LIST_NAME";
    public static final String CREATE_LIST_POSITION = "CREATE_LIST_POSITION";
    public static final String CREATE_LIST_TYPE = "CREATE_LIST_TYPE";
    public static final int CREATE_TYPE_MENU_LIST = 0;
    public static final int CREATE_TYPE_MUSIC_LIST = 1;

    public static final String DB_NAME = "DB_SAOMusicPlayer";
    public static final String SP_NAME = "SP_SAOMusicPlayer";
    public static final String SP_ITEM_MENUS = "menus";
}
