package ejb;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.CandidatoFacade;
import model.Candidato;

@Stateless
public class CandidatoFacadeEJB extends AbstractFacade<Candidato> implements CandidatoFacade {
	
	
	@PersistenceContext(unitName = "votweetPU")
	private EntityManager em;
	
	public CandidatoFacadeEJB() {
		super(Candidato.class);
	}
	

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}



}