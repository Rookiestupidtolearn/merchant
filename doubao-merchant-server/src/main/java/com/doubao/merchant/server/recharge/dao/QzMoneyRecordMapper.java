package com.doubao.merchant.server.recharge.dao;

import com.doubao.merchant.api.recharge.entity.QzMoneyRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QzMoneyRecordMapper {

    int saveQzMoneyRecord(QzMoneyRecordEntity qzMoneyRecordEntity);

    QzMoneyRecordEntity queryLastMoneyRecord(@Param("userId") int userId);
}
