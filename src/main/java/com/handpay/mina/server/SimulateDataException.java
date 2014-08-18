package com.handpay.mina.server;

import com.handpay.core.common.exception.BaseException;

public class SimulateDataException extends BaseException {
	public static final String PARAM_ERROR = "001";// ��������
	public static final String CONFIG_ERROR = "001";// ���ô���

	public SimulateDataException(String errorCode, String errorMsg) {
		super(errorCode, errorMsg);
	}
}
