package com.doubao.merchant.server.recharge.service;

import com.doubao.merchant.api.recharge.entity.QzMoneyRecordEntity;


/** 
 * @ClassName: QzMoneyRecordApiService 
 * @Description: 
 * @author ycs
 * @date 2019年1月10日 下午5:15:36
 *
 */
public interface QzMoneyRecordApiService {



	int saveQzMoneyRecord(QzMoneyRecordEntity qzMoneyRecordEntity);

	/**
	 * 查询最后一笔记录
	 * @param userId
	 * @return
	 */
	QzMoneyRecordEntity queryLastMoneyRecord(int userId);
}
