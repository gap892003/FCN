import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.LinkedList;


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
			String serverToContact = null; 
			String queryString = null;
			String stringToSend = reqMessage.data;
			// first lookup
			queryString = queryString.substring(reqMessage.data.indexOf("."));
			serverToContact = DataLayer.shareInstance().getNextServerToContact(queryString);
			
			// this mean Auth is not there
			if (serverToContact == null ){
				
				stringToSend = queryString;
				queryString = queryString.substring(queryString.indexOf("."));
				serverToContact = DataLayer.shareInstance().getNextServerToContact(queryString);
			}
			
			// this means TLD is not there
			if ( serverToContact == null ){
				
				stringToSend = queryString;								
				serverToContact = server.knownServerIP + ":" + server.knownServerPortNumber;
//				response = new Message();
//				response.data = "No server found!";
//				response.error = true;
//				return response;
			}
			
//			String [] serverInfo = serverToContact.split(":");			
			ArrayList<String> queue = new ArrayList<String>();
			queue.add(serverToContact);

			boolean stop = false;
			do {
				
				// future thing to add here is to contact array servers one by one 
				// if they are not reachable
				
				if (queue.isEmpty()){
					
					response = new Message();
					response.error = true;
					response.data = "Something went wrong!";
					break;
				}
								
				String nextServer = queue.remove(0);
				String [] serverInfo = nextServer.split(":");;
				RequestSender sender = new RequestSender(serverInfo[0], Integer.parseInt(serverInfo[1]));
				
				// create new request 
				Message tempRequest = new Message();
				tempRequest.qr = true;
				tempRequest.data = stringToSend;
						
				Message tempResponse = sender.sendUDP(reqMessage);
				
				// this indicates we have a response 
				// ONLY APPLICABLE FOR ITERATIVE QUERY
				// we stop only when a valid response has arrived 
				// or an error has occurred 
				// also no server to contact more
				if ( tempResponse.error && queue.isEmpty() ){
															
					response = tempResponse;
					response.qr = false;
					stop = true;
					break;
				}else if ( tempResponse.isReferral ){
					
					// temp response data should contain next server to contact					
					// empty queue here 
					// cache entry 
					//DataLayer.shareInstance().writeEntryInCache( " " + );
					queue.clear();					
					String nextServers[] = tempResponse.data.split(",");	
					
					for (String s : nextServers){
						
						queue.add(s);
					}
					
//					String nextServer = nextServers.split(",")[0]; // get first instance
//																	// if it is a list of servers
				}	
				
			} while( !stop );			
		}
		
		return response;
	}	
}
