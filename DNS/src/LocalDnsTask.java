import java.net.DatagramPacket;


public class LocalDnsTask extends ServerTask{

	public LocalDnsTask(DatagramPacket _packet) {
		super(_packet);
	}

	@Override
	Message processRequest( Message reqMessage ){
		
		return null;
	}	
}
