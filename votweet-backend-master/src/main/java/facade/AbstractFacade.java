package facade;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


import model.Usuario;
/**
 * 
 * @author AbrahamCerda
 *
 * @param <T>
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;
    
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public String create(T entity) {
    	String res=null;
    	try{
    		getEntityManager().persist(entity);
    		res ="creado";
    		return res;
    	}catch(EntityExistsException e1){
    		res="existe";
    		return res;
    	}
    	/*catch(IllegalArgumentException e2){
    		res=e2.toString();
    		return res;
    	}
    	*/
    	catch(TransactionRequiredException e3){
    		res="errorTransaccion";
    		return res;
    	}
    		
    	}
        

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    /*
	//Metodo que ejecuta la named query (definida en el modelo) que representa la consulta
	//que busca todas las peliculas en las que ha participado un actor por el id del actor.
	 public List<T> findAllMoviesActor(Object id){
	    	EntityManager em = getEntityManager();
	    	Query q = em.createNamedQuery("find all movies by actor by his id");
	    	q.setParameter("id", id);
	    	return q.getResultList();
	  }
	 
		public FilmActor createFilmActor(Object id_actor, Object id_film){
	    	FilmActor fa = new FilmActor();
	    	fa.setActorId((int)id_actor);
	    	fa.setFilmId((int)id_film);
	    	Date date= new Date();
	    	long miliseg= date.getTime();
	    	Timestamp ts = new Timestamp(miliseg);
	    	fa.setLastUpdate(ts);
	    	return fa;
	    	
	    }
		
		//Metodo que ejecuta la named query (definida en el modelo) que representa la consulta
		//que busca todos los actores de una pelicula por el id de la pelicula
		public List<T> findAllActorsMovie(Object id){
			EntityManager em = getEntityManager();
	    	Query q = em.createNamedQuery("find all actors by movie by his id");
	    	q.setParameter("id", id);
	    	return q.getResultList();
		}
    
   */
    /*
    public void eliminarUsuarioId(Object id){
    	EntityManager em = getEntityManager();
    	Query q = em.createNamedQuery("eliminar usuario por id");
    	q.setParameter("id", id);
    	q.executeUpdate();
    }
    
    public void eliminarUsuarioCorreo(Object correo){
    	EntityManager em = getEntityManager();
    	Query q = em.createNamedQuery("eliminar usuario por correo");
    	q.setParameter("correo", correo);
    	q.executeUpdate();
    }
    
    public void eliminarCandidatoId(Object id){
    	EntityManager em = getEntityManager();
    	Query q = em.createNamedQuery("eliminar candidato por id");
    	q.setParameter("id", id);
    	q.executeUpdate();
    }
    
    public void eliminarPartidoId(Object id){
    	EntityManager em = getEntityManager();
    	Query q = em.createNamedQuery("eliminar partido por id");
    	q.setParameter("id", id);
    	q.executeUpdate();
    }
    
*/
    /*
    @SuppressWarnings("unchecked")
	public List<T> encontrarUsuarioPorCorreo(String correo){
    	EntityManager em = getEntityManager();
    	Query q = em.createNamedQuery("encontrar usuario por correo");
    	q.setParameter("correo", correo);
    	return q.getResultList();
    }
    */
    
    
}
