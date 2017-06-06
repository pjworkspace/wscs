/**
 * 文件名:ProvinceDataUtil.java
 * 版本信息:1.0
 * 日期:2015-5-30
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.xxx.market.web.core.cities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: wjun.java@gmail.com
 * @date:2015-5-30
 */
public final class ProvinceDataUtil {
	
	 /**
     * 所有省
     */
	public static String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
	public static Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
	public static Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
	public static Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

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
	public static String mCurrentDistrictName = "";

    /**
     * 当前区的邮政编码
     */
	public static String mCurrentZipCode = "";

	public static List<ProvinceModel> provinceList = new ArrayList<ProvinceModel>();

	public static List<ProvinceModel> getProvinceList() {
		return provinceList;
	}

	public static void setProvinceList(List<ProvinceModel> provinceList) {
		ProvinceDataUtil.provinceList = provinceList;
		initProvinceDatas();
	}
	
	private static void initProvinceDatas() {
        //*/ 初始化默认选中的省、市、区
        if (provinceList != null && !provinceList.isEmpty()) {
            mCurrentProviceName = provinceList.get(0).getName();
            List<CityModel> cityList = provinceList.get(0).getCityList();
            if (cityList != null && !cityList.isEmpty()) {
                mCurrentCityName = cityList.get(0).getName();
                List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                mCurrentDistrictName = districtList.get(0).getName();
                mCurrentZipCode = districtList.get(0).getZipcode();
            }
        }
        //*/
        mProvinceDatas = new String[provinceList.size()];
        for (int i = 0; i < provinceList.size(); i++) {
            // 遍历所有省的数据
            mProvinceDatas[i] = provinceList.get(i).getName();
            List<CityModel> cityList = provinceList.get(i).getCityList();
            String[] cityNames = new String[cityList.size()];
            for (int j = 0; j < cityList.size(); j++) {
                // 遍历省下面的所有市的数据
                cityNames[j] = cityList.get(j).getName();
                List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                String[] distrinctNameArray = new String[districtList.size()];
                DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                for (int k = 0; k < districtList.size(); k++) {
                    // 遍历市下面所有区/县的数据
                    DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                    // 区/县对于的邮编，保存到mZipcodeDatasMap
                    mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                    distrinctArray[k] = districtModel;
                    distrinctNameArray[k] = districtModel.getName();
                }
                // 市-区/县的数据，保存到mDistrictDatasMap
                mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
            }
            // 省-市的数据，保存到mCitisDatasMap
            mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
        }

    }
	
}
