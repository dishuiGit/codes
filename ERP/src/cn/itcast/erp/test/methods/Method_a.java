package cn.itcast.erp.test.methods;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class Method_a {
	@Before(value = "execution(* cn.itcast.erp.test.Target_inter.*(..))")
	public void fn1(){
		System.out.println("fn1.....");
	}
	@After(value = "execution(* cn.itcast.erp.test.Target_inter_base.*(..))")
	public void fn2(){
		System.out.println("fn2.....");
	}
}
