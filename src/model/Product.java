package model;
import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@Entity
@Table(name="Product")
@XmlRootElement(name="product")
@XmlType(propOrder = {"totalProducts", "name","available", "wholesalerPrice","publicPrice", "stock"}) 
public class Product {
	@Id
	@GeneratedValue
	private int id;
	@Column
    private String name;
    @Transient
    private Amount publicPrice;
    @Transient
    private Amount wholesalerPrice;
    @Column
    private boolean available = true;
    @Column
    private int stock;
    private static int totalProducts;
    @Column
    private double price;
    

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
		super();
		this.id =++totalProducts;
	
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
		this.publicPrice = new Amount(wholesalerPrice.getValue()*2);
	}
	@XmlElement(name="available")
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

	public int getTotalProducts() {
		return totalProducts;
	}

	public void setTotalProducts(int totalProducts) {
		Product.totalProducts = totalProducts;
	}
	  public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
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
