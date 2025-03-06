package dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Employee;
import model.Product;

public interface Dao {
	
	public void connect() throws SQLException;	
	public Employee getEmployee(int employeeId, String password) ;
	public void disconnect() throws SQLException; 
	public ArrayList<Product> getInventory() ;
	public void updateProduct(Product product);
	public void removeProduct(int productId);
	public void addProduct(Product product);
	public boolean writeInventory(ArrayList<Product> inventory);
	


	
}
