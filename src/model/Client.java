package model;

import main.Logable;
import main.Payable;

public  class Client extends Person implements Payable { // la clase cliente hereda la clase Person y tiene conexión con la onterface Payable.
	private int memberId;
	private Amount balance;
	  // Declaramos las variables con un valor fijo
	final static int MEMBER_ID = 456;
	final static double BALANCE = 50.00;
	//Hacemos un constructor
	public Client(String client) {
		super(client);
		this.balance= new Amount(BALANCE);
		this.memberId=MEMBER_ID;
	}
	
	@Override
	public boolean pay(Amount amount) { 
		double precioFinal=0.0; //Declaramos la variable
		precioFinal = 	BALANCE- amount.getValue(); // El dinero que tiene el cliente se resta con el total de la compra
		if(precioFinal>= 0) { // Si el precio es mayor que 0 se hará el cálculo anterior
			Amount amounts = new Amount(precioFinal);
			System.out.println("Cliente restante:" + amounts);
			return true;
		}else {
			return false;
		}
	}
	
//Un toString
	@Override
	public String toString() {
		return "Client [name=" + name + "]";
	}
	
	//Getters y los setters

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}


	public Amount getBalance() {
		return balance;
	}
	public void setBalance(Amount balance) {
		this.balance=balance;
	}

	
}
