package com.doubao.merchant.server.nideshopuser.service.impl;

import com.doubao.merchant.api.nideshopuser.entity.NideshopUser;
import com.doubao.merchant.server.nideshopuser.dao.NideshopUserMapper;
import com.doubao.merchant.server.nideshopuser.service.NideshopuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NideshopuserServiceImpl implements NideshopuserService {

	@Autowired
	private NideshopUserMapper nideshopUserMapper;

	@Override
	public Boolean saveNideshopUser(NideshopUser record) {
		return nideshopUserMapper.saveNideshopUser(record);
	}

	@Override
	public NideshopUser selectByMobile(String phone) {
		return nideshopUserMapper.selectByMobile(phone);
	}

}
