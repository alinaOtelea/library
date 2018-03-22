package ejb;

import jpa.Cititor;

import java.util.List;
/**
 * Created by Computer on 3/25/2017.
 */
public interface CititorFacade {
    List<Cititor> getCititor();
    Cititor addCititor(Cititor cititor);
    void deleteCititor(Cititor cititor);
    void updateCititor(Cititor cititor);
    Cititor findCititor(Integer idCititor);
    Cititor getCnpCititor(String cnp);
}
