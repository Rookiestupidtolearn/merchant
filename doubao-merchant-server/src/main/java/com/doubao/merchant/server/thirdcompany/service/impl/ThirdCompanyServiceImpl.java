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
import java.util.List;
import java.util.Map;

@Service
public class ThirdCompanyServiceImpl implements ThirdCompanyService{


	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private ThirdCompanyMapper thirdCompanyMapper;



	@Override
	public List<ThridCompany> getThridCompanyByAppId(String appId) {
		return thirdCompanyMapper.getThridCompanyByAppId(appId);
	}

	/**
	 * 生成公私钥
	 * @param appId
	 * @return
	 */
	@Override
	public Map<String, Object> genKeyPair(String appId) {
		List<ThridCompany> list =getThridCompanyByAppId(appId);
		Map<String, Object> resMap = new HashMap<>();
		if (null==list || list.size()==0){
			LOGGER.info("没有 appId 为 {} 的 商户 ",appId );
			resMap.put("msg", "没有该用户");
			return resMap;
		}
		if(list.size()>1){
			LOGGER.info("异常用户用户id{}",appId );
			resMap.put("msg", "异常用户");
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
		ThridCompany thridCompany=list.get(0);
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
		List<ThridCompany> list= getThridCompanyByAppId(appId);
		Map<String, Object> resMap = new HashMap<>();
		if (null==list || list.size()==0){
			LOGGER.info("没有 appId 为 {} 的 商户 ",appId );
			resMap.put("msg","没有该用户");
			return resMap;
		}
		if(list.size()>1){
			LOGGER.info("异常用户用户id{}",appId );
			resMap.put("msg", "异常用户");
			return resMap;
		}
		ThridCompany thridCompany=list.get(0);
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
		List<ThridCompany> list = getThridCompanyByAppId(appId);
		Map<String, Object> reMap = new HashMap<>();
		if (null==list || list.size()==0){
			LOGGER.info("没有 appId 为 {} 的 商户 ",appId );
			reMap.put("msg", "没有该用户");
			return reMap;
		}
		if(list.size()>1){
			LOGGER.info("异常用户用户id{}",appId );
			reMap.put("msg", "异常用户");
			return reMap;
		}
		ThridCompany thridCompany=list.get(0);
		String publicKey = thridCompany.getPublicKey();
		String privateKey = thridCompany.getPublicKey();

		reMap.put("publicKey", publicKey);
		reMap.put("privateKey", privateKey);
		return reMap;
	}
	@Override
	public List<ThridCompany> getList() {
		return thirdCompanyMapper.findList();
	}

	@Override
	public void update(ThridCompany thridCompany) {

		thirdCompanyMapper.updateByPrimaryKeySelective(thridCompany);
	}

	@Override
	public void insert(ThridCompany thridCompany) {
		thridCompany.setCreateDate(new Date());
		thridCompany.setUpdateDate(new Date());
		thridCompany.setStatus("1"); // 1可用
		thirdCompanyMapper.insert(thridCompany);
		if (thridCompany.getPrivateKey() == null || thridCompany.getPublicKey() == null) {
			genKeyPair(thridCompany.getAppid());//生成公司钥
		}
	}

}
