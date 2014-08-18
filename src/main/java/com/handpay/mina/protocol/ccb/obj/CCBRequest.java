package com.handpay.mina.protocol.ccb.obj;

public abstract class CCBRequest {

	/** 交易请求类型 */
	protected String methodType;
	/** 时间戳 */
	protected String time;
	/** 签名 */
	protected String sign;

	public String getMethodType() {
		return methodType;
	}
	public void setMethodType(String methodType) {
		this.methodType = methodType;
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
