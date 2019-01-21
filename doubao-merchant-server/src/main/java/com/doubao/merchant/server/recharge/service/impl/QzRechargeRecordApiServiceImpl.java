package com.doubao.merchant.server.recharge.service.impl;

import com.doubao.merchant.server.recharge.dao.QzRechargeRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doubao.merchant.api.recharge.entity.QzRechargeRecordEntity;
import com.doubao.merchant.server.recharge.service.QzRechargeRecordApiService;

/** 
 * @ClassName: QzRechargeRecordApiServiceImpl 
 * @Description: 
 * @author ycs
 * @date 2019年1月10日 下午5:13:00
 *
 */
@Service
public class QzRechargeRecordApiServiceImpl implements QzRechargeRecordApiService{

	@Autowired
	private QzRechargeRecordMapper qzRechargeRecordMapper;
	@Override
	public int saveQzRechargeRecord(QzRechargeRecordEntity qzRechargeRecordEntity) {
	
		return qzRechargeRecordMapper.saveQzRechargeRecord(qzRechargeRecordEntity);
	}

}
