package cn.gavinliu.eventcast.poster;

import android.os.Handler;
import android.os.Looper;

import cn.gavinliu.eventcast.Receptor;

/**
 * Created by gavin on 15-5-5.
 */
public class MainThreadPoster implements Poster {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    Poster poster = new EventPoster();

    @Override
    public void post(final Receptor receptor, final Object[] data) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                poster.post(receptor, data);
            }
        });
    }

}
