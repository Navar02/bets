package businessLogic;
import java.util.ArrayList;
//hola
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Question;
import domain.Tarjeta;
import domain.Transaccion;
import domain.User;
import domain.Apuesta;
import domain.Event;
import exceptions.EventAlreadyExist;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
//&  
/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;
	private static final Logger logger = Logger.getLogger(BLFacadeImplementation.class.getName());
	String init="initialize";
	public BLFacadeImplementation()  {		
		logger.info("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals(init)) {
		    dbManager=new DataAccess(c.getDataBaseOpenMode().equals(init));
		    dbManager.initializeDB();
		    } else
		     dbManager=new DataAccess();
		dbManager.close();

		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
    	logger.info("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals(init)) {
			da.open(true);
			da.initializeDB();
			da.close();

		}
		dbManager=da;		
	}
	

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
   @WebMethod
   public Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist{
	   
	    //The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry=null;
		
	    
		if(new Date().compareTo(event.getEventDate())>0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
				
		
		 qry=dbManager.createQuestion(event,question,betMinimum);		

		dbManager.close();
		
		return qry;
   }
	
	/**
	 * This method invokes the data access to retrieve the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
    @WebMethod	
	public Vector<Event> getEvents(Date date)  {
		dbManager.open(false);
		Vector<Event>  events=dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

    
	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date>  dates=dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}
	
	
	public void close() {
		DataAccess dB4oManager=new DataAccess(false);

		dB4oManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}

    
    public Event createEvent(Integer eventNumber, String description,Date eventDate) throws EventAlreadyExist {
    	dbManager.open(false);
    	Event ev=dbManager.createEvent(eventNumber, description, eventDate);
    	dbManager.close();
    	return ev;
    	
    }
    
    
    public String anadirUsuario(String nombUsuario, String password, String numTarjeta, String correo) {
    	String user= "Ya existe un usuario con el mismo nombre.";
    	if(!userExist(nombUsuario)) {
    		if(getTarjeta(numTarjeta)!=null) {
    			user= "Ya existe dicho numero de tarjeta.";
    		}else {
    			dbManager.open(false);
        		user= dbManager.anadirUsuario(nombUsuario, password, numTarjeta, correo);
        		dbManager.close();
    		}
    	}
    	return user;
    }
    
    
    public boolean userExist(String userName) {
    	dbManager.open(false);
    	boolean existe= dbManager.userExist(userName);
    	dbManager.close();
    	return existe;
    }
    
    
    public User getUserByName(String userName) {
    	dbManager.open(false);
    	User usuario= dbManager.getUser(userName);
    	dbManager.close();
    	return usuario;
    }
    
    
    public void anadirPronostico(int num, Vector<String>vector, Vector<Float>vector2) {
    	dbManager.open(false);
    	dbManager.anadirPronostico(num, vector, vector2);
    	dbManager.close();
    }

	public Question getQuestionByNum(int num) {
		dbManager.open(false);
    	Question q= dbManager.getQuestionByNum(num);
    	dbManager.close();
    	return q;
	}
	
	public Vector<String> getPronosticosPregunta(int num){
		dbManager.open(false);
		Vector<String>qVector= new Vector<>();
		if(dbManager.getQuestionByNum(num)!=null && dbManager.getQuestionByNum(num).getPronosticos()!=null) {
			qVector=dbManager.getPronosticosPregunta(num);
		}
		dbManager.close();
		return qVector;
	}
	
	public Vector<Float> getPorcentajesPronosticosPregunta(int num){
		dbManager.open(false);
		Vector<Float>qVector= new Vector<>();
		if(dbManager.getQuestionByNum(num)!=null) {
			if(dbManager.getQuestionByNum(num).getPronosticos()!=null) {
				qVector=dbManager.getPorcentajesPronosticosPregunta(num);
			}
		}
		dbManager.close();
		return qVector;
	}
	
	public boolean pronosticosExist(int num) {
		dbManager.open(false);
		boolean b= dbManager.pronosticosExist(num);
		dbManager.close();
		return b;
	}
	
	public Tarjeta getTarjeta(String nTarjeta) {
		dbManager.open(false);
    	Tarjeta t= dbManager.getTarjeta(nTarjeta);
    	dbManager.close();
    	return t;
	}
	
	public boolean tarjetaExist(String nTarjeta) {
		dbManager.open(false);
    	boolean t= dbManager.tarjetaExist(nTarjeta);
    	dbManager.close();
    	return t;
	}

	
	public void apostar(String userName, int qNum, float cantidad, String pronostico) {
		dbManager.open(false);
    	dbManager.anadirApuesta(userName, qNum, cantidad, pronostico);
    	dbManager.close();
		
	}
	
	public void anadirDinero(String userName, float dinero, boolean a) {
		dbManager.open(false);
    	dbManager.addDinero(userName, dinero, a);
    	dbManager.close();
	}
	public void setResult(int qNum, String result) {
		dbManager.open(false);
    	dbManager.setResult(qNum, result);
    	dbManager.close();
	}
	
	
	public Vector<Apuesta> getApuestasQuestion(int qNum){
		dbManager.open(false);
    	Vector<Apuesta>vector= dbManager.getApuestasQuestion(qNum);
    	dbManager.close();
    	return vector;
	}
	
	public Vector<Apuesta> getUserApuestas(String userName) {
		dbManager.open(false);
    	Vector<Apuesta>vector= dbManager.getUserApuestas(userName);
    	dbManager.close();
    	return vector;
	}

	public Vector<Transaccion> getTransacciones(String tarjeta){
		dbManager.open(false);
		Vector<Transaccion>vect= dbManager.getTransacciones(tarjeta);
		dbManager.close();
		return vect;
	}
	
	public void cambiarPassword(String userName, String pwd){
		dbManager.open(false);
		dbManager.cambiarPasswd(userName, pwd);
		dbManager.close();
	}
	
	public Vector<User> getAllUsers(){
		dbManager.open(false);
		Vector<User>vec=dbManager.getAllUsers();
		dbManager.close();
		return vec;
	}
	
	public void setVeto(String userName) {
		dbManager.open(false);
		dbManager.setVeto(userName);
		dbManager.close();
	}
	
	public Vector<Object> getUsersMasGanacias(){
		dbManager.open(false);
		Vector<Object>vec= dbManager.getUsersMasGanancias();
		dbManager.close();
		return vec;
	}
	
	public void setImageUser(String user, String ruta) {
		dbManager.open(false);
		dbManager.setImageUser(user, ruta);
		dbManager.close();
	}

	@Override
	public ExtendedIterator<Event> getEventsIterator(Date date) {
		return new ExtendedIteratorEvent(new ArrayList<>(getEvents(date)));
		
	}
	
}

