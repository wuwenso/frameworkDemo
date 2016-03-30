package com.demo.util.execl.back;

import java.util.List;

import com.demo.persistence.dialect.entity.PageResult;

public interface RecordCallBack<T> {

	public List<T> getRecord(PageResult<T> page) ;

}
