package com.doubao.merchant.server.recharge.service.impl;

import com.doubao.merchant.api.recharge.entity.QzUserAccountVo;
import com.doubao.merchant.server.recharge.dao.QzUserAccountMapper;
import com.doubao.merchant.server.recharge.service.QzUserAccountApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 
 * @ClassName: QzUserAccountApiServiceImpl 
 * @Description: 
 * @author ycs
 * @date 2019年1月10日 下午5:24:13
 *
 */
@Service
public class QzUserAccountApiServiceImpl implements QzUserAccountApiService {

	@Autowired
	private QzUserAccountMapper qzUserAccountMapper;

	@Override
	public QzUserAccountVo queruUserAccountInfo(Integer userId) {
		return qzUserAccountMapper.queruUserAccountInfo(userId);
	}

	@Override
	public int saveQzUserAccountVo(QzUserAccountVo vo) {
		return qzUserAccountMapper.saveQzUserAccountVo(vo);
	}

	@Override
	public int updateUserAccount(QzUserAccountVo vo) {
		return qzUserAccountMapper.updateUserAccount(vo);
	}

}
