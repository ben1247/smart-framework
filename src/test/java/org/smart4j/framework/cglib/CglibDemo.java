package org.smart4j.framework.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * cglib demo
 * Created by yuezhang on 17/10/28.
 */
public class CglibDemo {

    /**
     * 使用FixedValue可以很容易的替换掉方法的返回值。
     */
    @Test
    public void testFixedValue(){
        Enhancer e = new Enhancer();
        e.setSuperclass(SampleClass.class);
        e.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "hello cglib...";
            }
        });

        SampleClass proxy = (SampleClass)e.create();
        System.out.println(proxy.test1(null));
        System.out.println(proxy.test2(null));

    }

    /**
     * 唯一需要注意的就是proxy.invokeSuper和proxy.invoke的区别。
     * invokeSuper是退出当前interceptor的处理，进入下一个callback处理，invoke则会继续回调该方法，
     * 如果传递给invoke的obj参数出错容易造成递归调用。
     */
    @Test
    public void testMethodInterceptor(){
        Enhancer e = new Enhancer();
        e.setSuperclass(SampleClass.class);
        e.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class){
                    return "hello cglib...";
                }else {
                    return methodProxy.invokeSuper(obj,args);
                }

            }
        });

        SampleClass proxy = (SampleClass)e.create();
        System.out.println(proxy.test1(null));
        System.out.println(proxy.toString());
    }



}
