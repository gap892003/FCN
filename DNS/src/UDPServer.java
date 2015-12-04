/**
 * This class represents a UDP server. 
 * It listens on mentioned port. Creates new thread
 * for each request received. According to whether it is serving as proxy
 * or not it creates a ProxyServerTask or ServerTask Object. 
 * 
 * @author Gaurav Joshi
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer extends Server {

	UDPServer( ) {
		
		// it will expect port to be set later on
	}

	UDPServer( int _udpPort ) {
		
		port =_udpPort;
	}
	
	private DatagramSocket datagramServerSocket;

	@Override
	void startServer(){

		System.out.println("Starting UDP Server");
		
		try {
			
			setDatagramServerSocket(new DatagramSocket(port));
			
			while(true){
				
				byte[] receivedData = new byte[2048];
				DatagramPacket packet = new DatagramPacket(receivedData, receivedData.length);
				datagramServerSocket.receive(packet);
				Logger.log("Received UDP request");
				ServerTask task;				
				task = new ServerTask( packet );				
				task.setServer(this); // parent object 
				task.setTCP(false);	 // task is UDP or TCP			
				task.processUDP(); // this makes server task sequential 
			}	
			
		} catch (SocketException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
				
	public DatagramSocket getDatagramServerSocket() {
		
		return datagramServerSocket;
	}

	public void setDatagramServerSocket(DatagramSocket datagramServerSocket) {
		this.datagramServerSocket = datagramServerSocket;
	}	
}
