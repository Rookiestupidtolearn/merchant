package com.doubao.merchant.api.recharge.entity;

import java.io.Serializable;

/**
 * 
 * 确认支付
 * @author zct
 *
 */
public class RequestRechargeEntity implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 商户号
	 */
	private String  appId;
	
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	
	/**
	 * 签名
	 */
	private String signature;

	
	public String regSignVal(){
		String src = appId + "|" + orderNo ;
		return src;
	}

	public String getAppId() {
		return appId;
	}


	public void setAppId(String appId) {
		this.appId = appId;
	}


	public String getOrderNo() {
		return orderNo;
	}


	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	public String getSignature() {
		return signature;
	}


	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
