package service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import pipeline.ContextPipeline;
import util.RandomString;

public class Container {
	private final ContextPipeline pipeline;
	private final ConcurrentHashMap<String, HttpSession> sessionMap;
	private final String path;
	public Container(String path){
		this.path = path;
		pipeline = new ContextPipeline(this);
		sessionMap = new ConcurrentHashMap<String, HttpSession>();
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
	private void getWebappsList(String path){
		File webappsPath = new File(path);
		File[] fileArray = webappsPath.listFiles();
		ExecutorService executor = new ThreadPoolExecutor
				(1, 8, 500, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(32));
		for(File file : fileArray){
			if(file.isDirectory()){
				executor.submit(new Runnable() {
					@Override
					public void run() {
						pipeline.addContext(file);
					}
				});
			}
		}
		executor.shutdown();
	}
	public ContextPipeline getPipeline(){
		return pipeline;
	}
	
}
