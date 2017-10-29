package org.smart4j.framework.bean;

import org.smart4j.framework.util.CastUtil;
import org.smart4j.framework.util.CollectionUtil;

import java.util.Map;

/**
 * 请求参数对象
 * Created by yuezhang on 17/10/6.
 */
public class Param {

    private Map<String,Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * 根据参数名获取long型参数值
     * @param name
     * @return
     */
    public Long getLong(String name){
        Object obj = paramMap.get(name);
        if (obj != null){
            return CastUtil.castLong(obj);
        }else {
            return null;
        }
    }

    public String getString(String name){
        return paramMap.get("name") != null ? paramMap.get("name").toString() : null;
    }

    /**
     * 获取所有字段信息
     * @return
     */
    public Map<String,Object> getMap(){
        return paramMap;
    }

    /**
     * 验证参数是否为空
     * @return
     */
    public boolean isEmpty(){
        return CollectionUtil.isEmpty(paramMap);
    }
}
