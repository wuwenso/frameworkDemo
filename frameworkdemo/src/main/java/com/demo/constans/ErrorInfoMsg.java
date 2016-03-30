package com.demo.constans;

import java.text.MessageFormat;


public class ErrorInfoMsg {
	// -------------------------------登录----------------------
	private static final String ACCOUN = "用户名";
	public static final String ACCOUNT_NOT_NULL = message(MessageTemplate.TEXT_NOT_NULL, ACCOUN);
	public static final String ACCOUNT_ERROR = message(MessageTemplate.TEXT_ERROR, ACCOUN);

	private static final String PASSWORD = "密码";
	public static final String PASSWORD_NOT_NULL = message(MessageTemplate.TEXT_NOT_NULL, PASSWORD);
	public static final String PASSWORD_ERROR = message(MessageTemplate.TEXT_ERROR, PASSWORD);

	public static final String ACCOUNT_PASSWORD_ERROR = message(MessageTemplate.TEXT_ERROR, ACCOUN + "或"
			+ PASSWORD);

	private static final String CODE = "验证码";
	public static final String CODE_NOT_NULL = message(MessageTemplate.TEXT_NOT_NULL, CODE);
	public static final String CODE_ERROR = message(MessageTemplate.TEXT_ERROR, CODE);
	public static final String CODE_PAST_DUE = "验证码过期,请重新输入";

	/*-----------------------修改密码--------------------------------*/
	private static final String OLD_PASSWORD = "原密码";
	public static final String OLD_PASSWORD_NOT_NULL = message(MessageTemplate.TEXT_NOT_NULL, OLD_PASSWORD);
	public static final String OLD_PASSWORD_ERROR = message(MessageTemplate.TEXT_ERROR, OLD_PASSWORD);

	private static final String NEW_PASSWORD = "新密码";
	public static final String NEW_PASSWORD_NOT_NULL = message(MessageTemplate.TEXT_NOT_NULL, NEW_PASSWORD);
	public static final String PWD_FORMAT_ERROR = message(MessageTemplate.NUM_RANGE, NEW_PASSWORD, 6, 16);

	private static final String CONFIRM_NEW_PASSWORD = "确认新密码";
	public static final String CONFIRM_NEW_PASSWORD_NOT_NULL = message(MessageTemplate.TEXT_NOT_NULL,
			CONFIRM_NEW_PASSWORD);
	public static final String CONFIRM_NEW_PASSWORD_ERROR = "新密码应与新密码确认相同，请重新输入！";

	/*-----------------------手机号码验证--------------------------------*/
	private static final String PHONE = "手机号码";
	public static final String PHONE_NOT_NULL = message(MessageTemplate.TEXT_NOT_NULL, PHONE);
	public static final String PHONE_ERROR = "手机号码应为以1开头的11位数字！";
	public static final String CREATEVERIFI_ERROR = "验证码生成失败";

	public static final String INTERFACE_SYNCH_ERROR = "接口同步错误！";

	public static String message(String pattern, Object ... arguments) {
		return MessageFormat.format(pattern, arguments);
	}
}
