//获取用户ID
var userId = getCookie("userId");
// 弹出修改笔记本名称对话框
function alertRenameBookName() {
	var oldName = $(this).text().trim();
	var url = schema_url + "/alert/alert_rename.html";
	$("#can").load(url, function() {
		$("#input_notebook_rename").val(oldName);
		$(".opacity_bg").show();
	});
};

// 修改笔记本名称
function renameBook() {
	var bookName = $("#input_notebook_rename").val();
	var $bookli = $("#book_ul a.checked").parent();
	var bookId = $bookli.data("bookId");
	$.ajax({
		url : schema_url + "/book/renameBook.do",
		type : "post",
		data : {
			"bookId" : bookId,
			"bookName" : bookName
		},
		dataType : "json",
		success : function(result) {
			if(result.status==0){
				//刷新页面
				location.href = "edit.html";
			}else{
				alert(result.msg);
			}
		},
		error : function() {
			alert("服务器异常，请稍后再试");
		}
	});
}
// 弹出对话框
function alertWindow(url) {
	$("#can").load(url);// 载入对话框内容
	$(".opacity_bg").show();// 显示灰色背景
}
// 弹出转移笔记对话框
function alertMoveNote() {
	var url = schema_url + "/alert/alert_move.html";
	$("#can").load(
			url,
			function() {
				// 在载入对话框内容之后调用
				// 获取笔记本列表信息,生成Option选项
				var books = $("#book_ul li");
				for (var i = 0; i < books.length; i++) {
					var $li = $(books[i]);// 将dom变成jQuery对象
					var bookId = $li.data("bookId");
					var bookName = $li.text().trim();
					// 拼一个option
					var opt = "<option value='" + bookId + "'>" + bookName
							+ "</option>";
					$("#moveSelect").append(opt);
				}
			});
	$(".opacity_bg").show();// 显示灰色背景
}
// 弹出删除笔记确认框
function alertDeleteNote() {
	var url = schema_url + "/alert/alert_delete_note.html";
	alertWindow(url);
};

// 弹出添加笔记本对话框
function alertAddBook() {
	var url = schema_url + "/alert/alert_notebook.html";
	alertWindow(url);
};
// 弹出添加笔记对话框
function alertAddNote() {
	// 如果没有选中的笔记本提示
	var $li = $("#book_ul a.checked").parent();
	if ($li.length == 0) {
		alert("请选择笔记本");
		return;
	}
	var url = schema_url + "/alert/alert_note.html";
	alertWindow(url);
};

// 关闭对话框
function closeAlertWindow() {
	$("#can").empty();
	$(".opacity_bg").hide();
};