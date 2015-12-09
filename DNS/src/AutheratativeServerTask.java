import java.net.DatagramPacket;


public class AutheratativeServerTask extends ServerTask {

	public AutheratativeServerTask(DatagramPacket _packet) {
		super(_packet);
	}

	@Override
	Message processRequest( Message reqMessage ){
		
		// Authoratative server should bot come here 
		// if it does that means entry is not in cache 
		// throw error 
		Message response  = new Message();
		response.qr = false;
		response.error = true;
		response.data = "No such domain !";		
		return response;
	}
}
