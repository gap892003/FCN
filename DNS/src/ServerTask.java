/**
 *  ServerTask.java
 *  
 *  Server task object. Its a thread that serves incoming request. 
 *  Responsibilities include processing request and sending appropriate 
 *  response to the client.
 *  
 *  @author Gaurav
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;

public class ServerTask implements Runnable{

	protected Server server; // required for this specify
	Socket socket;
	protected DatagramPacket packet;
	private boolean isTCP;
	
	ServerTask( Socket _socket ){
		
		socket = _socket;
	}

	public ServerTask( DatagramPacket _packet) {
		
		packet= _packet;
	}
	
	@Override
	public void run() {
		
		processUDP();
	}
		
	/**
	 * Method to process incoming UDP requests
	 * 
	 * */
	void processUDP(){

		Message request = new Message();
		request.messageFromData( packet.getData() );		
		
		Message messageToSend = null;
		messageToSend = cacheLookup(request); 
				
		// this means we could not find data in cache
		if (messageToSend == null){
			
			// this will be different for all servers
			messageToSend = processRequest( request );			
		}

		// return the response
		byte [] dataToSend = messageToSend.messageDataToBytes();
		InetAddress ipToSend = packet.getAddress();
		int portTosend = packet.getPort();
        DatagramPacket packetToSend =
        new DatagramPacket(dataToSend, dataToSend.length, ipToSend, portTosend);
        try {
			((UDPServer) server).getDatagramServerSocket().send(packetToSend);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Cache lookup functionality for all servers 
	 * 
	 * @param reqMessage
	 * @return response Message 
	 */
	Message cacheLookup( Message reqMessage ){
				
		DataLayer data = DataLayer.shareInstance();
		String result = data.lookupInCache( reqMessage.data );
	
		if ( result  == null) return null;
		
		// TODO: add few things here 
		Message response = new Message();
		response.data = result;
		return response;
	}
	
	/**
	 * 
	 * Deserialise request object read it and perform actions
	 * accordingly. 
	 * 
	 * Creates response object. Puts path from request in it. 
	 * 
	 * @param reqMessage     request received 
	 * @param clientProtocol not used here 
	 * @return
	 */
	Message processRequest( Message reqMessage ){
			
		// Do something here 
		Message response = new Message();		
		return response;
	}
		
	/**
	 * Start executing task
	 */
	void execute(){
		
		Thread thread = new Thread(this);
		thread.start();
	}

	
	// getters and setters
	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public boolean isTCP() {
		return isTCP;
	}

	public void setTCP(boolean isTCP) {
		this.isTCP = isTCP;
	}
}
