/**
 * 
 * Client class implements functionality for client 
 * where it sends request to localDNS and waits for response. 
 * No multithreading here. 
 * 
 * @author Gaurav
 * @author Sourabh
 */
public class Client {

	private String localDNS ;
	private int localDNSPort;
	
	Client ( String _localDNS, int _port ){
		
		localDNS = _localDNS;
		localDNSPort = _port;
	}
		
	void sendRequest( String str ){
		
		// do a validation here about the website  
		RequestSender sender = new RequestSender( localDNS, localDNSPort );
		Message request = new Message();
		request.qr = true;
		
		Message response = sender.sendUDP ( request );

		// print response 
		if ( !response.error ){
			
			System.out.println("Result is : " + response.data);
		}else{
			
			System.out.println("Something went wrong !" + "Error :" + response.data );
		}
	}
	
	public String getLocalDNS() {
		return localDNS;
	}

	public void setLocalDNS(String localDNS) {
		this.localDNS = localDNS;
	}

	public int getLocalDNSPort() {
		return localDNSPort;
	}

	public void setLocalDNSPort(int localDNSPort) {
		this.localDNSPort = localDNSPort;
	}

}
