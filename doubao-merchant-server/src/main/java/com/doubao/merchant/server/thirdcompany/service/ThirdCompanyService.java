package com.doubao.merchant.server.thirdcompany.service;

import com.doubao.merchant.api.recharge.entity.ThridCompany;

import java.util.List;
import java.util.Map;

public interface ThirdCompanyService {
	
	ThridCompany getThridCompanyByAppId(String appId);

	Map<String,Object> genKeyPair(String appId);

	Map<String, Object> getKeyPair(String appId);

	Map<String,Object> updateCallBackUrl(String appId, String callBackUrl);
	List<ThridCompany> getList();

	void update(ThridCompany thridCompany);

	void insert(ThridCompany thridCompany);
}
