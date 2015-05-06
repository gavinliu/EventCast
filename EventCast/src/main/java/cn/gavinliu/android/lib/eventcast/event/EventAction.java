package cn.gavinliu.android.lib.eventcast.event;

import cn.gavinliu.android.lib.eventcast.annotation.Receiver;

/**
 * 通过 {@link cn.gavinliu.android.lib.eventcast.annotation.Receiver}的信息 区别不同的接收器
 * <p/>
 * 1.有设置Tag的Receiver,使用tag和parameterTypes区分;
 * 2.没设置Tag的Receiver,使用className,methodName,parameterTypes区分.
 * <p/>
 * Created by gavin on 15-4-29.
 */
public class EventAction {

    String className;
    String methodName;
    String parameterTypesName;
    String tag;

    public EventAction(String className, String methodName, String parameterTypesName, String tag) {
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
        final int prime = 31;
        int result = 1;
        result = prime * result + ((className == null) ? 0 : className.hashCode());
        result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
        result = prime * result + ((parameterTypesName == null) ? 0 : parameterTypesName.hashCode());
        result = prime * result + ((tag == null) ? 0 : tag.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }

        EventAction other = (EventAction) o;
        if (parameterTypesName == null) {
            if (other.parameterTypesName != null) {
                return false;
            }
        } else if (!parameterTypesName.equals(other.parameterTypesName)) {
            return false;
        }

        if (tag != null && !tag.equals(Receiver.NULL_TAG)) {
            if (other.tag == null) {
                return false;
            } else if (!tag.equals(other.tag)) {
                return false;
            }
        } else {
            if (className == null) {
                if (other.className != null) {
                    return false;
                }
            } else if (!className.equals(other.className)) {
                return false;
            }

            if (methodName == null) {
                if (other.methodName != null) {
                    return false;
                }
            } else if (!methodName.equals(other.methodName)) {
                return false;
            }

        }
        return true;
    }
}
