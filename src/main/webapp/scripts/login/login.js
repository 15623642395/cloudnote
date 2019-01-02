//获取用户ID
var userId = getCookie("userId");

/**
 * 修改密码
 * 
 * @param changepwdSuccess
 * @param changepwdError
 */
function changepwd(changepwdSuccess, changepwdError) {
	var password = $("#new_password").val();
	$.ajax({
		url : schema_url + "/user/resetPwd.do",
		type : "post",
		data : {
			"userId" : userId,
			"password" : password
		},
		dataType : "json",
		success : function(result) {
			changepwdSuccess(result);
		},
		error : function() {
			changepwdError();
		}
	});
}

/**
 * 退出登录
 */
function logout() {
	delCookie(userId);// 删除用户cookie
	location.href = "log_in.html";
}
