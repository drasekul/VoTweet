package ejb;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.KeywordFacade;
import model.Keyword;

@Stateless
public class KeywordFacadeEJB extends AbstractFacade<Keyword> implements KeywordFacade {
	
	
	@PersistenceContext(unitName = "votweetPU")
	private EntityManager em;
	
	public KeywordFacadeEJB() {
		super(Keyword.class);
	}
	

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}



}