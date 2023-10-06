package domain;


import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Tarjeta {

	@Id
	private String numTarjeta;
	private float dinero;
	@OneToMany(fetch=FetchType.EAGER)
	private Vector<Transaccion> transacciones= new Vector<Transaccion>();
	
	
	public Tarjeta(String numTarjeta) {
		this.dinero= 0;
		this.numTarjeta= numTarjeta;
		setDinero(0);
	}
	
	
	public float addDinero(float money) {
		dinero= dinero+money;
		return dinero;
	}
	
	public void setDinero(float n) {
		dinero=n;
	}
	
	public String getNumTarjeta() {
		return numTarjeta;
	}
	
	public float getDinero() {
		return dinero;
	}

	
	
	public Vector<Transaccion> getTransaccioness(){
		return transacciones;
	}
	
	public void setTransacciones(Transaccion tran) {
		if(transacciones == null) {
			transacciones= new Vector<Transaccion>();
		}
		transacciones.add(tran);
	}
}
