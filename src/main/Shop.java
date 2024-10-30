package main;

import model.Amount;
import model.Client;
import model.Employee;
import model.Product;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime; // Para poder usar la fecha y la hora
import java.time.format.DateTimeFormatter; //Para poder modificar la fecha y se muestre como queremos
import java.awt.Component;
import java.io.File;//Para los archivos
import java.io.FileNotFoundException; //Para que nos diga si hay un error a la hora de crear un File
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.io.FileReader;
import java.io.FileWriter;

import model.Sale;
import java.util.Scanner;

import dao.Dao;
import dao.DaoImplFile;
import dao.DaoImplXml;
// COPIA
public class Shop {
	private double cash = 100.00;
	//private DaoImplFile dao;
	private ArrayList<Product> inventory; //La arraylist<la clase>
	private int numberProducts;
	//private DaoImplFile dao=new DaoImplFile();
	private DaoImplXml dao=new DaoImplXml();
	//private Sale[] sales = new Sale[10]; --> Creamos una array para las ventas 
	private ArrayList<Sale> sales; //Lo pasamos a una ArrayList 1.2
	//Wholesalerprice --> el price al que compra los productos al distribuidor
	
	final static double TAX_RATE = 1.04; //Discount
	//Product[] products = new Product[];
	ArrayList <String> products = new ArrayList<String>();  // Creamos esta array para tener 10 posiciones

	public Shop() {
		setCash(100.0);
		inventory = new ArrayList<Product>();//La arraylist<la clase>
		sales = new ArrayList<Sale>();
		//Assignar a atributo dao un objeto del constructor DaoImplFile
		
	}

	public static void main(String[] args) throws SQLException, IOException {
		
		Employee employee = new Employee(); //Crear un objeto
		Shop shop = new Shop();
		
		shop.loadInventory();

		Scanner scanner = new Scanner(System.in);
		int opcion = 0;
		boolean exit = false;
		//Dao dao = new Dao();
	//LLamamos la método
		shop.initSession();
		
		do {
			System.out.println("\n");
			System.out.println("===========================");
			System.out.println("Menu principal miTienda.com");
			System.out.println("===========================");
			System.out.println("1) Contar caja");
			System.out.println("2) Añadir producto");
			System.out.println("3) Añadir stock");
			System.out.println("4) Marcar producto proxima caducidad");
			System.out.println("5) Ver inventario");
			System.out.println("6) Venta");
			System.out.println("7) Ver ventas");
			System.out.println("8) Eliminar producto"); //Añadimos el caso 8 (2).
			System.out.println("9) Ver las ventas totales"); //Añadimos un sysout para el caso 9
			System.out.println("10) Salir programa");
			System.out.print("Seleccione una opción: ");
			opcion = scanner.nextInt();

			switch (opcion) {
			case 1:
				shop.showCash();
				break;

			case 2:
				shop.addProduct();
				break;

			case 3:
				shop.addStock();
				break;

			case 4:
				shop.setExpired();
				break;

			case 5:
				shop.showInventory();
				break;

			case 6:
				shop.sale();
				
				break;

			case 7:
				shop.showSales();
				break;
			case 8: //Añadimos el caso (2)
				shop.deleteProduct();
			case 9:
				shop.totalSale(); //Llamamos al método
				break;
			case 10:
				System.out.println("You exit the program");
				exit = true;
				break;
				
			}

		} while (!exit);

	}
//Creamos este metodo para que cuando el usuario haga bien el nñumero y la contraseña pueda ver el login.
	public void initSession() throws SQLException {
		Employee employee = new Employee();
		Scanner scanner = new Scanner(System.in);
		int user = 0;
		String password = "";
		
		do {
		boolean again = false;
		System.out.println("Introduzca número de empleado: ");
		user = scanner.nextInt();
		System.out.println("Introduzca contraseña:");
		password = scanner.next();
		}while(!employee.login(user, password));
		System.out.println("Login correcto");
	
	
}

	/**
	 * load initial inventory to shop
	 */
	public void loadInventory() {	
		this.inventory=dao.getInventory();
		System.out.println(inventory.toString());
		
		/* try {
	        File file = new File("Files/inputInventory.txt");
	        Scanner myReader = new Scanner(file);
	        double wholesalerPrice = 0;
	        int stock = 0;
	        String name = " ";
	        while (myReader.hasNextLine()) {
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
	            Amount amount = new Amount(wholesalerPrice);
	            Product product = new Product(name, amount, true, stock, new Amount(amount.getValue() * 2));
	            addProduct(product);
	        }
	        
	        myReader.close();
	    } catch (FileNotFoundException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	    }*/
	}
		
	
//		addProduct(new Product("Manzana", 10.00, true, 10, 20.00));
//		addProduct(new Product("Pera", 20.00, true, 20, 40.00));
//		addProduct(new Product("Hamburguesa", 30.00, true, 30, 60.00));
//		addProduct(new Product("Fresa", 5.00, true, 20, 10.00));
		
	

	/**
	 * show current total cash
	 */
	public void showCash() {
		System.out.println("Dinero actual: " + getCash() );
	}

	/**
	 * add a new product to inventory getting data from console
	 */
	public void addProduct() {
		if (isInventoryFull()) {
			System.out.println("No se pueden añadir más productos");
			return;
		}
		Scanner scanner = new Scanner(System.in);
		System.out.print("Nombre: ");
		String name = scanner.nextLine();
		System.out.print("Precio mayorista: ");
		double wholesalerPrice = scanner.nextDouble();
		System.out.print("Stock: ");
		int stock = scanner.nextInt();
		Amount amount = new Amount(wholesalerPrice); //Llamar a la clase Amount para poder hacer uso del double wholesalerPrice 
		addProduct(new Product(name, amount, true, stock, new Amount(amount.getValue()*2))); //WholersalerPrice se multiplica por 2 porque el publicPrice es el doble.
		//Cambiamos el wholesalerPrice por el amount
		
	}
	public void deleteProduct() { //Creamos el método para eliminar el producto
		System.out.println("Introduzca el nombre del producto que quiera eliminar");
		Scanner scanner = new Scanner(System.in);
		System.out.print("Nombre: ");
		String name = scanner.nextLine();
		Product product  = findProduct(name);
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) != null && inventory.get(i).getName().equalsIgnoreCase(name)) { //Para que sea non Key Sensitive(3)
				inventory.remove(product);
				System.out.println("Se ha eliminado el producto: " + name);
			}
		}

	}
	/**
	 * add stock for a specific product
	 */
	public void addStock() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Seleccione un nombre de producto: ");
		String name = scanner.next();
		Product product = findProduct(name);

		if (product != null) {
			// ask for stock
			System.out.print("Seleccione la cantidad a añadir: ");
			int stock = scanner.nextInt();
			// update stock product
			product.setStock(product.getStock() + stock); //Suma la cantidad que añade al producto (4)
			System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());

		} else {
			System.out.println("No se ha encontrado el producto con nombre " + name);
		}
	}

	/**
	 * set a product as expired
	 */
	private void setExpired() {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Seleccione un nombre de producto: ");
		String name = scanner.next();
		Product product = findProduct(name);
		//Amount amount = new Amount();
		if (product != null) {
			double expire =0.0;
			
			expire = product.getPublicPrice().getValue()-product.expire(); //Añadimos el método product.expire() para la operación
			
			System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getPublicPrice().getValue());
		}else {
			System.out.println("No se ha actualizado " + name);
		}
	}

	/**
	 * show all inventory
	 */
	public void showInventory() {
		System.out.println("Contenido actual de la tienda:");
		for (Product product : inventory) { //Cambiamos un String por la clase Product
			if (product != null) {
				System.out.println(product.toString()); //Le ponemos  .toString para que nos de toda la infromación
			}
		}
	}

	/**
	 * make a sale of products to a client
	 */
	public void sale() {
		// ask for client name
		Scanner sc = new Scanner(System.in);
		System.out.println("Realizar venta, escribir nombre cliente");
		String client = sc.nextLine();
		//Product [] products = new Product[10]; //Creamos el objeto products
		ArrayList <Product> products = new ArrayList<Product>();  //Pasamos la array products a un ArrayList
		//Creamos las varibles para que nos sirvan de contadores
		LocalDateTime almacenarFecha = LocalDateTime.now(); // Creamos el objeto para poder usarlo
		DateTimeFormatter modificarFecha = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss"); //Creamos el objeto para poder modificar el formato.
		String almacenarFechas = almacenarFecha.format(modificarFecha); //Para que se cambie el formato.
		//Creamos el objeto client para verificar el número del cliente
		Client numberClient = new Client(client);
		int sale= 0;
		int sale2=0;
		// sale product until input name is not 0
		double totalAmount = 0.0;
		String name = "";
		while (!name.equals("0")) {
			System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
			name = sc.nextLine();

			if (name.equals("0")) {
				break;
			}
			Product product = findProduct(name);
			boolean productAvailable = false;
			if (product != null && product.isAvailable()) {
				productAvailable = true;
				totalAmount += product.getPublicPrice().getValue();
				product.setStock(product.getStock() - 1);
				// if no more stock, set as not available to sale
				if (product.getStock() == 0) {
					product.setAvailable(false);
				}
				System.out.println("Producto añadido con éxito");
				//products[sale] = product; //El objeto products le damos el valor de sale para que nos cuente las ventas
				//sale ++; 
				//Modificamos lo de arriba para que nos funcione la arryaList
				products.add(product);
			}

			if (!productAvailable) {
				System.out.println("Producto no encontrado o sin stock");
			}
		}

		// show cost total
		totalAmount = totalAmount * TAX_RATE;
		setCash(getCash() + totalAmount);
		System.out.println("Venta realizada con éxito, total: " + totalAmount); // Nos contará las ventas
		//Creamos un objeto
		Amount amount = new Amount(totalAmount);
		//Declaramos un boolean
		boolean amounts;
		//La usamos para que nos ayude hacer uso del metodo pay y también la operación
		amounts= numberClient.pay(amount); //Llamamos al metodo pay
		if(!amounts) {
			//Saldrá el input cuando el cliente deba dinero, se sobrepase de los 50€
			double deber = numberClient.getBalance().getValue()-amount.getValue();
			System.out.println("Cliente deber:"+deber);
		}
		
		
		//sales[sale2] = new Sale(client, products, totalAmount, almacenarFechas); // Para el toString de sale
		//sale2 ++;
		sales.add(new Sale(client, products, new Amount(amount.getValue()), almacenarFechas));
		
	}	

	/**
	 * show all sales
	 * @throws IOException 
	 */
	private void showSales() throws IOException {
		//System.out.println(inventory.toString());
		dao.writeInventory(inventory);
		
		//LocalDate date = LocalDate.now(); //yyyy-MM-dd
        /**System.out.println("Lista de ventas:");
      
        Scanner scanner = new Scanner(System.in);
        System.out.println("Quieres crear un fichero?\n" + 
        "1)Sí\n"+
        		"2)No\n");
        int options = scanner.nextInt();
        switch(options) {//Bucle switch para darle al usuario la opción de hacer un fichero escrito o no
        case 1:
    		try {
    		File files = new File("files/sales_"+date+".txt"); //Hacemos la ruta del fichero
    		FileWriter write = new FileWriter(files,true);
    		PrintWriter print = new PrintWriter(write);
    		int counterSale = 0;
    		for (Sale sale : sales) {
    			
    			StringBuilder line = new StringBuilder(counterSale+"Client: "+sales.get(0).getClient()+"; Date="+sales.get(0).getAlmacenarFechas()+"\n");
    			print.write(line.toString());
    			write.write("\n");
    			
    			StringBuilder product = new StringBuilder();
    				
    			for (Product products : sale.getProducts()) {
    				
    				product.append(products.getName()+","+products.getPublicPrice());
    				
    			}
    			StringBuilder seconLine = new StringBuilder(counterSale+";"+"Product: "+product);
    			
    			print.write(seconLine.toString());
    			write.write("\n");
    			
    			StringBuilder amount = new StringBuilder(counterSale+";"+"Amount: "+sales.get(0).getAmount()+"€");
    			print.write(amount.toString());
    			write.write("\n");
    			}
    			
    			print.close();
    			write.close();
    		}catch(IOException e) {
    			System.out.println("ERROR");
    		e.printStackTrace();
    		}
        break;
        case 2:
        	System.out.println("Lista de ventas:");
        	 for (Sale sale : sales) {
                 if (sale != null) { 
                 }
                 System.out.println(sale.toString());
             }
        	break;
        default:
        	System.out.println("Not options");
        	break;
        }*/
       }
	
	private void totalSale() {
		double totalAmount = 0.0;
		Amount amount = new Amount(totalAmount);
		for(Sale sale : sales) {
			if (sale != null) {
				totalAmount+=sale.getAmount().getValue(); // Para que vaya cotando el total de las ventas
			}
		}
		System.out.println("Ventas totales: " + totalAmount);
	}
	
	


	/**
	 * add a product to inventory
	 * 
	 * @param product
	 */
	//	private ArrayList<String> inventory;
	public void addProduct(Product product) {
		if (isInventoryFull()) {
			System.out.println("No se pueden añadir más productos, se ha alcanzado el máximo de " + inventory.size());
			return;
		}
		//	inventory[numberProducts] = product;
		// numberProducts++;
		//El cambio para el 1.1
		inventory.add(product);
		numberProducts++;
	}

	/**
	 * check if inventory is full or not
	 */
	public boolean isInventoryFull() {
		if (numberProducts >= 10) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * find product by name
	 * 
	 * @param product name
	 */
	//Cambiamos el length por el .get(i)
	public Product findProduct(String name) {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) != null && inventory.get(i).getName().equalsIgnoreCase(name)) { //Para que sea non Key Sensitive(3)
				return inventory.get(i);
			}
		}
		return null;

	}
	//Nos devuelva el valor del cash
	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}
	//Nos devuelva el inventorio
	public ArrayList<Product> getInventory(){
		return inventory;
	};
	public ArrayList<Product> setInventory(ArrayList<Product> arrayList) {
		return inventory;
	};
	  // Get dao 
    public DaoImplXml getDao() {
        return dao;
    }
    
    // Set dao 
    public void setDao(DaoImplXml dao) {
        this.dao = dao;
    }
    public boolean writeInventory(ArrayList<Product> products)throws IOException{
    	
    	return dao.writeInventory(inventory);
		
    }
    public void readInventrory() {
    	this.setInventory(dao.getInventory());
    }


}