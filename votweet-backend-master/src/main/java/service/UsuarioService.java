package service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import facade.UsuarioFacade;
import model.Usuario;

@Path("/usuarios")
public class UsuarioService {
	
	@EJB 
	UsuarioFacade usuarioFacadeEJB;
	
	Logger logger = Logger.getLogger(UsuarioService.class.getName());
	
	@GET
	@Produces({"application/json;charset=utf-8"})
	public List<Usuario> findAll(){
		return usuarioFacadeEJB.findAll();
	}
	
	@GET
	@Path("{id}/borrar")
	@Produces({"application/json;charset=utf-8"})
	public String eliminarUsuarioId(@PathParam("id") Integer id){
		String respuesta;
		if(usuarioFacadeEJB.find(id)==null){
			respuesta="No existe dicho usuario en la base de datos";
			return respuesta;
		}
		try{
			usuarioFacadeEJB.remove(usuarioFacadeEJB.find(id));
			respuesta="Se ha eliminado el usuario con exito";
			return respuesta;
		}
		catch(Exception e){
			respuesta="Error desconocido";
			return respuesta;
		}
	}
	
	@GET
    @Path("{id}")
    @Produces({"application/json;charset=utf-8"})
    public Usuario find(@PathParam("id") Integer id) {
        return usuarioFacadeEJB.find(id);
    }
	
	
	@POST
    @Consumes({"application/json;charset=utf-8"})
    public String create(Usuario entity) {
		String respuesta = usuarioFacadeEJB.create(entity);
		return respuesta;
		
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json;charset=utf-8"})
    public void edit(@PathParam("id") Integer id, Usuario entity) {
    	entity.setUsrId(id.intValue());
        usuarioFacadeEJB.edit(entity);
    }
	

}
