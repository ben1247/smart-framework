package org.smart4j.framework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理链
 *
 * Created by yuezhang on 17/10/11.
 */
public class ProxyChain {

    /** 目标类 */
    private final Class<?> targetClass;

    /** 目标对象 */
    private final Object targetObject;

    /** 目标方法 */
    private final Method targetMethod;

    /** 方法代理 */
    private final MethodProxy methodProxy;

    /** 方法参数 */
    private final Object [] methodParams;

    /** 代理列表 */
    private List<Proxy> proxyList = new ArrayList<>();

    /** 代理索引 */
    private int proxyIndex = 0;

    public ProxyChain(Class<?> targetClass,
                      Object targetObject,
                      Method targetMethod,
                      MethodProxy methodProxy,
                      Object[] methodParams,
                      List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    /**
     * 在该方法中，我们通过proxyIndex来充当代理对象的计数器，
     * 若尚未达到proxyList的上限，则从proxyList中取出相应的Proxy对象，并调用其doProxy方法。
     * 在Proxy接口的实现中会提供相应的横切逻辑，并调用doProxyChain方法，
     * 随后将再次调用当前ProxyChain对象的doProxyChain方法，直到proxyIndex达到proxyList上限为止，
     * 最后调用methodProxy的invokeSuper方法，执行目标对象的业务逻辑。
     * @return
     * @throws Throwable
     */
    public Object doProxyChain() throws Throwable{
        Object methodResult;
        if(proxyIndex < proxyList.size()){
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        }else{
            methodResult = methodProxy.invokeSuper(targetObject,methodParams);
        }
        return methodResult;
    }
}
