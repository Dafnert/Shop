package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import model.Amount;
import model.Employee;
import model.Product;

public class DaoImplJDBC implements Dao {
	private ArrayList<Product> products = new ArrayList<>();
	private Connection connection;

	// Para que haya conexión en la base de datos
	public void connect() throws SQLException {
		if (connection != null) {
			return; // Ya está conectada
		}
		String url = "jdbc:mysql://localhost:3306/shop";
		String user = "root";
		String password = "";
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Employee getEmployee(int employeeId, String password) {

		Employee employee = null;
		// prepare query
		String query = "select * from employee where employeeId = ? and password = ?";

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			// set id and the password to search for
			ps.setInt(1, employeeId);
			ps.setString(2, password);
			// System.out.println(ps.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					employee = new Employee(rs.getInt(1), rs.getString(2));
				} else {
					System.out.println("no data");
				}
			}
		} catch (SQLException e) {
			// in case error in SQL
			e.printStackTrace();
		}

		return employee;
	}

	// Para que se desconecte en la base de datos
	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
				connection = null; // Marcar conexión como cerrada
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Product> getInventory() {
		try {
			connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// SQL query
		String query = "SELECT * FROM inventory";
		// List to store the products
		ArrayList<Product> products = new ArrayList<>();

		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

			// Iterate through the query results
			while (rs.next()) {
				// Get the amount
				Amount wholesalerPrice = new Amount(rs.getDouble("wholesalerPrice"));
				Amount amount = new Amount(wholesalerPrice.getValue() * 2);

				// Add a new product to the list
				products.add(new Product(rs.getString("name"), wholesalerPrice, rs.getBoolean("available"),
						rs.getInt("stock"), amount));
				System.out.println("Import inventory");
			}

		} catch (SQLException e) {
			e.printStackTrace(); // Consider logging the error
		}

		disconnect();

		return products;
	}

	public boolean writeInventory(ArrayList<Product> inventory) {
	    String query = "INSERT INTO historical_inventory (name, wholesalerPrice, available, stock, created_at) VALUES (?, ?, ?, ?, ?)";
	    
	    try {
	        connect();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        for (Product product : inventory) {
	            stmt.setString(1, product.getName());
	            stmt.setDouble(2, product.getWholesalerPrice().getValue());
	            stmt.setInt(3, product.isAvailable() ? 1 : 0);  // Corrige el booleano
	            stmt.setInt(4, product.getStock());
	            stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
	            stmt.executeUpdate();
	            System.out.println("Export inventory");
	        }
	        disconnect();
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}


	public void updateProduct(Product product) {
		String query = "UPDATE inventory SET stock = ? WHERE name = ?";
		try {
			connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, product.getStock());
			stmt.setString(2, product.getName());
			stmt.executeUpdate();
			System.out.println("Update product");
			disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeProduct(int productId) {
		String query = "DELETE FROM inventory WHERE id = ?";
		try {
			connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, productId);
			stmt.executeUpdate();
			System.out.println("Delete product");
			disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void addProduct(Product product) {
		String query = "INSERT INTO inventory (id, name, wholesalerPrice, available, stock) VALUES (?, ?, ?, ?, ?)";
		try {
			connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, product.getId());
			stmt.setString(2, product.getName());
			Amount amount = new Amount();
			amount = product.getWholesalerPrice();
			stmt.setDouble(3, amount.getValue());
			stmt.setBoolean(4, product.isAvailable());
			stmt.setInt(5, product.getStock());
			stmt.executeUpdate();
			System.out.println("Add product");
			disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
