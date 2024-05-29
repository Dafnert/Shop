package view;
import java.awt.EventQueue;
import javax.swing.JOptionPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import exception.LimitLoginException;
import model.Employee;
import utils.Constants;

import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class LoginView extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField password;
	private JTextField numeroEmpleado;
	private JTextPane txtNumEmpleado;
	private JLabel lblNewLabel;
	private int attempts =0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView frame = new LoginView();
					frame.setVisible(true);
					frame.setTitle("Login");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 */
	public LoginView() {
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 675, 424);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(173, 194, 209));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		password = new JPasswordField();
		password.setBounds(361, 184, 199, 38);
		contentPane.add(password);
		password.setColumns(10);
		
		JButton acceder = new JButton("Acceder");
		this.dispose();
		acceder.setVisible(true);
		acceder.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	//Llamamos a la class Employee
		    	Employee employee = new Employee();
		    	//Creamos variables con el valor que le ponga el usuario para luego saber si esta bien o no
		        int numberEmployee = Integer.parseInt(numeroEmpleado.getText());
		        String pass = password.getText();
		        
		        try {
					if (employee.login(numberEmployee, pass)) {
					    ShopView shopView = new ShopView(); // Crear un nuevo JFrame para la tienda
					    shopView.setVisible(true);
					    dispose(); // Cerrar el formulario de inicio de sesión
					} else {
						//Contamos los intentos 
					    attempts++;
					    //Si supera más de 3 intentos saldrá el mensaje del JOptionPane
					    if (attempts >= Constants.MAX_LOGIN_TIMES) {
					        JOptionPane.showMessageDialog(null,
					                "Error login, superados los 3 intentos",
					                "Error",
					                JOptionPane.ERROR_MESSAGE);
					        dispose();
					    } else {
					    	//Para que el usuario sepa que tiene algún dato mal
					        JOptionPane.showMessageDialog(null,
					                "Usuario o contraseña incorrectos, intento " + attempts,
					                "Error",
					                JOptionPane.ERROR_MESSAGE);
					    }
					    //Ponemos el método para que se limpien los campos del form
					    limpiarCampo();
					    }
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println(e1.getMessage());
					e1.printStackTrace();
				}
	                
		    	
		    }
		});

		
			
		acceder.setForeground(new Color(54, 86, 105));
		acceder.setFont(new Font("Footlight MT Light", Font.PLAIN, 17));
		acceder.setBackground(new Color(108, 152, 179));
		acceder.setBounds(460, 258, 100, 25);
		contentPane.add(acceder);
		
		numeroEmpleado = new JTextField();
		numeroEmpleado.setForeground(new Color(166, 192, 208));
		numeroEmpleado.setColumns(10);
		numeroEmpleado.setBounds(361, 113, 199, 38);
		contentPane.add(numeroEmpleado);
		
		JTextPane password = new JTextPane();
		password.setEditable(false);
		password.setFont(new Font("Georgia Pro Cond", Font.PLAIN, 17));
		password.setText("Password:");
		password.setBackground(new Color(173, 194, 209));
		password.setForeground(new Color(52, 103, 124));
		password.setBounds(361, 161, 78, 25);
		contentPane.add(password);
		limpiarCampo();
		
		txtNumEmpleado = new JTextPane();
		txtNumEmpleado.setEditable(false);
		txtNumEmpleado.setText("Número de empleado:");
		txtNumEmpleado.setForeground(new Color(52, 103, 124));
		txtNumEmpleado.setFont(new Font("Georgia Pro Cond", Font.PLAIN, 17));
		txtNumEmpleado.setBackground(new Color(173, 194, 209));
		txtNumEmpleado.setBounds(361, 87, 173, 25);
		contentPane.add(txtNumEmpleado);
		
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setForeground(new Color(53, 103, 124));
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\dafne\\OneDrive\\Escritorio\\log.png"));
		lblNewLabel.setBounds(70, 87, 199, 208);
		contentPane.add(lblNewLabel);
	}
//Implementa el método de la interface actionPerfomed
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	//Creamos este método para que cuando se vuelva hacer el login, el formulario este en blanco
	public void limpiarCampo() {
		password.setText("");
		numeroEmpleado.setText("");
		
	}

}
