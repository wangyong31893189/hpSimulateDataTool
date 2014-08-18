package com.handpay.mina.protocol.ccb.obj;

public abstract class CCBResponse {

	/** 返回码 */
	protected String result;
	/** 错误提示 */
	protected String errorMsg;
	/** 时间戳 */
	protected String time;
	/** 签名 */
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
