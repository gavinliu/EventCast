package cn.gavinliu.android.lib.eventcast.match;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import cn.gavinliu.android.lib.eventcast.event.EventAction;
import cn.gavinliu.android.lib.eventcast.utils.Utils;

/**
 * Created by gavin on 15-5-12.
 */
public class WidelyMatchPolicy implements MatchPolicy {

    @Override
    public List<EventAction> findMatchEventActions(EventAction action, Object data) {
        Class<?> eventClass = data.getClass();
        HashSet<EventAction> set = new HashSet<EventAction>();

        while (eventClass != null) {
            String parameterTypesName = Utils.makeParameterTypesName(eventClass);
            EventAction eventAction = new EventAction(action.className, action.methodName, parameterTypesName, action.tag);
            set.add(eventAction);
            addInterfaces(set, eventClass, action);
            eventClass = eventClass.getSuperclass();
        }

        List<EventAction> result = new ArrayList<EventAction>();
        result.addAll(set);
        return result;
    }

    private void addInterfaces(HashSet<EventAction> eventTypes, Class<?> eventClass, EventAction action) {
        if (eventClass == null) {
            return;
        }
        Class<?>[] interfacesClasses = eventClass.getInterfaces();
        for (Class<?> interfaceClass : interfacesClasses) {
            if (!eventTypes.contains(interfaceClass)) {
                String parameterTypesName = Utils.makeParameterTypesName(interfaceClass);
                EventAction eventAction = new EventAction(action.className, action.methodName, parameterTypesName, action.tag);
                eventTypes.add(eventAction);
                addInterfaces(eventTypes, interfaceClass, action);
            }
        }
    }

}
