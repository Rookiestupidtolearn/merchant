package com.doubao.merchant.server.recharge.controller;


import com.doubao.merchant.server.thirdcompany.service.ThirdCompanyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/thirdCompany")
public class ThirdCompanyController {

    @Autowired
    private ThirdCompanyService thirdCompanyService;


    @PutMapping("/{appId}/keyPair")
    public Map<String,Object> genKeyPair(@PathVariable(value = "appId")String appId){
        return thirdCompanyService.genKeyPair(appId);
    }


    @ApiOperation(value = "查询")
    @GetMapping("/keyPair")
    public Map<String,Object> getKeyPair(@RequestParam String appId){
        return thirdCompanyService.getKeyPair(appId);

    }

    @PutMapping("/{appId}/callBackUrl")
    public Map<String,Object> updateCallBackUrl(@PathVariable(value = "appId")String appId,
                                                @RequestBody String callBackUrl ){
        return thirdCompanyService.updateCallBackUrl(appId,callBackUrl);
    }






}
