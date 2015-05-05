package cn.gavinliu.eventcast;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.gavinliu.eventcast.annotation.Receiver;
import cn.gavinliu.eventcast.event.EventAction;
import cn.gavinliu.eventcast.utils.Utils;

/**
 * Created by gavin on 15-4-29.
 */
public class ReceiverMethodFinder {

    Map<EventAction, CopyOnWriteArrayList<Receptor>> mReceptorMap;

    public ReceiverMethodFinder(Map<EventAction, CopyOnWriteArrayList<Receptor>> receptorMap) {
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


                    String mode = annotation.mode();
                    String parameterTypesName = Utils.makeParameterTypesName(parameterTypes);

                    String className = null;
                    String methodName = null;
                    String tag = annotation.tag();
                    if (tag.equals(Receiver.NULL_TAG)) {
                        tag = null;
                        className = clazz.getName();
                        methodName = method.getName();
                    }

                    Receptor receptor = new Receptor(receiver, method, parameterTypes, mode);
                    EventAction receiverAction = new EventAction(className, methodName, parameterTypesName, tag);

                    add(receiverAction, receptor);
                }
            }


        }
    }

    private void add(EventAction receiverAction, Receptor receptor) {
        CopyOnWriteArrayList<Receptor> receptors = mReceptorMap.get(receiverAction);

        if (receptors == null) {
            receptors = new CopyOnWriteArrayList<Receptor>();
        }

        if (receptors.contains(receptor)) {
            return;
        }

        receptors.add(receptor);

        mReceptorMap.put(receiverAction, receptors);
    }

    public void removeReceiverMethod(Object receiver) {
        Iterator<CopyOnWriteArrayList<Receptor>> iterator = mReceptorMap.values().iterator();
        while (iterator.hasNext()) {
            CopyOnWriteArrayList<Receptor> receptors = iterator.next();
            if (receptors != null) {
                List<Receptor> findReceptors = new ArrayList<Receptor>();

                for (Receptor receptor : receptors) {
                    if (receptor.receiver.equals(receiver)) {
                        Log.d("", "### 移除订阅 " + receiver.getClass().getName());
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
