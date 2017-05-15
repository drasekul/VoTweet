package model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the actor database table.
 * 
 */
@Entity
@Table(name="candidato")
@NamedQueries({
	@NamedQuery(name="eliminar candidato por id", query="DELETE FROM Candidato c WHERE c.cdtoId = :id")
})
public class Candidato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cdto_id", unique=true, nullable=false)
	private int cdtoId;

	@Column(name="ptdo_id")
	private int ptdoId;
	
	@Column(name="cdto_nombre",length=255)
	private String cdtoNombre;
	
	@Column(name="cdto_cuenta_twitter",length=255)
	private String cdtoCuentaTwitter;
	
	@Column(name="cdto_fecha_nacimiento")
	private Date cdtoFechaNacimiento;
	
	@Column(name="cdto_edad")
	private String cdtoEdad;
	
	@Column(name="cdto_descripcion")
	private String cdtoDescripcion;
	
	@Column(name="cdto_imagen", length=255)
	private String cdtoImagen;
	
	@Column(name="cdto_activo")
	private boolean cdtoActivo;

	public int getCdtoId() {
		return cdtoId;
	}

	public void setCdtoId(int cdtoId) {
		this.cdtoId = cdtoId;
	}

	public int getPtdoId() {
		return ptdoId;
	}

	public void setPtdoId(int ptdoId) {
		this.ptdoId = ptdoId;
	}

	public String getCdtoNombre() {
		return cdtoNombre;
	}

	public void setCdtoNombre(String cdtoNombre) {
		this.cdtoNombre = cdtoNombre;
	}

	public String getCdtoCuentaTwitter() {
		return cdtoCuentaTwitter;
	}

	public void setCdtoCuentaTwitter(String cdtoCuentaTwitter) {
		this.cdtoCuentaTwitter = cdtoCuentaTwitter;
	}

	public Date getCdtoFechaNacimiento() {
		return cdtoFechaNacimiento;
	}

	public void setCdtoFechaNacimiento(Date cdtoFechaNacimiento) {
		this.cdtoFechaNacimiento = cdtoFechaNacimiento;
	}

	public String getCdtoEdad() {
		return cdtoEdad;
	}

	public void setCdtoEdad(String cdtoEdad) {
		this.cdtoEdad = cdtoEdad;
	}

	public String getCdtoDescripcion() {
		return cdtoDescripcion;
	}

	public void setCdtoDescripcion(String cdtoDescripcion) {
		this.cdtoDescripcion = cdtoDescripcion;
	}

	public String getCdtoImagen() {
		return cdtoImagen;
	}

	public void setCdtoImagen(String cdtoImagen) {
		this.cdtoImagen = cdtoImagen;
	}

	public boolean isCdtoActivo() {
		return cdtoActivo;
	}

	public void setCdtoActivo(boolean cdtoActivo) {
		this.cdtoActivo = cdtoActivo;
	}
	
	
}