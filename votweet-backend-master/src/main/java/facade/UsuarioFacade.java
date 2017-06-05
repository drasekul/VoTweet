package facade;

import java.util.List;

import javax.ejb.Local;

import model.Usuario;
@Local
public interface UsuarioFacade {
	
	public String create(Usuario entity);

	public void edit(Usuario entity);

	public void remove(Usuario entity);

	public Usuario find(Object id);

	public List<Usuario> findAll();

	public List<Usuario> findRange(int[] range);

	public int count();
	
	//public void eliminarUsuarioId(Object id);
	
	//public void eliminarUsuarioCorreo(Object correo);
	//public List<Usuario> encontrarUsuarioPorCorreo(String correo);
	
}
