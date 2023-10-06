package domain;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
public class Transaccion {
	private float cantidad;
	@Id
//	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private int numTran;
	private String numTarjeta;
	private Date fecha;
	private float dineroActual;
	private boolean apuesta;
	
	public Transaccion(float cant, String numTarjeta, Date f, float dA, boolean a) {
		this.cantidad= cant;
		this.numTarjeta= numTarjeta;
		this.fecha= f;
		this.dineroActual=dA;
		this.apuesta= a;
//		numTran++;
	}
	
	public void setCant(float cant) {
		this.cantidad= cant;
	}
	
	public float getCant() {
		return cantidad;
	}
	
	public String getUserName() {
		return numTarjeta;
	}
	
	public Date getDate() {
		return fecha;
	}
	
	public float getDineroActualT() {
		return dineroActual;
	}
	
	public void setApuesta(boolean a) {
		this.apuesta=a;
	}
	
	public boolean isApuesta() {
		return apuesta;
	}

}
