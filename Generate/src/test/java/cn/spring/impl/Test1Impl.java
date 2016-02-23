package cn.spring.impl;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.spring.bean.Test1;
@Component
@Scope("prototype")
public class Test1Impl {

	@Resource
	private Test1 test1;
	
	
	public void test1out(){
		System.out.println(test1.getId());
		System.out.println(test1.getName());
	}
	
	public void yy(){
		System.out.println(test1);
	}
}
