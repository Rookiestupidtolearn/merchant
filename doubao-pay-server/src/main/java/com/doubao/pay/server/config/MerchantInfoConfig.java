package com.doubao.pay.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("merchant")
@PropertySource("classpath:yeepay.properties")
public class MerchantInfoConfig {
    //一键支付移动端paymobile，生产环境测试商户编号
    private String merchantaccount;
    //商户私钥
    private String merchantPrivateKey;
    //易宝公玥
    private String yeepayPublicKey;
    //订单支付接口请求地址
    private String PayApi;
    //#订单支付回调
    private String OrderCallbackApi;
    //
    private String OrderFcallbackurlApi;
    //三方商城充值回调
    private String AppIdRechargeCallbackApi;
    //订单查询接口请求地址
    private String QueryOrderApi;
    //消费对账文件下载接口请求地址
    private String PayClearDataApi;
    //退款查询接口请求地址
    private String RefundApi;
    private String QueryRefundApi;
    //退款对账文件下载接口
    private String RefundClearDataApi;
    //银行卡信息查询接口请求地址
    private String CheckBankcardApi;
    //查询绑卡信息列表
    private String QueryBankCardListApi;
    //解绑卡
    private String UnbindCardApi;

    public String getMerchantaccount() {
        return merchantaccount;
    }

    public void setMerchantaccount(String merchantaccount) {
        this.merchantaccount = merchantaccount;
    }

    public String getMerchantPrivateKey() {
        return merchantPrivateKey;
    }

    public void setMerchantPrivateKey(String merchantPrivateKey) {
        this.merchantPrivateKey = merchantPrivateKey;
    }

    public String getYeepayPublicKey() {
        return yeepayPublicKey;
    }

    public void setYeepayPublicKey(String yeepayPublicKey) {
        this.yeepayPublicKey = yeepayPublicKey;
    }

    public String getPayApi() {
        return PayApi;
    }

    public void setPayApi(String payApi) {
        PayApi = payApi;
    }

    public String getOrderCallbackApi() {
        return OrderCallbackApi;
    }

    public void setOrderCallbackApi(String orderCallbackApi) {
        OrderCallbackApi = orderCallbackApi;
    }

    public String getOrderFcallbackurlApi() {
        return OrderFcallbackurlApi;
    }

    public void setOrderFcallbackurlApi(String orderFcallbackurlApi) {
        OrderFcallbackurlApi = orderFcallbackurlApi;
    }

    public String getAppIdRechargeCallbackApi() {
        return AppIdRechargeCallbackApi;
    }

    public void setAppIdRechargeCallbackApi(String appIdRechargeCallbackApi) {
        AppIdRechargeCallbackApi = appIdRechargeCallbackApi;
    }

    public String getQueryOrderApi() {
        return QueryOrderApi;
    }

    public void setQueryOrderApi(String queryOrderApi) {
        QueryOrderApi = queryOrderApi;
    }

    public String getPayClearDataApi() {
        return PayClearDataApi;
    }

    public void setPayClearDataApi(String payClearDataApi) {
        PayClearDataApi = payClearDataApi;
    }

    public String getRefundApi() {
        return RefundApi;
    }

    public void setRefundApi(String refundApi) {
        RefundApi = refundApi;
    }

    public String getQueryRefundApi() {
        return QueryRefundApi;
    }

    public void setQueryRefundApi(String queryRefundApi) {
        QueryRefundApi = queryRefundApi;
    }

    public String getRefundClearDataApi() {
        return RefundClearDataApi;
    }

    public void setRefundClearDataApi(String refundClearDataApi) {
        RefundClearDataApi = refundClearDataApi;
    }

    public String getCheckBankcardApi() {
        return CheckBankcardApi;
    }

    public void setCheckBankcardApi(String checkBankcardApi) {
        CheckBankcardApi = checkBankcardApi;
    }

    public String getQueryBankCardListApi() {
        return QueryBankCardListApi;
    }

    public void setQueryBankCardListApi(String queryBankCardListApi) {
        QueryBankCardListApi = queryBankCardListApi;
    }

    public String getUnbindCardApi() {
        return UnbindCardApi;
    }

    public void setUnbindCardApi(String unbindCardApi) {
        UnbindCardApi = unbindCardApi;
    }
}
