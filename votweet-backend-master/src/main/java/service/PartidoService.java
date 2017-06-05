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

import facade.PartidoFacade;
import model.Partido;

@Path("/partidos")
public class PartidoService {
	
	@EJB 
	PartidoFacade partidoFacadeEJB;
	
	Logger logger = Logger.getLogger(PartidoService.class.getName());
	
	@GET
	@Produces({"application/xml", "application/json"})
	public List<Partido> findAll(){
		return partidoFacadeEJB.findAll();
	}
	
	@GET
	@Path("{id}/borrar")
	@Produces({"application/xml", "application/json"})
	public String eliminarPartidoId(@PathParam("id") Integer id){
		String respuesta;
		if(partidoFacadeEJB.find(id)==null){
			respuesta="No existe dicho partido en la base de datos";
			return respuesta;
		}
		try{
			partidoFacadeEJB.remove(partidoFacadeEJB.find(id));
			respuesta="Se ha eliminado el partido con exito";
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
    public Partido find(@PathParam("id") Integer id) {
        return partidoFacadeEJB.find(id);
    }
	
	
	@POST
    @Consumes({"application/xml", "application/json"})
    public String create(Partido entity) {
		String respuesta=partidoFacadeEJB.create(entity);
		return respuesta;
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Partido entity) {
    	entity.setPtdoId(id.intValue());
        partidoFacadeEJB.edit(entity);
    }
	

}
