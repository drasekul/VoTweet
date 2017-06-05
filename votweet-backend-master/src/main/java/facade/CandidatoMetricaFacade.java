package facade;

import java.util.List;

import model.CandidatoMetrica;

public interface CandidatoMetricaFacade {
	public String create(CandidatoMetrica entity);

	public void edit(CandidatoMetrica entity);

	public void remove(CandidatoMetrica entity);

	public CandidatoMetrica find(Object id);

	public List<CandidatoMetrica> findAll();

	public List<CandidatoMetrica> findRange(int[] range);

	public int count();
}
