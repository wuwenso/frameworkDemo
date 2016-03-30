package com.demo.persistence.dialect.impl;

import java.util.List;

import com.demo.persistence.dialect.Dialect;
import com.demo.persistence.dialect.entity.PageResult;
import com.demo.persistence.dialect.entity.PageSortBean;


public class MySqlDialect extends Dialect{

//	@Override
	public String buildPageSql(String sql, PageResult<?> page) {
		StringBuilder pageSql = new StringBuilder(" ");
		int pageSize = page.getPageSize();
		int beginrow = (page.getCurrentPageNo() - 1) * page.getPageSize();
		List<PageSortBean> sortBeanList = page.getSortBeanList();
		if (sortBeanList != null
				&& sortBeanList.size() > 0 ){
			pageSql.append(" order by ");
			for (PageSortBean pageSortBean : sortBeanList){
				pageSql.append(" "+pageSortBean.getSortField()+" "+ pageSortBean.getSortType().getType());
			}
		}
		if (pageSize > 0){
			pageSql.append(" limit ");
			if (beginrow > 0) {
				pageSql.append(beginrow).append(",").append(pageSize);
			} else {
				pageSql.append(pageSize);
			}
		}
		String resSql = sql + pageSql.toString();
		if (sql.indexOf("<pageSql>") > 0){
			resSql = sql.replace("<pageSql>", "").replace("</pageSql>", pageSql.toString());
		}
		return resSql;
	}

}
