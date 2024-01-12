package b_Money;

import java.util.Hashtable;

public class Bank {
	private Hashtable<String, Account> accountlist = new Hashtable<String, Account>();
	private String name;
	private Currency currency;

	/**
	 * Constructs a new Bank.
	 *
	 * @param name     The name of the bank.
	 * @param currency The base currency of the bank.
	 */
	public Bank(String name, Currency currency) {
		this.name = name;
		this.currency = currency;
	}

	/**
	 * Gets the name of the bank.
	 *
	 * @return The name of the bank.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the currency of the bank.
	 *
	 * @return The currency of the bank.
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * Opens a new account in the bank.
	 * [Change] Now properly creates a new account and adds it to the account list.
	 * @param accountid The ID of the new account.
	 * @throws AccountExistsException if the account already exists.
	 */
	public void openAccount(String accountid) throws AccountExistsException {
		if (accountlist.containsKey(accountid)) {
			throw new AccountExistsException();
		} else {
			accountlist.put(accountid, new Account(accountid, this.currency));
		}
	}

	/**
	 * Deposits money into an account.
	 * [Change] Fixed the conditional check to correctly identify existing accounts.
	 * @param accountid The ID of the account.
	 * @param money The money to deposit.
	 * @throws AccountDoesNotExistException if the account does not exist.
	 */
	public void deposit(String accountid, Money money) throws AccountDoesNotExistException {
		if (!accountlist.containsKey(accountid)) {
			throw new AccountDoesNotExistException();
		} else {
			Account account = accountlist.get(accountid);
			account.deposit(money);
		}
	}

	/**
	 * Withdraws money from an account.
	 * [Change] Corrected the method to actually withdraw money instead of depositing it.
	 * @param accountid The ID of the account.
	 * @param money The money to withdraw.
	 * @throws AccountDoesNotExistException if the account does not exist.
	 */
	public void withdraw(String accountid, Money money) throws AccountDoesNotExistException {
		if (!accountlist.containsKey(accountid)) {
			throw new AccountDoesNotExistException();
		} else {
			Account account = accountlist.get(accountid);
			account.withdraw(money);
		}
	}

	/**
	 * Gets the balance of an account.
	 *
	 * @param accountid The ID of the account.
	 * @return The balance of the account.
	 * @throws AccountDoesNotExistException if the account does not exist.
	 */
	public Integer getBalance(String accountid) throws AccountDoesNotExistException {
		if (!accountlist.containsKey(accountid)) {
			throw new AccountDoesNotExistException();
		} else {
			return accountlist.get(accountid).getBalance().getAmount();
		}
	}

	/**
	 * Transfers money between two accounts.
	 *
	 * @param fromaccount The ID of the account to deduct from in this bank.
	 * @param tobank      The bank where the receiving account resides.
	 * @param toaccount   The ID of the receiving account.
	 * @param amount      The amount of money to transfer.
	 * @throws AccountDoesNotExistException if one of the accounts does not exist.
	 */
	public void transfer(String fromaccount, Bank tobank, String toaccount, Money amount) throws AccountDoesNotExistException {
		if (!accountlist.containsKey(fromaccount) || !tobank.accountlist.containsKey(toaccount)) {
			throw new AccountDoesNotExistException();
		} else {
			accountlist.get(fromaccount).withdraw(amount);
			tobank.accountlist.get(toaccount).deposit(amount);
		}
	}

	/**
	 * Transfers money between two accounts within the same bank.
	 *
	 * @param fromaccount The ID of the account to deduct from.
	 * @param toaccount   The ID of the receiving account.
	 * @param amount      The amount of money to transfer.
	 * @throws AccountDoesNotExistException if one of the accounts does not exist.
	 */
	public void transfer(String fromaccount, String toaccount, Money amount) throws AccountDoesNotExistException {
		transfer(fromaccount, this, toaccount, amount);
	}

	/**
	 * Adds a timed payment.
	 *
	 * @param accountid   The ID of the account to deduct from.
	 * @param payid       The ID of the timed payment.
	 * @param interval    The number of ticks between payments.
	 * @param next        The number of ticks till the first payment.
	 * @param amount      The amount of money to transfer each payment.
	 * @param tobank      The bank where the receiving account resides.
	 * @param toaccount   The ID of the receiving account.
	 */
	public void addTimedPayment(String accountid, String payid, Integer interval, Integer next, Money amount, Bank tobank, String toaccount) {
		Account account = accountlist.get(accountid);
		account.addTimedPayment(payid, interval, next, amount, tobank, toaccount);
	}

	/**
	 * Removes a timed payment.
	 *
	 * @param accountid The ID of the account to remove the timed payment from.
	 * @param id        The ID of the timed payment.
	 */
	public void removeTimedPayment(String accountid, String id) {
		Account account = accountlist.get(accountid);
		account.removeTimedPayment(id);
	}

	/**
	 * Processes a time unit passing in the system, triggering timed payments.
	 * [Change] Added method to process time units for timed payments.
	 * @throws AccountDoesNotExistException if an account involved in a timed payment does not exist.
	 */
	public void tick() throws AccountDoesNotExistException {
		for (Account account : accountlist.values()) {
			account.tick();
		}
	}
}
