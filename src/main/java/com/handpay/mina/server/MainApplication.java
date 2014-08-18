package com.handpay.mina.server;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApplication {
	public final static String IPREX="(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)";
	public static void main(String[] args) {
		if(args!=null&&args.length>0){
			Logger logger=LoggerFactory.getLogger(MainApplication.class);
			ApplicationContext appctx = new ClassPathXmlApplicationContext(
			"/spring/spring-application.xml");
			MinaServer minaServer=null;
			if (minaServer != null) {
				try {
					minaServer.destroy();
				} catch (Exception e) {
					logger.error("Simulate data service stop fail!",e);
					return;
				}
				logger.warn("Simulate data service before start must stop service!");
			}
			String defaultPath = Class.class.getClass().getResource("/").getPath();
			if (StringUtils.isBlank(args[0])
					||!args[0]
							.matches(IPREX)) {
				logger.error("IP address is not keeping with rule! ip:" + args[0]);
				return;
			}
			if (!StringUtils.isNumeric(args[1])) {
				logger.error("Port is not keeping with rules! port:" + args[1]);
				System.exit(0);
				return;
			}
			try {
				ReadDataConfig.getInstance()
				.initServerConfig(defaultPath);
			} catch (SimulateDataException e) {
				logger.error(e.getErrorMsg(),e);
				return;
			}
			minaServer = (MinaServer) appctx.getBean("ccbServer");
			minaServer.setIp(args[0]);
			minaServer.setPort(Integer.valueOf(args[1]));
			try {
				minaServer.init();
				logger.info("Simulate data service start success!");
			} catch (Exception e) {
				logger.error("Simulate data service start fail!",e);
				return;
			}
		}else{
			SimulateDataTool dataTool = new SimulateDataTool();
			dataTool.setVisible(true);
			dataTool.setShow(true);
		}
	}
}
