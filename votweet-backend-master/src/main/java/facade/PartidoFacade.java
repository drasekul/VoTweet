package facade;

import java.util.List;

import model.Partido;

public interface PartidoFacade {
	
	public String create(Partido entity);

	public void edit(Partido entity);

	public void remove(Partido entity);

	public Partido find(Object id);

	public List<Partido> findAll();

	public List<Partido> findRange(int[] range);

	public int count();
	
	//public void eliminarPartidoId(Object id);
}