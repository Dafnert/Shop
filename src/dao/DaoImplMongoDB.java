package dao;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import model.Amount;
import model.Employee;
import model.Product;

public class DaoImplMongoDB implements Dao {
	MongoCollection<Document> collection;
	ObjectId id;
	MongoClient mongoClient;
	MongoCollection<Document> mongoCollection;
	@Override
	public void connect() {
		String uri = "mongodb://localhost:27017";
		MongoClientURI mongoClientURI = new MongoClientURI(uri);
		mongoClient = new MongoClient(mongoClientURI);
		MongoDatabase mongoDatabase = mongoClient.getDatabase("shop");
		collection = mongoDatabase.getCollection("inventory");
	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		connect();
		MongoDatabase mongoDatabase = mongoClient.getDatabase("shop");
		MongoCollection<Document> login;
		login = mongoDatabase.getCollection("users");
		Document user = login.find(eq("employeeId", employeeId)).filter(eq("password", password)).first();
		if (user != null) {
			Employee employee = new Employee();
			employee.setEmployeeId(employeeId);
			employee.setPassword(password);
			return employee;
		}
		return null;
	}

	@Override
	public void disconnect() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Product> getInventory() {
		ArrayList<Product> products = new ArrayList<>();
		connect();
		try {
			FindIterable<Document> iterable = collection.find();
			for (Document doc : iterable) {
				Product product = new Product();
				product.setName(doc.getString("name"));
				Document wholesalerPrice = (Document) doc.get("wholesalerPrice");
				double value = wholesalerPrice.getDouble("value");
				String currency = wholesalerPrice.getString("currency");
				Amount amount = new Amount(value);
				amount.setCurrency(currency);
				product.setWholesalerPrice(amount);
				product.setAvailable(doc.getBoolean("available"));
				product.setStock(doc.getInteger("stock"));
				product.setId(doc.getInteger("id"));
				products.add(product);
				System.out.println(product);
			}
		} catch (MongoException e) {
			System.err.println("Error fetching inventory: " + e.getMessage());
		}
		return products;
	}

	public boolean writeInventory(ArrayList<Product> inventory) {
		connect();
		MongoDatabase mongoDatabase = mongoClient.getDatabase("shop");
		mongoCollection = mongoDatabase.getCollection("historical_inventory");
		try {
			for (Product product : inventory) {
				Amount amount = new Amount();
				amount = product.getWholesalerPrice();
				
				Document doc = new Document("_id", new ObjectId()).append("id", product.getId())
						.append("name", product.getName()).append("available", product.isAvailable())
						.append("stock", product.getStock())
						.append("wholesalerPrice", new Document("value", amount.getValue()).append("currency", amount.getCurrency()));
				mongoCollection.insertOne(doc);
			}
			return true;
		} catch (MongoException e) {
			System.err.println("Error inserting inventory: " + e.getMessage());
			return false;
		}
	}

	public void updateProduct(Product product) {
		try {
			connect();
			UpdateResult result = collection.updateOne(eq("id", product.getId()),
					new Document("$inc", new Document("stock", product.getStock())));
			product.setStock(product.getStock());
			System.out.println("Document updated: " + result.getModifiedCount());

		} catch (MongoException e) {
			System.err.println("Error updating product: " + e.getMessage());
		}
	}

	public void removeProduct(int productId) {
		try {
			connect();
			DeleteResult result = collection.deleteOne(eq("id", productId));
			System.out.println("Product deleted: " + result.getDeletedCount());
		} catch (MongoException e) {
			System.err.println("Error removing product: " + e.getMessage());
		}
	}

	public void addProduct(Product product) {
		try {
			connect();
			Document document = new Document("id", product.getId()).append("name", product.getName())
					.append("stock", product.getStock())
					.append("wholesalerPrice",
							new Document("value", product.getWholesalerPrice().getValue()).append("currency",
									product.getWholesalerPrice().getCurrency()))
					.append("available", product.isAvailable());

			collection.insertOne(document);
			System.out.println("Product added Successfully: " + document);
		} catch (MongoException e) {
			System.err.println("Error adding product: " + e.getMessage());
		}
	}

}
