package cn.gavinliu.eventcast.poster;

import cn.gavinliu.eventcast.Receptor;

/**
 * Created by gavin on 15-5-5.
 */
public class EventPoster implements Poster {

    @Override
    public void post(Receptor receptor, Object[] data) {
        if (receptor == null || receptor.receiver == null) {
            return;
        }
        try {
            if (data == null) {
                receptor.method.invoke(receptor.receiver);
            } else {
                receptor.method.invoke(receptor.receiver, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
