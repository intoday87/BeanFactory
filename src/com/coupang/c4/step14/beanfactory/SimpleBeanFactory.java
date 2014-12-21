package com.coupang.c4.step14.beanfactory;

import com.coupang.c4.ResourceUtil;

import java.util.HashMap;
import java.util.Map;

/*
 * 목적 - C/C++ 방식 벗어남. OOP개발
 *
 * 유연성, 확장성을 고려해 SIMPLEBEANFACTORY를 나누고 추상화
 *
 * 100 - 감동
 * 90 - 스펙 완성
 *
 * 1. singleton instance 관리 - 생성된 bean 캐싱
 *  1-1. 고려 내용 추후 다른 scope 생성이 용이한 구조가 되도록. -> step 16번에서 풀고 있음
 * //2. thread safe 하게 구성할 것.
 * //3. 계층 구조가 가능한 bean factory(1-1과 관련, 프로토타입 빈팩토리, 싱글톤 빈팩토리.. 들에서 물어봐서 만들수 있는지 물어본다.)
 *
 *  멤버 변수를 만들어 인스턴스를 싱글톤으로 관리한다.
 *  맵을 만들어 beanName을 키로한다.
 *  프로토타입 스코프 - 언제 어디서 하든 새로운 인스턴스
 *  리퀘스트 스코프 -
 *  멤버변수로 맵을 넣어놓으면
 */

public class SimpleBeanFactory {
	private String propertyPath;
	private Map<Scope, BeanFactory> beanFactoryMap;

	public SimpleBeanFactory(String propertyPath){
		this.propertyPath = propertyPath;
		beanFactoryMap = new HashMap<Scope, BeanFactory>();
		beanFactoryMap.put(Scope.SINGLETON, new SingletonBeanFactory());

		String[] texts;
		SingletonBeanFactory singletonBeanFactory = (SingletonBeanFactory) beanFactoryMap.get(Scope.SINGLETON);

		try {
			texts = ResourceUtil.readFully(ResourceUtil.resourceAsInputStream(propertyPath));

			for(String keyValue: texts) {
				String[] splited = keyValue.split("=");

				if (splited.length != 2) throw new Exception("property line format is not valid");

				String key = splited[0];
				String value = splited[1];

				singletonBeanFactory.addBean(key, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public <T> T getInstance(Class<T> type){
		// TODO : 코드를 채워주세요
		for (Map.Entry<Scope, BeanFactory> beanFactory : beanFactoryMap.entrySet()) {
			T instance = beanFactory.getValue().getInstance(type);
			if (instance != null) {
				return instance;
			}
		}
		return null;
	}
	
	public Object getInstance(String beanName){
		// TODO : 코드를 채워주세요
		for (Map.Entry<Scope, BeanFactory> beanFactory : beanFactoryMap.entrySet()) {
			Object instance = beanFactory.getValue().getInstance(beanName);
			if (instance != null) {
				return instance;
			}
		}

		return null;
	}
}
