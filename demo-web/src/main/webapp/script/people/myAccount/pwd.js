/* [export] */

/**
 * 修改密码页面（登陆密码、支付密码）
 * 
 * @author LDL
 */
Pwd = {
	login_url : '/home/user/modifyScrit', // 修改登陆密码
	pay_url : '/home/user/modifyPayScrit',// 修改支付密码
	findPay_url : '/home/user/findPayPwd',// 找回支付密码
	verify_url : '/captcha/email',// 验证码
	setPay_url : '/home/user/designPayPwd',
	old_pwd : '',
	new_pwd : '',
	new_pwd2 : '',
	wait : 60,
	returnUrl : '',
	init : function(ctx, type, email, returnUrl) {
		this.login_url = ctx + this.login_url;
		this.pay_url = ctx + this.pay_url;
		this.pay_url = ctx + this.pay_url;
		this.findPay_url = ctx + this.findPay_url;
		this.verify_url = ctx + this.verify_url;
		this.setPay_url = ctx + this.setPay_url;
		this.returnUrl = returnUrl;

		this.verify_url += "?type=" + type + "&email=" + email;
	},
	// 登录密码修改
	updateLoginPwd : function(url) {
		if (!this.updatePwdValidater()) {
			return;
		}
		Pwd.checkPwd();
		var param = {
			pwd : Pwd.old_pwd,
			newPwd1 : Pwd.new_pwd,
			newPwd2 : Pwd.new_pwd2,
			captcha : $("#verifyCode").val()
		};
		var str = Util.ajaxPost(Pwd.login_url, param);
		var data = Util.toJson(str);
		if (Util.isEmpty(data) || data.result == "false") {
			alert(data.message);
			return;
		}

		alert("修改成功");
		if (this.returnUrl != "") {
			window.location.href = this.returnUrl;
		} else {
			window.history.back();
		}
	},
	// 支付密码设置
	setPayPwd : function() {
		this.setPwdValidater();
		if (!$("#setPwdform").valid()) {
			return;
		}
		// var r = Pwd.passValidater();
		// if (r == "F") {
		// return;
		// }
		Pwd.checkPwd();
		var param = {
			payPwd1 : Pwd.new_pwd,
			payPwd2 : Pwd.new_pwd2,
			captcha : $("#verifyCode").val()
		};
		var str = Util.ajaxPost(Pwd.setPay_url, param);
		var data = Util.toJson(str);
		if (Util.isEmpty(data) || data.result == "false") {
			alert(data.message);
			return;
		}
		alert(data.message);
		if (this.returnUrl != "") {
			window.location.href = this.returnUrl;
		} else {
			window.history.back();
		}
	},
	// 支付密码修改
	updatePayPwd : function() {
		if (!this.updatePwdValidater()) {
			return;
		}
		// var r = Pwd.checkPwd();
		// if (r == "F") {
		// return;
		// }
		Pwd.checkPwd();
		var param = {
			payPwd : Pwd.old_pwd,
			newPayPwd1 : Pwd.new_pwd,
			newPayPwd2 : Pwd.new_pwd2,
			captcha : $("#verifyCode").val()
		};
		var str = Util.ajaxPost(Pwd.pay_url, param);
		var data = Util.toJson(str);
		if (Util.isEmpty(data) || data.result == "false") {
			alert(data.message);
			return;
		}

		alert("修改成功");
		if (this.returnUrl != "") {
			window.location.href = this.returnUrl;
		} else {
			window.history.back();
		}
	},
	// 支付密码找回
	findPayPwd : function() {
		// var r = Pwd.checkPwd();
		// if (r == "F") {
		// return;
		// }
		Pwd.checkPwd();
		if (!this.setPwdValidater()) {
			return;
		}
		var param = {
			payPwd1 : Pwd.new_pwd,
			payPwd2 : Pwd.new_pwd2,
			captcha : $("#verifyCode").val()
		};
		var str = Util.ajaxPost(Pwd.findPay_url, param);
		var data = Util.toJson(str);
		if (Util.isEmpty(data) || data.result == "false") {
			alert(data.message);
			return;
		}

		alert("修改成功");
		if (this.returnUrl != "") {
			window.location.href = this.returnUrl;
		} else {
			window.history.back();
		}
	},
	checkPwd : function() {
		Pwd.old_pwd = $("#old_pwd").val();
		Pwd.new_pwd = $("#new_pwd").val();
		Pwd.new_pwd2 = $("#new_pwd2").val();

		// if (Pwd.old_pwd != undefined && Pwd.old_pwd == "") {
		// Util.showErrorMessage("旧密码不能为空");
		// return "F";
		// }
		// if (Pwd.new_pwd == "") {
		// Util.showErrorMessage("新密码不能为空");
		// return "F";
		// }
		// if (Pwd.new_pwd2 == "") {
		// Util.showErrorMessage("请输入确认密码");
		// return "F";
		// }
		// if (Pwd.new_pwd != Pwd.new_pwd2) {
		// Util.showErrorMessage("两次输入的密码不一致");
		// return "F";
		// }
		// return "T";
	},
	sendEmail : function(btn) {
		Util.ajaxAsync(this.verify_url, {}, "GET");
		this.timer(btn);
	},
	timer : function(btn) {
		if (Pwd.wait == 0) {
			btn.removeAttribute("disabled");
			btn.value = "获取邮箱校验码";
			Pwd.wait = 60;
		} else {
			btn.setAttribute("disabled", true);
			btn.value = "重新发送(" + Pwd.wait + ")";
			Pwd.wait--;
			setTimeout(function() {
				Pwd.timer(btn)
			}, 1000)
		}
	},
	// 表单校验
	updatePwdValidater : function() {
		var fvoid = $("#updatePwdform").validate({
			onkeyup : false,// 是否在敲击键盘时验证
			submitHandler : function(form) { // 通过之后回调
				// 不提交
			},
			// 设置验证规则
			rules : {
				old_pwd : {
					required : true,
					rangelength : [ 6, 12 ]
				},
				new_pwd : {
					required : true,
					rangelength : [ 6, 12 ]
				},
				new_pwd2 : {
					required : true,
					rangelength : [ 6, 12 ],
					equalTo : "#new_pwd"
				},
				verifyCode : {
					required : true,
					digits : true
				}
			},
			// 设置错误信息
			messages : {
				old_pwd : {
					required : "请输入旧密码",
					rangelength : "密码长度为6-12位",
				},
				new_pwd : {
					required : "请输入新密码",
					rangelength : "密码长度为6-12位"
				},
				new_pwd2 : {
					required : "请再次输入密码",
					rangelength : "密码长度为6-12位",
					equalTo : "两次输入密码不相同"
				},
				verifyCode : {
					required : "请输入接收到的验证码",
					digits : "只能输入数字"
				},
			},
			errorElement : "em",
			errorPlacement : function(error, element) {
				error.appendTo(element.parent().find(".tipinfo"));
			},
			success : "valid"
		});
		return fvoid.form();
	},
	// 设置交易密码表单校验
	setPwdValidater : function() {
		var fvoid = $("#setPwdform").validate({
			onkeyup : false,// 是否在敲击键盘时验证
			submitHandler : function(form) { // 通过之后回调
				// 不提交
			},
			// 设置验证规则
			rules : {
				new_pwd : {
					required : true,
					rangelength : [ 6, 12 ]
				},
				new_pwd2 : {
					required : true,
					rangelength : [ 6, 12 ],
					equalTo : "#new_pwd"
				},
				verifyCode : {
					required : true,
					digits : true
				}
			},
			// 设置错误信息
			messages : {
				new_pwd : {
					required : "请输入新密码",
					rangelength : "密码长度为6-12位"
				},
				new_pwd2 : {
					required : "请再次输入密码",
					rangelength : "密码长度为6-12位",
					equalTo : "两次输入密码不相同"
				},
				verifyCode : {
					required : "请输入接收到的验证码",
					digits : "只能输入数字"
				},
			},
			errorElement : "em",
			errorPlacement : function(error, element) {
				error.appendTo(element.parent().find(".tipinfo"));
			},
			success : "valid"
		});
		return fvoid.form();
	},
}
