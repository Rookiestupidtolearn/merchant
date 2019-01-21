package com.doubao.merchant.server.util;

import java.math.BigDecimal;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.doubao.merchant.api.recharge.entity.RequestPreRechargeEntity;
import com.doubao.merchant.api.recharge.entity.RequestRechargeEntity;
import com.doubao.merchant.server.recharge.constant.PreRechargeConstants.ReturnResult;



public class FieldValidation {

	
	public static final String REGEX_MOBILE = "^\\d{11}$";
	/**
	 * 预充值（生成签名和预充值 字段验证）
	 * @param reRechargeRecord
	 * @param flag
	 * @return
	 */
	public static Map<String, Object> preFieldValidation(RequestPreRechargeEntity reRechargeRecord,String flag){
		
			if(StringUtils.isEmpty(reRechargeRecord.getAppId())){
				//return ReturnUtil.returnFail("商户号不能为空");
				return ReturnUtil.returnAny(ReturnResult.FAIL, "100001", "商户号不能为空");
			}
			
			if(reRechargeRecord.getAppId().indexOf(" ") != -1 || reRechargeRecord.getAppId().indexOf(" ") != -1 || reRechargeRecord.getAppId().indexOf("	") != -1){
				//return ReturnUtil.returnFail("商户号不能存在空格");
				return ReturnUtil.returnAny(ReturnResult.FAIL, "100002", "商户号不能存在空格");
			}
			
			if(StringUtils.isEmpty(reRechargeRecord.getPhone())){
				//return ReturnUtil.returnFail("手机号不能为空");
				return ReturnUtil.returnAny(ReturnResult.FAIL, "100010", "手机号不能为空");
			}
			
			if(reRechargeRecord.getPhone().indexOf(" ") != -1 || reRechargeRecord.getPhone().indexOf(" ") != -1 || reRechargeRecord.getPhone().indexOf("	") != -1){
				//return ReturnUtil.returnFail("手机号不能存在空格");
				return ReturnUtil.returnAny(ReturnResult.FAIL, "100011", "手机号不能存在空格");
			}
			
			if(!isMobile(reRechargeRecord.getPhone())){
				//return ReturnUtil.returnFail("手机号码格式错误");
				return ReturnUtil.returnAny(ReturnResult.FAIL, "100012", "手机号码格式错误");
			}
			
			if(StringUtils.isEmpty(reRechargeRecord.getChannelNo())){
				//return ReturnUtil.returnFail("渠道号不能为空");
				return ReturnUtil.returnAny(ReturnResult.FAIL, "100013", "渠道号不能为空");
			}
			
			if(reRechargeRecord.getChannelNo().indexOf(" ") != -1 || reRechargeRecord.getChannelNo().indexOf(" ") != -1 || reRechargeRecord.getChannelNo().indexOf("	") != -1){
				//return ReturnUtil.returnFail("渠道号不能存在空格");
				return ReturnUtil.returnAny(ReturnResult.FAIL, "100014", "渠道号不能存在空格");
			}
			
			if(StringUtils.isEmpty(reRechargeRecord.getRequestId())){
				//return ReturnUtil.returnFail("序列号不能为空");
				return ReturnUtil.returnAny(ReturnResult.FAIL, "100015", "序列号不能为空");
			}
			
			if(reRechargeRecord.getRequestId().indexOf(" ") != -1 || reRechargeRecord.getRequestId().indexOf(" ") != -1 || reRechargeRecord.getRequestId().indexOf("	") != -1){
				//return ReturnUtil.returnFail("序列号不能存在空格");
				return ReturnUtil.returnAny(ReturnResult.FAIL, "100016", "序列号不能存在空格");
			}
			
			if(StringUtils.isEmpty(reRechargeRecord.getOrderNo())){
				//return ReturnUtil.returnFail("订单号不能为空");
				return ReturnUtil.returnAny(ReturnResult.FAIL, "100003", "订单号不能为空");
			}
			
			if(reRechargeRecord.getOrderNo().indexOf(" ") != -1 || reRechargeRecord.getOrderNo().indexOf(" ") != -1 || reRechargeRecord.getOrderNo().indexOf("	") != -1){
				//return ReturnUtil.returnFail("订单号不能存在空格");
				return ReturnUtil.returnAny(ReturnResult.FAIL, "100004", "订单号不能存在空格");
			}
			
			try {
		           
		           if(new BigDecimal(reRechargeRecord.getAmount().intValue()).compareTo(reRechargeRecord.getAmount()) != 0){
		        	  // return ReturnUtil.returnFail("金额必须为正整数");
		        	   return ReturnUtil.returnAny(ReturnResult.FAIL, "100017", "金额必须为正整数");
		           }
		           
		           if (reRechargeRecord.getAmount().intValue() <= 0 ) {
		            //   return ReturnUtil.returnFail("金额必须为大于零的正整数");
		               return ReturnUtil.returnAny(ReturnResult.FAIL, "100018", "金额必须为大于零的正整数");
		           }
		           
		    } catch (Exception e) {
		    		e.printStackTrace();
		           // return ReturnUtil.returnFail("金额必须为正整数");
		            return ReturnUtil.returnAny(ReturnResult.FAIL, "100017", "金额必须为正整数");
		    }
			
			if(!"1".equals(flag)){
				if(StringUtils.isEmpty(reRechargeRecord.getSignature())){
					//return ReturnUtil.returnFail("签名不能为空");
					return ReturnUtil.returnAny(ReturnResult.FAIL, "100005", "签名不能为空");
				}
				
				if(reRechargeRecord.getSignature().indexOf(" ") != -1 || reRechargeRecord.getSignature().indexOf(" ") != -1 || reRechargeRecord.getSignature().indexOf("	") != -1){
					//return ReturnUtil.returnFail("签名不能存在空格");
					return ReturnUtil.returnAny(ReturnResult.FAIL, "100006", "签名不能存在空格");
				}
			}
			
			return ReturnUtil.returnSuccess();
	}
	
	/**
	 * 充值字段验证
	 * @param requestRecharge
	 * @param flag
	 * @return
	 */
	public static Map<String, Object> reFieldValidation(RequestRechargeEntity requestRecharge,String flag){
		if(StringUtils.isEmpty(requestRecharge.getAppId())){
			//return ReturnUtil.returnFail("商户号不能为空");
			return ReturnUtil.returnAny(ReturnResult.FAIL, "100001", "商户号不能为空");
		}
		
		if(requestRecharge.getAppId().indexOf(" ")!=-1 || requestRecharge.getAppId().indexOf(" ")!=-1 || requestRecharge.getAppId().indexOf("	")!=-1){
			//return ReturnUtil.returnFail("商户号不能存在空格");
			return ReturnUtil.returnAny(ReturnResult.FAIL, "100002", "商户号不能存在空格");
		}
		
		if(StringUtils.isEmpty(requestRecharge.getOrderNo())){
			//return ReturnUtil.returnFail("订单号不能为空");
			return ReturnUtil.returnAny(ReturnResult.FAIL, "100003", "订单号不能为空");
		}
		
		if(requestRecharge.getOrderNo().indexOf(" ")!=-1 || requestRecharge.getOrderNo().indexOf(" ")!=-1 || requestRecharge.getOrderNo().indexOf("	")!=-1){
			//return ReturnUtil.returnFail("订单号不能存在空格");
			return ReturnUtil.returnAny(ReturnResult.FAIL, "100004", "订单号不能存在空格");
		}
		
		if(!"1".equals(flag)){
			if(StringUtils.isEmpty(requestRecharge.getSignature())){
				//return ReturnUtil.returnFail("签名不能为空");
				return ReturnUtil.returnAny(ReturnResult.FAIL, "100005", "签名不能为空");
			}
			
			if(requestRecharge.getSignature().indexOf(" ")!=-1 || requestRecharge.getSignature().indexOf(" ")!=-1 || requestRecharge.getSignature().indexOf("	")!=-1){
				//return ReturnUtil.returnFail("签名不能存在空格");
				return ReturnUtil.returnAny(ReturnResult.FAIL, "100006", "签名不能存在空格");
			}
		}
		
		return ReturnUtil.returnSuccess();
	}
	
    /**
     * 校验手机号
     * 
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }
    
    public static boolean isNumeric(String str) {  
        if (str == null) {  
            return false;  
        }  
        int sz = str.length();  
        for (int i = 0; i < sz; i++) {  
            if (Character.isDigit(str.charAt(i)) == false) {  
                return false;  
            }  
        }  
        return true;  
    }  
    
    public static void main(String[] args) {
		System.out.println(isMobile("18510665668"));
	}
}
