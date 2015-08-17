/* [export] */
accountRecharge={
		info_url : '',
		do_url:'',
		init:function(info_url,do_url){
			this.info_url=info_url;
			this.do_url=do_url;
			//初始化页面信息
			var str = Util.ajaxPost(info_url,{});
			var data = Util.toJson(str);
			if (Util.isEmpty(data)) {
				return;
			}
			$('#username').html(data.username);
			$('#balance').find('font').html(data.balance);
		},
		//充值按钮事件
		pay:function(){
			var method=$('input[name=payType]:checked').val();//支付方式
			var money=$('#rechargeMoney').val();//充值金额	
			if (Util.isEmpty(money)) {
				Util.showErrorMessage('请填写充值金额！')
				return;
			}
			if (Util.isEmpty(method)) {
				Util.showErrorMessage('请选择支付方式')
				return;
			}
			window.location.href = this.do_url + "?method=" + method+ "&money=" + money;
			return;
		},
}