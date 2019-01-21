package com.doubao.pay.server.controller;


import com.doubao.pay.api.entity.YeeTradeOrderEntityDTO;
import com.doubao.pay.server.service.YeeTradeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/yeeTradeOrder")
public class YeeTradeOrderController {

    @Autowired
    private YeeTradeOrderService yeeTradeOrderService;
    @GetMapping("/")
    public YeeTradeOrderEntityDTO query(@RequestParam ("orderNo") String orderNo){
       return yeeTradeOrderService.query(orderNo);
    }


    @PostMapping("/")
    public void update(@RequestBody YeeTradeOrderEntityDTO yeeTradeOrderEntityDTO){
        yeeTradeOrderService.update(yeeTradeOrderEntityDTO);
    }

}
