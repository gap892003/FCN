import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class DataLayer {

	public static DataLayer instance = null;
	private File dataFile;
	
	/**
	 * 
	 * Method to retrieve singleton instance
	 * 
	 * @return
	 */
	public static DataLayer shareInstance(){
		
		if ( instance == null ){
			
			instance = new DataLayer();
		}		
		
		return instance;
	}
	
	
	/**
	 * Constructor is private because we want to have
	 * singleton instance
	 */
	private DataLayer (){
		
		//initialize file here
		// create a directory with name same as IP address 
		try {
			
			String folderName = InetAddress.getLocalHost().getHostAddress().replace(".", "_");
			File folder = new File( folderName );			
			
			if ( !folder.exists() ){
				
				folder.mkdir();				
			}
			
		} catch (UnknownHostException e) {

			e.printStackTrace();
		}
		
		// append file should be true 
		dataFile = new File("dataFile.txt");
	}
	
	void writeEntryInCache( String str ){
		
		// open file output stream 
		// write in file here
	}
	
	
	String lookupInCache ( String lookupEntry ) {
		
		// read from file here
		return null;
	}
	
	String getNextServerToContact (){
		
		
		return null;
	}
}
