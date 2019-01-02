/***
 * 修改密码成功 
 * @param result
 */
function changepwdSuccess(result){
	if(result.status == 0){
		logout();
	}else{
		alert("修改密码失败");
	}
}
/***
 * 修改密码失败
 */
function changepwdError(){
	alert("系统异常,请联系管理员");
}