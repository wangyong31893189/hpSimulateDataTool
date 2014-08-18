package com.handpay.mina.processor;

import com.handpay.core.common.exception.BaseException;

public class ProcessException extends BaseException {
	
	private static final long serialVersionUID = -7339281910096541147L;
	public ProcessException(String errorCode, String errorMsg){
		super(errorCode, errorMsg);
	}
}
