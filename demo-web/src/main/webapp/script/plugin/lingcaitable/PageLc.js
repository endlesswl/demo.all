/* [export] */

/**
 * 零彩宝分页插件
 * 
 * @author LDL
 */

PageLc = {
	pageId : '',
	pageNum : 1,
	pageSize : 10,
	totalPage : 0,
	totalRow : 0,
	obj : {},
	init : function(obj) {
		PageLc.obj = obj;
		
		var p = '';
		var currPage = Number(PageLc.pageNum);
		
		//设置显示最大页号（当前往后加2页）
		var maxNum = currPage + 2;
		if (maxNum < 5) {
			maxNum = 5
		}
		
		//第一页
		if (currPage > 3) {
			//当前页大于3页时
			p += '<div class="page_wrap" onclick="PageLc.gotoPage('+1+')"><a href="javascript:void(0)">' + 1 + '</a></div>';
			if (currPage > 4 && PageLc.totalPage>=7) {
				// 左边"..."显示
				p += '<span class="page-split">...</span>';
			}
		}
		
		//起始页号=当前页往前2页
		var start = currPage - 2;
		//如果maxNum到最后一页，起始页号不变，始终等于totalPage-4
		if (PageLc.totalPage <= maxNum) {
			start = PageLc.totalPage - 4;
		}
		if(currPage>=4 && PageLc.totalPage<6){
			start=2;
		}
		
		for (var i = start; i <= PageLc.totalPage; i++) {
			if (i <= 0) {
				continue;
			}
			var className = "page_wrap";
			if (i == currPage) {
				className = "page_wrap page_cur";
			}
			
			if (i <= maxNum && currPage < maxNum) {
				p += '<div class="'+className+'" onclick="PageLc.gotoPage('+i+')"><a href="javascript:void(0)">' + i + '</a></div>';
			}
		}
		// 右边"..."显示
		if (PageLc.totalPage - currPage > 2 && PageLc.totalPage>5) {
			p += '<span class="page-split">...</span>';
		}
		
		//写入DIV
		var str = 
			'<div class="">'+
			'<div class="pagination_page">'+
			'<div class="page_wrap marR5" onclick="PageLc.gotoPage('+(currPage-1)+')"><a href="javascript:void">&lt;</a></div>'+p+
			'<div class="page_wrap" onclick="PageLc.gotoPage('+(currPage+1)+')"><a href="javascript:void">&gt;</a></div>'+
			'</div>'+
			'<div class="pagination_count"><span>共</span><span class="b">'+PageLc.totalPage+'</span><span>页，</span></div>'+
			'<div class="pagination_form"><label>去第</label>&nbsp;<input type="text" name="page" id="gotoPageId" value="" class="page_num"><label>&nbsp;页</label><div class="btn3"><input type="submit" onclick="PageLc.gotoPage()" value="确定"></div></div>'+
			'</div>';
		$("#"+PageLc.pageId).html(str);
	},
	//跳转页码
	gotoPage : function(pageNum) {
		if (pageNum == undefined) {
			pageNum = $("#gotoPageId").val();
		}
		if(isNaN(pageNum)){
			alert("请输入正确页码");
			return;
		}
		if (Number(pageNum) > PageLc.totalPage) {
			return;
		}
		if (Number(pageNum) <= 0) {
			pageNum = 1;
		}
		PageLc.obj.queryPage(pageNum);
	}
}

//
//PageLc.prototype = {
//	pageId : '',
//	pageNum : 1,
//	pageSize : 10,
//	totalPage : 0,
//	totalRow : 0,
//	init : function(obj) {
//		if (obj.pageId != null) {
//			PageLc.pageId = obj.pageId;
//		}
//		if (obj.pageNum != null) {
//			PageLc.pageNum = Number(obj.pageNum);
//		}
//		if (obj.pageSize != null) {
//			PageLc.pageSize = Number(obj.pageSize);
//		}
//		if (obj.totalPage != null) {
//			PageLc.totalPage = Number(obj.totalPage);
//		}
//		if (obj.totalRow != null) {
//			PageLc.totalRow = Number(obj.totalRow);
//		}
//		if (obj.totalRow != null) {
//			PageLc.totalRow = Number(obj.totalRow);
//		}
//	},
//	showPage : function() {
//		var p = '';
//		var currPage = PageLc.pageNum;
//		
//		//设置显示最大页号（当前往后加2页）
//		var maxNum = currPage + 2;
//		if (maxNum < 5) {
//			maxNum = 5
//		}
//		
//		//第一页
//		if (currPage > 3) {
//			//当前页大于3页时
//			p += '<div class="page_wrap" onclick="PageLc.obj.setQuery(\'\','+1+')"><a href="javascript:void(0)">' + 1 + '</a></div>';
//			if (currPage > 4) {
//				// 左边"..."显示
//				p += '<span class="page-split">...</span>';
//			}
//		}
//		
//		//起始页号=当前页往前2页
//		var n = currPage - 2;
//		//如果maxNum到最后一页，起始页号不变，始终等于totalPage-5
//		if (PageLc.totalPage <= maxNum) {
//			n = PageLc.totalPage - 5;
//		}
//		
//		for (var i = n; i <= PageLc.totalPage; i++) {
//			if (i <= 0) {
//				continue;
//			}
//			var className = "page_wrap";
//			if (i == currPage) {
//				className = "page_wrap page_cur";
//			}
//			
//			if (i <= maxNum && currPage < maxNum) {
//				p += '<div class="'+className+'" onclick="PageLc.obj.setQuery(\'\','+i+')"><a href="javascript:void(0)">' + i + '</a></div>';
//			}
//		}
//		// 右边"..."显示
//		if (PageLc.totalPage - currPage > 2) {
//			p += '<span class="page-split">...</span>';
//		}
//		
//		//写入DIV
//		var str = 
//			'<div class="">'+
//			'<div class="pagination_page">'+
//			'<div class="page_wrap marR5" onclick="PageLc.obj.setQuery(\'\','+(currPage-1)+')">&lt;</div>'+p+
//			'<div class="page_wrap" onclick="PageLc.obj.setQuery(\'\','+(currPage+1)+')">&gt;</div>'+
//			'</div>'+
//			'<div class="pagination_count"><span>共</span><span class="b">'+PageLc.totalPage+'</span><span>页，</span></div>'+
//			'<div class="pagination_form"><label>去第</label>&nbsp;<input type="text" name="page" id="gotoPageId" value="" class="page_num"><label>&nbsp;页</label><div class="btn3"><input type="submit" onclick="PageLc.obj.gotoPage()" value="确定"></div></div>'+
//			'</div>';
//		$("#"+PageLc.pageId).html(str);
//	}
//}
