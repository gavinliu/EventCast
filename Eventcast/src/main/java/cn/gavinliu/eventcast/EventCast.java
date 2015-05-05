package cn.gavinliu.eventcast;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.gavinliu.eventcast.event.Event;
import cn.gavinliu.eventcast.event.EventAction;
import cn.gavinliu.eventcast.poster.EventPoster;
import cn.gavinliu.eventcast.poster.Poster;
import cn.gavinliu.eventcast.utils.Utils;

/**
 * Created by gavin on 15-3-25.
 */
public class EventCast {

    private final Map<EventAction, CopyOnWriteArrayList<Receptor>> mReceptorMap = new ConcurrentHashMap<EventAction, CopyOnWriteArrayList<Receptor>>();

    ReceiverMethodFinder mReceiverMethodFinder = new ReceiverMethodFinder(mReceptorMap);

    ThreadLocal<Queue<Event>> mEventQueue = new ThreadLocal<Queue<Event>>() {
        @Override
        protected Queue<Event> initialValue() {
            return new ConcurrentLinkedQueue<Event>();
        }
    };

    EventDispatcher mEventDispatcher = new EventDispatcher();

    private static class EventCastLoader {
        private static final EventCast instance = new EventCast();
    }

    public static EventCast getInstance() {
        return EventCastLoader.instance;
    }

    private EventCast() {
    }

    public void register(Object receiver) {
        if (receiver == null) {
            throw new NullPointerException();
        }

        synchronized (this) {
            mReceiverMethodFinder.findReceiverMethod(receiver);
        }
    }

    public void unRegister(Object receiver) {
        if (receiver == null) {
            throw new NullPointerException();
        }

        synchronized (this) {
            mReceiverMethodFinder.removeReceiverMethod(receiver);
        }
    }

    /**
     * 通知所有 tag 的方法,此方法没有参数
     */
    public void post(String tag) {
        EventAction action = new EventAction(null, null, null, tag);
        notify(action, null);
    }

    /**
     * 通知所有 tag 的方法,此方法有参数
     */
    public void post(String tag, Object... data) {
        if (data == null) {
            throw new NullPointerException("events不能为null");
        }
        String types = Utils.makeParameterTypesName(data);
        EventAction action = new EventAction(null, null, types, tag);
        notify(action, data);
    }

    /**
     * 通知 class 的 method 方法,此方法没有参数
     */
    public void post(Class<?> clazz, String method) {
        EventAction action = new EventAction(clazz.getName(), method, null, null);
        notify(action, null);
    }

    /**
     * 通知 class 的 method 方法,此方法有参数
     */
    public void post(Class<?> clazz, String method, Object... data) {
        if (data == null) {
            throw new NullPointerException("events不能为null");
        }
        String types = Utils.makeParameterTypesName(data);
        EventAction action = new EventAction(clazz.getName(), method, types, null);
        notify(action, data);
    }

    private void notify(EventAction action, Object[] datas) {
        Event event = new Event(action, datas);
        mEventQueue.get().offer(event);

        mEventDispatcher.dispatchEvents();
    }

    private class EventDispatcher {

        Poster defaultPoster = new EventPoster();

        void dispatchEvents() {
            Queue<Event> eventQueue = mEventQueue.get();
            while (eventQueue.size() > 0) {
                Event event = eventQueue.poll();

                List<Receptor> receptors = mReceptorMap.get(event.eventAction);
                if (receptors == null) {
                    return;
                }

                for (Receptor receptor : receptors) {
                    defaultPoster.post(receptor, event.data);
                }

            }
        }

    }

}
