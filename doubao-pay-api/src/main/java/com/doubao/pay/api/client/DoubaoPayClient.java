package com.doubao.pay.api.client;

import com.doubao.pay.api.entity.ThirdPreCompanyRechargeRecordDTO;
import com.doubao.pay.api.entity.YeeTradeOrderEntityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.TreeMap;

@FeignClient(name = "doubao-pay-server")
public interface DoubaoPayClient {

    //验证签名
    @GetMapping("/recharge/verifySign" )
    boolean sign(@RequestParam("data")String data,
                 @RequestParam("encryptkey") String encryptkey);

    //更新易宝订单状态
    @PostMapping("/yeeTradeOrder/")
    void update(@RequestBody YeeTradeOrderEntityDTO yeeTradeOrderEntityDTO);

    //生成支付url
    @PostMapping("/recharge/recharge")
    Map<String,Object> recharge(@RequestBody ThirdPreCompanyRechargeRecordDTO thirdPreCompanyRechargeRecordDTO);

    //查询
    @GetMapping("/yeeTradeOrder/")
    YeeTradeOrderEntityDTO query(@RequestParam("orderNo") String orderNo);

    //易保回调数据解析
    @GetMapping("/recharge/decrypt")
    TreeMap<String ,String > decrypt(@RequestParam("data")String data,
                                 @RequestParam("encryptkey") String encryptkey);

}
