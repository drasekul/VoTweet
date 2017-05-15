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
	@Produces({"application/xml", "application/json"})
	public List<Usuario> findAll(){
		return usuarioFacadeEJB.findAll();
	}
	
	@GET
	@Path("{id}/borrar")
	@Produces({"application/xml", "application/json"})
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
    @Produces({"application/xml", "application/json"})
    public Usuario find(@PathParam("id") Integer id) {
        return usuarioFacadeEJB.find(id);
    }
	
	
	@POST
    @Consumes({"application/xml", "application/json"})
    public String create(Usuario entity) {
		String respuesta;
		boolean esta=false;
		List<Usuario> usuarios = usuarioFacadeEJB.findAll();
		for(int i=0 ; i < usuarios.size(); i++){
			if(usuarios.get(i).getUsrCorreo().equals(entity.getUsrCorreo())){
				esta=true;
			}
		}
		if(esta==true){
			respuesta="Un usuario con ese correo ya existe en la base de datos";
			return respuesta;
		}
		else{
			usuarioFacadeEJB.create(entity);
			respuesta="Usuario creado con exito";
			return respuesta;
		}
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Usuario entity) {
    	entity.setUsrId(id.intValue());
        usuarioFacadeEJB.edit(entity);
    }
	

}
