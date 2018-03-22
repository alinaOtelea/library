package ejb;

import jpa.Carte;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Computer on 3/19/2017.
 */
@Stateless
public class CarteFacadeImpl implements CarteFacade {
    @PersistenceContext(unitName = "helloWorldDS")
    private EntityManager entityManager;

    public List<Carte> getCarte(){
        Query query = entityManager.createQuery( "SELECT c FROM Carte c" );
        return (List<Carte>) query.getResultList();
    }

    public Carte addCarte(Carte carte){
        entityManager.persist(carte);
        entityManager.flush();
        return carte;
    }

    public void updateCarte(Carte carte){
        entityManager.merge(carte);
    }

    public void deleteCarte(Carte carte){
        carte = entityManager.merge(carte);
        entityManager.remove(carte);
    }

    public Carte findCarte(Integer id) {
        return entityManager.find(Carte.class, id);
    }


}
