package cn.gavinliu.eventcast;

/**
 * ReceiverAction
 * 通过 {@link cn.gavinliu.eventcast.annotation.Receiver}的信息 区别不同的接收器
 * <p/>
 * 1.有设置Tag的Receiver,使用tag和parameterTypes区分;
 * 2.没设置Tag的Receiver,使用className,methodName,parameterTypes区分.
 * <p/>
 * Created by gavin on 15-4-29.
 */
public class ReceiverAction {

    String className;
    String methodName;
    String parameterTypesName;
    String tag;

    public ReceiverAction(String className, String methodName, String parameterTypesName, String tag) {
        this.className = className;
        this.methodName = methodName;
        this.parameterTypesName = parameterTypesName;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (tag != null) {

        } else {

        }

        return super.equals(o);
    }
}
