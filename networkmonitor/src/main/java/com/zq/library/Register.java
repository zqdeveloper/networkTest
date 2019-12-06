package com.zq.library;

/**
 * @author :Created by zhangqing on 2019/03/14 14:53
 * @description :
 * @email :1423118197@qq.com
 * @classpath : com.zq.library.Register
 */
public interface Register<T>
{
    void register(final T target);

    void unregister();
}
