package b_Money;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the Money class.
 */
public class MoneyTest {
	Currency SEK, DKK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;

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
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(500, EUR); // Changed amount to 500 EUR
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	/**
	 * Test case for getting the amount of money.
	 */
	@Test
	public void testGetAmount() {
		assertEquals(10000, (int) SEK100.getAmount());
	}

	/**
	 * Test case for getting the currency of the money.
	 */
	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SEK100.getCurrency());
	}

	/**
	 * Test case for converting money to its string representation.
	 */
	@Test
	public void testToString() {
		assertEquals("100.00 SEK", SEK100.toString());
	}

	/**
	 * Test case for calculating the global value of money.
	 */
	@Test
	public void testGlobalValue() {
		assertEquals(1500, (int) SEK100.universalValue());
	}

	/**
	 * Test case for checking equality between money objects.
	 */
	@Test
	public void testEqualsMoney() {
		Money anotherSEK100 = new Money(10000, SEK);
		assertTrue(SEK100.equals(anotherSEK100));

		int eur10InSEK = (int) (EUR10.universalValue() / SEK.getRate());
		Money eur10ConvertedToSEK = new Money(eur10InSEK, SEK);
		assertFalse(SEK100.equals(eur10ConvertedToSEK));
	}

	/**
	 * Test case for adding two money amounts.
	 */
	@Test
	public void testAdd() {
		int eur10InSEK = (int) (500 * 1.5 / 0.15);
		int expectedAmountInSEK = 10000 + eur10InSEK;
		Money sum = SEK100.add(EUR10);
		assertEquals(expectedAmountInSEK, sum.getAmount().intValue());
	}

	/**
	 * Test case for subtracting two money amounts.
	 */
	@Test
	public void testSub() {
		int eur10InSEK = (int) (500 * 1.5 / 0.15);
		int expectedAmountInSEK = 20000 - eur10InSEK;
		Money difference = SEK200.sub(EUR10);
		assertEquals(expectedAmountInSEK, difference.getAmount().intValue());
	}

	/**
	 * Test case for checking if money is zero.
	 */
	@Test
	public void testIsZero() {
		assertTrue(SEK0.isZero());
		assertFalse(SEK100.isZero());
	}

	/**
	 * Test case for negating money.
	 */
	@Test
	public void testNegate() {
		assertEquals(-10000, (int) SEKn100.getAmount());
	}

	/**
	 * Test case for comparing two money amounts.
	 */
	@Test
	public void testCompareTo() {
		assertEquals(0, SEK100.compareTo(new Money(10000, SEK)));

		int eur10InSEK = (int) (EUR10.universalValue() / SEK.getRate());
		Money eur10ConvertedToSEK = new Money(eur10InSEK, SEK);
		assertTrue(SEK200.compareTo(eur10ConvertedToSEK) > 0);
	}
}
