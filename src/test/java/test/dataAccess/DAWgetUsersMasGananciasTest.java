package test.dataAccess;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Event;
import domain.Question;
import domain.User;

public class DAWgetUsersMasGananciasTest {
	static DataAccess sut;
    static TestDataAccess testDA;
	

    @Test
    public void testGetUsersMasGananciasWithNoUsers() {
    	try {
    		//Set sut
    		sut = new DataAccess();
    		// No hay nada en la BD por tanto esta vacio
    		Vector<Object> result = sut.getUsersMasGanancias();
    		assertTrue(result.isEmpty());
    	}catch(Exception e) {
    		fail("Va mal");
    	}
    }

    @Test
    public void testGetUsersMasGananciasWithUsersAndTransactions() {
    	//Set test y sut
    	testDA = new TestDataAccess();
		sut = new DataAccess();
		
		//Aï¿½adimos user y lo obtenemos para comprobar q existe
		String u1= sut.anadirUsuario("Paco22", "123", "1111111111111122", "mi@correo.com");
		User uu1 = sut.getUser("Paco22");
		
		//Para aï¿½adir evento
		Calendar today = Calendar.getInstance();
		int month=today.get(Calendar.MONTH);
		month+=1;
		int year=today.get(Calendar.YEAR);
		if (month==12) { month=0; year+=1;}  
		
		//Aï¿½adimos el evento
		Event ev30= testDA.addEventWithQuestion("Celta-Real Sociedad", UtilDate.newDate(year,month+1,17), "ï¿½Quien gana?", 20);
		
		//Obtenemos las preguntas de dicho evento
		Vector<Question> qVec = ev30.getQuestions();
		
		//Obtenemos el numero de la primera pregunta "la unica q hemos aï¿½adido"
		int qNum = qVec.get(0).getQuestionNumber();
    	try {
    		//Aï¿½adimos el dinero
    		sut.addDinero(uu1.getUserName(), Float.parseFloat("100"), false);
    		
    		//Ponemos las opciones del pronostico
    		Vector<String> vec1= new Vector<String>();
    		vec1.add("Local"); vec1.add("Empate"); vec1.add("Visitante");
    		
    		//Las ganancias de cada opcion del pronostico
    		Vector<Float> vec2= new Vector<Float>();
    		vec2.add(Float.parseFloat("0.2")); vec2.add(Float.parseFloat("0.5")); vec2.add(Float.parseFloat("0.3"));
    		
    		//Aï¿½adimos el pronostico
    		sut.anadirPronostico(qNum, vec1, vec2);
    		
    		//Aï¿½adimos la apuesta de Paco22
    		sut.anadirApuesta(uu1.getUserName(), qNum, Float.parseFloat("50"), "Empate");
    		
    		//Le damos un resultado
    		sut.setResult(qNum, "Empate");
    		
    		//Le asignamos lo gandado al usuario (Esto en la app lo gestiona la GUI)
    		sut.addDinero(uu1.getUserName(), 75, true);
    		
            Vector<Object> result = sut.getUsersMasGanancias();
            
            assertTrue(!result.isEmpty() && sut.getUser("Paco22").getTarjeta().getDinero()==Float.parseFloat("125"));
            
    	}catch(Exception e) {
    		fail("Mal");
    	}finally {
    		testDA.open();
    		//Eliminamos la apuesta realizada
    		testDA.eliminarApuesta(uu1, qNum);
    		//Eliminamos el pronostico añadido
    		testDA.eliminarPronostico(qNum);
    		//Eliminamos el usuario junto a sus transacciones y su tarjeta
    		testDA.eliminarUser(uu1);
    		//Eliminamos el evento junto a las preguntas añadidas
    		testDA.removeEvent(ev30);
    		testDA.close();
    	}
    }

}

