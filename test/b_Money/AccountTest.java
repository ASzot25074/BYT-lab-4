package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the Account class.
 */
public class AccountTest {
	private Currency SEK;
	private Bank SweBank;
	private Account testAccount;

	/**
	 * Setup method to initialize test data before each test case.
	 *
	 * @throws Exception If an exception occurs during setup.
	 */
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("TestAccount");
		testAccount = new Account("TestAccount", SEK);
		testAccount.deposit(new Money(1000, SEK));
	}

	/**
	 * Test case for adding and removing timed payments.
	 */
	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("payment1", 5, 0, new Money(200, SEK), SweBank, "AnotherAccount");
		assertTrue("Timed payment should exist", testAccount.timedPaymentExists("payment1"));
		testAccount.removeTimedPayment("payment1");
		assertFalse("Timed payment should be removed", testAccount.timedPaymentExists("payment1"));
	}

	/**
	 * Test case for getting the account balance.
	 */
	@Test
	public void testGetBalance() {
		assertEquals("Initial balance should be 1000 SEK", new Money(1000, SEK), testAccount.getBalance());
	}

	/**
	 * Test case for timed payments.
	 *
	 * @throws AccountDoesNotExistException If the account does not exist.
	 */
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		Money initialBalance = testAccount.getBalance();
		Money paymentAmount = new Money(200, SEK);
		testAccount.addTimedPayment("payment1", 1, 0, paymentAmount, SweBank, "TestAccount");

		testAccount.tick();

		Money expectedBalanceAfterPayment = initialBalance.sub(paymentAmount);

		assertEquals("Balance should decrease by 200 SEK after timed payment",
				expectedBalanceAfterPayment,
				testAccount.getBalance());
	}

	/**
	 * Test case for adding and withdrawing money from the account.
	 */
	@Test
	public void testAddWithdraw() {
		testAccount.deposit(new Money(500, SEK));
		assertEquals("Balance should be 1500 SEK after deposit", new Money(1500, SEK), testAccount.getBalance());
		testAccount.withdraw(new Money(500, SEK));
		assertEquals("Balance should be 1000 SEK after withdrawal", new Money(1000, SEK), testAccount.getBalance());
	}
}
