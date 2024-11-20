package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

//Creamos la clase para poder usarla en nuestra actividad de UF4
public class Amount {
	private double value;
	private String currency="€";
	
	public Amount(double value) {
		super();
		this.value=value;
	}
	public Amount() {
		
	}
	 public Amount(double value, String currency) {
	     this.value = value;
	     this.currency = currency;
	 }
	 @XmlValue
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	@XmlAttribute(name="currency")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return  value + currency;
	}
	

}
