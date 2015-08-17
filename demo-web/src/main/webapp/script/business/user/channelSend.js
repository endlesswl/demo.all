/* [export] */

/**
 * 下发渠道页面
 * 
 * @author LDL
 */
ChannelSend = {
	url : '',
	send_url : '',
	sendType : '0',
	sendTime : 'now()',
	area:'',
	province_url:'',
	user_url:'',
	userIdStr:'',
	unameStr:'',
	type:'0',//下发方式
	pageNum:1,
	init : function(url, send_url,province_url,user_url) {
		this.user_url=user_url;
		this.send_url = send_url;
		this.province_url=province_url;
		//加载分组下拉框
		var str = Util.ajaxPost(url, {});	
		var data = Util.toJson(str);
		if (Util.isEmpty(data)) {
			return;
		}
		
		for (var int = 0; int < data.list.length; int++) {
			var group = data.list[int];
			//console.log("group===", group.clustno);
			$("#userGroup").append('<option value="' + group.clustno + '">' + group.clustname + '</option>');
		}
		
		//加载用户的省市区信息
		var provinceData=Util.ajaxPost(province_url,{countryCode:697573});//当前默认是中国的省市区
		provinceData = eval("(" + provinceData + ")");
		this.showProvinceData(provinceData.provinceList);	
	},
	//省份列表
	showProvinceData:function(data){
		var optionObj='';
		data=eval(data);
		for(var i=0;i<data.length;i++){
			dictionary=data[i];
			$('#province').append('<option id='+dictionary.code+' value='+dictionary.code+'>'+dictionary.name+'</option>');
		}
	},
	//城市列表
	showCityData:function(ctx){
		var provinceId=$('#province').find('option:selected').attr('id');//拿到省份id
		if(Util.isEmpty(provinceId)){
			return;
		}
		if(ctx==undefined)ctx=''
		var city_data = Util.ajaxPost(ctx+'/dictionary/cityList',{provinceCode:provinceId});
		city_data=eval("(" + city_data + ")")
		var cityList=eval(city_data.cityList);
		$('#city').find('option:gt(0)').remove();
		for(var i=0;i<cityList.length;i++){
			dictionary=cityList[i];
			if(i==0){
				$('#city').append('<option id='+dictionary.code+' selected value='+dictionary.code+'>'+dictionary.name+'</option>');
			}else{
				$('#city').append('<option id='+dictionary.code+' value='+dictionary.code+'>'+dictionary.name+'</option>');
			}
			
		}
		this.showareaData();//级联地区列表
	},
	//地区列表
	showareaData:function(ctx){
		var cityId=$('#city').find('option:selected').attr('id');//拿到城市id
		if(Util.isEmpty(cityId)){
			return;
		}
		if(ctx==undefined)ctx=''
		var area_data = Util.ajaxPost(ctx+'/dictionary/areaList',{cityCode:cityId});
		area_data=eval("(" + area_data + ")")
		var areaList=eval(area_data.areaList);
		$('#area').find('option:gt(0)').remove();
		
		for(var i=0;i<areaList.length;i++){
			dictionary=areaList[i];
			if(i==0){
				$('#area').append('<option id='+dictionary.code+' selected value='+dictionary.code+'>'+dictionary.name+'</option>');
			}else{
				$('#area').append('<option id='+dictionary.code+' value='+dictionary.code+'>'+dictionary.name+'</option>');
			}
		}
	},
	setParamm : function(sendType,sendTime) {
		if (Util.isNotEmpty(sendType)) {
			this.sendType = sendType;
		}
		if (Util.isNotEmpty(sendTime)) {
			this.sendTime = sendTime;
		}
	},
	send : function(content) {		
		if(this.type==0){
			var clustno=$("#userGroup").find("option:selected").val();
			if(clustno==""){
				alert("请选择用户组");
				return ;
			}
			var sex=$("#sex").find("option:selected").val();
			var region='';
			var province=$("#province").find("option:selected").val();
			var city=$("#city").find("option:selected").val();
			var area=$("#area").find("option:selected").val();
			if(!Util.isEmpty(province)){
				if(!Util.isEmpty(region)){
					region+=('-'+province);
				}else{
					region+=province;
				}
			}
			if(!Util.isEmpty(city)){
				if(!Util.isEmpty(region)){
					region+=('-'+city);
				}else{
					region+=city;
				}
			}
			if(!Util.isEmpty(area)){
				if(!Util.isEmpty(region)){
					region+=('-'+area);
				}else{
					region+=area;
				}
			}
		}else{
			if(this.userIdStr==""){
				alert("请选择发送对象");
				return ;
			}
		}
		if(content==""){
			alert("发送内容不能为空");
			return ;
		}
		var  title=$('#title').val();//标题
		if(title==""){
			alert("标题不能为空");
			return ;
		}
		var time = $("#sendTime").val();
		if(this.sendTime=="timer" && time =="" ){
			alert("请选择定时发送时间");
			return;
		}
		if(this.sendTime=="now()"){
			time = '';
		}
		var param = {
				clustno : clustno,
				sex : sex,
				region :region,
				userIdStr:this.userIdStr,
				content :content,
				sendType :this.sendType,
				sendTime :time,
				type:this.type,
				title:title
			};
		var str = Util.ajaxPost(this.send_url, param);
		var data = Util.toJson(str);
		if (Util.isEmpty(data)) {
			return;
		}
		alert(data.message);
	},
	goSubmit:function(){
		if(this.sendType=="0"){
			this.send(editor.text());
		}else{
			this.send(editor.html());
		}
	},
	showUser:function(){
		this.query();
		$.dialog({
			title: '选择的用户',
			content:$('#userDiv').html(),
			cancelValue: '取消',
			cancel:true,
			lock:true,
			okValue:'确定',
			ok: function(){					
				//获取选中的userId
				ChannelSend.userIdStr='';
				ChannelSend.unameStr='';
				//处理数据
				$('#dataList').find('input[name=ckbox]:checked').each(function(){
					ChannelSend.userIdStr+=$(this).attr('value')+",";
					ChannelSend.unameStr+=$(this).attr('uname')+",";					
				});	
				$('#chooseName').val(ChannelSend.unameStr.toString());				
			}
		});
	},
	// 查询
	query : function(pageNum) {
		if(Util.isEmpty(pageNum)){
    		this.pageNum=1;
    	}
		var url= this.user_url ;		
		var str = Util.ajaxPost(url,{page:this.pageNum});
		var data = Util.toJson(str);
		
		$("#dataList tr:gt(0)").remove();
		//测试
		if (Util.isEmpty(data) || data.list.length == 0) {			
			$('#pageDiv').hide();
			return;
		}
		this.loadData(data.list);
		
		 // 分页插件，要实现queryPage方法查询
		PageLc.pageId = 'pageDiv';
		PageLc.pageNum = data.pageNum;
		PageLc.totalPage = data.pages;
		PageLc.totalRow = data.total;
		PageLc.init(this);
	},
	// 翻页
	queryPage : function(pageNum) {
		this.pageNum = pageNum;
		this.query(pageNum);
	},
	// 加载页面TABLE
	loadData : function(rows) {
		if (Util.isEmpty(rows)) {
			return;
		}
		$("#dataList tr:gt(0)").remove();
		for (var i = 0; i <= rows.length-1; i++) {
			var liveness='';
			if(Util.isEmpty(rows[i].liveness)){
				liveness=0;
			}else{
				liveness=rows[i].liveness;
			}
			var dataTr ='<tr>'+
			'<td><input type="checkbox" name="ckbox" value="'+rows[i].userid+'" uname="'+rows[i].nickname+'"></td>'+
			'<td>'+(i+1)+'</td>'+
			'<td>'+rows[i].nickname+'</td>'+
			'<td>'+liveness+'</td>'+
			'</tr>';
			$("#dataList").append(dataTr);
		}
	} ,
	//选择下发方式
	chooseType:function(type){
		this.userIdStr='';
		if (Util.isNotEmpty(type)) {
			this.type = type;
		}
		if(type==1){
			$('#areaItem').hide();
			$('#userGroup').hide();
			$('#sex').hide();
			$('#sexFont').hide();
			$('#chooseName').show();
		}else{
			$('#areaItem').show();
			$('#userGroup').show();
			$('#sex').show();
			$('#sexFont').show();
			$('#chooseName').hide();
		}
	},
}

