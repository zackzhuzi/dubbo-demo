package com.github.dubbo.commons.utils;

import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 
 * @ClassName: PropertyConfigurer
 * @Description: 自定义属性配置类,在SPRING的XML中配置加载所有的属性文件
 * @author: yuzhu.peng
 * @date: 2017年11月15日 下午1:38:47
 */
public class PropertyConfigurer extends PropertyPlaceholderConfigurer {
    
    private static Properties properties;
    
    public static Integer getIntProperty(String name) {
        return Integer.parseInt(properties.getProperty(name, "0"));
    }
    
    public static String getStringProperty(String name) {
        return properties.getProperty(name, "");
    }
    
    public static String getProperty(String name, String defaultValue) {
        return properties.getProperty(name, defaultValue);
    }
    
    public static Object get(String key) {
        return properties.getProperty(key);
    }
    
    public Properties getProperties() {
        return properties;
    }
    
    @Override
    public void setProperties(Properties properties) {
        PropertyConfigurer.properties = properties;
    }
}
