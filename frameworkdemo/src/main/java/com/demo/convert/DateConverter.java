package com.demo.convert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<String, Date> {
	
    private static final DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat TIMEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Date convert(String source) {
		
		try {
			if(source.contains(":")) {
				return TIMEFORMAT.parse(source);
			} else {
				return DATEFORMAT.parse(source);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
