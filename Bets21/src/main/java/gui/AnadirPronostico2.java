package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class AnadirPronostico2 extends JFrame {

	private JPanel contentPane;
	private JTextField textField1;
	private JTextField textField2;
	private JTextField textField3;
	private JButton btnAddForecast;
	private JLabel lblNewLabel;
	private JButton btnBack;

	private BLFacade facade;
	private JTextField porcentaje1;
	private JTextField porcentaje2;
	private JTextField porcentaje3;

	/**
	 * Launch the application.
	 */
	//	public AnadirPronostico2(int num)
	//	{
	//		try
	//		{
	//			AnadirPronosticosGUI2(num);
	//		}
	//		catch(Exception e)
	//		{
	//			e.printStackTrace();
	//		}
	//	}

	public void setBusinessLogic(BLFacade b) {
		facade=b;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AnadirPronostico2 frame = new AnadirPronostico2(Integer.parseInt(args[0]));
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
	public AnadirPronostico2(int num) {
		setTitle("Pronosticos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 589, 376);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setBusinessLogic(MainGUI.getBusinessLogic());

		JLabel lblOpcion1 = new JLabel("Primera opcion");
		lblOpcion1.setBounds(10, 42, 82, 20);
		contentPane.add(lblOpcion1);


		JLabel lblOpcion2 = new JLabel("Segunda opcion");
		lblOpcion2.setBounds(10, 117, 82, 20);
		contentPane.add(lblOpcion2);

		JLabel lblOpcion3 = new JLabel("Tercera opcion");
		lblOpcion3.setBounds(10, 193, 82, 20);
		contentPane.add(lblOpcion3);

		textField1 = new JTextField();
		textField1.setBounds(99, 37, 276, 32);
		contentPane.add(textField1);
		textField1.setColumns(10);

		textField2 = new JTextField();
		textField2.setColumns(10);
		textField2.setBounds(99, 112, 276, 32);
		contentPane.add(textField2);

		textField3 = new JTextField();
		textField3.setColumns(10);
		textField3.setBounds(99, 188, 276, 32);
		contentPane.add(textField3);

		btnAddForecast = new JButton("A\u00F1adir Pronosticos");
		btnAddForecast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String r1= textField1.getText();
					String r2= textField2.getText();
					String r3= textField3.getText();
					Float p1= Float.parseFloat(porcentaje1.getText());
					Float p2= Float.parseFloat(porcentaje2.getText());
					Float p3= Float.parseFloat(porcentaje3.getText());
					Vector<String>vector= new Vector<String>();
					Vector<Float>vector2= new Vector<Float>();
					if(r1.length()>0 && r2.length()>0 && r3.length()>0 && p1>0 && p2>0 && p3>0) {
						if((p1+p2+p3)==1) {
							vector.add(0, r1);
							vector.add(1, r2);
							vector.add(2, r3);
							vector2.add(0, p1);
							vector2.add(1, p2);
							vector2.add(2, p3);
						}else {
							lblNewLabel.setForeground(Color.RED);
							lblNewLabel.setText("La suma de todos los multiplicadores de acierto debe de ser igual a 1");
						}
					}else if(r1.length()>0 && r2.length()>0 && p1>0 && p2>0) {
						if((p1+p2)==1) {
							vector.add(0, r1);
							vector.add(1, r2);
							vector2.add(0, p1);
							vector2.add(1, p2);
						}else {
							lblNewLabel.setForeground(Color.RED);
							lblNewLabel.setText("La suma de todos los multiplicadores de acierto debe de ser igual a 1");
						}
						
					}
					if(vector.size()>1 && vector2.size()>1) {
						facade.anadirPronostico(num, vector, vector2);
						lblNewLabel.setText("Pronostico añadido de forma correcta");
						jButtonClose_actionPerformed(e);
					}
				}catch(Exception ee) {
					lblNewLabel.setForeground(Color.RED);
					lblNewLabel.setText("Rellena los campos de forma ordenada por favor");
				}		
			}
		});
		btnAddForecast.setBounds(122, 281, 147, 32);
		contentPane.add(btnAddForecast);

		lblNewLabel = new JLabel("", SwingConstants.CENTER);
		lblNewLabel.setBounds(55, 258, 470, 13);
		contentPane.add(lblNewLabel);

		btnBack = new JButton("Volver");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		btnBack.setBounds(285, 281, 147, 32);
		contentPane.add(btnBack);

		porcentaje1 = new JTextField();
		porcentaje1.setBounds(405, 37, 96, 32);
		porcentaje1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') ||
						(c == KeyEvent.VK_BACK_SPACE) ||
						(c == KeyEvent.VK_DELETE) ||
						(c == KeyEvent.VK_PERIOD))) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
		contentPane.add(porcentaje1);
		porcentaje1.setColumns(10);

		porcentaje2 = new JTextField();
		porcentaje2.setColumns(10);
		porcentaje2.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') ||
						(c == KeyEvent.VK_BACK_SPACE) ||
						(c == KeyEvent.VK_DELETE) ||
						(c == KeyEvent.VK_PERIOD))) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
		porcentaje2.setBounds(405, 112, 96, 32);
		contentPane.add(porcentaje2);

		porcentaje3 = new JTextField();
		porcentaje3.setColumns(10);
		porcentaje3.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') ||
						(c == KeyEvent.VK_BACK_SPACE) ||
						(c == KeyEvent.VK_DELETE) ||
						(c == KeyEvent.VK_PERIOD))) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
		porcentaje3.setBounds(405, 188, 96, 32);
		contentPane.add(porcentaje3);

		JLabel lblNewLabel_1 = new JLabel("Multiplicador de acierto:");
		lblNewLabel_1.setBounds(383, 10, 160, 13);
		contentPane.add(lblNewLabel_1);
	}
}
