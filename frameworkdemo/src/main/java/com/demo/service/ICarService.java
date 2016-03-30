package com.demo.service;

import java.util.List;
import java.util.Map;

import com.demo.entity.auto.Car;
import org.apache.ibatis.annotations.Param;

import com.demo.entity.extend.CarVo;
import com.demo.persistence.dialect.entity.PageResult;

public interface ICarService {
	/**
	 * 分页查询
	 * @param map
	 * @param page
	 * @return
	 */
	List<CarVo> findCarList(@Param("map")Map<String,Object> map,PageResult<CarVo> page);
	/**
	 * 删除
	 * @param ids
	 */
	void delCarList(List<Integer> ids);

	/**
	 * 根据id查询car
	 * @param id
	 * @return
	 */
	Car getCarById(int id);
}
