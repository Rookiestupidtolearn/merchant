package com.doubao.merchant.server.recharge.constant;

/**
 * 预充值常量类
 * @author Administrator
 *
 */
public class PreRechargeConstants {

	
		/**
		 * 返回给三方的结果常量
		 */
		public static class ReturnResult{
			
			public static  final String SUCCESS ="success" ;
			
			public static  final String FAIL ="fail" ;
			
			public static  final String SUCCESS_CODE ="000000";
			
			public static  final String FAIL_CODE ="999999";
			
		}
		
		
		
		
		/**
		 * 充值状态
		 * @author zct
		 */
		public static class RechargeStatus{
			
			public static  final String SUCCESS ="2" ;
			
			public static  final String INIT ="0" ;
			
			public static  final String FAIL ="3" ;
			
			public static  final String PROCESS ="1" ;
			
			public static  final String INVALID ="4" ;
			
		}
		
		
	
}
