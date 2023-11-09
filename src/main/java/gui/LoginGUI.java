package gui;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.User;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	
	private BLFacade facade;
	private JButton btnNewButton;
	private JButton closeButton;
	private JLabel errorLabel;
	private JRadioButton rdbtnNewRadioButton;
	private JLabel errorLbl2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
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
	public LoginGUI() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setBusinessLogic(MainGUI.getBusinessLogic());
		
		JLabel lblNewLabel = new JLabel("Nombre de Usuario:");
		lblNewLabel.setBounds(34, 51, 127, 21);
		contentPane.add(lblNewLabel);
		
		JLabel lblContrasea = new JLabel("Introducir Password:");
		lblContrasea.setBounds(34, 91, 127, 21);
		contentPane.add(lblContrasea);
		
		textField = new JTextField();
		textField.setBounds(193, 50, 210, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(193, 90, 210, 24);
		contentPane.add(passwordField);
		
		btnNewButton = new JButton("Entrar a la cuenta");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) /*throws NullPointerException*/{
				String pwd= new String(passwordField.getPassword());
				String usuario= textField.getText();
				if(pwd.length()==0) {
					errorLabel.setText("Rellena todos los campos antes de continuar.");
				}else if(facade.userExist(usuario) && !facade.getUserByName(usuario).getVeto()) {
					User user= facade.getUserByName(usuario); // Mandar el nombre del usuario y la contrase�a
					if(user.getPassword().equals(pwd)) {
						if(user.isAdmin()) {
							JFrame a= new AdminGUI(usuario);
							a.setVisible(true);
						}else {
							JFrame a= new UserGUI(usuario);
							a.setVisible(true);
						}
						jButtonClose_actionPerformed(e);
					}else {
						errorLabel.setText("La password "+ pwd +" es incorrecta.");
					}
				}else if(!facade.userExist(usuario)) {
					errorLabel.setText("No existe un usuario de nombre '" + usuario + "'");
				}else {
					errorLabel.setText("El usuario '" + usuario + "'" + " ha sido vetado.");
					errorLbl2.setText("Revise el motivo del veto en su correo.");
				}
			}
		});
		btnNewButton.setBounds(44, 195, 168, 39);
		contentPane.add(btnNewButton);
		
		closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		closeButton.setBounds(235, 195, 168, 39);
		contentPane.add(closeButton);
		
		errorLabel = new JLabel("", SwingConstants.CENTER);
		errorLabel.setForeground(Color.RED);
		errorLabel.setBounds(65, 151, 313, 13);
		contentPane.add(errorLabel);
		
		rdbtnNewRadioButton = new JRadioButton("�Has olvidado la contrase�a?");
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new PasswordOlvidadaGUI();
				a.setVisible(true);
				jButtonClose_actionPerformed(e);
			}
		});
		rdbtnNewRadioButton.setBounds(92, 124, 289, 21);
		contentPane.add(rdbtnNewRadioButton);
		
		errorLbl2 = new JLabel("", SwingConstants.CENTER);
		errorLbl2.setForeground(Color.RED);
		errorLbl2.setBounds(44, 174, 334, 13);
		contentPane.add(errorLbl2);
	}
}
