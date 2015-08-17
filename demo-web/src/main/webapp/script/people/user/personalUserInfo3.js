/* [export] */

/**
 * 个人中心 个人基本信息修改
 * 
 * @author 
 */
userInfo = {
	bankList_url:'/dictionary/bankList',
	userbanklist_url:'/home/bank/list',
	delbank_url:'/home/bank/delete',
	updatebank_url:'/home/bank/bankUpdate',
	addbank_url:'/home/bank/bankAdd',
	ctx:'',
	init :function(ctx) {	
		this.ctx = ctx;
		this.getUserBankData();
		this.getBankData();
	},
	getBankData:function(){
		var str = Util.ajaxPost(this.ctx + this.bankList_url, {});
		var data = Util.toJson(str);
		if (Util.isEmpty(data)) {
			return;
		}
		var content = "<option selected='selected' value=''>请选择银行</option>";
		var banklist = data.list;
		for (var i = 0; i < banklist.length; i++) {
			content += "<option value="+banklist[i].code+">"+banklist[i].name+"</option>";
		}
		$("#bankCode").html(content);
	},
	getUserBankData:function(){
		var str = Util.ajaxPost(this.ctx + this.userbanklist_url, {});
		var data = Util.toJson(str);
		if (Util.isEmpty(data)) {
			return;
		}
		var bankList = data.banks;
		var content = "";
		for (var i = 0; i < bankList.length; i++) {
			content += "<div class='my_card'>";
			content += "    <div class='card_lx " + bankList[i].abbreviation + "'></div>";
			content += "    <span class='card-num'>" + bankList[i].cardno + "</span>";
			content += "    <div class='my-card-edit clearfix'>";
			content += "        <div class='edit-icon edit-a' onclick=\"userInfo.toUpdateBank('"+bankList[i].id+"', '"+bankList[i].cardno+"', '"+bankList[i].abbreviation+"');\"></div>";
			content += "        <div class='edit-icon edit-b' onclick=\"userInfo.toDelBank('"+bankList[i].id+"');\"></div>";
			content += "    </div>";
			content += "</div>";
		}
		$("#bankList").html(content);
	},
	toDelBank:function(id){
		if(window.confirm("确定删除吗？")){
			$("#addBankDiv").hide();
			var param = {
					id:id
				};
			var str = Util.ajaxPost(this.ctx + this.delbank_url, param);
			var data = Util.toJson(str);
			if (Util.isEmpty(data)) {
				return;
			}
			if(data.result == "true"){
				this.getUserBankData();
			}
			alert(data.message);
		}
	},
	toUpdateBank:function(id, cardno, abbreviation){
		$("#bankid").val(id);
		$("#cardno").val(cardno);
		$("#bankCode").find("option[value='"+abbreviation+"']").attr('selected', 'selected');
		$("#divName").html("修改银行卡：");
		$("#aName").html("修改");
		$("#addBankDiv").show();
	},
	updateBank:function(){
		var result = this.check();
		if (result == "F") {
			return false;
		}
		var param = {
			id:$("#bankid").val(),//bankid
			bankname:$("#bankCode").find("option:selected").text(),//开户行全称
			cardno:$("#cardno").val(),//卡号
			abbreviation:$("#bankCode").val()//开户行简写
		};
		var r = Util.ajaxPost(this.ctx + this.updatebank_url, param);
		var data = Util.toJson(r);
		if(data.result == "true"){
			this.getUserBankData();
			$("#addBankDiv").hide();
		}
		alert(data.message);
	},
	toAddBank:function(){
		$("bankid").val("");
		$("#cardno").val("");
		$("#bankCode").val("");
		$("#divName").html("添加银行卡：");
		$("#aName").html("添加");
		$("#addBankDiv").show();
	},
	addBank:function(){
		var result = this.check();
		if (result == "F") {
			return false;
		}
		var param = {
			bankname:$("#bankCode").find("option:selected").text(),//开户行全称
			cardno:$("#cardno").val(),//卡号
			abbreviation:$("#bankCode").val()//开户行简写
		};
		var r = Util.ajaxPost(this.ctx + this.addbank_url, param);
		var data = Util.toJson(r);
		if(data.result == "true"){
			this.getUserBankData();
			$("#addBankDiv").hide();
		}
		alert(data.message);
	},
	add:function(){
		if($("#aName").html() == "添加"){
			this.addBank();
		}else{
			this.updateBank();
		}
	},
	hideDiv:function(){
		$("#addBankDiv").hide();
	},
	check : function() {
		var bankname = $("#bankCode").find("option:selected").text();
		var cardno = $("#cardno").val();
		var abbreviation = $("#bankCode").val();
		if (abbreviation == "") {
			Util.showErrorMessage("请选择银行");
			return "F";
		}
		if (cardno == "") {
			Util.showErrorMessage("请输入银行卡号！");
			return "F";
		}
		if (!/^(\d{16}|\d{19})$/.test(cardno)) {
			Util.showErrorMessage("请输入正确的银行卡号！");
			return "F";
		}
		return "T";
	}
}
 