package com.doubao.merchant.server.queue;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.doubao.merchant.api.recharge.entity.ThirdMerchantRechargeRecord;
import com.doubao.merchant.api.recharge.entity.ThridCompany;
import com.doubao.merchant.server.recharge.service.ThirdMerchantRechargeRecordService;
import com.doubao.merchant.server.thirdcompany.service.ThirdCompanyService;
import com.doubao.merchant.server.util.HttpClient4Utils;

@Component
public class Receive {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Receive.class);
	
	/**
	 * 默认发送次数
	 */
	private static final Integer SEND_NUM = 4;
	
	/**
	 * 平均间隔时间
	 */
	private static final Long AVERAGE_INTERVAL_TIME = 1L * 10 * 60 * 1000;
	
	@Autowired
	private ThirdMerchantRechargeRecordService thirdMerchantRechargeRecordService;
	
	@Autowired
	private ThirdCompanyService thridCompanyService;
	
	@Autowired
	private Send send;
	
    @JmsListener(destination = "doubaoMerchantDelayQueue")
    public void delayQueueReceive(String orderNo){
    	LOGGER.info("延时队列接收到的消息："+orderNo);
    	try {
    		ThirdMerchantRechargeRecord record = new ThirdMerchantRechargeRecord();
    		record.setOrderNo(orderNo);
    		ThirdMerchantRechargeRecord thirdMerchantRechargeRecord = thirdMerchantRechargeRecordService.getThirdMerchantRechargeRecord(record);
    		if(thirdMerchantRechargeRecord == null){
    			LOGGER.info("延时队列，当前订单：{}，不存在。",orderNo);
    			return;
    		}
    		
    		List<ThridCompany> companyList = thridCompanyService.getThridCompanyByAppId(thirdMerchantRechargeRecord.getAppId());
    		if(null!=companyList || companyList.size()>1 ){
				LOGGER.info("延时队列，商户异常：商户号为{}，不存在。",thirdMerchantRechargeRecord.getAppId());
    			return;
			}
			ThridCompany thridCompanyByAppId=companyList.get(0);
			String callBackUrl = thridCompanyByAppId.getCallBackUrl(); //回调地址
    		
    		String data = thirdMerchantRechargeRecord.getData();
    		JSONObject parseObject = JSON.parseObject(data);
    		@SuppressWarnings("unchecked")
			Map<String,String> map = JSON.toJavaObject(parseObject, Map.class);
    		
    		String sendHttpRequest = HttpClient4Utils.sendHttpRequest(callBackUrl, map, "UTF-8", true);
			LOGGER.info("延时队列，订单号："+orderNo+"回调结果："+sendHttpRequest);
			
			if("SUCCESS".equals(sendHttpRequest)){
				record.setMark("回调成功");
				record.setSendNum(thirdMerchantRechargeRecord.getSendNum()+1);
				record.setStatus("1");
				record.setUpdateTime(new Date());
			}else if("ERROR".equals(sendHttpRequest)){
				record = new ThirdMerchantRechargeRecord();
				record.setMark("回调失败");
				record.setSendNum(thirdMerchantRechargeRecord.getSendNum()+1);
				record.setStatus("2");
				record.setUpdateTime(new Date());
			}else if("failed".equals(sendHttpRequest)){
				record = new ThirdMerchantRechargeRecord();
				record.setMark("接口异常");
				record.setSendNum(thirdMerchantRechargeRecord.getSendNum()+1);
				record.setStatus("3");
				record.setUpdateTime(new Date());
			}else{
				record = new ThirdMerchantRechargeRecord();
				record.setMark("接口异常");
				record.setSendNum(thirdMerchantRechargeRecord.getSendNum()+1);
				record.setStatus("3");
				record.setUpdateTime(new Date());
			}
			thirdMerchantRechargeRecordService.updateThirdMerchantRechargeRecord(record, orderNo);
    		
    		if(!"SUCCESS".equals(sendHttpRequest) && SEND_NUM > thirdMerchantRechargeRecord.getSendNum()){
    			send.delaySend(orderNo, (thirdMerchantRechargeRecord.getSendNum()+1) * AVERAGE_INTERVAL_TIME);
    		}
    		LOGGER.info("延时队列，订单号：{}，发送次数：{}，返回结果：{}，处理完成。",orderNo,(thirdMerchantRechargeRecord.getSendNum()+1),sendHttpRequest);
		} catch (Exception e) {
			LOGGER.error("延时队列接收到的消息，订单号：{}，出现异常：",orderNo,e);
		}
    }
    
    public static void main(String[] args) {
//		String str = "{\"amount\":\"1\",\"orderNo\":\"33\",\"signature\":\"IuqtkJ75oTGQi2jsuZXL6TlQfmpWi96uYCC29E1JNHzfOB8GN8te+vipfYnpQ880SSLuy9dXyeWGGI6C1z49IRZppTAWQaTvOUobNUce8bJ+w+Nvy7wMnGk4BcxxHkLyx97buUwvzXXl07kWYYkoo2QnqtO2kX5JlJ+XLUWSm88=\",\"appId\":\"12345\",\"state\":\"2\"}";
//		JSONObject parseObject = JSON.parseObject(str);
//		Map javaObject = JSON.toJavaObject(parseObject, Map.class);
//		System.out.println(javaObject);
	}
}
