package domain;

import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
@Entity
public class Pronosticos {
	@Id
	private int questionNum;
	@OneToMany(fetch=FetchType.EAGER)
	private Vector<String>opciones= new Vector<String>();
	private boolean hayPronostico= false;
	@OneToMany(fetch=FetchType.EAGER)
	private Vector<Float>porcentajes=new Vector<Float>();
	
	public Pronosticos(int num, Vector<String>vect, Vector<Float>vect2) {
		this.questionNum=num;
		setPronostico(vect, vect2);
		this.hayPronostico= true;
	}

	
	public void setQuestionNum(int num) {
		questionNum=num;
	}
	
	public int getQuestionNum() {
		return questionNum;
	}
	
	public Vector<String> getOpciones(){
		return opciones;
	}
	
	public Vector<Float> getPorcentajes(){
		return porcentajes;
	}
	
	public boolean getHayPronostico() {
		return hayPronostico;
	}
	
	public void setHayPronostico(boolean b) {
		hayPronostico= b;
	}
	
	
	public void setPronostico(Vector<String> vector, Vector<Float>vector2) {
		if(!hayPronostico && vector.size()>0 && vector.size()<4) {
			if(vector.size()==2) {
				set2Pronosticos(vector.get(0), vector.get(1), vector2.get(0), vector2.get(1));
			}else if(vector.size()==3) {
				set3Pronosticos(vector.get(0), vector.get(1), vector.get(2), vector2.get(0), vector2.get(1), vector2.get(2));
			}
			hayPronostico=true;
		}
		
	}
	
	private void set2Pronosticos(String a, String b, Float p1, Float p2) {
		opciones.add(0, a);
		opciones.add(1, b);
		porcentajes.add(0, p1);
		porcentajes.add(1, p2);
	}
	
	private void set3Pronosticos(String a, String b, String c, Float p1, Float p2, Float p3) {
		opciones.add(0, a);
		opciones.add(1, b);
		opciones.add(2, c);
		porcentajes.add(0, p1);
		porcentajes.add(1, p2);
		porcentajes.add(2, p3);
	}

}
