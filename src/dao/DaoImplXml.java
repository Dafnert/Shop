package dao;
import dao.xml.SaxReader;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import dao.xml.DomWriter;
import model.Employee;
import model.Product;


public class DaoImplXml implements Dao{

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

		// Read an existing xml document
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser;
		try {
			parser = factory.newSAXParser();
			File file = new File ("xml/inputInventory.xml");
			SaxReader saxReader = new SaxReader();
			parser.parse(file, saxReader);
			products = saxReader.getProducts();
			
		} catch (ParserConfigurationException | SAXException e) {
			System.out.println("ERROR creating the parser");
		} catch (IOException e) {
			System.out.println("ERROR file not found");
		}
		return products;
	
	}
    public boolean writeInventory(ArrayList<Product> products) throws IOException {
		DomWriter domWriter = new DomWriter();
        try {
        	System.out.println(products.toString());
    		domWriter.generateDocument(products);

            return true;
        } catch (Exception e) {
            System.out.println("Error writing inventory: " + e.getMessage());
            return false;
        }finally {
        	if(domWriter!=null) {
        		
        	}
        }
    }

	
}


