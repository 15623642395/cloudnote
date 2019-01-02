//预览分享笔记
function sureViewShareNote(){
	//获取请求参数
	var noteId = $(this).data("noteId");
	//发送Ajax请求
	$.ajax({
		url:schema_url+"/note/view.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				var share = result.data;
				//设置标题和内容
				$("#noput_note_title").html(
					share.cn_share_title);
				$("#noput_note_body").html(
					share.cn_share_body);
				//切换显示
				$("#pc_part_5").show();
				$("#pc_part_3").hide();
			}
		},
		error:function(){
			alert("加载笔记信息失败");
		}
	});
};
//搜索分享笔记操作
function sureSearchShareNote(event){
	var code = event.keyCode;//键值
	var keyword = $(this).val();
	if(code==13){//回车键
		//发送Ajax请求
		$.ajax({
			url:schema_url+"/note/search.do",
			type:"post",
			data:{"keyword":keyword},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					//显示搜索结果列表,其他列表隐藏
					showNoteList(6);
					//清除原有列表
					$("#share_ul li").remove();
					//获取搜索的分享笔记结果
					var shares = result.data;
					//循环生成列表元素
					for(var i=0;i<shares.length;i++){
						var title = shares[i].cn_share_title;
						var noteId = shares[i].cn_note_id;
						//拼一个li
						var sli='<li class="online">';
							sli+='	<a>';
							sli+='		<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
							sli+=title;
							sli+='		<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-star"></i></button>';
							sli+='	</a>';
							sli+='</li>';
						//绑定noteId
						var $li = $(sli);
						$li.data("noteId",noteId);
						//添加到ul中
						$("#share_ul").append($li);
					}
				}
			},
			error:function(){
				alert("搜索失败");
			}
		});
	}
};

//切换笔记列表显示区
function showNoteList(index){
	$("div.col-xs-3:not('#savebutton_div')").hide();
	$("#pc_part_"+index).show();
}

//分享笔记操作
function sureShareNote(){
	//获取请求参数
	var $li = $(this).parents("li");
	var noteId = $li.data("noteId");
	//发送Ajax请求
	$.ajax({
		url:schema_url+"/note/share.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(result){
			if(result.status==0||result.status==1){
				alert(result.msg);
			}
		},
		error:function(){
			alert("分享笔记失败");
		}
	});
};
//转移笔记操作
function sureMoveNote(){
	//获取请求参数
	var $bookli = $("#book_ul a.checked").parent();
	var old_bookId = $bookli.data("bookId");
	var new_bookId = $("#moveSelect").val();
	var $noteli = $("#note_ul a.checked").parent();
	var noteId = $noteli.data("noteId");
	//做检查
	if(new_bookId=="none"){
		$("#select_span").html("请选择笔记本");
	}else if(new_bookId==old_bookId){
		$("#select_span").html("与原笔记本相同");
	}else{
		//发送Ajax请求
		$.ajax({
			url:schema_url+"/note/move.do",
			type:"post",
			data:{"noteId":noteId,"bookId":new_bookId},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					closeAlertWindow();//关闭对话框
					$noteli.remove();//移除笔记li
					um.setContent("");
					$("#input_note_title").val("");
					alert(result.msg);//提示成功
				}
			},
			error:function(){
				alert("转移笔记失败");
			}
		});
	}
	
};
//更新笔记内容
function updateNote(){
	//获取请求参数
	var title = $("#input_note_title").val().trim();
	var body = um.getContent();
	var $li = $("#note_ul a.checked").parent();
	var noteId = $li.data("noteId");
	//检查数据格式
	if($li.length==0){
		alert("请选择要保存的笔记");
		return;
	}
	//TODO 检查title和body非空
	//发送Ajax请求
	$.ajax({
		url:schema_url+"/note/update.do",
		type:"post",
		data:{"noteId":noteId,
			   "noteTitle":title,
			   "noteBody":body},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//同步li的笔记标题
				var str ='<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
					str+=title;
					str+='<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
				$li.find("a").html(str);
				//提示成功
				alert(result.msg);
			}
		},
		error:function(){
			alert("保存笔记失败");
		}
	});
};
//根据笔记ID加载笔记内容
function loadNoteDetail(){
	//将点击的笔记设置成选中
	$("#note_ul a").removeClass("checked");
	$(this).find("a").addClass("checked");
	//获取请求参数
	var noteId = $(this).data("noteId");
	//发送ajax请求
	$.ajax({
		url:schema_url+"/note/loaddetail.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				var note = result.data;
				var title = note.cn_note_title;
				var body = note.cn_note_body;
				//设置标题和内容
				$("#input_note_title").val(title);
				um.setContent(body);
				//切换显示
				$("#pc_part_5").hide();
				$("#pc_part_3").show();
			}
		},
		error:function(){
			alert("加载笔记内容失败");
		}
	});
};
//根据笔记本ID加载笔记列表
function loadBookNotes(){
	//切换列表显示,全部笔记列表显示,其他隐藏
	showNoteList(2);
	//将笔记本li设置成选中
	$("#book_ul a").removeClass("checked");
	$(this).find("a").addClass("checked");
	//获取请求参数
	var bookId = $(this).data("bookId");
	//发送ajax请求
	$.ajax({
		url:schema_url+"/note/loadnotes.do",
		type:"post",
		data:{"bookId":bookId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//清除原有笔记列表
				//$("#note_ul").empty();
				$("#note_ul li").remove();
				//循环生成笔记li
				var notes = result.data;
				for(var i=0;i<notes.length;i++){
					var noteTitle = notes[i].cn_note_title;
					var noteId = notes[i].cn_note_id;
					createNoteLi(noteId,noteTitle);
				}
			}
		},
		error:function(){
			alert("加载笔记列表失败");
		}
	});
};

//创建笔记Li添加到列表
function createNoteLi(noteId,noteTitle){
	var sli='<li class="online">';
		sli+='	<a>';
		sli+='		<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
		sli+=noteTitle;
		sli+='		<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
		sli+='	</a>';
		sli+='	<div class="note_menu" tabindex="-1">';
		sli+='		<dl>';
		sli+='			<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>';
		sli+='			<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>';
		sli+='			<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>';
		sli+='		</dl>';
		sli+='	</div>';
		sli+='</li>';
	var $li = $(sli);
	$li.data("noteId",noteId);
	$("#note_ul").append($li);
};

//确认创建笔记
function sureAddNote(){
	//获取请求参数
	var title = $("#input_note").val().trim();
	var $li = $("#book_ul a.checked").parent();
	var bookId = $li.data("bookId");
	//TODO 格式检查
	//发送Ajax
	$.ajax({
		url:schema_url+"/note/add.do",
		type:"post",
		data:{"userId":userId,
				"bookId":bookId,
				"noteTitle":title},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//关闭对话框
				closeAlertWindow();
				//追加笔记li
				var noteId = result.data;
				createNoteLi(noteId,title);
				$("#note_ul li:last").click();
				//弹出成功提示
				alert(result.msg);
			}
		},
		error:function(){
			alert("创建笔记失败");
		}
	});
};

//笔记菜单控制
function showNoteMenu(){
	//点击下拉按钮显示菜单
	$("#note_ul").on("click",".btn_slide_down",function(){
		//隐藏所有li的菜单
		$("#note_ul .note_menu").hide();
		//将点击的li的菜单显示
		var $li = $(this).parents("li");
		var $menu = $li.find(".note_menu");
		$menu.slideDown(1000);
		//将当前笔记li设置为选中
		$("#note_ul a").removeClass("checked");
		$li.find("a").addClass("checked");
		//阻止冒泡
		return false;
	});
	//点击页面其他位置隐藏菜单
	$("body").click(function(){
		$("#note_ul .note_menu").hide();
	});
	//当鼠标移开菜单时隐藏菜单
	$("#note_ul").on("mouseout",".note_menu",function(){
		$(this).hide();
	});
	//当鼠标移上菜单时保持显示
   $("#note_ul").on("mouseover",".note_menu",function(){
		$(this).show();
	});
};

//确认删除笔记操作
function sureDeleteNote(){
	//获取请求参数
	var $li = $("#note_ul a.checked").parent();
	var noteId = $li.data("noteId");
	//发送Ajax请求
	$.ajax({
		url:schema_url+"/note/delete.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//删除笔记li
				$li.remove();
				//清空笔记内容编辑区
				um.setContent("");
				$("#input_note_title").val("");
				//提示成功
				alert(result.msg);
			}
		},
		error:function(){
			alert("删除笔记失败");
		}
	});
};



