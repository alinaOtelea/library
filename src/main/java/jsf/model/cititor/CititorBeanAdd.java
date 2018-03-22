package jsf.model.cititor;

import ejb.CititorFacade;
import jpa.Cititor;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="cititorBeanAdd")
@RequestScoped
@Getter @Setter
public class CititorBeanAdd {

    @EJB
    private CititorFacade cititorFacade;

    private Integer idCititor;

    private String cnp;

    private String nume;

    private String prenume;

    private String adresa;

    private String telefon;

    private String email;

    private String data_nasterii;

    private String data_inregistrare;

    public String add() {
        if (idCititor != 0) {
            Cititor newCititor = Cititor.builder().cnp(cnp).nume(nume).prenume(prenume).adresa(adresa).telefon(telefon).email(email).data_nasterii(data_nasterii).data_inregistrare(data_inregistrare).build();
            cititorFacade.addCititor(newCititor);
            return "cititor";
        } else {
            return "cititorInvalid";
        }
    }
}
