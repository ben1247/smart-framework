package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * Created by yuezhang on 17/10/6.
 */
public final class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     * @param cls
     * @return
     */
    public static Object newInstance(Class<?> cls){
        Object instance;
        try {
            instance  = cls.newInstance();
        } catch (Exception e) {
            LOGGER.error("new instance failure",e);
            throw new RuntimeException(e);
        }
        return instance;
    }
    public static Object newInstance(String className){
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            LOGGER.error("class not found",e);
            throw new RuntimeException(e);
        }
        return newInstance(cls);

    }

    /**
     * 调用方法
     * @param obj
     * @param method
     * @param args
     * @return
     */
    public static Object invokeMethod(Object obj , Method method , Object ... args){

        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(obj,args);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("invoke method failure",e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置成员变量的值
     * @param obj
     * @param field
     * @param value
     */
    public static void setField(Object obj , Field field , Object value){
        try {
            field.setAccessible(true);
            field.set(obj,value);
        } catch (IllegalAccessException e) {
            LOGGER.error("set field failure",e);
            throw new RuntimeException(e);
        }
    }

}
