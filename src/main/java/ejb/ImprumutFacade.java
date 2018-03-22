package ejb;

import jpa.Imprumut;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Computer on 3/26/2017.
 */
@Local
public interface ImprumutFacade {
    List<Imprumut> getImprumut();
    Imprumut addImprumut(Imprumut imprumut);
//    void deleteImprumut(Imprumut imprumut);
    void updateImprumut(Imprumut imprumut);
    Imprumut findImprumut(Integer idImprumut);
    long  queryCarte(Integer id);
}
