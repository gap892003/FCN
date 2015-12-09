import java.net.DatagramPacket;


public class TLDServerTask extends ServerTask{

	public TLDServerTask(DatagramPacket _packet) {
		
		super(_packet);
	}
	
	@Override
	Message processRequest( Message reqMessage ){
	 
		// if TLD server comes here that means we need to
		// forward query or give response back 
		//forwarding query applies to only recursive mode. 
		
		RequestSender sender = new RequestSender( server.knownServerIP, server.knownServerPortNumber);
		Message tempResponse = sender.sendUDP(reqMessage);
		
		// cache this reposne
		if ( !tempResponse.error ){
			
			DataLayer.shareInstance().writeEntryInCache( tempResponse.data );
		}
		
		return tempResponse;
	}
}
