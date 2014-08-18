package com.handpay.mina.processor.ccb;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.core.session.IoSession;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.handpay.mina.processor.IMinaHandlerProcessor;
import com.handpay.mina.processor.ProcessException;
import com.handpay.mina.server.ConsoleTextArea;
import com.handpay.mina.server.ReadDataConfig;
import com.handpay.mina.server.SimulateDataConfig;

public class SimulatedDataProcessor implements IMinaHandlerProcessor {
	private ConsoleTextArea logger = ConsoleTextArea.getInstance(this
			.getClass());

	@Override
	public void process(IoSession session, Object message)
			throws ProcessException {
		StringBuffer responsStr = new StringBuffer();
		try {
			if (message instanceof Map) {
				Map<String, String> requestMap = (Map<String, String>) message;
				Set<String> keys = requestMap.keySet();
				ReadDataConfig readDataConfig = ReadDataConfig.getInstance();
				Map<String, SimulateDataConfig> configs = readDataConfig
						.getConfigs();
				String requestUri = requestMap.get("uri");
				SimulateDataConfig config = configs.get(requestUri);
				if (config == null) {
					responsStr.append("{\"error\":\"can not find URI:"
							+ requestUri + " the config of request info!\"}");
				} else {
					JsonObject request = (JsonObject) config.getRequest();// 配置的请求参数
					logger.info("the config of request params:" + request);

					// 判断配置的请求参数是否和输入的参数一致
					JsonObject params = request.getAsJsonObject("params");
					if (params!=null&&!params.isJsonNull()) {
						try {
							for (Iterator iterator = params.entrySet()
									.iterator(); iterator.hasNext();) {
								Entry<String, JsonElement> entry = (Entry<String, JsonElement>) iterator
										.next();
								String key = entry.getKey();
								if (StringUtils.isBlank(requestMap.get(key))) {
									logger.error("config params key:"+ key+ " not keeping with request of rules,please check it!");
									responsStr = new StringBuffer();
									responsStr.append("{\"error\":\"config params key:"+ key+ " not keeping with request of rules,please check it!\"}");
									session.write(responsStr);
									session.close(false);
									return;
								}
							}
						} catch (Exception e) {
							logger.error("read config param error!", e);
							writeErrorPage(e, session);
							return;
						}
					}
					JsonElement response = config.getResponse();// 配置的响应参数
					String str=response.isJsonPrimitive()?response.getAsString():response.toString();//响应的字符串
					JsonPrimitive type = request.getAsJsonPrimitive("type");
					if (type != null && !type.isJsonNull()) {
						String typeStr = type.getAsString();
						if (!"json".equalsIgnoreCase(typeStr)) {
							StringBuffer sb = new StringBuffer();
							sb.append("HTTP/1.1 \n");
							sb.append("Content-Type:text/html;charset=utf-8 \n");
//							sb.append("Connection: keep-alive \n");
//							sb.append("Content-Length:"+(str.getBytes().length+7)+ " \n");
							sb.append("\r\n");
							responsStr.append(sb);
//							sb.append("<!Doctype html><html><head><title></title></head><body>");
							responsStr.append(str);
							logger.info(responsStr.toString());
//							sb.append("</body></html>");
						}else{
							responsStr.append(str);
						}
					}else{
						responsStr.append(str);
					}
					logger.info("the response params of config is :"+ response);
				}
				if (keys.contains("hpjsonpcall")) {
					logger.info("this request is jsonp request!jsonp param is hpjsonpcall");
					String jsonpParam = requestMap.get("hpjsonpcall");
					String str = responsStr.toString();
					responsStr = new StringBuffer();
					responsStr.append(jsonpParam + "(" + str + ")");
				} else if (keys.contains("jsonpcallback")) {
					logger.info("this request is jsonp request!jsonp param is jsonpcallback");
					String jsonpParam = requestMap.get("jsonpcallback");
					String str = responsStr.toString();
					responsStr = new StringBuffer();
					responsStr.append(jsonpParam + "(" + str + ")");
				} else {
					logger.info("this request is normal request!");
				}
				session.write(responsStr);
				session.close(false);
				return;
			}
		} catch (Exception e) {
			logger.error("read config param error!", e);
			writeErrorPage(e, session);
		}

	}

	void writeErrorPage(Exception e, IoSession session) {
		String notfound = "<html><head><title>Not Found</title></head><body><h1>Error 500 ["
				+ e.getMessage() + "]</h1></body></html>";
		StringBuffer sb = new StringBuffer();
		sb.append("HTTP/1.1 500 Internal Server Error\n");
		sb.append("Content_Type:text/html\n");
		sb.append("Content-Length:" + notfound.getBytes().length + "\n");
		sb.append("\r\n\r\n");
		sb.append(notfound);
		session.write(sb);
		session.close(false);
	}
}
