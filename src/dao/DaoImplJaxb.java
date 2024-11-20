package dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.jaxb.JaxbMarshaller;
import dao.jaxb.JaxbUnMarshaller;
import model.Employee;
import model.Product;

public class DaoImplJaxb implements Dao{

	@Override
	public void connect() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Employee getEmployee(int employeeId, String password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disconnect() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Product> getInventory() {
		ArrayList<Product> products = new ArrayList<>();
		// from xml2java
		(new JaxbUnMarshaller()).init();
		return products;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) throws IOException {
		// from java2xml
		(new JaxbMarshaller()).init(inventory);
		return false;
	}
	
}
