package com.doubao.merchant.server.recharge.service;

import java.util.List;

import com.doubao.merchant.api.recharge.entity.CardPaySynchInfo;

/** 
 * @ClassName: CardPaySynchInfoService 
 * @Description: 
 * @author ycs
 * @date 2019年1月16日 下午5:19:05
 *
 */
public interface CardPaySynchInfoService {
	
    int deleteByPrimaryKey(Integer id);

    int insert(CardPaySynchInfo record);

    int insertSelective(CardPaySynchInfo record);

    CardPaySynchInfo selectByPrimaryKey(Integer id);

    /**
     * 通过订单号查询该记录是否已经入库通知
     * @param orderNo
     * @return
     */
    CardPaySynchInfo selectByOrderNo(String  orderNo);

    int updateByPrimaryKeySelective(CardPaySynchInfo record);

    int updateByPrimaryKey(CardPaySynchInfo record);


    /**
     * 查询集合
     * @param cardPaySynchInfo
     * @return
     */
    List<CardPaySynchInfo> findCarPaySychInfoList(CardPaySynchInfo cardPaySynchInfo);
    /**
     * 查询集合总数量
     * @param cardPaySynchInfo
     * @return
     */
    int findCarPaySychInfoListCount(CardPaySynchInfo cardPaySynchInfo);

}
