package com.doubao.merchant.server.recharge.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.doubao.merchant.api.nideshopuser.entity.NideshopUser;
import com.doubao.merchant.api.recharge.entity.CardPaySynchInfo;
import com.doubao.merchant.api.recharge.entity.QzMoneyRecordEntity;
import com.doubao.merchant.api.recharge.entity.QzRechargeRecordEntity;
import com.doubao.merchant.api.recharge.entity.QzUserAccountVo;
import com.doubao.merchant.api.recharge.entity.RequestPreRechargeEntity;
import com.doubao.merchant.api.recharge.entity.RequestRechargeEntity;
import com.doubao.merchant.api.recharge.entity.ThirdMerchantRechargeRecord;
import com.doubao.merchant.api.recharge.entity.ThirdPreCompanyRechargeRecord;
import com.doubao.merchant.api.recharge.entity.ThridCompany;
import com.doubao.merchant.server.nideshopuser.service.NideshopuserService;
import com.doubao.merchant.server.queue.Send;
import com.doubao.merchant.server.recharge.constant.PreRechargeConstants.RechargeStatus;
import com.doubao.merchant.server.recharge.constant.PreRechargeConstants.ReturnResult;
import com.doubao.merchant.server.recharge.service.CardPaySynchInfoService;
import com.doubao.merchant.server.recharge.service.PreRechargeRecordService;
import com.doubao.merchant.server.recharge.service.QzMoneyRecordApiService;
import com.doubao.merchant.server.recharge.service.QzRechargeRecordApiService;
import com.doubao.merchant.server.recharge.service.QzUserAccountApiService;
import com.doubao.merchant.server.recharge.service.ThirdMerchantRechargeRecordService;
import com.doubao.merchant.server.thirdcompany.service.ThirdCompanyService;
import com.doubao.merchant.server.util.EncryptUtil;
import com.doubao.merchant.server.util.FieldValidation;
import com.doubao.merchant.server.util.GenerateOrderNoUtil;
import com.doubao.merchant.server.util.HttpClient4Utils;
import com.doubao.merchant.server.util.IpUtil;
import com.doubao.merchant.server.util.MsgDigestUtils;
import com.doubao.merchant.server.util.ReturnUtil;
import com.doubao.merchant.server.util.redis.RedisUtils;
import com.doubao.pay.api.client.DoubaoPayClient;
import com.doubao.pay.api.entity.ThirdPreCompanyRechargeRecordDTO;
import com.doubao.pay.api.entity.YeeTradeOrderEntityDTO;

@Api(tags = "商户充值")
@RestController
@RequestMapping("/recharge")
public class RechargeController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RechargeController.class);
	
	@Value("${doubao.subbmitrecharge.url}")
	private String doubaoSubbmitrechargeUrl;
	
	@Value("${doubao.cardpaysynchinfo.url}")
	private String doubaoCardpaysynchinfoUrl;
	
	
	@Value("${doubao.checkuser.url}")
	private String doubaoCheckUserUrl;
	

	@Autowired
	private ThirdCompanyService thridCompanyService;
	
	@Autowired
	private PreRechargeRecordService preRechargeRecordService;
	
	@Autowired
	private NideshopuserService nideshopuserService;
	
	@Autowired
	private ThirdMerchantRechargeRecordService thirdMerchantRechargeRecordService; 
	
	@Autowired
	private QzRechargeRecordApiService qzRechargeRecordApiService;
	
	@Autowired
	private QzMoneyRecordApiService qzMoneyRecordApiService;
	
	@Autowired
	private QzUserAccountApiService qzUserAccountApiService;
	
	
	@Autowired
	private DoubaoPayClient doubaoPayClient;
	
	
	@Autowired
	private JedisPool redisPoolFactory;
	
	@Autowired
	private CardPaySynchInfoService cardPaySynchInfoService;
	
	@Autowired
	private Send send;
	
	/**
	 *生成PRE签名
	 * @param
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "生成PRE签名")
	@PostMapping (value = "/preSign")
	public Map<String, Object> preSign(@Valid @RequestBody RequestPreRechargeEntity reRechargeRecord,
									   HttpServletRequest request, HttpServletResponse response) {

		LOGGER.info("【签名】入参:" + reRechargeRecord.toString());
	
		Map<String, Object> preFieldValidation = FieldValidation.preFieldValidation(reRechargeRecord, "1");
		if(ReturnResult.FAIL.equals(preFieldValidation.get("msg"))){
			return preFieldValidation;
		}
		
		ThridCompany thridCompany = thridCompanyService.getThridCompanyByAppId(reRechargeRecord.getAppId());

		if (null == thridCompany) {
			return ReturnUtil.returnAny(ReturnResult.FAIL, "100020", "商户无权限");
		}

		if (StringUtils.isEmpty(thridCompany.getPublicKey())) {
			return ReturnUtil.returnAny(ReturnResult.FAIL, "100021", "商户信息未配置");
		}
		
		Map<String, Object> successMap = ReturnUtil.returnSuccess();
		successMap.put("sign", MsgDigestUtils.sign(reRechargeRecord.regSignVal(),thridCompany.getPrivateKey()));
		return successMap;
	}

	/**
	 *生成re签名
	 * @param
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "生成re签名")
	@PostMapping (value = "/reSign")
	public Map<String, Object> reSign(@Valid @RequestBody RequestRechargeEntity requestRecharge, HttpServletRequest request,
									  HttpServletResponse response) {

		LOGGER.info("【支付签名】入参:" + requestRecharge.toString());
	
		Map<String, Object> reFieldValidation = FieldValidation.reFieldValidation(requestRecharge, "1");
		if(ReturnResult.FAIL.equals(reFieldValidation.get("msg"))){
			return reFieldValidation;
		}
		
		ThridCompany thridCompany = thridCompanyService.getThridCompanyByAppId(requestRecharge.getAppId());

		if (null == thridCompany) {
			return ReturnUtil.returnAny(ReturnResult.FAIL, "100020", "商户无权限");
		}

		if (StringUtils.isEmpty(thridCompany.getPublicKey())) {
			return ReturnUtil.returnAny(ReturnResult.FAIL, "100021", "商户信息未配置");
		}

		Map<String, Object> successMap = ReturnUtil.returnSuccess();
		successMap.put("sign", MsgDigestUtils.sign(requestRecharge.regSignVal(),thridCompany.getPrivateKey()));
		return successMap;
	}
	
	
	/**
	 *预支付
	 * @param reRechargeRecord
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "预支付")
	@PostMapping (value = "/pre_recharge")
	public Map<String, Object> preRecharge(@Valid @RequestBody RequestPreRechargeEntity reRechargeRecord,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.info("【预充值】入参:" + reRechargeRecord.toString());
	
		Map<String, Object> preFieldValidation = FieldValidation.preFieldValidation(reRechargeRecord, "2");
		if(ReturnResult.FAIL.equals(preFieldValidation.get("msg"))){
			return preFieldValidation;
		}
		
		ThridCompany thridCompany = thridCompanyService.getThridCompanyByAppId(reRechargeRecord.getAppId());
		LOGGER.info("传入签名为=="+reRechargeRecord.getSignature()+"公钥为==="+thridCompany.getPublicKey()+"要签名的数据为=="+reRechargeRecord.regSignVal());
		if (null == thridCompany) {
			return ReturnUtil.returnAny(ReturnResult.FAIL, "100020", "商户无权限");
		}

		if (StringUtils.isEmpty(thridCompany.getPublicKey())) {
			return ReturnUtil.returnAny(ReturnResult.FAIL, "100021", "商户信息未配置");
		}

		if (!MsgDigestUtils.verifySign(reRechargeRecord.regSignVal(), reRechargeRecord.getSignature(),
				thridCompany.getPublicKey())) {
			LOGGER.info("验签失败传入签名为=="+reRechargeRecord.getSignature()+"公钥为==="+thridCompany.getPublicKey()+"要签名的数据为=="+reRechargeRecord.regSignVal());
			return ReturnUtil.returnAny(ReturnResult.FAIL, "100022", "验签失败");
		}

		//校验金额，必须为正整数
        try {
           int money = reRechargeRecord.getAmount().intValue();
           if (money <= 0 ) {
               return ReturnUtil.returnFail("金额必须为正整数");
           }
        } catch (Exception e) {
            return ReturnUtil.returnFail("金额必须为正整数");
        }

		Long requestId = System.currentTimeMillis();

		Jedis jedis = redisPoolFactory.getResource();

		String key = reRechargeRecord.getAppId() + "-" + reRechargeRecord.getOrderNo();
		Boolean locked = RedisUtils.tryGetDistributedLock(jedis,key,Long.toString(requestId), 1000 * 120);

		if (!locked) {
			return ReturnUtil.returnAny(ReturnResult.FAIL, "100030", "当前订单正在处理中");
		}

		//查询历史是否有支付单子
		ThirdPreCompanyRechargeRecord oldThirdPreCompanyRechargeRecord = preRechargeRecordService.getPreRechargeRecordByThirdOrder(reRechargeRecord.getOrderNo(), reRechargeRecord.getAppId());
		if (null != oldThirdPreCompanyRechargeRecord) {
			Date updateTime = oldThirdPreCompanyRechargeRecord.getUpdateTime();
			if ("0".equals(oldThirdPreCompanyRechargeRecord.getStatus()) ) {
				
				return ReturnUtil.returnAny(ReturnResult.FAIL, "100030", "当前订单正在处理中");
			} else if("1".equals(oldThirdPreCompanyRechargeRecord.getStatus())){
				if((new Date().getTime() - updateTime.getTime()) >= 1000 * 60 * 60 * 23.5){
					oldThirdPreCompanyRechargeRecord.setStatus(RechargeStatus.INVALID);
            		oldThirdPreCompanyRechargeRecord.setRemark("订单失效");
            		oldThirdPreCompanyRechargeRecord.setUpdateTime(new Date());
					preRechargeRecordService.updateByPrimaryKeySelective(oldThirdPreCompanyRechargeRecord);
					
					return ReturnUtil.returnAny(ReturnResult.FAIL, "100031", "订单已经失效,请重新支付");
				}
				
				return ReturnUtil.returnAny(ReturnResult.FAIL, "100030", "当前订单正在处理中");
			}else if ("2".equals(oldThirdPreCompanyRechargeRecord.getStatus())) {
				
				return ReturnUtil.returnAny(ReturnResult.FAIL, "100032", "订单支付成功,请勿重复支付");
			}else if("4".equals(oldThirdPreCompanyRechargeRecord.getStatus())){
				
				return ReturnUtil.returnAny(ReturnResult.FAIL, "100031", "订单已经失效,请重新支付");
			}
			else {
				
				return ReturnUtil.returnAny(ReturnResult.FAIL, "100033", "订单支付失败,请重新支付");
			}
		}

		try {
			String phone = reRechargeRecord.getPhone();
			NideshopUser nideshopUser = nideshopuserService.selectByMobile(phone);
			//不存在创建用户
			if (null == nideshopUser) {
				nideshopUser = new NideshopUser();
                nideshopUser.setUsername("");
				nideshopUser.setMobile(reRechargeRecord.getPhone());
				nideshopUser.setRegisterTime(new Date());
				nideshopUser.setThirdCompanyId(thridCompany.getId());

				
				//获取手机号在斗宝系统中的userId
				TreeMap<String, Object> treeMap	= new TreeMap<String, Object>();
				treeMap.put("mobile", phone);			
				JSONObject json =new JSONObject(treeMap);
				String content = json.toString();		   
		        String encrypt = EncryptUtil.aesEncrypt(content); 
		        Map<String, String> paramMap = new HashMap<String, String>();
	            paramMap.put("encrypt", encrypt);
		        String sendHttpRequest = HttpClient4Utils.sendHttpRequest(doubaoCheckUserUrl, paramMap, "UTF-8", true);
		        JSONObject result = JSON.parseObject(sendHttpRequest);
		        if ("success".equals(result.getString("code"))) {		        	
		        	String douBaoUserId = result.getString("userId");
		        	nideshopUser.setDouBaoUserId(douBaoUserId);
				} else {					
					LOGGER.info("三方订单号:{},斗宝查询失败用户返回信息：{}", reRechargeRecord.getOrderNo(), sendHttpRequest);	
					return ReturnUtil.returnAny(ReturnResult.FAIL, "100040", "开户失败");
				}				
		        				
				Boolean isSave = nideshopuserService.saveNideshopUser(nideshopUser);
				if (!isSave) {
					RedisUtils.releaseDistributedLock(jedis, key, Long.toString(requestId));
					return ReturnUtil.returnAny(ReturnResult.FAIL, "100040", "开户失败");
				}
			}

			// 创建订单号
			String orderNo = GenerateOrderNoUtil.gen(reRechargeRecord.getChannelNo(),reRechargeRecord.getAppId());
			
			ThirdPreCompanyRechargeRecord thirdPreCompanyRechargeRecord = new ThirdPreCompanyRechargeRecord();
			thirdPreCompanyRechargeRecord.setAppId(reRechargeRecord.getAppId());
			thirdPreCompanyRechargeRecord.setOrderNo(orderNo);
			thirdPreCompanyRechargeRecord.setOrderThird(reRechargeRecord.getOrderNo());
			thirdPreCompanyRechargeRecord.setAmount(reRechargeRecord.getAmount());
			thirdPreCompanyRechargeRecord.setPhone(reRechargeRecord.getPhone());
			thirdPreCompanyRechargeRecord.setChannelPay("yeepay");
			thirdPreCompanyRechargeRecord.setChannelThird(reRechargeRecord.getChannelNo());
			thirdPreCompanyRechargeRecord.setStatus(RechargeStatus.INIT);
			thirdPreCompanyRechargeRecord.setCreateTime(new Date());
            thirdPreCompanyRechargeRecord.setUpdateTime(new Date());

			if (preRechargeRecordService.savePreRechargeRecord(thirdPreCompanyRechargeRecord)) {
				RedisUtils.releaseDistributedLock(jedis, key, Long.toString(requestId));
                return ReturnUtil.returnSuccess();
			} else {
				RedisUtils.releaseDistributedLock(jedis, key, Long.toString(requestId));
				return ReturnUtil.returnAny(ReturnResult.FAIL, "100050", "充值失败");
			}

		} catch (Exception e) {
			LOGGER.error("【预充值】异常", e);
            RedisUtils.releaseDistributedLock(jedis, key, Long.toString(requestId));
            return ReturnUtil.returnAny(ReturnResult.FAIL, "200000", "未知异常,请联系管理员");
		}finally{
			if(jedis != null){
				jedis.close();
			}
		}

	}
	
	/**
	 * 支付
	 * @param requestRecharge
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "支付")
	@PostMapping(value = "/recharge")
	@ResponseBody
	public Map<String, Object> recharge(@Valid @RequestBody RequestRechargeEntity requestRecharge, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.info("充值】入参:" + requestRecharge.toString());
	
		Map<String, Object> reFieldValidation = FieldValidation.reFieldValidation(requestRecharge, "2");
		if(ReturnResult.FAIL.equals(reFieldValidation.get("msg"))){
			return reFieldValidation;
		}
		
		ThridCompany thridCompany = thridCompanyService.getThridCompanyByAppId(requestRecharge.getAppId());

		if (null == thridCompany) {
			
			return ReturnUtil.returnAny(ReturnResult.FAIL, "100020", "商户无权限");
		}

		if (StringUtils.isEmpty(thridCompany.getPublicKey())) {
			return ReturnUtil.returnAny(ReturnResult.FAIL, "100021", "商户信息未配置");
		}
     
		if (!MsgDigestUtils.verifySign(requestRecharge.regSignVal(), requestRecharge.getSignature(),thridCompany.getPublicKey())) {
			//return ReturnUtil.returnFail("验签失败");
			return ReturnUtil.returnAny(ReturnResult.FAIL, "100022", "验签失败");
		}

		Jedis jedis = redisPoolFactory.getResource();
        //查询历史是否有预支付单子
        ThirdPreCompanyRechargeRecord oldThirdPreCompanyRechargeRecord = preRechargeRecordService.getPreRechargeRecordByThirdOrder(requestRecharge.getOrderNo(), requestRecharge.getAppId());
        //状态0或3的单子 可以支付
        if (null != oldThirdPreCompanyRechargeRecord) {
            if ( RechargeStatus.PROCESS.equals(oldThirdPreCompanyRechargeRecord.getStatus())) {
            	Date updateTime = oldThirdPreCompanyRechargeRecord.getUpdateTime();
            	//是否失效
            	if((new Date().getTime() - updateTime.getTime()) >= 1000 * 60 * 60 * 23.5){
            		oldThirdPreCompanyRechargeRecord.setStatus(RechargeStatus.INVALID);
            		oldThirdPreCompanyRechargeRecord.setRemark("订单失效");
            		oldThirdPreCompanyRechargeRecord.setUpdateTime(new Date());
					preRechargeRecordService.updateByPrimaryKeySelective(oldThirdPreCompanyRechargeRecord);
            		return ReturnUtil.returnAny(ReturnResult.FAIL, "100031", "订单已经失效,请重新支付");
            	}else{
            		YeeTradeOrderEntityDTO yee = doubaoPayClient.query(oldThirdPreCompanyRechargeRecord.getOrderNo());
                	Map<String, Object> returnFail = ReturnUtil.returnAny(ReturnResult.SUCCESS, "100034", "当前订单正在处理中,请勿重复操作");
                	String str = yee.getResponseMsg();
                	str = str.trim();
            		str = str.substring(1, str.length()-1);
            		String [] strs = str.split(",");
                    for(String s:strs){
                    	if(s.contains("imghexstr") || s.contains("sign")){
                    		continue;
                    	}
                        String [] s1 = s.split("=");
                        if(s1[0].contains("payurl")){
                        	returnFail.put("payUrl", s1[1]);
                        }
                    }
                    return returnFail;
            	}
            } else if (RechargeStatus.SUCCESS.equals(oldThirdPreCompanyRechargeRecord.getStatus())) {                
            	return ReturnUtil.returnAny(ReturnResult.FAIL, "100032", "订单支付成功,请勿重复支付");
            }else if (RechargeStatus.INVALID.equals(oldThirdPreCompanyRechargeRecord.getStatus())){
            	oldThirdPreCompanyRechargeRecord.setOrderNo(GenerateOrderNoUtil.gen(oldThirdPreCompanyRechargeRecord.getChannelThird(),oldThirdPreCompanyRechargeRecord.getAppId()));
            }
        } else {            
        	return ReturnUtil.returnAny(ReturnResult.FAIL, "100035", "订单不存在,请先调用预支付接口");
        }

		Long requestId = System.currentTimeMillis();	
		//锁key
		String key = requestRecharge.getAppId() + "-"  + requestRecharge.getOrderNo() +"-pay";
		Boolean locked = RedisUtils.tryGetDistributedLock(jedis, key,Long.toString(requestId), 1000 * 60);

		if (!locked) {
			return ReturnUtil.returnAny(ReturnResult.FAIL, "100030", "当前订单正在处理中");
		}

        String phone = oldThirdPreCompanyRechargeRecord.getPhone();
        NideshopUser nideshopUser = nideshopuserService.selectByMobile(phone);

        ThirdPreCompanyRechargeRecordDTO  thirdPreCompanyRechargeRecordDTO = new ThirdPreCompanyRechargeRecordDTO();
        BeanUtils.copyProperties(oldThirdPreCompanyRechargeRecord, thirdPreCompanyRechargeRecordDTO);	        
		thirdPreCompanyRechargeRecordDTO.setUserId(Integer.valueOf(nideshopUser.getDouBaoUserId()));  		
        thirdPreCompanyRechargeRecordDTO.setIp(IpUtil.getIpAddr(request));
        
        //符合要求，去调用真正的支付渠道生成订单
        Map<String, Object> stringObjectMap = doubaoPayClient.recharge(thirdPreCompanyRechargeRecordDTO);
        Map<String, Object> successMap = ReturnUtil.returnSuccess();
        if (null != stringObjectMap) {
        	if(StringUtils.isEmpty(String.valueOf(stringObjectMap.get("payurl")))){
				RedisUtils.releaseDistributedLock(jedis,key,String.valueOf(requestId));
        		return ReturnUtil.returnAny(ReturnResult.FAIL, "100036", "订单支付异常,请重新提交");       		
        	}
            successMap.put("payUrl", stringObjectMap.get("payurl"));
        } else {
			RedisUtils.releaseDistributedLock(jedis,key,String.valueOf(requestId));
        	return ReturnUtil.returnAny(ReturnResult.FAIL, "100036", "订单支付异常,请重新提交");
        }
		if(jedis != null){
			jedis.close();
		}

        //如果为失效订单，需要新增
        if(RechargeStatus.INVALID.equals(oldThirdPreCompanyRechargeRecord.getStatus())){
        	//订单失效，需要重新创建
            ThirdPreCompanyRechargeRecord thirdPreCompanyRechargeRecord = new ThirdPreCompanyRechargeRecord();
			thirdPreCompanyRechargeRecord.setAppId(oldThirdPreCompanyRechargeRecord.getAppId());
			thirdPreCompanyRechargeRecord.setOrderNo(oldThirdPreCompanyRechargeRecord.getOrderNo());
			thirdPreCompanyRechargeRecord.setOrderThird(oldThirdPreCompanyRechargeRecord.getOrderThird());
			thirdPreCompanyRechargeRecord.setAmount(oldThirdPreCompanyRechargeRecord.getAmount());
			thirdPreCompanyRechargeRecord.setPhone(oldThirdPreCompanyRechargeRecord.getPhone());
			thirdPreCompanyRechargeRecord.setChannelPay("yeepay");
			thirdPreCompanyRechargeRecord.setChannelThird(oldThirdPreCompanyRechargeRecord.getChannelThird());
			thirdPreCompanyRechargeRecord.setStatus(RechargeStatus.PROCESS);
			thirdPreCompanyRechargeRecord.setCreateTime(new Date());
			thirdPreCompanyRechargeRecord.setRemark("支付中");
            thirdPreCompanyRechargeRecord.setUpdateTime(new Date());
            if (preRechargeRecordService.savePreRechargeRecord(thirdPreCompanyRechargeRecord)) {
           		return successMap;			
	   	    } else {
	   			LOGGER.error("支付时，修改商户订单失败，商户号:"+thirdPreCompanyRechargeRecord.getAppId()+",系统订单号:"+thirdPreCompanyRechargeRecord.getOrderNo()+",商户订单号:"+thirdPreCompanyRechargeRecord.getOrderThird());
	   			return ReturnUtil.returnAny(ReturnResult.FAIL, "100037", "订单系统处理出现异常，请联系系统管理员");
	   		}
        }else{
        	 oldThirdPreCompanyRechargeRecord.setUpdateTime(new Date());
//           if ("3".equals(oldThirdPreCompanyRechargeRecord.getStatus())) {
//               oldThirdPreCompanyRechargeRecord.setRemark("历史支付失败订单，重新支付");
//           }
        	 oldThirdPreCompanyRechargeRecord.setRemark("支付中");
        	 oldThirdPreCompanyRechargeRecord.setStatus(RechargeStatus.PROCESS);
        	 if (preRechargeRecordService.updateByPrimaryKeySelective(oldThirdPreCompanyRechargeRecord)) {
           		return successMap;			
        	 } else {
	   			LOGGER.error("支付时，修改商户订单失败，商户号:"+oldThirdPreCompanyRechargeRecord.getAppId()+",系统订单号:"+oldThirdPreCompanyRechargeRecord.getOrderNo()+",商户订单号:"+oldThirdPreCompanyRechargeRecord.getOrderThird());
	   			return ReturnUtil.returnAny(ReturnResult.FAIL, "100037", "订单系统处理出现异常，请联系系统管理员");
	   		 }
        }
	}	
	
	/**
	 *
	 * @Title: queryOrder
	 * @Description: 订单查询
	 * @param requestRecharge
	 * @param request
	 * @param response
	 * @return   Map<String,Object>
	 *
	 * @throws
	 */
	@ApiOperation(value = "商户订单查询")
	@PostMapping(value = "/queryOrder")
	@ResponseBody
	public Map<String, Object> queryOrder(@Valid @RequestBody RequestRechargeEntity requestRecharge, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.info("订单查询入参:" + requestRecharge.toString());	
	
		Map<String, Object> reFieldValidation = FieldValidation.reFieldValidation(requestRecharge, "2");
		if(ReturnResult.FAIL_CODE.equals(reFieldValidation.get("code"))){
			return reFieldValidation;
		}
		
		ThridCompany thridCompany = thridCompanyService.getThridCompanyByAppId(requestRecharge.getAppId());

		if (null == thridCompany) {			
			return ReturnUtil.returnAny(ReturnResult.FAIL, "100020", "商户无权限");
		}

		if (StringUtils.isEmpty(thridCompany.getPublicKey())) {
			return ReturnUtil.returnAny(ReturnResult.FAIL, "100021", "商户信息未配置");
		}

		if (!MsgDigestUtils.verifySign(requestRecharge.regSignVal(), requestRecharge.getSignature(),thridCompany.getPublicKey())) {
			return ReturnUtil.returnAny(ReturnResult.FAIL, "100022", "验签失败");
		}

		ThirdPreCompanyRechargeRecord preRechargeRecordByThirdOrder = preRechargeRecordService.getPreRechargeRecordByThirdOrder(requestRecharge.getOrderNo(), requestRecharge.getAppId());
		
		if (null == preRechargeRecordByThirdOrder) {
			return ReturnUtil.returnAny(ReturnResult.FAIL, "100038", "您选择的订单不存在,请选择正确的订单号");
		}else {			
			Map<String, Object> successMap = ReturnUtil.returnSuccess();
			successMap.put("state", preRechargeRecordByThirdOrder.getStatus());
			return successMap;
		}	
	}	
	
	//回调通知易宝
	@RequestMapping(value = "/yeePayRecharge",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String yeePayRecharge(String data, String encryptkey){
		LOGGER.info("易宝回调通知原始数据：{data:"+data+",encrykey:"+encryptkey+"}");
		Map<String, Object> yee = yeePayRechargeOperation(data, encryptkey);
		if(ReturnResult.SUCCESS.equals(String.valueOf(yee.get("msg")))){
			return "SUCCESS";
		}
		return "ERROR";
	}
	
	
	private Map<String, Object> yeePayRechargeOperation(String data,String encryptkey){
		LOGGER.info("易宝支付订单支付回调start");
		String appId = "";
		try {
			if(StringUtils.isEmpty(data) || StringUtils.isEmpty(encryptkey)){
				LOGGER.info("易宝回调参数有误！！！");
				Map<String, Object> returnAny  =  ReturnUtil.returnAny(ReturnResult.FAIL, "10001", "易宝回调参数有误！");
				return returnAny;
			}

			// 解密data
			TreeMap<String, String> dataMap = doubaoPayClient.decrypt(data, encryptkey);
			LOGGER.info("易宝支付订单回调，返回的明文参数：" + dataMap);
			
			//商户订单号
			String orderid = dataMap.get("orderid");
			LOGGER.info("易宝支付订单，当前订单号："+ orderid);
			ThirdPreCompanyRechargeRecord preRecord = preRechargeRecordService.getPreRechargeRecordByOrderNo(orderid);
			appId = preRecord.getAppId();
			
			// sign验签

			if (!doubaoPayClient.sign(data, encryptkey)) {
				LOGGER.error("易宝支付订单回调 ，sign 验签失败！");
				Map<String, Object> returnAny  =  ReturnUtil.returnAny(ReturnResult.FAIL, "10002", "sign 验签失败！");
				return returnAny;
			}
			
			if(null != preRecord){
				if ("2".equals(preRecord.getStatus())) {
					// 无需再次回调
					LOGGER.info("本地订单已经处理成功，无需再处理，商户订单号：" + orderid );
					Map<String, Object> returnAny = ReturnUtil.returnAny(ReturnResult.SUCCESS, "9999", "已经支付成功");
					return returnAny;
				}
				
				//状态是否成功
				if("1".equals(dataMap.get("status"))){
					preRecord.setStatus(RechargeStatus.SUCCESS);
				}else{
					preRecord.setStatus(RechargeStatus.FAIL);
					preRecord.setRemark("充值失败");
					preRecord.setUpdateTime(new Date());
					preRechargeRecordService.updateByPrimaryKeySelective(preRecord);
					yeeTradeOrder(preRecord, dataMap);
					Map<String, Object> returnAny = ReturnUtil.returnAny(ReturnResult.FAIL, "10003", "充值失败");
					callbackMerchants(dataMap, returnAny, appId, preRecord);
					return returnAny;
				}
			
				//实际支付与订单金额是否一致
				BigDecimal pay = new BigDecimal(dataMap.get("amount")).divide(new BigDecimal(100));
				if(pay.compareTo(preRecord.getAmount()) != 0){
					LOGGER.info("实际支付给易宝金额跟实际订单金额不一致，商户订单号：" + orderid );
					preRecord.setRemark("实际支付给易宝金额跟实际订单金额不一致");
				}else{
					preRecord.setRemark("支付成功");
				}
				preRecord.setUpdateTime(new Date());
				preRechargeRecordService.updateByPrimaryKeySelective(preRecord);
//				shopUserHandle(preRecord);
//				shopUserRechargeRecord(preRecord);
//				shopUserAccount(preRecord);
				//修改易宝支付状态
				yeeTradeOrder(preRecord, dataMap);
				
				//到这步 支付指定成功了
				HandleCardPaySynchInfo(preRecord);
			}
			
			LOGGER.info("易宝支付订单支付回调成功，商户订单号：" + orderid );
			Map<String, Object> returnAny = ReturnUtil.returnAny(ReturnResult.SUCCESS, "10000", "支付成功");
			callbackMerchants(dataMap, returnAny, appId, preRecord);
			return returnAny;
		} catch (Exception e) {
			LOGGER.error("易宝支付订单支付回调失败，",e);
			Map<String, Object> returnAny = ReturnUtil.returnAny(ReturnResult.FAIL, "10004", "订单支付回调失败");			
			return returnAny;
		}
	}
	
	/**
	 * 回调通知商户
	 * @throws Exception 
	 */
	private void callbackMerchants(TreeMap<String, String> dataMap,Map<String, Object> returnAny,String appId, ThirdPreCompanyRechargeRecord preRecord) {
	
		String orderNo = dataMap.get("orderid");
		try {
			if(StringUtils.isEmpty(appId)){
				LOGGER.info("appId为空");
			}
			
			if(dataMap.containsKey("sign")){
				dataMap.remove("sign");
			}
			
			Set<Entry<String, Object>> entrySet = returnAny.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				dataMap.put(entry.getKey(), String.valueOf(entry.getValue()));
			}
			
			ThridCompany thridCompanyByAppId = thridCompanyService.getThridCompanyByAppId(appId);
			String callBackUrl = thridCompanyByAppId.getCallBackUrl(); //回调地址
			//String publicKey = thridCompanyByAppId.getPublicKey();
			//String encrypt = RSA.encrypt(JSON.toJSONString(dataMap), publicKey);
			
			Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("appId", appId);
            paramMap.put("orderNo", preRecord.getOrderThird());
            paramMap.put("amount", String.valueOf(preRecord.getAmount()) );
            paramMap.put("signature", MsgDigestUtils.sign(appId + "|" + preRecord.getOrderThird(), thridCompanyByAppId.getPrivateKey()));
            paramMap.put("state", "10000".equals(returnAny.get("code"))?"2":"3");

			ThirdMerchantRechargeRecord record = new ThirdMerchantRechargeRecord();
			record.setAppId(appId);
			record.setData(JSON.toJSONString(paramMap));
			record.setMark("初始化");
			record.setOrderNo(orderNo);
			record.setSendNum(0);
			record.setStatus("0");//初始化状态
			record.setUpdateTime(new Date());
			record.setCreateTime(new Date());
			thirdMerchantRechargeRecordService.saveThirdMerchantRechargeRecord(record);
			//发送MQ
			send.delaySend(orderNo, 0L);
			LOGGER.info("商户号："+appId+"，订单号："+orderNo+"回调通知商户处理完成。");
		} catch (Exception e) {
			LOGGER.error("商户号："+appId+"，订单号："+orderNo+"回调通知商户出现异常：",e);
		}
		
	}

	/**
	 * 充值成功之后，用户资金流水 数据变动
	 * @param preRecord
	 */
	private void shopUserHandle(ThirdPreCompanyRechargeRecord preRecord){

		try {
			NideshopUser nideshopUser = nideshopuserService.selectByMobile(preRecord.getPhone());
			if (nideshopUser == null) {
				LOGGER.info("手机号："+preRecord.getPhone()+"，没有此用户信息");
				return ;
			}
			Integer id = nideshopUser.getId();//获取用户id
			QzMoneyRecordEntity qzMoneyRecordEntity1 = qzMoneyRecordApiService.queryLastMoneyRecord(id);

			QzMoneyRecordEntity qzMoneyRecordEntity = new QzMoneyRecordEntity();			
			qzMoneyRecordEntity.setShopUserId(id);
			qzMoneyRecordEntity.setTranType("1");
			qzMoneyRecordEntity.setTranFlag(1);
			qzMoneyRecordEntity.setTarnAmount(preRecord.getAmount());
			qzMoneyRecordEntity.setTradeNo(preRecord.getOrderNo());
			qzMoneyRecordEntity.setRemark("充值成功");
			qzMoneyRecordEntity.setLockAmount(new BigDecimal("0.00"));
			if (qzMoneyRecordEntity1 == null) {
				qzMoneyRecordEntity.setCurrentAmount(preRecord.getAmount());
			}else {
				qzMoneyRecordEntity.setCurrentAmount(qzMoneyRecordEntity1.getCurrentAmount().add(preRecord.getAmount()));
			}
			qzMoneyRecordApiService.saveQzMoneyRecord(qzMoneyRecordEntity);


			LOGGER.info("用户订单号："+preRecord.getOrderNo() + "，用户资金流水信息更新成功");
		}catch (Exception e){
			LOGGER.error("用户订单号："+preRecord.getOrderNo() + "，用户资金流水信息更新失败：",e);
		}

	}


	/**
	 * 用户充值记录
	 * @param preRecord
	 */
	private void shopUserRechargeRecord(ThirdPreCompanyRechargeRecord preRecord){

		try {
			NideshopUser nideshopUser = nideshopuserService.selectByMobile(preRecord.getPhone());

			if (nideshopUser == null) {
				LOGGER.info("手机号："+preRecord.getPhone()+"，没有此用户信息");
				return ;
			}

			Integer id = nideshopUser.getId();//获取用户id

			QzRechargeRecordEntity qzRechargeRecordEntity = new QzRechargeRecordEntity();
			qzRechargeRecordEntity.setShopUserId(id);
			qzRechargeRecordEntity.setMobile(preRecord.getPhone());
			qzRechargeRecordEntity.setState("1");
			qzRechargeRecordEntity.setOperateTime(new Date());
			//qzRechargeRecordEntity.setOperateId("");
			qzRechargeRecordEntity.setAmount(preRecord.getAmount());
			qzRechargeRecordEntity.setMemo("易宝充值成功");
			qzRechargeRecordEntity.setTradeNo(preRecord.getOrderNo());
			qzRechargeRecordEntity.setRechargeType(3);//易宝充值成功标识
			qzRechargeRecordEntity.setUpdateTime(new Date());
			qzRechargeRecordApiService.saveQzRechargeRecord(qzRechargeRecordEntity);
			LOGGER.info("用户订单号："+preRecord.getOrderNo() + "，用户充值记录信息更新成功");
		}catch (Exception e){
			LOGGER.error("用户订单号："+preRecord.getOrderNo() + "，用户充值记录信息更新失败：",e);
		}

	}

	/**
	 * 用户账号
	 * @param preRecord
	 */
	private void  shopUserAccount(ThirdPreCompanyRechargeRecord preRecord){
		try {
			NideshopUser nideshopUser = nideshopuserService.selectByMobile(preRecord.getPhone());

			if (nideshopUser == null) {
				LOGGER.info("手机号："+preRecord.getPhone()+"，没有此用户信息");
				return ;
			}

			Integer id = nideshopUser.getId();//获取用户id
			QzUserAccountVo qzUserAccountVo1 = qzUserAccountApiService.queruUserAccountInfo(id);

			QzUserAccountVo qzUserAccountVo = new QzUserAccountVo();
			if (qzUserAccountVo1 == null) {
				qzUserAccountVo.setAmount(preRecord.getAmount());
				qzUserAccountVo.setLast_update_time(new Date());
				qzUserAccountVo.setLock_amount(new BigDecimal("0.00"));
				qzUserAccountVo.setCreate_time(new Date());
				qzUserAccountVo.setShop_user_id(id);
				qzUserAccountVo.setVersion(1);
				qzUserAccountApiService.saveQzUserAccountVo(qzUserAccountVo);
			}else{
				qzUserAccountVo.setShop_user_id(id);
				qzUserAccountVo.setAmount(qzUserAccountVo1.getAmount().add(preRecord.getAmount()));
				qzUserAccountVo.setLast_update_time(new Date());
				qzUserAccountApiService.updateUserAccount(qzUserAccountVo);
			}
			LOGGER.info("用户订单号："+preRecord.getOrderNo() + "，用户账号信息更新成功");
		}catch (Exception e){
			LOGGER.error("用户订单号："+preRecord.getOrderNo() + "，用户账号信息更新失败：",e);
		}

	}
	
	/**
	 * 处理易宝数据
	 */
	private void yeeTradeOrder(ThirdPreCompanyRechargeRecord preRecord,TreeMap<String, String> dataMap){		
		try {
			LOGGER.info("明文数据：" + dataMap);
//			/YeeTradeOrderEntityDTO entity = doubaoPayClient.query(dataMap.get("yborderid"));
			YeeTradeOrderEntityDTO entity = doubaoPayClient.query(preRecord.getOrderNo());
			if(entity != null){
				if (entity.getPayStatus() == 1) {
					// 无需再次回调
					LOGGER.info("本地订单已经处理成功，无需再处理，易宝交易流水号" + dataMap.get("yborderid")+"，系统订单号："+ preRecord.getOrderNo());
					return;
				}
				
				entity.setPayAmount(new BigDecimal(dataMap.get("amount")).divide(new BigDecimal(100)));
				entity.setBankCode(dataMap.get("bankcode"));
				entity.setBank(dataMap.get("bank"));
				entity.setLastno(dataMap.get("lastno"));
				entity.setCardType(dataMap.get("cardtype"));
				
				if(RechargeStatus.FAIL.equals(preRecord.getStatus())){
					entity.setPayStatus(2);// 2失败
					entity.setMsg("error"); //
					entity.setMemo("充值失败");
					doubaoPayClient.update(entity);
				}
				
				if("实际支付给易宝金额跟实际订单金额不一致".equals(preRecord.getRemark())){
					entity.setPayStatus(1);// 1成功
					entity.setMsg("exception"); //异常
					entity.setMemo("实际支付给易宝金额跟实际订单金额不一致");
					doubaoPayClient.update(entity);
				}
				
				if("支付成功".equals(preRecord.getRemark())){
					entity.setPayStatus(1);// 1成功
					entity.setMsg("success");
					entity.setMemo("支付成功");
					doubaoPayClient.update(entity);
				}
			} 
			LOGGER.info("用户订单号："+preRecord.getOrderNo() + "，易宝交易信息更新成功");
		} catch (Exception e) {
			LOGGER.error("用户订单号："+preRecord.getOrderNo() + "，易宝交易信息更新失败：",e);
		}
	}
	
	
	/**
	 * 
	* @Title: HandleCardPaySynchInfo 
	* @Description:    调用斗宝真正的充值
	*  
	* @throws
	 */
	
	public void HandleCardPaySynchInfo(ThirdPreCompanyRechargeRecord preRecord) {
		try {
			CardPaySynchInfo record = new CardPaySynchInfo();
			record.setUserMobile(preRecord.getPhone());
			record.setLoanNbr(preRecord.getOrderThird());
			record.setOrderNo(preRecord.getOrderNo());
			record.setPayAmt(preRecord.getAmount());
			record.setCardType("3");
			record.setQueryCheckUrl(doubaoCardpaysynchinfoUrl);
			record.setCardPayCode("1");
			record.setNotifyCode("0");
			record.setCreateDate(new Date());						
			cardPaySynchInfoService.insertSelective(record);
			
			LOGGER.info("同步信息结果："+record);			
			TreeMap<String, Object> treeMap	= new TreeMap<String, Object>();
			treeMap.put("mobile", preRecord.getPhone());
			treeMap.put("amount", 			preRecord.getAmount());		
			treeMap.put("thirdTradeNo", preRecord.getOrderNo());
			treeMap.put("platformType", preRecord.getAppId());//平台类型
			treeMap.put("queryCheckUrl", doubaoCardpaysynchinfoUrl);
			treeMap.put("cardType", 3);//平台类型				
			JSONObject json =new JSONObject(treeMap);
			String content = json.toString();
	        LOGGER.info("加密前：" + content);  
	        String encrypt = EncryptUtil.aesEncrypt(content); 
	        LOGGER.info("加密后：" + encrypt);  
	        Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("encrypt", encrypt);
	        String sendHttpRequest = HttpClient4Utils.sendHttpRequest(doubaoSubbmitrechargeUrl, paramMap, "UTF-8", true);
	        LOGGER.info("订单号：{}，斗宝充值返回结果：{}。",preRecord.getOrderNo() , sendHttpRequest);
	        JSONObject result = JSON.parseObject(sendHttpRequest);
	        CardPaySynchInfo record1 = new CardPaySynchInfo();
	        record1.setId(record.getId());
	        if ("success".equals(result.getString("code"))) {
	        	record1.setNotifyCode("1");
	        	record1.setNotifyDate(new Date());
	        	cardPaySynchInfoService.updateByPrimaryKeySelective(record1);
	        	LOGGER.info("订单号:{},斗宝充值成功", preRecord.getOrderNo());
			} else {

				record1.setNotifyCode("2");
				record1.setNotifyDate(new Date());
	        	cardPaySynchInfoService.updateByPrimaryKeySelective(record1);
				LOGGER.info("订单号:{},斗宝充值失败返回信息：{}", preRecord.getOrderNo(), sendHttpRequest);				
			}
		} catch (Exception e) {
			LOGGER.error("斗宝充值异常：", e);			
		}
		

		
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws Exception {
//		Map<String, String> generateKeyPair = RSA.generateKeyPair();
//		String privateKey = generateKeyPair.get("privateKey");
//		String publicKey = generateKeyPair.get("publicKey");
//		String sign = MsgDigestUtils.sign("1|2|3|4|5", privateKey);
//		System.out.println(sign);
//		boolean verifySign = MsgDigestUtils.verifySign("1|2|3|4|5", sign, publicKey);
//		System.out.println(verifySign);		
		System.out.println(StringUtils.isBlank("ds  "));
	}

}
	
	

