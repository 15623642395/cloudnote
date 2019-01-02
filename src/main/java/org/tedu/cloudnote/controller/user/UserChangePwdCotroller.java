package org.tedu.cloudnote.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tedu.cloudnote.service.UserService;
import org.tedu.cloudnote.util.NoteResult;

@Controller // 扫描
@RequestMapping("/user")
public class UserChangePwdCotroller {
	@Autowired // 注入
	private UserService userService;

	/**
	 * 修改用户密码
	 * @param loginUserId
	 * @param pwd
	 * @return
	 */
	@RequestMapping("/resetPwd.do")
	@ResponseBody
	public NoteResult execute(String userId, String password) {
		NoteResult result = userService.changePwd(userId, password);
		return result;
	}
}
