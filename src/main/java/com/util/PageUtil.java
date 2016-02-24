package com.util;


public abstract class PageUtil {
	
	public static int getStartIndex(int pageNo,int pageSize, int count) {
		int startIndex = getStartIndex(pageNo, pageSize);
		//矫正初始查询参数
		while(startIndex > count) {
			pageNo = pageNo - 1; 
			startIndex = getStartIndex(pageNo, pageSize);
		}
		return startIndex;
	}
	
	public static int getStartIndex(int page,int rows) {
		return (page - 1) * rows;
	}

}
