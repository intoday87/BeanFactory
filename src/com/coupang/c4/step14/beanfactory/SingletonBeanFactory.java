package com.coupang.c4.step14.beanfactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by coupang on 14. 12. 21..
 */
public class SingletonBeanFactory implements BeanFactory {
    Map<String, Object> instanceMap;;


    public SingletonBeanFactory() {
        instanceMap = new HashMap<String, Object>();
    }

    @Override
    public <T> T getInstance(Class<T> type) {
        for (Object instance : instanceMap.values()) {
            if (instance.getClass().equals(type)) {
                return (T)instance;
            }
        }
        return null;
    }

    @Override
    public Object getInstance(String beanName) {
        if(!instanceMap.containsKey(beanName)) {
            return null;
        }
        return instanceMap.get(beanName);
    }

    public boolean addBean(String beanName, String classFullPath) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        if(instanceMap.containsKey(beanName)) {
            return false;
        }

        Constructor<?> constructor = Class.forName(classFullPath).getDeclaredConstructor();
        constructor.setAccessible(true);
        instanceMap.put(beanName, constructor.newInstance());

        return true;
    }
}
