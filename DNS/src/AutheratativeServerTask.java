import java.net.DatagramPacket;


public class AutheratativeServerTask extends ServerTask {

	public AutheratativeServerTask(DatagramPacket _packet) {
		super(_packet);
	}

	@Override
	Message processRequest( Message reqMessage ){
		
		return null;
	}
}
