package com.doubao.merchant.server.thirdcompany.service.impl;

import com.doubao.merchant.server.util.rea.RSA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doubao.merchant.api.recharge.entity.ThridCompany;
import com.doubao.merchant.server.thirdcompany.service.ThirdCompanyService;
import com.doubao.merchant.server.thirdcompany.service.dao.ThirdCompanyMapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ThirdCompanyServiceImpl implements ThirdCompanyService{


	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private ThirdCompanyMapper thirdCompanyMapper;



	@Override
	public ThridCompany getThridCompanyByAppId(String appId) {
		return thirdCompanyMapper.getThridCompanyByAppId(appId);
	}

	@Override
	public Map<String, Object> genKeyPair(String appId) {
		ThridCompany thridCompany =getThridCompanyByAppId(appId);
		Map<String, Object> resMap = new HashMap<>();
		if (null == thridCompany){
			LOGGER.info("没有 appId 为 {} 的 商户 ",appId );
			resMap.put("msg", "没有该用户");
			return resMap;
		}
		Map<String, String> keyPair = new HashMap<>();
		try {
			keyPair = RSA.generateKeyPair();
		} catch (Exception e) {
			LOGGER.error("用户{}生成秘钥失败,原因 ： ",appId,e );
			resMap.put("msg", "生成秘钥失败");
			return resMap;
		}
		String publicKey = keyPair.get("publicKey");
		String privateKey = keyPair.get("privateKey");
		thridCompany.setPrivateKey(privateKey);
		thridCompany.setPublicKey(publicKey);
		thridCompany.setUpdateDate(new Date());
		int res = thirdCompanyMapper.updateByPrimaryKeySelective(thridCompany);
		if (res<1){
			resMap.put("mes","更新操作失败");
			return resMap;
		}
		resMap.put("publicKey", publicKey);
		resMap.put("privateKey", privateKey);
		return resMap;
	}

	@Override
	public Map<String, Object> updateCallBackUrl(String appId, String callBackUrl) {
		ThridCompany thridCompany = getThridCompanyByAppId(appId);
		Map<String, Object> resMap = new HashMap<>();
		if (null == thridCompany){
			LOGGER.info("没有 appId 为 {} 的 商户 ",appId );
			resMap.put("msg","没有该用户");
			return resMap;
		}
		thridCompany.setCallBackUrl(callBackUrl);
		thridCompany.setUpdateDate(new Date());
		int res = thirdCompanyMapper.updateByPrimaryKeySelective(thridCompany);

		if (res<1){
			resMap.put("msg","appid:"+ appId + "数据操作失败");
			return resMap;
		}
		resMap.put("msg", "success");
		return resMap;
	}


	@Override
	public Map<String, Object> getKeyPair(String appId) {
		ThridCompany thridCompany = getThridCompanyByAppId(appId);
		Map<String, Object> reMap = new HashMap<>();
		if (null == thridCompany){
			LOGGER.info("没有 appId 为 {} 的 商户 ",appId );
			reMap.put("msg", "没有该用户");
			return reMap;
		}
		String publicKey = thridCompany.getPublicKey();
		String privateKey = thridCompany.getPublicKey();

		reMap.put("publicKey", publicKey);
		reMap.put("privateKey", privateKey);
		return reMap;
	}

}
