package model;

public class Person {
	//Hacemos un constructor para luego usarlo
	public Person(String client) {
		super();
		this.name=client;
	}
	public Person() {
		
	}
	
	// Declaramos la variable name con protected
	protected String name;
}
