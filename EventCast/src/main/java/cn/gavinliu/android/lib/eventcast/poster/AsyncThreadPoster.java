package cn.gavinliu.android.lib.eventcast.poster;

import android.os.HandlerThread;
import android.os.Handler;

import cn.gavinliu.android.lib.eventcast.Receptor;

/**
 * Created by gavin on 15-5-5.
 */
public class AsyncThreadPoster implements Poster {

    DispatcherThread mDispatcherThread;
    EventPoster mEventPoster = new EventPoster();

    public AsyncThreadPoster() {
        mDispatcherThread = new DispatcherThread(AsyncThreadPoster.class.getSimpleName());
        mDispatcherThread.start();
    }

    @Override
    public void post(final Receptor receptor, final Object[] data) {
        mDispatcherThread.post(new Runnable() {

            @Override
            public void run() {
                mEventPoster.post(receptor, data);
            }
        });
    }

    class DispatcherThread extends HandlerThread {

        protected Handler mAsyncHandler;

        public DispatcherThread(String name) {
            super(name);
        }

        public void post(Runnable runnable) {
            if (mAsyncHandler == null) {
                throw new NullPointerException("mAsyncHandler == null, please call start() first.");
            }

            mAsyncHandler.post(runnable);
        }

        @Override
        public synchronized void start() {
            super.start();
            mAsyncHandler = new Handler(this.getLooper());
        }

    }
}
