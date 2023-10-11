package dataAccess;

import java.time.LocalDateTime;
//hello
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Apuesta;
import domain.Event;
import domain.Pronosticos;
import domain.Question;
import domain.Tarjeta;
import domain.Transaccion;
import domain.User;
import exceptions.EventAlreadyExist;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	protected static EntityManager  db;
	protected static EntityManagerFactory emf;


	ConfigXML c=ConfigXML.getInstance();

	public DataAccess(boolean initializeMode)  {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccess()  {	
		this(false);
	}


	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){

		db.getTransaction().begin();
		try {


			Calendar today = Calendar.getInstance();

			int month=today.get(Calendar.MONTH);
			month+=1;
			int year=today.get(Calendar.YEAR);
			if (month==12) { month=0; year+=1;}  

			Event ev1=new Event(1, "Atl�tico-Athletic", UtilDate.newDate(year,month,17));
			Event ev2=new Event(2, "Eibar-Barcelona", UtilDate.newDate(year,month,17));
			Event ev3=new Event(3, "Getafe-Celta", UtilDate.newDate(year,month,17));
			Event ev4=new Event(4, "Alav�s-Deportivo", UtilDate.newDate(year,month,17));
			Event ev5=new Event(5, "Espanyol-Villareal", UtilDate.newDate(year,month,17));
			Event ev6=new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year,month,17));
			Event ev7=new Event(7, "Malaga-Valencia", UtilDate.newDate(year,month,17));
			Event ev8=new Event(8, "Girona-Legan�s", UtilDate.newDate(year,month,17));
			Event ev9=new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year,month,17));
			Event ev10=new Event(10, "Betis-Real Madrid", UtilDate.newDate(year,month,17));

			Event ev11=new Event(11, "Atl�tico-Athletic", UtilDate.newDate(year,month,1));
			Event ev12=new Event(12, "Eibar-Barcelona", UtilDate.newDate(year,month,1));
			Event ev13=new Event(13, "Getafe-Celta", UtilDate.newDate(year,month,1));
			Event ev14=new Event(14, "Alav�s-Deportivo", UtilDate.newDate(year,month,1));
			Event ev15=new Event(15, "Espanyol-Villareal", UtilDate.newDate(year,month,1));
			Event ev16=new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year,month,1));


			Event ev17=new Event(17, "M�laga-Valencia", UtilDate.newDate(year,month+1,28));
			Event ev18=new Event(18, "Girona-Legan�s", UtilDate.newDate(year,month+1,28));
			Event ev19=new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year,month+1,28));
			Event ev20=new Event(20, "Betis-Real Madrid", UtilDate.newDate(year,month+1,28));

			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;
			Question q7;
			Question q8;
			Question q9;
			Question q10;
			Question q11;
			Question q12;
			String ganar= "�Qui�n ganar� el partido?";
			String gol= "�Qui�n meter� el primer gol?";
			if (Locale.getDefault().equals(new Locale("es"))) {
				q1=ev1.addQuestion(ganar,1);
				q2=ev1.addQuestion(gol,2);
				q3=ev11.addQuestion(ganar,1);
				q4=ev11.addQuestion("�Cuantos goles se marcar�n?",2);
				q5=ev17.addQuestion(ganar,1);
				q6=ev17.addQuestion("�Habr� goles en la primera parte?",2);
				q7=ev2.addQuestion(ganar,1);
				q8=ev2.addQuestion(gol,2);
				q9=ev3.addQuestion(ganar,1);
				q10=ev3.addQuestion(gol,2);
				q11=ev4.addQuestion(ganar,1);
				q12=ev4.addQuestion(gol,2);
			}
			else if (Locale.getDefault().equals(new Locale("en"))) {
				q1=ev1.addQuestion("Who will win the match?",1);
				q2=ev1.addQuestion("Who will score first?",2);
				q3=ev11.addQuestion("Who will win the match?",1);
				q4=ev11.addQuestion("How many goals will be scored in the match?",2);
				q5=ev17.addQuestion("Who will win the match?",1);
				q6=ev17.addQuestion("Will there be goals in the first half?",2);
				q7=ev2.addQuestion("Who will win the match?",1);
				q8=ev2.addQuestion("Who will score first?",2);
				q9=ev3.addQuestion("Who will win the match?",1);
				q10=ev3.addQuestion("Who will score first?",2);
				q11=ev4.addQuestion("Who will win the match?",1);
				q12=ev4.addQuestion("Who will score first?",2);
			}			
			else {
				q1=ev1.addQuestion("Zeinek irabaziko du partidua?",1);
				q2=ev1.addQuestion("Zeinek sartuko du lehenengo gola?",2);
				q3=ev11.addQuestion("Zeinek irabaziko du partidua?",1);
				q4=ev11.addQuestion("Zenbat gol sartuko dira?",2);
				q5=ev17.addQuestion("Zeinek irabaziko du partidua?",1);
				q6=ev17.addQuestion("Golak sartuko dira lehenengo zatian?",2);
				q7=ev2.addQuestion("Zeinek irabaziko du partidua?",1);
				q8=ev2.addQuestion("Zeinek sartuko du lehenengo gola?",2);
				q9=ev3.addQuestion("Zeinek irabaziko du partidua?",1);
				q10=ev3.addQuestion("Zeinek sartuko du lehenengo gola?",2);
				q11=ev4.addQuestion("Zeinek irabaziko du partidua?",1);
				q12=ev4.addQuestion("Zeinek sartuko du lehenengo gola?",2);

			}

			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6); 
			db.persist(q7);
			db.persist(q8);
			db.persist(q9);
			db.persist(q10);
			db.persist(q11);
			db.persist(q12);
			

			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);	
			
			
			/*
			Pronosticos pVacio1= new Pronosticos(1, (new Vector<String>()));
			Pronosticos pVacio2= new Pronosticos(2, (new Vector<String>()));
			Pronosticos pVacio3= new Pronosticos(3, (new Vector<String>()));
			Pronosticos pVacio4= new Pronosticos(4, (new Vector<String>()));
			Pronosticos pVacio5= new Pronosticos(5, (new Vector<String>()));
			Pronosticos pVacio6= new Pronosticos(6, (new Vector<String>()));
			
			q1.setPronostico(pVacio1);
			q2.setPronostico(pVacio2);
			q3.setPronostico(pVacio3);
			q4.setPronostico(pVacio4);
			q5.setPronostico(pVacio5);
			q6.setPronostico(pVacio6);
			
			db.persist(pVacio1);
			db.persist(pVacio2);
			db.persist(pVacio3);
			db.persist(pVacio4);
			db.persist(pVacio5);
			db.persist(pVacio6);
			*/

			User usuario= new User("Carlos", "1234", "1111111111111111", "therealcodemonkeys@gmail.com");
			usuario.setAdmin(true);
			db.persist(usuario);
			
			User usuario2= new User("Juan", "123", "1111111111111112", "jaheki1761@angeleslid.com");
			usuario2.setAdmin(false);
			Tarjeta t= new Tarjeta("1111111111111112");
			usuario2.setTarjeta(t);
			
			db.persist(t);
			db.persist(usuario2);
			
			User usuario3= new User("Paco", "123", "1111111111111113", "jaheki1761@angeleslid.com");
			usuario3.setAdmin(false);
			Tarjeta t3= new Tarjeta("1111111111111113");
			usuario3.setTarjeta(t3);
			
			db.persist(t3);
			db.persist(usuario3);
			
			User usuario4= new User("Juan2", "123", "1111111111111114", "jaheki1761@angeleslid.com");
			usuario4.setAdmin(false);
			Tarjeta t4= new Tarjeta("1111111111111114");
			usuario4.setTarjeta(t4);
			
			db.persist(t4);
			db.persist(usuario4);
			
			User usuario5= new User("Fran", "123", "1111111111111115", "jaheki1761@angeleslid.com");
			usuario5.setAdmin(false);
			Tarjeta t5= new Tarjeta("1111111111111115");
			usuario5.setTarjeta(t5);
			
			db.persist(t5);
			db.persist(usuario5);
			
			User usuario6= new User("Fran2", "123", "1111111111111116", "jaheki1761@angeleslid.com");
			usuario6.setAdmin(false);
			Tarjeta t6= new Tarjeta("1111111111111116");
			usuario6.setTarjeta(t6);
			
			db.persist(t6);
			db.persist(usuario6);
			
			User usuario7= new User("Fran3", "123", "1111111111111117", "jaheki1761@angeleslid.com");
			usuario7.setAdmin(false);
			Tarjeta t7= new Tarjeta("1111111111111117");
			usuario7.setTarjeta(t7);
			
			db.persist(t7);
			db.persist(usuario7);
			
			User usuario8= new User("Antton", "123", "1234123412341234", "behoj84702@duetube.com");
			usuario8.setAdmin(false);
			Tarjeta t8= new Tarjeta("1234123412341234");
			usuario8.setTarjeta(t8);
			
			db.persist(t8);
			db.persist(usuario8);
			
			db.getTransaction().commit();
			
			Vector<String> vec= new Vector<String>();
			vec.add(0, "Local");
			vec.add(1, "Empate");
			vec.add(2, "Visitante");
			
			Vector<Float> vec2= new Vector<Float>();
			vec2.add(0, Float.parseFloat("0.2"));
			vec2.add(1, Float.parseFloat("0.5"));
			vec2.add(2, Float.parseFloat("0.3"));
			
			anadirPronostico(3, vec, vec2);

			vec= new Vector<String>();
			vec.add(0, "0 goles");
			vec.add(1, "1-2 goles");
			vec.add(2, "3 o + goles");
			
			vec2= new Vector<Float>();
			vec2.add(0, Float.parseFloat("0.5"));
			vec2.add(1, Float.parseFloat("0.2"));
			vec2.add(2, Float.parseFloat("0.3"));
			
			anadirPronostico(4, vec, vec2);

			addDinero("Paco", Float.parseFloat("100"), false);
			anadirApuesta("Paco", 3, Float.parseFloat("50"), "Empate");
			
			anadirApuesta("Paco", 4, Float.parseFloat("50"), "0 goles");
			
			addDinero("Juan", Float.parseFloat("200"), false);
			anadirApuesta("Juan", 3, Float.parseFloat("150"), "Empate");
			
			anadirApuesta("Juan", 4, Float.parseFloat("50"), "1-2 goles");
			
			addDinero("Juan2", Float.parseFloat("500"), false);
			anadirApuesta("Juan2", 3, Float.parseFloat("200"), "Empate");
			
//			addDinero("Juan2", Float.parseFloat("500"), false);
			anadirApuesta("Juan2", 4, Float.parseFloat("200"), "1-2 goles");
			
			addDinero("Fran", Float.parseFloat("300"), false);
			anadirApuesta("Fran", 3, Float.parseFloat("10"), "Local");
			
			anadirApuesta("Fran", 4, Float.parseFloat("200"), "0 goles");
			
			addDinero("Fran2", Float.parseFloat("130"), false);
			anadirApuesta("Fran2", 3, Float.parseFloat("50"), "Visitante");
			
			anadirApuesta("Fran2", 4, Float.parseFloat("50"), "0 goles");
			
			addDinero("Fran3", Float.parseFloat("600"), false);
			anadirApuesta("Fran3", 3, Float.parseFloat("500"), "Empate");
			
			anadirApuesta("Fran3", 4, Float.parseFloat("100"), "1-2 goles");
			
//			setResult(3, "Empate");
//			setResult(4, "0 goles");
			
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws  QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= "+event+" question= "+question+" betMinimum="+betMinimum);
		if(event==null) {
			throw new NullPointerException("Error evento nulo");
		}
		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question)) throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);
		//db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;

	}
	
	public Event createEvent(Integer eventNumber, String description,Date eventDate) throws EventAlreadyExist {
		System.out.println(">> DataAccess: createEvent=> eventNumber= "+eventNumber+" description= "+description+" date="+eventDate);

		Event ev = db.find(Event.class, eventNumber);

		if (ev!=null) throw new EventAlreadyExist("");

		db.getTransaction().begin();
		ev= new Event(eventNumber, description, eventDate);
		//db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return ev;
	}
	

	/**
	 * This method retrieves from the database the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();	
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1",Event.class);   
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev:events){
			System.out.println(ev.toString());		 
			res.add(ev);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();	

		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);


		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2",Date.class);   
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d:dates){
			System.out.println(d.toString());		 
			res.add(d);
		}
		return res;
	}


	public void open(boolean initializeMode){

		System.out.println("Opening DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		String fileName=c.getDbFilename();
		if (initializeMode) {
			fileName=fileName+";drop";
			System.out.println("Deleting the DataBase");
		}

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			db = emf.createEntityManager();
			
		}

	}
	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion=> event= "+event+" question= "+question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.DoesQuestionExists(question);

	}
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}




	public String anadirUsuario(String nombUsuario, String password, String numTarjeta, String correo) {
		db.getTransaction().begin();
		User usuario= new User(nombUsuario, password, numTarjeta, correo);
		Tarjeta t= new Tarjeta(numTarjeta);
		usuario.setTarjeta(t);
		db.persist(t);
		db.persist(usuario);
		System.out.println("Usuario a�adido");
		db.getTransaction().commit();
		System.out.println("DataBase closed");
		return nombUsuario + ", " + password;
	}


	public boolean userExist(String userName) {
		boolean esta= false;
		User user= getUser(userName);
		if(user != null) {
			esta= true;
		}
		return esta;
	}


	public User getUser(String userName) {
		User usuario= db.find(User.class, userName);
		return usuario;
	}
	
	
	public void anadirPronostico(int num, Vector<String> vector, Vector<Float>vector2) {
		db.getTransaction().begin();
		Question q=db.find(Question.class, num);
//		Pronosticos pro= db.find(Pronosticos.class, num);
		Pronosticos pro= new Pronosticos(num, vector, vector2);
		q.setPronostico(pro);
//		pro.setPronostico(vector);
//		q.setPronostico(pro);
		q.setHayPronostico(true);
		db.persist(pro);
		db.getTransaction().commit();
	}
	
	public Question getQuestionByNum(int num) {
		db.getTransaction().begin();
		Question q=db.find(Question.class, num);
		db.getTransaction().commit();
		return q;
	}
	
	public Vector<String> getPronosticosPregunta(int num){
		db.getTransaction().begin();
		Pronosticos pro=db.find(Pronosticos.class, num);
//		Vector<String>resp= q.getPronosticos();
		db.getTransaction().commit();
		return pro.getOpciones();
	}
	
	public Vector<Float> getPorcentajesPronosticosPregunta(int num){
		db.getTransaction().begin();
		Pronosticos pro=db.find(Pronosticos.class, num);
//		Vector<String>resp= q.getPronosticos();
		db.getTransaction().commit();
		return pro.getPorcentajes();
	}
	
	public boolean pronosticosExist(int num) {
		db.getTransaction().begin();
		Question q= db.find(Question.class, num);
		db.getTransaction().commit();
		boolean b= true;
		if(q!=null) {
			b=q.hayPronostico();
		}
		return b;
	}
	
	
	public float addDinero(String username, float money, boolean a) {
		db.getTransaction().begin();
		User user= db.find(User.class, username);
		Tarjeta t =user.getTarjeta();
		t.addDinero(money);
		Transaccion tran= new Transaccion(money, t.getNumTarjeta(), new java.util.Date(), t.getDinero(), a);
		t.setTransacciones(tran);
		db.persist(tran);
		db.getTransaction().commit();
		return money;
	}
	
	public void anadirApuesta( String userName, int qNum, float cantidad, String pronostico) {
		db.getTransaction().begin();
		User user= getUser(userName);
		float dinero= user.getTarjeta().getDinero();
		user.getTarjeta().setDinero(dinero - cantidad);
		Apuesta ap= new Apuesta(userName, qNum, cantidad, pronostico);
		Transaccion tran= new Transaccion(-cantidad, user.getTarjeta().getNumTarjeta(), new java.util.Date(), user.getTarjeta().getDinero(), true);
		db.getTransaction().commit();
		Question q= getQuestionByNum(qNum);
		db.getTransaction().begin();
		q.setApuestas(ap);
		user.addVecApuestas(ap);
		user.getTarjeta().setTransacciones(tran);
		db.persist(ap);
		db.persist(tran);
		db.getTransaction().commit();
	}
	
	
	public Tarjeta getTarjeta(String nTarjeta) {
		Tarjeta t= db.find(Tarjeta.class, nTarjeta);
		return t;
	}
	
	public boolean tarjetaExist(String nTarjeta) {
		boolean t=false;
		if(getTarjeta(nTarjeta)!=null) {
			t=true;
		}
		return t;
	}
	
	public void setResult(int qNum, String result) {
		Question q= getQuestionByNum(qNum);
		db.getTransaction().begin();
		q.setResult(result);
		db.getTransaction().commit();
	}
	
	public Vector<Apuesta> getApuestasQuestion(int qNum){
		Question q= getQuestionByNum(qNum);
		return q.getApuestas();
	}
	
	public Vector<Apuesta> getUserApuestas(String userName){
		User user = getUser(userName);
		db.getTransaction().begin();
		Vector<Apuesta>vec= user.getVecApuestas();
		db.getTransaction().commit();
		return vec;
	}
	
	public Vector<Transaccion> getTransacciones(String tarjeta){
		Tarjeta tarj= getTarjeta(tarjeta);
		return tarj.getTransaccioness();
	}
	
	public void cambiarPasswd(String userName, String newPasswd) {
		User user= getUser(userName);
		db.getTransaction().begin();
		user.setPassword(newPasswd);
		db.getTransaction().commit();
		System.out.println(">> DataAccess: user=> userName, new password setted.");
	}

	public Vector<User> getAllUsers(){
		Vector<User> vector= new Vector<User>();
		TypedQuery<User> query = db.createQuery("SELECT u FROM User u WHERE u.admin<>true and u.vetado<>true",User.class);
		List<User> users = query.getResultList();
		for(User i: users) {
			vector.add(i);
		}
		return vector;
	}
	
	public void setVeto(String userName) {
		User user= getUser(userName);
		db.getTransaction().begin();
		user.setVeto(true);
		db.getTransaction().commit();
	}
	
	public Vector<Object> getUsersMasGanancias(){
		Vector<Object>row= new Vector<Object>();
		Vector<User>vecUsers= getAllUsers();
		for(User i: vecUsers) {
			String username= i.getUserName();
			Vector<Transaccion> tran= i.getTarjeta().getTransaccioness();
			float total=0;
			int numApuestas=0;
			for(Transaccion tra: tran) {
				if(tra.isApuesta() && tra.getCant()>0) {
					total+= tra.getCant();
				} else if(tra.isApuesta() && tra.getCant()<0) {
					numApuestas++;
				}
			}
			row.add(username);
			row.add(total);
			row.add(numApuestas);
		}
		return row;
	}
	
	public void setImageUser(String username, String ruta) {
		User user= getUser(username);
		db.getTransaction().begin();
		user.setRutaImagen(ruta);
		db.getTransaction().commit();
	}
}
