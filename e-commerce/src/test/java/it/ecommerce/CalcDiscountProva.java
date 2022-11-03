package it.ecommerce;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import it.ecommerce.util.Util;

public class CalcDiscountProva {

	@RunWith(Parameterized.class)
	public static class CalcDiscountParameterizedTest {

		@Parameters
		public static Collection<Object[]> testConditions() {
			return Arrays.asList(new Object[][] { { 200.00, 50, 50, 50.00 }, { 1234.00, null, null, 1234.00 },
					{ 1234.00, null, 50, 617.00 }, { 1234.00, 50, null, 617.00 }, { 1000.00, 99, 20, 8.00 },
					{ 1000.00, 99, 17, 8.30 } });
		}

		private double start, expected;
		private Integer pers, gen;

		public CalcDiscountParameterizedTest(double start, Integer pers, Integer gen, double expected) {
			this.start = start;
			this.pers = pers;
			this.gen = gen;
			this.expected = expected;
		}

		@Test
		public void testCalcDiscount() {
			assertEquals(expected, Util.calcDiscount(start, pers, gen), 0);
		}

//		@Test
//		public void testNullParameters() {
//			assertEquals(1234, Util.calcDiscount(1234, null, null),0);
//			assertEquals(617, Util.calcDiscount(1234, null, 50),0);
//			assertEquals(617, Util.calcDiscount(1234, 50, null),0);
//		}

	}
}
