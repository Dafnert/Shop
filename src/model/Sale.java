package model;

import java.util.ArrayList;

public class Sale{
	Client client;
	//Product[] products;
	//Lo cambiamos los ArrayList
	ArrayList<Product> products; 
	Amount amount;
	String almacenarFechas;

	 
	// public Sale(String client, Product[] products, double amount)
	//Cambiamos el Product[] products por el ArrayList <String> products
	public Sale(String client, ArrayList<Product> products, Amount amount,String almacenarFechas){
		super();
		this.client = new Client(client);
		this.products = products;
		this.amount = amount;
		this.almacenarFechas=almacenarFechas;
	}
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	//Cambiamos el array Product[] products por el ArrayList <String> products
	public ArrayList<Product> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	public Amount getAmount() {
		return amount;
	}
	public void setAmount(Amount amount) {
		this.amount = amount;
	}
	public String getAlmacenarFechas() {
		return almacenarFechas;
	}
	public void getAlmacenarFechas(String almacenarFechas) {
		this.almacenarFechas = almacenarFechas;
	}

	@Override
	//Para que nos de la información de sale.toString()
	public String toString() {
		//Quitamos el toSting() y solamente le ponemos el nombre.
		return "Sale [client=" + client + ", products=" + products + ", amount=" + amount + ", Fecha y hora= "+ almacenarFechas+ "]";
	}
	
	
	
	

}
