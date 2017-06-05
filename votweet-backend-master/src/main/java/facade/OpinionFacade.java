package facade;

import java.util.List;

import model.Opinion;

public interface OpinionFacade {
	public String create(Opinion entity);

	public void edit(Opinion entity);

	public void remove(Opinion entity);

	public Opinion find(Object id);

	public List<Opinion> findAll();

	public List<Opinion> findRange(int[] range);

	public int count();
	
	//public void eliminarOpinionId(Object id);
}
