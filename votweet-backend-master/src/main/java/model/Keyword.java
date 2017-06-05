package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the keyword database table.
 * 
 */
@Entity
@Table(name="keyword")
@NamedQuery(name="Keyword.findAll", query="SELECT k FROM Keyword k")
public class Keyword implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="kwd_id", unique=true, nullable=false)
	private int kwdId;

	@Column(name="kwd_activo")
	private boolean kwdActivo;

	@Column(name="kwd_texto", length=255)
	private String kwdTexto;
	
	@Column(name="cdto_id",nullable=false)
	private int cdtoId;

	public int getKwdId() {
		return kwdId;
	}

	public void setKwdId(int kwdId) {
		this.kwdId = kwdId;
	}

	public boolean isKwdActivo() {
		return kwdActivo;
	}

	public void setKwdActivo(boolean kwdActivo) {
		this.kwdActivo = kwdActivo;
	}

	public String getKwdTexto() {
		return kwdTexto;
	}

	public void setKwdTexto(String kwdTexto) {
		this.kwdTexto = kwdTexto;
	}

	public int getCdtoId() {
		return cdtoId;
	}

	public void setCdtoId(int cdtoId) {
		this.cdtoId = cdtoId;
	}
	
	
}