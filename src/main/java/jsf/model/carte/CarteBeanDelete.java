package jsf.model.carte;

import ejb.CarteFacade;
import jpa.Carte;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="carteBeanDelete")
@RequestScoped
@Getter
@Setter
public class CarteBeanDelete {

    @EJB
    private CarteFacade employeeFacade;

    public String delete(Carte carte) {
        employeeFacade.deleteCarte(carte);
        return null;
    }
}
