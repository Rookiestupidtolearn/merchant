package com.doubao.merchant.server.recharge.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.doubao.merchant.api.recharge.entity.ThirdPreCompanyRechargeRecord;


/** 
 * @ClassName: PreRechargeRecordMapper 
 * @Description: 
 * @author ycs
 * @date 2019年1月10日 下午4:21:35
 *
 */
@Mapper
public interface PreRechargeRecordMapper {
	
	List<ThirdPreCompanyRechargeRecord> getPreRechargeRecordByThirdOrder(@Param("thridOrder")String thridOrder, @Param("appId") String appId);
	
	Boolean updateByPrimaryKeySelective(ThirdPreCompanyRechargeRecord record);
	
	Boolean savePreRechargeRecord(ThirdPreCompanyRechargeRecord reRechargeRecord);

	ThirdPreCompanyRechargeRecord getByOrderNo(@Param("orderNo") String orderNo);
}
