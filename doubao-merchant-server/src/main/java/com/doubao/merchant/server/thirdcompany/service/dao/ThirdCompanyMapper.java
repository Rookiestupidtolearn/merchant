package com.doubao.merchant.server.thirdcompany.service.dao;

import org.apache.ibatis.annotations.Mapper;

import com.doubao.merchant.api.recharge.entity.ThridCompany;

import feign.Param;

import java.util.List;

/** 
 * @ClassName: ThirdCompanyMapper 
 * @Description: 
 * @author ycs
 * @date 2019年1月10日 下午3:13:21
 *
 */
@Mapper
public interface ThirdCompanyMapper {

	ThridCompany getThridCompanyByAppId(@Param("appId") String appId);

	int updateByPrimaryKeySelective(ThridCompany thridCompany);

	List<ThridCompany> findList();

	void insert(ThridCompany thridCompany);
}
