/**
 * Man class which will take command line arguments and initialize
 * the process as client, localDNS or TLD
 *  
 * Also takes the file name where all data is stored. If file
 *  doesn't exist then creates one. 
 *    
 * @author Gaurav Joshi
 *
 */

public class DNS {
	
	/**
	 * main
	 */	
	public static void main( String args[] ){
		
		
		if ( args[0].equals("-c") ){
			
			startAsClient(args);
		} else if ( args[0].equals("-s") ){
			
			startAsServer(args);
		}
	}
	
	/**
	 * Method to start client
	 *  
	 * @param args
	 */
	public static void startAsClient( String args[] ){
		
		String request = null;
		String ip = null;
		int port = 0;
		
		if ( args[1].equals( "-q" ) ){
			
			request = args[2];			
		}
		
		ip = args[2];
		port = Integer.parseInt( args[3] );
		Client client = new Client ( ip, port );
		client.sendRequest(request);
	}
	
	/**
	 * 
	 * Method to start server
	 * 
	 * @param args
	 */
	public static void startAsServer( String args[] ){
		
		UDPServer server = null;
		Server.ServerType type = null;
		int port= Integer.parseInt( args [args.length - 1] );		
		server = new UDPServer( port );

		if ( args[1].equals("l") ) {
			
			// local DNS
			type = Server.ServerType.LOCALDNS;						
			server.knownServerIP = args[2];
			server.knownServerPortNumber = Integer.parseInt( args[3] );		
		}else if ( args[1].equals("r") ) {
			
			// root server 
			type = Server.ServerType.ROOT;
			
		}else if ( args[1].equals("t") ) {
			
			// TLD server
			type = Server.ServerType.TLD;
		}else if ( args[1].equals("a") ) {
			
			// Authoritative
			type = Server.ServerType.AUTHORATATIVE;
		} 
		
		server.serverType = type ; 
		server.start();		
	}
}
