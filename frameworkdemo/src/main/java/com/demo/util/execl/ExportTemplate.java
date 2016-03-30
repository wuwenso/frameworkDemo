package com.demo.util.execl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.demo.persistence.dialect.entity.PageResult;
import com.demo.util.execl.back.RecordCallBack;

public class ExportTemplate {
	public static <T> InputStream exportExel(Class<T> clz,String[] filterProperty,String sheetName,String fileName,RecordCallBack<T> recordCallBack) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(out);
		zos.setEncoding("gbk");
		//String timestamp=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		try {
			PageResult<T> page=new PageResult();
			page.setCurrentPageNo(1);
			page.setPageSize(60000);
			int i=1;
			do{
				String excelFileName=fileName/*+"-"+timestamp*/;
				List<T> list=recordCallBack.getRecord(page);
				if (page.getTotalPage() <= 1) {
					excelFileName += ".xls";
				} else {
					excelFileName +=  "-" + (i++) + ".xls";
				}
				ZipEntry zentry = new ZipEntry(excelFileName);
				zos.putNextEntry(zentry);
				ExcelUtil.createExcelOutput(zos, sheetName, clz,filterProperty, list);
				
				page.setCurrentPageNo(page.getCurrentPageNo()+1);
			}while(page.getCurrentPageNo()<=page.getTotalPage());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (zos != null) {
					zos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new ByteArrayInputStream(out.toByteArray());
	}
	public static <T> InputStream exportExel(Class<T> clz,String sheetName,String fileName,RecordCallBack<T> recordCallBack) {
		return exportExel(clz,null,sheetName,fileName,recordCallBack);
	}
}
