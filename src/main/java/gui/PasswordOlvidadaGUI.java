package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;

public class PasswordOlvidadaGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textUser;
	private JTextField textField_1;
	private JTextField textField_2;
	private JButton btnCorreo;
	private JButton btnCerrar;
	private BLFacade facade;
	private JLabel errorLbl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PasswordOlvidadaGUI frame = new PasswordOlvidadaGUI();
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
	public PasswordOlvidadaGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 604, 354);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setBusinessLogic(MainGUI.getBusinessLogic());
		
		JLabel lblNewLabel = new JLabel("Nombre de Usuario:");
		lblNewLabel.setBounds(22, 73, 150, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNumeroDeTarjeta = new JLabel("Numero de tarjeta:");
		lblNumeroDeTarjeta.setBounds(22, 124, 150, 13);
		contentPane.add(lblNumeroDeTarjeta);
		
		JLabel lblCorreoElectronico = new JLabel("Correo Electronico:");
		lblCorreoElectronico.setBounds(22, 182, 150, 13);
		contentPane.add(lblCorreoElectronico);
		
		textUser = new JTextField();
		textUser.setBounds(185, 68, 288, 24);
		contentPane.add(textUser);
		textUser.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(185, 119, 288, 24);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(185, 177, 288, 24);
		contentPane.add(textField_2);
		
		btnCorreo = new JButton("Recibir Nueva Password");
		btnCorreo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName= textUser.getText();
				String numT= textField_1.getText();
				String correo= textField_2.getText();
				User user= facade.getUserByName(userName);
				if(user!=null) {
					if(user.getTarjeta().getNumTarjeta().equals(numT)) {
						String pwd= user.getPassword();
						try {
							sendMail(userName, correo, pwd);
						} catch (MessagingException e1) {
							errorLbl.setText("Ha habido un problema, comprueba que el correo introducido sea correcto.");
						}
					}
				}
			}
		});
		btnCorreo.setBounds(81, 264, 204, 32);
		contentPane.add(btnCorreo);
		
		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new LoginGUI();
				a.setVisible(true);
				jButtonClose_actionPerformed(e);
			}
		});
		btnCerrar.setBounds(323, 264, 204, 32);
		contentPane.add(btnCerrar);
		
		JLabel lblNewLabel_1 = new JLabel("�Has olvidado tu cantrase�a?", SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma 10", Font.BOLD, 20));
		lblNewLabel_1.setBounds(103, 10, 393, 24);
		contentPane.add(lblNewLabel_1);
		
		errorLbl = new JLabel("");
		errorLbl.setForeground(Color.RED);
		errorLbl.setBounds(81, 224, 446, 13);
		contentPane.add(errorLbl);
	}
	
	private void sendMail(String userName, String correo, String pwd) throws MessagingException {
		 // Recipient's email ID needs to be mentioned.
        String destinatario = correo;

        // Sender's email ID needs to be mentioned
        String from = "therealcodemonkeys@gmail.com";
        
        //Contrase�a del emisor
        String password= "6kB25Zk*$~6kxT?/";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";
		
		//Obtenemos las propiedades del sistema
		Properties propiedad= new Properties();
		
		String asunto= "Contrase�a olvidada";
		
		String mensaje= "Hola " + userName +", \n Esta es la contrase�a que"
			+ "tenia asociada a su cuenta: " + pwd + "\n \n Atte: CodeMonkeys";
		
		
		//Configuraci�n del server
		propiedad.put("mail.smtp.host", host);
		propiedad.put("mail.smtp.port", "465");
		propiedad.put("mail.smtp.ssl.enable", "true");
		propiedad.put("mail.smtp.auth", "true");
		
		Session sesion = Session.getDefaultInstance(propiedad);
		
		
		// Create a default MimeMessage object.
        MimeMessage mail = new MimeMessage(sesion);
        
        try {
        	// Set From: header field of the header.
            mail.setFrom(new InternetAddress(from));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            mail.setSubject(asunto);
            mail.setText(mensaje);
            
            
            Transport transporte= sesion.getTransport("smtp");
            transporte.connect(from, password);
            transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transporte.close();
            
            errorLbl.setForeground(Color.GREEN);
            errorLbl.setText("Correo Enviado");
            
//            JOptionPane.showMessageDialog(null, "Correo Enviado con exito");
            
        }catch(AddressException ex) {
        	errorLbl.setText("A Ha habido un problema, comprueba que el correo introducido sea correcto.");
        }catch (MessagingException mex) {
        	errorLbl.setText("MEX Ha habido un problema, comprueba que el correo introducido sea correcto.");
        }
    
        
	}
}
