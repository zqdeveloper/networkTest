package com.zq.annotations;

import com.zq.type.NetWork;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author :Created by zhangqing on 2019/02/19 15:02
 * @description :
 * @email :1423118197@qq.com
 * @classpath : com.zq.annotations.NetWork
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface NetWorkType
{
    NetWork value() default NetWork.AUTO;
}
