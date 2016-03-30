package com.demo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.demo.entity.auto.SystemMember;
import com.demo.persistence.mapper.extend.SystemExtendMapper;
import com.demo.service.ISystemService;

@Service
public class SystemServiceImpl implements ISystemService{

	@Resource
	SystemExtendMapper systemExtendMapper;
	
	@Override
	public int findAccount(String account) {
		return systemExtendMapper.findAccount(account);
	}

	@Override
	public SystemMember findAccount(String account, String pwd) {
		return systemExtendMapper.findLoginUser(account, pwd);
	}

	
	
}
