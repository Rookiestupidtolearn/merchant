package com.doubao.merchant.api.recharge.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 用户资金流水实体
 * 表名 qz_money_record
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2018-10-15 16:41:42
 */
public class QzMoneyRecordEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //会员id
    private Integer shopUserId;
    //资金变动类型 1-充值2 -优惠券
    private String tranType;
    //金额变动标志 0-支出 1-收入
    private Integer tranFlag;
    //变动金额
    private BigDecimal tarnAmount;
    //当前余额
    private BigDecimal currentAmount;
    //创建时间
    private Date createTime;
    //交易流水号(关联各资金订单)
    private String tradeNo;
    //备注
    private String remark;
    //冻结金额
    private BigDecimal lockAmount;

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
     * 设置：会员id
     */
    public void setShopUserId(Integer shopUserId) {
        this.shopUserId = shopUserId;
    }

    /**
     * 获取：会员id
     */
    public Integer getShopUserId() {
        return shopUserId;
    }
    /**
     * 设置：资金变动类型 1-充值
     */
    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    /**
     * 获取：资金变动类型 1-充值
     */
    public String getTranType() {
        return tranType;
    }
    /**
     * 设置：金额变动标志 0-支出 1-收入
     */
    public void setTranFlag(Integer tranFlag) {
        this.tranFlag = tranFlag;
    }

    /**
     * 获取：金额变动标志 0-支出 1-收入
     */
    public Integer getTranFlag() {
        return tranFlag;
    }
    /**
     * 设置：变动金额
     */
    public void setTarnAmount(BigDecimal tarnAmount) {
        this.tarnAmount = tarnAmount;
    }

    /**
     * 获取：变动金额
     */
    public BigDecimal getTarnAmount() {
        return tarnAmount;
    }
    /**
     * 设置：当前余额
     */
    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    /**
     * 获取：当前余额
     */
    public BigDecimal getCurrentAmount() {
        return currentAmount;
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
    /**
     * 设置：交易流水号(关联各资金订单)
     */
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    /**
     * 获取：交易流水号(关联各资金订单)
     */
    public String getTradeNo() {
        return tradeNo;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getLockAmount() {
		return lockAmount;
	}

	public void setLockAmount(BigDecimal lockAmount) {
		this.lockAmount = lockAmount;
	}
    
}
