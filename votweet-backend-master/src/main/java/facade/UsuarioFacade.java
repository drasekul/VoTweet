package facade;

import java.util.List;

import model.Usuario;

public interface UsuarioFacade {
	
	public void create(Usuario entity);

	public void edit(Usuario entity);

	public void remove(Usuario entity);

	public Usuario find(Object id);

	public List<Usuario> findAll();

	public List<Usuario> findRange(int[] range);

	public int count();
	
	public void eliminarUsuarioId(Object id);
	
	public void eliminarUsuarioCorreo(Object correo);
	
}
