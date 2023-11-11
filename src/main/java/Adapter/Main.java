package Adapter;

import domain.User;
import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import dataAccess.DataAccess;

public class Main {

	public static void main(String[] args) {
		try {
			ConfigXML c = ConfigXML.getInstance();
			BLFacade blFacade;
			DataAccess da = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			blFacade = new BLFacadeImplementation(da);
			User user = blFacade.getUserByName("Juan");
			WindowTable vt = new WindowTable(user);
			vt.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
