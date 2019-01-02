package org.tedu.cloudnote.aspect;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ExceptionBean {
	
	/**
	 * 将异常信息写入文件
	 * @param e 目标组件方法抛出的异常对象
	 */
	@AfterThrowing(throwing="e",
	pointcut="within(org.tedu.cloudnote.controller..*)")
	public void execute(Exception e){
		//获取异常对象,将异常信息写入文件
		try{
			String file = "note.log";
			FileWriter fw = 
				new FileWriter(file,true);
			PrintWriter pw = new PrintWriter(fw);
			//打印头部
			pw.println("**************************************");
			pw.println("**异常类型："+e);
			pw.println("**发生时间："+new Date());
			pw.println("**************************************");
			//打印异常信息
			e.printStackTrace(pw);
			pw.flush();
			pw.close();
			fw.close();
		}catch(Exception ex){
			System.out.println("异常写入日志失败");
		}
	}
	
}
