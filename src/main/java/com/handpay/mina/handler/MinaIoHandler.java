/*
 * @(#)MinaIoHandler.java        1.0 2012-1-6
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

package com.handpay.mina.handler;

import java.util.Map;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.handpay.mina.processor.IMinaHandlerProcessor;
import com.handpay.mina.protocol.ccb.obj.CommonErrorReq;
import com.handpay.mina.protocol.ccb.obj.CommonErrorRes;
import com.handpay.mina.server.ConsoleTextArea;

/**
 * Class description goes here.
 * 
 * @version 1.0 2012-1-6
 * @author lfjiang
 * @history
 * 
 */
public class MinaIoHandler implements IoHandler {
	private ConsoleTextArea logger = ConsoleTextArea.getInstance(this.getClass());

	private Map<String, IMinaHandlerProcessor> handlerProcessors;

	/**
	 * @return the handlerProcessors
	 */
	public Map<String, IMinaHandlerProcessor> getHandlerProcessors() {
		return handlerProcessors;
	}

	/**
	 * @param handlerProcessors
	 *            the handlerProcessors to set
	 */
	public void setHandlerProcessors(
			Map<String, IMinaHandlerProcessor> handlerProcessors) {
		this.handlerProcessors = handlerProcessors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandler#exceptionCaught(org.apache.mina
	 * .core.session.IoSession, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandler#messageReceived(org.apache.mina
	 * .core.session.IoSession, java.lang.Object)
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		if (!(message instanceof Map)){
			logger.error("request message error");
			throw new Exception("request message error");
		}
		/** 公共错误请求 */
		if (message instanceof CommonErrorReq){
			CommonErrorReq req = (CommonErrorReq)message;
			CommonErrorRes res = new CommonErrorRes();
			res.setResult(req.getErrorCode());
			res.setErrorMsg(req.getErrorMsg());
			session.write(res);
			session.close(false);
			return;
		}
//		CCBRequest req = (CCBRequest) message;
		/** 查找处理类 */
		IMinaHandlerProcessor process = this.handlerProcessors.get("SIMULATED");
		if (process == null) {
			logger.error("no process for business: SIMULATED");
			throw new Exception("no process for business:SIMULATED");
		}
		process.process(session, message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandler#messageSent(org.apache.mina.core
	 * .session.IoSession, java.lang.Object)
	 */
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandler#sessionClosed(org.apache.mina.
	 * core.session.IoSession)
	 */
	@Override
	public void sessionClosed(IoSession session) throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandler#sessionCreated(org.apache.mina
	 * .core.session.IoSession)
	 */
	@Override
	public void sessionCreated(IoSession session) throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandler#sessionIdle(org.apache.mina.core
	 * .session.IoSession, org.apache.mina.core.session.IdleStatus)
	 */
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandler#sessionOpened(org.apache.mina.
	 * core.session.IoSession)
	 */
	@Override
	public void sessionOpened(IoSession session) throws Exception {
	}

}
