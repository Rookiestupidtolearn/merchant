package com.doubao.pay.server.controller;

import com.doubao.pay.server.entity.DTO.ThirdPreCompanyRechargeRecordDTO;
import com.doubao.pay.server.payutil.PaymobileUtils;
import com.doubao.pay.server.service.PayOrderBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/recharge")
public class PreRechargeController {


    private  final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private PayOrderBizService payOrderBizService;
    @Autowired
    private PaymobileUtils paymobileUtils;
    @PostMapping("/recharge")
    public Map<String,Object> recharge(@RequestBody ThirdPreCompanyRechargeRecordDTO thirdPreCompanyRechargeRecordDTO){
        return payOrderBizService.yeepayRechargeSubmmit(thirdPreCompanyRechargeRecordDTO);
    }


    @GetMapping("/verifySign" )
    public boolean sign(@RequestParam("data")String data,
                        @RequestParam("encryptkey") String encryptkey){
        TreeMap<String, String> treeMap = paymobileUtils.decrypt(data, encryptkey);
        if (null==treeMap){
            LOGGER.info("预充值验签失败 treeMap为空传入的数据data为{}，encryptkey为{}",data,encryptkey);
            return false;
        }
        return paymobileUtils.checkSign(treeMap);
    }


    @GetMapping("/decrypt")
    public TreeMap<String ,String > decrypt(@RequestParam("data")String data,
                                        @RequestParam("encryptkey") String encryptkey){
        LOGGER.info("data: "+ data + "encryptkey" + encryptkey);
        return paymobileUtils.decrypt(data, encryptkey);
    }
}
