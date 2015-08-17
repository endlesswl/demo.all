/* [export] */

/**
 * 用户列表页面
 * 
 * @author LDL
 */
UserList = {
	url : '',
	tableName : 'tableInit',
	userIdStr:'',
	add_url:'',//添加用户url
	g_url:'',//初始化分组信息
	send_url:'',
	pageNum : 1,
	init : function(url,add_url,g_url,send_url) {
		this.add_url=add_url;
		this.url = url;
		this.g_url=g_url;
		this.userIdStr='';
		this.send_url=send_url;
		//初始化列表信息
		this.query();		
		//全选效果
		$("#ckboxAll").click(function() {
			$("input[name='ckbox']").prop("checked", this.checked);
		});
		this.initCheckBox();
		//初始化列表信息
		var str = Util.ajaxPost(g_url,{});
		var data = Util.toJson(str);
		$('.user-group').find('li:eq(0)').html('全部用户（'+data.allUserCount+'）');//所有人数
		
		var checkbox='';
		var optionObj='';
		//初始化各分组信息
		for(var i=0;i<data.list.length;i++){
			var liObj='<li id="'+data.list[i].clustno+'">'+data.list[i].clustname+'（'+data.list[i].count+'）</li>';
			$('.user-group').append(liObj);
			if(i==0){
				checkbox+='<li><input type="checkbox" name="groupCkbox" value="'+data.list[i].clustno+'">'+data.list[i].clustname+'</li>';
			}else{
				checkbox+='<li><input type="checkbox" name="groupCkbox" value="'+data.list[i].clustno+'">'+data.list[i].clustname+'</li>';
			}
			optionObj+='<option value="'+data.list[i].clustno+'">'+data.list[i].clustname+'</option>';
		}
		$('#usergroup').append(optionObj);
		$('#userGroupDiv').append('<ul id="chooseWrap">'+checkbox+'</ul>');
	},
	//复选框事件
	initCheckBox:function(){	
		$('#ckboxAll').prop('checked',false);
		var checkedSize=$('#dataList').find('input[name=ckbox]').size();
		$('#dataList').find('input[name=ckbox]').each(function(){
			$(this).click(function(){
				var newCheckedSize=$('#dataList').find('input[name=ckbox]:checked').size();					
				if(newCheckedSize==checkedSize){
					$('#ckboxAll').prop('checked',true);
				}else{
					$('#ckboxAll').prop('checked',false);
				}					
			});
		});
	},
	// 查询
	query : function(time,pageNum) {		
		if(Util.isEmpty(pageNum)){
    		this.pageNum=1;
    	}
		var userName = $("#userName").val();//用户昵称
		var marketName = $("#marketName").val();//活动名称
		var clustno = $("#usergroup option:selected").val();//所在群组编码
		var url='';
		if (Util.isEmpty(time)) {
			$('.ui-button-on').attr("class", "ui-button");
			url = UserList.url + "?userName=" + userName + "&marketname=" + marketName+"&clustno="+clustno+"&page="+this.pageNum;
		}else{			
			url = UserList.url + "?time=" + time+"&page="+this.pageNum ;
		}
		var str = Util.ajaxPost(url,{});
		var data = Util.toJson(str);
		
		$("#dataList tr:gt(0)").remove();
		//测试
		if (Util.isEmpty(data) || data.list.length == 0) {	
			$('#pageDiv').hide();
			return;
		}else{
			$('#pageDiv').show();
		}
		UserList.loadData(data.list);
		
		this.initCheckBox();
		 // 分页插件，要实现queryPage方法查询
		PageLc.pageId = 'pageDiv';
		PageLc.pageNum = data.pageNum;
		PageLc.totalPage = data.pages;
		PageLc.totalRow = data.total;
		PageLc.init(this);
	},
	 // 翻页
	queryPage : function(pageNum) {
		this.pageNum = pageNum;
		this.query('',pageNum);
		this.initCheckBox();
	},
	// 加载页面TABLE
	loadData : function(rows) {
		if (Util.isEmpty(rows)) {
			return;
		}
		$("#dataList tr:gt(0)").remove();
		for (var i = 0; i <= rows.length-1; i++) {
			var liveness='';
			if(Util.isEmpty(rows[i].liveness)){
				liveness=0;
			}else{
				liveness=rows[i].liveness;
			}
			var dataTr ='<tr>'+
			'<td><input type="checkbox" name="ckbox" value="'+rows[i].userid+'" uname="'+rows[i].nickname+'"></td>'+
			'<td>'+(i+1)+'</td>'+
			'<td>'+rows[i].nickname+'</td>'+
			'<td>'+rows[i].mobile+'</td>'+
			'<td>'+rows[i].city+'</td>'+
			'<td>'+liveness+'</td>'+
			'</tr>';
			$("#dataList").append(dataTr);
		}		
	} ,	
	//添加用户到对应的分组
	addToGroup:function(){		
		this.userIdStr='';
		//处理数据
		$('#dataList').find('input[name=ckbox]:checked').each(function(){
			UserList.userIdStr+=$(this).attr('value')+",";
		});				
		if(Util.isEmpty(this.userIdStr)){
			alert('请选择用户！');
			return;
		}
		var groupsize=$('#userGroupDiv').find('input[name=groupCkbox]').size();
		if(groupsize==0){
			alert('您还没有建立分组！可以到群组管理建立自己的分组！');
			return;
		}
		$.dialog({
			title: '添加到分组',
			content:$('#userGroupDiv').html(),
			cancelValue: '取消',
			cancel:true,
			lock:true,
			okValue:'确定',
			ok: function(){						
				var clustno=$("input[name='groupCkbox']:checked").val();//目标分组编码
				if(Util.isEmpty(clustno)){
					$.dialog({
		   				title: '警告框',
		   				icon: 'warning',
		   				content: "请选择分组",
				     	okValue:"确定",
				     	ok:true,
				     	lock:true,
				     	time:3
		   			})
		   			return false;
				}
				// 保存
				var param={
						userIdStr:UserList.userIdStr,//要添加分组的用户id集合
						clustno:clustno,//目标分组
				};
				var str = Util.ajaxPost(UserList.add_url, param);
				var data = Util.toJson(str);
				if (data.result) {					
					$("input[name='ckbox']").prop("checked", false);
					alert(data.message);
				} else {
					alert(data.message);
					return;
				}
			}
		});
		
	},	
	checkBoxClick:function(){
		var divObj=$('.aui_content').find('#chooseWrap').find('li');
		divObj.each(function(){
			$(this).click(function(){
				$('input[name=groupCkbox]').prop('checked',false);
				if($(this).find('input').prop("checked")){
					$(this).find('input').prop("checked",false);
				}else{
					$(this).find('input').prop("checked",true);
				}
			})
		})
	},
}

