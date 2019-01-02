package org.tedu.cloudnote.aspect;

//做切面组件使用
public class LoggerBean {
	
	public void loggerController(){
		//切面处理逻辑
		System.out.println(
			"执行Controller.execute方法");
	}
	
	public void loggerService(){
		
	}
}
