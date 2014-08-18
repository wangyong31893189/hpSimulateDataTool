package com.handpay.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.handpay.mina.server.ConsoleTextArea;
import com.handpay.mina.server.MinaServer;

/**
 * ����nima ����
 * @author lfsheng
 *
 */
public class MinaServerServlet extends HttpServlet{
	private ConsoleTextArea logger = ConsoleTextArea.getInstance(this.getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = -6747896506226119782L;

	public void service(ServletRequest arg0, ServletResponse arg1)
			throws ServletException, IOException {
		super.service(arg0, arg1);
	}

	public void destroy() {
		super.destroy();
		logger.info("......��unbind socket����.......");
		ApplicationContext appctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		logger.debug("appctx="+appctx);
		MinaServer minaServer = (MinaServer) appctx.getBean("ccbServer");
		logger.debug("minaServer="+minaServer);
		try {
			minaServer.destroy();
		} catch (Exception e) {
			logger.error("mina����unbindʧ��......",e);
		}
	}

	@Override
	public void init() throws ServletException {
		super.init();
		
		logger.info(".......������socket����.......");
		ApplicationContext appctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		logger.debug("appctx="+appctx);
		MinaServer minaServer = (MinaServer) appctx.getBean("ccbServer");
		logger.debug("minaServer="+minaServer);
		
		try {
			minaServer.init();
		} catch (Exception e) {
			logger.error("mina��������ʧ��......minaServer="+minaServer,e);
		}
		
		
		
		
	}
	

}
