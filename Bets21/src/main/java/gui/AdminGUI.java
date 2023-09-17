package gui;


import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import domain.Event;
import javax.swing.JLabel;

public class AdminGUI extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	private JButton btnConsutarPregunta;
	private JButton btnCrearPregunta;
	private JButton btnExit;
	private JButton btnCrearEvento;
	private JButton btnAnadirPronostico;
	private JButton btnLogout;
	private JButton closeEventBtn;
	private JButton btnNewButton;
	private JButton vetarBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminGUI frame = new AdminGUI(args[0]);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	/**
	 * Create the frame.
	 */
	public AdminGUI(String userName) {
		setTitle("Administrador"); //Pasar el nombre por parametroooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 577, 386);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnConsutarPregunta = new JButton("Consultar Pregunta");
		btnConsutarPregunta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						jButtonClose_actionPerformed(e);
						JFrame a= new FindQuestionsGUI(true, false, userName);
						a.setVisible(true);
			}
		});
		btnConsutarPregunta.setBounds(44, 40, 203, 72);
		contentPane.add(btnConsutarPregunta);
		
		btnCrearPregunta = new JButton("Crear Pregunta");
		btnCrearPregunta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						jButtonClose_actionPerformed(e);
						JFrame a= new CreateQuestionGUI(new Vector<Event>(), true, false, userName);
						a.setVisible(true);
			}
		});
		btnCrearPregunta.setBounds(271, 40, 208, 72);
		contentPane.add(btnCrearPregunta);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
//				jButtonClose_actionPerformed(e);
			}
		});
		btnExit.setBounds(271, 284, 208, 40);
		contentPane.add(btnExit);
		
		btnCrearEvento = new JButton("Crear Evento");
		btnCrearEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					jButtonClose_actionPerformed(e);
						JFrame a= new CreateEventGUI(new Vector<Event>(), userName);
						a.setVisible(true);
			}
		});
		
		btnCrearEvento.setBounds(44, 131, 203, 70);
		contentPane.add(btnCrearEvento);
		
		btnAnadirPronostico = new JButton("A\u00F1adir Pronostico");
		btnAnadirPronostico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
				JFrame a = new AnadirPronosticosGUI(userName);
				a.setVisible(true);
			}
		});
		btnAnadirPronostico.setBounds(271, 131, 208, 70);
		contentPane.add(btnAnadirPronostico);
		
		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		btnLogout.setBounds(44, 284, 203, 40);
		contentPane.add(btnLogout);
		
		closeEventBtn = new JButton("Cerrar Evento");
		closeEventBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
				JFrame a = new CloseEventGUI(userName);
				a.setVisible(true);
				
			}
		});
		closeEventBtn.setBounds(44, 220, 203, 46);
		contentPane.add(closeEventBtn);
		
		JLabel helloLbl = new JLabel("Bienvenido " + userName, SwingConstants.CENTER);
		helloLbl.setFont(new Font("Tahoma 10", Font.BOLD, 20));
		helloLbl.setBounds(92, 10, 281, 20);
		contentPane.add(helloLbl);
		
		btnNewButton = new JButton("Cambiar Contraseña");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CambiarPasswordGUI(userName, true);
				a.setVisible(true);
				jButtonClose_actionPerformed(e);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 7));
		btnNewButton.setBounds(434, 9, 119, 21);
		contentPane.add(btnNewButton);
		
		vetarBtn = new JButton("Vetar Usuario");
		vetarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new VetarUserGUI(userName);
				a.setVisible(true);
				jButtonClose_actionPerformed(e);
			}
		});
		vetarBtn.setBounds(271, 220, 203, 46);
		contentPane.add(vetarBtn);
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


