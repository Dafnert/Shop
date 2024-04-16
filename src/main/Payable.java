package main;

import model.Amount;

public interface Payable {

	boolean pay(Amount amount); // Definimos un metodo pay con entrada de la clase Amount y que devuelve un boolean
}
