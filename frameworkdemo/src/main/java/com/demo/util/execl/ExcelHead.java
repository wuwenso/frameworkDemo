package com.demo.util.execl;



public class ExcelHead implements Comparable<ExcelHead> {
	private String title;
	private int order;
	private String propertyName;
	private String datePattern;
	private String numberPattern;
	
	public String getNumberPattern() {
		return numberPattern;
	}

	public void setNumberPattern(String numberPattern) {
		this.numberPattern = numberPattern;
	}

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String methodName) {
		this.propertyName = methodName;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int compareTo(ExcelHead o) {
		ExcelHead target = o;
		ExcelHead self = this;
		return self.getOrder() > target.getOrder() ? 1
				: (self.getOrder() == target.getOrder() ? 0 : -1);
	}

	public ExcelHead() {
		super();
	}


	public ExcelHead(String title, int order, String propertyName,
			String datePattern, String numberPattern) {
		super();
		this.title = title;
		this.order = order;
		this.propertyName = propertyName;
		this.datePattern = datePattern;
		this.numberPattern = numberPattern;
	}

	@Override
	public String toString() {
		return "ExcelHead [title=" + title + ", order=" + order
				+ ", propertyName=" + propertyName + ", datePattern="
				+ datePattern + "]";
	}


}
