/* [export] */

/**
 * 客户维护提醒页面
 * 
 * @author LDL
 */
UserNotify = {
	url : '',
	sendUrl : '',
	searchTime : "1",
	currTable : 'tableInit',
	pageNum : 1,
	pageSize : 10,
	totalPage:1,
	init : function(url, sendUrl) {
		UserNotify.url = url;
		UserNotify.query();
		// 发送地址
		UserNotify.sendUrl = sendUrl;
	},
	// 设置查询条件
	setQuery : function(searchTime, pageNum) {
		if (Util.isNotEmpty(searchTime)) {
			UserNotify.searchTime = searchTime;
		}
		if (Util.isNotEmpty(pageNum)) {
			UserNotify.pageNum = pageNum;
		}
		
		UserNotify.query(pageNum);
	},
	//分页插件实现方法
	queryPage : function(pageNum) {
		UserNotify.setQuery('',pageNum);
	},
	// 查询
	query : function(pageNum) {
		var param = {
				pageNum : UserNotify.pageNum,
				pageSize : UserNotify.pageSize,
				searchTime : UserNotify.searchTime
		};
		
		var str = Util.ajaxPost(UserNotify.url, param);
		var data = Util.toJson(str);
		//测试
		//var str =  '{"endRow":0,"list":[{"id":18,"name":"美国","site":"002","username":"15801469625'+UserNotify.pageNum+'","remark":"备注"},{"id":19,"name":"美国","site":"002","username":"13423423423","remark":"备注"},{"id":156,"name":"美国","site":"002","username":"18201125845","remark":"备注"}],"pageNum":'+UserNotify.pageNum+',"pageSize":10,"pages":14,"startRow":0,"total":3}';// 测试
		if (Util.isEmpty(data) || data.list.length == 0) {
			return;
		}
		
		UserNotify.loadData(data.list);

		//分页插件，要实现queryPage方法查询
		PageLc.pageId = 'pageDiv';
		PageLc.pageNum = data.pageNum;
		PageLc.pageSize = data.pageSize;
		PageLc.totalPage = data.pages;
		PageLc.totalRow = data.total;
		PageLc.init(UserNotify);
	},
	// 加载页面TABLE
	loadData : function(rows) {
		if (Util.isEmpty(rows)) {
			return;
		}
		$("#dataList tr:gt(0)").remove();
		for (var i = 0; i <= rows.length-1; i++) {
			var dataTr ='<tr>'+
			'<td><input type="checkbox" name="ckbox" value="'+rows[i].id+'"></td>'+
			'<td class="f16 blue">'+(i+1)+'</td>'+
			'<td>'+rows[i].username+'</td>'+
			'<td>'+rows[i].name+'</td>'+
			'<td>'+rows[i].remark+'</td>'+
			'</tr>';
			$("#dataList").append(dataTr);
		}
		
		$("#ckboxAll").removeAttr("checked");
	},
	//发送短信
	sendMessage : function() {
		var mobileArr = [];
		$("input[name='ckbox']").each(function() {
			if (this.checked) {
				mobileArr.push(this.value);
			}
		})
		if (mobileArr.length == 0) {
			alert("您还没选择用户");
			return;
		}

		var mobiles = mobileArr.join(",");
		var url = UserNotify.sendUrl + "?userIds=" + mobiles;

		var str = Util.ajaxPost(url, {});
		var data = Util.toJson(str);
		if (data.result == "success") {
			alert("发送成功");
		}
	}
}

