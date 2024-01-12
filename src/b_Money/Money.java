package b_Money;

import java.util.Objects;

public class Money implements Comparable<Money> {
	private int amount;
	private Currency currency;

	/**
	 * Constructor for Money.
	 * @param amount The amount of money.
	 * @param currency The currency of the money.
	 */
	Money(Integer amount, Currency currency) {
		this.amount = amount;
		this.currency = currency;
	}

	/**
	 * Return the amount of money.
	 * @return The amount of money.
	 */
	public Integer getAmount() {
		return this.amount;
	}

	/**
	 * Returns the currency of this Money.
	 * @return The currency of this Money.
	 */
	public Currency getCurrency() {
		return this.currency;
	}

	/**
	 * Returns a string representation of the money.
	 * @return A string representing the amount of Money.
	 */
	public String toString() {
		int wholePart = amount / 100;
		int fractionalPart = Math.abs(amount % 100);
		return String.format("%d.%02d %s", wholePart, fractionalPart, currency.getName());
	}

	/**
	 * Gets the universal value of the Money.
	 * @return The value of the Money in the "universal currency".
	 */
	public Integer universalValue() {
		return currency.universalValue(amount);
	}

	/**
	 * Compares this Money object to another object for equality.
	 * [Change] Overridden to ensure that Money objects are compared based on their amount and currency.
	 *
	 * @param obj The object to compare with.
	 * @return true if the given object represents a Money object equivalent to this one, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Money money = (Money) obj;
		return amount == money.amount && Objects.equals(currency, money.currency);
	}

	/**
	 * Returns a hash code value for the object.
	 *
	 * @return A hash code value for this object.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(amount, currency);
	}


	/**
	 * Adds a Money to this Money.
	 * @param other The Money to be added.
	 * @return A new Money instance representing the sum.
	 */
	public Money add(Money other) {
		int addedAmountInThisCurrency = this.amount + (int)(other.universalValue() / this.currency.getRate());
		return new Money(addedAmountInThisCurrency, this.currency);
	}

	/**
	 * Subtracts a Money from this Money.
	 * @param other The Money to be subtracted.
	 * @return A new Money instance representing the difference.
	 */
	public Money sub(Money other) {
		int subtractedAmountInThisCurrency = this.amount - (int)(other.universalValue() / this.currency.getRate());
		return new Money(subtractedAmountInThisCurrency, this.currency);
	}

	/**
	 * Check if the amount of this Money is zero.
	 * @return True if the amount is zero, False otherwise.
	 */
	public Boolean isZero() {
		return this.amount == 0;
	}

	/**
	 * Negates the amount of money.
	 * @return A new Money instance with the negated amount.
	 */
	public Money negate() {
		return new Money(-this.amount, this.currency);
	}

	/**
	 * Compares this Money with another Money.
	 * @param other The Money to compare with.
	 * @return 0 if equal, a negative integer if less, a positive if more.
	 */
	public int compareTo(Money other) {
		return this.universalValue().compareTo(other.universalValue());
	}
}
