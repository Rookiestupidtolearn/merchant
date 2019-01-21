package com.doubao.merchant.server.recharge.service.impl;

import com.doubao.merchant.server.recharge.dao.QzMoneyRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doubao.merchant.api.recharge.entity.QzMoneyRecordEntity;
import com.doubao.merchant.server.recharge.service.QzMoneyRecordApiService;

/** 
 * @ClassName: QzMoneyRecordApiServiceImpl 
 * @Description: 
 * @author ycs
 * @date 2019年1月10日 下午5:15:55
 *
 */
@Service
public class QzMoneyRecordApiServiceImpl implements QzMoneyRecordApiService {

	@Autowired
	private QzMoneyRecordMapper qzMoneyRecordMapper;

	@Override
	public int saveQzMoneyRecord(QzMoneyRecordEntity qzMoneyRecordEntity) {

		return qzMoneyRecordMapper.saveQzMoneyRecord(qzMoneyRecordEntity);
	}

	@Override
	public QzMoneyRecordEntity queryLastMoneyRecord(int userId) {

		return qzMoneyRecordMapper.queryLastMoneyRecord(userId);
	}

}
