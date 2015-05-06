package cn.gavinliu.android.lib.eventcast.event;


/**
 * Created by gavin on 15-5-5.
 */
public class Event {

    public EventAction eventAction;
    public Object[] data;

    public Event(EventAction eventAction, Object[] data) {
        this.data = data;
        this.eventAction = eventAction;
    }

}
