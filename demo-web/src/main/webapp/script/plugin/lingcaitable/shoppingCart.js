Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
		this.splice(index, 1);
	}
};

function shoppingCart() {
}  
      
shoppingCart.prototype = {
	path : "",
	multiple : 1,
	moneyNameId : "",
	gameId : "",
	thisPage : false,
	input_multiple : "",
	getLocalStorage : function(){
		var ballNos = localStorage.getItem(this.gameId);
		var result = JSON.parse(ballNos);
		return result.multiple;
	},
	setMultple : function(){
		var ballNos = localStorage.getItem(this.gameId);
		var result = JSON.parse(ballNos);
		result.multiple = this.multiple;
		var jsonObj = JSON.stringify(result);
		try{
			localStorage.setItem(this.gameId, jsonObj);
		}catch (e){
			alert("您处于无痕浏览，无法为您保存数据，请切换另一种模式");
		}
		
	},
	addMultiple : function(nameId){
		this.multiple = 1 + parseInt(this.multiple);
		
		if(this.multiple >= 50){
			$.dialog({
				content : '倍数不能大于50倍',
				title : "alert",
				//width: 600,
				time : 2000,
				lock : false
			});
			this.multiple = 50;
		}
		
		$("#"+nameId).val(this.multiple);
		this.setMultple();
		this.getTotalMoney(this.moneyNameId,this.gameId);
	},
	subMultiple : function(nameId){
		if(this.multiple > 1){
			this.multiple-=1;
		}else{
			 $.dialog({
				content : '最小一倍',
				title : "alert",
				// width: 600,
				time : 2000,
				lock : false
			});
		}
		$("#"+nameId).val(this.multiple);
		this.setMultple();
		this.getTotalMoney(this.moneyNameId,this.gameId);
	},
	goSsq : function(reqSource){
		window.location.href = this.path+"/select/ssq?reqSource="+reqSource;
	},
	goDlt : function(reqSource){
		window.location.href = this.path+"/select/dlt?reqSource="+reqSource;
	},
	goQlc : function(reqSource){
		window.location.href = this.path+"/select/qlc?reqSource="+reqSource;
	},
	goD3 : function(reqSource){
		window.location.href = this.path+"/select/d3?reqSource="+reqSource;
	},
	//以后实现计算
	calculatorMoney : function(ballNo,playType){
		
		return 2;
	},
	addLocalStorage : function(gameId,playType,ball,multiple){
		var ballNo = localStorage.getItem(gameId);
		
		if(ballNo == null || ballNo == 'null'){
			var result = new Object();
			result.gameId = gameId;
			result.multiple = this.multiple;
			var ballArray = new Array();
			var obj = new Object();
			var uuid = Math.uuid(15);
			obj.id = uuid;
			obj.ballNo = ball;
			obj.money = 2;
			obj.gameId = gameId;
			obj.playType = playType;
			obj.multiple = multiple;
			ballArray.push(obj);
			result.tickets = ballArray;
			var jsonObj = JSON.stringify(result);
			try{
				localStorage.setItem(gameId, jsonObj);
			}catch (e){
				alert("您处于无痕浏览，无法为您保存数据，请切换另一种模式数据，请切换另一种模式");
			}
			
		}else{
			var result = JSON.parse(ballNo);
			var obj = new Object();
			var uuid = Math.uuid(15);
			obj.id = uuid;
			obj.ballNo = ball;
			obj.money = 2;
			obj.gameId = gameId;
			obj.playType = playType;
			obj.multiple = multiple;
			result.tickets.push(obj);
			var jsonObj = JSON.stringify(result);
			try{
				localStorage.setItem(gameId, jsonObj);
			}catch (e){
				alert("您处于无痕浏览，无法为您保存数据，请切换另一种模式");
			}
			
		}
	},
	addSsq : function(ball){
		this.addLocalStorage("SSQ",0,ball,1);
		if(this.thisPage == true){
			this.addOneBall("SSQ");
		}
	},
	addDlt : function(ball){
		this.addLocalStorage("DLT",1,ball,1);
		if(this.thisPage == true){
			this.addOneBall("DLT");
		}
	},
	addQlc : function(ball){
		this.addLocalStorage("QLC",0,ball,1);
		if(this.thisPage == true){
			this.addOneBall("QLC");
		}
	},
	addD3 : function(ball){
		this.addLocalStorage("D3",0,ball,1);
		if(this.thisPage == true){
			this.addOneBall("D3");
		}
	},
	addOneBall : function(gameId){
		var ballNos = localStorage.getItem(gameId);
		var result = JSON.parse(ballNos);
		var tickets = result.tickets;
		var oneItem = tickets[tickets.length-1];
		var ballList_li = "<li class='clearfix add' id='ballList_li"+oneItem.id+"'></li>";
		$("#ballList").prepend(ballList_li);
		
		var ballList_li_div = "<div class='gw_cartleft' id='ballList_li_div"+oneItem.id+"'></div>";
		$("#ballList_li"+oneItem.id).append(ballList_li_div);
		
		var ballList_li_a = "<a href='#' onclick=shoppingCart1.deleteBall("+(tickets.length-1)+","+"'"+gameId+"'"+","+"'ballList_li"+oneItem.id+"','"+oneItem.id+"')> <div class='gw_close'> <em> </em> </div> </a>";
		$("#ballList_li"+oneItem.id).append(ballList_li_a);
		
		var ballList_li_div_div = "<div class='gw_cartnum' id='ballList_li_div_div"+oneItem.id+"'></div>";
		$("#ballList_li_div"+oneItem.id).append(ballList_li_div_div);
		
		var ballList_li_div_div2 = '<div class="f12 gray_deep">单式投注 1注</div>';
		$("#ballList_li_div"+oneItem.id).append(ballList_li_div_div2);
		this.addBallToPage(gameId,oneItem);
		
	},
	addBallToPage : function(gameId,oneBall){
		if(gameId == "SSQ"){
			var oneItem = oneBall;
			var ballNo = oneItem.ballNo;
			var red = ballNo.split(":")[0];
			var bule = ballNo.split(":")[1];
			var redArray = red.split(",");
			for (var j = 0; j < redArray.length; j++) {
				var lii = "<i>"+redArray[j]+"</i>";
				$("#ballList_li_div_div"+oneItem.id).append(lii);
			}
			
			$("#ballList_li_div_div"+oneItem.id).append("<i class='blue'>"+bule+"</i>");
			this.getTotalMoney(this.moneyNameId,"SSQ");
		}
		if(gameId == "DLT"){
			var oneItem = oneBall;
			var ballNo = oneItem.ballNo;
			var red = ballNo.split("/")[0];
			var bule = ballNo.split("/")[1];
			red = red.substring(1,red.length-1);
			bule = bule.substring(1,bule.length-1);
			var redArray = red.split(",");
			var buleArray = bule.split(",");
			for (var j = 0; j < redArray.length; j++) {
				var lii = "<i>"+redArray[j]+"</i>";
				$("#ballList_li_div_div"+oneItem.id).append(lii);
			}
			for (var k = 0; k < buleArray.length; k++) {
				var lii = "<i class='blue'>"+buleArray[k]+"</i>";
				$("#ballList_li_div_div"+oneItem.id).append(lii);
			}
//			$("#ballList_li_div_div"+oneItem.id).append("<i class='blue'>"+bule+"</i>");
			this.getTotalMoney(this.moneyNameId,"DLT");
		}
		if(gameId == "D3"){
			var oneItem = oneBall;
			var ballNo = oneItem.ballNo;
			var redArray = ballNo.split(":");
			for (var j = 0; j < redArray.length; j++) {
				var lii = "<i>"+redArray[j]+"</i>";
				$("#ballList_li_div_div"+oneItem.id).append(lii);
			}
			this.getTotalMoney(this.moneyNameId,"D3");
		}
		if(gameId == "QLC"){
			var oneItem = oneBall;
			var ballNo = oneItem.ballNo;
			var redArray = ballNo.split(",");
			for (var j = 0; j < redArray.length; j++) {
				var lii = "<i>"+redArray[j]+"</i>";
				$("#ballList_li_div_div"+oneItem.id).append(lii);
			}
			this.getTotalMoney(this.moneyNameId,"QLC");
		}
		
	},
	getTotalMoney : function(nameId,gameId){
		var ballNos = localStorage.getItem(gameId);
		var result = JSON.parse(ballNos);
		var tickets = result.tickets;
		
		var money = 0;
		
		for (var i = 0; i < tickets.length; i++){
			money += tickets[i].money;
		}
		
		if(isNaN(this.multiple)){
			this.multiple = 1;
		}
		
		this.multiple = $.trim(this.multiple);
		
		if(this.multiple == "" || this.multiple < 1){
			if(this.multiple < 1){
				$.dialog({
					content : '最小一倍',
					title : "alert",
					//width: 600,
					time : 2000,
					lock : false
				});
			}
			this.multiple = 1;
		}
		
		if(this.multiple > 50){
			$.dialog({
				content : '倍数不能大于50倍',
				title : "alert",
				//width: 600,
				time : 2000,
				lock : false
			});
			this.multiple = 50;
		}
		
		this.multiple = Math.ceil(this.multiple);
		
		this.setMultple();
		
		totalMoney = money * this.multiple;
		
		$("#"+this.input_multiple).val(this.multiple);
		  
		$("#"+nameId).val("立即付款 "+totalMoney + " 元");
		return totalMoney;
		
	},
	removeOneItem : function(removeId){
		$("#"+removeId).attr("class","clearfix delete") ;
		setTimeout(function(){
			$("#"+removeId).remove();
		}, 500); 
	},
	deleteBall : function(i,gameId,removeId,ballId){
		
		var ballNos = localStorage.getItem(gameId);
		
		var result = JSON.parse(ballNos);
		
		var tickets = result.tickets;
		
		for (var i = 0; i < tickets.length; i++){
			if(tickets[i].id == ballId){
				tickets.remove(tickets[i]);
				break;
			}
		}
//		tickets.remove(tickets[i]);
		
		var jsonObj = JSON.stringify(result);
		try{
			localStorage.setItem(gameId, jsonObj);
		}catch(e){
			alert("您处于无痕浏览，无法为您保存数据，请切换另一种模式");
		}
		
		if (gameId == "SSQ") {
			this.removeOneItem(removeId);
			this.getTotalMoney(this.moneyNameId,"SSQ");
		}
		if (gameId == "D3") {
			this.removeOneItem(removeId);
			this.getTotalMoney(this.moneyNameId,"D3");
		}
		if (gameId == "DLT") {
			this.removeOneItem(removeId);
			this.getTotalMoney(this.moneyNameId,"DLT");
		}
		if (gameId == "QLC") {
			this.removeOneItem(removeId);
			this.getTotalMoney(this.moneyNameId,"QLC");
		}
		
	},
	reloadSsq : function(){
		var ballNos = localStorage.getItem("SSQ");
		
		if(ballNos == null){
			return ;
		}
		
		var result = JSON.parse(ballNos);
		
		var tickets = result.tickets;
		$("#ballList").html("");
		for (var i = tickets.length - 1; i >= 0; i--) {
			
			var ballList_li = "<li class='clearfix add' id='ballList_li"+i+"'></li>";
			$("#ballList").append(ballList_li);
			
			var ballList_li_div = "<div class='gw_cartleft' id='ballList_li_div"+i+"'></div>";
			$("#ballList_li"+i).append(ballList_li_div);
			
			var ballList_li_a = "<a href='#' onclick=shoppingCart1.deleteBall("+i+","+"'SSQ'"+","+"'ballList_li"+i+"','"+tickets[i].id+"')> <div class='gw_close'> <em> </em> </div> </a>";
			$("#ballList_li"+i).append(ballList_li_a);
			
			var ballList_li_div_div = "<div class='gw_cartnum' id='ballList_li_div_div"+i+"'></div>";
			$("#ballList_li_div"+i).append(ballList_li_div_div);
			
			var ballList_li_div_div2 = '<div class="f12 gray_deep">单式投注 1注</div>';
			$("#ballList_li_div"+i).append(ballList_li_div_div2);
				
			var oneItme = tickets[i];
			
			var ballNo = oneItme.ballNo;
			
			var red = ballNo.split(":")[0];
			
			var bule = ballNo.split(":")[1];
			
			var redArray = red.split(",");
			
			for (var j = 0; j < redArray.length; j++) {
				var lii = "<i>"+redArray[j]+"</i>";
				$("#ballList_li_div_div"+i).append(lii);
			}
			
			$("#ballList_li_div_div"+i).append("<i class='blue'>"+bule+"</i>");
		}
		this.getTotalMoney(this.moneyNameId,"SSQ");
	},
	reloadDlt : function(ball){
		
		var ballNos = localStorage.getItem("DLT");
		
		if(ballNos == null){
			return ;
		}
		
		var result = JSON.parse(ballNos);
		
		var tickets = result.tickets;
		$("#ballList").html("");
		for (var i = tickets.length - 1; i >= 0; i--) {
			
			var ballList_li = "<li class='clearfix add' id='ballList_li"+i+"'></li>";
			$("#ballList").append(ballList_li);
			
			var ballList_li_div = "<div class='gw_cartleft' id='ballList_li_div"+i+"'></div>";
			$("#ballList_li"+i).append(ballList_li_div);
			
			var ballList_li_a = "<a href='#' onclick=shoppingCart1.deleteBall("+i+","+"'DLT'"+","+"'ballList_li"+i+"','"+tickets[i].id+"')> <div class='gw_close'> <em> </em> </div> </a>";
			$("#ballList_li"+i).append(ballList_li_a);
			
			var ballList_li_div_div = "<div class='gw_cartnum' id='ballList_li_div_div"+i+"'></div>";
			$("#ballList_li_div"+i).append(ballList_li_div_div);
			
			var ballList_li_div_div2 = '<div class="f12 gray_deep">单式投注 1注</div>';
			$("#ballList_li_div"+i).append(ballList_li_div_div2);
				
			var oneItme = tickets[i];
			
			var ballNo = oneItme.ballNo;
			
			var red = ballNo.split("/")[0];
			
			red = red.substring(1,red.length-1);
			
			var bule = ballNo.split("/")[1];
			
			bule = bule.substring(1,bule.length-1);
			
			var redArray = red.split(",");
			
			var buleArray = bule.split(",");
			
			for (var j = 0; j < redArray.length; j++) {
				var lii = "<i>"+redArray[j]+"</i>";
				$("#ballList_li_div_div"+i).append(lii);
			}
			
			for (var k = 0; k < buleArray.length; k++) {
				var lii = "<i class='blue'>"+buleArray[k]+"</i>";
				$("#ballList_li_div_div"+i).append(lii);
			}
			
		}
		this.getTotalMoney(this.moneyNameId,"DLT");
		
	},
	reloadQlc : function(ball){
		
		var ballNos = localStorage.getItem("QLC");
		
		if(ballNos == null){
			return ;
		}
		
		var result = JSON.parse(ballNos);
		
		var tickets = result.tickets;
		
		$("#ballList").html("");
		for (var i = tickets.length - 1; i >= 0; i--) {
			var ballList_li = "<li class='clearfix add' id='ballList_li"+i+"'></li>";
			$("#ballList").append(ballList_li);
			
			var ballList_li_div = "<div class='gw_cartleft' id='ballList_li_div"+i+"'></div>";
			$("#ballList_li"+i).append(ballList_li_div);
			
			var ballList_li_a = "<a href='#' onclick=shoppingCart1.deleteBall("+i+","+"'QLC'"+","+"'ballList_li"+i+"','"+tickets[i].id+"')> <div class='gw_close'> <em> </em> </div> </a>";
			$("#ballList_li"+i).append(ballList_li_a);
			
			var ballList_li_div_div = "<div class='gw_cartnum' id='ballList_li_div_div"+i+"'></div>";
			$("#ballList_li_div"+i).append(ballList_li_div_div);
			
			var ballList_li_div_div2 = '<div class="f12 gray_deep">单式投注 1注</div>';
			$("#ballList_li_div"+i).append(ballList_li_div_div2);
				
			var oneItme = tickets[i];
			
			var ballNo = oneItme.ballNo;
			
			var redArray = ballNo.split(",");
			
			for (var j = 0; j < redArray.length; j++) {
				var lii = "<i>"+redArray[j]+"</i>";
				$("#ballList_li_div_div"+i).append(lii);
			}
		}
		this.getTotalMoney(this.moneyNameId,"QLC");
	},
	reloadD3 : function(ball){
		
		var ballNos = localStorage.getItem("D3");
		
		if(ballNos == null){
			return ;
		}
		
		var result = JSON.parse(ballNos);
		
		var tickets = result.tickets;
		$("#ballList").html("");
		for (var i = tickets.length - 1; i >= 0; i--) {
			
			var ballList_li = "<li class='clearfix add' id='ballList_li"+i+"'></li>";
			$("#ballList").append(ballList_li);
			
			var ballList_li_div = "<div class='gw_cartleft' id='ballList_li_div"+i+"'></div>";
			$("#ballList_li"+i).append(ballList_li_div);
			
			var ballList_li_a = "<a href='#' onclick=shoppingCart1.deleteBall("+i+","+"'D3'"+","+"'ballList_li"+i+"','"+tickets[i].id+"')> <div class='gw_close'> <em> </em> </div> </a>";
			$("#ballList_li"+i).append(ballList_li_a);
			
			var ballList_li_div_div = "<div class='gw_cartnum' id='ballList_li_div_div"+i+"'></div>";
			$("#ballList_li_div"+i).append(ballList_li_div_div);
			
			var ballList_li_div_div2 = '<div class="f12 gray_deep">单式投注 1注</div>';
			$("#ballList_li_div"+i).append(ballList_li_div_div2);
				
			var oneItme = tickets[i];
			
			var ballNo = oneItme.ballNo;
			
			var redArray = ballNo.split(":");
			
			for (var j = 0; j < redArray.length; j++) {
				var lii = "<i>"+redArray[j]+"</i>";
				$("#ballList_li_div_div"+i).append(lii);
			}
		}
		this.getTotalMoney(this.moneyNameId,"D3");
	}
	
	
}

