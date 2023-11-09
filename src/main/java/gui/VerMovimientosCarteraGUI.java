package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Apuesta;
import domain.Transaccion;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;

public class VerMovimientosCarteraGUI extends JFrame {

	private JPanel contentPane;
	private String[] columnNamesApuestas = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Fecha"), 
			ResourceBundle.getBundle("Etiquetas").getString("Ingresos"),
			ResourceBundle.getBundle("Etiquetas").getString("SA"),
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
					VerMovimientosCarteraGUI frame = new VerMovimientosCarteraGUI(args[0]);
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
	public VerMovimientosCarteraGUI(String userName) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 599, 397);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setBusinessLogic(MainGUI.getBusinessLogic());
		this.setTitle("Ver Mis Movimientos");
		
		scrollPaneApuestas = new JScrollPane();
		scrollPaneApuestas.setBounds(20, 61, 555, 254);
		contentPane.add(scrollPaneApuestas);
		
		tableApuestas = new JTable();
		tableApuestas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Fecha", "Ingresos", "Saldo Actual"
			}
		));
		tableApuestas.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableApuestas.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableApuestas.getColumnModel().getColumn(2).setPreferredWidth(150);
		scrollPaneApuestas.setViewportView(tableApuestas);
		
		tableModelApuestas = new DefaultTableModel(null, columnNamesApuestas);

		tableApuestas.setModel(tableModelApuestas);

		this.getContentPane().add(scrollPaneApuestas, null);
		
		lblNewLabel = new JLabel(" ", SwingConstants.CENTER);
		lblNewLabel.setBounds(99, 38, 392, 13);
		contentPane.add(lblNewLabel);
		
		if(facade.getUserByName(userName).getTarjeta().getTransaccioness()!= null) {
			Vector<Transaccion>vec= facade.getUserByName(userName).getTarjeta().getTransaccioness();
			for(Transaccion transacciones : vec) {
				Date fecha= transacciones.getDate();
				float cantidad= transacciones.getCant();
				float saldoActual= transacciones.getDineroActualT();
				Vector<Object> row = new Vector<Object>();
				row.add(fecha);
				row.add(cantidad);
				row.add(saldoActual);
				
				tableModelApuestas.addRow(row);
			}
			tableApuestas.setModel(tableModelApuestas);
			tableApuestas.getColumnModel().getColumn(0).setPreferredWidth(150);
			tableApuestas.getColumnModel().getColumn(1).setPreferredWidth(100);
			tableApuestas.getColumnModel().getColumn(2).setPreferredWidth(150);
		}else {
			lblNewLabel.setForeground(Color.RED);
			lblNewLabel.setText("No tiene ninguna transacci�n realizada.");
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
		
		JLabel helloLbl = new JLabel("Mis Movimientos ", SwingConstants.CENTER);
		helloLbl.setFont(new Font("Tahoma 10", Font.BOLD, 20));
		helloLbl.setBounds(136, 10, 295, 30);
		contentPane.add(helloLbl);
		
		
	}
}
