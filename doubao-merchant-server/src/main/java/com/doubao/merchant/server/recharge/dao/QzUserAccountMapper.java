package com.doubao.merchant.server.recharge.dao;

import com.doubao.merchant.api.recharge.entity.QzUserAccountVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QzUserAccountMapper {

    QzUserAccountVo queruUserAccountInfo(Integer userId);

    int saveQzUserAccountVo(QzUserAccountVo vo);

    int updateUserAccount(QzUserAccountVo vo);
}
