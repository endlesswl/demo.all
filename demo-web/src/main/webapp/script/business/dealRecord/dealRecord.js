/* [export] */

/**
 * 交易记录页面
 * 
 * @author LDL
 */
DealRecord = {
	url : '',
	sBillTime : "",
	eBillTime : "",
	billret : "",
	pagerows : 10,
	pageNum : 1,
	billchannel : '6',
	init : function(url) {
		this.url = url;
		this.query(1);
	},
	changeTab : function(type) {
		// 切换页签
		this.billchannel = type;
		this.query(1);
	},
	setQuery : function(dateLabel, status) {
		// 设置查询条件
		this.sBillTime = $("#sBillTime").val();
		this.eBillTime = $("#eBillTime").val();
		
		if (Util.isNotEmpty(dateLabel)) {
			this.convertTime(dateLabel);
		}
		if (Util.isNotEmpty(status) && status != "-1") {
			this.billret = status;
		} else {
			this.billret = "";
		}
		
		this.query(1);
	},
	query : function(pageNum) {
		// 查询
		var param = {
			page : pageNum,
			rows : this.pagerows,
			billchannel : this.billchannel,
			sBillTime : this.sBillTime,
			eBillTime : this.eBillTime,
			billret : this.billret
		};
		
		var str = Util.ajaxPost(this.url, param);
		var data = Util.toJson(str);
		
		this.pageNum = data.pageNum;
		
		//分页插件，要实现queryPage方法查询
		PageLc.pageId = 'pageDiv';
		PageLc.pageNum = data.pageNum;
		PageLc.totalPage = data.pages;
		PageLc.totalRow = data.total;
		PageLc.init(this);
		
		this.loadData(data);
	},
	loadData : function(data) {
		$("#dataList tr:gt(0)").remove();
		if (Util.isEmpty(data) || data.length == 0) {
			return;
		}
		var rows = data.list;
		
		for (var i = 0; i < rows.length; i++) {
			var row = rows[i];
			
			var add = row.amountAdd;
			if(add=="0.00" || add==""){
				add="";
			}else{
				add="+"+add;
			}
			var amountMun = row.amountMun;
			if(amountMun=="0.00" || amountMun==""){
				amountMun=""; 
			}else{
				amountMun="-"+amountMun;
			}
			var dataTr = '<tr>' 
				+ '<td width="5%">' + (i + 1) + '</td>'
				+ '<td class="f14" width="25%">' + row.createtime + '</td>'
				+ '<td width="15%">' + row.billchannelStr + '</td>'
				+ '<td class="red" style="font-weight:bold" width="10%">' + add + '</td>'
				+ '<td class="green" width="10%" style="font-weight:bold">' +  amountMun + '</td>'
				//+ '<td>' + nullToZero(row.afterbalance) + '</td>'
				+ '<td width="10%">' + row.billretStr + '</td>'
				+ '<td width="25%">' + row.subject + '</td>'
				+ '</tr>';
			$("#dataList").append(dataTr);
		}
	},
	queryPage : function(pageNum) {
		this.query(pageNum);
	}, 
	//转换时间标签
	convertTime :function(dateLabel){
		var dateFormat = new DateFormat(false);
		if(dateLabel == 'today'){
	        this.sBillTime = dateFormat.format(new Date(),dateFormat.DEFAULT_DATE_FORMAT) ;
	        this.eBillTime = dateFormat.format(new Date(),dateFormat.DEFAULT_DATE_FORMAT);
	    }
	    // 一个月
	    if(dateLabel == 'oneMonth'){
	        this.sBillTime = dateFormat.changeDate(new Date(),"Month",-1,null,dateFormat.DEFAULT_DATE_FORMAT);
	        this.eBillTime = dateFormat.format(new Date(),dateFormat.DEFAULT_DATE_FORMAT);
	    }
	    // 三个月
	    if(dateLabel == 'threeMonth'){
	        this.sBillTime = dateFormat.changeDate(new Date(),"Month",-3,null,dateFormat.DEFAULT_DATE_FORMAT);
	        this.eBillTime = dateFormat.format(new Date(),dateFormat.DEFAULT_DATE_FORMAT);
	    }
	    // 一年
	    if(dateLabel == 'oneYear'){
	        this.sBillTime = dateFormat.changeDate(new Date(),"Year",-1,null,dateFormat.DEFAULT_DATE_FORMAT);
	        this.eBillTime = dateFormat.format(new Date(),dateFormat.DEFAULT_DATE_FORMAT);
	    }
	    $("#sBillTime").val(this.sBillTime);
		$("#eBillTime").val(this.eBillTime);
	}
};


function nullToZero(str) {
	if (Util.isEmpty(str) || "null" == str) {
		return "0.00";
	}
	return str;
}
