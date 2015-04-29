package cn.gavinliu.eventcast;

/**
 * Created by gavin on 15-3-25.
 */
public class Eventcast {


    public void register(Object receiver) {

    }

    public void unRegister(Object receiver) {

    }


    /**
     * 通知所有 tag 的方法,此方法没有参数
     */
    public void post(String tag) {

    }

    /**
     * 通知所有 tag 的方法,此方法有参数
     *
     *
     */
    public void post(String tag, Object event) {
        if (event == null) {
            throw new NullPointerException("event不能为null");
        }

    }

    /**
     * 通知 class 的 method 方法,此方法没有参数
     */
    public void post(Class<?> clazz, String method) {

    }

    /**
     * 通知 class 的 method 方法,此方法有参数
     */
    public void post(Class<?> clazz, String method, Object event) {
        if (event == null) {
            throw new NullPointerException("event不能为null");
        }
    }


    private void notify(ReceiverMethod method, Object event){

    }
}