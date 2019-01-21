package com.doubao.pay.server.service;


import com.doubao.pay.server.config.MerchantInfoConfig;
import com.doubao.pay.server.entity.DTO.ThirdPreCompanyRechargeRecordDTO;
import com.doubao.pay.server.entity.YeeTradeOrderEntity;
import com.doubao.pay.server.payutil.PaymobileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
public class PayOrderBizService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private PaymobileUtils paymobileUtils;
    @Autowired
    private MerchantInfoConfig merchantInfoConfig;
    @Autowired
    private YeeTradeOrderService yeeTradeOrderService;

    /**
     * 商户充值，所用易宝支付
     * @param oldThirdPreCompanyRechargeRecord
     * @param
     * @return
     */
    public Map<String, Object> yeepayRechargeSubmmit(ThirdPreCompanyRechargeRecordDTO oldThirdPreCompanyRechargeRecord) {
        Map<String, Object> resultObj = new HashMap<>();
        //创建易宝订单实体
        YeeTradeOrderEntity entity = new YeeTradeOrderEntity();
        entity.setYeeOrderNo(oldThirdPreCompanyRechargeRecord.getOrderNo());
        entity.setTradeNo(String.valueOf(System.currentTimeMillis()));
        entity.setUserId(oldThirdPreCompanyRechargeRecord.getUserId());
        entity.setPayStatus(0); //初始
        entity.setPayType(0);
        entity.setAmount(oldThirdPreCompanyRechargeRecord.getAmount());

        System.out.println(entity);
        int amount = oldThirdPreCompanyRechargeRecord.getAmount().multiply(new BigDecimal(100)).intValue();//实际下单的金额
        //使用TreeMap
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("orderid", entity.getYeeOrderNo());
        treeMap.put("transtime", Calendar.getInstance().getTimeInMillis() / 1000);//带秒的时间戳
        treeMap.put("amount", amount);//以分为单位
        treeMap.put("currency", 156); //交易币种
        treeMap.put("productcatalog", "20");//行业类别 20 代表其他
        treeMap.put("productname", "斗宝俱乐部-斗宝精品充值");
//		treeMap.put("productdesc", 		"斗宝商铺");
        treeMap.put("identitytype", 2); //2 代表用户ID
        treeMap.put("identityid", entity.getUserId().toString());  //用户id
//		treeMap.put("terminaltype", 	3);
//		treeMap.put("terminalid", 		"44-45-53-54-00-00");
        treeMap.put("userip", oldThirdPreCompanyRechargeRecord.getIp());
        //	treeMap.put("userip", 			"192.168.1.1");
        treeMap.put("version", 0);

////		treeMap.put("fcallbackurl", 	fcallbackurl);
        //需要配置回调地址
        treeMap.put("callbackurl", merchantInfoConfig.getAppIdRechargeCallbackApi());
//		treeMap.put("paytypes", 		paytypes);

//		treeMap.put("orderexpdate", 	orderexpdate);
        //
//		treeMap.put("cardno", 			cardno);
//		treeMap.put("idcardtype", 		idcardtype);
//		treeMap.put("idcard", 			idcard);
//		treeMap.put("owner", 			owner);
        entity.setRequestParam(treeMap.toString());
        logger.info("商户充值，易宝支付请求参数：" + treeMap.toString());
        //第一步 生成AESkey及encryptkey
        String AESKey = paymobileUtils.buildAESKey();
        String encryptkey = paymobileUtils.buildEncyptkey(AESKey);

        //第二步 生成data
        String data = paymobileUtils.buildData(treeMap, AESKey);

        //第三步 获取商户编号及请求地址，并组装支付链接
        String merchantaccount = merchantInfoConfig.getMerchantaccount();
        String url = merchantInfoConfig.getPayApi();
        TreeMap<String, String> responseMap = paymobileUtils.httpPost(url, merchantaccount, data, encryptkey);
        //第四步 判断请求是否成功
        if (responseMap.containsKey("error_code")) {
            logger.error("易宝订单请求支付失败");
            entity.setResponseMsg(responseMap.toString());
            entity.setMsg("error");
            entity.setErrorCode(responseMap.get("error_code"));
            entity.setErrorMsg(responseMap.get("error_msg"));
            resultObj.put("payurl", "");
            yeeTradeOrderService.save(entity);
            return resultObj;
        }

        //第五步 请求成功，则获取data、encryptkey，并将其解密
        String data_response = responseMap.get("data");
        String encryptkey_response = responseMap.get("encryptkey");
        TreeMap<String, String> responseDataMap = paymobileUtils.decrypt(data_response, encryptkey_response);

        //第六步 sign验签
        if (!paymobileUtils.checkSign(responseDataMap)) {
            logger.error("sign 验签失败！");
            entity.setResponseMsg(responseMap.toString());
            entity.setMsg("error");
            entity.setErrorCode(responseMap.get("error_code"));
            entity.setErrorMsg(responseMap.get("error_msg"));
            resultObj.put("payurl", "");
            yeeTradeOrderService.save(entity);
            return resultObj;
        }

        //第七步 判断请求是否成功
        if (responseDataMap.containsKey("error_code")) {
            logger.error("支付响应未成功返回" + responseDataMap.toString());
            entity.setResponseMsg(responseMap.toString());
            entity.setMsg("error");
            entity.setErrorCode(responseMap.get("error_code"));
            entity.setErrorMsg(responseMap.get("error_msg"));
            resultObj.put("payurl", "");
            yeeTradeOrderService.save(entity);
            return resultObj;

        }
        resultObj.put("payurl", responseDataMap.get("payurl"));
        entity.setResponseMsg(responseDataMap.toString());
        entity.setMsg("init");
        entity.setYborderid(responseDataMap.get("yborderid"));
        yeeTradeOrderService.save(entity);

        return resultObj;

    }

}
