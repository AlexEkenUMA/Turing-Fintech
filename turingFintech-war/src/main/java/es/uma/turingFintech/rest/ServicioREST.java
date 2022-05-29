package es.uma.turingFintech.rest;

import clases.ejb.GestionClientes;
import clases.ejb.GestionCuentas;
import clases.ejb.GestionUsuarios;
import clases.ejb.exceptions.*;
import es.uma.turingFintech.restClasses.SearchParametersClients;
import es.uma.turingFintech.restClasses.SearchParametersProducts;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("")
public class ServicioREST {
    @EJB
    private GestionCuentas gestionCuentas;
    @EJB
    private GestionClientes gestionClientes;

    @Context
    private UriInfo uriInfo;

    @Path("/healthcheck")
    @GET
    public Response healthcheck() {
        return Response.ok().build();
    }

    @Path("/clients")
    @GET
    @Consumes ({MediaType.APPLICATION_JSON})
    @Produces ({MediaType.APPLICATION_JSON})
    public Response getClientes(SearchParametersClients clientsRequest) {
        try {
            gestionClientes.getClientesHolanda(clientsRequest.getName().getFirstName(), clientsRequest.getName().getLastName(),
                    clientsRequest.getStartPeriod(), clientsRequest.getEndPeriod());
        } catch (NoEsAdministrativo e) {
            throw new RuntimeException(e);
        } catch (UsuarioNoEncontrado e) {
            throw new RuntimeException(e);
        } catch (NingunClienteCoincideConLosParametrosDeBusqueda e) {
            throw new RuntimeException(e);
        }
        //TODO: Modelar formato de respuesta JSON
        return Response.ok().build();
    }

    @Path("/products")
    @POST
    @Consumes ({MediaType.APPLICATION_JSON})
    @Produces ({MediaType.APPLICATION_JSON})
    public Response getProducto(SearchParametersProducts productsRequest) {
        try {
            gestionCuentas.getCuentasHolanda(productsRequest.getStatus(), productsRequest.getProductNumber());
        } catch (NoEsAdministrativo e) {
            throw new RuntimeException(e);
        } catch (UsuarioNoEncontrado e) {
            throw new RuntimeException(e);
        } catch (NingunaCuentaCoincideConLosParametrosDeBusqueda e) {
            throw new RuntimeException(e);
        }
        //TODO: Modelar formato de respuesta JSON
        return Response.ok().build();
    }





}
