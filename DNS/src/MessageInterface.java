/**
 * Interface to be implemented by Message class for 
 * serialization and desrailization
 * 
 * @author Gaurav
 *
 */
public interface MessageInterface {

	byte[] messageDataToBytes();
	void messageFromData(byte[] array);
}
