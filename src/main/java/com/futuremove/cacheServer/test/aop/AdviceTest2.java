package com.futuremove.cacheServer.test.aop;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;


@Aspect  
@Order(2)
public class AdviceTest2 {
	@Before("execution(* com.futuremove.cacheServer.test.aop.cutter.*.*(..))")  
    public void authorith(){  
       // logger.trace("模拟进行权限检查22222。");
    }  
	
	@After("execution(* com.futuremove.cacheServer.test.aop.cutter.*.*(..))")  
    public void after(){
       // logger.trace("after22222222。");
    }
	
	
}
