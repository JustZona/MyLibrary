package com.example.mylibrary.Area;

import java.util.List;

public class ProvinceModel {
	private String name;
	private List<CityModel> cityList;
	private String area_id;
	private String pid;
	private String sort;

	
	public ProvinceModel() {
		super();
	}

	public ProvinceModel(String name, List<CityModel> cityList,String area_id,String pid,String sort) {
		super();
		this.name = name;
		this.cityList = cityList;
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

	public List<CityModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityModel> cityList) {
		this.cityList = cityList;
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
		return "ProvinceModel [name=" + name + ", cityList=" + cityList + "]";
	}
	
}
