package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the metrica database table.
 * 
 */
@Entity
@Table(name="metrica")
@NamedQuery(name="Metrica.findAll", query="SELECT m FROM Metrica m")
public class Metrica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="met_id", unique=true, nullable=false)
	private int metId;

	@Column(name="met_activo")
	private boolean metActivo;

	@Column(name="met_fecha")
	private Timestamp metFecha;

	@Column(name="met_nombre", length=255)
	private String metNombre;

	@Column(name="met_valor")
	private double metValor;

	public int getMetId() {
		return metId;
	}

	public void setMetId(int metId) {
		this.metId = metId;
	}

	public boolean isMetActivo() {
		return metActivo;
	}

	public void setMetActivo(boolean metActivo) {
		this.metActivo = metActivo;
	}

	public Timestamp getMetFecha() {
		return metFecha;
	}

	public void setMetFecha(Timestamp metFecha) {
		this.metFecha = metFecha;
	}

	public String getMetNombre() {
		return metNombre;
	}

	public void setMetNombre(String metNombre) {
		this.metNombre = metNombre;
	}

	public double getMetValor() {
		return metValor;
	}

	public void setMetValor(double metValor) {
		this.metValor = metValor;
	}

	
}