package com.doubao.merchant.api.recharge.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class QzUserAccountVo implements Serializable{
	 private static final long serialVersionUID = 1L;
	 
	 //主键
    private Long id;
    //用户余额
    private BigDecimal amount;
    //冻结金额
    private BigDecimal lock_amount;
    //修改时间
    private Date last_update_time;
    //创建时间
    private Date create_time;
    
    private Integer shop_user_id;
    
    private Integer version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getLast_update_time() {
		return last_update_time;
	}

	public void setLast_update_time(Date last_update_time) {
		this.last_update_time = last_update_time;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Integer getShop_user_id() {
		return shop_user_id;
	}

	public void setShop_user_id(Integer shop_user_id) {
		this.shop_user_id = shop_user_id;
	}

	public BigDecimal getLock_amount() {
		return lock_amount;
	}

	public void setLock_amount(BigDecimal lock_amount) {
		this.lock_amount = lock_amount;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
    
}
