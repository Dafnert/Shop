package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import model.Amount;
import model.Employee;
import model.Product;
import model.Sale;


public class DaoImplFile implements Dao{
	private ArrayList<Sale> sales;
	private ArrayList<Product> inventory;
	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disconnect()  {
		// TODO Auto-generated method stub
		
	}
	//we create the method getInventory for the file
	@Override
	public ArrayList<Product> getInventory()  {
		ArrayList<Product> files = new ArrayList<Product>();
		try {
			
	        File file = new File("Files/inputInventory.txt");
	        Scanner myReader = new Scanner(file);
	        while (myReader.hasNextLine()) {
	        	double wholesalerPrice = 0;
	   	        int stock = 0;
	   	        String name = " ";
	            String data = myReader.nextLine();
	            String[] part1 = data.split(";");
	            for (int i= 0; i < part1.length; i++) {
	                String part = part1[i];
	                String[] part2 = part.split(":");
	                switch (i) {
	                    case 0:
	                        name = part2[1];
	                        break;
	                    case 1:
	                        wholesalerPrice = Double.parseDouble(part2[1]);
	                        break;
	                    case 2:
	                        stock = Integer.parseInt(part2[1]);
	                        break;
	                    default:
	                        break;
	                }
	        
	            }
	            
	            Product product= new Product(name, new Amount(wholesalerPrice), true, stock,new Amount(wholesalerPrice*2));
	            files.add(product);
	        }
	        
	        myReader.close();
	    } catch (FileNotFoundException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	    }
		return files;
	}
	
	/**private void addProduct(Product product) {
		// TODO Auto-generated method stub
		
	}*/
	//we create the method writeInventory for the file write the products
	public boolean writeInventory(ArrayList<Product> inventory){
	    LocalDate date = LocalDate.now();
	    try {
	        File file = new File("files/inventory_" + date + ".txt"); 
	        FileWriter write = new FileWriter(file, true); 
	        PrintWriter print = new PrintWriter(write);
	        int counterInventory = 0;
	        
	        // Loop through the products and write them to the file
	        for (Product product : inventory) {
	            String line = product.getId() + ";" + "Product: " + product.getName() + ";" + "Stock: " + product.getStock();
	            print.println(line); // Write each product on a new line
	            counterInventory++;
	        }
	        
	        //Write the number total of products on the file
	        print.println("\nNúmero total de productos: " + counterInventory);
	        
	        print.close();
	        write.close();
    		}catch(IOException e) {
    			System.out.println("ERROR: " + e.getMessage());
    		e.printStackTrace();
    		}
	    return false;
	}

	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeProduct(int productId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		
	}
	

}
