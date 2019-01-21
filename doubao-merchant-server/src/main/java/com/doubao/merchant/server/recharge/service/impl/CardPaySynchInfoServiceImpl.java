package com.doubao.merchant.server.recharge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doubao.merchant.api.recharge.entity.CardPaySynchInfo;
import com.doubao.merchant.server.recharge.dao.CardPaySynchInfoMapper;
import com.doubao.merchant.server.recharge.service.CardPaySynchInfoService;

/** 
 * @ClassName: CardPaySynchInfoServiceImpl 
 * @Description: 
 * @author ycs
 * @date 2019年1月16日 下午5:19:23
 *
 */
@Service
public class CardPaySynchInfoServiceImpl implements CardPaySynchInfoService{
	
	@Autowired
	private CardPaySynchInfoMapper cardPaySynchInfoMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return cardPaySynchInfoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CardPaySynchInfo record) {
		return cardPaySynchInfoMapper.insert(record);
	}

	@Override
	public int insertSelective(CardPaySynchInfo record) {
		return cardPaySynchInfoMapper.insertSelective(record);
	}

	@Override
	public CardPaySynchInfo selectByPrimaryKey(Integer id) {
		return cardPaySynchInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public CardPaySynchInfo selectByOrderNo(String orderNo) {
		return cardPaySynchInfoMapper.selectByOrderNo(orderNo);
	}

	@Override
	public int updateByPrimaryKeySelective(CardPaySynchInfo record) {		
		return cardPaySynchInfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CardPaySynchInfo record) {
		return cardPaySynchInfoMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<CardPaySynchInfo> findCarPaySychInfoList(
			CardPaySynchInfo cardPaySynchInfo) {
		return cardPaySynchInfoMapper.findCarPaySychInfoList(cardPaySynchInfo);
	}

	@Override
	public int findCarPaySychInfoListCount(CardPaySynchInfo cardPaySynchInfo) {
		return cardPaySynchInfoMapper.findCarPaySychInfoListCount(cardPaySynchInfo);
	}

}
