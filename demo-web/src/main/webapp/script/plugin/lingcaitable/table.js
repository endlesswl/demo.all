/* [export] */
/**
 * Created by pangxin on 15-3-20.
 */
function TableLc() {

}
//url:contextPath + "search.action",
//    datatype:"json", //数据来源，本地数据
//    mtype:"POST",//提交方式
//    height:420,//高度，表格高度。可为数值、百分比或'auto'
//    //width:1000,//这个宽度不能为百分比
//    autowidth:true,//自动宽
//    colNames:['添加日期', '手机号码', '银行卡号','备注','操作'],
//    colModel:[
//    //{name:'id',index:'id', width:'10%', align:'center' },
//    {name:'createDate',index:'createDate', width:'20%',align:'center'},
//    {name:'phoneNo',index:'phoneNo', width:'15%',align:'center'},
//    {name:'cardNo',index:'cardNo', width:'20%', align:"center"},
//    {name:'remark',index:'remark', width:'35%', align:"left", sortable:false},
//    {name:'del',index:'del', width:'10%',align:"center", sortable:false}
//],
//    rownumbers:true,//添加左侧行号
//    //altRows:true,//设置为交替行表格,默认为false
//    //sortname:'createDate',
//    //sortorder:'asc',
//    viewrecords: true,//是否在浏览导航栏显示记录总数
//    rowNum:15,//每页显示记录数
//    rowList:[15,20,25],//用于改变显示行数的下拉列表框的元素数组。
//    jsonReader:{
//    id: "blackId",//设置返回参数中，表格ID的名字为blackId
//        repeatitems : false
//},
//pager:$('#gridPager')

TableLc.prototype = {
    //path
    path: "",
    //插件名称
    divName: "",
    //请求路径
    url: "",
    //表格总体长度
    height: "",
    //表格总体宽度
    width: "800",
    //列名称
    colNames: [],
    //列的属性
    colModel: [],
    //添加左侧行号
    rownumbers: false,
    //是否交替
    altRows: true,
    //每页显示记录数
    rowNum: 15,
    //表格样式
    tableClass : "tab6 mt0",
    //瀑布模式
    flowFlag : "false",
    tableId :"lingcai_table",
    //加载的数量
    totalCount : 0,
    tableContentId:"tableContent",
    tableHeaderId:"tableHeader",
    
    init: function (obj) {
        if (obj.url != null) {
            this.url = obj.url;
        }
        if (obj.divName != null) {
            this.divName = obj.divName;
            this.tableId = "lctable_"+obj.divName;
            this.tableContentId = "content"+obj.divName;
            this.tableHeaderId = "header"+obj.divName;
            this.tableId = "lctable_"+obj.divName;
        }
        if (obj.height != null) {
            this.height = obj.height;
        }
        if (obj.width != null) {
            this.width = obj.width;
        }
        if (obj.colNames != null) {
            this.colNames = obj.colNames;
        }
        if (obj.colModel != null) {
            this.colModel = obj.colModel;
        }
        if (obj.rownumbers != null) {
            this.rownumbers = obj.rownumbers;
        }
        if (obj.altRows != null) {
            this.altRows = obj.altRows;
        }
        if (obj.rowNum != null) {
            this.rowNum = obj.rowNum;
        }
        if(obj.tableClass != null){
        	this.tableClass = obj.tableClass;
        }
        if(obj.flowFlag != null){
        	this.flowFlag = obj.flowFlag;
        }
        
        //初始化表格
    	$("#"+this.tableId).remove();
        var tableStart = "<table id='"+this.tableId+"' width=" + this.width + " border='0' cellspacing='0' cellpadding='0' class='"+this.tableClass+"'> </table>";
        $("#" + this.divName).append(tableStart);
        //初始化表头
        $("#"+this.tableId).append("<tr id='"+this.tableHeaderId+"'></tr>");
        //初始化表头内容内容数据
        for (var i = 0; i < this.colNames.length; i++) {
            //这里判断, 并且添加列表宽度
            //align:'center'
            var columnName = "";
            columnName = "<th width='" + this.colModel[i].width + "'>" + this.colNames[i] + "</th>";
            $("#"+this.tableHeaderId).append(columnName);
        }

    },
    setDate: function (data) {

        if("false" == this.flowFlag){
          	 $("#"+this.tableId+" tr:gt(0)").remove();
        }
//        var result = JSON.parse(data);
        var rowNum = 0;
        var result = data;
        for(var i=this.totalCount;i<result.length+this.totalCount;i++){
            //读取一行
            var row = result[rowNum];
            rowNum++;
            //设置 TR
            if(this.altRows == true){
                var dataTr = "<tr id='"+this.tableContentId +i+"'></tr>";
                $("#"+this.tableId).append(dataTr);
            }else{
                var dataTr = "<tr id="+this.tableContentId +i+"></tr>";
                $("#"+this.tableId).append(dataTr);
            }

            for(var j=0;j<this.colModel.length;j++){

                var classNameTemp=this.colModel[j].className;
                if(this.colModel[j].name == "num"){
                    if(classNameTemp!=null && typeof(classNameTemp) != undefined){
                        $("#"+this.tableContentId +i).append("<td class='"+classNameTemp+"'>"+(i+1)+"</td>");
                        continue;
                    }else{
                        $("#"+this.tableContentId +i).append("<td>"+(i+1)+"</td>");
                        continue;
                    }
                }

                if(this.colModel[j].operate !=null && typeof(this.colModel[j].operate) != undefined){
                    var operrateStr = window[this.colModel[j].operate](row);
                    if(classNameTemp!=null && typeof(classNameTemp) != undefined){
                        $("#"+this.tableContentId +i).append("<td class='"+classNameTemp+"'>"+operrateStr+"</td>");
                    }else{
                        $("#"+this.tableContentId +i).append("<td>"+operrateStr+"</td>");
                    }
                    continue;
                }

                for(clu in row){
                    if(clu == this.colModel[j].name){
                        if(this.colModel[j].operate !=null && typeof(this.colModel[j].operate) != undefined){


                        }else{
                            if(classNameTemp!=null && typeof(classNameTemp) != undefined){
                                $("#"+this.tableContentId +i).append("<td class='"+classNameTemp+"'>"+row[clu]+"</td>");
                            }else{
                                $("#"+this.tableContentId +i).append("<td>"+row[clu]+"</td>");
                            }
                        }
                    }
                }
            }
        }
        if("false" == this.flowFlag){
            this.totalCount = 0;
        }else{
            this.totalCount=result.length+this.totalCount;
        }

    },
    getAltRows: function () {
        if (this.altRows == true) {
        } else {
        }
    },
    removeAll : function(){

    }
}

TableLcTemp = {
    //path
    path: "",
    //插件名称
    divName: "",
    //请求路径
    url: "",
    //表格总体长度
    height: "",
    //表格总体宽度
    width: "800",
    //列名称
    colNames: [],
    //列的属性
    colModel: [],
    //添加左侧行号
    rownumbers: false,
    //是否交替
    altRows: true,
    //每页显示记录数
    rowNum: 15,
    tableClass:"",
    flowFlag:"false"
}