package test.dataAccess;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import domain.Apuesta;
import domain.Event;
import domain.Pronosticos;
import domain.Question;
import domain.User;

public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestDataAccess()  {
		
		System.out.println("Creating TestDataAccess instance");

		open();
		
	}

	
	public void open(){
		
		System.out.println("Opening TestDataAccess instance ");

		String fileName=c.getDbFilename();
		
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

	public boolean removeEvent(Event ev) {
		System.out.println(">> DataAccessTest: removeEvent");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e!=null) {
			db.getTransaction().begin();
			for(Question q : e.getQuestions()) {
				db.remove(q);
			}
			db.remove(e);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
		
		public Event addEventWithQuestion(String desc, Date d, String question, float qty) {
			System.out.println(">> DataAccessTest: addEvent");
			Event ev=null;
				db.getTransaction().begin();
				try {
				    ev=new Event(desc,d);
				    ev.addQuestion(question, qty);
					db.persist(ev);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return ev;
	    }
		public boolean existQuestion(Event ev,Question q) {
			System.out.println(">> DataAccessTest: existQuestion");
			Event e = db.find(Event.class, ev.getEventNumber());
			if (e!=null) {
				return e.DoesQuestionExists(q.getQuestion());
			} else 
			return false;
			
		}
		public boolean eliminarUser(User uName) {
			System.out.println(">> DataAccessTest: existUser");
			User u = db.find(User.class, uName.getUserName());
			if (u!=null) {
				db.getTransaction().begin();
				db.remove(u.getTarjeta());
				db.remove(u);
				db.getTransaction().commit();
				return true;
			} else 
			return false;
		}
		
		public boolean eliminarApuesta(User user, int qNum) {
			System.out.println(">> DataAccessTest: existApuesta");
			TypedQuery<Apuesta> query= db.createQuery("SELECT a FROM Apuesta a WHERE a.userName LIKE 'Paco22' and a.questionNum = 52", Apuesta.class);// user.getUserName().toString()
			List<Apuesta> apuestas = query.getResultList();
			if (query!=null) {
				db.getTransaction().begin();
				for(Apuesta i: apuestas) {
					db.remove(i);
				}
				db.getTransaction().commit();
				return true;
			} else 
			return false;
		}
		
		public boolean eliminarPronostico(int num) {
            System.out.println(">> DataAccessTest: eliminarPronostico");
            Pronosticos p=db.find(Pronosticos.class, num);
            if(p!=null) {
                db.getTransaction().begin();
                db.remove(p);
                db.getTransaction().commit();
                return true;
            }
            else {
                return false;
            }
        }
}

