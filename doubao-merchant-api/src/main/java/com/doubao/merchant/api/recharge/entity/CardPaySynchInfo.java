package com.doubao.merchant.api.recharge.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *   同步信息
 */
public class CardPaySynchInfo implements Serializable{

    private static final long serialVersionUID = 1229552519150324361L;
    private Integer id;

    private Integer userId;
    /**
     * 登陆用户手机号
     */
    private String userMobile;
    /**
     * 申请单 编号
     */
    private String loanNbr;

    /**
     *订单编号
     */
    private String orderNo;
    /**
     *支付金额
     */
    private BigDecimal payAmt;
    /**
     *优惠券金额
     */
    private BigDecimal couponAmt;
    /**
     *会员卡 展期卡
     */
    private String cardType;
    /**
     *查询核验地址
     */
    private String queryCheckUrl;
    /**
     *卡支付状态
     */
    private String cardPayCode;
    /**
     * 通知状态
     */
    private String notifyCode;
    /**
     *通知地址
     */
    private String notifyUrl;
    /**
     * 通知日期
     */
    private Date notifyDate;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
    * 开始时间
     */
    private Date createDateStart;
    /**
     * 结束时间
     */
    private Date createDateEnd;

    //起始位置
    private Integer offset;
    //每页条数
    private Integer limit;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile == null ? null : userMobile.trim();
    }

    public String getLoanNbr() {
        return loanNbr;
    }

    public void setLoanNbr(String loanNbr) {
        this.loanNbr = loanNbr == null ? null : loanNbr.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public BigDecimal getCouponAmt() {
        return couponAmt;
    }

    public void setCouponAmt(BigDecimal couponAmt) {
        this.couponAmt = couponAmt;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public String getQueryCheckUrl() {
        return queryCheckUrl;
    }

    public void setQueryCheckUrl(String queryCheckUrl) {
        this.queryCheckUrl = queryCheckUrl == null ? null : queryCheckUrl.trim();
    }

    public String getCardPayCode() {
        return cardPayCode;
    }

    public void setCardPayCode(String cardPayCode) {
        this.cardPayCode = cardPayCode == null ? null : cardPayCode.trim();
    }

    public String getNotifyCode() {
        return notifyCode;
    }

    public void setNotifyCode(String notifyCode) {
        this.notifyCode = notifyCode == null ? null : notifyCode.trim();
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl == null ? null : notifyUrl.trim();
    }

    public Date getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(Date notifyDate) {
        this.notifyDate = notifyDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Date getCreateDateStart() {
        return createDateStart;
    }

    public void setCreateDateStart(Date createDateStart) {
        this.createDateStart = createDateStart;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

	@Override
	public String toString() {
		return "CardPaySynchInfo [id=" + id + ", userId=" + userId
				+ ", userMobile=" + userMobile + ", loanNbr=" + loanNbr
				+ ", orderNo=" + orderNo + ", payAmt=" + payAmt
				+ ", couponAmt=" + couponAmt + ", cardType=" + cardType
				+ ", queryCheckUrl=" + queryCheckUrl + ", cardPayCode="
				+ cardPayCode + ", notifyCode=" + notifyCode + ", notifyUrl="
				+ notifyUrl + ", notifyDate=" + notifyDate + ", createDate="
				+ createDate + ", createDateStart=" + createDateStart
				+ ", createDateEnd=" + createDateEnd + ", offset=" + offset
				+ ", limit=" + limit + "]";
	}
}