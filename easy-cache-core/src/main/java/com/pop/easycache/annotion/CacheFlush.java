package com.pop.easycache.annotion;

import java.lang.annotation.*;

/**
 * Created by xugang on 17/6/25.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CacheFlush {
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
}
