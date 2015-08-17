/* [export] */

/**
 * 个人中心 个人基本信息修改
 * 
 * @author 
 */
userInfo = {
	updateUserInfo_url:'/home/user/updateUserInfo',
	bankList_url:'/dictionary/bankList',
	sendSms_url : '/captcha/sms?type=BIND_MOBILE&mobile=',
	ctx:'',
	wait: 60,
	isSending : true,
	mobile:'',
	init : function(ctx, mobile) {	
		this.ctx = ctx;
		this.mobile = mobile;
		this.getBankData();
	},
	getBankData:function(){
		var str = Util.ajaxPost(this.ctx + this.bankList_url, {});
		var data = Util.toJson(str);
		if (Util.isEmpty(data)) {
			return;
		}
		var content = "<option selected='selected' value=''>请选择银行</option>";
		var banklist = data.list;
		for (var i = 0; i < banklist.length; i++) {
			content += "<option value="+banklist[i].code+">"+banklist[i].name+"</option>";
		}
		$("#bankCode").html(content);
	},
	//保存修改用户信息
	updateUserInfo:function(){
		var result = this.check();
		if (result == "F") {
			return false;
		}
		var param = {
			realName:$("#realName").val(),//真实姓名
			cardNo:$("#cardNo").val(), //身份证号
			bankname:$("#bankCode").find("option:selected").text(),//开户行全称
			cardno:$("#cardno").val(),//卡号
			abbreviation:$("#bankCode").val(),//开户行简写
			captcha:$("#captchaInfo").val()//验证码
		};
		var r = Util.ajaxPost(this.ctx + this.updateUserInfo_url, param);
		var data = Util.toJson(r);
		if (data.result == "true") {
			alert(data.message);
			window.location.href = this.ctx + '/home/user/info2';
			return;
		}
		Util.showErrorMessage(data.message);
	},
	check : function() {
		var realName = $("#realName").val();
		var cardNo = $("#cardNo").val();
		if (realName == "") {
			Util.showErrorMessage("请输入真实姓名！");
			return "F";
		} 
		if (!/^[\u4e00-\u9fa5]{2,10}$/.test(realName)) {
			Util.showErrorMessage("请输入正确的真实姓名！");
			return "F";
		}
		if (cardNo == "") {
			Util.showErrorMessage("请输入身份证号！");
			return "F";
		}
		if (!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(cardNo)) {
			Util.showErrorMessage("请输入正确的身份证号！");
			return "F";
		}
		
		var bankname = $("#bankCode").find("option:selected").text();
		var cardno = $("#cardno").val();
		var abbreviation = $("#bankCode").val();
		if (abbreviation == "") {
			Util.showErrorMessage("请选择银行");
			return "F";
		}
//		if(this.accountname == ""||Util.strlen(this.accountname)<4||Util.strlen(this.accountname)>8||!/[\u4E00-\u9FA5\uF900-\uFA2D]/.test(this.accountname)){
//			Util.showErrorMessage("请正确输入2到4位持卡人姓名");
//			return "F";
//		}
		if (cardno == "") {
			Util.showErrorMessage("请输入银行卡号！");
			return "F";
		}
		if (!/^(\d{16}|\d{19})$/.test(cardno)) {
			Util.showErrorMessage("请输入正确的银行卡号！");
			return "F";
		}

		/*var captcha=$("#captcha").val();
		if(Util.strlen(captcha)!=6||captcha==""||!Util.isNumber(captcha)){
			Util.showErrorMessage("请正确输入6位数字验证码");
			return "F";
		}*/
		/*
		if($("#payPwd").val()==""){
			Util.showErrorMessage("请输入交易密码！");
			return "F";
		}
		if(!/^[A-Za-z0-9_]{6,20}$/.test($("#payPwd").val())){
			Util.showErrorMessage("请输入正确的交易密码！");
			return "F";
		}
		*/
		
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
				userInfo.timer(btn);
			}, 1000)
		}
	},
	setRealName : function(){
		$("#ckr").html($("#realName").val());
	}
}
 