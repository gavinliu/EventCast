package cn.gavinliu.eventcast;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.gavinliu.eventcast.annotation.Receiver;

/**
 * Created by gavin on 15-4-29.
 */
public class ReceiverMethodFinder {

    Map<ReceiverAction, CopyOnWriteArrayList<Receptor>> mReceptorMap;

    public ReceiverMethodFinder(Map<ReceiverAction, CopyOnWriteArrayList<Receptor>> receptorMap) {
        this.mReceptorMap = receptorMap;
    }

    public void findReceiverMethod(Object receiver) {
        Class<?> clazz = receiver.getClass();

        if (clazz != null && isSystemClass(clazz.getName())) {
            Method[] allMethods = clazz.getDeclaredMethods();

            for (Method method : allMethods) {
                Receiver annotation = method.getAnnotation(Receiver.class);
                Class<?>[] parameterTypes = method.getParameterTypes();

                String tag = annotation.tag();
                String mode = annotation.mode();

                String parameterTypesName = makeParameterTypesName(parameterTypes);
                String methodName = method.getName();

                Receptor receptor = new Receptor(receiver, method, parameterTypes, mode);
                ReceiverAction receiverAction = new ReceiverAction(clazz.getName(), methodName, parameterTypesName, tag);

                add(receiverAction, receptor);
            }

        }
    }

    private void add(ReceiverAction receiverAction, Receptor receptor) {
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

    private boolean isSystemClass(String name) {
        return name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("android.");
    }

    private String makeParameterTypesName(Class<?>[] parameterTypes) {
        if (parameterTypes == null) {
            return null;
        }
        String name = "";
        for (Class<?> type : parameterTypes) {
            name += type.getName() + ",";
        }
        return name;
    }

}
