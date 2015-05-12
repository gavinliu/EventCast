package cn.gavinliu.android.lib.eventcast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.gavinliu.android.lib.eventcast.annotation.Receiver;
import cn.gavinliu.android.lib.eventcast.event.EventAction;
import cn.gavinliu.android.lib.eventcast.logger.L;
import cn.gavinliu.android.lib.eventcast.poster.PosterType;
import cn.gavinliu.android.lib.eventcast.utils.Utils;

/**
 * Created by gavin on 15-4-29.
 */
public class ReceiverMethodSpider {

    Map<EventAction, CopyOnWriteArrayList<Receptor>> mReceptorMap;

    public ReceiverMethodSpider(Map<EventAction, CopyOnWriteArrayList<Receptor>> receptorMap) {
        this.mReceptorMap = receptorMap;
    }

    public void findReceiverMethod(Object receiver) {
        Class<?> clazz = receiver.getClass();

        if (clazz != null && !Utils.isSystemClass(clazz.getName())) {
            Method[] allMethods = clazz.getDeclaredMethods();

            for (Method method : allMethods) {
                Receiver annotation = method.getAnnotation(Receiver.class);
                if (annotation != null) {
                    Class<?>[] parameterTypes = method.getParameterTypes();

                    PosterType posterType = annotation.posterType();
                    String parameterTypesName = Utils.makeParameterTypesName(parameterTypes);

                    String className = null;
                    String methodName = null;
                    String tag = annotation.tag();
                    if (tag.equals(Receiver.NULL_TAG)) {
                        tag = null;
                        className = clazz.getName();
                        methodName = method.getName();
                    }

                    Receptor receptor = new Receptor(receiver, method, posterType);
                    EventAction receiverAction = new EventAction(className, methodName, parameterTypesName, tag);

                    add(receiverAction, receptor);
                }
            }


        }
    }

    private void add(EventAction eventAction, Receptor receptor) {
        CopyOnWriteArrayList<Receptor> receptors = mReceptorMap.get(eventAction);

        if (receptors == null) {
            receptors = new CopyOnWriteArrayList<Receptor>();
        }

        if (receptors.contains(receptor)) {
            return;
        }

        receptors.add(receptor);
        L.d("增加 EventAction: " + eventAction.toString());
        L.d("增加 Receptor: " + receptor.toString());
        mReceptorMap.put(eventAction, receptors);
    }

    public void removeReceiverMethod(Object receiver) {
        Iterator<CopyOnWriteArrayList<Receptor>> iterator = mReceptorMap.values().iterator();
        while (iterator.hasNext()) {
            CopyOnWriteArrayList<Receptor> receptors = iterator.next();
            if (receptors != null) {
                List<Receptor> findReceptors = new ArrayList<Receptor>();

                for (Receptor receptor : receptors) {
                    if (receptor.receiver.equals(receiver)) {
                        L.d("删除: " + receptor.toString());
                        findReceptors.add(receptor);
                    }
                }

                receptors.removeAll(findReceptors);
            }

            if (receptors == null || receptors.size() == 0) {
                iterator.remove();
            }
        }

    }

}
