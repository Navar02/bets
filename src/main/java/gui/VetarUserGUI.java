package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import businesslogic.BLFacade;
import domain.User;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextField;

public class VetarUserGUI extends JFrame {

	private JPanel contentPane;
	private JButton closeBtn;
	private JButton vetarBtn;
	private BLFacade facade;
	private JLabel errorLbl;
	private JList<String> list;
	private DefaultListModel<String> model;
	private JTextField findUserTextField;
	private JButton buscarBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VetarUserGUI frame = new VetarUserGUI(args[0]);
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
	public VetarUserGUI(String username) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 547, 346);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.setTitle("Vetar Usuario");
		
		setBusinessLogic(MainGUI.getBusinessLogic());
		
		JLabel lblNewLabel = new JLabel("Vetar Usuario", SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma 10", Font.BOLD, 20));
		lblNewLabel.setBounds(79, 10, 270, 32);
		contentPane.add(lblNewLabel);
		
		Vector<User>vec= facade.getAllUsers();
		
		model = new DefaultListModel<>();
		list = new JList<>( model );
		try {
			for(User i: vec) {
				model.addElement(i.getUserName());
			}
		}catch(Exception ee) {
			errorLbl.setText("No hay usuarios registrados.");
		}
		list.setBounds(52, 67, 424, 170);
		contentPane.add(list);
		
		vetarBtn = new JButton("Vetar Usuario");
		vetarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userVetado= list.getSelectedValue();
				if(userVetado != null) {
					JFrame a= new EnviarMotivoVetoGUI(username, userVetado);
					a.setVisible(true);
					jButtonClose_actionPerformed(e);
				}else {
					errorLbl.setText("Selecciona un usuario al que vetar.");
				}
				
			}
		});
		vetarBtn.setBounds(106, 266, 115, 21);
		contentPane.add(vetarBtn);
		
		closeBtn = new JButton("Cerrar");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a= new AdminGUI(username);
				a.setVisible(true);
				jButtonClose_actionPerformed(e);
			}
		});
		closeBtn.setBounds(286, 266, 115, 21);
		contentPane.add(closeBtn);
		
		errorLbl = new JLabel("", SwingConstants.CENTER);
		errorLbl.setForeground(Color.RED);
		errorLbl.setBounds(42, 209, 359, 13);
		contentPane.add(errorLbl);
		
		findUserTextField = new JTextField();
		findUserTextField.setText("Nombre exacto");
		findUserTextField.setBounds(359, 10, 168, 19);
		contentPane.add(findUserTextField);
		findUserTextField.setColumns(10);
		
		buscarBtn = new JButton("Buscar");
		buscarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean encontrado=false;
				int j=0;
				String fUser= findUserTextField.getText();
				while( j<model.getSize()) {
					if(model.getElementAt(j).compareTo(fUser) == 0) {
						encontrado=true;
						j++;
					}else {
						model.removeElementAt(j);
					}
				}
				if(!encontrado) {
					errorLbl.setText("No hay usuarios con ese nombre.");
					try {
						for(User i: vec) {
							model.addElement(i.getUserName());
						}
					}catch(Exception ee) {
						errorLbl.setText("No hay usuarios registrados.");
					}
				}
			}
		});
		buscarBtn.setBounds(401, 36, 85, 21);
		contentPane.add(buscarBtn);
		
		
	}
}
