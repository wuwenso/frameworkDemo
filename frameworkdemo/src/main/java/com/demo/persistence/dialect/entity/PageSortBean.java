package com.demo.persistence.dialect.entity;


public class PageSortBean {
	public  enum SortEnum {
		DESC("desc"),ASC("asc");
		
		private String type;
		
		SortEnum(String type){
			this.type = type;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}
	private SortEnum sortType = SortEnum.ASC;

	private String sortField;

	public PageSortBean(SortEnum sortType, String sortField){
		this.sortType = sortType;
		this.sortField = sortField;
	}


	public SortEnum getSortType() {
		return sortType;
	}

	public void setSortType(SortEnum sortType) {
		this.sortType = sortType;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

}
