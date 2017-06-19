package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="cdto_met")
@NamedQueries({
	@NamedQuery(name="CandidatoMetrica.findByCdtoId()", query="SELECT cm FROM CandidatoMetrica cm WHERE cm.cdtoId= :idCandidato")	
})
public class CandidatoMetrica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cdto_id", nullable=false)
	private int cdtoId;
	
	@Id
	@Column(name="met_id", nullable=false)
	private int metId;

	/**
	 * @return the cdtoId
	 */
	public int getCdtoId() {
		return cdtoId;
	}

	/**
	 * @param cdtoId the cdtoId to set
	 */
	public void setCdtoId(int cdtoId) {
		this.cdtoId = cdtoId;
	}

	/**
	 * @return the metId
	 */
	public int getMetId() {
		return metId;
	}

	/**
	 * @param metId the metId to set
	 */
	public void setMetId(int metId) {
		this.metId = metId;
	}
	
	
}
