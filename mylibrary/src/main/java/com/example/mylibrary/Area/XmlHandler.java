package com.example.mylibrary.Area;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by zy on 2017/7/8.
 * 全国省市区三级地址数据.
 * 初始化:
 * XmlHandler.initProvinceDatas(context);
 * 获取省数据.
 * XmlHandler.mProvinceDatas
 * 获取市数据.
 * XmlHandler.mCitisDatasMap.get(province[position]);
 * 获取县/区
 * XmlHandler.mDistrictDatasMap.get(city[position]);
 */

public class XmlHandler {

    /**
     * 所有省
     */
    public static List<ProvinceModel> mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    public static Map<String, List<CityModel>> mCitisDatasMap = new HashMap<String, List<CityModel>>();
    /**
     * key - 市 values - 区
     */
    public static Map<String, List<DistrictModel>> mDistrictDatasMap = new HashMap<String, List<DistrictModel>>();


    /**
     * 当前省的名称
     */
    public static String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    public static String mCurrentCityName;
    /**
     * 当前区的名称
     */
    public static String mCurrentDistrictName ="";

    /**
     * 当前区的邮政编码
     */
    public static String mCurrentZipCode ="";

    public static void initProvinceDatas(Context context)
    {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = context.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList!= null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList!= null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                }
            }
            //*/
            mProvinceDatas = provinceList;
            for (int i=0; i< provinceList.size(); i++) {
                // 遍历所有省的数据
                List<CityModel> cityList = mProvinceDatas.get(i).getCityList();
                for (int j=0; j< cityList.size(); j++) {
                    CityModel cityModel = cityList.get(j);
                    mDistrictDatasMap.put(cityModel.getArea_id(), cityModel.getDistrictList());
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getArea_id(), cityList);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

}
