package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.sun.mail.smtp.SMTPOutputStream;
import com.toedter.calendar.JCalendar;
import domain.Question;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;


public class FindQuestionsGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents= new JTable();
	private JTable tableQueries = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;


	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};

	private String[] columnNamesPronosticos = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("ApuestaN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Apuesta")

	};
	private final JTable tablePronosticos = new JTable();
	private DefaultTableModel tableModelPronosticos;

	private JScrollPane scrollPanePronosticos;

	private BLFacade facadee;
	private final JButton btnApostar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("FindQuestionsGUI.btnApostar.text")); //$NON-NLS-1$ //$NON-NLS-2$

	public FindQuestionsGUI(boolean admin, boolean user, String userName)
	{
		try
		{
			jbInit(admin, user, userName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void setBusinessLogic(BLFacade b) {
		facadee=b;
	}



	private void jbInit(boolean admin, boolean user, String userName) throws Exception
	{

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(40, 247, 406, 14);
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);

		jButtonClose.setBounds(new Rectangle(157, 411, 130, 30));
		setBusinessLogic(MainGUI.getBusinessLogic());

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
				if(admin) {
					JFrame a= new AdminGUI(userName);
					a.setVisible(true);
				}else if(user){
					JFrame a= new UserGUI(userName);
					a.setVisible(true);
				}					
			}
		});

		this.getContentPane().add(jButtonClose, null);


		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{


			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{

				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
					//					jCalendar1.setCalendar(calendarAct);
					Date firstDay=UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));



					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);

					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						

						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = MainGUI.getBusinessLogic();

						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
					}



					CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);
					//					setBusinessLogic(MainGUI.getBusinessLogic());


					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects


						Vector<domain.Event> events=facade.getEvents(firstDay);

						if (events.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarAct.getTime()));
						else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarAct.getTime()));
						for (domain.Event ev:events){
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events "+ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);		
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not shown in JTable
					} catch (Exception e1) {

						jLabelQueries.setText(e1.getMessage());
					}

				}
			} 
		});

		this.getContentPane().add(jCalendar1, null);

		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(40, 271, 406, 116));

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				Vector<Question> queries=ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);

				if (queries.isEmpty())
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("NoQueries")+": "+ev.getDescription());
				else 
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent")+": "+ev.getDescription());

				for (domain.Question q:queries){
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					tableModelQueries.addRow(row);	
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
			}
		});

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);


		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);

		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);

		scrollPanePronosticos = new JScrollPane();
		scrollPanePronosticos.setBounds(485, 269, 164, 118);
		getContentPane().add(scrollPanePronosticos);
		tablePronosticos.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Apuesta#", "Apuesta"
				}
				));
		tablePronosticos.getColumnModel().getColumn(0).setPreferredWidth(75);  //50
		tablePronosticos.getColumnModel().getColumn(1).setPreferredWidth(168); //268

		scrollPanePronosticos.setViewportView(tablePronosticos);

		//		scrollPanePronosticos.setViewportView(tablePronosticos);
		tableModelPronosticos = new DefaultTableModel(null, columnNamesPronosticos);

		tablePronosticos.setModel(tableModelPronosticos);

		this.getContentPane().add(scrollPanePronosticos, null);

		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("FindQuestionsGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setBounds(485, 248, 164, 14);
		getContentPane().add(lblNewLabel);

		btnApostar.setEnabled(user);
		btnApostar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(!admin) {
						int i=tableQueries.getSelectedRow();
						int q=(int)tableModelQueries.getValueAt(i,0);
						JFrame a= new ApostarGUI(q, userName);
						a.setVisible(true);
						jButton2_actionPerformed(e);
					}
				}catch(Exception ee) {
					jLabelQueries.setText("No hay pronosticos para esta pregunta.");
				}
			}
		});
		btnApostar.setBounds(new Rectangle(157, 411, 130, 30));
		btnApostar.setBounds(337, 411, 130, 30);		

		getContentPane().add(btnApostar);

		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int i=tableQueries.getSelectedRow();
					int q=(int)tableModelQueries.getValueAt(i,0);
					Question pregunta= facadee.getQuestionByNum(q);
					Vector<String> apuestas= new Vector<String>();
					apuestas= pregunta.getPronosticos();

					tableModelPronosticos.setDataVector(null, columnNamesPronosticos);

					if (apuestas.isEmpty()) {
						jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("NoOpcionApuesta")+": "+pregunta.getQuestion());
					}else 
						jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedQuery")+" "+pregunta.getQuestion());

					int cont=1;
					for (String a:apuestas){
						Vector<Object> row = new Vector<Object>();
						row.add(cont);
						row.add(a);
						tableModelPronosticos.addRow(row);
						cont++;
					}
					tablePronosticos.setModel(tableModelPronosticos);
					tablePronosticos.getColumnModel().getColumn(0).setPreferredWidth(50);
					tablePronosticos.getColumnModel().getColumn(1).setPreferredWidth(268);
					//							jButtonClose_actionPerformed(e);

				}catch(NullPointerException ee){
					jLabelQueries.setText("No hay pronosticos para esta pregunta");
				}
			}
		});


		//		scrollPanePronosticos.setViewportView(tablePronosticos);
		//		tableModelPronosticos = new DefaultTableModel(null, columnNamesPronosticos);
		//
		//		tablePronosticos.setModel(tableModelPronosticos);
		//		tablePronosticos.getColumnModel().getColumn(0).setPreferredWidth(50);
		//		tablePronosticos.getColumnModel().getColumn(1).setPreferredWidth(268);
		//
		//		this.getContentPane().add(scrollPanePronosticos, null);
	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
