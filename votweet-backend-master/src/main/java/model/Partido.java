package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the partido database table.
 * 
 */
@SuppressWarnings("unused")
@Entity
@Table(name="partido")
@NamedQuery(name="Partido.findAll", query="SELECT p FROM Partido p")
public class Partido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ptdo_id", unique=true, nullable= false)
	private int ptdoId;

	@Column(name="ptdo_activo")
	private boolean ptdoActivo;

	@Lob
	@Column(name="ptdo_descripcion")
	private String ptdoDescripcion;

	@Column(name="ptdo_logo", length=255)
	private String ptdoLogo;

	@Column(name="ptdo_nombre", length=255)
	private String ptdoNombre;

	public int getPtdoId() {
		return ptdoId;
	}

	public void setPtdoId(int ptdoId) {
		this.ptdoId = ptdoId;
	}

	public boolean isPtdoActivo() {
		return ptdoActivo;
	}

	public void setPtdoActivo(boolean ptdoActivo) {
		this.ptdoActivo = ptdoActivo;
	}

	public String getPtdoDescripcion() {
		return ptdoDescripcion;
	}

	public void setPtdoDescripcion(String ptdoDescripcion) {
		this.ptdoDescripcion = ptdoDescripcion;
	}

	public String getPtdoLogo() {
		return ptdoLogo;
	}

	public void setPtdoLogo(String ptdoLogo) {
		this.ptdoLogo = ptdoLogo;
	}

	public String getPtdoNombre() {
		return ptdoNombre;
	}

	public void setPtdoNombre(String ptdoNombre) {
		this.ptdoNombre = ptdoNombre;
	}

	
}