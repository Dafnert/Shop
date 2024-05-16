package view;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Shop;
import model.Employee;
import utils.Constants;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\dafne\\OneDrive\\Escritorio\\3110602.png"));
		lblNewLabel.setBounds(41, 99, 249, 256);
		contentPane.add(lblNewLabel);
		
		JButton cajaContar = new JButton("1. Contar caja");
		cajaContar.setFont(new Font("Footlight MT Light", Font.PLAIN, 17));
		cajaContar.setForeground(new Color(54, 86, 105));
		cajaContar.setBackground(new Color(108, 152, 179));
		cajaContar.setBounds(389, 114, 174, 31);
		contentPane.add(cajaContar);
		cajaContar.addActionListener(this);
		cajaContar.setActionCommand("cajaContar");
			   
		
		JButton stockAdd = new JButton("3. Añadir Stock");
		stockAdd.setForeground(new Color(54, 86, 105));
		stockAdd.setFont(new Font("Footlight MT Light", Font.PLAIN, 17));
		stockAdd.setBackground(new Color(108, 152, 179));
		stockAdd.setBounds(389, 242, 174, 31);
		contentPane.add(stockAdd);
		stockAdd.addActionListener(this);
		stockAdd.setActionCommand("stockAdd");
		
		JButton productAdd = new JButton("2. Añadir Producto");
		productAdd.setForeground(new Color(54, 86, 105));
		productAdd.setFont(new Font("Footlight MT Light", Font.PLAIN, 17));
		productAdd.setBackground(new Color(108, 152, 179));
		productAdd.setBounds(389, 177, 174, 31);
		contentPane.add(productAdd);
		productAdd.addActionListener(this);
		productAdd.setActionCommand("productAdd");
		
		JButton productDelete = new JButton("9. Eliminar Producto");
		productDelete.setForeground(new Color(54, 86, 105));
		productDelete.setFont(new Font("Footlight MT Light", Font.PLAIN, 17));
		productDelete.setBackground(new Color(108, 152, 179));
		productDelete.setBounds(389, 305, 174, 31);
		contentPane.add(productDelete);
		productDelete.addActionListener(this);
		productDelete.setActionCommand("productDelete");
		
		textField = new JTextField();
		textField.setBackground(new Color(52, 103, 124));
		textField.setEditable(false);
		textField.setBounds(0, 0, 612, 74);
		contentPane.add(textField);
		textField.setColumns(10);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	//Creamos el menú para las teclas
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		//Cuando el user tecleé un número se habrá al ventana que quiere
          if (e.getKeyChar()== KeyEvent.VK_1){
        	  this.openCashView();
          }
          if (e.getKeyChar()== KeyEvent.VK_2){
        	  setTitle("Añadir Producto");
        	  this.openProductView(2, shop);
          }
          if(e.getKeyChar()== KeyEvent.VK_3) {
        	  setTitle("Añadir Stock");
        	  this.openProductView(3, shop);
          }
          if(e.getKeyChar()== KeyEvent.VK_9) {
				setTitle("Eliminar Producto");
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
	                setTitle("Añadir Producto");
	                openProductView(2,shop);
	                break;
	            case "stockAdd":
	            	setTitle("Añadir Stock");
	            	openProductView(3,shop);
	            	break;
	            case "productDelete":
	                setTitle("Eliminar Producto");
	                openProductView(9,shop);
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
	
	
}
