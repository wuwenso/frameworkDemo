package com.demo.ctrl.car;

import com.demo.constans.MessageTemplate;
import com.demo.ctrl.BaseCtrl;
import com.demo.entity.extend.CarVo;
import com.demo.persistence.dialect.entity.PageResult;
import com.demo.service.ICarService;
import com.demo.util.FastJson;
import com.google.common.base.Strings;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

@Controller
@RequestMapping("pf/car")
public class CarCtrl extends BaseCtrl{

	Logger log=LoggerFactory.getLogger(CarCtrl.class);
	@Resource
	private ICarService carService;
	@RequestMapping
	public String index(){
		return "car/carlist";
	}
	@RequestMapping("carList.do")
	@ResponseBody
	public Map<String,Object> findCarList(PageResult<CarVo> page,@RequestParam Map<String,Object> param){
		Map<String,Object> rs=new HashMap<String,Object>();
		try {
			rs.put("items",carService.findCarList(param, page));
			rs.put("totalCount",page.getTotalCount());
		} catch (Exception e) {
			log.info("查询车辆列表异常...",e);
		}
		return rs;
	}
	@RequestMapping("/delCar.do")
	@ResponseBody
	public Map<String,Object> delCar(@RequestParam("ids[]") Integer[] ids){
		Map<String,Object> rs=new HashMap<String,Object>();
		try {
			log.info("删除参数：{}",FastJson.toJson(ids));
			carService.delCarList(Arrays.asList(ids));
			rs.put("status",1);
			rs.put("message",MessageTemplate.DEL_SUCCESS);
		} catch (Exception e) {
			log.info("删除异常..",e);
			rs.put("status",2);
			rs.put("message",MessageTemplate.DEL_FAILED);
		}
		return rs;
	}
	@RequestMapping("toSaveOrUpdate{id}")
	public String toSaveOrUpdate(@PathVariable("id") Integer id){
		try{
			if(null!=id){
				set("car",carService.getCarById(id));
			}

		}catch (Exception e){
			log.info("异常...",e);
		}

		return "car/modifycar";
	}
}
