package gui;


import java.awt.Color;
import java.awt.EventQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.User;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class RegisterGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField1;
	private JButton createUserJButton;

	private BLFacade facade;
	private JFormattedTextField numTarjeta;
	private JLabel errorLabel;
	private JLabel errorLabel2;
	private JButton closeBtn;
	private JTextField correoTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
					//					frame = new BLFacadeImplementation(); 
					frame.setVisible(true);
					//					facade= MainGUI.getBusinessLogic();
					//					frame.pack();
					frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setBusinessLogic(BLFacade b) {
		facade=b;
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	/**
	 * Create the frame.
	 */
	public RegisterGUI() {
		setTitle("Register");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setBusinessLogic(MainGUI.getBusinessLogic());

		JLabel lblNewLabel = new JLabel("Nombre de Usuario:");
		lblNewLabel.setBounds(10, 10, 155, 21);
		contentPane.add(lblNewLabel);

		JLabel lblContrasea = new JLabel("Introducir Password:");
		lblContrasea.setBounds(10, 41, 155, 21);
		contentPane.add(lblContrasea);

		textField = new JTextField();
		textField.setBounds(191, 9, 210, 24);
		contentPane.add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(191, 40, 210, 24);
		contentPane.add(passwordField);

		createUserJButton = new JButton("Crear cuenta");
		createUserJButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) /*throws NullPointerException*/{
				String pwd= new String(passwordField.getPassword());
				String pwd1= new String(passwordField1.getPassword());
				String usuario= textField.getText();
				String nTarjeta= numTarjeta.getText();
				String correo= correoTextField.getText();
				if(pwd.equals(pwd1) && pwd.length()>0 && pwd1.length()>0 && !facade.userExist(usuario) && nTarjeta.length()==16 && !facade.tarjetaExist(nTarjeta)) {
					String user= facade.anadirUsuario(usuario, pwd, nTarjeta, correo); // Mandar el nombre del usuario y la contraseña
					System.out.println("El usuario: "+ user + " se ha añadido.");
					jButtonClose_actionPerformed(e);
					JFrame a= new LoginGUI();
					a.setVisible(true);
				}else if(facade.userExist(usuario)) {
					errorLabel.setText("Ya existe un usuario de nombre '" + usuario + "'.");
				}
				else if(pwd.length()==0 || pwd1.length()==0) {
					errorLabel.setText("Rellena todos los campos antes de continuar.");
				}else if(nTarjeta.length()!=16) {
					errorLabel.setText("El número de la tarjete debe contener 16 numeros.");
				}else if(facade.tarjetaExist(nTarjeta)) {
					errorLabel.setText("Ya existe un user con dicha tarjeta.");
					errorLabel2.setText("Introduce tu verdadero número de tarjeta por favor.");
				}else {
					errorLabel.setText("Las password son diferentes.");
				}
			}
		});

		createUserJButton.setBounds(34, 214, 168, 39);
		contentPane.add(createUserJButton);

		passwordField1 = new JPasswordField();
		passwordField1.setBounds(191, 74, 210, 24);
		contentPane.add(passwordField1);

		JLabel lblRepetirPassword = new JLabel("Repetir Password:");
		lblRepetirPassword.setBounds(10, 75, 155, 21);
		contentPane.add(lblRepetirPassword);
		
		JLabel lblIntroducirNmeroDe = new JLabel("Introducir n\u00FAmero de tarjeta:");
		lblIntroducirNmeroDe.setBounds(10, 109, 171, 21);
		contentPane.add(lblIntroducirNmeroDe);
		
		numTarjeta = new JFormattedTextField();
		numTarjeta.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();
		        if (!((c >= '0') && (c <= '9') ||
		           (c == KeyEvent.VK_BACK_SPACE) ||
		           (c == KeyEvent.VK_DELETE))) {
		          getToolkit().beep();
		          e.consume();
		        }
		      }
		    });
		numTarjeta.setBounds(191, 108, 210, 24);
		contentPane.add(numTarjeta);
		
		errorLabel = new JLabel("", SwingConstants.CENTER);
		errorLabel.setBounds(68, 168, 294, 14);
		errorLabel.setForeground(Color.RED);
		contentPane.add(errorLabel);
		
		errorLabel2 = new JLabel("", SwingConstants.CENTER);
		errorLabel2.setForeground(Color.RED);
		errorLabel2.setBounds(68, 192, 294, 14);
		contentPane.add(errorLabel2);
		
		closeBtn = new JButton("Cerrar");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		closeBtn.setBounds(234, 214, 168, 39);
		contentPane.add(closeBtn);
		
		JLabel correoLbl = new JLabel("Introducir correo electronico:");
		correoLbl.setBounds(10, 151, 171, 13);
		contentPane.add(correoLbl);
		
		correoTextField = new JTextField();
		correoTextField.setColumns(10);
		correoTextField.setBounds(191, 142, 210, 24);
		contentPane.add(correoTextField);
//setBounds(191, 134, 210, 24);

	}
}
