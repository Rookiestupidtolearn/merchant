package com.doubao.merchant.server.recharge.service;

import com.doubao.merchant.api.recharge.entity.QzUserAccountVo;

/** 
 * @ClassName: QzUserAccountApiService 
 * @Description: 
 * @author ycs
 * @date 2019年1月10日 下午5:23:48
 *
 */
public interface QzUserAccountApiService {
	
	QzUserAccountVo queruUserAccountInfo(Integer userId);
	
	int saveQzUserAccountVo(QzUserAccountVo vo);
	
	int updateUserAccount(QzUserAccountVo vo);
	

}
