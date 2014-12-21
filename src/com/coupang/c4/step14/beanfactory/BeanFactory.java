package com.coupang.c4.step14.beanfactory;

/**
 * Created by coupang on 14. 12. 21..
 */
public interface BeanFactory {
    public <T> T getInstance(Class<T> type);
    public Object getInstance(String beanName);
}
