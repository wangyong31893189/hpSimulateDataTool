package com.handpay.mina.server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class ReadDataConfig {
	private ConsoleTextArea logger = ConsoleTextArea.getInstance(this
			.getClass());

	private static ReadDataConfig tool;

	private Map<String, SimulateDataConfig> configs;

	private ReadDataConfig() {

	}

	public static ReadDataConfig getInstance() {
		if (tool == null) {
			tool = new ReadDataConfig();
		}
		return tool;
	}

	public void initServerConfig(String folderPath) throws SimulateDataException {
		File file=new File(folderPath);
		if(!file.isDirectory()){
			logger.error("The config path is not the folder path! folder path is:"+folderPath);
		}
		File[] files=file.listFiles();
		configs=new HashMap<String,SimulateDataConfig>();
		for (File confFile : files) {
			if(confFile.isDirectory()){
				continue;
			}
			String fileName=confFile.getName();
			int index=fileName.lastIndexOf(".json");
			if(index==-1){
				continue;
			}
			if(!".json".equalsIgnoreCase(fileName.substring(index))){
				continue;
			}
			try {
				String jsonConfig=FileUtils.readFileToString(confFile,"utf-8");
				JsonParser jsonParser=new JsonParser();
				JsonElement element=jsonParser.parse(jsonConfig);
				JsonObject jsonObject=element.getAsJsonObject();
				JsonObject request=jsonObject.getAsJsonObject("request");
				if(request!=null&&request.isJsonNull()){
					logger.error("request params config error, config file is :"+fileName);
					throw new SimulateDataException(SimulateDataException.CONFIG_ERROR, "request params config error, config file is :"+fileName);
				}
				JsonPrimitive uri=request.getAsJsonPrimitive("uri");//请求地址
				if(uri==null||uri.isJsonNull()){
					logger.error("request uri param config is empty ,config file is :"+fileName);
					throw new SimulateDataException(SimulateDataException.CONFIG_ERROR, "request uri param config is empty ,config file is :"+fileName);
				}
				JsonPrimitive type=request.getAsJsonPrimitive("type");//请求类型	
				if(type!=null&&type.isJsonNull()){
					logger.error("request type param config is empty ,config file is :"+fileName);
					throw new SimulateDataException(SimulateDataException.CONFIG_ERROR, "request type param config is empty ,config file is :"+fileName);
				}
//				JsonObject data=response.getAsJsonObject("data");
//				if(data.isJsonNull()){
//					logger.error("response参数data配置错误，配置文件为："+fileName);
//					throw new SimulateDataException(SimulateDataException.CONFIG_ERROR, "response参数data配置错误，配置文件为："+fileName);
//				}
				SimulateDataConfig config=new SimulateDataConfig();
				String typeStr="html";
				if(type!=null){
					typeStr=type.getAsString();
				}
				if("json".equalsIgnoreCase(typeStr)){//当请求类型为json的时候   status和msg是必须要定义的
					JsonObject response=jsonObject.getAsJsonObject("response");
					if(response!=null&&response.isJsonNull()){
						logger.error("response param config error ,config file is : "+fileName);
						throw new SimulateDataException(SimulateDataException.CONFIG_ERROR, "response param config error ,config file is : "+fileName);
					}
					JsonPrimitive status=response.getAsJsonPrimitive("status");
					if(status!=null&&status.isJsonNull()){
						logger.error("data param status config error  ,config file is :"+fileName);
						throw new SimulateDataException(SimulateDataException.CONFIG_ERROR, "data param status config error,config file is :"+fileName);
					}
					JsonPrimitive msg=response.getAsJsonPrimitive("msg");
					if(msg!=null&&msg.isJsonNull()){
						logger.error("data param msgconfig error ,config file is :"+fileName);
						throw new SimulateDataException(SimulateDataException.CONFIG_ERROR, "data param msg config error ,config file is :"+fileName);
					}
					config.setResponse(response);
					logger.info("read the config file content is : uri:"+uri+",request:"+request+",response:"+response);
				}else if("text".equalsIgnoreCase(typeStr)){
					JsonPrimitive response=jsonObject.getAsJsonPrimitive("response");
					if(response!=null&&response.isJsonNull()){
						logger.error("response param config error ,config file is : "+fileName);
						throw new SimulateDataException(SimulateDataException.CONFIG_ERROR, "response param config error ,config file is : "+fileName);
					}
					String res=response.getAsString();
					res=html(res);
					response=new JsonPrimitive(res);
					config.setResponse(response);
					logger.info("read the config file content is : uri:"+uri+",request:"+request+",response:"+response);
				}else if("html".equalsIgnoreCase(typeStr)){
					JsonPrimitive response=jsonObject.getAsJsonPrimitive("response");
					if(response!=null&&response.isJsonNull()){
						logger.error("response param config error ,config file is : "+fileName);
						throw new SimulateDataException(SimulateDataException.CONFIG_ERROR, "response param config error ,config file is : "+fileName);
					}
					config.setResponse(response);
					logger.info("read the config file content is : uri:"+uri+",request:"+request+",response:"+response);
				}else{
					JsonPrimitive response=jsonObject.getAsJsonPrimitive("response");
					if(response.isJsonNull()){
						logger.error("response param config error ,config file is : "+fileName);
						throw new SimulateDataException(SimulateDataException.CONFIG_ERROR, "response param config error ,config file is : "+fileName);
					}
					config.setResponse(response);
					logger.info("read the config file content is : uri:"+uri+",request:"+request+",response:"+response);
				}
				config.setRequest(request);
				configs.put(uri.getAsString(),config);
			} catch (IOException e) {
				logger.error("config file read error!config file is : "+fileName, e);
				throw new SimulateDataException(SimulateDataException.CONFIG_ERROR, "config file read error!config file is : "+fileName);
			}catch (Exception e) {
				logger.error("config file edit wrong!config file is : "+fileName, e);
				throw new SimulateDataException(SimulateDataException.CONFIG_ERROR, "config file edit wrong!config file is : "+fileName);
			}
		}
	}

	public String html(String content) {
		if (content == null){
			return "";
		}
		String html = content;
		html = StringUtils.replace(html, "'", "&apos;");
		html = StringUtils.replace(html, "\"", "&quot;");
		html = StringUtils.replace(html, "\t", "&nbsp;&nbsp;");// 替换跳格
		html = StringUtils.replace(html, " ", "&nbsp;");// 替换空格
		html = StringUtils.replace(html, "<", "&lt;");
		html = StringUtils.replace(html, ">", "&gt;");
		return html;
	}

	public Map<String, SimulateDataConfig> getConfigs() {
		return configs;
	}

	public void setConfigs(Map<String, SimulateDataConfig> configs) {
		this.configs = configs;
	}

}
