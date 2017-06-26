package ejb;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.CandidatoMetricaFacade;
import model.CandidatoMetrica;

@Stateless
public class CandidatoMetricaFacadeEJB extends AbstractFacade<CandidatoMetrica> implements CandidatoMetricaFacade {
	
	
	@PersistenceContext(unitName = "votweetPU")
	private EntityManager em;
	
	public CandidatoMetricaFacadeEJB() {
		super(CandidatoMetrica.class);
	}
	

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}



}