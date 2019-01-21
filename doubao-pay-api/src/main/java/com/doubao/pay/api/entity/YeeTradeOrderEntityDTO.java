package com.doubao.pay.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 易宝支付订单表实体
 * 表名 yee_trade_order
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2018-12-11 16:34:06
 */
public class YeeTradeOrderEntityDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键

    private Integer id;
    //交易流水号(传给易宝)
    private String yeeOrderNo;
    //交易流水号(关联订单)
    private String tradeNo;
    //用户ID
    private Integer userId;
    //订单状态 0-初始化
    private Integer payStatus;
    //支付方式 0-默认,1-微信支付,2-支付宝支付,3-一键支付
    private Integer payType;
    //支付总金额
    private BigDecimal amount;
    //请求参数
    private String requestParam;
    //响应结果
    private String responseMsg;
    //易宝交易流水号(支付请求成功后)
    private String yborderid;
    //订单状态 init,success,error
    private String msg;
    //错误状态码
    private String errorCode;
    //错误描述
    private String errorMsg;
    //卡类型
    private String cardType;
    //卡号后四位
    private String lastno;
    //支付卡所属银行的名称
    private String bank;
    //支付卡所属银行的编码
    private String bankCode;
    //备注
    private String memo;
    //创建时间
    private Date createTime;
    //实际支付金额
    private BigDecimal payAmount;
    /**
     * 设置：主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：主键
     */
    public Integer getId() {
        return id;
    }
    /**
     * 设置：交易流水号(传给易宝)
     */
    public void setYeeOrderNo(String yeeOrderNo) {
        this.yeeOrderNo = yeeOrderNo;
    }

    /**
     * 获取：交易流水号(传给易宝)
     */
    public String getYeeOrderNo() {
        return yeeOrderNo;
    }
    /**
     * 设置：交易流水号(关联订单)
     */
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    /**
     * 获取：交易流水号(关联订单)
     */
    public String getTradeNo() {
        return tradeNo;
    }
    /**
     * 设置：用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取：用户ID
     */
    public Integer getUserId() {
        return userId;
    }
    /**
     * 设置：订单状态 0-初始化
     */
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 获取：订单状态 0-初始化
     */
    public Integer getPayStatus() {
        return payStatus;
    }
    /**
     * 设置：支付方式 0-默认,1-微信支付,2-支付宝支付,3-一键支付
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
     * 获取：支付方式 0-默认,1-微信支付,2-支付宝支付,3-一键支付
     */
    public Integer getPayType() {
        return payType;
    }
    /**
     * 设置：支付总金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取：支付总金额
     */
    public BigDecimal getAmount() {
        return amount;
    }
    /**
     * 设置：请求参数
     */
    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    /**
     * 获取：请求参数
     */
    public String getRequestParam() {
        return requestParam;
    }
    /**
     * 设置：响应结果
     */
    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    /**
     * 获取：响应结果
     */
    public String getResponseMsg() {
        return responseMsg;
    }
    /**
     * 设置：易宝交易流水号(支付请求成功后)
     */
    public void setYborderid(String yborderid) {
        this.yborderid = yborderid;
    }

    /**
     * 获取：易宝交易流水号(支付请求成功后)
     */
    public String getYborderid() {
        return yborderid;
    }
    /**
     * 设置：订单状态 init,success,error
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取：订单状态 init,success,error
     */
    public String getMsg() {
        return msg;
    }
    /**
     * 设置：错误状态码
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 获取：错误状态码
     */
    public String getErrorCode() {
        return errorCode;
    }
    /**
     * 设置：错误描述
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 获取：错误描述
     */
    public String getErrorMsg() {
        return errorMsg;
    }
    /**
     * 设置：卡类型
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    /**
     * 获取：卡类型
     */
    public String getCardType() {
        return cardType;
    }
    /**
     * 设置：卡号后四位
     */
    public void setLastno(String lastno) {
        this.lastno = lastno;
    }

    /**
     * 获取：卡号后四位
     */
    public String getLastno() {
        return lastno;
    }
    /**
     * 设置：支付卡所属银行的名称
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * 获取：支付卡所属银行的名称
     */
    public String getBank() {
        return bank;
    }
    /**
     * 设置：支付卡所属银行的编码
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * 获取：支付卡所属银行的编码
     */
    public String getBankCode() {
        return bankCode;
    }
    /**
     * 设置：备注
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 获取：备注
     */
    public String getMemo() {
        return memo;
    }
    /**
     * 设置：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

    @Override
    public String toString() {
        return "YeeTradeOrderEntityDTO{" +
                "id=" + id +
                ", yeeOrderNo='" + yeeOrderNo + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", userId=" + userId +
                ", payStatus=" + payStatus +
                ", payType=" + payType +
                ", amount=" + amount +
                ", requestParam='" + requestParam + '\'' +
                ", responseMsg='" + responseMsg + '\'' +
                ", yborderid='" + yborderid + '\'' +
                ", msg='" + msg + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", cardType='" + cardType + '\'' +
                ", lastno='" + lastno + '\'' +
                ", bank='" + bank + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", memo='" + memo + '\'' +
                ", createTime=" + createTime +
                ", payAmount=" + payAmount +
                '}';
    }
}
