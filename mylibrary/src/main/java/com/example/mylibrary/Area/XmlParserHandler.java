package com.example.mylibrary.Area;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XmlParserHandler extends DefaultHandler {

	/**
	 * 存储所有的解析对象
	 */
	private List<ProvinceModel> provinceList = new ArrayList<ProvinceModel>();

	public XmlParserHandler() {

	}

	public List<ProvinceModel> getDataList() {
		return provinceList;
	}

	@Override
	public void startDocument() throws SAXException {
		// 当读到第一个开始标签的时候，会触发这个方法
	}

	ProvinceModel provinceModel = new ProvinceModel();
	CityModel cityModel = new CityModel();
	DistrictModel districtModel = new DistrictModel();

	@Override
	public void startElement(String uri, String localName, String qName,
							 Attributes attributes) throws SAXException {
		// 当遇到开始标记的时候，调用这个方法
		if (qName.equals("province")) {
			provinceModel = new ProvinceModel();
			provinceModel.setName(attributes.getValue(0));
			provinceModel.setCityList(new ArrayList<CityModel>());
			provinceModel.setArea_id(attributes.getValue(1));
			provinceModel.setPid(attributes.getValue(2));
			provinceModel.setSort(attributes.getValue(3));

		} else if (qName.equals("city")) {
			cityModel = new CityModel();
			cityModel.setName(attributes.getValue(0));
			cityModel.setDistrictList(new ArrayList<DistrictModel>());
			cityModel.setArea_id(attributes.getValue(1));
			cityModel.setPid(attributes.getValue(2));
			cityModel.setSort(attributes.getValue(3));
		} else if (qName.equals("district")) {
			districtModel = new DistrictModel();
			districtModel.setName(attributes.getValue(0));
			districtModel.setArea_id(attributes.getValue(1));
			districtModel.setPid(attributes.getValue(2));
			districtModel.setSort(attributes.getValue(3));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// 遇到结束标记的时候，会调用这个方法
		if (qName.equals("district")) {
			cityModel.getDistrictList().add(districtModel);
		} else if (qName.equals("city")) {
			provinceModel.getCityList().add(cityModel);
		} else if (qName.equals("province")) {
			provinceList.add(provinceModel);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}

}
