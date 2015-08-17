/* [export] */

/**
 * 交易记录页面
 * 
 * @author LDL
 */
DealRecord = {
	url : '/home/userLuckdraw/searchMarketPrizePage',
	sBillTime : "",
	eBillTime : "",
	billret : "",
	pagerows : 10,
	pageNum : 1,
	ctx:'',
	init : function(ctx) {
		this.ctx = ctx;
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
			startTime : this.sBillTime,
			endTime : this.eBillTime
		};
		
		var str = Util.ajaxPost(this.ctx + this.url, param);
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
			var isprize = row.isprize;
			if(isprize == '0'){
				isprize = '未中奖';
			}else if(isprize == '1'){
				isprize = '中奖';
			}else{
				isprize = '等待开奖';
			}
			var dataTr = '<tr>' 
				+ '<td width="5%">' + (i + 1) + '</td>'
				+ '<td width="20%">双色球-' + row.issueNo + '期</td>'
				+ '<td width="10%" class="red" style="font-weight:bold">' + row.money + '元</td>'
				+ '<td width="20%">' + row.createTime.substr(0,16) + '</td>'
				+ '<td width="20%" class="green" style="font-weight:bold">' + row.prizetime.substr(0,16) + '</td>'
				+ '<td width="10%">' + isprize + '</td>'
				+ '<td width="15%">' + '<a href="'+this.ctx+'/home/userLuckdraw/toSearchMarketPrizeDetail?userLotteryId='+ row.id +'">详情</a>'; + '</td>'
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
