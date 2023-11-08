package gui;

import java.awt.Color;
import java.net.URL;
import java.util.Locale;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import businesslogic.BLFacade;
import businesslogic.BLFacadeImplementation;
import configuration.BLFactory;
import configuration.ConfigXML;
import dataAccess.DataAccess;

public class ApplicationLauncher { 
	
	
	
	public static void main(String[] args) {
		ConfigXML c=ConfigXML.getInstance();
		System.out.println(c.getLocale());
		Locale.setDefault(new Locale(c.getLocale()));
		System.out.println("Locale: "+Locale.getDefault());
		MainGUI a=new MainGUI();
		a.setVisible(true);
		
		try {
		BLFacade appFacadeInterface; //product
		UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		
		BLFactory fc= new BLFactory(); //Creator
		appFacadeInterface=fc.generateBLogic(c,a); //concrete Product (BLFacadeImplementation)
		
		MainGUI.setBussinessLogic(appFacadeInterface);
		
		}
		catch (Exception e) {
			
		a.jLabelSelectOption.setText("Error: "+e.toString());
		
		a.jLabelSelectOption.setForeground(Color.RED);
		
		System.out.println("Error in ApplicationLauncher: "+e.toString());
		}
		}

}
