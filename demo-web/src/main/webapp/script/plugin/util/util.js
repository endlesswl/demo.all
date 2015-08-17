/* [export] */

Util = {
	CURR_USER_KEY : 'LINGCAIBAO_WAP_USERID',
	CTX_KEY : 'LCB_CTX',
	ajaxPost : function(url, data1) {
		// 同步请求
		//console.log("url=", url);
		var s = "";
		$.ajax({
			type : "POST",
			url : url,
			async : false,
			data : data1,
			success : function(data) {
				s = checkToken(data, url, data1);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				//alert(XMLHttpRequest.status);
			}
		});
		return s;
	},
	ajaxGet : function(url, data1) {
		// 同步请求
		//console.log("url=", url);
		var s = "";
		$.ajax({
			type : "get",
			url : url,
			async : false,
			cache : false,
			data : data1,
			success : function(data) {
				s = checkToken(data, url, data1);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				//alert(XMLHttpRequest.status);
			}
		});
		return s;
	},
	ajaxAsync : function(url, data1, type) {
		// 异步请求，不需要返回结果
		//console.log("url=", url);
		$.ajax({
			type : type,
			url : url,
			async : true,
			data : data1,
			success : function(data) {
			}
		});
	},
	// H5跳登陆页面，成功后返回上次页面
	toLogin_h5 : function(ctx, returnUrl) {
		var url = ctx + '/location?path=H5/login&redirectUrl='+ encodeURIComponent(returnUrl)
		window.location.href = url;
	},
	isNotEmpty : function(a) {
		if (a == undefined || a == '' || a == null) {
			return false;
		}
		return true;
	},
	isEmpty : function(a) {
		if (a == undefined || a == '' || a == null || a == "undefined") {
			return true;
		}
		return false;
	},
	nullToStr : function(a) {
		if (a != undefined) {
			a = $.trim(a);
		}
		if (a == undefined || a == '' || a == null) {
			return "";
		}
		return a;
	},
	nullToZero : function(str) {
		if (Util.isEmpty(str) || "null" == str) {
			return "0.00";
		}
		return str;
	},
	toJson : function(str) {
		if (Util.isNotEmpty(str)) {
			return eval("(" + str + ")");
		}
		return null;
	},
	getLocalUser : function() {
		var str = window.localStorage.getItem(this.CURR_USER_KEY);
		if (str == null) {
			// 涉及的地方太多，在这里赋空值
			var temp = {
				userid : '',
				token : ''
			}
			return temp;
		}
		return Util.toJson(str);
	},
	saveLocalUser : function(param, ctx) {
		try{
			window.localStorage.setItem(this.CURR_USER_KEY, param);
			if (Util.isEmpty(ctx)) {
				return;
			}
			window.localStorage.setItem(this.CTX_KEY, ctx);
		}catch (e){
			alert("您处于无痕浏览，无法为您保存数据，请切换另一种模式");
		}
	},
	removeLocalUser : function() {
		window.localStorage.removeItem(this.CURR_USER_KEY);
	},
	dateSx : function(date) {
		if (!(date instanceof Date)) {
			date = new Date();
		}
		var hour = date.getHours();
		if (hour >= 6 && hour < 8) {
			CURRENT_TIME = "早上好！";
		} else if (hour >= 8 && hour < 11) {
			CURRENT_TIME = "上午好！";
		} else if (hour >= 11 && hour < 13) {
			CURRENT_TIME = "中午好！";
		} else if (hour >= 13 && hour < 18) {
			CURRENT_TIME = "下午好！";
		} else {
			CURRENT_TIME = "晚上好！";
		}
		return CURRENT_TIME;
	},
	// 比较时间差几天,自然天
	compareDays : function(type, str1, str2) {
		var date1 = new Date(Date.parse(str1));
		var date2 = new Date(Date.parse(str2));
		// 时间差的毫秒数
		var seconds = date2.getTime() - date1.getTime();
		var hours = seconds / (3600 * 1000);
		var days = seconds / (24 * 3600 * 1000);

		if (type == "d") {
			// 计算出相差天数
			return Math.ceil(days);
		}
		if (type == "w") {
			// 计算出相差天数
			return Math.floor(days);
		}
		if (type == "m") {
			return Math.ceil(days) + 1;
		}
		if (type == "y") {
			return Math.ceil(days) + 1;
		}
	},
	/**
	 * 获取字符串长度
	 */
	strlen : function(str){
	    var len = 0;
	    for(var i = 0; i < str.length; i++) {
	        len += str.charCodeAt(i) < 0 || str.charCodeAt(i) > 255 ? 2 : 1;
	    }
	    return len;
	},	
	//校验手机号码
	isMobile : function(mobile) {
		var reg0 = /^0{0,1}(13[0-9]|14[0-9]|15[0-9]|18[0-9])[0-9]{8}$/;
		var isOk = false;
		if (reg0.test(mobile))isOk=true;
	    return isOk;
	},
	//是否是数字
	isNumber:function(str){
		var re = /^[1-9]+[0-9]*]*$/;  
		var isOk = false;
		if (re.test(str))isOk=true;
	    return isOk;
	},
	isUrl:function(rurl){
		var strRegex = /^http:\/\/(?:[\w-\.]{0,255})(?:(?:\/?[^\s]{0,255}){0,255})/g; 
		var isOk = false;
		if (strRegex.test(rurl))isOk=true;
	    return isOk;
	},
	/*是否是大于0的数 小数点后两位
	 * */
	isNewNumber:function(str){
		var re = /^\d+.?\d{0,2}$/;
		var isOk = false;
		if (re.test(str))isOk=true;
	    return isOk;
	},
	toAuthor:function(){
		var user = Util.getLocalUser();
		// 获取ctx
		var ctx = "";
		if (window.location.host == "wap.lingcaibao.com") {
			ctx = "";// 线上环境
		} else {
			ctx = "/wap";// 测试环境
		}
		var loginUrl = ctx + '/location?path=H5/login&redirectUrl='+encodeURIComponent(window.location.href);
		// 重新授权
		var param = {
			username : user.username,
			password : user.password
		};
		var loginResult = '';
		$.ajax({
			type : "POST",
			url : ctx + "/mobile/authorize",
			async : false,
			data : param,
			success : function(data) {
				loginResult = data;
			}
		});
		var data22 = Util.toJson(loginResult);
		if (data22 == null || data22.result != "true") {
			// 授权失败，跳到登陆页面
			window.location.href = loginUrl;
			return;
		}

		// 授权后更新token
		var userData = Util.getLocalUser();
		userData.userid = data22.userid;
		userData.token = data22.token;
		var userDataStr = '{"username":"' + userData.username
				+ '","userid" : "' + userData.userid + '","password":"'
				+ userData.password + '","token" : "' + data22.token + '"}';
		Util.saveLocalUser(userDataStr);
	},
	checkSpace : function(){
		//input框禁止输入空格
		$('form :input').attr({"onkeyup":"this.value=this.value.replace(/^ +| +$/g,'')","onkeydown":"if(event.keyCode==32) return false"});
	},
	showErrorMessage:function(errorMessage){
		if($('#Vdialog').size()==0){
			var div = document.createElement('div');
			$(document.body).append(div);
			div.id='Vdialog';
			$(div).attr('class','Vdialog');
			var aWidth=$(window).width();
			var aHeight=$(window).height()/2;
			var aLeft=(aWidth-$('#Vdialog').width())/2;
			$('#Vdialog').css({'left':aLeft,'top':aHeight});
		}		
		$('#Vdialog').html(errorMessage);		
		$('#Vdialog').show();
		setTimeout(function() {
			$('#Vdialog').hide();
		}, 1500)
		
	}
}


//-----------------------------------------------------
//H5请求判断：--若token失效，自动请求授权更新token
function checkToken(str, oldUrl, oldData) {
	if (Util.isEmpty(str) 
			|| Util.isEmpty(oldData)
			|| oldData.token == undefined
			|| (oldUrl.indexOf("/mobile/") < 0 && oldUrl.indexOf("/mActivity/") < 0)) {
		return str;
	}
	var result = Util.toJson(str);
	if ((result.respCode != undefined && result.respCode == "003") || (result.respCode != undefined && result.respCode == "001")) {
		
		// 获取ctx
		var ctx = "";
		if (window.location.host == "wap.lingcaibao.com") {
			ctx = "";// 线上环境
		} else {
			ctx = "/wap";// 测试环境
		}
		var loginUrl = ctx + '/location?path=H5/login&redirectUrl='+encodeURIComponent(window.location.href);
		
		var user = Util.getLocalUser();
		if (Util.isEmpty(user.userid)) {
			window.location.href = loginUrl;
		}

		// 重新授权
		var param = {
			username : user.username,
			password : user.password
		};
		var loginResult = '';
		$.ajax({
			type : "POST",
			url : ctx + "/mobile/authorize",
			async : false,
			data : param,
			success : function(data) {
				loginResult = data;
			}
		});
		var data22 = Util.toJson(loginResult);
		if (data22 == null || data22.result != "true") {
			// 授权失败，跳到登陆页面
			window.location.href = loginUrl;
			return;
		}

		// 授权后更新token
		var userData = Util.getLocalUser();
		userData.userid = data22.userid;
		userData.token = data22.token;
		var userDataStr = '{"username":"' + userData.username
				+ '","userid" : "' + userData.userid + '","password":"'
				+ userData.password + '","token" : "' + data22.token + '"}';
		Util.saveLocalUser(userDataStr);

		// 重新请求
		oldData.token = userData.token;
		$.ajax({
			type : "POST",
			url : oldUrl,
			async : false,
			data : oldData,
			success : function(data) {
				str = data;
			}
		});
	}
	return str;
}


// -----------------------------------------------------
/**
 * 计划类型枚举值
 */
function MarketTypeEnum() {
	// nothing
}
MarketTypeEnum.prototype = {
	typeList : [ {
		type : "BIGWHEEL",
		name : "大转盘"
	}, {
		type : "TURNOVER",
		name : "翻牌"
	}, {
		type : "TIGER",
		name : "老虎机"
	}, {
		type : "DICE",
		name : "掷骰子"
	}, {
		type : "SCRATCHCARD",
		name : "刮刮卡"
	}, {
		type : "FORTUNEBAG",
		name : "福袋"
	} ],
	findByType : function(type) {
		for (var i = 0; i < this.typeList.length; i++) {
			if (this.typeList[i].type == type) {
				return this.typeList[i];
			}
		}
		return null;
	}
}
//-----------------------------------------------------
function PrizeEnum() {
	// nothing
}
PrizeEnum.prototype = {
	typeList : [ {
		type : "SSQ",
		name : "双色球"
	} ],
	findByType : function(type) {
		for (var i = 0; i < this.typeList.length; i++) {
			if (this.typeList[i].type == type) {
				return this.typeList[i];
			}
		}
		return null;
	}
}
