package com.doubao.merchant.api.recharge.entity;


import java.io.Serializable;

import org.springframework.util.StringUtils;

/**
 * @Package: com.qzjf.qisudai.mall.vo
 * @Description:  查询订单支付流水的状态
 * @Author: WangHui
 * @CreateDate: 2018/12/18 16:46
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class QueryMemberCardReqVO implements Serializable{
    private static final long serialVersionUID = 3353059427662226579L;
    /**
     * card交易流水
     */
    private  String  cardTradeNbr;
    /**
     * card 交易类型
     */
    private  String  cardTradeType;
    /**
     * 查询用户手机号
     */
    private  String  userMobile;

    public String getCardTradeNbr() {
        return cardTradeNbr;
    }

    public void setCardTradeNbr(String cardTradeNbr) {
        this.cardTradeNbr = cardTradeNbr;
    }

    public String getCardTradeType() {
        return cardTradeType;
    }

    public void setCardTradeType(String cardTradeType) {
        this.cardTradeType = cardTradeType;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public QueryMemberCardReqVO() {
    }

    public  boolean isEmpty(){
        return (StringUtils.isEmpty(this.cardTradeNbr) || StringUtils.isEmpty(this.cardTradeType)|| StringUtils.isEmpty(this.userMobile));
    }
}

