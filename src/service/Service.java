package service;


public class Service {
	private final static Service service = new Service();
	private final Connector connector1;
	private final Container container;
	private static final String WEB_PATH = "e:/test";
	private Service(){
		container = new Container(WEB_PATH);
		connector1 = new HttpConnector(this);
	}
	public static Service getService(){
		return service;
	}
	public void start(){
		new Thread(connector1).start();
	}
	public Container getContainer(){
		return container;
	}

}
