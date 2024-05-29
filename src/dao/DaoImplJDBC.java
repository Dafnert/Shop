package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Employee;

public class DaoImplJDBC implements Dao {
	 
private Connection connection;
	//Para que haya conexión en la base de datos
	public void connect() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/shop";
		String user = "root";
		String password = "";
		this.connection = DriverManager.getConnection(url, user, password);
	}
	
	public Employee getEmployee(int employeeId, String password) {
		
		Employee employee = null;
		// prepare query
		String query = "select * from employee where employeeId = ? and password = ?";
		
		try (PreparedStatement ps = connection.prepareStatement(query)) { 
			// set id and the password to search for
			ps.setInt(1,employeeId);
			ps.setString(2, password);
		  	//System.out.println(ps.toString());
	        try (ResultSet rs = ps.executeQuery()) {
	        	if (rs.next()) {
	                employee = new Employee(rs.getInt(1), rs.getString(2));
	        	}else {
	        		System.out.println("no data");
	        	}
	        }
	    } catch (SQLException e) {
			// in case error in SQL
			e.printStackTrace();
		}
				
		return employee;
	}	
	//Para que se desconecte en la base de datos
	public void disconnect() throws SQLException{
		if (connection != null) {
			connection.close();
		}
	}
}
