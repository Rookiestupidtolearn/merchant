package com.doubao.merchant.server.recharge.dao;

import com.doubao.merchant.api.recharge.entity.QzRechargeRecordEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QzRechargeRecordMapper {

    int saveQzRechargeRecord(QzRechargeRecordEntity qzRechargeRecordEntity);
}
