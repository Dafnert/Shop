package model;
import main.Logable;
public class Employee extends Person implements Logable {
	// Creamos la clase Employee que herede de la clase Person y tenga relación con el interface Logable
	private int employeeId;
 
	// Definimos las constnates de USER y PASSWORD para que pueda acceder al login
	final static int USER = 123;
	final static String PASSWORD = "test"; 
	
	//Implementamos el metodo login con parametros de entrada que son las variables constantes
	
	public boolean login(int user, String password) {
		//En el caso que haga el usuario ponga bien el número de empleado y su contraseña podrá ver el login
		if(user== 123 && password.equalsIgnoreCase("test")) {
			return true;
		}else {
		return false;
		}
	}
	
	
}
