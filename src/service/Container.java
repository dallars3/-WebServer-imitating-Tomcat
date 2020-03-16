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
	//保存Session的map，key为JSessionID
	private final ConcurrentHashMap<String, HttpSession> sessionMap;
	//webapp路径
	private final String path;
	public Container(String path){
		this.path = path;
		pipeline = new ContextPipeline(this);
		sessionMap = new ConcurrentHashMap<String, HttpSession>();
		initContext();
	}
	//将session存入request，以便webapp中调用
	public void getSession(Request request){
		String id = request.getSessionId();
		request.setSession(sessionMap.get(id));
	}
	//若request中没有JSessionID，服务器随机生成一个JSessionID和session
	public void createSession(Request request, Response response){
		String id = RandomString.getRandomString(16);
		Session session = new Session(id);
		sessionMap.put(id, session);
		response.appendHead("Set-cookie: JSESSIONID=" + id + "\r\n");
	}
	//初始化所有webapp
	private void initContext(){
		getWebappsList(path);
	}
	private void getWebappsList(String path){
		File webappsPath = new File(path);
		File[] fileArray = webappsPath.listFiles();
		//使用线程池，并发初始化每个webapp
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
