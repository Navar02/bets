package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class CambiarPasswordGUI extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JButton changePasswordBtn;
	private JButton closeBtn;
	private BLFacade facade;
	private JLabel errorLbl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, boolean admin) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CambiarPasswordGUI frame = new CambiarPasswordGUI(args[0], admin);
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
	

	/**
	 * Create the frame.
	 */
	public CambiarPasswordGUI(String userName, boolean admin) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setTitle("Cambiar Contrase�a");
		
		setBusinessLogic(MainGUI.getBusinessLogic());
		
		JLabel titlelbl = new JLabel("Cambiar Contrase�a", SwingConstants.CENTER);
		titlelbl.setFont(new Font("Tahoma 10", Font.BOLD, 20));
		titlelbl.setBounds(63, 10, 275, 24);
		contentPane.add(titlelbl);
		
		JLabel newPassword = new JLabel("Nueva Contrase�a:");
		newPassword.setBounds(10, 64, 132, 13);
		contentPane.add(newPassword);
		
		JLabel newPassword_1 = new JLabel("Nueva Contrase�a:");
		newPassword_1.setBounds(10, 112, 132, 13);
		contentPane.add(newPassword_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(164, 107, 222, 24);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(164, 59, 222, 24);
		contentPane.add(passwordField_1);
		
		changePasswordBtn = new JButton("Cambiar Contrase\u00F1a");
		changePasswordBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pwd1= new String(passwordField.getPassword());
				String pwd2= new String(passwordField_1.getPassword());
				if(pwd1.equals(pwd2)) {
					facade.cambiarPassword(userName, pwd1);
					if(admin) {
						JFrame a= new AdminGUI(userName);
						a.setVisible(true);
					}else {
						JFrame a= new UserGUI(userName);
						a.setVisible(true);
					}
					jButtonClose_actionPerformed(e);
				}else {
					errorLbl.setText("Las contrase�as no son iguales.");
				}
				/*
				 * get contrase�a1
				 * get contrase�a2
				 * comprobar que son iguales
				 * cambiar contrase�a
				 * �Enviar un correo diciendo q su contrase�a ha sido modifcada?
				 */
			}
		});
		changePasswordBtn.setBounds(63, 198, 140, 30);
		contentPane.add(changePasswordBtn);
		
		closeBtn = new JButton("Cerrar");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(admin) {
					JFrame a= new AdminGUI(userName);
					a.setVisible(true);
				}else {
					JFrame a= new UserGUI(userName);
					a.setVisible(true);
				}
				jButtonClose_actionPerformed(e);
			}
		});
		closeBtn.setBounds(244, 198, 140, 30);
		contentPane.add(closeBtn);
		
		errorLbl = new JLabel("", SwingConstants.CENTER);
		errorLbl.setForeground(Color.RED);
		errorLbl.setBounds(63, 158, 323, 13);
		contentPane.add(errorLbl);
		
		
	}
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
