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
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import model.Employee;
import model.Product;

public class DaoImplMongoDB implements Dao {
	MongoCollection<Document> collection;
	ObjectId id;

	@Override
	public void connect() throws SQLException {
		String uri = "mongodb://localhost:27017";
		MongoClientURI mongoClientURI = new MongoClientURI(uri);
		MongoClient mongoClient = new MongoClient(mongoClientURI);

		MongoDatabase mongoDatabase = mongoClient.getDatabase("shop");
		collection = mongoDatabase.getCollection("inventory");
	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
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
		try {
			connect();
			Iterable<Document> documents = collection.find();
			int count = 0;
			for (Document doc : documents) {
				System.out.println("Product" + (++count) + ": " + doc.toJson());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
		// TODO Auto-generated method stub
		return false;
	}


	public void updateProduct(Product product) {
		UpdateResult result = collection.updateOne(eq("name", product.getName()), // Buscar por nombre
				set("stock", product.getStock()) // Cambiar solo stock
		);
		System.out.println("Document updated: " + result.toString());
	}

	
	public void removeProduct(int productId) {
	    DeleteResult result = collection.deleteOne(eq("id", String.valueOf(productId))); // Convertir a String si es necesario
	    System.out.println("Product deleted: " + result.toString());
	}


	public void addProduct(Product product) {
		try {
			connect();
			Document document = new Document("_id", new ObjectId()).append("name", product.getName())
					.append("stock", product.getStock())
					.append("wholesalerPrice",
							new Document("value", product.getWholesalerPrice().getValue()).append("currency",
									product.getWholesalerPrice().getCurrency()))
					.append("available", product.isAvailable()).append("id", product.getId());
			collection.insertOne(document);
			System.out.println("Product added Successfully." + document);
			this.id = new ObjectId();
			System.out.println("document inserted with ID: " + this.id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
