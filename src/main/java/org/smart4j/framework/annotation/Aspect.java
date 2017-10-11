package org.smart4j.framework.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 * 通过@Target(ElementType.TYPE)来设置该注解只能应用在类上。
 * 该注解中包含一个名为value的属性，它是一个注解类，用来定义Controller这类注解。
 * Created by yuezhang on 17/10/11.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    Class<? extends Annotation> value();

}
