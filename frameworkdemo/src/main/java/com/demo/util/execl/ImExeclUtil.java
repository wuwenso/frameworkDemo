package com.demo.util.execl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ReflectionUtils;

import com.demo.util.execl.annotation.ExcelSource;
import com.demo.util.execl.back.ExeclCallBackAbstract;
import com.demo.util.execl.back.ImCallBack;

public class ImExeclUtil {
	//时间格式
	private static SimpleDateFormat dateFormat=new SimpleDateFormat();
		
	private static final  String filPath="/static/emp";
	/**
	 * 导入
	 * @param request
	 * @param importFile导入的文件
	 * @param clz 行数据封装 对象
	 * @param callback 回调
	 * @return
	 * @throws Exception 
	 */
	public static <T> ExeclCallBackAbstract<T> exportExel(HttpServletRequest request,File importFile,Class<T> clz,ExeclCallBackAbstract<T> callback) throws Exception {
		try {
			List<ExcelHead> heads=parseExcelHead(clz,null);
			Workbook book=convertBook(importFile);
			
			Sheet sheet = book.getSheetAt(0);
			Row oneRow=sheet.getRow(0);
			//标题验证
			if(!headValidate(oneRow, heads)){
				return callback;
			}
			/*
			 * 获取行数----调preValidate验证
			 */
			int sheetRows = sheet.getPhysicalNumberOfRows();
			ExeclInfo filedata=new ExeclInfo();
			filedata.setTotalRows(sheetRows);
			if(!callback.preValidate(filedata)){
				return callback;
			}
		
			List<T> dataList = readExecl(book,sheet,heads,clz,callback);
			if(sheetRows-1!=dataList.size()){
				//错误保存execl
				setErrorMsg(book,oneRow,heads.size(),"错误信息");
				callback.error(saveExecl(request,book,importFile.getName()));
			}else{
				callback.success(dataList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return callback;
	}
	/**
	 * 读取execl
	 * @param <T>
	 * @return
	 * @throws ParseException 
	 * @throws NoSuchFieldException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	private static <T> List<T> readExecl(Workbook book,Sheet sheet,List<ExcelHead> heads,Class<T> clz,ImCallBack<T> callback) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, ParseException{
		List<T> dataList=new ArrayList<T>();
		int sheetRows = sheet.getPhysicalNumberOfRows();
		// 记录每列的数据，判断每列里面是否有重复数据
		for (int i = 1; i < sheetRows; i++){
			Row row = sheet.getRow(i);
			int sheetCol = row.getPhysicalNumberOfCells();
			if (heads.size() != sheetCol){
				setErrorMsg(book,row,heads.size(),"总列数应为" + heads.size() + "，请重新选择上传！");
			}else if (row != null && sheetCol != 0){
				//读取行数据装配成对象
				T t=rowsValue(row,clz,heads);
				String val=callback.cellValidate(dataList,t,i);
				if(StringUtils.isNotBlank(val)){
					setErrorMsg(book,row,heads.size(),val);
				}
				dataList.add(t);
			}
		}
		return dataList;
	}
	/**
	 * 设置错误提示
	 * @param book
	 * @param row
	 * @param cellIndex
	 * @param text
	 */
	private static void setErrorMsg(Workbook book,Row row,int cellIndex,String text){
		//红色字体
		CellStyle style = book.createCellStyle();
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		//设置内容
		Cell cell=row.createCell(cellIndex);
		cell.setCellStyle(style);
		cell.setCellValue(text);
	}
	/**
	 *保存
	 * @param response
	 * @param book
	 * @param fileName
	 * @throws IOException
	 */
	private static String saveExecl(HttpServletRequest request,Workbook book,String fileName) throws IOException{
		String path=request.getRealPath(filPath);
		File file=new File(path);
		if  (!file.exists()  && !file.isDirectory()){
			file .mkdir();
		}
		FileOutputStream output=null;
		try {
			output = new FileOutputStream(path+"/"+fileName);
			book.write(output);
		}catch (IOException e) {
			e.printStackTrace();
			throw new IOException(e);
		}finally{
			try {
				output.flush();
				if(null!=output){
					//关闭流
					output.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return filPath+"/"+fileName;
	}
	/**
	 * 头部标题验证
	 * @param row
	 * @param heads
	 * @return
	 */
	private static boolean headValidate(Row row,List<ExcelHead> heads){
		int sheetCol =row.getPhysicalNumberOfCells();
		if(sheetCol!=heads.size()){
			return false;
		}
		for(int i=0;i<heads.size();i++){
			Cell cell = row.getCell(i);
			if(null!=cell){
				ExcelHead head=heads.get(i);
				if(!cell.getStringCellValue().trim().toLowerCase().equals(head.getTitle().toLowerCase())){
					return false;
				}
			}
		}
		return true;
	} 
	/**
	 * 转换workbook
	 * @return
	 * @throws IOException 
	 */
	private static Workbook convertBook(File file) throws IOException{
		InputStream is = null;
		Workbook book = null;
		try {
			is = new FileInputStream(file);
			if (file.getName().endsWith(".xlsx")){
				book = new XSSFWorkbook(is);
			}else{
				book = new HSSFWorkbook(is);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException(e);
		}finally{
			try{
				if(null!=is){
					is.close();
				}
			}catch (IOException e){
				e.printStackTrace();
			}
		}
		return book;
	}
	/**
	 *  获取该行数据
	 * @param row
	 * @param count
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 * @throws ParseException 
	 * @throws NoSuchFieldException 
	 */
	private static <T> T rowsValue(Row row,Class<T> clz,List<ExcelHead> heads) throws InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, ParseException{
		T t=clz.newInstance();
		for(int i=0;i<heads.size();i++){
			Cell cell = row.getCell(i);
			if (cell != null){
				Object val=null;
				switch (cell.getCellType()){
					case Cell.CELL_TYPE_NUMERIC:
						val = cell.getNumericCellValue();
						break;
					case Cell.CELL_TYPE_STRING:
						val = cell.getStringCellValue();
						break;
					default:
						val = cell.getStringCellValue();
						break;
				}
				ExcelHead head=heads.get(i);
				//调用set
				setProperty(t,head,val);
			}
		}
		return t;
	}
	/**
	 * 获取类方法上的注解
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
	/**
	 * 调用get方法
	 * @param target
	 * @param fieldName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	private static Object getProperty(Object target,String fieldName) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException{
		String methodName="get"+Character.toUpperCase(fieldName.charAt(0))+fieldName.substring(1);
		Method method=target.getClass().getMethod(methodName);
		return method.invoke(target);
	}
	/**
	 * 调用set方法
	 * @param target
	 * @param head
	 * @param value
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws NoSuchFieldException
	 * @throws ParseException
	 */
	private static void setProperty(Object target,ExcelHead head,Object value) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, NoSuchFieldException, ParseException{
		String methodName="set"+Character.toUpperCase(head.getPropertyName().charAt(0))+head.getPropertyName().substring(1);
		
		Class clz=(Class) target.getClass().getDeclaredField(head.getPropertyName()).getGenericType();
		Method method=target.getClass().getMethod(methodName,clz);
		
		if(null!=value){
			String values=value.toString();
			if(clz == Date.class){
				//日期转换格式
				String dataPattern=head.getDatePattern();
				dateFormat.applyPattern(dataPattern);
				method.invoke(target,dateFormat.parse(values));
			}else if(clz==Double.class){
				method.invoke(target,Double.valueOf(values));
 			}else if(clz==Integer.class){
 				method.invoke(target,Integer.valueOf(values));
 			}else{
 				method.invoke(target,values);
 			}
		}else{
			method.invoke(target,null);
		}
	}
	
}
