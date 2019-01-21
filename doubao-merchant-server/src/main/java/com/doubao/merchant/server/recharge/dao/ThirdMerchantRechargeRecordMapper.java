package com.doubao.merchant.server.recharge.dao;

import java.util.List;

import com.doubao.merchant.api.recharge.entity.ThirdMerchantRechargeRecord;

public interface ThirdMerchantRechargeRecordMapper {


    int saveThirdMerchantRechargeRecord(ThirdMerchantRechargeRecord record);

    int updateByOrderNo(ThirdMerchantRechargeRecord record);

    int insert(ThirdMerchantRechargeRecord record);
    
    List<ThirdMerchantRechargeRecord>  selectByOrderNo(ThirdMerchantRechargeRecord record);
}