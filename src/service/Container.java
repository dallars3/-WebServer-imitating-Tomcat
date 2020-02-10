package service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import pipeline.ContextPipeline;
import util.RandomString;

public class Container {
	private ContextPipeline pipeline;
	private HashMap<String, HttpSession> sessionMap;
	private String path;
	public Container(String path){
		this.path = path;
		pipeline = new ContextPipeline(this);
		sessionMap = new HashMap<String, HttpSession>();
		initContext();
	}
	public void getSession(Request request){
		String id = request.getSessionId();
		request.setSession(sessionMap.get(id));
	}
	public void createSession(Request request, Response response){
		String id = RandomString.getRandomString(16);
		Session session = new Session(id);
		sessionMap.put(id, session);
		response.appendHead("Set-cookie: JSESSIONID=" + id + "\r\n");
	}
	private void initContext(){
		getWebappsList(path);
	}
	private  void getWebappsList(String path){
		File webappsPath = new File(path);
		File[] fileArray = webappsPath.listFiles();
		ArrayList<File> webappsList = new ArrayList<File>();
		for(File file : fileArray){
			if(file.isDirectory()){
				pipeline.addContext(file);
			}
		}
	}
	public ContextPipeline getPipeline(){
		return pipeline;
	}
	
}
