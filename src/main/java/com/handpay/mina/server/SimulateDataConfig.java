package com.handpay.mina.server;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SimulateDataConfig {
	private JsonElement request;
	
	private JsonElement response;

	public JsonElement getRequest() {
		return request;
	}

	public void setRequest(JsonElement request) {
		this.request = request;
	}

	public JsonElement getResponse() {
		return response;
	}

	public void setResponse(JsonElement response) {
		this.response = response;
	}
	
	public static void main(String[] args) {
		Gson gson=new Gson();
		SimulateDataConfig jsonElement=new SimulateDataConfig();
		JsonArray request=new JsonArray();
		JsonParser jsonParser=new JsonParser();
		
		String json="";
		try {
			json = FileUtils.readFileToString(new File("D:/360Downloads/mobileOrder.json"), "utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsonElement element=jsonParser.parse(json);
		JsonObject jsonObject=element.getAsJsonObject();
		System.out.println(jsonObject.get("request"));
	}

}
