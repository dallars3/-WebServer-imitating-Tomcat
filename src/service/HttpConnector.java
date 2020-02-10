package service;

import java.io.*;
import java.net.*;

import javax.net.ssl.SSLContext;

public class HttpConnector implements Connector{
	private final int PORT = 8090;
	private final Service service;
	public HttpConnector(Service service) {
		this.service = service;
	}
	@Override
	public void run() {
		ServerSocket ss;
		try {
			ss = new ServerSocket(PORT);
			while(true){
				Socket socket = ss.accept();
				new Thread(new Processor(socket, this)).start();
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	public Service getService(){
		return service;
	}
}
