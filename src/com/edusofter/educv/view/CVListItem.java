package com.edusofter.educv.view;

public interface CVListItem {
	public static final String LANG_ES = "es";
	public static final String LANG_EN = "en";
	public static final String LANG_COL = "lang";
	
	public String getName();
	public String getDesc();
	public String getStartDate();
	public String getEndDate();
	public String getEmployer();
	public int getId();

}
