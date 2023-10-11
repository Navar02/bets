package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businesslogic.BLFacade;
import domain.User;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class EnviarMotivoVetoGUI extends JFrame {

	private JPanel contentPane;
	private JButton vetarBtn;
	private JButton closeBtn;
	private BLFacade facade;
	private JLabel errorLbl;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnviarMotivoVetoGUI frame = new EnviarMotivoVetoGUI(args[0], args[1]);
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
	public EnviarMotivoVetoGUI(String username, String userVetado) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setTitle("Enviar Motivo Veto");
		
		setBusinessLogic(MainGUI.getBusinessLogic());
		
		JLabel userVetadoLbl = new JLabel("Usuario Vetado:");
		userVetadoLbl.setBounds(10, 31, 118, 13);
		contentPane.add(userVetadoLbl);
		
		JLabel userNameLbl = new JLabel(userVetado);
		userNameLbl.setBounds(158, 31, 268, 13);
		contentPane.add(userNameLbl);
		
		JLabel motivoLbl = new JLabel("Motivo del veto:");
		motivoLbl.setBounds(10, 58, 99, 13);
		contentPane.add(motivoLbl);
		
		vetarBtn = new JButton("Vetar Usuario");
		vetarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user= facade.getUserByName(userVetado);
				String texto= textArea.getText();
				sendMail(userVetado, user.getCorreo(), texto);
				facade.setVeto(userVetado);
				JFrame a= new VetarUserGUI(username);
				a.setVisible(true);
				jButtonClose_actionPerformed(e);
			}
		});
		vetarBtn.setBounds(75, 227, 118, 26);
		contentPane.add(vetarBtn);
		
		closeBtn = new JButton("Cerrar");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a= new VetarUserGUI(username);
				a.setVisible(true);
				jButtonClose_actionPerformed(e);
			}
		});
		closeBtn.setBounds(238, 227, 118, 26);
		contentPane.add(closeBtn);
		
		errorLbl = new JLabel("");
		errorLbl.setBounds(35, 189, 391, 13);
		contentPane.add(errorLbl);
		
		textArea = new JTextArea();
		textArea.setBounds(50, 81, 376, 94);
		contentPane.add(textArea);
	}
	
	private void sendMail(String userName, String correo, String texto) /*throws MessagingException*/ {
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
		
		String asunto= "Cuenta Vetada";
		
		String mensaje= "Hola " + userName +", \n " + texto + " \n Atte: CodeMonkeys";
		
		
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
           
//           JOptionPane.showMessageDialog(null, "Correo Enviado con exito");
           
       }catch(AddressException ex) {
       	errorLbl.setText("A Ha habido un problema, comprueba que el correo introducido sea correcto.");
       }catch (MessagingException mex) {
       	errorLbl.setText("MEX Ha habido un problema, comprueba que el correo introducido sea correcto.");
       }
   
       
	}
}
