package com.doubao.merchant.api.recharge.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户充值记录实体
 * 表名 qz_recharge_record
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2018-10-15 16:41:42
 */
public class QzRechargeRecordEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //会员id
    private Integer shopUserId;
    //手机号
    private String mobile;
    //状态：0-初始，1-通过，2-拒绝
    private String state;
    //操作人id
    private Long operateId;
    //审核时间
    private Date operateTime;
    //金额
    private BigDecimal amount;
    //备注
    private String memo;
    //流水号
    private String tradeNo;
    //充值类型 1-后台充值 2-奇速贷充值
    private Integer rechargeType;
    //审核人id
    private Long auditId;
    //修改时间
    private Date updateTime;
    //创建时间
    private Date createTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getShopUserId() {
		return shopUserId;
	}
	public void setShopUserId(Integer shopUserId) {
		this.shopUserId = shopUserId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getOperateId() {
		return operateId;
	}
	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public Integer getRechargeType() {
		return rechargeType;
	}
	public void setRechargeType(Integer rechargeType) {
		this.rechargeType = rechargeType;
	}
	public Long getAuditId() {
		return auditId;
	}
	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

   
    
}
