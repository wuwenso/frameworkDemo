package com.demo.util.execl;

import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.ReflectionUtils;

import com.demo.util.execl.annotation.ExcelSource;

public class ExcelUtil {
	public static <T> void createExcelOutput(OutputStream out,String sheetName, Class<T> clz, List<T> dataSource){
		createExcelOutput(out, sheetName, clz, null, dataSource);
	}
	public static <T> void createExcelOutput(OutputStream out,String sheetName, Class<T> clz,String[] filterProperty, List<T> dataSource){
		List<ExcelHead> heads=parseExcelHead(clz,filterProperty);
		//数字格式
		DecimalFormat decimalFormat=new DecimalFormat();
		//时间格式
		SimpleDateFormat dateFormat=new SimpleDateFormat();
		Workbook workbook=null;
		try {
			workbook=new HSSFWorkbook();
			Sheet sheet=workbook.createSheet(sheetName);
			sheet.createFreezePane(0, 1);
			//设置Excel字体
			Font font=workbook.createFont();
			font.setFontHeightInPoints((short) 12);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			CellStyle style=workbook.createCellStyle();
			style.setFont(font);
			//设置excel表头
			Row firstRow=sheet.createRow(0);
			for (int i = 0; i <heads.size(); i++) {
				ExcelHead head=heads.get(i);
				Cell cell= firstRow.createCell(i);
				cell.setCellValue(head.getTitle());
			}
			for(int i=0;i<dataSource.size();i++){
				T t=dataSource.get(i);
				Row row=sheet.createRow(i+1);
				for(int j=0;j<heads.size();j++){
					Cell cell= row.createCell(j);
					Object value=getProperty(t,heads.get(j).getPropertyName());
					String cellValue=value==null?"":value+"";
					//数字转换格式
					String numberPattern= heads.get(j).getNumberPattern();
					//日期转换格式
					String dataPattern=heads.get(j).getDatePattern();
					if(value!=null&&value instanceof Date ){
						dateFormat.applyPattern(dataPattern);
						cellValue=dateFormat.format(value);
					}else if(value!=null && value instanceof Number && !StringUtils.isBlank(numberPattern)){
						decimalFormat.applyPattern(numberPattern);
						cellValue=decimalFormat.format((Number)value);
					}
					cell.setCellValue(cellValue);
				}
			}
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取类 方法上的注解
	 * @param clz
	 * @return
	 */
	private static List<ExcelHead> parseExcelHead(Class<?> clz,String[] filterProperty){
		Method[] methods= ReflectionUtils.getAllDeclaredMethods(clz);
		List<ExcelHead> list=new ArrayList<ExcelHead>();
		for (Method method : methods) {
			if(method.isAnnotationPresent(ExcelSource.class)){
				ExcelSource excelSource=method.getAnnotation(ExcelSource.class);
				String field=Character.toLowerCase(method.getName().charAt(3))+method.getName().substring(4);
				if(filterProperty==null||!Arrays.asList(filterProperty).contains(field))
					list.add(new ExcelHead(excelSource.title(),excelSource.order(),field,excelSource.datePattern(),excelSource.numberPattern()));
			}
		}
		Collections.sort(list);
		return list;
	}
	
	private static Object getProperty(Object target,String fieldName) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException{
		String methodName="get"+Character.toUpperCase(fieldName.charAt(0))+fieldName.substring(1);
		Method method=target.getClass().getMethod(methodName);
		return method.invoke(target);
	}
}
