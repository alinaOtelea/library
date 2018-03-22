package jsf.model.carte;

import ejb.CarteFacade;
import jpa.Carte;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="carteBeanAdd")
@RequestScoped
@Getter @Setter
public class CarteBeanAdd {

    @EJB
    private CarteFacade carteFacade;

    private int idCarte;

    private String autor;

    private String titlu;

    private String domeniu;

    private String editura;

    private int stoc;

    private int data_publicare;

    public String add() {
        if (idCarte != 0) {
            Carte newCarte = Carte.builder().idCarte(idCarte).autor(autor).titlu(titlu).domeniu(domeniu).editura(editura).stoc(stoc).data_publicare(data_publicare).build();
            carteFacade.addCarte(newCarte);
            return "carte";
        } else {
            return "carteInvalid";
        }
    }
}
