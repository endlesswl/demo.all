/* [export] */

/**
 * 商户基本资料页面
 * 
 * @author LDL
 */
UserInfo = {
	info_url : '',
	pwd_url : '',
	pay_url : '',
	save_url : '',
	init : function(info_url, pwd_url, pay_url,save_url) {
		UserInfo.info_url = info_url;
		UserInfo.pwd_url = pwd_url;
		UserInfo.pay_url = pay_url;
		UserInfo.save_url = save_url;
		
		//测试
		var str = Util.ajaxPost(info_url,{});
		var data = Util.toJson(str);
		if (Util.isEmpty(data)) {
			return;
		}
		UserInfo.showData(data);
	},
	// 加载页面
	showData : function(data) {
		if(!Util.isEmpty(data.avatar)){
			$("#headImg").attr("src", data.avatar);
		}
		$("#company").val(data.company);
		$("#email").val(data.email);
		$("#realName").val(data.realName);
		$("#mobile").val(data.mobile);
		$("#address").val(data.address);
		$("#phone").val(data.phone);
		$('#nickname').val(data.nickname);
		if(!Util.isEmpty(data.payPwd)){
			$('#setPayPwd').hide();
			$('#upPayPwd').show();
		}else{
			$('#setPayPwd').show();
			$('#upPayPwd').hide();
		}
	},
	save:function(){
		if(!this.formValidater()){
			return;
		}
		var param = {
			company : $("#company").val(),
			avatar : $("#headImg").attr("src"),
			email : $("#email").val(),
			realName : $("#realName").val(),
			mobile : $("#mobile").val(),
			address : $("#address").val(),
			phone : $("#phone").val(),
			nickname:$('#nickname').val()
		};
		
		var r = Util.ajaxPost(UserInfo.save_url, param);
		var data = Util.toJson(r);
		alert(data.message);
	},
	// 表单校验
	formValidater : function() {
		var fvoid =$("#userform").validate({
			onkeyup : false,// 是否在敲击键盘时验证
			submitHandler : function(form) { // 通过之后回调
				// 不提交
			},
			scope : false,
			// 设置验证规则
			rules : {
				company : {
					maxlength:20,
				},
				email : {
					required: true,
					isEmail:true
				},
				realName : {
					isBankName: true,
					maxlength:4,
					minlength:2
				},
				nickname : {
					maxlength:16,
				},
				address : {
					maxlength:20,
				},
				mobile : {
					isMobile : true
				},
				phone : {
					isPhone : true
				}
			},
			// 设置错误信息
			messages : {
				company : {
					maxlength: "请输入不超过20个字符"
				},
				email : {
					required : "请输入邮箱",
					isEmail: "请正确填写邮箱格式"
				},
				realName : {
					isBankName: "只能输入中文或英文",
					maxlength: "请输入不超过4个字符",
					minlength:"请输入不少于2个字符"
				},
				nickname : {
					maxlength: "请输入不超过16个字符"
				},
				address : {
					maxlength: "请输入不超过20个字符"
				},
				mobile : {
					isMobile: "请输入有效的手机号码"
				},
				phone : {
					isPhone: "请输入有效的电话号码"
				}
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
 