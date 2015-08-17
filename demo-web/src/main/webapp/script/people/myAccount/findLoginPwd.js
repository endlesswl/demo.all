/* [export] */

/**
 * 修改密码页面（登陆密码）
 * 
 * @author LDL
 */
FindLoginPwd = {
	findLogin_url : '/register/findPwd', // 找回登陆密码
	verify_url : '/register/findPwdCaptcha',// 验证码
	wait : 60,
	ctx : '',
	type:'',
	init : function(ctx, type) {
		this.ctx = ctx;
		this.type = type;
	},
	// 登陆密码找回
	findLoginPwd : function() {
		if ($("#username").val() == "") {
			Util.showErrorMessage("请输入用户名");
			return;
		}
		if ($("#verifyCode").val() == "") {
			Util.showErrorMessage("请输入邮箱验证码");
			return;
		}
		var param = {
			username : $("#username").val(),
			captcha : $("#verifyCode").val()
		};
		var str = Util.ajaxPost(this.ctx+this.findLogin_url, param);
		var data = Util.toJson(str);
		if (Util.isEmpty(data) || data.result == "false") {
			alert(data.message);
			return;
		}
		alert(data.message);
		window.history.back();
	},
	// 验证码（找回登陆密码）
	sendEmail : function(btn) {
		if ($("#username").val() == "") {
			Util.showErrorMessage("请输入用户名");
			return;
		}
		var url = this.ctx + this.verify_url + "?username=" + $("#username").val();
		Util.ajaxAsync(url, {}, "GET");
		this.timer(btn);
	},
	timer : function(btn) {
		if (FindLoginPwd.wait == 0) {
			btn.removeAttribute("disabled");
			btn.value = "获取邮箱校验码";
			FindLoginPwd.wait = 60;
		} else {
			btn.setAttribute("disabled", true);
			btn.value = "重新发送(" + FindLoginPwd.wait + ")";
			FindLoginPwd.wait--;
			setTimeout(function() {
				FindLoginPwd.timer(btn)
			}, 1000)
		}
	}
}
