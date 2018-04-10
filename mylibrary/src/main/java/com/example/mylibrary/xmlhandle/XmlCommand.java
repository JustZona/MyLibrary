package com.example.mylibrary.xmlhandle;

import com.example.mylibrary.Json.MyExclus;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class XmlCommand {
	
	private String pNameStart="";
	
	private String qNameEnd="";
	
	private Map<String,String> qNames = new HashMap<String, String>();
	
	private List<XmlCommand> xmlCommands = new LinkedList<XmlCommand>();
	
	@MyExclus
	private XmlCommand parentXml;
	
	private String content;
	
	public XmlCommand getParentXml() {
		return parentXml;
	}

	public void setParentXml(XmlCommand parentXml) {
		this.parentXml = parentXml;
		if(parentXml!=null){
			this.parentXml.addChild(this);
		}
		
	}
	
	public void addChild(XmlCommand value){
		xmlCommands.add(value);
	}

	public void setQVName(String key,String value){
		qNames.put(key, value);
	}
	
	public String getpNameStart() {
		return pNameStart;
	}

	public void setpNameStart(String pNameStart) {
		this.pNameStart = pNameStart;
	}

	public String getqNameEnd() {
		return qNameEnd;
	}

	public void setqNameEnd(String qNameEnd) {
		this.qNameEnd = qNameEnd;
	}

	public Map<String, String> getqNames() {
		return qNames;
	}

	public void setqNames(Map<String, String> qNames) {
		this.qNames = qNames;
	}

	public List<XmlCommand> getXmlCommands() {
		return xmlCommands;
	}

	public void setXmlCommands(List<XmlCommand> xmlCommands) {
		this.xmlCommands = xmlCommands;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
