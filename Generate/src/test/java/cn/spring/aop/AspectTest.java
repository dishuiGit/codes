package cn.spring.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectTest {

	@Around("execution(* cn.spring.impl.Test1Impl.test*(..))")
	public Object before(ProceedingJoinPoint pjp) throws Throwable{
//		jp.
		System.out.println("before!!");
//		Object proceed = pjp.proceed();
//		System.out.println("after!");
		return "before";
	}
}
