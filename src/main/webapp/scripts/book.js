//确认创建笔记本操作
function sureAddBook(){
	//获取请求参数
	var bookName = $("#input_notebook").val().trim();
	//TODO 检查格式,检查bookName非空
	//发送Ajax请求
	$.ajax({
		url:schema_url+"/book/add.do",
		type:"post",
		data:{"userId":userId,"bookName":bookName},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//关闭对话框
				closeAlertWindow();
				//创建一个笔记本li
				var bookId = result.data;
				createBookLi(bookId,bookName);
				//将新建的笔记本设置为选中
				$("#book_ul li:last").click();//模拟点击处理
				//提示成功
				alert(result.msg);
			}
		},
		error:function(){
			alert("创建笔记本失败");
		}
	});
};

//加载用户笔记本列表
function loadUserBooks(){
	$.ajax({
		url:schema_url+"/book/loadbooks.do",
		type:"post",
		data:{"userId":userId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				var books = result.data;//笔记本信息集合
				for(var i=0;i<books.length;i++){
					var bookName = books[i].cn_notebook_name;
					var bookId = books[i].cn_notebook_id;
					createBookLi(bookId,bookName);
				}
			}
		},
		error:function(){
			alert("加载用户笔记本列表失败");
		}
	});
};

//生成一个笔记本li,添加到列表区
function createBookLi(bookId,bookName){
	//拼一个li
	var sli='<li class="online">';
		sli+='  <a>';
		sli+='    <i class="fa fa-book" title="online" rel="tooltip-bottom"></i>';
		sli+=	bookName;
		sli+='  </a>';
		sli+='</li>';
	var $li = $(sli);
	$li.data("bookId",bookId);
	//将li添加到ul列表
	$("#book_ul").append($li);
};


