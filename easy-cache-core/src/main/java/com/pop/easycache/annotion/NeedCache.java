package com.pop.easycache.annotion;

import java.lang.annotation.*;

/**
 * Created by xugang on 17/6/25.
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface NeedCache {
    /**
     * 缓存键值的名字
     * @return
     */
    String name();

    /**
     * 支持SpEL表达式，和name共同组成最终的key
     * @return
     */
    String key();

//    /**
//     * 过期时间
//     * @return
//     */
//    long ttl() default -1l;
//
//    /**
//     * 过期时间单位
//     * @return
//     */
//    TimeUnit ttlUnit() default TimeUnit.SECONDS;
}
