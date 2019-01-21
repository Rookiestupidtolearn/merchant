package com.doubao.merchant.api.recharge.entity;

public class QueryMemberCardResponse {

	private String tradeAmt;
	private String cardTradeNbr ;
	private String tradeCde;

	public String getTradeAmt() {
		return tradeAmt;
	}
	public void setTradeAmt(String tradeAmt) {
		this.tradeAmt = tradeAmt;
	}
	public String getCardTradeNbr() {
		return cardTradeNbr;
	}
	public void setCardTradeNbr(String cardTradeNbr) {
		this.cardTradeNbr = cardTradeNbr;
	}
	public String getTradeCde() {
		return tradeCde;
	}
	public void setTradeCde(String tradeCde) {
		this.tradeCde = tradeCde;
	}
	
}
