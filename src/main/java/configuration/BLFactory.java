package configuration;

import java.awt.Color;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import businesslogic.BLFacade;
import businesslogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import gui.MainGUI;

public class BLFactory {
	public BLFactory() {
		
	}

	public BLFacade generateBLogic(ConfigXML c, MainGUI a) {
		BLFacade appFacadeInterface;
		try {
			if (c.isBusinessLogicLocal()) {
				DataAccess da = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
				appFacadeInterface = new BLFacadeImplementation(da);
			} else { // If remote
				String serviceName = "http://" + c.getBusinessLogicNode() + ":" + c.getBusinessLogicPort() + "/ws/"
						+ c.getBusinessLogicName() + "?wsdl";
				URL url = new URL(serviceName);
				QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
				Service service = Service.create(url, qname);
				appFacadeInterface = service.getPort(BLFacade.class);
			}
			return appFacadeInterface;
		} catch (Exception e) {
			a.jLabelSelectOption.setText("Error: " + e.toString());

			a.jLabelSelectOption.setForeground(Color.RED);

			System.out.println("Error in ApplicationLauncher: " + e.toString());
		}
		return null;
	}
}
