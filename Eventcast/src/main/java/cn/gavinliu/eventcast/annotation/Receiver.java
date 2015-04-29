package cn.gavinliu.eventcast.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Event 接收器注解
 * <p/>
 * Created by gavin on 15-3-26.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Receiver {

    String tag();

    String mode();

}
