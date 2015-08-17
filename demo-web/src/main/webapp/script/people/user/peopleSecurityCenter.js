/* [export] */

/**
 * 安全中心
 * 
 * @author 
 */
securityCenter = {
	info_url : '',
	ctx:'',
	init : function(info_url,ctx) {
		this.ctx=ctx;
		this.info_url = info_url;
		var str = Util.ajaxPost(info_url,{});
		var data = Util.toJson(str);
		if (Util.isEmpty(data)) {
			return;
		}
		this.showData(data);
	},
	// 加载页面
	showData : function(data) {		
		//时间
		var dateFormat = new DateFormat(false);
		$('#h2Time').html(dateFormat.dateSx(new Date())+' '+data.nickname);
		//账户安全等级
		var gradescore=data.gradescores;
		var safe_level=$('.safe-level');
		if(gradescore<=60){
			safe_level.attr('class','safe-level low-level');
			safe_level.html('低');
		}else if(gradescore>60&&gradescore<=80){
			safe_level.attr('class','safe-level middle-level');
			safe_level.html('中');
		}else if(gradescore>80){
			safe_level.attr('class','safe-level hight-level');
			safe_level.html('高');
		}
		//登录密码
		var  pwdgrade=data.pwdgrade;
		var pwdgradeFont=$('#pwdgradeFont');
		if(pwdgrade==2){
			pwdgradeFont.html('高');
		}else if(pwdgrade==1){
			pwdgradeFont.html('中');
		}else if(pwdgrade==0){
			pwdgradeFont.html('低');
		}
		//交易密码
		var  paypwdgrade=data.paypwdgrade;
		var paypwdgradeFont=$('#paypwdgradeFont');
		if(paypwdgrade==2){
			paypwdgradeFont.html('高');
		}else if(paypwdgrade==1){
			paypwdgradeFont.html('中');
		}else if(paypwdgrade==0){
			paypwdgradeFont.html('低');
		}
		//实名认证 0:未认证 1:认证中 2:认证成功 3:认证失败',
		var authflags=data.authflags;
		var _authflags_=$('#authflags');
		if(authflags==1){
			_authflags_.html('认证中');
		}else if(authflags==2){
			_authflags_.html('认证成功');
		}else if(authflags==3){
			_authflags_.html('认证失败');
			_authflags_.attr('href',this.ctx+'/location/business?path=business/account/authRealName');
		}else if(authflags==0){
			_authflags_.html('未认证');
			_authflags_.attr('href',this.ctx+'/location/business?path=business/account/authRealName');
		}
		if(!Util.isEmpty(data.avatar)){
			$("#headImg").attr("src", data.avatar);
		}
		
	},
	toFindPayPwd:function (){
		var returnUrl =encodeURIComponent(window.location.href);
		window.location.href=this.ctx+"/location/user?path=person/basicmessage/findPayPwd&returnUrl="+returnUrl;
	},
	toUpdatePayPwd:function (){
		var returnUrl =encodeURIComponent(window.location.href);
		window.location.href=this.ctx+"/location/user?path=person/basicmessage/updatePayPwd&returnUrl="+returnUrl;
	},
	toUpdatePwd:function (){
		var returnUrl =encodeURIComponent(window.location.href);
		window.location.href=this.ctx+"/location/user?path=person/basicmessage/updateLoginPwd&returnUrl="+returnUrl;
	}
}
 