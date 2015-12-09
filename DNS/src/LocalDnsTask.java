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
			// Following is required for local DNS as it will cache entries for TLDs 
			String serverToContact = DataLayer.shareInstance().getNextServerToContact();
			String [] serverInfo = serverToContact.split(":");
			
			boolean stop = false;
			do {
				
				// future thing to add here is to contact array servers one by one 
				// if they are not reachable 				
				RequestSender sender = new RequestSender(serverInfo[0], Integer.parseInt(serverInfo[1]));
				Message tempResponse = sender.sendUDP(reqMessage);
				
				// this indicates we have a response 
				// ONLY APPLICABLE FOR ITERATIVE QUERY
				// we stop only when a valid response has arrived 
				// or an error has occurred 
				if ( !tempResponse.isReferral || tempResponse.error ){
					
					response = tempResponse;
					stop = true;
				}else{
					
					// temp reponse data should contain next server to contact
					String nextServers = tempResponse.data;
					String nextServer = nextServers.split(",")[0]; // get first instance
																	// if it is a list of servers
					serverInfo = nextServer.split(":");
				}	
				
			} while( !stop );			
		}
		
		return response;
	}	
}
