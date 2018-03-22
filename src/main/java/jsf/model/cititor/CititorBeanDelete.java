package jsf.model.cititor;

import ejb.CititorFacade;
import jpa.Cititor;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="cititorBeanDelete")
@RequestScoped
@Getter
@Setter
public class CititorBeanDelete {

    @EJB
    private CititorFacade employeeFacade;

    public String delete(Cititor cititor) {
        employeeFacade.deleteCititor(cititor);
        return null;
    }
}
