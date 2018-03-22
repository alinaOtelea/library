package jsf.model.cititor;

import ejb.CititorFacade;
import jpa.Cititor;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@ManagedBean(name="carteBean")
@RequestScoped
public class CititorBean implements Serializable{

    private static final long serialVersionUID = 1L;

//        private static final List<Carte> carte = Arrays.asList(
//            Carte.builder().idCarte(1).autor("Ceva").titlu("test").domeniu("dom test").editura("edit").stoc(1).data_publicare(2014).build()
//    );

    @EJB
    private CititorFacade cititorFacade;

    public List<Cititor> getCarte() {
        return cititorFacade.getCititor();
    }

}
