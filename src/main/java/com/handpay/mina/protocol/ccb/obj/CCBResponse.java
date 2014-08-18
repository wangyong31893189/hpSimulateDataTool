package com.handpay.mina.protocol.ccb.obj;

public abstract class CCBResponse {

	/** ������ */
	protected String result;
	/** ������ʾ */
	protected String errorMsg;
	/** ʱ��� */
	protected String time;
	/** ǩ�� */
	protected String sign;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
}
