package test.dataAccess;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.Calendar;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Event;
import domain.User;
import exceptions.EventAlreadyExist;

public class DAWPronosticosExistTest {
	static DataAccess sut;
	ConfigXML c = ConfigXML.getInstance();
	static TestDataAccess testDA;
	
    @Test
    public void testPronosticosExistWithValidQuestion() {
        // Prueba con una pregunta existente
    	testDA = new TestDataAccess();
		sut = new DataAccess();
		Calendar today = Calendar.getInstance();
		int month=today.get(Calendar.MONTH);
		int year=today.get(Calendar.YEAR);
		Event ev33= testDA.addEventWithQuestion("Max Verstappen siempre gana", UtilDate.newDate(year,month+1,17), "�Quien gana?", 14);
		int qnum=ev33.getQuestions().get(0).getQuestionNumber();
		Vector<String> vec1= new Vector<String>();
        vec1.add("Local"); vec1.add("Empate"); vec1.add("Visitante");
        
        //Las ganancias de cada opcion del pronostico
        Vector<Float> vec2= new Vector<Float>();
        vec2.add(Float.parseFloat("0.2")); vec2.add(Float.parseFloat("0.5")); vec2.add(Float.parseFloat("0.3"));
        try {
		sut.anadirPronostico(qnum,vec1,vec2);
        assertTrue(sut.pronosticosExist(qnum));
        }catch(Exception e) {
        	fail("error");
        }finally {
        	testDA.open();
        	testDA.eliminarPronostico(14);
    		testDA.removeEvent(ev33);
    		testDA.close();
        }
    }

    @Test
    public void testPronosticosExistWithInvalidQuestion() {
        // Prueba con una pregunta inexistente
    	Calendar today = Calendar.getInstance();
		int month=today.get(Calendar.MONTH);
		int year=today.get(Calendar.YEAR);
    	testDA = new TestDataAccess();
		sut = new DataAccess();
		Event ev66=testDA.addEventWithQuestion("Chuck Norris", UtilDate.newDate(year,month+1,17), "Quien es jesus?", 69);
		try {
			assertFalse(sut.pronosticosExist(ev66.getQuestions().get(0).getQuestionNumber()));
		} catch (Exception e) {
			fail("error");//esperado
			e.printStackTrace();
		}finally{
			testDA.open();
			testDA.eliminarPronostico(69);
    		testDA.removeEvent(ev66);
    		testDA.close();
		}
    }
}
