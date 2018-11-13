package com.github.dubbo.commons.utils;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;

import model.GoodsItemDTO;
import net.sf.json.JSONObject;

/**
 * 使用reflect进行转换
 */
public class Map2ObjectUtils {
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T mapToObject(Map map, Class<T> beanClass) {
        net.sf.json.JSONObject fromObject = net.sf.json.JSONObject.fromObject(map);
        Object bean = JSONObject.toBean(fromObject, beanClass);
        return (T)bean;
    }
    
    public static void main(String[] args) {
        HashMap<Object, Object> newHashMap = Maps.newHashMap();
        newHashMap.put("goodsItemId", 1);
        GoodsItemDTO mapToObject = mapToObject(newHashMap, GoodsItemDTO.class);
        System.out.println(mapToObject);
    }
}
