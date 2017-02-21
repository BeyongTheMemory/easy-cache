package com.pop.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xugang on 17/1/19.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheEvict {
    boolean needRemote() default true;//whether cache in remote

    /**
     * name and key will made  ceche key
     * if both is null,will use method signature to cache key
     * the key resutl must same as NeedCache key result will the cache can be evict
     */
    String name() default "";
    String key() default "";
}
