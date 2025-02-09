package model;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "historical_inventory")
public class ProductHistory {
	@Id
	@GeneratedValue
	private int id;
	@Column
	private boolean available = true;
	@Column
	private LocalDateTime created_at;
	@Column
	private int productId;
	@Column
	private String name;
	@Column
	private int stock;
	@Column
	private double price;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "ProductHistory [id=" + id + ", name=" + name + ", available=" + available + ", stock=" + stock
				+ ", price=" + price + ", created_at=" + created_at + "]";
	}
}
