package com.handpay.mina.protocol.ccb.obj;

public class CommonErrorReq extends CCBRequest {

	/** ������� */
	protected String errorCode;
	/** �������� */
	protected String errorMsg;
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
