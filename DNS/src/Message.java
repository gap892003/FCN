import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
	boolean qr;   		// set this bit true if request, false if response
	boolean recursiveQuery; // set this true if recursive mode is required 
	boolean error;		// indicates if any error occurred 
	boolean isReferral; // for iterative query this means 
						// we need to request a different server 
	String data = null ;		// variable length data 
						// please note that data will be sent in 
						// format IP:Port and need to be extracted that way.
						// if there are multiple records that match the entry 
						// then comma seperated values IP1:PORT1, IP2:PORT2
						// will be provided 
	/**
	 * Use this method to send to serialize object
	 */
	@Override
	public byte[] messageDataToBytes() {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dataOut = new DataOutputStream(out);
		byte[] returnArr = null;
		
		try {
			
			dataOut.writeBoolean( qr );
			dataOut.writeBoolean( recursiveQuery );
			dataOut.writeBoolean( error );
			dataOut.writeBoolean( isReferral );
			
			// writing length of the data  
			if (data != null){
				
				dataOut.writeInt( data.length() );
				dataOut.write(data.getBytes());
			}else{
				
				dataOut.writeInt( 0 );
			}
		} catch (IOException e) {
		
			e.printStackTrace();
		}

		return returnArr;
	}

	/**
	 * Use this method to send to deserialize object
	 */
	@Override
	public void messageFromData(byte[] array) {
		
		ByteArrayInputStream in = new ByteArrayInputStream(array);
		DataInputStream datain = new DataInputStream(in);
		
		try {
			
			qr = datain.readBoolean();
			recursiveQuery = datain.readBoolean();
			error = datain.readBoolean();
			isReferral = datain.readBoolean();
			
			// read length of data 
			int length = datain.readInt();
			
			// no point in reading ahead 
			if ( length == 0 ) return;
			
			byte[] dataByteArr = new byte[length];
			datain.readFully( dataByteArr );
			data = new String (dataByteArr) ;			
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
