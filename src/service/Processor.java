package service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import util.FileTransfer;
import value.Value;

public class Processor implements Runnable {
	private InputStream is;
	private OutputStream os;
	private final Socket socket;
	private final Connector connector;
	public Processor(Socket socket, Connector connector) {
		this.socket = socket;
		this.connector = connector;
		initStream();
	}
	@Override
	public void run() {
		String reqString = read();
		String[] reqStrings = parse(reqString);
		Request req;
		//request是否有参数
		if(reqStrings.length == 2){
			req = RequestFactory.createRequest(reqStrings[0], reqStrings[1]);
		}else{
			req = RequestFactory.createRequest(reqStrings[0], null);
		}
		Response response = new Response(os);
		//request是否带有JSessionID
		if( ! req.hasCookie()){
			connector.getService().getContainer().createSession(req, response);
		}else{
			connector.getService().getContainer().getSession(req);
		}
		//传入request寻找请求的文件/servlet
		Value c = connector.getService().getContainer().getPipeline().getFirst();
		try {
			c.invoke(req, response);
		} catch (Exception e) {
			System.out.println("ERROR");
		}
		//发送文件
		if(response.isSendF()){
			sendFile(response);
		}
		try {
			os.close();
			is.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
	//传输文件
	private void sendFile(Response response){
		String path = response.getContext() + response.getFile();	
		String data = FileTransfer.readFile(new File(path));
		PrintWriter pw = new PrintWriter(os);	
		pw.write(response.getHead());
		pw.write("\r\n");
		pw.write(data);
		pw.flush();
		pw.close();
		
	}
	private void initStream(){
		try {
			is = socket.getInputStream();
			os=socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//读取request
	private String read(){
		String reqString;
		byte[] str = new byte[1024*1024];
        int len = 0;
		try {
			len = is.read(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        reqString = new String(str, 0, len);
        System.out.println(reqString);
        return reqString;
	}
	private String[] parse(String req){
		return req.split("\r\n\r\n");
	}
}
