/**
 * 
 * Message class that implements serialization 
 * and deserialization methods
 * 
 * @author Gaurav
 *
 */
public class Message implements MessageInterface{

	
	
	// Message structure 
	// length of data 
	// 
	boolean bit;
	String data;
	
	/**
	 * Use this method to send to serialize object
	 */
	@Override
	public byte[] messageDataToBytes() {
		
		return null;
	}

	/**
	 * Use this method to send to deserialize object
	 */
	@Override
	public void messageFromData(byte[] array) {
		
	}
}
