package com.doubao.merchant.server;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages ={"com.doubao.pay.api.client"})
@EnableSwagger2
@MapperScan(basePackages = {"com.doubao.merchant.server.*.dao","com.doubao.merchant.server.thirdcompany.service.dao"})
public class DoubaoMerchantServerApplication {

	
	/**
	 * 延时队列
	 * @return
	 */
	@Bean
	public Queue delayQueue() {
		return new ActiveMQQueue("doubaoMerchantDelayQueue");
	}

	@Bean
	public Queue delayQueue1() {
		return new ActiveMQQueue("23");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DoubaoMerchantServerApplication.class, args);
	}

}

