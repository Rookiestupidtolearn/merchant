package com.doubao.merchant.server.nideshopuser.dao;

import com.doubao.merchant.api.nideshopuser.entity.NideshopUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NideshopUserMapper {


    Boolean saveNideshopUser(NideshopUser record);

    NideshopUser selectByMobile(@Param("mobile") String mobile);

    Integer updateByPrimaryKey(NideshopUser record);

    Integer updateByPrimaryKeySelective (NideshopUser record);
}