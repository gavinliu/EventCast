package cn.gavinliu.android.lib.eventcast.poster;

import cn.gavinliu.android.lib.eventcast.Receptor;

/**
 * Created by gavin on 15-4-30.
 */
public interface Poster {

    void post(Receptor receptor, Object[] data);

}
