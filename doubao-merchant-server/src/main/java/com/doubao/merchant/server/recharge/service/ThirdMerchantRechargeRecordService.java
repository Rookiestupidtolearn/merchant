package com.doubao.merchant.server.recharge.service;

import com.doubao.merchant.api.recharge.entity.ThirdMerchantRechargeRecord;

/** 
 * @ClassName: ThirdMerchantRechargeRecordService 
 * @Description: 
 * @author ycs
 * @date 2019年1月10日 下午5:02:41
 *
 */
public interface ThirdMerchantRechargeRecordService {
	
	int saveThirdMerchantRechargeRecord(ThirdMerchantRechargeRecord record);
	
	int updateThirdMerchantRechargeRecord(ThirdMerchantRechargeRecord record, String orderNo);
	
	ThirdMerchantRechargeRecord getThirdMerchantRechargeRecord(ThirdMerchantRechargeRecord record);

}
