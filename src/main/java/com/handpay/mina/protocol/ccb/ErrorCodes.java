package com.handpay.mina.protocol.ccb;

public class ErrorCodes {
	public static final String PACK_DFT_ERROR = "0001";
	public static final String PACK_MD5_ERROR = "1001";
	public static final String ACCOUNT_INVALID_TYPE = "2001";
	public static final String ACCOUNT_ERROR_DATA = "2002";
	public static final String ACCOUNT_LACK_OF_DATA = "2003";
	public static final String ACCOUNT_TRANSFER_FAIL = "2004";
	
	/** ������Ʒ�Ѿ��¼�*/
	public static final String QUERY_MDSE_NOT_SALE = "1002";
	/** ������Ʒ������*/
	public static final String QUERY_MDSE_NO_STOCK = "1003";  
	/**������Ʒ������㣬���������û��Ķ�������*/
	public static final String PAY_MDSE_NOT_ENOUGH = "1004";
	/**ϵͳ����*/
	public static final String UNKNOW_EXCEP = "1005";
	/**�ʺŲ�����*/
	public static final String PAY_ACCOUNT_NOT_EXSIST = "1006";
	/**����Ʒ�����ڻ�δ���俪��*/
	public static final String PAY_MDSE_NOT_EXSIST = "1007";
	/**������Ϣ������*/
	public static final String PAY_ORDER_NOT_EXSIST = "1008";
	/**�ʺ�����*/
	public static final String PAY_MONEY_NOT_ENOUGH = "1009";
	/**CP�����ۼ۸���ͣ����ڳɱ��۸�*/
	public static final String LOW_SALE_PRICE = "1010";
	/**��������IP��Դ���Ϸ�*/
	public static final String ERROR_IP = "1011";
	
	/**����һ����*/
	public static final String PAY_ORDER_EXSIST = "1012";
	
	
	/**����ʧ�ܣ��ۿ�ɹ������治�������ԭ�����ʧ�ܣ�*/
	public static final String PAY_ORDER_FAIL = "9000";  
}
