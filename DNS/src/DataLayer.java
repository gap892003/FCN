import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		try 
		{
			fileWriter = new FileWriter(dataFile,true);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(str);
			bufferedWriter.newLine();
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	String lookupInCache ( String lookupEntry ) {
		// read from file here
		FileReader fileReader;
		try {
			fileReader = new FileReader(dataFile);
			BufferedReader bufferedReader =	new BufferedReader(fileReader);
			String line;
			while((line = bufferedReader.readLine()) != null) {
	            String anEntry[] = line.split(" ");
				if(anEntry[0].equals(lookupEntry))
	            {
	            	 return anEntry[1];
	            }
	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	String getNextServerToContact (String str){
		
		// IP:PORT,IP:PORT
		int dotIndex = str.lastIndexOf('.');
		String strToLookup = str.substring(dotIndex);
		return lookupInCache(strToLookup);
	}
}
