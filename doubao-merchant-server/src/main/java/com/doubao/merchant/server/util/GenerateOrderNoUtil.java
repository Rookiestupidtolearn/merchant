package com.doubao.merchant.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerateOrderNoUtil {
	
	 private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	    /**
	     * 生成订单号
	     *
	     * @param pre 订单号前缀
	     * @return
	     */
	    public static String gens(String pre, Long museId) {
	        //生成
	        String orderNo = pre + sdf.format(new Date()) + (1 + (int) (Math.random() * 10000)) + museId;
	        return orderNo;
	    }

	    /**
	     * 生成订单号
	     *
	     * @param pre 订单号前缀
	     * @return
	     */
	    public static String gen(String pre, String museId) {
	        //生成
	        String orderNo = pre + ((int)((Math.random()*9+1)*10000)) + museId + (System.currentTimeMillis() / 1000);
	        return orderNo;
	    }

	    public static void main(String[] args) {
//	        System.out.println(gens("R",530L));
	        System.out.println(gen("R","530L"));
	    }



}
