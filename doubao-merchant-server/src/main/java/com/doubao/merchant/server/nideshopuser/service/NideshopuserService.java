package com.doubao.merchant.server.nideshopuser.service;

import com.doubao.merchant.api.nideshopuser.entity.NideshopUser;



public interface NideshopuserService {
	/**
	 * 保存用户
	 * @param record
	 * @return
	 */
	Boolean  saveNideshopUser(NideshopUser record);

	/**
	 * 通过手机号查询用户需信息 t
	 * @param phone
	 * @return
	 */
	NideshopUser  selectByMobile(String phone);
}
