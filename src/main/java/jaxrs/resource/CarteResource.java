package jaxrs.resource;

import ejb.CarteFacade;
import jpa.Carte;
import org.springframework.web.bind.annotation.*;

import javax.ejb.EJB;
import javax.json.JsonString;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

/**
 * Created by Computer on 3/19/2017.
 */
@Path("/")
public class CarteResource {
    @EJB
    CarteFacade carteFacade;

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("all")
    public Response all() {
        List<Carte> carte = carteFacade.getCarte();
        return Response.ok(carte).build();
    }


    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("carte")
    @ResponseBody
    public Response addNewCarte(
            @RequestBody Map<String, String> json
    ) {
        Carte carte = carteFacade.addCarte(
                Carte.builder()
                        .autor(json.get("autor"))
                        .titlu(json.get("titlu"))
                        .domeniu(json.get("domeniu"))
                        .editura(json.get("editura"))
                        .stoc(parseInt(json.get("stoc")))
                        .data_publicare(parseInt(json.get("data_publicare")))
                        .isbn(json.get("isbn"))
                        .disponibile(parseInt(json.get("stoc")))
                        .build()
        );
        return Response.ok(carte).build();
    }

    @DELETE
    @Path("remove/{idCarte}")
    public Response removeCarte(@NotNull @PathParam("idCarte") Integer idCarte){

        Carte carte = carteFacade.findCarte(idCarte);
        if( carte == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        carteFacade.deleteCarte(carte);
        return Response.ok().build();
    }

    @PUT
    @Path("update/{idCarte}")
    public Response updateCarte(
        @NotNull @PathParam("idCarte") Integer idCarte,
        @NotNull @Size(min = 1, max = 30) @QueryParam("autor") String autor,
        @NotNull @Size(min = 1, max = 40) @QueryParam("titlu") String titlu,
        @NotNull @Size(min = 1, max = 30) @QueryParam("domeniu") String domeniu,
        @NotNull @Size(min = 1, max = 15) @QueryParam("editura") String editura,
        @NotNull @QueryParam("stoc") Integer stoc,
        @NotNull @QueryParam("data_publicare") Integer data_publicare,
        @NotNull @Size(min = 1, max = 15) @QueryParam("isbn") String isbn,
        @NotNull @QueryParam("disponibile") Integer disponibile
    ){
        System.out.println(disponibile);

        Carte carte = carteFacade.findCarte(idCarte);
        if( carte == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        carteFacade.updateCarte(
                Carte.builder()
                        .idCarte(idCarte)
                        .autor(autor)
                        .titlu(titlu)
                        .domeniu(domeniu)
                        .editura(editura)
                        .stoc(stoc)
                        .data_publicare(data_publicare)
                        .isbn(isbn)
                        .disponibile(disponibile)
                        .build()
        );
        return Response.ok(carte).build();
    }

}
