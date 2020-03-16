package service;

public interface Connector extends Runnable{
	/**
	 * Get Service
	 * @return Service
	 */
	public Service getService();
	/**
	 * Connector start running
	 */
	public void run();
}
