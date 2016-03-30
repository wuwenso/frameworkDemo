package com.demo.persistence.mapper.extend;

import org.apache.ibatis.annotations.Param;

import com.demo.entity.auto.SystemMember;

public interface SystemExtendMapper {

	/**
	 * 查找账户是否存在
	 * @param account
	 * @return
	 */
	int findAccount(@Param("account") String account);
	/**
	 * 登陆
	 * @param account
	 * @param pwd
	 * @return
	 */
	SystemMember findLoginUser(@Param("account") String account,@Param("pwd") String pwd);
}
