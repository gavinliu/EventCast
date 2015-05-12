package cn.gavinliu.android.lib.eventcast.logger;

import android.util.Log;

/**
 * Created by gavin on 15-5-7.
 */
public final class L {

    public static boolean isDEBUG = true;
    private static final String TAG = "EventCast";

    public static void d(String msg) {
        if (isDEBUG) {
            Log.d(TAG, msg);
        }
    }

}
