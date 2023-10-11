package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import businesslogic.BLFacade;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class ImportImageGUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField;
	private JButton verImageBtn;
	private JLabel errorLbl;
	private JButton closeBtn;
	private BLFacade facade;
	private JLabel lblNewLabel_2;
	private JButton buscarBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImportImageGUI frame = new ImportImageGUI(args[0]);
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
	public ImportImageGUI(String username) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setTitle("Importar Imagen");
		
		setBusinessLogic(MainGUI.getBusinessLogic());
		
		JLabel lblNewLabel = new JLabel("Introduce la ruta de la imagen", SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma 10", Font.BOLD, 20));
		lblNewLabel.setBounds(63, 10, 303, 32);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(23, 95, 371, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel imagenLbl1 = new JLabel("Imagen introducida:");
		imagenLbl1.setBounds(26, 142, 144, 13);
		contentPane.add(imagenLbl1);
		
		JLabel lblNewLabel_1 = new JLabel("La imagen debe ser 50x50, de extensi�n .png", SwingConstants.CENTER);
//		lblNewLabel_1.setFont(new Font("Tahoma 10", Font.BOLD, 20));
		lblNewLabel_1.setBounds(10, 41, 416, 19);
		contentPane.add(lblNewLabel_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(169, 152, 50, 50);
		contentPane.add(panel);
		
		verImageBtn = new JButton("Cargar Imagen");
		verImageBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ruta= textField.getText();
				try {
					BufferedImage img = ImageIO.read(new File(ruta));
					JLabel pic = new JLabel(new ImageIcon(img));
					panel.add(pic);
					
					facade.setImageUser(username, ruta);
					
					errorLbl.setForeground(Color.GREEN);
					errorLbl.setText("La imagen ha sido cambiada correctamente.");
					
				} catch(IOException ee){
					errorLbl.setForeground(Color.RED);
					errorLbl.setText("No se ha podido cargar o encontrar la imagen.");
				}
			}
		});
		verImageBtn.setBounds(63, 232, 128, 21);
		contentPane.add(verImageBtn);
		
		closeBtn = new JButton("Cerrar");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a= new UserGUI(username);
				a.setVisible(true);
				jButtonClose_actionPerformed(e);
			}
		});
		closeBtn.setBounds(241, 232, 85, 21);
		contentPane.add(closeBtn);
		
		errorLbl = new JLabel("");
		errorLbl.setForeground(Color.RED);
		errorLbl.setBounds(23, 199, 403, 13);
		contentPane.add(errorLbl);
		
		lblNewLabel_2 = new JLabel("Tambi�n debe ir junto con la extensi�n .png al final", SwingConstants.CENTER);
		lblNewLabel_2.setBounds(10, 66, 416, 19);
		contentPane.add(lblNewLabel_2);
		
		buscarBtn = new JButton("Buscar");
		buscarBtn.addActionListener(this);
		buscarBtn.setBounds(149, 121, 85, 21);
		contentPane.add(buscarBtn);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif"); 
		fileChooser.setFileFilter(imgFilter);

		int result = fileChooser.showOpenDialog(this);

		if (result != JFileChooser.CANCEL_OPTION) {

			File fileName = fileChooser.getSelectedFile();

			if ((fileName == null) || (fileName.getName().equals(""))) {
				textField.setText("...");
			} else {
				textField.setText(fileName.getAbsolutePath());
			}
		}
	}
		
		
	}


