package ejb;

import jpa.Imprumut;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Computer on 3/19/2017.
 */
@Stateless
public class ImprumutFacadeImpl implements ImprumutFacade {
    @PersistenceContext(unitName = "helloWorldDS")
    private EntityManager entityManager;

    public List<Imprumut> getImprumut(){
        Query query = entityManager.createQuery( "SELECT im FROM Imprumut im" );
        return (List<Imprumut>) query.getResultList();
    }

    public Imprumut addImprumut(Imprumut imprumut){
        entityManager.persist(imprumut);
        entityManager.flush();
        return imprumut;
    }

    public void updateImprumut(Imprumut imprumut){
        entityManager.merge(imprumut);
    }

//    public void deleteImprumut(Imprumut imprumut){
//        imprumut = entityManager.merge(imprumut);
//        entityManager.remove(imprumut);
//    }

    public Imprumut findImprumut(Integer idImprumut) {
        return entityManager.find(Imprumut.class, idImprumut);
    }

    public long  queryCarte (Integer id){
        Query query = entityManager.createQuery( "SELECT count(im.idCarte) FROM Imprumut im where im.returnata = 0 and im.idCarte = " + id);
        long count = (long)query.getSingleResult();
        return count;
    }

//    SELECT count(idcarte) from imprumut where idcarte = 1 and returnata = 0

}
