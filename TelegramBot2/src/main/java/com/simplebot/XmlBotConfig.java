package com.simplebot;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class XmlBotConfig implements BotConfig {
	
	
	private String userName;
	private String token;
	
	private Request request;
	
	public XmlBotConfig(String xmlPath) throws DocumentException
	{
		
		System.out.println("xml_path:"+xmlPath);
		SAXReader reader = new SAXReader();
        Document document = reader.read(new File(xmlPath));
        userName=document.selectSingleNode("/bot/user_name").getText();
        token=document.selectSingleNode("/bot/token").getText();
        System.out.println("userName:"+userName+",token:"+token);
        
        Node nodeRequest=document.selectSingleNode("/bot/request");
        
        //nodeRequest.getUniquePath()
        
        if(nodeRequest!=null)
        {
        	
        	request=new Request(null,nodeRequest,nodeRequest.valueOf("@key"));
        }
	}
	
	

	public String getUserName() {

		return userName;
	}

	public String getToken() {

		return token;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setToken(String token) {
		this.token = token;
	}



	public Request getRootRequest() {
		return request;
	}

}
