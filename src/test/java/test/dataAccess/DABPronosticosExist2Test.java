package test.dataAccess;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Vector;

import org.junit.Test;

import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.DataAccessPronosticoExist;
import dataAccess.DataAccessUserExists;
import domain.Event;
import domain.Pronosticos;

public class DABPronosticosExist2Test {
	static DataAccessPronosticoExist sut;
	ConfigXML c=ConfigXML.getInstance();
	static TestDataAccess testDA;
	
	
	@Test
	public void testPronosticosExistWithValidQuestion() {
		// Prueba con una pregunta existente
		testDA = new TestDataAccess();
		sut = new DataAccessPronosticoExist();
		Vector<String> vec=new Vector<String>();
		vec.add("Local"); vec.add("Empate"); vec.add("Visitante");
		Vector<Float> porc=new Vector<Float>();
		porc.add(Float.parseFloat("0.5")); porc.add(Float.parseFloat("0.2")); porc.add(Float.parseFloat("0.3"));
		domain.Event e=new domain.Event(33, "Real Madrid-Real Sociedad", UtilDate.newDate(2023,9+1,28));
		Pronosticos p=new Pronosticos(33, vec, porc);
		
		Calendar today = Calendar.getInstance();
		int month=today.get(Calendar.MONTH);
		month+=1;
		int year=today.get(Calendar.YEAR);
		if (month==12) { month=0; year+=1;}  
		Event ev30= testDA.addEventWithQuestion("Celta-Real Sociedad", UtilDate.newDate(year,month+1,17), "�Quien gana?", 33);
		int numEv=ev30.getQuestions().get(0).getQuestionNumber();
		
		try {
			//e=sut.createEvent(33, "Real Madrid-Real Sociedad", UtilDate.newDate(2023,9+1,28));
//			sut.createEvent(e.getEventNumber(), e.getDescription(), e.getEventDate());
//			sut.createQuestion(e,"¿Quien gana?", Float.parseFloat("0.2"));
//    		sut.anadirPronostico(33, vec, porc);
			sut.anadirPronostico(numEv, p.getOpciones(), p.getPorcentajes());
    		assertTrue(sut.pronosticosExist(numEv));
    	}catch(Exception z) {
    		fail("No funciona correctamente");
    	}finally {
    		testDA.open();
    		testDA.eliminarPronostico(33);
    		testDA.removeEvent(e);
    		testDA.close();
    	}
	}

	@Test
	public void testPronosticosExistWithInvalidQuestion() {
		// Prueba con una pregunta inexistente
		
		testDA = new TestDataAccess();
		sut = new DataAccessPronosticoExist();
		try {
    		assertFalse(sut.pronosticosExist(100)); // Número de pregunta inexistente
    	}catch(Exception e) {
    		fail("No funciona correctamente");
    	}finally {
    		testDA.open();
    		testDA.close();
    	}
	}
}
