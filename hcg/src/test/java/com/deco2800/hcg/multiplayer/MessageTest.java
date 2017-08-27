/**
 * Testing message in multiplayer function 
 * 
 * @author Duc (Ethan) Phan 
 *
 */
package com.deco2800.hcg.multiplayer;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.rules.ExpectedException;
import static junit.framework.TestCase.fail;



public class MessageTest {
	private Message receiveMessage;
	
	private byte[] message1 = "H4RDC0R39876JOIN123456789ABC".getBytes();
	private byte[] message2 = "H4RDC0R19876JOIN123456789ABC".getBytes();
	private byte[] message3 = "H4RDC0R39876JOiK123456789ABC".getBytes();
	
	@Test
    public void MessageTest0() throws MessageFormatException{	
		// Correct Format
		new Message(message1);
	}
	@Test
	public void MessageTest1(){		
		// Wrong header
		try {
			Message receivedMessage2 = new Message(message2);
			fail("Expected a MessageFormatException to be thrown"); 
		} catch (MessageFormatException e){
		}
	}	
	@Test
	public void MessageTest2(){		
		// Wrong Message Type
		try {
			Message receivedMessage3 = new Message(message3);
			fail("Expected a MessageFormatException to be thrown");
		} catch (MessageFormatException e){
		}
		
	}


}
