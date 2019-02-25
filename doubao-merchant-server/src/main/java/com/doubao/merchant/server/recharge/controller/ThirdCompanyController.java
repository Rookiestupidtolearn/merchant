package com.doubao.merchant.server.recharge.controller;


import com.doubao.merchant.api.recharge.entity.ThridCompany;
import com.doubao.merchant.server.thirdcompany.service.ThirdCompanyService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/thirdCompany")
public class ThirdCompanyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThirdCompanyController.class);

    @Autowired
    private ThirdCompanyService thirdCompanyService;




    @PutMapping("/{appId}/keyPair")
    public Map<String,Object> genKeyPair(@PathVariable(value = "appId")String appId){
        return thirdCompanyService.genKeyPair(appId);
    }

    /**
     * 根据appId查询公私钥
     * @param appId
     * @return
     */
    @ApiOperation(value = "查询")
    @GetMapping("/keyPair")
    public Map<String,Object> getKeyPair(@RequestParam String appId){
        return thirdCompanyService.getKeyPair(appId);

    }

    /**
     * 更新第三方回调url
     * @param appId
     * @param callBackUrl
     * @return
     */
    @PutMapping("/{appId}/callBackUrl")
    public Map<String,Object> updateCallBackUrl(@PathVariable(value = "appId")String appId,
                                                @RequestBody String callBackUrl ){
        return thirdCompanyService.updateCallBackUrl(appId,callBackUrl);
    }


    /**
     * 查询第三方信息
     * @return
     */
    @GetMapping("/query")
    public Map<String,Object> getList(){
        Map<String, Object> res = new HashMap<>();
        res.put("data", thirdCompanyService.getList());
        return res;
    }

    /**
     * 创建第三方信息存在更新没有新建
     * @param thridCompany
     * @return
     */
    @PostMapping("/create")
    public Map<String,Object>create(@RequestBody ThridCompany thridCompany ){
        Map<String, Object> res = new HashMap<>();
        List<ThridCompany> list =  thirdCompanyService.getThridCompanyByAppId(thridCompany.getAppid());
        if (null!= list){
            if(list.size()>1){
                LOGGER.error("【异常商户存在多个商户信息】商户id为：{}",thridCompany.getAppid());
                res.put("error",false);
                return res;
            }
            ThridCompany old=list.get(0);
            thridCompany.setId(old.getId());
            thridCompany.setUpdateDate(new Date());
            thirdCompanyService.update(thridCompany);
            LOGGER.info("【更新商户成功】商户id为：{}",thridCompany.getAppid());
        }else {

            thirdCompanyService.insert(thridCompany);
            LOGGER.info("【新建商户成功】商户id为：{}",thridCompany.getAppid());
        }
        res.put("success", true);
        return res;
    }

}
