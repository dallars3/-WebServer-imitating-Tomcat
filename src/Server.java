import service.*;


public class Server {
	public static void main(String[] args){
		start();
	}
	private static void start(){
		Service service = Service.getService();
		service.start();
	}
}
