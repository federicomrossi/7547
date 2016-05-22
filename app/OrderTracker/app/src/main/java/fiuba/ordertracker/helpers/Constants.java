package fiuba.ordertracker.helpers;

import android.graphics.Color;

/**
 * Created by ezequiel on 02/04/16.
 */
public final class Constants {
    public static final String UNIT = "K";
    public static final String COMPLETE_UNIT = "Km";
    //public static final String URL_SERVER = "http://ec2-52-33-59-48.us-west-2.compute.amazonaws.com";
    public static final String URL_SERVER = "http://192.168.0.105";
    public static final Double FIUBA_LATITUDE = 34.6175246;
    public static final Double FIUBA_LONGITUDE = -58.3705057;
    public static final Double OBELISCO_LATITUDE = -34.603738900;
    public static final Double OBELISCO_LONGITUDE = -58.381570400;
    public static final Integer COLOR_TEXT_FILTER = Color.BLACK;
    public static final Integer COLOR_HINT_FILTER = Color.BLACK;

    public static final String COLOR_INDICATOR_DEFAULT = "#CCCCCC";
    public static final String COLOR_INDICATOR_VISITED = "#BBFF78";
    public static final String COLOR_INDICATOR_VISITED_OUT_OF_TIME = "#FF6F6F";
    public static final String COLOR_INDICATOR_NOT_VISITED = "#FFFF6B";

    // Orders possible states constants
    public static final String ACTIVE_STATE = "1";
    public static final String CONFIRM_STATE = "2";
    public static final String BUILDING_STATE = "3";
    public static final String SHIPPING_STATE = "4";
    public static final String COMPLETED_STATE = "5";
    public static final String CANCELED_STATE = "6";
}
