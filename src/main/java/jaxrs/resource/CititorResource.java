package jaxrs.resource;

import javax.ws.rs.Path;
import ejb.CititorFacade;
import jpa.Cititor;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
/**
 * Created by Computer on 3/25/2017.
 */
@Path("/")
public class CititorResource {
    @EJB
    CititorFacade cititorFacade;

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("allCititor")
    public Response allCitior() {
        List<Cititor> cititor = cititorFacade.getCititor();
        return Response.ok(cititor).build();
    }

    @GET
    @Path("cnp/{cnp}")
    public Response getIdCitior(@PathParam("cnp") String cnp) {
            Cititor cititor = cititorFacade.getCnpCititor(cnp);
        return Response.ok(cititor.getIdCititor()).build();
    }

    @POST
    @Path("addNewCititor")
    public Response addNewCititor(
            @NotNull @Size(min = 1, max = 15) @QueryParam("cnp") String cnp,
            @NotNull @Size(min = 1, max = 10) @QueryParam("nume") String nume,
            @NotNull @Size(min = 1, max = 10) @QueryParam("prenume") String prenume,
            @NotNull @Size(min = 1, max = 50) @QueryParam("adresa") String adresa,
            @NotNull @Size(min = 1, max = 10) @QueryParam("telefon") String telefon,
            @NotNull @Size(min = 1, max = 30) @QueryParam("email") String email,
            @NotNull @Size(min = 1, max = 10) @QueryParam("data_nasterii") String data_nasterii,
            @NotNull @Size(min = 1, max = 10) @QueryParam("data_inregistrare") String data_inregistrare
    ) {
        Cititor cititor = cititorFacade.addCititor(
                Cititor.builder()
                        .cnp(cnp)
                        .nume(nume)
                        .prenume(prenume)
                        .adresa(adresa)
                        .telefon(telefon)
                        .email(email)
                        .data_nasterii(data_nasterii)
                        .data_inregistrare(data_inregistrare)
                        .build()
        );
        return Response.ok(cititor.getIdCititor()).build();
    }

    @DELETE
    @Path("removeCititor/{idCititor}")
    public Response removeCititor(@NotNull @PathParam("idCititor") Integer idCititor){

        Cititor cititor = cititorFacade.findCititor(idCititor);
        if( cititor == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        cititorFacade.deleteCititor(cititor);
        return Response.ok().build();
    }

    @PUT
    @Path("updateCititor/{idCititor}")
    public Response updateCititor(
            @NotNull @PathParam("idCititor") Integer idCititor,
            @NotNull @Size(min = 1, max = 15) @QueryParam("cnp") String cnp,
            @NotNull @Size(min = 1, max = 10) @QueryParam("nume") String nume,
            @NotNull @Size(min = 1, max = 10) @QueryParam("prenume") String prenume,
            @NotNull @Size(min = 1, max = 50) @QueryParam("adresa") String adresa,
            @NotNull @Size(min = 1, max = 10) @QueryParam("telefon") String telefon,
            @NotNull @Size(min = 1, max = 30) @QueryParam("email") String email,
            @NotNull @Size(min = 1, max = 10) @QueryParam("data_nasterii") String data_nasterii,
            @NotNull @Size(min = 1, max = 10) @QueryParam("data_inregistrare") String data_inregistrare
    ){

        Cititor cititor = cititorFacade.findCititor(idCititor);
        if( cititor == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        cititorFacade.updateCititor(
                Cititor.builder()
                        .idCititor(idCititor)
                        .cnp(cnp)
                        .nume(nume)
                        .prenume(prenume)
                        .adresa(adresa)
                        .telefon(telefon)
                        .email(email)
                        .data_nasterii(data_nasterii)
                        .data_inregistrare(data_inregistrare)
                        .build()
        );
        return Response.ok().build();
    }


}
