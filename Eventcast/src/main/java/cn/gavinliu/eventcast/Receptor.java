package cn.gavinliu.eventcast;

import java.lang.reflect.Method;

/**
 * Created by gavin on 15-4-29.
 */
public class Receptor {

    Object receiver;
    Method method;
    Class<?>[] parameterTypes;
    String mode;

    public Receptor(Object receiver, Method method, Class<?>[] parameterTypes, String mode) {
        this.receiver = receiver;
        this.method = method;
        this.parameterTypes = parameterTypes;
        this.mode = mode;
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
        return super.equals(o);
    }
}
