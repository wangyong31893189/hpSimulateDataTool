/*
 * @(#)PayAccountAction.java        1.0 2012-4-2
 *
 * Copyright (c) 2007-2012 Shanghai Handpay IT, Co., Ltd.
 * 16/F, 889 YanAn Road. W., Shanghai, China
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * Shanghai Handpay IT Co., Ltd. ("Confidential Information").  
 * You shall not disclose such Confidential Information and shall use 
 * it only in accordance with the terms of the license agreement you 
 * entered into with Handpay.
 */

package com.handpay.action;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.handpay.core.common.util.ObjectUtil;
import com.handpay.mina.server.ConsoleTextArea;
import com.handpay.util.MD5Util;

/**
 * 账户直冲订单发货
 * 
 * @version 1.0 2012-4-2
 * @author yongwang
 * @history
 * 
 */
public class PayAccountAction extends Action {
	private ConsoleTextArea logger = ConsoleTextArea.getInstance(PayAccountAction.class);
	private String MD5Key;
	private String ccbSocketServer;
	private int ccbSocketPort;
	private int timeOutSecond;
	private final String METHOD_TYPE="PAYACCOUNT";

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String merchId = request.getParameter("merchId");
		String cardId = request.getParameter("cardId");
		String orderId = request.getParameter("orderId");
		String cardNum = request.getParameter("cardNum");
		String feeStr = request.getParameter("fee");
		String gameAccount = request.getParameter("gameAccount");
		String gameNetAccount = request.getParameter("gameNetAccount");
		String gameArea = request.getParameter("gameArea");
		String gameServer = request.getParameter("gameServer");
		String sign = request.getParameter("sign");
		if (ObjectUtil.isNull(merchId) || ObjectUtil.isNull(cardId)
				|| ObjectUtil.isNull(orderId) || ObjectUtil.isNull(cardNum)
				|| ObjectUtil.isNull(feeStr) || ObjectUtil.isNull(gameAccount)|| 
				ObjectUtil.isNull(gameNetAccount)|| ObjectUtil.isNull(gameArea)
				|| ObjectUtil.isNull(gameServer)
				|| ObjectUtil.isNull(sign)) {
			logger.error("缺少必要的参数");
			return mapping.findForward("error");
		}
		Socket s = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			s = new Socket(this.ccbSocketServer, this.ccbSocketPort);
			s.setSoTimeout(this.timeOutSecond * 1000);
			StringBuffer body = new StringBuffer();
			String time = ObjectUtil.getCurrentDateStr("yyyyMMddHHmmss");
			body.append(METHOD_TYPE).append("~");
			body.append(merchId).append("~");
			body.append(cardId).append("~");
			body.append(orderId).append("~");
			body.append(cardNum).append("~");
			body.append(feeStr).append("~");
			body.append(gameAccount).append("~");
			body.append(gameNetAccount).append("~");
			body.append(gameArea).append("~");
			body.append(gameServer).append("~");
			body.append(time).append("~");
			StringBuffer encBuf = new StringBuffer();
			encBuf.append(METHOD_TYPE);
			encBuf.append(merchId);
			encBuf.append(cardId);
			encBuf.append(orderId);
			encBuf.append(cardNum);
			encBuf.append(feeStr);
			encBuf.append(gameAccount);
			encBuf.append(gameNetAccount);
			encBuf.append(gameArea);
			encBuf.append(gameServer);
			encBuf.append(time);
			encBuf.append(this.MD5Key);
			body.append(MD5Util.getMD5Str(encBuf.toString(), "GB2312"));
			out = s.getOutputStream();
			int len = body.toString().getBytes("GB2312").length;
			String lenStr = ObjectUtil.convertInt2String(len, 8);
			out.write(lenStr.getBytes("GB2312"));
			out.write(body.toString().getBytes("GB2312"));
			in = s.getInputStream();
			byte[] resLen = new byte[8];
			in.read(resLen);
			String header = new String(resLen, "GB2312");
			logger.info("header:" + header);
			int l = Integer.valueOf(header);
			byte[] bodyBytes = new byte[l];
			in.read(bodyBytes);
			String bodyStr = new String(bodyBytes, "GB2312");
			logger.info("body:" + bodyStr);
			int index = bodyStr.indexOf("~");
			if (index == -1){
				logger.error("返回报文格式不正确");
				return mapping.findForward("error");
			}
			String result = bodyStr.substring(0, index);
			if ("0".equals(result)){
				logger.info("账户直冲订单发货成功");
				return mapping.findForward("success");
			}else{
				logger.error("账户直冲订单发货失败");
				return mapping.findForward("error");
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return mapping.findForward("error");
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception ex) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (Exception ex) {
				}
			}
			if (s != null) {
				try {
					s.close();
				} catch (Exception ex) {
				}
			}
		}
	}
	
	public String getMD5Key() {
		return MD5Key;
	}

	public void setMD5Key(String mD5Key) {
		MD5Key = mD5Key;
	}

	public String getCcbSocketServer() {
		return ccbSocketServer;
	}

	public void setCcbSocketServer(String ccbSocketServer) {
		this.ccbSocketServer = ccbSocketServer;
	}

	public int getCcbSocketPort() {
		return ccbSocketPort;
	}

	public void setCcbSocketPort(int ccbSocketPort) {
		this.ccbSocketPort = ccbSocketPort;
	}

	public int getTimeOutSecond() {
		return timeOutSecond;
	}

	public void setTimeOutSecond(int timeOutSecond) {
		this.timeOutSecond = timeOutSecond;
	}

}
