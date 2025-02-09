package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Shop;
import model.Amount;
import model.Product;
import utils.Constants;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ProductView extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JTextPane txtNameProduct = new JTextPane();
	private JTextField name;
	private JTextField stock;
	private JTextField price;
	private Shop shop;
	private int option;
	private ArrayList<Product> inventory;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 */
	public ProductView(int option, Shop shop) {
		// Damos valores al option
		this.option = option;
		this.shop = shop;
		getContentPane().setBackground(new Color(173, 194, 209));
		setBounds(100, 100, 563, 388);
		getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();

			buttonPane.setBackground(new Color(173, 194, 209));
			buttonPane.setBounds(0, 318, 549, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("OK");
				okButton.setFont(new Font("Georgia Pro Cond", Font.PLAIN, 13));
				okButton.setForeground(new Color(108, 152, 179));
				okButton.setBackground(new Color(52, 103, 124));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// Para que se haga cuando le damos al button OK
						if (e.getActionCommand().equals("OK")) {
							// El segundo case es añadir producto
							if (option == Constants.OPTION_ADD_PRODUCT) {
								// Creamos las variables y les damos el valor a lo que ponga el usuario
								String nameProduct = name.getText();
								int stockProduct = Integer.parseInt(stock.getText());
								double priceProduct = Double.parseDouble(price.getText());
								// Llamos a la class Amount para ponerle el valor del precio que ponga al
								// usuario
								Amount amount = new Amount(priceProduct);
								// Llamamos a la class Product le damos el valor del buscar producto en la clase
								// shop
								Product product = shop.findProduct(nameProduct);

								// Si el producto no existe(null), enotnces se añadirá.
								if (product == null) {
									// Se añade el product
									Product products = new Product(nameProduct, amount, true, stockProduct,
											new Amount(amount.getValue() * 2));
									// shop.addProduct(new Product(nameProduct, amount, true, stockProduct, new
									// Amount(amount.getValue()*2)));
									products.setPrice(priceProduct);
									shop.addProduct(products);
									// Cuando se añade aparece el JOptionPane
									JOptionPane.showMessageDialog(null, "Producto creado y añadido en el inventario",
											"", JOptionPane.INFORMATION_MESSAGE);
									dispose();
								} else {
									// En el caso que el producto exita, entonces se verá el JOptionPane de que
									// "existe el Producto"
									JOptionPane.showMessageDialog(null, "Ya existe el producto", "Error",
											JOptionPane.ERROR_MESSAGE);
								}
							}
							// El case 3
							if (option == Constants.OPTION_ADD_STOCK) {
								// Creamos las variables y le damos el valor que le pondrá el user
								String nameProduct = name.getText();
								int stockProduct = Integer.parseInt(stock.getText());
								// Para que nos busque el nombre del product
								Product product = shop.findProduct(nameProduct);
								// Si el producto existe(no es null)
								if (product != null) {
									// Se le añade el stock que el user pone

									JOptionPane.showMessageDialog(null, "Stock actualizado", "",
											JOptionPane.INFORMATION_MESSAGE);
									shop.addStock(product, stockProduct);
									dispose();
								} else {
									// En el caso que no exista el producto entonces aparecerá el siguiente
									// JOptionPane
									JOptionPane.showMessageDialog(null,
											"No se ha encontrado el producto para añadir el stock", "Error",
											JOptionPane.ERROR_MESSAGE);
								}
							}
							// Case 9
							if (option == Constants.OPTION_REMOVE_PRODUCT) {
								// Creamos las variables y le damos el valor que le pondrá el user
								String nameProduct = name.getText();
								// Para que nos busque el nombre del product
								Product product = shop.findProduct(nameProduct);
								int productId = product.getId();
								// Si el producto existe(no es null)
								if (product != null) {
									// El invetory le damos el valor de que nos devuelva el inventario
									inventory = shop.getInventory();
									// Se eliminará el producto del inventario
									inventory.remove(product);
									shop.deleteProduct(product, productId);
									// Cuando se eliminé saldrá el siguiente JOptionPane
									JOptionPane.showMessageDialog(null, "Producto eliminado", "",
											JOptionPane.INFORMATION_MESSAGE);
									dispose();

								} else {
									// En el caso que no exista el producto entonces aparecerá el siguiente
									// JOptionPane
									JOptionPane.showMessageDialog(null, "El producto no se ha eliminado", "Error",
											JOptionPane.ERROR_MESSAGE);
								}
							}
							if (option == Constants.OPTION_EXPORT_INVENTORY) {
								try {
									shop.writeInventory(inventory);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								JOptionPane.showMessageDialog(null, "Export Inventory", "",
										JOptionPane.INFORMATION_MESSAGE);
								dispose();
							} else {
								JOptionPane.showMessageDialog(null, "	Fail export Inventory", "Error",
										JOptionPane.ERROR_MESSAGE);
							}
							dispose();
						}
					}
				});
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setForeground(new Color(108, 152, 179));
				cancelButton.setFont(new Font("Georgia Pro Cond", Font.PLAIN, 13));
				cancelButton.setBackground(new Color(52, 103, 124));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				// Cuando le de al button Cancel se salga de la pantalla
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						dispose();
					}
				});
			}
		}
		txtNameProduct.setBounds(77, 86, 134, 26);
		getContentPane().add(txtNameProduct);
		txtNameProduct.setText("Nombre Producto:");
		txtNameProduct.setForeground(new Color(52, 103, 124));
		txtNameProduct.setFont(new Font("Georgia Pro Cond", Font.PLAIN, 17));
		txtNameProduct.setEditable(false);
		txtNameProduct.setBackground(new Color(173, 194, 209));

		{
			JTextPane txtStockProduct = new JTextPane();
			txtStockProduct.setText("Stock Producto:");
			txtStockProduct.setForeground(new Color(52, 103, 124));
			txtStockProduct.setFont(new Font("Georgia Pro Cond", Font.PLAIN, 17));
			txtStockProduct.setEditable(false);
			txtStockProduct.setBackground(new Color(173, 194, 209));
			txtStockProduct.setBounds(77, 152, 117, 26);
			getContentPane().add(txtStockProduct);
			if (option == Constants.OPTION_REMOVE_PRODUCT) {
				txtStockProduct.setVisible(false);
			}
		}
		{
			JTextPane txtPriceProduct = new JTextPane();
			txtPriceProduct.setText("Precio Producto:");
			txtPriceProduct.setForeground(new Color(52, 103, 124));
			txtPriceProduct.setFont(new Font("Georgia Pro Cond", Font.PLAIN, 17));
			txtPriceProduct.setEditable(false);
			txtPriceProduct.setBackground(new Color(173, 194, 209));
			txtPriceProduct.setBounds(77, 214, 122, 26);
			getContentPane().add(txtPriceProduct);
			// Utilizamos el setVisible para que cuando el usuario le demos a la opción dos
			// no aparezca el contenedor del precio
			if (option == Constants.OPTION_ADD_STOCK) {
				txtPriceProduct.setVisible(false);

			}
			// Utilizamos el setVisible para que cuando el usuario le demos a la opción 9
			// no aparezca el contenedor del precio
			if (option == Constants.OPTION_REMOVE_PRODUCT) {
				txtPriceProduct.setVisible(false);
			}
		}
		{
			name = new JTextField();
			name.setHorizontalAlignment(SwingConstants.CENTER);
			name.setForeground(new Color(166, 192, 208));
			name.setColumns(10);
			name.setBounds(268, 74, 199, 38);
			getContentPane().add(name);
		}
		{
			stock = new JTextField();
			stock.setHorizontalAlignment(SwingConstants.CENTER);
			stock.setForeground(new Color(166, 192, 208));
			stock.setColumns(10);
			stock.setBounds(268, 140, 199, 38);
			getContentPane().add(stock);
			// Utilizamos el setVisible para que cuando el usuario le demos a la opción 9
			// no aparezca el contenedor del stock
			if (option == Constants.OPTION_REMOVE_PRODUCT) {
				stock.setVisible(false);
			}
		}
		{
			price = new JTextField();
			price.setHorizontalAlignment(SwingConstants.CENTER);
			price.setForeground(new Color(166, 192, 208));
			price.setColumns(10);
			price.setBounds(268, 202, 199, 38);
			getContentPane().add(price);
			// Utilizamos el setVisible para que cuando el usuario le demos a la opción dos
			// no aparezca el contenedor del precio
			if (option == Constants.OPTION_ADD_STOCK) {
				price.setVisible(false);

			}
			// Utilizamos el setVisible para que cuando el usuario le demos a la opción 9
			// no aparezca el contenedor del precio
			if (option == Constants.OPTION_REMOVE_PRODUCT) {
				price.setVisible(false);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		/**
		 * if(e.getSource()instanceof JButton) { if("Añadir Stock".equals(option)) {
		 * setTitle("Añadir Stock");
		 * 
		 * } if("Añadir Producto".equals(option)) { //productView.openProductView();
		 * setTitle("Añadir Producto");
		 * 
		 * } if("Añadir Stock".equals(option)) { setTitle("Añadir Stock");
		 * 
		 * } if("Eliminar Producto".equals(option)) { setTitle("Eliminar Producto");
		 * 
		 * } }
		 */

	}
}
