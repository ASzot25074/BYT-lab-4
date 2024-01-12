package b_Money;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the Currency class.
 */
public class CurrencyTest {
	Currency SEK, DKK, EUR;

	/**
	 * Setup method to initialize test data before each test case.
	 *
	 * @throws Exception If an exception occurs during setup.
	 */
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	/**
	 * Test case for getting the currency name.
	 */
	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}

	/**
	 * Test case for getting the currency exchange rate.
	 */
	@Test
	public void testGetRate() {
		assertEquals(0.15, SEK.getRate(), 0.001);
		assertEquals(0.20, DKK.getRate(), 0.001);
		assertEquals(1.5, EUR.getRate(), 0.001);
	}

	/**
	 * Test case for setting the currency exchange rate.
	 */
	@Test
	public void testSetRate() {
		SEK.setRate(0.2);
		assertEquals(0.2, SEK.getRate(), 0.001);
	}

	/**
	 * Test case for calculating the global value of an amount in the currency.
	 */
	@Test
	public void testGlobalValue() {
		assertEquals(1500, (int) SEK.universalValue(10000));
	}

	/**
	 * Test case for calculating the value of an amount in this currency.
	 */
	@Test
	public void testValueInThisCurrency() {
		int amountInEUR = 1500;
		long expectedAmountInSEK = (long) (amountInEUR * EUR.getRate() / SEK.getRate()); // Convert to SEK
		assertEquals(expectedAmountInSEK, (long) SEK.valueInThisCurrency(amountInEUR, EUR));
	}
}
