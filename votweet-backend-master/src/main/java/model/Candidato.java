package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the candidato database table.
 * 
 */
@Entity
@Table(name="candidato")
@NamedQueries({
@NamedQuery(name="Candidato.findAll", query="SELECT c FROM Candidato c")
})
public class Candidato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cdto_id", unique=true, nullable= false)
	private int cdtoId;

	@Column(name="cdto_activo")
	private boolean cdtoActivo;

	@Column(name="cdto_cuenta_twitter", length=255)
	private String cdtoCuentaTwitter;

	@Lob
	@Column(name="cdto_descripcion")
	private String cdtoDescripcion;

	@Column(name="cdto_edad")
	private int cdtoEdad;

	@Temporal(TemporalType.DATE)
	@Column(name="cdto_fecha_nacimiento")
	private Date cdtoFechaNacimiento;

	@Column(name="cdto_imagen")
	private String cdtoImagen;

	@Column(name="cdto_nombre")
	private String cdtoNombre;

	@Column(name="ptdo_id")
	private int ptdoId;
	
	@Column(name="urlInicio")
	private String urlInicio;
	
	

	public String getUrlInicio() {
		return urlInicio;
	}

	public void setUrlInicio(String urlInicio) {
		this.urlInicio = urlInicio;
	}

	public int getCdtoId() {
		return cdtoId;
	}

	public void setCdtoId(int cdtoId) {
		this.cdtoId = cdtoId;
	}

	public boolean isCdtoActivo() {
		return cdtoActivo;
	}

	public void setCdtoActivo(boolean cdtoActivo) {
		this.cdtoActivo = cdtoActivo;
	}

	public String getCdtoCuentaTwitter() {
		return cdtoCuentaTwitter;
	}

	public void setCdtoCuentaTwitter(String cdtoCuentaTwitter) {
		this.cdtoCuentaTwitter = cdtoCuentaTwitter;
	}

	public String getCdtoDescripcion() {
		return cdtoDescripcion;
	}

	public void setCdtoDescripcion(String cdtoDescripcion) {
		this.cdtoDescripcion = cdtoDescripcion;
	}

	public int getCdtoEdad() {
		return cdtoEdad;
	}

	public void setCdtoEdad(int cdtoEdad) {
		this.cdtoEdad = cdtoEdad;
	}

	public Date getCdtoFechaNacimiento() {
		return cdtoFechaNacimiento;
	}

	public void setCdtoFechaNacimiento(Date cdtoFechaNacimiento) {
		this.cdtoFechaNacimiento = cdtoFechaNacimiento;
	}

	public String getCdtoImagen() {
		return cdtoImagen;
	}

	public void setCdtoImagen(String cdtoImagen) {
		this.cdtoImagen = cdtoImagen;
	}

	public String getCdtoNombre() {
		return cdtoNombre;
	}

	public void setCdtoNombre(String cdtoNombre) {
		this.cdtoNombre = cdtoNombre;
	}

	public int getPtdoId() {
		return ptdoId;
	}

	public void setPtdoId(int ptdoId) {
		this.ptdoId = ptdoId;
	}

	

}