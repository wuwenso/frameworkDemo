package com.demo.service;

import com.demo.entity.auto.SystemMember;

public interface ISystemService {

	/**
	 * 查找用户是否存在
	 * @param account
	 * @return
	 */
	int findAccount(String account);
	/**
	 * 登陆
	 * @param account
	 * @param pwd
	 * @return
	 */
	SystemMember findAccount(String account,String pwd);
}
