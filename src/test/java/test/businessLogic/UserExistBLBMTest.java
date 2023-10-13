package test.businessLogic;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import businesslogic.BLFacadeImplementation;
import dataAccess.DataAccess;

@RunWith(MockitoJUnitRunner.class)
public class UserExistBLBMTest {
		@Mock
		DataAccess DAO;
		@InjectMocks
		BLFacadeImplementation sut;
	
	@Test
	public void userExistTest() {
		Mockito.doReturn(true).when(DAO).userExist("Paco");
		try {
			assertTrue(sut.userExist("Paco"));
		}catch(Exception e) {
			fail("error");
		}
	}
	
	@Test
	public void userNotExistTest() {
		Mockito.doReturn(false).when(DAO).userExist("Elemao");
		try {
			assertFalse(sut.userExist("Elemao"));
		}catch(Exception e) {
			fail("error");
		}
	}
	
	@Test
	public void userNotExistNullTest() {
		Mockito.doReturn(false).when(DAO).userExist(null);
		try {
			assertFalse(sut.userExist(null));
		}catch(Exception e) {
			fail("error");
		}
	}

   
}
