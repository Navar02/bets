package dataAccess;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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

public class DataAccessGetUsersMasGanancias {
	protected static EntityManager  db;
	protected static EntityManagerFactory emf;


	ConfigXML c=ConfigXML.getInstance();

	public DataAccessGetUsersMasGanancias(boolean initializeMode)  {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccessGetUsersMasGanancias()  {	
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
				String winMatch = "Who will win the match?";
				q1=ev1.addQuestion(winMatch,1);
				String scoreFirst = "Who will score first?";
				q2=ev1.addQuestion(scoreFirst,2);
				q3=ev11.addQuestion(winMatch,1);
				q4=ev11.addQuestion("How many goals will be scored in the match?",2);
				q5=ev17.addQuestion(winMatch,1);
				q6=ev17.addQuestion("Will there be goals in the first half?",2);
				q7=ev2.addQuestion(winMatch,1);
				q8=ev2.addQuestion(scoreFirst,2);
				q9=ev3.addQuestion(winMatch,1);
				q10=ev3.addQuestion(scoreFirst,2);
				q11=ev4.addQuestion(winMatch,1);
				q12=ev4.addQuestion(scoreFirst,2);
			}			
			else {
				String zeinek = "Zeinek irabaziko du partidua?";
				q1=ev1.addQuestion(zeinek,1);
				String zeinek2 = "Zeinek sartuko du lehenengo gola?";
				q2=ev1.addQuestion(zeinek2,2);
				q3=ev11.addQuestion(zeinek,1);
				q4=ev11.addQuestion("Zenbat gol sartuko dira?",2);
				q5=ev17.addQuestion(zeinek,1);
				q6=ev17.addQuestion("Golak sartuko dira lehenengo zatian?",2);
				q7=ev2.addQuestion(zeinek,1);
				q8=ev2.addQuestion(zeinek2,2);
				q9=ev3.addQuestion(zeinek,1);
				q10=ev3.addQuestion(zeinek2,2);
				q11=ev4.addQuestion(zeinek,1);
				q12=ev4.addQuestion(zeinek2,2);

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
			
			String correo1 = "jaheki1761@angeleslid.com";
			User usuario2= new User("Juan", "123", "1111111111111112", correo1);
			usuario2.setAdmin(false);
			Tarjeta t= new Tarjeta("1111111111111112");
			usuario2.setTarjeta(t);
			
			db.persist(t);
			db.persist(usuario2);
			
			User usuario3= new User("Paco", "123", "1111111111111113", correo1);
			usuario3.setAdmin(false);
			Tarjeta t3= new Tarjeta("1111111111111113");
			usuario3.setTarjeta(t3);
			
			db.persist(t3);
			db.persist(usuario3);
			
			String juan2 = "Juan2";
			User usuario4= new User(juan2, "123", "1111111111111114", correo1);
			usuario4.setAdmin(false);
			Tarjeta t4= new Tarjeta("1111111111111114");
			usuario4.setTarjeta(t4);
			
			db.persist(t4);
			db.persist(usuario4);
			
			User usuario5= new User("Fran", "123", "1111111111111115", correo1);
			usuario5.setAdmin(false);
			Tarjeta t5= new Tarjeta("1111111111111115");
			usuario5.setTarjeta(t5);
			
			db.persist(t5);
			db.persist(usuario5);
			
			String fran2 = "Fran2";
			User usuario6= new User(fran2, "123", "1111111111111116", correo1);
			usuario6.setAdmin(false);
			Tarjeta t6= new Tarjeta("1111111111111116");
			usuario6.setTarjeta(t6);
			
			db.persist(t6);
			db.persist(usuario6);
			
			String fran3 = "Fran3";
			User usuario7= new User(fran3, "123", "1111111111111117", correo1);
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
			String empate = "Empate";
			vec.add(1, empate);
			vec.add(2, "Visitante");
			
			Vector<Float> vec2= new Vector<Float>();
			vec2.add(0, Float.parseFloat("0.2"));
			vec2.add(1, Float.parseFloat("0.5"));
			vec2.add(2, Float.parseFloat("0.3"));
			
			anadirPronostico(3, vec, vec2);

			vec= new Vector<String>();
			String Nogoles = "0 goles";
			vec.add(0, Nogoles);
			String unoODosGoles = "1-2 goles";
			vec.add(1, unoODosGoles);
			vec.add(2, "3 o + goles");
			
			vec2= new Vector<Float>();
			vec2.add(0, Float.parseFloat("0.5"));
			vec2.add(1, Float.parseFloat("0.2"));
			vec2.add(2, Float.parseFloat("0.3"));
			
			anadirPronostico(4, vec, vec2);

			addDinero("Paco", Float.parseFloat("100"), false);
			anadirApuesta("Paco", 3, Float.parseFloat("50"), empate);
			
			anadirApuesta("Paco", 4, Float.parseFloat("50"), Nogoles);
			
			addDinero("Juan", Float.parseFloat("200"), false);
			anadirApuesta("Juan", 3, Float.parseFloat("150"), empate);
			
			anadirApuesta("Juan", 4, Float.parseFloat("50"), unoODosGoles);
			
			addDinero(juan2, Float.parseFloat("500"), false);
			anadirApuesta(juan2, 3, Float.parseFloat("200"), empate);
			

			anadirApuesta(juan2, 4, Float.parseFloat("200"), unoODosGoles);
			
			addDinero("Fran", Float.parseFloat("300"), false);
			anadirApuesta("Fran", 3, Float.parseFloat("10"), "Local");
			
			anadirApuesta("Fran", 4, Float.parseFloat("200"), Nogoles);
			
			addDinero(fran2, Float.parseFloat("130"), false);
			anadirApuesta(fran2, 3, Float.parseFloat("50"), "Visitante");
			
			anadirApuesta(fran2, 4, Float.parseFloat("50"), Nogoles);
			
			addDinero(fran3, Float.parseFloat("600"), false);
			anadirApuesta(fran3, 3, Float.parseFloat("500"), empate);
			
			anadirApuesta(fran3, 4, Float.parseFloat("100"), unoODosGoles);
			
			
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
			db.getTransaction().rollback();
		}
	}
	
	public User getUser(String userName) {
		User usuario= db.find(User.class, userName);
		return usuario;
	}
	
	public void anadirApuesta(String userName, int i, float parseFloat, String empate) {
		db.getTransaction().begin();
		User user= getUser(userName);
		float dinero= user.getTarjeta().getDinero();
		user.getTarjeta().setDinero(dinero - parseFloat);
		Apuesta ap= new Apuesta(userName, i, parseFloat, empate);
		Transaccion tran= new Transaccion(-parseFloat, user.getTarjeta().getNumTarjeta(), new java.util.Date(), user.getTarjeta().getDinero(), true);
		db.getTransaction().commit();
		Question q= getQuestionByNum(i);
		db.getTransaction().begin();
		q.setApuestas(ap);
		user.addVecApuestas(ap);
		user.getTarjeta().setTransacciones(tran);
		db.persist(ap);
		db.persist(tran);
		db.getTransaction().commit();
	}

	private Question getQuestionByNum(int i) {
		db.getTransaction().begin();
		Question q=db.find(Question.class, i);
		db.getTransaction().commit();
		return q;
	}

	public float addDinero(String  username, float money, boolean b) {
		db.getTransaction().begin();
		User user= db.find(User.class, username);
		Tarjeta t =user.getTarjeta();
		t.addDinero(money);
		Transaccion tran= new Transaccion(money, t.getNumTarjeta(), new java.util.Date(), t.getDinero(), b);
		t.setTransacciones(tran);
		db.persist(tran);
		db.getTransaction().commit();
		return money;
	}

	public void anadirPronostico(int i, Vector<String> vec, Vector<Float> vec2) {
		db.getTransaction().begin();
		Question q=db.find(Question.class, i);
		Pronosticos pro= new Pronosticos(i, vec, vec2);
		q.setPronostico(pro);
		q.setHayPronostico(true);
		db.persist(pro);
		db.getTransaction().commit();
		
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
	
	public void close(){
		db.close();
		System.out.println("DataBase closed");
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
	
	public Vector<Object> getUsersMasGanancias(){
		Vector<Object>row= new Vector<Object>();
		Vector<User>vecUsers= getAllUsers();
		extracted(row, vecUsers);
		return row;
	}

	private void extracted(Vector<Object> row, Vector<User> vecUsers) {
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
			if(total>0) {
				row.add(username);
				row.add(total);
				row.add(numApuestas);
			}
		}
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
	
	public void setResult(int qNum, String result) {
		Question q= getQuestionByNum(qNum);
		db.getTransaction().begin();
		q.setResult(result);
		db.getTransaction().commit();
	}
}
