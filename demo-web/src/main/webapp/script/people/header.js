/* [export] */
var  header={
		url:'/home/user/balanceMessage',
		ctx:'',
		init:function(ctx){
			this.ctx=ctx;
			var str = Util.ajaxPost(ctx+this.url,{});
			var data = Util.toJson(str);
			if (Util.isEmpty(data)) {
				return;
			}
			$('.yh-name').html(data.nickname);
			$('.yh-name').next().find('font:eq(0)').html(data.amount.substring(0,data.amount.indexOf('.')));
			$('.yh-name').next().find('font:eq(1)').html(data.amount.substring(data.amount.indexOf('.'),data.amount.length));
			$('.user-infro').find('.blue').parent().html('个人资料完成 '+data.percents+'%&nbsp;&nbsp;&nbsp;<a class="blue" href="'+ctx+'/location/user?path=person/basicmessage/PersonalUserInfo">完善 >></a>');
			if(!Util.isEmpty(data.avatar)){
				$('.tp-lp').find('img').attr('src',data.avatar);
			}			
		},
}