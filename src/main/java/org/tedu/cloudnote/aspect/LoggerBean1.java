package org.tedu.cloudnote.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component//扫描
@Aspect//指定为切面组件，等价于<aop:aspect>
public class LoggerBean1 {
	
	@Before("within(org.tedu.cloudnote.service..*)")//等价于<aop:before>
	public void loggerService(){
		System.out.println(
			"==执行Service业务处理==");
	}
	
}
