//用户注册
function userRegist(){
	//清除提示信息
	$("#warning_1 span").html("");
	$("#warning_2 span").html("");
	$("#warning_3 span").html("");
	$("#warning_1").hide();
	$("#warning_2").hide();
	$("#warning_3").hide();
	//获取请求参数
	var name = $("#regist_username").val().trim();
	var nick = $("#nickname").val().trim();
	var password = $("#regist_password").val().trim();
	var final_password = $("#final_password").val().trim();
	//检查格式
	var check = true;
	if(name==""){
		check = false;
	   $("#warning_1 span").html("用户名不能为空");
		$("#warning_1").show();
	}
	if(password==""){//检查非空
		check = false;
		$("#warning_2 span").html("密码不能为空");
		$("#warning_2").show();
	}else if(password.length<6){//检查位数
		check = false;
		$("#warning_2 span").html("密码长度过短");
		$("#warning_2").show();
	}
	if(final_password==""){//检查非空
	   check = false;
		$("#warning_3 span").html("密码不能为空");
		$("#warning_3").show();
	}else	if(final_password!=password){//检查一致性
		check = false;
		$("#warning_3 span").html("密码输入不一致");
		$("#warning_3").show();
	}
	//发送Ajax请求
	if(check){
		$.ajax({
			url:schema_url+"/user/regist.do",
			type:"post",
			data:{"name":name,"nick":nick,"password":password},
			dataType:"json",
			success:function(result){
				if(result.status==0){//成功
					alert(result.msg);//提示成功
					$("#back").click();//触发返回按钮的单击
				}else if(result.status==1){//用户名被占用
					$("#warning_1 span").html(result.msg);
					$("#warning_1").show();
				}
			},
			error:function(){
				alert("注册失败");
			}
		});
	}
};

//用户登陆
function userLogin(){
	//清除原有提示信息
	$("#count_span").html("");
	$("#password_span").html("");
	//获取请求参数
	var name = $("#count").val().trim();
	var password = $("#password").val().trim();
	//格式检查
	var check = true;//通过检查
	if(name==""){
		check = false;
		$("#count_span").html("用户名为空");
	}
	if(password==""){
		check = false;
		$("#password_span").html("密码为空");
	}
	//通过格式检查,再发送ajax请求
	if(check){
		$.ajax({
			url:schema_url+"/user/login.do",
			type:"post",
			data:{"name":name,"password":password},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					//获取用户ID,写入Cookie
					var userId = result.data;
					addCookie("userId",userId,1);
					//进入edit.html
					window.location.href="edit.html";
				}else if(result.status==1){
					$("#count_span").html(result.msg);
				}else if(result.status==2){
					$("#password_span").html(result.msg);
				}
			},
			error:function(){
				alert("登录失败");
			}
		});
	}
};