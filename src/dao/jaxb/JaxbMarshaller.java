package dao.jaxb;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import model.Product;
import model.ProductList;



public class JaxbMarshaller {
	public void init (ArrayList<Product> products) {
		LocalDate date = LocalDate.now();
		try {
			JAXBContext context = JAXBContext.newInstance(ProductList.class);
			Marshaller marshaller = context.createMarshaller();
			System.out.println("marshalling... ");
			ProductList product = createXml(products);
			marshaller.marshal(product, new File("xml/inventory_"+date+".xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	private ProductList createXml(ArrayList<Product> products) {
	   
	        for (Product p : products) {
	            System.out.println(p); 
	        }
	        
	        ProductList productList = new ProductList(products);
	        productList.setTotal(products.size());
	        
	    return productList;
	}
}
