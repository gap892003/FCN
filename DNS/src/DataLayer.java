
public class DataLayer {

	public static DataLayer instance = null;
	/**
	 * 
	 * Method to retriev singleton instance
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
	 * Constructor is private beacuse we wnat to have
	 * singleton instance
	 */
	private DataLayer (){
		
		//initialize file here
		
		// 
	}
	
	void writeEntryInCache( String str ){
		
		
	}
	
	
	String lookupInCache ( String lookupEntry ) {
		
		return null;
	}
}
