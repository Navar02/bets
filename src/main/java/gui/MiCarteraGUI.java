package gui;

import java.awt.BorderLayout;
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

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

public class MiCarteraGUI extends JFrame {

	private JPanel contentPane;
	private JLabel textField;
	private JTextField textField_1;
	private JButton closeBtn;
	private JButton anadirSaldoBtn;

	private BLFacade facade;
	private JLabel errorLbl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MiCarteraGUI frame = new MiCarteraGUI(args[0]);
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
	public MiCarteraGUI(String userName) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setTitle("Mi cartera");

		setBusinessLogic(MainGUI.getBusinessLogic());

		JLabel userNameLbl = new JLabel("Tu cartera");
		userNameLbl.setBounds(162, 22, 96, 13);
		contentPane.add(userNameLbl);

		JLabel saldoLbl = new JLabel("Tu saldo: ");
		saldoLbl.setBounds(28, 70, 73, 13);
		contentPane.add(saldoLbl);

		textField = new JLabel();
		textField.setText("" + facade.getUserByName(userName).getTarjeta().getDinero());
		textField.setBounds(111, 67, 96, 19);
		contentPane.add(textField);

		JLabel lblNewLabel = new JLabel("�Cuanto dinero deseas a�adir a tu cartera?");
		lblNewLabel.setBounds(94, 123, 263, 13);
		contentPane.add(lblNewLabel);

		JLabel cantidadLbl = new JLabel("Introduce la cantidad que desea a�adir a su cartera: ");
		cantidadLbl.setBounds(10, 155, 300, 13);
		contentPane.add(cantidadLbl);

		textField_1 = new JFormattedTextField();
		textField_1.addKeyListener(new KeyAdapter() {
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
		textField_1.setBounds(315, 152, 96, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		anadirSaldoBtn = new JButton("A�adir Saldo");
		anadirSaldoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				try {
					if(textField_1.getText().length()>0 && textField_1.getText().length()<4) {
						int dinero= Integer.parseInt(textField_1.getText());
						facade.anadirDinero(userName, dinero, false);
						errorLbl.setForeground(Color.GREEN);
						errorLbl.setText("El saldo se ha a�adido correctamente.");
						textField.setText("" + facade.getUserByName(userName).getTarjeta().getDinero());
					}else {
						errorLbl.setText("A�ade una cantidad entre 1� hasta 999� .");
					}
//				}catch(Exception ee) {
//					errorLbl.setText("Debe introducir una cantidad antes de continuar con la operaci�n.");
//				}
			}
		});
		anadirSaldoBtn.setBounds(88, 222, 119, 31);
		contentPane.add(anadirSaldoBtn);

		closeBtn = new JButton("Cerrar");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a= new UserGUI(userName);
				a.setVisible(true);
				jButtonClose_actionPerformed(e);
			}
		});
		closeBtn.setBounds(238, 222, 119, 31);
		contentPane.add(closeBtn);
		
		errorLbl = new JLabel("");
		errorLbl.setForeground (Color.RED);
		errorLbl.setBounds(28, 191, 383, 13);
		contentPane.add(errorLbl);
	}

}
