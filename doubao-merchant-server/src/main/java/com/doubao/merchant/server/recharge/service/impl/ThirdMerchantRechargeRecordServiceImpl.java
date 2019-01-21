package com.doubao.merchant.server.recharge.service.impl;

import java.util.List;

import com.doubao.merchant.server.recharge.dao.ThirdMerchantRechargeRecordMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doubao.merchant.api.recharge.entity.ThirdMerchantRechargeRecord;
import com.doubao.merchant.server.recharge.service.ThirdMerchantRechargeRecordService;

/** 
 * @ClassName: ThirdMerchantRechargeRecordServiceImpl 
 * @Description: 
 * @author ycs
 * @date 2019年1月10日 下午5:02:57
 *
 */
@Service
public class ThirdMerchantRechargeRecordServiceImpl implements
		ThirdMerchantRechargeRecordService {

	@Autowired
	private ThirdMerchantRechargeRecordMapper thirdMerchantRechargeRecordMapper;

	@Override
	public int saveThirdMerchantRechargeRecord(
			ThirdMerchantRechargeRecord record) {
		return thirdMerchantRechargeRecordMapper.saveThirdMerchantRechargeRecord(record);
	}

	@Override
	public int updateThirdMerchantRechargeRecord(ThirdMerchantRechargeRecord record, String orderNo) {
				record.setOrderNo(orderNo);
		return thirdMerchantRechargeRecordMapper.updateByOrderNo(record);
	}

	@Override
	public ThirdMerchantRechargeRecord getThirdMerchantRechargeRecord(
			ThirdMerchantRechargeRecord record) {
		List<ThirdMerchantRechargeRecord> selectByOrderNo = thirdMerchantRechargeRecordMapper.selectByOrderNo(record);
		if(selectByOrderNo == null || selectByOrderNo.size() == 0){
			return null;
		}
		return selectByOrderNo.get(0);
	}

}
