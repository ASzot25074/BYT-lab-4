package b_Money;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the Bank class.
 */
public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;

	/**
	 * Setup method to initialize test data before each test case.
	 *
	 * @throws Exception If an exception occurs during setup.
	 */
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	/**
	 * Test case for getting the bank name.
	 */
	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	/**
	 * Test case for getting the currency associated with the bank.
	 */
	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	/**
	 * Test case for opening an account.
	 *
	 * @throws AccountExistsException If the account already exists.
	 */
	@Test(expected = AccountExistsException.class)
	public void testOpenAccount() throws AccountExistsException {
		SweBank.openAccount("Ulrika");
	}

	/**
	 * Test case for depositing money into an account.
	 *
	 * @throws AccountDoesNotExistException If the account does not exist.
	 */
	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(5000, SEK));
		assertEquals(Integer.valueOf(5000), SweBank.getBalance("Ulrika"));
	}

	/**
	 * Test case for withdrawing money from an account.
	 *
	 * @throws AccountDoesNotExistException If the account does not exist.
	 */
	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(5000, SEK));
		SweBank.withdraw("Ulrika", new Money(3000, SEK));
		assertEquals(Integer.valueOf(2000), SweBank.getBalance("Ulrika"));
	}

	/**
	 * Test case for getting the balance of an account.
	 *
	 * @throws AccountDoesNotExistException If the account does not exist.
	 */
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(5000, SEK));
		assertEquals(Integer.valueOf(5000), SweBank.getBalance("Ulrika"));
	}

	/**
	 * Test case for transferring money between accounts.
	 *
	 * @throws AccountDoesNotExistException If the account does not exist.
	 */
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(5000, SEK));
		SweBank.transfer("Ulrika", "Bob", new Money(3000, SEK));
		assertEquals(Integer.valueOf(2000), SweBank.getBalance("Ulrika"));
		assertEquals(Integer.valueOf(3000), SweBank.getBalance("Bob"));
	}

	/**
	 * Test case for timed payments.
	 *
	 * @throws AccountDoesNotExistException If the account does not exist.
	 * @throws InterruptedException        If the thread is interrupted.
	 */
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException, InterruptedException {
		SweBank.addTimedPayment("Ulrika", "rent", 2, 2, new Money(1000, SEK), Nordea, "Bob");
		SweBank.deposit("Ulrika", new Money(5000, SEK));
		SweBank.tick();
		SweBank.tick();
		SweBank.tick();
		// After 3 ticks, the payment should have been made
		assertEquals(Integer.valueOf(4000), SweBank.getBalance("Ulrika"));
		assertEquals(Integer.valueOf(1000), Nordea.getBalance("Bob"));
		SweBank.removeTimedPayment("Ulrika", "rent");
	}
}
