package model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the opinion database table.
 * 
 */
@SuppressWarnings("unused")
@Entity
@Table(name="opinion")
@NamedQueries({
@NamedQuery(name="Opinion.findAll", query="SELECT o FROM Opinion o"),
@NamedQuery(name="opiniones mas importantes acerca de un candidato", query="SELECT o FROM Opinion o WHERE o.cdtoId=:idCandidato AND o.opinionAutor!=:cuentaCandidato AND (o.opinionPaisUbicacion='Chile' OR o.opinionPaisUbicacion='none') ORDER BY o.opinionRetweets DESC"),
@NamedQuery(name="opiniones escritas por un usuario", query="SELECT o FROM Opinion o WHERE o.opinionAutor=:screenNameUsuario"),
@NamedQuery(name="opiniones internacionales mas importantes acerca de un candidato", query="SELECT o FROM Opinion o WHERE o.cdtoId=:idCandidato AND o.opinionAutor!=:cuentaCandidato AND o.opinionPaisUbicacion='Extranjero' ORDER BY o.opinionRetweets DESC")
})
public class Opinion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="opinion_id", unique=true, nullable=false)
	private int opinionId;

	@Column(name="opinion_activo")
	private boolean opinionActivo;

	@Column(name="opinion_autor", length=255)
	private String opinionAutor;

	@Column(name="opinion_ciudad_ubicacion",length=255)
	private String opinionCiudadUbicacion;

	@Column(name="opinion_fecha")
	private Timestamp opinionFecha;

	@Column(name="opinion_likes")
	private int opinionLikes;

	@Column(name="opinion_menciona_candidato")
	private boolean opinionMencionaCandidato;

	@Column(name="opinion_pais_ubicacion",length=255)
	private String opinionPaisUbicacion;

	@Column(name="opinion_region_ubicacion",length=255)
	private String opinionRegionUbicacion;

	@Column(name="opinion_resp_candidato")
	private boolean opinionRespCandidato;

	@Column(name="opinion_retweets")
	private int opinionRetweets;

	@Column(name="opinion_sentimiento")
	private int opinionSentimiento;

	@Column(name="cdto_id")
	private int cdtoId;

	public int getOpinionId() {
		return opinionId;
	}

	public void setOpinionId(int opinionId) {
		this.opinionId = opinionId;
	}

	public boolean isOpinionActivo() {
		return opinionActivo;
	}

	public void setOpinionActivo(boolean opinionActivo) {
		this.opinionActivo = opinionActivo;
	}

	public String getOpinionAutor() {
		return opinionAutor;
	}

	public void setOpinionAutor(String opinionAutor) {
		this.opinionAutor = opinionAutor;
	}

	public String getOpinionCiudadUbicacion() {
		return opinionCiudadUbicacion;
	}

	public void setOpinionCiudadUbicacion(String opinionCiudadUbicacion) {
		this.opinionCiudadUbicacion = opinionCiudadUbicacion;
	}

	public Timestamp getOpinionFecha() {
		return opinionFecha;
	}

	public void setOpinionFecha(Timestamp opinionFecha) {
		this.opinionFecha = opinionFecha;
	}

	public int getOpinionLikes() {
		return opinionLikes;
	}

	public void setOpinionLikes(int opinionLikes) {
		this.opinionLikes = opinionLikes;
	}

	public boolean isOpinionMencionaCandidato() {
		return opinionMencionaCandidato;
	}

	public void setOpinionMencionaCandidato(boolean opinionMencionaCandidato) {
		this.opinionMencionaCandidato = opinionMencionaCandidato;
	}

	public String getOpinionPaisUbicacion() {
		return opinionPaisUbicacion;
	}

	public void setOpinionPaisUbicacion(String opinionPaisUbicacion) {
		this.opinionPaisUbicacion = opinionPaisUbicacion;
	}

	public String getOpinionRegionUbicacion() {
		return opinionRegionUbicacion;
	}

	public void setOpinionRegionUbicacion(String opinionRegionUbicacion) {
		this.opinionRegionUbicacion = opinionRegionUbicacion;
	}

	public boolean isOpinionRespCandidato() {
		return opinionRespCandidato;
	}

	public void setOpinionRespCandidato(boolean opinionRespCandidato) {
		this.opinionRespCandidato = opinionRespCandidato;
	}

	public int getOpinionRetweets() {
		return opinionRetweets;
	}

	public void setOpinionRetweets(int opinionRetweets) {
		this.opinionRetweets = opinionRetweets;
	}

	public int getOpinionSentimiento() {
		return opinionSentimiento;
	}

	public void setOpinionSentimiento(int opinionSentimiento) {
		this.opinionSentimiento = opinionSentimiento;
	}

	public int getCdtoId() {
		return cdtoId;
	}

	public void setCdtoId(int cdtoId) {
		this.cdtoId = cdtoId;
	}
	
	

}