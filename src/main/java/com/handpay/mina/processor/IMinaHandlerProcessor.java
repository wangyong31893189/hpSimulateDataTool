/*
 * @(#)IMinaHandlerProcessor.java        1.0 2012-1-9
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

package com.handpay.mina.processor;

import org.apache.mina.core.session.IoSession;


/**
 * minahandler的processor
 *
 * @version 	1.0 2012-1-9
 * @author		lfjiang
 * @history	
 *		
 */
public interface IMinaHandlerProcessor {

	/**
	 * 处理方法
	 * @author lfjiang  2012-1-13
	 * @param session
	 * @param o
	 * @throws ProcessorException
	 */
	public void process(IoSession session, Object o) throws ProcessException;
}
