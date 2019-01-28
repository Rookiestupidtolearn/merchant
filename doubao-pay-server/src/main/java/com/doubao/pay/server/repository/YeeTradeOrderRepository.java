package com.doubao.pay.server.repository;

import com.doubao.pay.server.entity.YeeTradeOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public  interface YeeTradeOrderRepository extends JpaRepository<YeeTradeOrderEntity,Integer> {

    List< YeeTradeOrderEntity> findYeeTradeOrderEntitiesByYeeOrderNo(String orderNo);





}
