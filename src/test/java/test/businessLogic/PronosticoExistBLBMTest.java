package test.businessLogic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;

@RunWith(MockitoJUnitRunner.class)
public class PronosticoExistBLBMTest {
	@Mock
	DataAccess DAO;
	@InjectMocks
	BLFacadeImplementation sut;

	@Test
	public void pronosticosExistTest() {
		Mockito.doReturn(true).when(DAO).pronosticosExist(33);
		try {
			assertTrue(sut.pronosticosExist(33));
		} catch (Exception e) {
			fail("error");
		}
	}

	@Test
	public void pronosticosNotExistTest() {
		Mockito.doReturn(false).when(DAO).pronosticosExist(66);
		try {
			assertFalse(sut.pronosticosExist(66));
		} catch (Exception e) {
			fail("error");
		}
	}
}
