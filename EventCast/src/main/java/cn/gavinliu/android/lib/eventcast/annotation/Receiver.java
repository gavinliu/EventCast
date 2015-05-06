package cn.gavinliu.android.lib.eventcast.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.gavinliu.android.lib.eventcast.poster.PosterType;

/**
 * Event 接收器注解
 * <p/>
 * Created by gavin on 15-3-26.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Receiver {
    public static final String NULL_TAG = "";

    String tag() default NULL_TAG;

    PosterType posterType() default PosterType.MAIN;

}
