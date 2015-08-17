/* [export] */

/**
 * 群组导入页面
 * 
 * @author LDL
 */
UserImport = {
	info_url:'',//初始化列表
	province:'',
	city:'',
	area:'',
	del_url:'',//删除url
	up_url:'',//修改分组
	new_url:'',//新建分组
	ctx:'',
	pageNum : 1,
	init : function(info_url,del_url,up_url,new_url,ctx) {
		this.info_url=info_url;
		this.del_url=del_url;
		this.up_url=up_url;
		this.new_url=new_url;
		this.ctx=ctx;
		//加载列表信息
		this.query();
	},
	// 查询
	query : function(pageNum) {
		if(Util.isEmpty(pageNum)){
    		this.pageNum=1;
    	}		
		var clustname = $("#clustname").val();//群组名称
		var url = this.info_url + "?clustname=" + clustname+"&page="+this.pageNum;;
		var str = Util.ajaxPost(url,{});
		var data = Util.toJson(str);
		
		//测试
		if (Util.isEmpty(data) || data.list.length == 0) {
			$('#pageDiv').hide();
			$("#dataList tr:gt(0)").remove();
			return;
		}else{
			$('#pageDiv').show();
		}
		UserImport.loadData(data.list);	
		
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
		this.query(pageNum);
	},
	// 加载页面TABLE
	loadData : function(rows) {
		if (Util.isEmpty(rows)) {
			return;
		}
		$("#dataList tr:gt(0)").remove();
		for (var i = 0; i <= rows.length-1; i++) {
			var dataTr ='<tr>'+
			'<td>'+(i+1)+'</td>'+
			'<td>'+rows[i].clustname+'</td>'+
			'<td>'+rows[i].createtime+'</td>'+
			'<td><ul class="opear-item clearfix icon-wrap" style="width:80px;"><li><a class="opear-item-update" title="修改" href="javascript:void(0);" onclick="UserImport.updateGroup('+rows[i].id+',\''+rows[i].clustname+'\')">修改</a></li><li><a class="opear-item-c" title="删除" href="javascript:void(0);" onclick="UserImport.delGroup('+rows[i].id+','+rows[i].clustno+')">删除</a></li></td>'+
			'</tr>';
			$("#dataList").append(dataTr);
		}
	} ,	
	//修改分组
	updateGroup:function(id,clustname){
		$('#updateClustname').val(clustname);
		//弹div
		$.dialog({
			title: '修改分组',
			content:$('#updateGroupDiv').html(),
			cancelValue: '取消',
			cancel:true,
			lock:true,
			okValue:'确定',
			ok: function(){
				// 保存
				if(Util.isEmpty($('#updateClustname').val())){
					$.dialog({
		   				title: '警告框',
		   				icon: 'warning',
		   				content: "请填写分组名称",
				     	okValue:"确定",
				     	ok:true,
				     	lock:true,
				     	time:3
		   			})
		   			return false;
				}else if($('#updateClustname').val().length>10){
					$.dialog({
		   				title: '警告框',
		   				icon: 'warning',
		   				content: "分组名称不能超过10个字",
				     	okValue:"确定",
				     	ok:true,
				     	lock:true,
				     	time:3
		   			})
		   			return false;
				}else{
					var param={
							clustname:$('#updateClustname').val(),//新名称
							id:id,
					};
					var str = Util.ajaxPost(UserImport.up_url, param);
					var data = Util.toJson(str);
					if (data.result == true) {
						alert(data.message);
						window.location.href=UserImport.ctx+'/location/business?path=business/user/userImport';
					} else {
						alert(data.message);
						return;
					}
				}
			}
		});		
	},
	//删除分组
	delGroup:function(id,clustno){
		// 请求后台删除
		if (confirm("确定要删除吗？")) {			
			var param={
					id:id,//列表id
					clustno:clustno,//分组编码
			};
			var str = Util.ajaxPost(this.del_url, param);
			var data = Util.toJson(str);
			if (data.result == true) {
				alert("删除成功");
				window.location.href=this.ctx+'/location/business?path=business/user/userImport';
			} else {
				alert(data.message);
				return;
			}			
		}else{
			return false;
		}
	},
	//确认
	save:function(){
		var result = Util.ajaxPost(UserImport.save_url,{});
	},
	//新建分组
	addGroup:function(){
		//弹div
		$.dialog({
			title: '新建分组',
			content:$('#addGroupDiv').html(),
			cancelValue: '取消',
			cancel:true,
			lock:true,
			okValue:'确定',
			ok: function(){
				// 保存
				if(Util.isEmpty($('#addclustname').val())){
					$.dialog({
		   				title: '警告框',
		   				icon: 'warning',
		   				content: "请填写分组名称",
				     	okValue:"确定",
				     	ok:true,
				     	lock:true,
				     	time:3
		   			})
		   			return false;
				}else if($('#addclustname').val().length>10){
					$.dialog({
		   				title: '警告框',
		   				icon: 'warning',
		   				content: "分组名称不能超过10个字",
				     	okValue:"确定",
				     	ok:true,
				     	lock:true,
				     	time:3
		   			})
		   			return false;
				}else{
					var param={
							clustname:$('#addclustname').val(),//新建分组名称				
					};
					var str = Util.ajaxPost(UserImport.new_url, param);
					var data = Util.toJson(str);
					if (data.result == true) {
						alert("保存成功");
						window.location.href=UserImport.ctx+'/location/business?path=business/user/userImport';
					} else {
						$.dialog({
			   				title: '警告框',
			   				icon: 'warning',
			   				content: data.message,
					     	okValue:"确定",
					     	ok:true,
					     	lock:true,
					     	time:3
			   			})
			   			return false;
					}
				}
			}
		});		
	},
}
