package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Apuesta {

	@Id
	private String userName;
	@Id
	private int questionNum;
	private String pronostico;
	private float cantidad;
	
	public Apuesta(String name, int qNum, float cantidad, String pronostico) {
		this.userName=name;
		this.questionNum= qNum;
		this.cantidad= cantidad;
		this.pronostico= pronostico;
	}
	
	public void setUsName(String name) {
		this.userName= name;
	}
	
	public String getName() {
		return userName;
	}
	
	public void setQNum(int qNum) {
		this.questionNum= qNum;
	}
	
	public int getQNum() {
		return this.questionNum;
	}
	
	public void setCantidad(float cant) {
		this.cantidad= cant;
	}
	
	public float getCantidad() {
		return cantidad;
	}
	
	public void setPronostico(String pronostico) {
		this.pronostico=pronostico;
	}
	
	
	public String getPronostico() {
		return pronostico;
	}
	
}
