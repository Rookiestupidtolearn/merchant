package com.doubao.merchant.api.recharge.entity;

public class QueryMemberCardRequest {
  private String cardTradeNbr  ;
  private String  cardTradeType ;
  private String userMobile ;
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

}
