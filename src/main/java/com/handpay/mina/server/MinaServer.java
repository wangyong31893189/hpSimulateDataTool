/*
 * @(#)MinaServer.java        1.0 2012-1-6
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

package com.handpay.mina.server;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * Mina服务
 * 
 * @version 1.0 2012-1-6
 * @author lfjiang
 * @history
 * 
 */
public class MinaServer {

	private ConsoleTextArea logger = ConsoleTextArea.getInstance(this
			.getClass());

	/**
	 * 默认监听ip
	 */
	private static final String DEFAULT_IP = "127.0.0.1";

	/**
	 * 默认监听端口
	 */
	private static final int DEFAULT_PORT = 6002;

	private Integer port = DEFAULT_PORT;

	private String ip = DEFAULT_IP;

	/**
	 * 协议解析，组包的工厂类
	 */
	private ProtocolCodecFactory protocolCodecFactory;

	/**
	 * IO处理类
	 */
	private IoHandler ioHandler;

	/**
	 * @return the port
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip
	 *            the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the protocolCodecFactory
	 */
	public ProtocolCodecFactory getProtocolCodecFactory() {
		return protocolCodecFactory;
	}

	/**
	 * @param protocolCodecFactory
	 *            the protocolCodecFactory to set
	 */
	public void setProtocolCodecFactory(
			ProtocolCodecFactory protocolCodecFactory) {
		this.protocolCodecFactory = protocolCodecFactory;
	}

	/**
	 * @return the ioHandler
	 */
	public IoHandler getIoHandler() {
		return ioHandler;
	}

	/**
	 * @param ioHandler
	 *            the ioHandler to set
	 */
	public void setIoHandler(IoHandler ioHandler) {
		this.ioHandler = ioHandler;
	}

	private SocketAcceptor acceptor;

	public void init() throws Exception {
		if (acceptor != null) {
			acceptor.unbind();
			acceptor = null;
		}
		acceptor = new NioSocketAcceptor(Runtime.getRuntime()
				.availableProcessors() + 1);
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		chain.addLast("last", new ProtocolCodecFilter(protocolCodecFactory));

		acceptor.setHandler(ioHandler);
		acceptor.bind(new InetSocketAddress(ip, port));
		logger.info("-------------mina server is start------------");

	}

	public void destroy() throws Exception {
		if (acceptor != null) {
			acceptor.unbind();
			acceptor = null;
		}
		logger.info("-------------mina server is unbinded------------");
	}

	@Override
	public String toString() {
		return "MinaServer [logger=" + logger + ", port=" + port + ", ip=" + ip
				+ ", protocolCodecFactory=" + protocolCodecFactory
				+ ", ioHandler=" + ioHandler + ", acceptor=" + acceptor + "]";
	}

	// public static void main(String[] args) {
	// MinaServer minaServer=new MinaServer();
	// minaServer.setIp("127.0.0.1");
	// minaServer.setPort(8088);
	// IoHandler handler= new MinaIoHandler();
	// minaServer.setIoHandler(handler);
	// try {
	// minaServer.init();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }

	// ApplicationContext appctx =new
	// ClassPathXmlApplicationContext("/spring/spring-application.xml");
	// MinaServer minaServer = (MinaServer) appctx.getBean("ccbServer");
	//		
	// try {
	// minaServer.init();
	// } catch (Exception e) {
	// //logger.error("BOCMinaServer mina服务启动失败......minaServer="+minaServer,e);
	// e.printStackTrace();
	// }
	// }
}
