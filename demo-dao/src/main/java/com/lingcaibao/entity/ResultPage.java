package com.lingcaibao.entity;

import java.util.ArrayList;
import java.util.List;

import com.lingcaibao.plugin.page.Page;

/**
 * 转换类
 * @author nzh
 *
 */
public class ResultPage {
	/** 总数 */
	private long total;
	/** 总页数 */
	private int pages;
	/** 页码，从1开始 */
	private int pageNum;
	/** 页面大小 */
	private int pageSize;
	/** 起始行 */
	private int startRow;
	/** 末行 */
	private int endRow;
	/** 返回结果列表 */
	private List<?> list = new ArrayList<Object>();

	public ResultPage() {

	}

	@SuppressWarnings("rawtypes")
	public ResultPage(Page page) {
		this.total = page.getTotal();
		this.pages = page.getPages();
		this.pageNum = page.getPageNum();
		this.pageSize = page.getPageSize();
		this.startRow = page.getStartRow();
		this.startRow = page.getStartRow();
		this.list = page.getResult();
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

}
