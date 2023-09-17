package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Apuesta;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;

public class MisApuestasGUI extends JFrame {

	private JPanel contentPane;
	private String[] columnNamesApuestas = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("PreguntaN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Pregunta"),
			ResourceBundle.getBundle("Etiquetas").getString("Cant"),
			ResourceBundle.getBundle("Etiquetas").getString("Pron"),
	};
	
	private JTable tableApuestas;
	private DefaultTableModel tableModelApuestas;
	private JButton closeBtn;
	private JScrollPane scrollPaneApuestas;
	private BLFacade facade;
	private JLabel lblNewLabel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MisApuestasGUI frame = new MisApuestasGUI(args[0]);
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
	public MisApuestasGUI(String userName) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 599, 397);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setBusinessLogic(MainGUI.getBusinessLogic());
		this.setTitle("Mis Apuestas");
		
		scrollPaneApuestas = new JScrollPane();
		scrollPaneApuestas.setBounds(20, 61, 555, 254);
		contentPane.add(scrollPaneApuestas);
		
		tableApuestas = new JTable();
		tableApuestas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"P#", "Pregunta", "Cantidad", "Pronostico"
			}
		));
		tableApuestas.getColumnModel().getColumn(0).setPreferredWidth(50);
		tableApuestas.getColumnModel().getColumn(1).setPreferredWidth(300);
		tableApuestas.getColumnModel().getColumn(2).setPreferredWidth(75);
		tableApuestas.getColumnModel().getColumn(3).setPreferredWidth(150);
		scrollPaneApuestas.setViewportView(tableApuestas);
		
		tableModelApuestas = new DefaultTableModel(null, columnNamesApuestas);

		tableApuestas.setModel(tableModelApuestas);

		this.getContentPane().add(scrollPaneApuestas, null);
		
		lblNewLabel = new JLabel(" ", SwingConstants.CENTER);
		lblNewLabel.setBounds(99, 38, 392, 13);
		contentPane.add(lblNewLabel);
		
		if(facade.getUserApuestas(userName)!= null) {
			Vector<Apuesta>vec= facade.getUserApuestas(userName);
			for(Apuesta apuestas : vec) {
				int qNum= apuestas.getQNum();
				String pregunta= facade.getQuestionByNum(qNum).getQuestion();
				float cantidad= apuestas.getCantidad();
				String pronostico= apuestas.getPronostico();
				Vector<Object> row = new Vector<Object>();
				row.add(qNum);
				row.add(pregunta);
				row.add(cantidad);
				row.add(pronostico);
				
				tableModelApuestas.addRow(row);
			}
			tableApuestas.setModel(tableModelApuestas);
			tableApuestas.getColumnModel().getColumn(0).setPreferredWidth(50);
			tableApuestas.getColumnModel().getColumn(1).setPreferredWidth(300);
			tableApuestas.getColumnModel().getColumn(2).setPreferredWidth(75);
			tableApuestas.getColumnModel().getColumn(3).setPreferredWidth(150);
		}else {
			lblNewLabel.setForeground(Color.RED);
			lblNewLabel.setText("No tienes apuestas realizadas.");
		}
		
		
		
		
		closeBtn = new JButton("Cerrar");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a= new UserGUI(userName);
				a.setVisible(true);
				jButtonClose_actionPerformed(e);
			}
		});
		closeBtn.setBounds(212, 325, 132, 25);
		contentPane.add(closeBtn);
		
		JLabel helloLbl = new JLabel("Tus Apuestas ", SwingConstants.CENTER);
		helloLbl.setFont(new Font("Tahoma 10", Font.BOLD, 20));
		helloLbl.setBounds(136, 10, 295, 30);
		contentPane.add(helloLbl);
		
		
	}
}
