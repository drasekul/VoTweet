package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="usuario")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="usr_id", unique=true, nullable=false)
	private int usrId;

	@Column(name="usr_activo")
	private boolean usrActivo;

	@Column(name="usr_apellido", length=255)
	private String usrApellido;

	@Column(name="usr_correo",length=255, unique=true)
	private String usrCorreo;

	@Column(name="usr_fecha_registro")
	private Timestamp usrFechaRegistro;

	@Column(name="usr_nombre",length=255)
	private String usrNombre;

	@Column(name="usr_password",length=255)
	private String usrPassword;

	@Column(name="usr_privilegio")
	private int usrPrivilegio;

	@Column(name="cdto_id")
	private int cdtoId;

	public int getUsrId() {
		return usrId;
	}

	public void setUsrId(int usrId) {
		this.usrId = usrId;
	}

	public boolean isUsrActivo() {
		return usrActivo;
	}

	public void setUsrActivo(boolean usrActivo) {
		this.usrActivo = usrActivo;
	}

	public String getUsrApellido() {
		return usrApellido;
	}

	public void setUsrApellido(String usrApellido) {
		this.usrApellido = usrApellido;
	}

	public String getUsrCorreo() {
		return usrCorreo;
	}

	public void setUsrCorreo(String usrCorreo) {
		this.usrCorreo = usrCorreo;
	}

	public Timestamp getUsrFechaRegistro() {
		return usrFechaRegistro;
	}

	public void setUsrFechaRegistro(Timestamp usrFechaRegistro) {
		this.usrFechaRegistro = usrFechaRegistro;
	}

	public String getUsrNombre() {
		return usrNombre;
	}

	public void setUsrNombre(String usrNombre) {
		this.usrNombre = usrNombre;
	}

	public String getUsrPassword() {
		return usrPassword;
	}

	public void setUsrPassword(String usrPassword) {
		this.usrPassword = usrPassword;
	}

	public int getUsrPrivilegio() {
		return usrPrivilegio;
	}

	public void setUsrPrivilegio(int usrPrivilegio) {
		this.usrPrivilegio = usrPrivilegio;
	}

	public int getCdtoId() {
		return cdtoId;
	}

	public void setCdtoId(int cdtoId) {
		this.cdtoId = cdtoId;
	}

	

}