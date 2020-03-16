package service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.Cookie;

public class RequestFactory {
	//根据String构建request对象
	public synchronized static Request createRequest(String head, String content){
		//保存get传的参数
		StringBuilder contentsb = new StringBuilder();
		HashMap<String, String> headers = createHead(head, contentsb);
		//get传参时，参数的提取
		if(content == null && contentsb.length() != 0){
			content = new String(contentsb);
		}else if(content != null && contentsb.length() != 0){
			contentsb.append("&");
			contentsb.append(content);
			content = new String(contentsb);
		}
		HashMap<String, String[]> parameterMap = createContent(content);
		ArrayList<Cookie> cookieList = createCookie(headers);
		return new Request(headers, cookieList, parameterMap);	
	}
	//构建request头部
	private static HashMap<String, String> createHead(String head, StringBuilder content){
		HashMap<String, String> headers = new HashMap<String, String>();
		String[] headArr = head.split("\r\n");
		String[] temp0 = headArr[0].split(" ");
		headers.put("method", temp0[0]);
		headers.put("protocol", temp0[2]);
		String[] path = temp0[1].split("\\?");
		headers.put("servletPath", path[0]);
		if(path.length == 2){
			content.append(path[1]);
		}
		for(int i = 1; i < headArr.length; i ++){
			String[] temp = headArr[i].split(": ");
			headers.put(temp[0], temp[1]);
		}
		return headers;
	}
	//构建request参数
	private static HashMap<String, String[]> createContent(String content){
		HashMap<String, String[]> parameterMap = new HashMap<String, String[]>();
		if(content == null){
			return null;
		}
		String[] datas = content.split("&");
		for(int i = 0; i < datas.length; i ++){
			String[] temp = datas[i].split("=");
			String[] data = parameterMap.get(temp[0]);
			if(data == null){
				data = new String[1];
				data[0] = temp[1];
			}else{
				String[] newData = new String[data.length+1];
				System.arraycopy(data, 0, newData, 0, data.length);
				newData[data.length] = temp[1];
				data = newData;
			}
			parameterMap.put(temp[0], data);
		}
		return parameterMap;
		
	}
	//提取cookie
	private static ArrayList<Cookie> createCookie(HashMap<String, String> headers){
		ArrayList<Cookie> cookieList = new ArrayList<Cookie>();
		String[] cookieStrs;
		try{
			cookieStrs = headers.get("Cookie").split(";");
		}catch (Exception e) {
			return cookieList;
		}
		for(String cookieStr : cookieStrs){
			String[] temp = cookieStr.split("=");
			cookieList.add(new Cookie(temp[0], temp[1]));
			
		}
		return cookieList;
	}
}
