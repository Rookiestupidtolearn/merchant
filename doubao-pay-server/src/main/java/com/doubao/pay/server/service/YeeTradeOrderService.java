package com.doubao.pay.server.service;

import com.doubao.pay.api.entity.YeeTradeOrderEntityDTO;
import com.doubao.pay.server.entity.YeeTradeOrderEntity;
import com.doubao.pay.server.repository.YeeTradeOrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("yeeTradeOrderService")
public class YeeTradeOrderService {

    @Autowired
    private YeeTradeOrderRepository yeeTradeOrderRepository;

    public void save(YeeTradeOrderEntity entity) {
        yeeTradeOrderRepository.save(entity);
    }

    public YeeTradeOrderEntityDTO query(String orderNo){
        List<YeeTradeOrderEntity> byYeeOrderNos = yeeTradeOrderRepository.findYeeTradeOrderEntitiesByYeeOrderNo(orderNo);
        if (byYeeOrderNos ==null || byYeeOrderNos.size() ==0){
            return null;
        }
        YeeTradeOrderEntityDTO res = new YeeTradeOrderEntityDTO();
        BeanUtils.copyProperties(byYeeOrderNos.get(byYeeOrderNos.size()-1),res);
        return res;
    }

    public void update(YeeTradeOrderEntityDTO yeeTradeOrderEntityDTO) {
        YeeTradeOrderEntity entity = new YeeTradeOrderEntity();
        BeanUtils.copyProperties(yeeTradeOrderEntityDTO,entity);
        yeeTradeOrderRepository.save(entity);
    }
}
