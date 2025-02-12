package dao;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import model.Amount;
import model.Employee;
import model.Product;
import model.ProductHistory;

public class DaoImplHibernate implements Dao {
	private Session session;
	private Transaction tx;

	@Override
	public void connect() {
		Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
		org.hibernate.SessionFactory sessionFactory = configuration.buildSessionFactory();
		session = sessionFactory.openSession();

	}

	@Override
	public Employee getEmployee(int employeeId, String password)  {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disconnect() {
		session.close();
	}

	//Implementar el set
	@Override
	public ArrayList<Product> getInventory() {
		connect();
		ArrayList<Product> products = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			Query q = session.createQuery("FROM Product");
			List<Product> productList = q.getResultList();
			products.addAll(productList);
			for (Product product : products) {
				double price = product.getPrice();
				Amount wholersalerPrice = new Amount(price);
				product.setWholesalerPrice(wholersalerPrice);
				
				System.out.println(product);
			}
			tx.commit();
			System.out.println("Successfully");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
		}
		disconnect();
		return products;
	}

	public void addProduct(Product product) {
		connect();
		try {
			tx = session.beginTransaction();
			session.save(product);
			tx.commit();
			System.out.println("Product added Successfully.");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
		}
		disconnect();
	}
	public void removeProduct(int productId) {
		connect();
		Product product = new Product();
		try {
			tx = session.beginTransaction();
			product.setId(productId);
			session.remove(product);
			tx.commit();
			System.out.println("Product Deleted Successfully.");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
		}
		disconnect();
	}
	public void updateProduct(Product product) {
		connect();
		try {
			tx = session.beginTransaction();
			session.update(product);
			tx.commit();
			System.out.println("Product Updated Successfully.");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
		}
		disconnect();
	}

	@Override
	public boolean writeInventory(ArrayList<Product> products) {
		
		connect();
		try {
			LocalDateTime date = LocalDateTime.now();
			tx = session.beginTransaction();
			for(Product  product:products) {
				ProductHistory productHistory = new ProductHistory();
				productHistory.setAvailable(product.isAvailable());
				productHistory.setCreated_at(date);
				productHistory.setProductId(product.getId());
				productHistory.setName(product.getName());
				productHistory.setPrice(product.getPrice());
				productHistory.setStock(product.getStock());
				session.save(productHistory);
			}
			tx.commit();
			System.out.println("Inventory written Successfully.");
			return true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
		}
		disconnect();
		return false;
	}

}
