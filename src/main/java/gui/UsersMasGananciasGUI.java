package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import businessLogic.BLFacade;
import domain.Apuesta;
import domain.Transaccion;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;

public class UsersMasGananciasGUI extends JFrame {

	private JPanel contentPane;
	private String[] columnNamesApuestas = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("User"), 
			ResourceBundle.getBundle("Etiquetas").getString("Ganancias"),
			ResourceBundle.getBundle("Etiquetas").getString("nAp"),
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
	public static void main(String[] args, boolean a) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsersMasGananciasGUI frame = new UsersMasGananciasGUI(args[0], a);
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
	public UsersMasGananciasGUI(String userName, boolean admin) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 599, 397);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setBusinessLogic(MainGUI.getBusinessLogic());
		this.setTitle("Usuarios con mas Ganancias");
		
		
		scrollPaneApuestas = new JScrollPane();
		scrollPaneApuestas.setBounds(20, 61, 555, 254);
		contentPane.add(scrollPaneApuestas);
		
		tableApuestas = new JTable();
		tableApuestas.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tableApuestas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Usuario", "Ganacia Total", "Número de Apuestas Realizadas"
			}
		));
		tableApuestas.setAutoCreateRowSorter(true);
		tableApuestas.getColumnModel().getColumn(0).setPreferredWidth(200);
		tableApuestas.getColumnModel().getColumn(1).setPreferredWidth(150);
		tableApuestas.getColumnModel().getColumn(2).setPreferredWidth(200);
		scrollPaneApuestas.setViewportView(tableApuestas);
		
		tableModelApuestas = new DefaultTableModel(null, columnNamesApuestas);

		tableApuestas.setModel(tableModelApuestas);

		this.getContentPane().add(scrollPaneApuestas, null);
		
		lblNewLabel = new JLabel(" ", SwingConstants.CENTER);
		lblNewLabel.setBounds(99, 38, 392, 13);
		contentPane.add(lblNewLabel);
		
		if(facade.getUserByName(userName).getTarjeta().getTransaccioness()!= null) {
			Vector<Object>vec= facade.getUsersMasGanacias();
			int i=0, cant=0, pos=0;
			boolean lleno= false;
			while(i<vec.size() && !lleno) {
				String user= (String)vec.get(i);
				i++;
				float cantidad= (float)vec.get(i);
				i++;
				int numApuestas= (int)vec.get(i);
				i++;
				Vector<Object> row = new Vector<Object>();
				row.add(user);
				row.add(cantidad);
				row.add(numApuestas);
				
				if(cantidad>= cant) {
					if(tableApuestas.getRowCount() == 5) {
						tableModelApuestas.removeRow(4);
					}
					tableModelApuestas.addRow(row);
				}else {
					if(tableApuestas.getRowCount() == 5) {
						tableModelApuestas.removeRow(4);
						tableModelApuestas.addRow(row);
					}else {
						pos=tableApuestas.getRowCount()-1;
						tableModelApuestas.insertRow(pos+1, row);
					}
				}
				cant= (int)tableModelApuestas.getValueAt(tableApuestas.getRowCount()-1, 2);
			}
			tableApuestas.setModel(tableModelApuestas);
			tableApuestas.getColumnModel().getColumn(0).setPreferredWidth(150);
			tableApuestas.getColumnModel().getColumn(1).setPreferredWidth(100);
			tableApuestas.getColumnModel().getColumn(2).setPreferredWidth(150);
		}else {
			lblNewLabel.setForeground(Color.RED);
			lblNewLabel.setText("No tiene ninguna transacción realizada.");
		}
		
		
		TableRowSorter<DefaultTableModel> trs= new TableRowSorter(tableApuestas.getModel());
		
		tableApuestas.setRowSorter(trs);
		
		java.util.List<RowSorter.SortKey> sortList = new ArrayList<>(5);
		
		sortList.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
		
		trs.setSortKeys(sortList);
		
		closeBtn = new JButton("Cerrar");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!admin) {
					JFrame a= new UserGUI(userName);
					a.setVisible(true);
				}else {
					JFrame a= new AdminGUI(userName);
					a.setVisible(true);
				}
				jButtonClose_actionPerformed(e);
			}
		});
		closeBtn.setBounds(212, 325, 132, 25);
		contentPane.add(closeBtn);
		
		JLabel helloLbl = new JLabel("Usuarios con Más Ganancias", SwingConstants.CENTER);
		helloLbl.setFont(new Font("Tahoma 10", Font.BOLD, 20));
		helloLbl.setBounds(86, 10, 392, 30);
		contentPane.add(helloLbl);
		
		
	}
}
