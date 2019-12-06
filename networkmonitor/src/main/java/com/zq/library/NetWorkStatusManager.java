package com.zq.library;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import android.view.View;

/**
 * @author :Created by zhangqing on 2019/03/13 16:25
 * @description :网络注册管理类
 * @email :1423118197@qq.com
 * @classpath : com.zq.library.NetWorkStatusManager
 */
public class NetWorkStatusManager
{
    private static final Map<Class<?>, Register<Object>> BINDERS = new LinkedHashMap<Class<?>, Register<Object>>();

    private NetWorkStatusManager()
    {
    }

    private static Register register(Object target, Object source, Finder finder)
    {
        Class<?> targetClass = target.getClass();
        try
        {
            Register<Object> viewBinder = findViewBinderForClass(targetClass, finder, source);
            if (viewBinder != null)
            {
                viewBinder.register(target);
            }
            return viewBinder;
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to register networkStatusManager for " + targetClass.getName(), e);
        }
    }

    public static void unregister(Object target)
    {
        Class<?> targetClass = target.getClass();
        try
        {
            Register<Object> viewBinder = BINDERS.get(targetClass);
            if (viewBinder != null)
            {
                viewBinder.unregister();
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to bind views for " + targetClass.getName(), e);
        }
    }

    private static Register<Object> findViewBinderForClass(Class<?> cls, Finder finder, Object source) throws IllegalAccessException, InstantiationException
    {
        Register<Object> viewBinder = BINDERS.get(cls);
        if (viewBinder != null)
        {
            return viewBinder;
        }
        String clsName = cls.getName();
        try
        {
            Class<?> viewBindingClass = Class.forName(clsName + NetWorkProcessor.SUFX);
            Constructor<?> cons = viewBindingClass.getConstructor(Finder.class, Object.class);
            viewBinder = (Register<Object>) cons.newInstance(finder, source);
        }
        catch (ClassNotFoundException e)
        {
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        BINDERS.put(cls, viewBinder);
        return viewBinder;
    }

    public static Register register(Activity activity)
    {
        return register(activity, activity, Finder.ACTIVITY);
    }

    public static Register register(View fragment)
    {
        return register(fragment, fragment, Finder.VIEW);
    }

    public enum Finder
    {
        ACTIVITY
                {
                    @Override
                    public Context getContext(Object source)
                    {
                        return (Activity) source;
                    }

                    @Override
                    public <T extends View> T findViewById(int viewId, Object source)
                    {
                        return (T) ((Activity) source).findViewById(viewId);
                    }
                }, VIEW
            {
                @Override
                public Context getContext(Object source)
                {
                    return ((View) source).getContext();
                }

                @Override
                public <T extends View> T findViewById(int viewId, Object source)
                {
                    return (T) ((View) source).findViewById(viewId);
                }
            }, DIALOG
            {
                @Override
                public Context getContext(Object source)
                {
                    return ((Dialog) source).getContext();
                }

                @Override
                public <T extends View> T findViewById(int viewId, Object source)
                {
                    return (T) ((Dialog) source).findViewById(viewId);
                }
            };

        public abstract Context getContext(Object source);

        public abstract <T extends View> T findViewById(int viewId, Object source);

    }


}
