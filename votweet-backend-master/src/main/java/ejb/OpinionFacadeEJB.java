package ejb;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.OpinionFacade;
import model.Opinion;

@Stateless
public class OpinionFacadeEJB extends AbstractFacade<Opinion> implements OpinionFacade {
	
	
	@PersistenceContext(unitName = "votweetPU")
	private EntityManager em;
	
	public OpinionFacadeEJB() {
		super(Opinion.class);
	}
	

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}



}