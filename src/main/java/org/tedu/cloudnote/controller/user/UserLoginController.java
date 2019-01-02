package org.tedu.cloudnote.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tedu.cloudnote.service.UserService;
import org.tedu.cloudnote.util.NoteResult;

/**
 * 发送请求：http://localhost:8080/cloudnote/log_in.html
 * @author 56525
 *
 */
@Controller // 扫描
@RequestMapping("/user")
public class UserLoginController {
	@Autowired // 注入
	private UserService userService;

	@RequestMapping("/login.do")
	@ResponseBody
	public NoteResult execute(String name, String password) {
		NoteResult result = userService.checkLogin(name, password);
		return result;
	}

}
