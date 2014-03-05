package com.edusofter.educv.model;

import com.edusofter.educv.view.CVListItem;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "education")
public class Education implements CVListItem{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(canBeNull = false)
	private String startDate;
	@DatabaseField
	private String endDate;
	@DatabaseField(canBeNull = false)
	private String name;
	@DatabaseField(columnName = "description")
	private String desc;
	@DatabaseField(canBeNull = false)
	private String lang;

	public Education(){
		
	}
	
	public Education(String name, String desc, String startDate, String endDate, String lang){
		this.name = name;
		this.desc = desc;
		this.startDate = startDate;
		this.endDate = endDate;
		this.lang = lang;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(id + " " + name + " " + desc);
		return sb.toString();
	}

	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String getEmployer() {
		return "";
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	

}
