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
	
	public List<Opinion> encontrar20opinionesImportantesCandidato(int idCandidato, String cuentaCandidato);
	
	public List<Opinion> opinionesEscritasPorUsuario(String screenNameUsuario);
	//public void eliminarOpinionId(Object id);
}
