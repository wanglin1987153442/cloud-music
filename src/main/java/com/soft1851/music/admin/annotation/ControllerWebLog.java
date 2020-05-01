package com.soft1851.music.admin.annotation;

/**
 * @author wl
 * @ClassNamesada
 * @Description TODO
 * @Date 2020/4/30
 * @Version 1.0
 */

import java.lang.annotation.*;

/**
 * @author wl
 * @ClassNameControllerWebLog
 * @Description 自定义web日志注解
 * @Date 2020/4/12
 * @Version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ControllerWebLog {
    //调用接口名称
    String name();
    //改日志是否要持久化存储
    boolean isSaved()default false;

}

