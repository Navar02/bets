package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businesslogic.BLFacade;
import domain.Apuesta;
import domain.Question;

import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JList;
import java.awt.Choice;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.ListModel;

public class ApostarGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JLabel textField_1;
	private JButton apostarBtn;
	private JButton closeBtn;
	private JLabel errorLbl;

	private BLFacade facade;
	private JList apuestasList;
	private DefaultListModel listModel= new DefaultListModel();
	private JList apuestasList2;
	private DefaultListModel listModel2= new DefaultListModel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApostarGUI frame = new ApostarGUI(Integer.parseInt(args[0]), args[1]);
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


	/**
	 * Create the frame.
	 */
	public ApostarGUI(int qNum, String userName) {
		setTitle("Apostar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 646, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setBusinessLogic(MainGUI.getBusinessLogic());

		JLabel questionLabel = new JLabel("Descipci�n de la pregunta: ");
		questionLabel.setBounds(10, 10, 223, 13);
		contentPane.add(questionLabel);

		JLabel questDescriptionPane = new JLabel();
		questDescriptionPane.setText(facade.getQuestionByNum(qNum).getQuestion());
		questDescriptionPane.setBounds(109, 33, 445, 25);
		contentPane.add(questDescriptionPane);

		JLabel minBetLbl = new JLabel("Apuesta m�nima: ");
		minBetLbl.setBounds(10, 244, 100, 13);
		contentPane.add(minBetLbl);

		

		Question q= facade.getQuestionByNum(qNum);
		Vector<Float> pronos= facade.getPorcentajesPronosticosPregunta(qNum);
		Vector<String>pronosticos= q.getPronosticos();
		
		if(pronosticos.size()==2) {
			listModel.addElement(pronosticos.get(0));
			listModel.addElement(pronosticos.get(1));
			listModel2.addElement(pronos.get(0));
			listModel2.addElement(pronos.get(1));

		}else {
			listModel.addElement(pronosticos.get(0));
			listModel.addElement(pronosticos.get(1));
			listModel.addElement(pronosticos.get(2));
			listModel2.addElement(pronos.get(0));
			listModel2.addElement(pronos.get(1));
			listModel2.addElement(pronos.get(2));
		}

		apuestasList = new JList(listModel);
		apuestasList.setBounds(69, 87, 255, 126);
		contentPane.add(apuestasList);
		
		apuestasList2 = new JList(listModel2);
		apuestasList2.setBounds(332, 87, 65, 126);
		contentPane.add(apuestasList2);

		JLabel lblNewLabel = new JLabel("Tu apuesta: ");
		lblNewLabel.setBounds(10, 288, 77, 13);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
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
		textField.setBounds(109, 285, 53, 19);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JLabel();
		float f= facade.getQuestionByNum(qNum).getBetMinimum();
		textField_1.setText("" + f);
		textField_1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c=='0') {
					getToolkit().beep();
					e.consume();
				}
			}
		});
		textField_1.setBounds(120, 241, 53, 19);
		contentPane.add(textField_1);

		apostarBtn = new JButton("Apostar");
		apostarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean exist=false;
				if(facade.getApuestasQuestion(qNum) != null) {
					Vector<Apuesta>vec= facade.getApuestasQuestion(qNum);
					int i=0;
					while(!exist && i<vec.size()) {
						if(userName.compareTo(vec.get(i).getName())==0) {
							exist= true;
						}
						i++;
					}
				}
				if(exist) {
					errorLbl.setForeground(Color.RED);
					errorLbl.setText("Ya ha realizado una apuesta para esta pregunta.");
				}else if(!exist && textField.getText().length()>0 && textField.getText().length()<5 && facade.getQuestionByNum(qNum).getResult()==null) {
					if(Float.parseFloat(textField.getText())<= facade.getUserByName(userName).getTarjeta().getDinero() && Float.parseFloat(textField.getText())>= f && apuestasList.getSelectedValue() !=null) {
						String pronostico= (String)apuestasList.getSelectedValue();
						float cantidadApuesta= Float.parseFloat(textField.getText());
						System.out.println(pronostico + cantidadApuesta);
						facade.apostar(userName, qNum, cantidadApuesta, pronostico);
						errorLbl.setForeground(Color.GREEN);
						errorLbl.setText("La apuesta se ha a�adido correctamente");
					}else if(Float.parseFloat(textField.getText())> facade.getUserByName(userName).getTarjeta().getDinero()) {
						errorLbl.setForeground(Color.RED);
						errorLbl.setText("No tiene dinero suficiente para realizar esta apuesta.");
					}else if(apuestasList.getSelectedValue() ==null) {
						errorLbl.setForeground(Color.RED);
						errorLbl.setText("Selecciona el pronostico de la lista a apostar.");
					}
					
					else {
						errorLbl.setForeground(Color.RED);
						errorLbl.setText("Tu apuesta no puede ser menor que el precio m�nimo.");
					}
				}else if(facade.getQuestionByNum(qNum).getResult()!=null) {
					errorLbl.setForeground(Color.RED);
					errorLbl.setText("Esta pregunta ya tiene resultado.");
				}else {
					errorLbl.setForeground(Color.RED);
					errorLbl.setText("Intruduce una cantidad a apostar.");
				}
			}
		});
		apostarBtn.setBounds(147, 323, 134, 32);
		contentPane.add(apostarBtn);

		closeBtn = new JButton("Cerrar");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a= new FindQuestionsGUI(false, true, userName);
				a.setVisible(true);
				jButtonClose_actionPerformed(e);
			}
		});
		closeBtn.setBounds(338, 323, 134, 32);
		contentPane.add(closeBtn);

		errorLbl = new JLabel("");
		errorLbl.setForeground(Color.RED);
		errorLbl.setBounds(194, 263, 381, 13);
		contentPane.add(errorLbl);
		
		
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
