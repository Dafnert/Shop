package view;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Shop;
import model.Employee;
import model.Product;
import utils.Constants;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;

public class ShopView extends JFrame implements KeyListener, ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private Shop shop;
	ArrayList <Product> products;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShopView frame = new ShopView();
					frame.setVisible(true);
					frame.setTitle("Shop View");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ShopView() {
		
		this.shop = new Shop();
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		

			shop.loadInventory();
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 626, 474);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(173, 194, 209));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane txtpnSeleccioneOPulse = new JTextPane();
		txtpnSeleccioneOPulse.setText("Seleccione o pulse una opción: ");
		txtpnSeleccioneOPulse.setForeground(new Color(108, 152, 179));
		txtpnSeleccioneOPulse.setFont(new Font("Georgia Pro Cond", Font.PLAIN, 20));
		txtpnSeleccioneOPulse.setEditable(false);
		txtpnSeleccioneOPulse.setBackground(new Color(52, 103, 124));
		txtpnSeleccioneOPulse.setBounds(72, 23, 257, 31);
		contentPane.add(txtpnSeleccioneOPulse);
		
		JLabel lblNewLabel = new JLabel("");
		//lblNewLabel.setIcon(new ImageIcon("C:\\Users\\dafne\\OneDrive\\Escritorio\\3110602.png"));
		lblNewLabel.setBounds(41, 99, 249, 256);
		contentPane.add(lblNewLabel);
		
		JButton inventory = new JButton("0. Export inventory");
		inventory.setFont(new Font("Footlight MT Light", Font.PLAIN, 17));
		inventory.setForeground(new Color(54, 86, 105));
		inventory.setBackground(new Color(108, 152, 179));
		inventory.setBounds(399, 154, 174, 31);
		contentPane.add(inventory);
		inventory.addActionListener(this);
		inventory.setActionCommand("inventory");
			   
		
		JButton stockAdd = new JButton("3. Add Stock");
		stockAdd.setForeground(new Color(54, 86, 105));
		stockAdd.setFont(new Font("Footlight MT Light", Font.PLAIN, 17));
		stockAdd.setBackground(new Color(108, 152, 179));
		stockAdd.setBounds(399, 277, 174, 31);
		contentPane.add(stockAdd);
		stockAdd.addActionListener(this);
		stockAdd.setActionCommand("stockAdd");
		
		JButton productAdd = new JButton("2. Add Product");
		productAdd.setForeground(new Color(54, 86, 105));
		productAdd.setFont(new Font("Footlight MT Light", Font.PLAIN, 17));
		productAdd.setBackground(new Color(108, 152, 179));
		productAdd.setBounds(399, 236, 174, 31);
		contentPane.add(productAdd);
		productAdd.addActionListener(this);
		productAdd.setActionCommand("productAdd");
		
		JButton productDelete = new JButton("9. Delete Product");
		productDelete.setForeground(new Color(54, 86, 105));
		productDelete.setFont(new Font("Footlight MT Light", Font.PLAIN, 17));
		productDelete.setBackground(new Color(108, 152, 179));
		productDelete.setBounds(399, 318, 174, 31);
		contentPane.add(productDelete);
		productDelete.addActionListener(this);
		productDelete.setActionCommand("productDelete");
		
		textField = new JTextField();
		textField.setBackground(new Color(52, 103, 124));
		textField.setEditable(false);
		textField.setBounds(0, 0, 612, 74);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton cajaContar_1 = new JButton("1. Count Cash");
		cajaContar_1.setForeground(new Color(54, 86, 105));
		cajaContar_1.setFont(new Font("Footlight MT Light", Font.PLAIN, 17));
		cajaContar_1.setBackground(new Color(108, 152, 179));
		cajaContar_1.setActionCommand("cajaContar");
		cajaContar_1.setBounds(399, 195, 174, 31);
		contentPane.add(cajaContar_1);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	//Creamos el menú para las teclas
	public void keyPressed(KeyEvent e) {
		//Cuando el user tecleé un número se habrá al ventana que quiere
		 if (e.getKeyCode()== KeyEvent.VK_0){
			 try {
		           shop.writeInventory(products);
		            JOptionPane.showMessageDialog(this, "Inventario exportado correctamente.", "Confirmaci�n", JOptionPane.INFORMATION_MESSAGE);
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(this, "Error al exportar inventario.", "Error", JOptionPane.ERROR_MESSAGE);
		        }
         }  
		if (e.getKeyChar()== KeyEvent.VK_1){
        	  this.openCashView();
          }
          if (e.getKeyChar()== KeyEvent.VK_2){
        	  setTitle("Add Product");
        	  this.openProductView(2, shop);
          }
          if(e.getKeyChar()== KeyEvent.VK_3) {
        	  setTitle("Add Stock");
        	  this.openProductView(3, shop);
          }
          if(e.getKeyChar()== KeyEvent.VK_9) {
				setTitle("Delete Product");
				 this.openProductView(9, shop);
          }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	//Creamos el menú oara que cuando clique el usuario pueda ver el menú
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 String comando = e.getActionCommand();
	        switch (comando) {
	        
	            case "cajaContar":
	                openCashView();
	                break;
	            case "productAdd":
	                setTitle("Add Product");
	                openProductView(2,shop);
	                break;
	            case "stockAdd":
	            	setTitle("Add Stock");
	            	openProductView(3,shop);
	            	break;
	            case "productDelete":
	                setTitle("Delete Product");
	                openProductView(9,shop);
	                break;
	            case "inventory":
	            	try {
	            	    shop.writeInventory(products);
	            	    JOptionPane.showMessageDialog(null, "Inventario exportado correctamente", "", JOptionPane.INFORMATION_MESSAGE);
	            	} catch (IOException e1) {
	            	    JOptionPane.showMessageDialog(null, "Error al exportar el inventario: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            	}

	                break;
	        }	
	}
	//Creamos un método para que se habrá al ventana de cashView
	public void openCashView() {
		CashView cashView = new CashView();
		cashView.setVisible(true);
		
	}
	//Creamos un método para que se habrá al ventana de productView
	public void openProductView(int option, Shop shop) {
		ProductView productView = new ProductView(option, shop);
		productView.setVisible(true);
	}
	public void openFile(int option, Shop shop, ArrayList <Product> products) throws IOException {
		shop.writeInventory(products);

	}
}
