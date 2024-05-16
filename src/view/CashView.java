package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Shop;
import model.Amount;
import model.Product;
import model.Sale;

import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CashView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField shopCash;
	private Shop shop;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the dialog.
	 */
	public CashView() {
		this.shop= new Shop();
		setBounds(100, 100, 500, 343);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(108, 152, 179));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel contentPane = new JPanel();
			contentPane.setLayout(null);
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setBackground(new Color(173, 194, 209));
			contentPane.setBounds(0, 0, 486, 306);
			contentPanel.add(contentPane);
			{
				JTextPane txtCash = new JTextPane();
				txtCash.setBounds(95, 102, 141, 31);
				contentPane.add(txtCash);
				txtCash.setText("Dinero en caja: ");
				txtCash.setForeground(new Color(52, 103, 124));
				txtCash.setFont(new Font("Georgia Pro Cond", Font.PLAIN, 17));
				txtCash.setEditable(false);
				txtCash.setBackground(new Color(173, 194, 209));
			}
			{
				shopCash = new JTextField();
				shopCash.setEditable(false);
				shopCash.setHorizontalAlignment(SwingConstants.CENTER);
				shopCash.setBackground(new Color(255, 255, 255));
				shopCash.setFont(new Font("Georgia Pro Cond", Font.PLAIN, 18));
			//	shopCash.setText("100,00€");
				shopCash.setForeground(new Color(52, 103, 124));
				shopCash.setColumns(10);
				shopCash.setBounds(194, 143, 96, 31);
				contentPane.add(shopCash);
				//double cash = shop.getCash();
				//Para que el valor tenga 100 llamando al method
				shopCash.setText(String.valueOf(shop.getCash()+"€"));
				
			}
		}
	}

}
