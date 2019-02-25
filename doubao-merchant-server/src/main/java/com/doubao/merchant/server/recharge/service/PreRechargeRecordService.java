package com.doubao.merchant.server.recharge.service;

import com.doubao.merchant.api.recharge.entity.ThirdPreCompanyRechargeRecord;
import java.util.List;
public interface PreRechargeRecordService {

	List<ThirdPreCompanyRechargeRecord> getPreRechargeRecordByThirdOrder(String thridOrder, String appId);
	
	ThirdPreCompanyRechargeRecord getPreRechargeRecordByOrderNo(String orderNo);
	
	Boolean updateByPrimaryKeySelective(ThirdPreCompanyRechargeRecord record);
	
	Boolean savePreRechargeRecord(ThirdPreCompanyRechargeRecord reRechargeRecord);
	
}
