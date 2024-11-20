package dao.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.Product;

public class DomWriter {
	private Document document;
	
	public DomWriter() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		} catch (ParserConfigurationException e) {
			System.out.println("ERROR generating document");
		}
	}	
	public void generateDocument(ArrayList <Product> products) {
		//PARENT NODE
		//root node
		Element product = document.createElement("products");
		product.setAttribute("total", String.valueOf(products.size()));
		document.appendChild(product);
		
		for (Product producto: products) {
			//Name
			Element productsDom = document.createElement("product");
			productsDom.setAttribute("name", producto.getName());
	        product.appendChild(productsDom);
	       //Price
	       Element wholesalerPrice = document.createElement("wholesalerPrice");
	        wholesalerPrice.setAttribute("currency", "€");
	        wholesalerPrice.appendChild(document.createTextNode(String.valueOf(producto.getWholesalerPrice().getValue())));
	        productsDom.appendChild(wholesalerPrice);
	        //Stock
	       Element stock = document.createElement("stock");
	        stock.appendChild(document.createTextNode(String.valueOf(producto.getStock())));
	        productsDom.appendChild(stock);
		}
		generateXml();
		}
	
	public void generateXml() {
		LocalDate date = LocalDate.now();
		try {			
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			
			Source source = new DOMSource(document);
			File file = new File ("xml/inventory" + date +".xml");
			FileWriter fw = new FileWriter(file);
			PrintWriter pw = new PrintWriter(fw);
			Result result = new StreamResult(pw);
			
			transformer.transform(source, result);
		} catch (IOException e) {
			System.out.println("Error when creating writter file");
		} catch (TransformerException e) {
			System.out.println("Error transforming the document");
		}
	}

}
