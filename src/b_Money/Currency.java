package b_Money;

public class Currency {
	private String name;
	private Double rate;

	/**
	 * Constructor for Currency.
	 * @param name The name of this Currency.
	 * @param rate The exchange rate of this Currency.
	 */
	Currency(String name, Double rate) {
		this.name = name;
		this.rate = rate;
	}

	/**
	 * Convert an amount of this Currency to its value in a universal currency.
	 * @param amount An amount of cash of this currency.
	 * @return The value of amount in the "universal currency".
	 */
	public Integer universalValue(Integer amount) {
		return (int)(amount * this.rate);
	}

	/**
	 * Get the name of this Currency.
	 * @return The name of this Currency.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the exchange rate of this Currency.
	 * @return The exchange rate of this Currency.
	 */
	public Double getRate() {
		return this.rate;
	}

	/**
	 * Set the exchange rate of this Currency.
	 * @param rate The new exchange rate for this Currency.
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}

	/**
	 * Convert an amount from another Currency to an amount in this Currency.
	 * @param amount Amount of the other Currency.
	 * @param othercurrency The other Currency.
	 * @return The amount in this Currency.
	 */
	public Integer valueInThisCurrency(Integer amount, Currency othercurrency) {
		double amountInUniversal = othercurrency.universalValue(amount);
		return (int) (amountInUniversal / this.rate);
	}

}
