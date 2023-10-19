package test.dataAccess;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import dataAccess.DataAccessUserExists;
import domain.User;

public class DABuserExist2 {
	private DataAccessUserExists sut;
	static TestDataAccess testDA;
	@Test
    public void testUserExistWithExistingUser() {
		//Set test y sut
        sut = new DataAccessUserExists();
        testDA = new TestDataAccess();
        //A�adimos le usuario y lo obtenemos
        sut.anadirUsuario("Paco22", "123", "1111111111111122", "tu@email.com");
        User u = sut.getUser("Paco22");
        try {
        	assertTrue(sut.userExist(u.getUserName()));
        }catch(Exception e) {
        	fail("No funciona bien");
        } finally {
        	//Eliminamos el usuario y junto a el sus transacciones y tarjeta
        	testDA.open();
        	testDA.eliminarUser(u);
        	testDA.close();
        }
    }

//    @Test
//    public void testUserExistWithNonExistingUser() {
//    	//Set test y sut
//    	sut = new DataAccessUserExists();
//        testDA = new TestDataAccess();
//        //A�adimos el usuario
//        sut.anadirUsuario("Paco22", "123", "1111111111111122", "tu@email.com");
//        //Obtenemos un usuario q no existe y luego el a�adidio para eliminarlo
//        User u = sut.getUser("Paco21");
//        User u2 = sut.getUser("Paco22");
//        try {
//        	assertFalse(sut.userExist("Paco21"));
//        	
//        }catch(Exception e) {
//        	fail("Mal, Paco21 no deberia de existir");
//        } finally {
//        	//Eliminamos el user a�adido
//        	testDA.open();
//        	testDA.eliminarUser(u2);
//        	testDA.close();
//        }
//    }
    
//    @Test
//    public void testUserExistWitNullUser() {
//    	//Set test y sut
//    	sut = new DataAccessUserExists();
//        testDA = new TestDataAccess();
//        //A�adimos el user
//        sut.anadirUsuario("Paco22", "123", "1111111111111122", "tu@email.com");
//        //Obtenemos un usuario q no existe y luego el a�adidio para eliminarlo
//        User u = sut.getUser("Paco21");
//        User u2 = sut.getUser("Paco22");
//        try {
//        	//Si da true esta mal porq el user no deberia de existir de ahi el fail
//        	if (sut.userExist(u.getUserName())) {
//        		fail("No deberia de existir");
//        	}
//        	
//        }catch(Exception e) {
//        	assertTrue(true);
//        } finally {
//        	// Eliminamos user
//        	testDA.open();
//        	testDA.eliminarUser(u2);
//        	testDA.close();
//        }
//    }
}
