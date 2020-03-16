package service;

import java.io.*;
import java.net.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

public class HttpConnector implements Connector{
	//端口号
	private final int PORT = 8090;
	private final Service service;
	private ExecutorService executor;
	public HttpConnector(Service service) {
		this.service = service;
	}
	@Override
	public void run() {
		//处理Http连接的连接池
		executor = new ThreadPoolExecutor
				(1, 8, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(16));
		ServerSocket ss;
		try {
			ss = new ServerSocket(PORT);
			while(true){
				Socket socket = ss.accept();
				executor.submit(new Processor(socket, this));
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	@Override
	public Service getService(){
		return service;
	}
}
