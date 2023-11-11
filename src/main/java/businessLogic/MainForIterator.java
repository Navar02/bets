package businessLogic;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import configuration.BLFactory;
import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Event;
import exceptions.EventAlreadyExist;

public class MainForIterator {
	public static void main(String[] args)	{
		// obtener el objeto Facade local
		ConfigXML c=ConfigXML.getInstance();
		BLFacade blFacade;
		DataAccess da = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
		blFacade = new BLFacadeImplementation(da);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			date = sdf.parse("17/12/2023"); // 17 del mes que viene
			ExtendedIterator<Event> i = blFacade.getEventsIterator(date);
			Event e;
			System.out.println("_____________________");
			System.out.println("RECORRIDO HACIA ATRÃ�S");
			i.goLast(); // Hacia atrÃ¡s
			while (i.hasPrevious()) {
				e = i.previous();
				System.out.println(e.toString());
			}
			System.out.println("fin recorrido hacia atras");
			System.out.println("_____________________");
			System.out.println("RECORRIDO HACIA ADELANTE");
			i.goFirst(); // Hacia adelante
			while (i.hasNext()) {
				e = i.next();
				System.out.println(e.toString());
			}
			System.out.println("fin recorrido hacia delante");
		} catch (ParseException e) {
			System.out.println("Problems with date?? " + "17/12/2020");
		}
		
}}