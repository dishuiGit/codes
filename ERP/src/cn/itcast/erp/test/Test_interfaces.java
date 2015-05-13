package cn.itcast.erp.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import org.junit.Test;

import cn.itcast.erp.auth.dep.business.ebi.DepEbi;
import cn.itcast.erp.auth.dep.business.ebo.DepEbo;

public class Test_interfaces {

	Class<DepEbi> clazz = DepEbi.class;
	Annotation[] annotations = clazz.getAnnotations();
	Class[] interfaces = clazz.getInterfaces();
	@Test
	public void fn(){
		for(Annotation anno:annotations){
			System.out.println(anno);
		}
	}
	
	@Test
	public void fn1(){
		Type[] types = clazz.getGenericInterfaces(); 
//		for(Class inter:interfaces){
//			System.out.println(inter);
//		}
	}
}
