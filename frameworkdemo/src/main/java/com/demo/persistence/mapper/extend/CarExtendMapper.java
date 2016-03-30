package com.demo.persistence.mapper.extend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.demo.entity.extend.CarVo;
import com.demo.persistence.dialect.entity.PageResult;

public interface CarExtendMapper {
	/**
	 * 分页查询
	 * @return
	 */
	List<CarVo> findCarList(@Param("map")Map<String,Object> map,PageResult<CarVo> page);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int delCarList(@Param("ids") List<Integer> ids);
}
