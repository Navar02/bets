package gui;


import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Event;
import domain.User;

import javax.swing.JLabel;

public class UserGUI extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	private JButton btnConsutarPregunta;
	private JButton btnMvCartera;
	private JButton btnExit;
	private JButton btnLogout;
	private JButton anadirDineroBtn;
	private JButton btnConsultApuestas;
	private JButton btnNewButton;
	private JButton topGananciasBtn;
	private JPanel panel;
	private JLabel monneyLbl;
	private BLFacade facade;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserGUI frame = new UserGUI(args[0]);
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
	
	private void jButtonClose_mouseClicked(MouseEvent e) {
		this.setVisible(false);
	}

	/**
	 * Create the frame.
	 */
	public UserGUI(String userName) {
		setTitle("Usuario"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 631, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setBusinessLogic(MainGUI.getBusinessLogic());
		
		btnConsutarPregunta = new JButton("Consultar Pregunta");
		btnConsutarPregunta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						jButtonClose_actionPerformed(e);
						JFrame a= new FindQuestionsGUI(false, true, userName);
						a.setVisible(true);
			}
		});
		btnConsutarPregunta.setBounds(28, 85, 263, 60);
		contentPane.add(btnConsutarPregunta);
		
		btnMvCartera = new JButton("Ver Movimietos Cartera");
		btnMvCartera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						jButtonClose_actionPerformed(e);
						JFrame a= new VerMovimientosCarteraGUI(userName);
						a.setVisible(true);
			}
		});
		btnMvCartera.setBounds(334, 85, 263, 60);
		contentPane.add(btnMvCartera);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
//				jButtonClose_actionPerformed(e);
			}
		});
		btnExit.setBounds(79, 338, 177, 40);
		contentPane.add(btnExit);
		
		
		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		btnLogout.setBounds(334, 338, 177, 40);
		contentPane.add(btnLogout);
		
		anadirDineroBtn = new JButton("A�adir dinero a la cartera");
		anadirDineroBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a= new MiCarteraGUI(userName);
				a.setVisible(true);
				jButtonClose_actionPerformed(e);
			}
		});
		anadirDineroBtn.setBounds(334, 172, 263, 60);
		contentPane.add(anadirDineroBtn);
		
		JLabel helloLbl = new JLabel("Bienvenido " + userName, SwingConstants.CENTER);
		helloLbl.setFont(new Font("Tahoma 10", Font.BOLD, 20));
		helloLbl.setBounds(10, 32, 432, 20);
		contentPane.add(helloLbl);
		
		btnConsultApuestas = new JButton("Ver mis apuestas");
		btnConsultApuestas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a= new MisApuestasGUI(userName);
				a.setVisible(true);
				jButtonClose_actionPerformed(e);
			}
		});
		btnConsultApuestas.setBounds(28, 172, 263, 60);
		contentPane.add(btnConsultApuestas);
		
		btnNewButton = new JButton("Cambiar contrase\u00F1a");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 7));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a= new CambiarPasswordGUI(userName, false);
				a.setVisible(true);
				jButtonClose_actionPerformed(e);
			}
		});
		btnNewButton.setBounds(484, 54, 109, 21);
		contentPane.add(btnNewButton);
		
		topGananciasBtn = new JButton("Ver Top Ganancias de Usuarios");
		topGananciasBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a= new UsersMasGananciasGUI(userName, false);
				a.setVisible(true);
				jButtonClose_actionPerformed(e);
			}
		});
		topGananciasBtn.setBounds(128, 263, 335, 40);
		contentPane.add(topGananciasBtn);
		
		panel = new JPanel();
		panel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JFrame a= new ImportImageGUI(userName);
				a.setVisible(true);
				jButtonClose_mouseClicked(e);
		    }
		});
		panel.setBounds(424, 9, 50, 50);
		contentPane.add(panel);
		
		
		
		JLabel nameLbl = new JLabel(userName, SwingConstants.CENTER);
		nameLbl.setBounds(484, 17, 113, 13);
		contentPane.add(nameLbl);
		
		User usern= facade.getUserByName(userName);
		float money= usern.getTarjeta().getDinero();
		monneyLbl = new JLabel(money+"�", SwingConstants.CENTER);
		monneyLbl.setBounds(484, 40, 113, 13);
		contentPane.add(monneyLbl);
		
		if(usern.getRutaImagen() == null || usern.getRutaImagen().equals("")) {
			try {
				BufferedImage img = ImageIO.read(new File("FotoDefault.png"));
				JLabel pic = new JLabel(new ImageIcon(img));
				panel.add(pic);
				
			} catch(IOException ee){
				
			}
		}else {
			String ruta= usern.getRutaImagen();
			try {
				BufferedImage img = ImageIO.read(new File(ruta));
				JLabel pic = new JLabel(new ImageIcon(img));
				panel.add(pic);
				
			} catch(IOException ee){
				
			}
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}


