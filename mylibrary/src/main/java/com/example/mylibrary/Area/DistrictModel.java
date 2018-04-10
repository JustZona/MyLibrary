package com.example.mylibrary.Area;

public class DistrictModel {
	private String name;
	private String area_id;
	private String pid;
	private String sort;
	
	public DistrictModel() {
		super();
	}

	public DistrictModel(String name,String area_id,String pid,String sort) {
		super();
		this.name = name;
		this.area_id = area_id;
		this.pid = pid;
		this.sort = sort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "DistrictModel [name=" + name + "]";
	}

}
