package org.smart4j.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理管理器
 *
 * Created by yuezhang on 17/10/11.
 */
public class ProxyManager {

    /**
     * 利用cglib第三方工具类,使用Enhancer对象，将targetClass设置成superClass参数，创建一个方法回调，
     * 则targetClass的所有方法都会被代理，代理进来后new 一个ProxyChain对象，传入了目标类对象、目标方法，目标方法参数，以及cglib的代理方法。
     * 最后一个参数是代理列表。Proxy接口的实现类，实现了doProxy方法，则方法里就有ProxyChain对象，则可以拿到目标类的一切东西，进行切面处理。
     *
     * @param targetClass
     * @param proxyList
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final Class<?> targetClass , final List<Proxy> proxyList){
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass,targetObject,targetMethod,methodProxy,methodParams,proxyList).doProxyChain();
            }
        });
    }

}
