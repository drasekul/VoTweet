package facade;

import java.util.List;

import model.Candidato;

public interface CandidatoFacade {
	
	public String create(Candidato entity);

	public void edit(Candidato entity);

	public void remove(Candidato entity);

	public Candidato find(Object id);

	public List<Candidato> findAll();

	public List<Candidato> findRange(int[] range);

	public int count();
	
	//public void eliminarCandidatoId(Object id);
}