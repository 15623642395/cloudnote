package org.tedu.cloudnote.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tedu.cloudnote.service.UserService;
import org.tedu.cloudnote.util.NoteResult;

@Controller
@RequestMapping("/user")
public class UserRegistController {
	@Resource
	private UserService userService;
	
	@RequestMapping("/regist.do")
	@ResponseBody
	public NoteResult execute(
		String name,String nick,String password){
		//调用业务组件处理
		NoteResult result = userService.registUser(
				name, nick, password);
		return result;
	}
	
}



