package service;

public interface Connector extends Runnable{
	public Service getService();
	public void run();
}
