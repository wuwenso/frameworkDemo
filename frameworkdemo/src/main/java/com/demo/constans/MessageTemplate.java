package com.demo.constans;

import java.util.ResourceBundle;


public class MessageTemplate {
	private static final ResourceBundle message = ResourceBundle.getBundle("conf/message");
	
	public static final String SAVE_SUCCESS = message.getString("saveSuccess");
	public static final String SAVE_FAILED = message.getString("saveFailed");

	public static final String UPDATE_SUCCESS = message.getString("updateSuccess");
	public static final String UPDATE_FAILED = message.getString("updateFailed");
	
	public static final String UPDATE_NOT_ALLOWED = message.getString("updateNotAllowed");

	public static final String DEL_SUCCESS = message.getString("delSuccess");
	public static final String DEL_FAILED = message.getString("delFailed");
	public static final String DEL_NOT_ALLOWED = message.getString("delNotAllowed");
	public static final String DEL_NONE_RECORD_STR = message.getString("delNoneRecordStr");
	
	public static final String CLOSE_ORDER_SUCCESS = message.getString("closeOrderSuccess");
	public static final String CLOSE_ORDER_FAILED = message.getString("closeOrderFailed");
	
	public static final String RELEASE_SUCCESS = message.getString("releaseSuccess");
	public static final String RELEASE_FAILED = message.getString("releaseFailed");
	
	public static final String AUDIT_SUCCESS = message.getString("auditSuccess");
	public static final String AUDIT_FAILED = message.getString("auditFailed");
	
	public static final String REJECT_SUCCESS = message.getString("rejectSuccess");
	public static final String REJECT_FAILED = message.getString("rejectFailed");
	
	public static final String CREATE_ORDER_SUCCESS = message.getString("createOrderSuccess");
	public static final String CREATE_ORDER_FAILED = message.getString("createOrderFailed");
	
	//导出提示的
	public static final String EXPORTEXCEL_SUCCESS = message.getString("exportExcelSuccess");
	public static final String EXPORTEXCEL_FAILED = message.getString("exportExcelFailed");
	
	public static final String SAVED_SUCCESS = message.getString("savedSuccess");
	public static final String SAVED_FAILED = message.getString("savedFailed");
	
	public static final String OPERATION_SUCCESS = message.getString("operationSuccess");
	public static final String OPERATION_FAILED = message.getString("operationFailed");
	
	//------------------------------错误提示模板---------------------
	public static final String TEXT_NOT_NULL = message.getString("textNotNull");
	public static final String SELECT_NOT_NULL = message.getString("selectNotNull");
	public static final String TEXT_LENGTH = message.getString("textLength");
	public static final String NUM_RANGE = message.getString("numRange");
	public static final String NUM_LENGTH = message.getString("numLength");
	public static final String NUM_FIXED_LENGTH = message.getString("numFixedLength");
	public static final String INT_RANGE = message.getString("intRange");
	public static final String FIELD_EXIST = message.getString("fieldExist");
	public static final String TEXT_ERROR = message.getString("textError");
	public static final String FIELD_FORMAT_ERROR = message.getString("fieldFormatError");
	public static final String CHAR_LENGTH = message.getString("charLength");
	public static final String CHARNUM_LENGTH = message.getString("charnumLength");
	public static final String TELPHONE = message.getString("telphone");
	public static final String PHONE = message.getString("phone");
	public static final String SYSTEMBUSY=message.getString("systemBusy");
	//有效状态
	public static final String memberPast=message.getString("memberPast").trim();
	
}
