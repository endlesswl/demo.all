/* [export] */

/**
 * 登录页面
 * 
 * @author LXQ
 */
Login = {
	// 初始化
	init : function() {
		var navBtn = $(".gr_nav").find("li");
		navBtn.click(function() {
			$(this).addClass('current').siblings().removeClass('current');
			var index = $(this).index();
			$("#loginBoxWrap .gr_login_box").hide();
			$('#loginBoxWrap .gr_login_box:eq(' + index + ')').show();
			$("#leftImgWrap img").hide();
			$('#leftImgWrap img:eq(' + index + ')').show();
		});
	},
	// 个人页面登录
	perLoginFun : function() {
		var form = $("#personalLogin");
		if (!this.check(form)) {
			return;
		}
		form.submit();
	},
	// 商家页面登录
	busiLoginFun : function() {
		var form = $("#busiLogin");
		if (!this.check(form)) {
			return;
		}
		form.submit();
	},
	// 代理商登录
	agentLoginFun : function() {
		var form = $("#agentLogin");
		if (!this.check(form)) {
			return;
		}
		form.submit();
	},
	check : function(form) {
		var f = true;

		var password = $(form).find("#password").val();
		if (password == "") {
			$(form).find("#password").parent().find(".tipinfo").html("请输入密码");
			$(form).find("#password").focus();
			f = false;
		} else {
			$(form).find("#password").parent().find(".tipinfo").html("");
		}

		var username = $(form).find("#username").val();
		if (username == "") {
			$(form).find("#username").parent().find(".tipinfo").html("请输入用户名");
			$(form).find("#username").focus();
			f = false;
		} else {
			$(form).find("#username").parent().find(".tipinfo").html("");
		}
		return f;
	},
	goRegister:function(url){
		var userType = $(".gr_nav").find(".current").attr("value");
		window.location.href=url+"&userType="+userType;
	},
	onblurFun:function(_this){
		if($(_this).val()!=""){
			$(_this).parent().find(".tipinfo").html("");
		}
	}
};
