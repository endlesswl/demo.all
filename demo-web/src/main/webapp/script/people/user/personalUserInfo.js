/* [export] */

/**
 * 个人中心 个人基本信息修改
 * 
 * @author 
 */
userInfo = {
	save_url : '',	
	info_url : '',
	province_url:'',
	province:'',
	city:'',
	area:'',
	ctx:'',
	init : function(save_url,info_url,province_url,ctx) {	
		this.ctx=ctx;
		this.save_url = save_url;
		this.info_url = info_url;
		//初始化页面信息
		var str = Util.ajaxPost(info_url,{});
		var data = Util.toJson(str);
		if (Util.isEmpty(data)) {
			return;
		}
		this.showData(data);
		
		//加载用户的省市区信息
		province=data.province;
		var provinceData=Util.ajaxPost(province_url,{countryCode:697573});//当前默认是中国的省市区
		provinceData = eval("(" + provinceData + ")");
		this.showProvinceData(provinceData.provinceList);		
		
		city=data.city;//城市
		if (!Util.isEmpty(city)) {
			this.showCityData(ctx);
		}		
		area=data.area;//地区
		if (!Util.isEmpty(area)) {
			this.showareaData(ctx);
		}
	},
	//省份列表
	showProvinceData:function(data){
		var optionObj='';
		data=eval(data);
		for(var i=0;i<data.length;i++){
			dictionary=data[i];
			if(province==dictionary.code){
				$('#province').append('<option id='+dictionary.code+' selected>'+dictionary.name+'</option>');
			}else{
				$('#province').append('<option id='+dictionary.code+'>'+dictionary.name+'</option>');
			}			
		}
	},
	//城市列表
	showCityData:function(ctx){
		var provinceId=$('#province').find('option:selected').attr('id');//拿到省份id
		if(ctx==undefined)ctx=''
		var city_data = Util.ajaxPost(ctx+'/dictionary/cityList',{provinceCode:provinceId});
		city_data=eval("(" + city_data + ")")
		var cityList=eval(city_data.cityList);
		if(Util.isEmpty(city)){
			$('#city').find('option:gt(0)').remove();
		}else{
			$('#city').find('option').remove();
		}
		for(var i=0;i<cityList.length;i++){
			dictionary=cityList[i];
			
			if (Util.isEmpty(city)) {
				if(i==0){
					$('#city').append('<option id='+dictionary.code+' selected>'+dictionary.name+'</option>');
				}else{
					$('#city').append('<option id='+dictionary.code+'>'+dictionary.name+'</option>');
				}
			}else{
				if(city==dictionary.code){
					$('#city').append('<option id='+dictionary.code+' selected>'+dictionary.name+'</option>');	
				}else{
					$('#city').append('<option id='+dictionary.code+'>'+dictionary.name+'</option>');	
				}
			}					
		}
		this.showareaData();//级联地区列表
	},
	//地区列表
	showareaData:function(ctx){
		var cityId=$('#city').find('option:selected').attr('id');//拿到城市id
		if(ctx==undefined)ctx=''
		var area_data = Util.ajaxPost(ctx+'/dictionary/areaList',{cityCode:cityId});
		area_data=eval("(" + area_data + ")")
		var areaList=eval(area_data.areaList);
		
		if(Util.isEmpty(area)){
			$('#area').find('option:gt(0)').remove();
		}else{
			$('#area').find('option').remove();
		}
		
		for(var i=0;i<areaList.length;i++){
			dictionary=areaList[i];
			if (Util.isEmpty(area)) {
				if(i==0){
					$('#area').append('<option id='+dictionary.code+' selected>'+dictionary.name+'</option>');
				}else{
					$('#area').append('<option id='+dictionary.code+'>'+dictionary.name+'</option>');	
				}
			}else{
				if(area==dictionary.code){
					$('#area').append('<option id='+dictionary.code+' selected>'+dictionary.name+'</option>');		
				}else{
					$('#area').append('<option id='+dictionary.code+'>'+dictionary.name+'</option>');		
				}
			}								
		}
	},
	// 加载页面
	showData : function(data) {
		$("#email").html(data.email);//邮箱
		//$('#realname').find('font').before(data.realname);
		$('#nickname').find('font').before(data.nickname);
		//是否绑定姓名
		/*if(data.authflags==2){
			$('#realname').find('font').html('已绑定');
		}else{
			$('#realname').find('font').html('未绑定');
		}*/
		var mobile=data.mobile;
		mobile=mobile.replace(mobile.substring(0,7),'*******')
		$("#mobile").find('a').before(mobile);
		//性别
		$('#gender').find('redio');
		if(data.gender==1){
			$('#gender').find('redio:eq(1)').checked;
		}else if(data.gender==2){
			$('#gender').find('redio:eq(0)').checked;
		}else{
			$('#gender').find('redio:eq(2)').checked;
		}
		if(!Util.isEmpty(data.birthday)){
			$('#birthday').val(data.birthday.substring(0,10));
		}else{
			$('#birthday').val(data.birthday);
		}		
		$("#email").val(data.email);
		$("#qqNumber").val(data.qq);
		$("#address").val(data.address);
		$("#zipcode").val(data.zipcode);
		$("#fax").val(data.fax);
		$("#phonenumber").val(data.phone);
	},
	//保存修改用户信息
	updateUserInfo:function(){
		var param = {
			gender:$('input[name=sex]:checked').val(),//性别			
			qq: $("#qqNumber").val(),//qq号					
			address: $("#address").val(),//地址
			zipcode: $("#zipcode").val(),//邮编
			phone: $("#phonenumber").val(),//电话号码
			fax: $("#fax").val(),//传真
			province:$('#province').find('option:selected').attr('id'),//省份
			city:$('#city').find('option:selected').attr('id'),//城市
			area:$('#area').find('option:selected').attr('id'),//地区	
			email:$("#email").val(),
			nickname:$("#nickname").val()
		};
		if(!Util.isEmpty($("#birthday").val())){//出生日期
			param.birthday=$("#birthday").val().replace('-','/').replace('-','/');
		}
		var r = Util.ajaxPost(this.save_url, param);
		var data = Util.toJson(r);
		alert(data.message);
	}
}
 