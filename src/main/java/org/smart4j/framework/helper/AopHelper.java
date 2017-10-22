package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.proxy.AspectProxy;
import org.smart4j.framework.proxy.Proxy;
import org.smart4j.framework.proxy.ProxyManager;
import org.smart4j.framework.proxy.TransactionProxy;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * AOP 助手类
 * Created by yuezhang on 17/10/12.
 */
public final class AopHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            Map<Class<?>,Set<Class<?>>> proxyMap = createProxyMap(); // key: 代理类  value：目标类集合
            Map<Class<?>,List<Proxy>> targetMap = createTargetMap(proxyMap); // key: 目标类  value：代理类集合
            for(Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()){
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                // 生成代理对象
                Object proxy = ProxyManager.createProxy(targetClass,proxyList);
                BeanHelper.setBean(targetClass,proxy);
            }
        } catch (Exception e) {
            LOGGER.error("aop failure" ,e);
        }
    }

    /**
     * 获取代理类和目标类之间的映射关系
     * @return Map集合，key：代理类  value：目标类集合
     * @throws Exception
     */
    private static Map<Class<?>,Set<Class<?>>> createProxyMap() throws Exception{
        Map<Class<?> , Set<Class<?>>> proxyMap = new HashMap<>();
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    /**
     * 获取切面代理类和目标类之间的映射关系
     * 注：切面代理类需要扩展AspectProxy抽象类，还需要带有@Aspect注解，
     * 只有满足这2个条件才能建立切面代理类和目标类集合之间的映射关系。
     * @param proxyMap
     * @return Map集合，key：切面代理类  value：目标类集合
     * @throws Exception
     */
    private static void addAspectProxy(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for(Class<?> proxyClass : proxyClassSet){
            if(proxyClass.isAnnotationPresent(Aspect.class)){
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass,targetClassSet);
            }
        }
    }

    /**
     * 获取事务代理类（TransactionProxy.class）和目标类之间的映射关系
     * 注：目标类需要带有@Service注解，
     * @param proxyMap
     * @return Map集合，key：事务代理类  value：目标类集合
     * @throws Exception
     */
    private static void addTransactionProxy(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
        Set<Class<?>> serviceClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class,serviceClassSet);
    }

    /**
     * 获取Aspect注解中设置的注解类
     * 例如：根据Controller.class 来获取 带有@Controller的类有哪些
     * @param aspect
     * @return 目标类集合（例子里所有controller类）
     * @throws Exception
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception{
        Set<Class<?>> targetClassSet = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        if(!annotation.equals(Aspect.class)){
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    /**
     * 通过代理类Map分析出目标类和代理对象列表之间的映射关系
     * @return
     * @throws Exception
     */
    private static Map<Class<?>,List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
        Map<Class<?> , List<Proxy>> targetMap = new HashMap<>();
        for(Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()){
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for(Class<?> targetClass : targetClassSet){
                Proxy proxy = (Proxy)proxyClass.newInstance();
                if(targetMap.containsKey(targetClass)){
                    targetMap.get(targetClass).add(proxy);
                }else{
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass,proxyList);
                }
            }
        }
        return targetMap;
    }

}
