package service;


public class Service {
	private final static Service SERVICE = new Service();
	private final Connector connector1;
	private final Container container;
	//webapp·��
	private static final String WEB_PATH = "e:/test";
	private Service(){
		container = new Container(WEB_PATH);
		//ʵ��HTTPЭ���Connector������չ����Э���Connector
		connector1 = new HttpConnector(this);
	}
	public static Service getService(){
		return SERVICE;
	}
	public void start(){
		new Thread(connector1).start();
	}
	public Container getContainer(){
		return container;
	}

}
