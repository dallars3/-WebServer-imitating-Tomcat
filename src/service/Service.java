package service;


public class Service {
	static Service service = null;
	private Connector connector = null;
	private Container container = null;
	private static final String WEB_PATH = "e:/test";
	private Service(){
		container = new Container(WEB_PATH);
	}
	public static Service getService(){
		if(service == null) service = new Service();
		return service;
	}
	public void start(){
		connector = new HttpConnector(this);
		new Thread(connector).start();
		
		
	}
	public Container getContainer(){
		return container;
	}

}
