package com.ge.cab;

public class JsonResponse {

	public String convertToJson(String str) 
	{
		return "{\"response\": \""+str+"\"}";
	}
	
	public String convertToJson(String str,String str2) 
	{
		return "{\"validity\":  \""+str+"\",\"response\": \""+str2+"\"}";
	}
}