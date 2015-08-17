/* [export] */
/**
 * Created by pangxin on 15-3-18.
 */

MyAccount = {
	path : "",
	bindMobileFlag : false,
	bindBankFlag : false,
	bindNameFlag : false,
	amount : 0,
	init : function() {

		var result = Util.ajaxPost(MyAccount.path + "/home/user/getUserAccountDetail", {});

		var resultDate = Util.toJson(result);
		
		this.bindMobileFlag = resultDate.bindMobileFlag;
		this.bindBankFlag = resultDate.bindBankFlag;
		this.bindNameFlag = resultDate.bindNameFlag;

		this.amount = resultDate.amount;
		
		// 设置余额
		$("#totalInteger").html(resultDate.total.split(".")[0]);
		$("#total").append("." + resultDate.total.split(".")[1] + "元");
		// 可提现金额
		$("#amountInteger").html(resultDate.amount.split(".")[0]);
		$("#amount").append("." + resultDate.amount.split(".")[1]);
		// 不可提现
		$("#couponInteger").html(resultDate.coupon.split(".")[0]);
		$("#coupon").append("." + resultDate.coupon.split(".")[1]);

		// 累计资产
		$("#assetsTotalInteger").html(resultDate.assetsTotal.split(".")[0]);
		$("#assetsTotal").append(
				"." + resultDate.assetsTotal.split(".")[1] + "元");

		// 月累计资产
		$("#assetsTotalMonthInteger").html(
				resultDate.assetsTotalMonth.split(".")[0]);
		$("#assetsTotalMonth").append(
				"." + resultDate.assetsTotalMonth.split(".")[1] + "元");

		var totalMoney = '';

		var assetsArray = resultDate.assets;

		var legendArray = new Array();

		// legendArray.push()

		var dataArray = new Array();

		// 初始化表格
		for (var i = 0; i < assetsArray.length; i++) {
			var row = "<tr><td width='40' class='fd1'><img src="
					+ assetsArray[i].pictureURL + "></td><td width='228'>"
					+ assetsArray[i].name + "</td><td>"
					+ assetsArray[i].assetstotal + "元</td></tr>";
			$("#agent").append(row);
			totalMoney += assetsArray[i].assetstotal;
			// 处理表格数据
			legendArray.push(assetsArray[i].name);
			var objTemp = new Object();
			objTemp.value = assetsArray[i].assetstotal;
			objTemp.name = assetsArray[i].name;
			dataArray.push(objTemp);

		}

		if (totalMoney == '') {
			totalMoney = 0.00;
		}

		$("#totalMoney").html("累计资产：" + totalMoney + " 元");
		
		require.config({
			paths : {
				echarts : 'http://echarts.baidu.com/build/dist'
			}
		});

		// 使用
		require([ 'echarts', 'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载\
		'echarts/chart/pie' ], function(ec) {
			// 基于准备好的dom，初始化echarts图表
			var myChart = ec.init(document.getElementById('main'));

			option = {
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b} : {c} ({d}%)"
				},
				legend : {
					orient : 'vertical',
					x : 'left',
					data : legendArray
				},
				calculable : true,
				series : [ {
					name : '访问来源',
					type : 'pie',
					center : [ '50%', '40%' ],
					radius : [ 50, 80 ],
					itemStyle : {
						normal : {
							label : {
								show : false
							},
							labelLine : {
								show : false
							}
						},
						emphasis : {
							label : {
								show : true,
								position : 'center',
								textStyle : {
									fontSize : '30',
									fontWeight : 'bold'
								}
							}
						}
					},
					data : dataArray
				} ]
			};

			// 为echarts对象加载数据
			myChart.setOption(option);
		});

	},
	getLotteryInfo : function(time) {
		// 总金额
		lotteryMoney = Util.ajaxGet(MyAccount.path
				+ "/home/user/userPrizeMoney", {});
		var lotteryMoneyDate = Util.toJson(lotteryMoney);
		$("#prizeTotalMoney").html("中奖累计：￥" + lotteryMoneyDate.lotteryMoney+"元");

		// 按照月查询数据
		lotteryMonth = Util.ajaxGet(MyAccount.path
				+ "/home/userPrize/userPrizeCount", {
			selectType : 'month'
		});

		var lotteryMonthData = Util.toJson(lotteryMonth);
		var lotteryMonthDataArray = lotteryMonthData.list;

		// 设置值
		var dataTitle = [];
		var dataContent = [];

		for (var i = 0; i < lotteryMonthDataArray.length; i++) {
			dataTitle.push(lotteryMonthDataArray[i].times);
			dataContent.push(lotteryMonthDataArray[i].money);
		}

		
		if(dataContent.length==0){
			$("#main1").html("<div style='margin:100px 50px 50px 250px'>暂无数据</div>");
			return;
		}
		
		require.config({
			paths : {
				echarts : 'http://echarts.baidu.com/build/dist'
			}
		});

		// 使用
		require([ 'echarts', 'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载\
		'echarts/chart/pie' ], function(ec) {
			// 基于准备好的dom，初始化echarts图表
			var myChart1 = ec.init(document.getElementById('main1'));

			option = {
				tooltip : {
					trigger : 'axis'
				},
				calculable : true,
				xAxis : [ {
					type : 'category',
					data : dataTitle
				} ],
				yAxis : [ {
					type : 'value'
				} ],
				series : [ {
					name : '中奖金额',
					type : 'bar',
					data : dataContent,
					/*markPoint : {
						data : [ {
							type : 'max',
							name : '最大值'
						}, {
							type : 'min',
							name : '最小值'
						} ]
					},
					markLine : {
						data : [ {
							type : 'average',
							name : '平均值'
						} ]
					}*/
				} ]
			};
			// 为echarts对象加载数据
			myChart1.setOption(option);
		});

	},
	withdraw:function(){
		if(this.amount <= 1){
			Util.showErrorMessage("可提现金额必须大于1元！");
			return false;
		}
		if(this.bindMobileFlag && this.bindBankFlag && this.bindNameFlag){
			window.location.href = this.path+'/location/user?path=person/myAccount/withdrawCash';
		}else{
			Util.showErrorMessage("请先完善基本信息！");
		}
	}
}