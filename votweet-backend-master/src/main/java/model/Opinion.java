package model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

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
@Table(name="opinion")
@NamedQueries({
	
})
public class Opinion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="opinion_id", unique=true, nullable=false)
	private int opinionId;

	@Column(name="cdto_id")
	private int cdtoId;
	
	@Column(name="opinion_fecha")
	private Timestamp opinionFecha;
	
	@Column(name="opinion_sentimiento")
	private int opinionSentimiento;
	
	@Column(name="opinion_ubicacion_pais",length=225)
	private String opinionUbicacionPais;
	
	@Column(name="opinion_ubicacion_region",length=225)
	private String opinionUbicacionRegion;
	
	@Column(name="opinion_ubicacion_ciudad",length=225)
	private String opinionUbicacionCiudad;
	
	@Column(name="opinion_retweets")
	private int opinionRetweets;
	
	@Column(name="opinion_likes")
	private int opinionLikes;
	
	@Column(name="opinion_menciona_candidato")
	private boolean opinionMencionaCandidato;
	
	@Column(name="opinion_resp_candidato")
	private boolean opinionRespCandidato;

	
	
	
}