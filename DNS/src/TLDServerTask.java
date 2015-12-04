import java.net.DatagramPacket;


public class TLDServerTask extends ServerTask{

	public TLDServerTask(DatagramPacket _packet) {
		
		super(_packet);
	}
	
	@Override
	Message processRequest( Message reqMessage ){
	 
		return null;
	}
}
