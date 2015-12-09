import java.net.DatagramPacket;


public class TLDServerTask extends ServerTask{

	public TLDServerTask(DatagramPacket _packet) {
		
		super(_packet);
	}
	
	@Override
	Message processRequest( Message reqMessage ){
	
		Message response;
		// if TLD server comes here that means we need to
		// forward query or give response back 
		//forwarding query applies to only recursive mode. 
			
		if ( reqMessage.recursiveQuery ){
		String serverToContact = DataLayer.shareInstance().getNextServerToContact(reqMessage.data);
		String [] serverInfo = serverToContact.split(":");
		RequestSender sender = new RequestSender( serverInfo[0], Integer.parseInt(serverInfo[1]) );
		Message tempResponse = sender.sendUDP(reqMessage);
		response = tempResponse;
//		Future scope:
// 		cache this reposne
//		if ( !tempResponse.error ){
//			
//			DataLayer.shareInstance().writeEntryInCache( tempResponse.data );
//		}
		}else{
			
			String resp = DataLayer.shareInstance().lookupInCache(reqMessage.data);
			response = new Message();
			
			if (resp != null){
				response.isReferral = true;
				response.data = resp;
			}else{
				
				response.error = true;
				response.data = "No match found";
			}
		}
		
		return response;
	}
}
