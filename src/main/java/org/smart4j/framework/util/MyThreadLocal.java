package org.smart4j.framework.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 自己实现的ThreadLocal
 * Created by yuezhang on 17/10/17.
 */
public class MyThreadLocal<T> {

    private Map<Thread,T> container = Collections.synchronizedMap(new HashMap<Thread, T>());

    public void set(T value){
        container.put(Thread.currentThread(),value);
    }

    public T get(){
        Thread thread = Thread.currentThread();
        T value = container.get(thread);
        if(value == null && !container.containsKey(thread)){
            value = initialValue();
            container.put(thread,value);
        }
        return value;
    }

    public void remove(){
        container.remove(Thread.currentThread());
    }

    /**
     * 为什么方法是protected的呢？就是为了提醒程序员，
     * 这个方法是要程序员来实现的，要给这个线程局部变量设置一个初始值。
     * @return
     */
    protected T initialValue(){
        return null;
    }

}
