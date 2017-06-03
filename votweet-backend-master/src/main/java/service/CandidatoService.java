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

import facade.CandidatoFacade;
import model.Candidato;

@Path("/candidatos")
public class CandidatoService {
	
	@EJB 
	CandidatoFacade candidatoFacadeEJB;
	
	Logger logger = Logger.getLogger(CandidatoService.class.getName());
	
	@GET
	@Produces({"application/xml", "application/json"})
	public List<Candidato> findAll(){
		return candidatoFacadeEJB.findAll();
	}
	
	@GET
	@Path("{id}/borrar")
	@Produces({"application/xml", "application/json"})
	public String eliminarCandidatoId(@PathParam("id") Integer id){
		String respuesta;
		if(candidatoFacadeEJB.find(id)==null){
			respuesta="No existe dicho candidato en la base de datos";
			return respuesta;
		}
		try{
			candidatoFacadeEJB.remove(candidatoFacadeEJB.find(id));
			respuesta="Se ha eliminado el candidato con exito";
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
    public Candidato find(@PathParam("id") Integer id) {
        return candidatoFacadeEJB.find(id);
    }
	
	
	@POST
    @Consumes({"application/xml", "application/json"})
    public String create(Candidato entity) {
		String respuesta;
		candidatoFacadeEJB.create(entity);
		respuesta="Usuario creado con exito";
		return respuesta;
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Candidato entity) {
    	entity.setCdtoId(id.intValue());
        candidatoFacadeEJB.edit(entity);
    }
	

}
