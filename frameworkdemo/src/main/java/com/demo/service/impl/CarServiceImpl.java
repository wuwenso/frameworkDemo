package com.demo.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.demo.entity.auto.Car;
import com.demo.persistence.mapper.auto.CarMapper;
import org.springframework.stereotype.Service;

import com.demo.entity.extend.CarVo;
import com.demo.persistence.dialect.entity.PageResult;
import com.demo.persistence.mapper.extend.CarExtendMapper;
import com.demo.service.ICarService;

@Service
public class CarServiceImpl implements ICarService{

	@Resource
	private CarExtendMapper carExtendMapper;
	@Resource
	private CarMapper carMapper;
	
	@Override
	public List<CarVo> findCarList(Map<String, Object> map,
			PageResult<CarVo> page) {
		return carExtendMapper.findCarList(map, page);
	}



	@Override
	public void delCarList(List<Integer> ids) {
		carExtendMapper.delCarList(ids);
	}

	@Override
	public Car getCarById(int id) {
		return carMapper.selectByPrimaryKey(id);
	}

}
