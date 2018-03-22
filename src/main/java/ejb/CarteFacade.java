package ejb;

import jpa.Carte;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Computer on 3/19/2017.
 */
@Local
public interface CarteFacade {
    List<Carte> getCarte();
    Carte addCarte(Carte carte);
    void deleteCarte(Carte carte);
    void updateCarte(Carte carte);
    Carte findCarte(Integer id);
}
