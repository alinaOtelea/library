package ejb;

import jpa.Cititor;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Computer on 3/19/2017.
 */
@Stateless
public class CititorFacadeImpl implements CititorFacade {
    @PersistenceContext(unitName = "helloWorldDS")
    private EntityManager entityManager;

    public List<Cititor> getCititor(){
        Query query = entityManager.createQuery( "SELECT ci FROM Cititor ci" );
        return (List<Cititor>) query.getResultList();
    }

    //functie care cauta un id al cititorului
    public Cititor getCnpCititor(String cnp){
        Query query = entityManager.createQuery( "SELECT ci FROM Cititor ci where ci.cnp = "+ cnp );
        return (Cititor) query.getSingleResult();
    }

    public Cititor addCititor(Cititor cititor){
        entityManager.persist(cititor);
        entityManager.flush();
        return cititor;
    }

    public void updateCititor(Cititor cititor){
        entityManager.merge(cititor);
    }

    public void deleteCititor(Cititor cititor){
        cititor = entityManager.merge(cititor);
        entityManager.remove(cititor);
    }

    public Cititor findCititor(Integer idCititor) {
        return entityManager.find(Cititor.class, idCititor);
    }

}
