package org.tedu.cloudnote.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tedu.cloudnote.dao.UserDao;
import org.tedu.cloudnote.entity.User;
import org.tedu.cloudnote.util.NoteResult;
import org.tedu.cloudnote.util.NoteUtil;

@Service("userService")
@Transactional // 作用到所有方法
public class UserServiceImpl implements UserService {
	@Resource
	private UserDao userDao;

	/**
	 * 用户登陆校验
	 */
	public NoteResult checkLogin(String name, String password) {
		NoteResult result = new NoteResult();
		// 用户名和密码检查
		User user = userDao.findByName(name);
		if (user == null) {
			result.setStatus(1);
			result.setMsg("用户不存在");
			// result.setData(null);
			return result;
		}
		// 将用户输入的password加密
		String md5_pwd = NoteUtil.md5(password);
		// 比对数据库和md5_pwd是否一致
		if (!user.getCn_user_password().equals(md5_pwd)) {
			result.setStatus(2);
			result.setMsg("密码不正确");
			return result;
		}
		result.setStatus(0);
		result.setMsg("登录成功");
		result.setData(user.getCn_user_id());// 返回用户ID
		return result;
	}

	/**
	 * 用户注册
	 */
	public NoteResult registUser(String name, String nick, String password) {
		NoteResult result = new NoteResult();
		// 用户名检查
		User has_user = userDao.findByName(name);
		if (has_user != null) {
			result.setStatus(1);
			result.setMsg("用户名已存在");
			return result;
		}
		// 执行注册操作
		User user = new User();
		user.setCn_user_name(name);
		user.setCn_user_nick(nick);
		String md5_pwd = NoteUtil.md5(password);
		user.setCn_user_password(md5_pwd);
		user.setCn_user_token(null);
		String userId = NoteUtil.createId();
		user.setCn_user_id(userId);
		userDao.save(user);// 插入
		result.setStatus(0);
		result.setMsg("注册成功");
		return result;
	}

	/**
	 * 修改用户密码
	 */
	@Override
	public NoteResult changePwd(String userId, String password) {
		NoteResult result = new NoteResult();
		String md5_pwd = NoteUtil.md5(password);
		User user = new User();
		user.setCn_user_id(userId);
		user.setCn_user_password(md5_pwd);
		Integer count = 0;
		count = userDao.changePwd(user);
		if (count == 1) {
			result.setStatus(0);
			result.setMsg("修改密码成功");
		} else {
			result.setStatus(1);
			result.setMsg("修改密码失败");
		}
		return result;
	}
}
