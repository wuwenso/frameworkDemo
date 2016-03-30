package com.demo.util.execl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExcelSource {
	String title();
	int order() default Integer.MAX_VALUE;
	String datePattern() default "yyyy-MM-dd HH:mm:ss";
	String numberPattern()  default "";
}
