package com.doubao.merchant.server.recharge.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.doubao.merchant.api.recharge.entity.CardPaySynchInfo;

/**
 * 卡信息同步接口信息
 */
@Mapper
public interface CardPaySynchInfoMapper {

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