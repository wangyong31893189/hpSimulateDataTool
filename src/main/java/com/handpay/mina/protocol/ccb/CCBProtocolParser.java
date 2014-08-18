package com.handpay.mina.protocol.ccb;

import java.util.HashMap;
import java.util.Map;

import com.handpay.core.common.util.ObjectUtil;
import com.handpay.mina.server.ConsoleTextArea;

public class CCBProtocolParser {
	private ConsoleTextArea logger = ConsoleTextArea.getInstance(this.getClass());
	/** Э��ʹ�õ�md5��Կ */
	private String MD5Key;// abcdefgh12345678ccbttf21

	public String getMD5Key() {
		return MD5Key;
	}

	public void setMD5Key(String mD5Key) {
		MD5Key = mD5Key;
	}

	/**
	 * 3.2.11Q��ֱ��ԤУ�� ����
	 * @param elements
	 * @return
	 */
	/*private CCBRequest parsePreCheck(List<String> elements) {
		*//** �ֶθ�ʽ���� *//*
		if (elements.size() != 7) {
			logger.error("pay account has 6 elements");
			CommonErrorReq req = new CommonErrorReq();
			req.setMethodType(elements.get(0));
			req.setErrorCode(ErrorCodes.PACK_DFT_ERROR);
			req.setErrorMsg("�������ݸ�ʽ����");
			return req;
		}
		String methodType = elements.get(0);
		String merchId = elements.get(1);
		String qq = elements.get(2);
		String num = elements.get(3);
		String fee = elements.get(4);
		String time = elements.get(5);
		String sign = elements.get(6);	
		
		boolean nb=false;
		try {
			int n = Integer.valueOf(num);
			if(n<0){
				nb=true;
			}
			if(num.indexOf(".")!=-1){
				nb=true;
			}
		} catch (Exception e) {
			logger.error("num ������ʽ����num:"+num,e);
			nb=true;
		}
		boolean fb=false;
		try {
			int ff = Integer.valueOf(fee);
			if(ff<=0||ff<50||ff>500000){//50-500000
				fb=true;
			}
			if(fee.indexOf(".")!=-1){
				fb=true;
			}
		} catch (Exception e) {
			logger.error("fee ������ʽ����fee:"+fee,e);
			fb=true;
		}
		logger.debug("nb="+nb+",fb="+fb);
		if (ObjectUtil.isNull(elements.get(0))
				|| ObjectUtil.isNull(elements.get(1))
				|| ObjectUtil.isNull(elements.get(2))
				|| ObjectUtil.isNull(elements.get(3))
				|| ObjectUtil.isNull(elements.get(4))
				|| ObjectUtil.isNull(elements.get(5))
				|| ObjectUtil.isNull(elements.get(6))||nb||fb) {
			logger.error("element is null");
			CommonErrorReq req = new CommonErrorReq();
			req.setMethodType(elements.get(0));
			req.setErrorCode(ErrorCodes.PACK_DFT_ERROR);
			req.setErrorMsg("�������ݸ�ʽ����");
			return req;
		}

		String encBody = methodType + merchId + qq + num + fee+time + this.MD5Key;
		String newMD5 = MD5Util.getMD5Str(encBody, "GB2312");
		if (!newMD5.toUpperCase().equals(sign.toUpperCase())) {
			CommonErrorReq req = new CommonErrorReq();
			req.setMethodType(elements.get(0));
			req.setErrorCode(ErrorCodes.PACK_MD5_ERROR);
			req.setErrorMsg("��CP�ṩ�Ĺ�Կ��CP���ýӿ�ʱ���ݵĲ�����ǩ��������ǩʱ��������");
			return req;
		}
		
		PreCheckReq req = new PreCheckReq();
		req.setMerId(merchId);
		req.setMethodType(methodType);
		req.setNum(num);//����
		req.setQq(qq);
		req.setFee(Integer.valueOf(fee));//�ܼ۸�
		req.setSign(sign);
		req.setTime(time);
		return req;
	}
	*/
	/**
	 * �������URI�Ͳ���
	 */
	public Map<String,String> splitBody(String body, String splitChar){
		Map<String,String> elements = new HashMap<String,String>();
		int i=body.indexOf("?");
		String uri="";
		if(i==-1){
			uri = body;
		}else{
			uri = body.substring(0,i);
			String bodyStr = body.substring(body.indexOf("?")+1);
			while (!ObjectUtil.isNull(bodyStr)){
				int index = bodyStr.indexOf(splitChar);
				if (index == -1){
					String[] bodyStrs=bodyStr.split("=");
					if(bodyStrs!=null&&bodyStrs.length>0){
						elements.put(bodyStrs[0],bodyStrs[1]);
					}
					break;
				}
				String sub = bodyStr.substring(0, index);
				String[] bodyStrs=sub.split("=");
				if(bodyStrs!=null&&bodyStrs.length>0){
					elements.put(bodyStrs[0],bodyStrs[1]);
				}
				bodyStr = bodyStr.substring(index + 1);
			}
		}
		elements.put("uri", uri);
		
		return elements;
	}
	
	

	public Map<String,String> parse(String bodyStr) {
		/**
		 * ���Э���ַ���
		 */
		Map<String,String> elements = this.splitBody(bodyStr, "&");
		if (elements.isEmpty()) {
			logger.error("not parse operate type");
			return null;
		}

//		String methodType = elements.get(0);
//		if (!this.isValidMethod(methodType)) {
//			logger.error("not support method:" + methodType);
//			return null;
//		}
		return elements;
	}
	
}
