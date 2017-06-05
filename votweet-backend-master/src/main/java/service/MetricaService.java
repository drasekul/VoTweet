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

import facade.MetricaFacade;
import model.Metrica;

@Path("/metricas")
public class MetricaService {
	
	@EJB 
	MetricaFacade metricaFacadeEJB;
	
	Logger logger = Logger.getLogger(MetricaService.class.getName());
	
	@GET
	@Produces({"application/xml", "application/json"})
	public List<Metrica> findAll(){
		return metricaFacadeEJB.findAll();
	}
	
	@GET
	@Path("{id}/borrar")
	@Produces({"application/xml", "application/json"})
	public String eliminarMetricaId(@PathParam("id") Integer id){
		String respuesta;
		if(metricaFacadeEJB.find(id)==null){
			respuesta="No existe dicha Metrica en la base de datos";
			return respuesta;
		}
		try{
			metricaFacadeEJB.remove(metricaFacadeEJB.find(id));
			respuesta="Se ha eliminado la metrica con exito";
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
    public Metrica find(@PathParam("id") Integer id) {
        return metricaFacadeEJB.find(id);
    }
	
	
	@POST
    @Consumes({"application/xml", "application/json"})
    public String create(Metrica entity) {
		String respuesta = metricaFacadeEJB.create(entity);
		return respuesta;
		
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Metrica entity) {
    	entity.setMetId(id.intValue());
    	metricaFacadeEJB.edit(entity);
    }
	

}
