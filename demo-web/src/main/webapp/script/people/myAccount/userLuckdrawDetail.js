/* [export] */

/**
 * 奖品详情
 */
var PrizeInfo = {
	info_url : '/home/userLuckdraw/searchMarketPrizeDetail',
	userLotteryId:'',
	ctx:'',
	init : function(ctx, userLotteryId) {
		this.ctx = ctx;
		this.userLotteryId = userLotteryId;
		this.query();
	},
	query : function() {
		var param = {
			userLotteryId : this.userLotteryId
		}
		var str = Util.ajaxPost(this.ctx + this.info_url, param);
		var data = Util.toJson(str);
		this.loadLottery(data);
	},
	loadLottery : function(data) {
		$("#issueNo").html("双色球" + data.issueNo + "期开奖号码");
		$("#money").html(data.money.toFixed(2) + "元");
		$("#createtime").html(data.createtime.substr(0,16));
		$("#prizetime").html(data.prizetime.substr(0,16));
		$("#ball").html(this.formatBall(data.ball));
		
		if(data.prizeMoney){
			$("#prizeMoney").html(data.prizeMoney.toFixed(2) + "元");
		}
		if(data.prizeMoneyAfterTax){
			$("#prizeMoneyAfterTax").html(data.prizeMoneyAfterTax.toFixed(2) + "元");
		}
		if(data.prizeball){
			$("#prizeball").html(this.formatPrizeBall(data.prizeball));
		}
		if(data.isprize == '未中奖'){
			$("#prizeMoneyAfterTax").html("未中奖");
			$("#prizeMoney").html("未中奖");
		}
	},
	// 格式化双色球
	formatBall : function(ball) {
		if (Util.isEmpty(ball)) {
			return;
		}
		
		var html = "<li class='hqon' style='width: 104px'>投注号码</li>";
		var arr = ball.split(",");
		for (var i = 0; i < arr.length - 1; i++) {
			html += '<li>' + arr[i] + '</li>';
		}

		var temp = arr[arr.length - 1].split(":");
		html += '<li>' + temp[0] + '</li>';
		html += '<li class="lq">' + temp[1] + '</li>';
		return html;
	},
	formatPrizeBall : function(prizeball) {
		if (Util.isEmpty(prizeball)) {
			return;
		}
		
		var html = "";
		var arr = prizeball.split(",");
		for (var i = 0; i < arr.length; i++) {
			if(i == arr.length - 1){
				html += '<li class="lq">' + arr[i] + '</li>';
			}else{
				html += '<li>' + arr[i] + '</li>';
			}
		}
		return html;
	}
}