package com.doubao.merchant.api.recharge.entity;



import java.math.BigDecimal;
import java.util.Date;

/**
 * 预支付实体
 * @author zct
 *
 */
public class ThirdPreCompanyRechargeRecord {
	
    /**
     * id
     */
    private Integer id;

    /**
     * 商户Id
     */
    private String appId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 三方订单号
     */
    private String orderThird;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 支付渠道
     */
    private String channelPay;

    /**
     * 三方渠道
     */
    private String channelThird;

    /**
     *状态 
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public Integer getId() {
    	
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getOrderThird() {
        return orderThird;
    }

    public void setOrderThird(String orderThird) {
        this.orderThird = orderThird == null ? null : orderThird.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getChannelPay() {
        return channelPay;
    }

    public void setChannelPay(String channelPay) {
        this.channelPay = channelPay == null ? null : channelPay.trim();
    }

    public String getChannelThird() {
        return channelThird;
    }

    public void setChannelThird(String channelThird) {
        this.channelThird = channelThird == null ? null : channelThird.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ThirdPreCompanyRechargeRecord{" +
                "id=" + id +
                ", appId='" + appId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", orderThird='" + orderThird + '\'' +
                ", amount=" + amount +
                ", phone='" + phone + '\'' +
                ", channelPay='" + channelPay + '\'' +
                ", channelThird='" + channelThird + '\'' +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}