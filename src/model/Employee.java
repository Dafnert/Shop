package model;
import java.sql.SQLException;

import dao.Dao;
import dao.DaoImplJDBC;
import main.Logable;
public class Employee extends Person implements Logable {
	
	// Definimos las constnates de USER y PASSWORD para que pueda acceder al login
	final static int USER = 123;
	final static String PASSWORD = "test"; 
		
	// Creamos la clase Employee que herede de la clase Person y tenga relaciÃ³n con el interface Logable
	private int employeeId;
	private String password;
	private String name;
	//We create the attribute dao of type Dao
	private Dao dao;
	//Assignar a atributo dao un objeto del constructor DaoImplJDBC

	public Employee() {
		this.dao= new DaoImplJDBC();
	}
	//Creamos un constructor con llos paramentro de entrada employyeId y el password
	public Employee(int employeeId,String password) {
		this.employeeId=employeeId;
		this.password=password;
	}
	
	//Implementamos el metodo login con parametros de entrada que son las variables constantes
	
	public boolean login(int user, String password) throws SQLException{
		dao=new DaoImplJDBC();
		
		try {
			//Se conecta a la base de datos
			System.out.println("Conectandose a la base de datos");
			dao.connect();
			System.out.println("Conectado");

			//Obtenemos la información del Employee
			System.out.println("Datos:");
			Employee employee = dao.getEmployee(user, password);
			// Devolver true si Employee está informado o false si es null
				if(employee != null){
					System.out.println("Employee found");
					return true;
				}//else {
					//return true;
				//} 
		}catch (SQLException ex) {
		System.out.println("ERROR con la BBDD: " + ex.getMessage());
		}finally {
         
			// Disconnect from the database
            dao.disconnect();
        
		}
		return false;
	    /**if(user== 123 && password.equalsIgnoreCase("test")) {
		return true;
	
	}else {
		return false;
	}*/
	}
	
	//Generamoslos gettes y setters
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public Dao getDao() {
		return dao;
	}
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}

	
	

