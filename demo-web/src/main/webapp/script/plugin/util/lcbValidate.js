/* [export] */

/**
 * 零彩宝通用校验
 * 
 * @author LDL
 */
var LcbValidate = {
	// 用户名只能由4-20位英文、数字及'_'组成
	registerUserName : function(str) {
		var reg = /^[A-Za-z0-9_]{4,20}$/;
		return reg.test(str);
	},
	// 只能包括中文字、英文字母、数字和下划线
	stringCheck : function(str) {
		var reg = /^[\u0391-\uFFE5\w]+$/;
		return reg.test(str);
	},
	// 输入的字符是否为中文
	isChinese : function(str) {
		var reg = /^[\u0391-\uFFE5]+$/;
		return reg.test(str);
	},
	// 是否数字
	isNumber : function(str) {
		return !isNaN(str);
	},
	// 校验手机号
	isMobile : function(str) {
		var length = str.length;
		var mobile = /(^(13|14|15|17|18)\d{9}$)/;
		return (length == 11 && mobile.test(str));
	},
	// 电话号码格式010-12345678
	isTel : function(str) {
		var tel = /^\d{3,4}-?\d{7,9}$/;
		return tel.test(str);
	},
	// 联系电话(手机/电话皆可)验证
	isPhone : function(str) {
		var mobile = /(^(13|14|15|17|18)\d{9}$)|(^0(([1,2]\d)|([3-9]\d{2}))\d{7,8}$)/;
		var tel = /^\d{3,4}-?\d{7,9}$/;
		return (tel.test(str) || mobile.test(str));
	},
	// 电子邮箱验证
	isEmail : function(str) {
		var reg = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/;
		return reg.test(str);
	}
}

function test() {
	alert(LcbValidate.isMobile("18111111111"));
}