package com.edusofter.educv.model;

import com.edusofter.educv.view.CVListItem;
import com.j256.ormlite.field.DatabaseField;

public class Experience implements CVListItem{
	
	
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
	@DatabaseField
	private String employer;
	
	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
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

	public String getDesc() {
		return desc;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public Experience(){
		
	}
	
	public Experience(String name, String desc, String startDate, String endDate, String employer,String language){
		this.name = name;
		this.desc = desc;
		this.startDate = startDate;
		this.endDate = endDate;
		this.lang = language;
		this.employer = employer;
	}
}
