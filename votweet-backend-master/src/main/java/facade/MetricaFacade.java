package facade;

import java.util.List;

import model.Metrica;

public interface MetricaFacade {
	public String create(Metrica entity);

	public void edit(Metrica entity);

	public void remove(Metrica entity);

	public Metrica find(Object id);

	public List<Metrica> findAll();

	public List<Metrica> findRange(int[] range);

	public int count();
}
