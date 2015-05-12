package cn.gavinliu.android.lib.eventcast;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.gavinliu.android.lib.eventcast.event.Event;
import cn.gavinliu.android.lib.eventcast.event.EventAction;
import cn.gavinliu.android.lib.eventcast.logger.L;
import cn.gavinliu.android.lib.eventcast.match.WidelyMatchPolicy;
import cn.gavinliu.android.lib.eventcast.poster.AsyncThreadPoster;
import cn.gavinliu.android.lib.eventcast.poster.EventPoster;
import cn.gavinliu.android.lib.eventcast.poster.MainThreadPoster;
import cn.gavinliu.android.lib.eventcast.poster.Poster;
import cn.gavinliu.android.lib.eventcast.utils.Utils;

/**
 * Created by gavin on 15-3-25.
 */
public class EventCast {

    private final Map<EventAction, CopyOnWriteArrayList<Receptor>> mReceptorMap;
    private final ThreadLocal<Queue<Event>> mEventQueue;
    private ReceiverMethodSpider mReceiverMethodSpider;
    private EventDispatcher mEventDispatcher;

    private static class EventCastLoader {
        private static final EventCast instance = new EventCast();
    }

    public static EventCast getInstance() {
        return EventCastLoader.instance;
    }

    private EventCast() {
        setDebug(true);
        mReceptorMap = new ConcurrentHashMap<EventAction, CopyOnWriteArrayList<Receptor>>();
        mEventQueue = new ThreadLocal<Queue<Event>>() {

            @Override
            protected Queue<Event> initialValue() {
                return new ConcurrentLinkedQueue<Event>();
            }

        };
        mReceiverMethodSpider = new ReceiverMethodSpider(mReceptorMap);
        mEventDispatcher = new EventDispatcher();
    }

    public void setDebug(boolean isDebug) {
        L.isDEBUG = isDebug;
    }

    public void register(Object receiver) {
        if (receiver == null) {
            throw new NullPointerException();
        }

        synchronized (this) {
            mReceiverMethodSpider.findReceiverMethod(receiver);
        }
    }

    public void unregister(Object receiver) {
        if (receiver == null) {
            throw new NullPointerException();
        }

        synchronized (this) {
            mReceiverMethodSpider.removeReceiverMethod(receiver);
        }
    }

    public void post(String tag, Object... data) {
        String types;

        if (data == null) {
            types = null;
        } else {
            types = Utils.makeParameterTypesName(data);
        }
        EventAction action = new EventAction(null, null, types, tag);
        notify(action, data);
    }

    public void post(Class<?> clazz, String method, Object... data) {
        String types;

        if (data == null) {
            types = null;
        } else {
            types = Utils.makeParameterTypesName(data);
        }
        EventAction action = new EventAction(clazz.getName(), method, types, null);
        notify(action, data);
    }

    private void notify(EventAction action, Object[] data) {
        Event event = new Event(action, data);
        mEventQueue.get().offer(event);

        mEventDispatcher.dispatchEvents();
    }

    private class EventDispatcher {

        Poster mainPoster = new MainThreadPoster();
        Poster postPoster = new EventPoster();
        Poster asyncPoster = new AsyncThreadPoster();

        Map<EventAction, List<EventAction>> cache = new ConcurrentHashMap<EventAction, List<EventAction>>();

        WidelyMatchPolicy matchPolicy = new WidelyMatchPolicy();

        void dispatchEvents() {
            Queue<Event> eventQueue = mEventQueue.get();
            while (eventQueue.size() > 0) {
                Event event = eventQueue.poll();

                if (event.data != null && event.data.length == 1) {

                    L.d("DispatchEvents: By Widely");
                    List<EventAction> actions;
                    if (cache.containsKey(event.eventAction)) {
                        actions = cache.get(event.eventAction);
                    } else {
                        actions = matchPolicy.findMatchEventActions(event.eventAction, event.data[0]);
                        cache.put(event.eventAction, actions);
                    }

                    for (EventAction action : actions) {
                        dispatch(action, event.data);
                    }

                } else {
                    L.d("DispatchEvents");

                    dispatch(event.eventAction, event.data);
                }

            }
        }

        void dispatch(EventAction eventAction, Object[] data) {
            List<Receptor> receptors = mReceptorMap.get(eventAction);
            if (receptors == null) {
                L.d("Not Found: " + eventAction.toString());
                L.d("Now ReceptorMap: " + mReceptorMap);
                return;
            }

            for (Receptor receptor : receptors) {
                L.d("Post: " + receptor.toString());
                switch (receptor.posterType) {
                    case MAIN:
                        mainPoster.post(receptor, data);
                        break;
                    case POST:
                        postPoster.post(receptor, data);
                        break;
                    case ASYNC:
                        asyncPoster.post(receptor, data);
                        break;
                }

            }
        }

    }

}
