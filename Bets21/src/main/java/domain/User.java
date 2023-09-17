package domain;

import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	@Id
	private String userName; // Clave primaria
	
	private boolean vetado;
	private String correo;
	private String password;
	private boolean admin;
	private Tarjeta tarjeta;
	@OneToMany(fetch=FetchType.EAGER)
	private Vector<Apuesta>vecApuestas;
	
	private String rutaImagen;
	
	public User(String name, String password, String numTarjeta, String correo) {
		this.userName = name;
		this.password = password;
		this.admin= false;
		this.tarjeta= new Tarjeta(numTarjeta);
		this.correo= correo;
		this.vetado=false;
		rutaImagen="";
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String pass) {
		password= pass;
	}
	
	public void setAdmin(boolean b) {
		admin=b;
	}
	
	public boolean isAdmin() {
		return admin;
	}
	
	public Tarjeta getTarjeta(){
		return tarjeta;
	}
	
	public void setTarjeta(Tarjeta t) {
		tarjeta=t;
	}
	
	public void setCorreo(String correo) {
		this.correo= correo;
	}
	
	public String getCorreo() {
		return correo;
	}
	
	public void setVeto(boolean a) {
		this.vetado=a;
	}
	
	public boolean getVeto() {
		return vetado;
	}
	
	public void addDinero(float money) {
		tarjeta.addDinero(money);
	}
	
	public void addVecApuestas(Apuesta apuesta) {
		if(this.vecApuestas==null) {
			this.vecApuestas= new Vector<Apuesta>();
		}
		this.vecApuestas.add(apuesta);
	}
	
	public Vector<Apuesta> getVecApuestas(){
		return vecApuestas;
	}
	
	public void setRutaImagen(String ruta) {
		rutaImagen= ruta;
	}
	
	public String getRutaImagen() {
		return rutaImagen;
	}
}
