package cn.gavinliu.android.lib.eventcast;

import java.lang.reflect.Method;

import cn.gavinliu.android.lib.eventcast.poster.PosterType;

/**
 * 接收器
 *
 * Created by gavin on 15-4-29.
 */
public class Receptor {

    public Object receiver;
    public Method method;
    public PosterType posterType;

    public Receptor(Object receiver, Method method, PosterType posterType) {
        this.receiver = receiver;
        this.method = method;
        this.posterType = posterType;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((receiver == null) ? 0 : receiver.hashCode());
        result = prime * result + ((method == null) ? 0 : method.hashCode());
        result = prime * result + ((posterType == null) ? 0 : posterType.hashCode());
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

        Receptor other = (Receptor) o;
        if (receiver == null) {
            if (other.receiver != null) {
                return false;
            }
        } else if (!receiver.equals(other.receiver)) {
            return false;
        }

        if (method == null) {
            if (other.method != null) {
                return false;
            }
        } else if (!method.equals(other.method)) {
            return false;
        }

        return true;
    }
}
