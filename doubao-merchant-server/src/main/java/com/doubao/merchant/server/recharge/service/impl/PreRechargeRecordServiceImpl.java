package com.doubao.merchant.server.recharge.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.doubao.merchant.api.recharge.entity.ThirdPreCompanyRechargeRecord;
import com.doubao.merchant.server.recharge.dao.PreRechargeRecordMapper;
import com.doubao.merchant.server.recharge.service.PreRechargeRecordService;

@Service
public class PreRechargeRecordServiceImpl implements PreRechargeRecordService {
	
	@Autowired
	private PreRechargeRecordMapper preRechargeRecordMapper;

	@Override
	public List<ThirdPreCompanyRechargeRecord> getPreRechargeRecordByThirdOrder(
			String thridOrder, String appId) {
		return preRechargeRecordMapper.getPreRechargeRecordByThirdOrder(thridOrder,appId);
	}

	@Override
	public Boolean updateByPrimaryKeySelective(
			ThirdPreCompanyRechargeRecord record) {
		return preRechargeRecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Boolean savePreRechargeRecord(
			ThirdPreCompanyRechargeRecord reRechargeRecord) {
		
		return preRechargeRecordMapper.savePreRechargeRecord(reRechargeRecord);
	}

	@Override
	public ThirdPreCompanyRechargeRecord getPreRechargeRecordByOrderNo(
			String orderNo) {

		return preRechargeRecordMapper.getByOrderNo(orderNo);
	}

}
