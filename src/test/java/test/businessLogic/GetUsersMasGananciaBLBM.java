package test.businessLogic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Vector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import businesslogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.User;

@RunWith(MockitoJUnitRunner.class)
public class GetUsersMasGananciaBLBM {
	@Mock
	DataAccess DAO;
	@InjectMocks
	BLFacadeImplementation sut;
	
	@Test
	public void getUserEmptyTest() {
		Mockito.doReturn(new Vector<Object>()).when(DAO).getUsersMasGanancias();
		try {
			assertTrue(sut.getUsersMasGanacias().isEmpty());
		}catch(Exception e) {
			fail("error");
		}
	}
	
	@Test
	public void getUserNoEmptyTest() {
		Vector<User> vec= new Vector<User>();
		vec.add(new User("Max Verstappen", null, null, null));
		Mockito.doReturn(vec).when(DAO).getUsersMasGanancias();
		try {
			assertFalse(sut.getUsersMasGanacias().isEmpty());
		}catch(Exception e) {
			fail("error");
		}
	}
}
