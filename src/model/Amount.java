package model;
//Creamos la clase para poder usarla en nuestra actividad de UF4
public class Amount {
	private double value;
	private String currency="€";
	
	public Amount(double value) {
		super();
		this.value=value;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "Amount [value=" + value + currency + "]";
	}
	

}
