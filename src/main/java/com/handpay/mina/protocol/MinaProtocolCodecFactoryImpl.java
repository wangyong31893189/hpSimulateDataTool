/*
 * @(#)MinaProtocolCodecFactoryImpl.java        1.0 2012-1-6
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

package com.handpay.mina.protocol;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * Class description goes here.
 *
 * @version 	1.0 2012-1-6
 * @author		lfjiang
 * @history	
 *		
 */
public class MinaProtocolCodecFactoryImpl implements ProtocolCodecFactory {
	
	private ProtocolDecoder decoder;
	
	private ProtocolEncoder encoder;

	/**
	 * @return the decoder
	 */
	public ProtocolDecoder getDecoder() {
		return decoder;
	}

	/**
	 * @param decoder the decoder to set
	 */
	public void setDecoder(ProtocolDecoder decoder) {
		this.decoder = decoder;
	}

	/**
	 * @return the encoder
	 */
	public ProtocolEncoder getEncoder() {
		return encoder;
	}

	/**
	 * @param encoder the encoder to set
	 */
	public void setEncoder(ProtocolEncoder encoder) {
		this.encoder = encoder;
	}

	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.ProtocolCodecFactory#getDecoder(org.apache.mina.core.session.IoSession)
	 */
	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.ProtocolCodecFactory#getEncoder(org.apache.mina.core.session.IoSession)
	 */
	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

}
