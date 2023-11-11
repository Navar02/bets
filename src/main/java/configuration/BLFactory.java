package configuration;

import java.awt.Color;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import gui.MainGUI;

public class BLFactory {
	
	private BLFacade appFacadeInterface;
	
	public BLFactory() {
		
	}

	public BLFacade generateBLogic(ConfigXML c) throws Exception {
		BLFacade appFacadeInterface;
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
	}
	
	
}
