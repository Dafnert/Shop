package model;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import model.Amount;

@XmlRootElement(name="product")
@XmlType(propOrder = {"name", "publicPrice", "wholesalerPrice", "stock"}) 
public class Product {
	private int id;
    private String name;
    private Amount publicPrice;
    private Amount wholesalerPrice;
    private boolean available;
    private int stock;
    private static int totalProducts;
    
    static double EXPIRATION_RATE=0.60;
    
	public Product(String name, Amount wholesalerPrice, boolean available, int stock, Amount publicPrice) {
		super();
		this.id = totalProducts+1;
		this.name = name;
		this.publicPrice = publicPrice;
		this.wholesalerPrice = wholesalerPrice;
		this.available = available;
		this.stock = stock;
		totalProducts++;
	}
	public Product(String name, Amount  wholesalerPrice, int stock) {
		this.name = name;
		this.wholesalerPrice = wholesalerPrice;
		this.stock = stock;
	}
	public Product() {
	
	}; 
	@XmlAttribute(name="id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@XmlAttribute(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@XmlElement(name = "publicPrice")
	public Amount getPublicPrice() {
		return publicPrice;
	}

	public void setPublicPrice(Amount publicPrice) {
		this.publicPrice = publicPrice;
	}
	@XmlElement(name = "wholesalerPrice")
	public Amount getWholesalerPrice() {
		return wholesalerPrice;
	}

	public void setWholesalerPrice(Amount wholesalerPrice) {
		this.wholesalerPrice = wholesalerPrice;
	}
	@XmlAttribute(name="available")
	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	@XmlElement(name="stock")
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public static int getTotalProducts() {
		return totalProducts;
	}

	public static void setTotalProducts(int totalProducts) {
		Product.totalProducts = totalProducts;
	}


	public double expire() { //Eliminamos el void y ponemos double porque nos daba error
		this.publicPrice.setValue(this.publicPrice.getValue()*EXPIRATION_RATE);
		return this.getPublicPrice().getValue();
		//Ponemos un return para que nos devuelva algo
	}

	@Override
	//Cranis este toString para que nos devuelva la información del producto
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", publicPrice=" + publicPrice + ", wholesalerPrice="
				+ wholesalerPrice + ", available=" + available + ", stock=" + stock +" ]";
	}   
}
