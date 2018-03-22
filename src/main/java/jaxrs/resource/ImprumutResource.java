package jaxrs.resource;

import javax.ws.rs.Path;
import ejb.ImprumutFacade;
import jpa.Imprumut;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

/**
 * Created by Computer on 3/25/2017.
 */
@Path("/")
public class ImprumutResource {
    @EJB
    ImprumutFacade imprumutFacade;

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("allImprumut")
    public Response allCitior() {
        List<Imprumut> imprumut = imprumutFacade.getImprumut();
        return Response.ok(imprumut).build();
    }

    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("addNewImprumut")
    @ResponseBody
    public Response addNewCarte(
            @RequestBody Map<String, String> json
    ) {
        Imprumut imprumut = imprumutFacade.addImprumut(
                Imprumut.builder()
                        .idCititor(parseInt(json.get("idCititor")))
                        .idCarte(parseInt(json.get("idCarte")))
                        .data_imprumut(json.get("data_imprumut"))
                        .data_retur(json.get("data_retur"))
                        .returnata(parseInt(json.get("returnata")))
                        .build()
        );
        return Response.ok(imprumut).build();
    }

//    @DELETE
//    @Path("removeImprumut/{idImprumut}")
//    public Response removeImprumut(@NotNull @PathParam("idImprumut") Integer idImprumut){
//
//        Imprumut imprumut = imprumutFacade.findImprumut(idImprumut);
//        if( imprumut == null)
//            return Response.status(Response.Status.NOT_FOUND).build();
//
//        imprumutFacade.deleteImprumut(imprumut);
//        return Response.ok().build();
//    }

    @PUT
    @Path("updateImprumut/{idImprumut}")
    public Response updateImprumut(
            @NotNull @PathParam("idImprumut") Integer idImprumut,
            @NotNull @QueryParam("idCititor") Integer idCititor,
            @NotNull @QueryParam("idCarte") Integer idCarte,
            @NotNull @QueryParam("data_imprumut") String data_imprumut,
            @NotNull @QueryParam("data_retur") String data_retur,
            @NotNull @QueryParam("returnata") Integer returnata
    ){

        Imprumut imprumut = imprumutFacade.findImprumut(idImprumut);
        if( imprumut == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        imprumutFacade.updateImprumut(
                Imprumut.builder()
                        .idImprumut(idImprumut)
                        .idCititor(idCititor)
                        .idCarte(idCarte)
                        .data_imprumut(data_imprumut)
                        .data_retur(data_retur)
                        .returnata(returnata)
                        .build()
        );
        return Response.ok().build();
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("count/{idCarte}")
    public Response queryCarte (
            @PathParam("idCarte") Integer id
    ) {
        long carte = imprumutFacade.queryCarte(id);
        return Response.ok(carte).build();
    }
}
