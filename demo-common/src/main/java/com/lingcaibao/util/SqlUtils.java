package com.lingcaibao.util;

public class SqlUtils {
	/**
	 * 构建排序SQL
	 * 排序，默认按ID倒序，参数名称必须为数据库字段名，多个排序字段以-间隔，行如：_id-name_
	 * @param sort
	 * @return
	 */
	public static String buildSort(String sort) {
		StringBuffer strf = new StringBuffer();
		int i=0;
		for (String sortStr : sort.split("-")) {
			if (i!=0) {
				strf.append(",");
			}
			if (sortStr.startsWith("_")) {// 以下划线开始为升序
				strf.append(sortStr.substring(1)).append(" ASC ");
			} else if (sortStr.endsWith("_")) {// 以下划线结束为降序
				strf.append(sortStr.substring(0, sortStr.length() - 1)).append( " DESC ");
			}
			i++;
		}
		return strf.toString();
	}
}
