package com.example.mylibrary.Area;

import java.util.List;

public class CityModel {
	private String name;
	private List<DistrictModel> districtList;
	private String area_id;
	private String pid;
	private String sort;
	
	public CityModel() {
		super();
	}

	public CityModel(String name, List<DistrictModel> districtList,String area_id,String pid,String sort) {
		super();
		this.name = name;
		this.districtList = districtList;
		this.area_id = area_id;
		this.pid = pid;
		this.sort = sort;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DistrictModel> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<DistrictModel> districtList) {
		this.districtList = districtList;
	}

	@Override
	public String toString() {
		return "CityModel [name=" + name + ", districtList=" + districtList
				+ "]";
	}
	
}
