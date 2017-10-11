package org.smart4j.framework.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.proxy.AspectProxy;

import java.lang.reflect.Method;


/**
 * 拦截 Controller 所有方法
 *
 * Created by yuezhang on 17/10/11.
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy{

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

    private long begin;

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        LOGGER.info("--------------- begin ---------------");
        LOGGER.info(String.format("class: %s" , cls.getName()));
        LOGGER.info(String.format("method: %s", method.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
        LOGGER.info(String.format("time: %dms",System.currentTimeMillis() - begin));
        LOGGER.info("--------------- end ---------------");
    }
}
