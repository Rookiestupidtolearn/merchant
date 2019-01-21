package com.doubao.merchant.server.recharge.service;

import com.doubao.merchant.api.recharge.entity.QzRechargeRecordEntity;

/** 
 * @ClassName: QzRechargeRecordApiService 
 * @Description: 
 * @author ycs
 * @date 2019年1月10日 下午5:12:40
 *
 */
public interface QzRechargeRecordApiService {

	int saveQzRechargeRecord(QzRechargeRecordEntity qzRechargeRecordEntity);
}
