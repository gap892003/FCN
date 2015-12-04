/**
 *  RequestSender.java
 *  
 *  Class that sends request according to protocol specified
 *  Used by client as well as ProxyServerTask to forward requests
 *   
 *  @author Gaurav Joshi
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class RequestSender{

	String serverIp;
	int port;
	final public static int TIMEOUT = 5;
	private boolean useTcp = true;
	private long RTT = 0;
	MessageInterface messg;
	
	RequestSender ( ){
		
		// this will expect serverIp and port number to be set later
	}
	
	RequestSender ( String serverIp, int port){
		
		this.serverIp = serverIp;
		this.port = port;
	}

	
	/**
	 * Sends request to server on tcp port
	 * 
	 * @param messageToSend
	 * @return
	 */
	Message sendTcp ( MessageInterface messageToSend ){
		
		long starttime = System.currentTimeMillis();
		Socket socket = null;
		Message response = null;
		
		try {
			
			socket = new Socket();
			socket.connect(new InetSocketAddress(serverIp, port), TIMEOUT );
			OutputStream out = socket.getOutputStream(); 
			out.write(messageToSend.messageDataToBytes());
			
			InputStream input = socket.getInputStream();
			byte[] incomingMessage = new byte[1024];
			input.read(incomingMessage);			
			response = new Message(); 
			response.messageFromData(incomingMessage);

		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}finally{
			
			try {
				
				socket.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		
		// print RTT
		long endTime = System.currentTimeMillis();
		System.out.println("RTT: " + (endTime-starttime) + "ms");
		return response;
	}
	
	/**
	 * Sends request to server on UDP port
	 * @param request
	 * @return
	 */
	Message sendUDP ( MessageInterface request ){
		
		long starttime = System.currentTimeMillis();
		InetAddress address = null;
		DatagramSocket clientSocket = null;
		Message response = null;
//		Timer timer = new Timer(true);
//		final int sequenceNumber = request.sequenceNumber;
		
//		// potential deadlock
//		// start timer for each request
//		timer.schedule( new TimerTask() {
//			
//			@Override
//			public void run() {
//				
//				synchronized (timeoutHandler) {
//					
//					if (timeoutHandler != null){
//						
//						timeoutHandler.timeout(sequenceNumber);
//					}					
//				}
//			}
//		}, 500);
		
		try {
		
			// send 
			address = InetAddress.getByName(serverIp);
			clientSocket = new DatagramSocket();
			byte[] buf = request.messageDataToBytes();
			DatagramPacket packet = new DatagramPacket( buf, buf.length ,address,port);
			clientSocket.send(packet);		
			
			// get response and parse
		    byte[] receivedData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receivedData, receivedData.length);
		    clientSocket.receive(receivePacket);
		    
		    // first thing cancel timer
//			timer.cancel();
		    response = new Message(); 
		    response.messageFromData( receivePacket.getData() );
		    
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			clientSocket.close();
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("RTT: " + (endTime-starttime) + "ms");
		RTT = endTime-starttime;
		return response;
	}
	
	/**
	 * Convenience method 
	 * 
	 * @param req
	 * @return
	 */
	Message send ( Message req ){
		
		if (useTcp){
			
			return sendTcp(req);
		}
		
		return sendUDP(req);
	}
	
	public boolean isUseTcp() {
		return useTcp;
	}

	public void setUseTcp(boolean useTcp) {
		this.useTcp = useTcp;
	}

	public long getRTT() {
		return RTT;
	}
	
}
