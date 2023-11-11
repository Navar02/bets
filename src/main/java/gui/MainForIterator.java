package gui;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import businessLogic.BLFacade;
import businessLogic.ExtendedIterator;
import configuration.BLFactory;
import configuration.UtilDate;
import domain.Event;

public class MainForIterator {
	public static void main(String[] args)	{
	//	obtener el	objeto Facade	local
	int isLocal =	1;
	BLFacade	blFacade =	new BLFactory().getBusinessLogicFactory(isLocal);
	//SimpleDateFormat	sdf =	new SimpleDateFormat("dd/MM/yyyy");
	Calendar today = Calendar.getInstance();
	int month=today.get(Calendar.MONTH);
	month+=1;
	int year=today.get(Calendar.YEAR);
	if (month==12) { month=0; year+=1;}
	Date	date = UtilDate.newDate(year,month,17);
	try {
		System.out.println("llega aqui");
		//date = sdf.parse("17/12/2023"); //	17	del	mes	que	viene
		ExtendedIterator<Event>	i =	blFacade.getEventsIterator(date);
		Event	e;
		System.out.println("_____________________");
		System.out.println("RECORRIDO	HACIA	ATRÁS");
		i.goLast();	//	Hacia atrás
		while (i.hasPrevious())	{
			e =	i.previous();
			System.out.println(e.toString());
		}
		System.out.println();
		System.out.println("_____________________");
		System.out.println("RECORRIDO	HACIA	ADELANTE");
		i.goFirst();	//	Hacia adelante
		while (i.hasNext())	{
			e =	i.next();
			System.out.println(e.toString());
		}
	} catch (Exception	e1)	{
		System.out.println("Problems	with	date??	" +	date);
	}
}}