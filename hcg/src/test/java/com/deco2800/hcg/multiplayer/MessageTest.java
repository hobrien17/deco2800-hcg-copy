/**
 * Testing message in multiplayer function 
 * 
 * @author Duc (Ethan) Phan
 * @author Max Crofts
 *
 */
package com.deco2800.hcg.multiplayer;

import static org.junit.Assert.*;
import org.junit.*;
import static junit.framework.TestCase.fail;

public class MessageTest {
	
	private byte[] correctHeader = "H4RDC0R3".getBytes();
	private byte[] incorrectHeader = "H4RDC0R1".getBytes();
	
	private byte[] id = {0, 0, 0, 1};
	
	private byte[] correctType = {1};
	private byte[] incorrectType = {99};
	
	private byte[] payload = "123456789ABC".getBytes();
	
	private byte[] concat(byte[] header, byte[] id, byte[] type, byte[] payload) {
		byte[] concatenated = new byte[header.length + id.length + type.length + payload.length];
		System.arraycopy(
				header, 0, concatenated, 0, header.length);
		System.arraycopy(
				id, 0, concatenated, header.length, id.length);
		System.arraycopy(
				type, 0, concatenated, header.length + id.length, type.length);
		System.arraycopy(
				payload, 0, concatenated, header.length + id.length + type.length, payload.length);
		return concatenated;
	}
	
	@Test
    public void MessageTest0() throws MessageFormatException{	
		// Correct Format
		new Message(concat(correctHeader, id, correctType, payload));
	}
	
	@Test
	public void MessageTest1(){		
		// Wrong header
		try {
			new Message(concat(incorrectHeader, id, correctType, payload));
			fail("Expected a MessageFormatException to be thrown"); 
		} catch (MessageFormatException e){
		}
	}
	
	@Test
	public void MessageTest2(){		
		// Wrong Message Type
		try {
			new Message(concat(correctHeader, id, incorrectType, payload));
			fail("Expected a MessageFormatException to be thrown");
		} catch (MessageFormatException e) {}	
	}
	
}
