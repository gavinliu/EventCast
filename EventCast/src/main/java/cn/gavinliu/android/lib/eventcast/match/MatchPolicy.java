package cn.gavinliu.android.lib.eventcast.match;

import java.util.List;

import cn.gavinliu.android.lib.eventcast.event.EventAction;

/**
 * Created by gavin on 15-4-29.
 */
public interface MatchPolicy {

    List<EventAction> findMatchEventActions(EventAction action, Object data);

}
