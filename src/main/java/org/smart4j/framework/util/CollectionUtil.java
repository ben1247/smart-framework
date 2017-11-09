package org.smart4j.framework.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.*;

/**
 * 集合工具类
 * Created by yuezhang on 17/9/27.
 */
public final class CollectionUtil {

    public static boolean isEmpty(Collection<?> collection){
        return CollectionUtils.isEmpty(collection);
    }

    public static boolean isNotEmpty(Collection<?> collection){
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Map<?,?> map){
        return MapUtils.isEmpty(map);
    }

    public static boolean isNotEmpty(Map<?,?> map){
        return !isEmpty(map);
    }

    public static <T> Set<T> list2Set(List<T> list){
        if (isEmpty(list)) return null;
        Set<T> set = new HashSet<>(list.size());
        set.addAll(list);
        return set;
    }

}
