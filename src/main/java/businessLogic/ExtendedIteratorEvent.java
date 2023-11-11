package businessLogic;

import java.util.List;

import domain.Event;
import businessLogic.ExtendedIterator;

public class ExtendedIteratorEvent implements ExtendedIterator{
	
	List<Event> eventos;
	int pos=0;
	
	public ExtendedIteratorEvent(List<Event> ev) {
		this.eventos=ev;
	}
	
	//return    the    actual    element    and    go    to    the    previous
	public Event previous(){
		Event evento=eventos.get(pos);
		pos--;
		return evento;
	}
	        
	//true    if ther    is    a    previous    element
	public boolean hasPrevious() {
		return pos>-1;
	}
			
	//It	is	placed	in	the	first	element
	public void goFirst() {
		pos=0;
	}
			
	// It	is	placed	in	the	last	element
	public void goLast() {
		pos=eventos.size()-1;
	}

	@Override
	public boolean hasNext() {
		return pos< eventos.size();
	}

	@Override
	public Event next() {
		Event evento =eventos.get(pos);
		pos ++;
		return evento;
	}
	
}
