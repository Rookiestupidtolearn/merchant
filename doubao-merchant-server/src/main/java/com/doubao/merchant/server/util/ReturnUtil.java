package com.doubao.merchant.server.util;

import java.util.HashMap;
import java.util.Map;

import com.doubao.merchant.server.recharge.constant.PreRechargeConstants.ReturnResult;



/**
 * 
 * 返回工具
 * 
 * @author zct
 * 
 *
 */
public class ReturnUtil {

	/**
	 * 返回成功结果
	 * 
	 * @return
	 */
	public static Map<String, Object> returnSuccess() {
		Map<String, Object> result = getMap();
		result.put("msg", ReturnResult.SUCCESS);
		result.put("code", ReturnResult.SUCCESS_CODE);
		return result;
	}

	/**
	 * 返回失败结果
	 * 
	 * @return
	 */
	public static Map<String, Object> returnFail(String remark) {
		Map<String, Object> result = getMap();
		result.put("msg", ReturnResult.FAIL);
		result.put("code", ReturnResult.FAIL_CODE);
		result.put("remark", remark);
		return result;
	}

	/**
	 * 
	 * 自定义返回
	 * @param msg
	 * @param code
	 * @param remark
	 * @return
	 */
	public static Map<String, Object> returnAny(String msg,String code ,String remark) {
		Map<String, Object> result = getMap();
		result.put("msg", msg);
		result.put("code", code);
		result.put("remark", remark);
		return result;
	}
	
	/**
	 * 获取map
	 * 
	 * @return
	 */
	public static Map<String, Object> getMap() {
		return new HashMap<String, Object>();
	}

}
