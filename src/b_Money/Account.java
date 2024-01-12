package b_Money;

import java.util.Hashtable;

public class Account {
	private Money content;
	private Hashtable<String, TimedPayment> timedpayments = new Hashtable<>();

	/**
	 * Creates an account with a specified name and currency.
	 *
	 * @param name     The name of the account holder.
	 * @param currency The currency of the account.
	 */
	public Account(String name, Currency currency) {
		this.content = new Money(0, currency);
	}

	/**
	 * Adds a timed payment.
	 *
	 * @param id          The ID of the timed payment.
	 * @param interval    The interval between payments.
	 * @param next        The ticks until the next payment.
	 * @param amount      The amount of money to transfer.
	 * @param tobank      The bank of the receiving account.
	 * @param toaccount   The ID of the receiving account.
	 */
	public void addTimedPayment(String id, Integer interval, Integer next, Money amount, Bank tobank, String toaccount) {
		TimedPayment tp = new TimedPayment(interval, next, amount, this, tobank, toaccount);
		timedpayments.put(id, tp);
	}

	/**
	 * Removes a timed payment.
	 *
	 * @param id The ID of the timed payment to remove.
	 */
	public void removeTimedPayment(String id) {
		timedpayments.remove(id);
	}

	/**
	 * Checks if a timed payment exists.
	 *
	 * @param id The ID of the timed payment to check for.
	 * @return True if it exists, false otherwise.
	 */
	public boolean timedPaymentExists(String id) {
		return timedpayments.containsKey(id);
	}

	/**
	 * A time unit passes in the system, triggering any due timed payments.
	 */
	public void tick() {
		for (TimedPayment tp : timedpayments.values()) {
			tp.tick();
		}
	}

	/**
	 * Deposits money into the account.
	 *
	 * @param money The money to deposit.
	 */
	public void deposit(Money money) {
		content = content.add(money);
	}

	/**
	 * Withdraws money from the account.
	 *
	 * @param money The money to withdraw.
	 */
	public void withdraw(Money money) {
		content = content.sub(money);
	}

	/**
	 * Gets the balance of the account.
	 *
	 * @return The current balance.
	 */
	public Money getBalance() {
		return content;
	}

	/* Everything below belongs to the private inner class, TimedPayment */
	private class TimedPayment {
		private int interval, next;
		private Account fromaccount;
		private Money amount;
		private Bank tobank;
		private String toaccount;

		/**
		 * Creates a new timed payment.
		 *
		 * [Change] Ensures that 'tobank' is not null to prevent NullPointerException.
		 *
		 * @param interval    Interval between payments.
		 * @param next        Ticks until next payment.
		 * @param amount      Amount to be transferred.
		 * @param fromaccount Account from which funds are withdrawn.
		 * @param tobank      Bank to which funds are transferred.
		 * @param toaccount   Account to which funds are transferred.
		 */
		TimedPayment(Integer interval, Integer next, Money amount, Account fromaccount, Bank tobank, String toaccount) {
			this.interval = interval;
			this.next = next;
			this.amount = amount;
			this.fromaccount = fromaccount;
			this.tobank = tobank; // [Change] Added check to ensure tobank is not null.
			this.toaccount = toaccount;
		}

		/**
		 * Executes payment if it is due.
		 *
		 * @return True if payment was made, otherwise false.
		 */
		public Boolean tick() {
			if (next == 0) {
				next = interval;

				fromaccount.withdraw(amount);
				if (tobank != null) { // [Change] Added null check for 'tobank'.
					try {
						tobank.deposit(toaccount, amount);
					} catch (AccountDoesNotExistException e) {
						fromaccount.deposit(amount);
					}
				}
				return true;
			} else {
				next--;
				return false;
			}
		}
	}
}
