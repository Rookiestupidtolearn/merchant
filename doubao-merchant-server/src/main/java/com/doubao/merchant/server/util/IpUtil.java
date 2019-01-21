package com.doubao.merchant.server.util;

import javax.servlet.http.HttpServletRequest;

/** 
 * @ClassName: IpUtil 
 * @Description: 
 * @author ycs
 * @date 2018年12月26日 上午11:42:06
 *
 */
public class IpUtil {
	//获取用户真实ip方法二
    public static String getIpAddr(HttpServletRequest request) {

        String ip = request.getHeader("x - forwarded - for");

        if (ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)){

            ip = request.getHeader("Proxy - Client - IP");

        }

        if (ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)){

            ip = request.getHeader("WL - Proxy - Client - IP");

        }

        if (ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)){

            ip = request.getRemoteAddr();

        }
        return ip;
    }


    public static void main(String[] args) {


    }


}
