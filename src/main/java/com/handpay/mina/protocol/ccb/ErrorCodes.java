package com.handpay.mina.protocol.ccb;

public class ErrorCodes {
	public static final String PACK_DFT_ERROR = "0001";
	public static final String PACK_MD5_ERROR = "1001";
	public static final String ACCOUNT_INVALID_TYPE = "2001";
	public static final String ACCOUNT_ERROR_DATA = "2002";
	public static final String ACCOUNT_LACK_OF_DATA = "2003";
	public static final String ACCOUNT_TRANSFER_FAIL = "2004";
	
	/** 此类商品已经下架*/
	public static final String QUERY_MDSE_NOT_SALE = "1002";
	/** 此类商品已售完*/
	public static final String QUERY_MDSE_NO_STOCK = "1003";  
	/**此类商品余货不足，不能满足用户的定购请求*/
	public static final String PAY_MDSE_NOT_ENOUGH = "1004";
	/**系统错误*/
	public static final String UNKNOW_EXCEP = "1005";
	/**帐号不存在*/
	public static final String PAY_ACCOUNT_NOT_EXSIST = "1006";
	/**此商品不存在或未对其开放*/
	public static final String PAY_MDSE_NOT_EXSIST = "1007";
	/**订单信息不存在*/
	public static final String PAY_ORDER_NOT_EXSIST = "1008";
	/**帐号余额不足*/
	public static final String PAY_MONEY_NOT_ENOUGH = "1009";
	/**CP方销售价格过低，低于成本价格*/
	public static final String LOW_SALE_PRICE = "1010";
	/**订单请求IP来源不合法*/
	public static final String ERROR_IP = "1011";
	
	/**订单一存在*/
	public static final String PAY_ORDER_EXSIST = "1012";
	
	
	/**订单失败，扣款成功（因库存不足或其他原因出卡失败）*/
	public static final String PAY_ORDER_FAIL = "9000";  
}
