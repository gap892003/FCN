/**
 * Base class for both servers.
 * Contains information about ports 
 * Whether the server is proxy or not
 * 
 * @author gaurav
 *
 */


public abstract class Server implements Runnable{

	protected int port;
	
	// Needed in case of a proxy server 
	protected boolean isSlave = false;
	protected String knownServerIP;
	protected int knownServerPortNumber;
	MasterServerInfo backupAtSlave;

	/**
	 * Use this constructor only when you are not sure 
	 * about port number.
	 */
	Server(){
		
		// it will expect port to be set later on
	}
	
	// Constructor
	// Prefer this one always
	Server( int port ){
		
		this.port = port;
	}
	
	@Override
	public void run(){
		
		startServer();
	}
	
	/**
	 * Method to start server 
	 */
	public void start(){
		
		Thread thread =new Thread(this);
		thread.start();
	}
	
	/**
	 * Method to start server
	 */
	abstract void startServer();
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isProxy() {
		return isSlave;
	}

	public void setProxy(boolean isSlave) {
		this.isSlave = isSlave;
	}		
	
	void initializeAsSlave(){
		
		// contact master server here 
	}
}
