package it.ecommerce;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import it.ecommerce.util.Util;

public class UtilFormatTest {

	@Test
	public void testFormatString() {
		assertEquals("Daniele", Util.formatString("dAnielE"));
	}

	@Test
	public void testFormatNull() {
		assertEquals("", Util.formatString(null));
	}
	
	@Test
	public void testFormatEmpty() {
		assertEquals("",Util.formatString(""));
	}
	
	@Test 
	public void testFormatSpace() {
		assertEquals("Matteo rossi", Util.formatString("matteo Rossi"));
	}
	
	@Test 
	public void testFormatChar() {
		assertEquals("M", Util.formatString("m"));
	}
}
