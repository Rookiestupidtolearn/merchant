package com.doubao.merchant.server.recharge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.doubao.merchant.api.recharge.entity.QueryMemberCardReqVO;
import com.doubao.merchant.api.recharge.entity.ThirdPreCompanyRechargeRecord;
import com.doubao.merchant.server.recharge.service.PreRechargeRecordService;

/** 
 * @ClassName: CardPaySynchInfoController 
 * @Description: 对外提供查询订单信息
 * @author ycs
 * @date 2019年1月16日 下午5:14:22
 *
 */
@RestController
@RequestMapping("/cardPaySynchInfo")
public class CardPaySynchInfoController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CardPaySynchInfoController.class);
	@Autowired
	private PreRechargeRecordService preRechargeRecordService;
	
	@RequestMapping(value = "/query",method = {RequestMethod.POST})
	public String query(@RequestBody QueryMemberCardReqVO queryMemberCardReqVO) {
		JSONObject result = new JSONObject();
		JSONObject data = new JSONObject();
		ThirdPreCompanyRechargeRecord oldThirdPreCompanyRechargeRecord = preRechargeRecordService.getPreRechargeRecordByOrderNo(queryMemberCardReqVO.getCardTradeNbr());
		if (oldThirdPreCompanyRechargeRecord == null ) {
			result.put("msg", "订单不存在");
			result.put("state", "0");
		} else {
			result.put("msg", "成功");
			result.put("state", "1");
			data.put("cardTradeNbr", queryMemberCardReqVO.getCardTradeNbr());
			data.put("tradeCde", "1");
			data.put("cardTradeType", queryMemberCardReqVO.getCardTradeType());			
			data.put("tradeAmt", oldThirdPreCompanyRechargeRecord.getAmount());
			data.put("userMobile", oldThirdPreCompanyRechargeRecord.getPhone());
			result.put("data", data);
//			data.put("extendCardTrade", );
//			data.put("memberCardTrade", );
//			data.put("", );
//			data.put("", );
//			data.put("", );
		}
		LOGGER.info("【订单id{}查询结果为：{}】,",queryMemberCardReqVO.getCardTradeNbr(),result.toString());
		return result.toJSONString();		
	}

}
