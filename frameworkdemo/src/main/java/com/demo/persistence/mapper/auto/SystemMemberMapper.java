package com.demo.persistence.mapper.auto;

import com.demo.entity.auto.SystemMember;

public interface SystemMemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SystemMember record);

    int insertSelective(SystemMember record);

    SystemMember selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemMember record);

    int updateByPrimaryKey(SystemMember record);
}