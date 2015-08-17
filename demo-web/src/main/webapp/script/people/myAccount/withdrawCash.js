/* [export] */
/**
 * 提现
 * @author h8wangjiabao
 */
withdrawCash = {
	userbanklist_url:'/home/bank/list',
	withdraw_url : '/home/withdraw/do',
	sendSms_url : '/captcha/sms?type=WITHDRAW_WLB&mobile=',
	bankid:'',
	ctx:'',
	amount:0,
	mobile:'',
	wait: 60,
	isSending : true,
	init :function(ctx, mobile) {
		this.ctx = ctx;
		this.mobile = mobile;
		this.getUserBankData();
		
		var result = Util.ajaxGet(this.ctx + "/home/user/getUserAccountDetail", {});
		var resultDate = Util.toJson(result);
		this.amount = resultDate.amount;
		$("#amount").html(resultDate.amount);
	},
	doWithdraw : function() {
		var result = this.check();
		if (result == "F") {
			return false;
		}
		var param = {
			bankid : this.bankid,
			amount : parseFloat($("#money").val()),//提现金额
			captcha:$("#captchaInfo").val()//验证码
		}
		var str = Util.ajaxPost(this.ctx + this.withdraw_url, param);
		var data = Util.toJson(str);
		if (data.result == "true") {
			alert(data.message);
			window.location.href = this.ctx+ '/location/user?path=person/myAccount/MyAccount';
			return;
		}
		Util.showErrorMessage(data.message);
	},
	getUserBankData:function(){
		var str = Util.ajaxPost(this.ctx + this.userbanklist_url, {});
		var data = Util.toJson(str);
		if (Util.isEmpty(data)) {
			return;
		}
		var bankList = data.banks;
		var content = "";
		for (var i = 0; i < bankList.length; i++) {
			if(i == 0){
				this.bankid = bankList[i].id;
				content += "<div class='withdrawal-blank on' onclick=\"withdrawCash.changeBank(this, '"+bankList[i].id+"');\">";
			}else{
				content += "<div class='withdrawal-blank' onclick=\"withdrawCash.changeBank(this, '"+bankList[i].id+"');\">";
			}
			content += "        <div class='card-bd " + bankList[i].abbreviation + "'>";
			content += "            <font class='black f16 fb pr10'>（卡号：" + bankList[i].cardno + "）</font>";
			content += "        </div>";
			content += "    </div>";
		}
		$("#bankList").html(content);
	},
	changeBank:function(div, id){
		$(".withdrawal-blank").removeClass("on");
		$(div).addClass("on");
		this.bankid = id;
	},
	check : function() {
		var money = $("#money").val();
		if (money == "") {
			Util.showErrorMessage("请输入提现金额！");
			return "F";
		}
		if(!Util.isNewNumber(money)){
			Util.showErrorMessage("请输入正确的提现金额！");
			return "F";
		}
		if (parseFloat(money) > parseFloat(this.amount)) {
			Util.showErrorMessage("提现金额不能大于余额");
			return "F";
		}
		if (parseFloat(money) <= 1) {
			Util.showErrorMessage("提现金额必须大于1元！");
			return "F";
		}
		
		var captchaInfo = $("#captchaInfo").val();
		if (captchaInfo == "") {
			Util.showErrorMessage("请输入验证码");
			return "F";
		}
		if (Util.strlen(captchaInfo) != 6 || !Util.isNumber(captchaInfo)){
			Util.showErrorMessage("请输入正确的验证码！");
			return "F";
		}
		return "T";
	},
	// 发送验证码
	sendVerifyCode : function(btn) {
		if (this.mobile == "") {
			Util.showErrorMessage("请输入手机号");
			return false;
		}
		if (!Util.isMobile(this.mobile)){
			Util.showErrorMessage("请输入正确的手机号！");
			return false;
		}
		if (this.isSending) {
			Util.ajaxAsync(this.ctx + this.sendSms_url + this.mobile, {}, "GET");
			this.timer(btn);
		}
	},
	timer : function(btn) {
		if (this.wait == 0) {
			$(btn).val("获取验证码");
			this.wait = 60;
			this.isSending = true;
		} else {
			this.isSending = false;
			$(btn).val("重新发送(" + this.wait + ")");
			this.wait--;
			setTimeout(function() {
				withdrawCash.timer(btn);
			}, 1000)
		}
	}
}