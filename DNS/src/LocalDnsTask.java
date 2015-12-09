import java.net.DatagramPacket;


public class LocalDnsTask extends ServerTask{

	public LocalDnsTask(DatagramPacket _packet) {
		super(_packet);
	}

	@Override
	Message processRequest( Message reqMessage ){
		
		Message response = null;
		
		if (reqMessage.recursiveQuery){
			
			// use this area
		}else{
			
			// iterative query
			boolean stop = false;
			do {
				
				RequestSender sender = new RequestSender(server.knownServerIP, server.port);
				Message tempResponse = sender.sendUDP(reqMessage);
				
				// this indicates we have a response 
				// ONLY APPLICABLE FOR ITERATIVE QUERY
				// we stop only when a valid response has arrived 
				// or an error has occurred 
				if ( !tempResponse.isReferral || tempResponse.error ){
					
					response = tempResponse;
					stop = true;
				}	
				
			} while( !stop );			
		}
		
		return response;
	}	
}
