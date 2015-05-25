package com.futuremove.cacheServer.test.aop;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;


@Aspect  
@Order(1)
public class AdviceTest1 {
	@Before("execution(* com.futuremove.cacheServer.test.aop.cutter.*.*(..))")  
    public void authorith(){  
        System.out.println("模拟进行权限检查。");  
    }  
	
	@After("execution(* com.futuremove.cacheServer.test.aop.cutter.*.*(..))")  
    public void after(){  
        System.out.println("after11111。");  
    }
    
}
