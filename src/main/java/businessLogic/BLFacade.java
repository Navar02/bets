package businesslogic;

import java.util.Vector;
import java.util.Date;
import domain.Question;
import domain.Transaccion;
import domain.User;
import domain.Apuesta;
import domain.Event;
import exceptions.EventAlreadyExist;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**  
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  

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
	@WebMethod Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;
	
	
	
	/**
	 * This method retrieves the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getEvents(Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();

	
	public String anadirUsuario(String nomUsuario, String password, String numTarjeta, String correo);
	
	
	public boolean userExist(String nomUsuario);
	
	
	public User getUserByName(String userName);
	
	
	public void anadirPronostico(int num, Vector<String> vector, Vector<Float>vector2);
	
	
	
	
	 public Event createEvent(Integer eventNumber, String description,Date eventDate) throws EventAlreadyExist;
	 
	 
	 public Question getQuestionByNum(int num);
	 
	 
	 public Vector<String> getPronosticosPregunta(int num);
	 
	 public Vector<Float> getPorcentajesPronosticosPregunta(int num);
	 
	 public boolean pronosticosExist(int num);
	
	 public boolean tarjetaExist(String numTarjeta);
	 
	 public void apostar(String userName, int qNum, float cant, String pronos);
	 
	 public void anadirDinero(String userName, float dinero, boolean a);
	 
	 public void setResult(int qNum, String result);
	 
	 public Vector<Apuesta> getApuestasQuestion(int qNum);
	 
	 public Vector<Apuesta> getUserApuestas(String userName);
	 
	 public Vector<Transaccion> getTransacciones(String tarjeta);
	 
	 public void cambiarPassword(String userName, String newPwd);
	 
	 public Vector<User> getAllUsers();
	 
	 public void setVeto(String userName);
	 
	 public Vector<Object> getUsersMasGanacias();
		
	 public void setImageUser(String user, String ruta);
	 
}
