package es.uma.turingFintech.rest;

import clases.ejb.GestionClientes;
import clases.ejb.GestionCuentas;
import clases.ejb.GestionUsuarios;
import clases.ejb.exceptions.NingunClienteCoincideConLosParametrosDeBusqueda;
import clases.ejb.exceptions.NoEsAdministrativo;
import clases.ejb.exceptions.UsuarioNoEncontrado;
import es.uma.turingFintech.Usuario;
import es.uma.turingFintech.backing.InfoSesion;

import java.net.URI;
import java.util.List;

import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

@Path("/holanda")
public class ServicioREST {
    @EJB
    private GestionCuentas gestionCuentas;
    @EJB
    private GestionClientes gestionClientes;
    @EJB
    private GestionUsuarios gestionUsuarios;

    private InfoSesion infoSesion;

    @Context
    private UriInfo uriInfo;

    @Path("/producto")
    @GET
    @Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getProducto() {
        //TODO
        return Response.ok().build();
    }

    @Path("/clientes")
    @GET
    @Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getClientes() {
        //TODO
        return Response.ok().build();
    }

    @Path("/healthcheck")
    @GET
    @Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response healthcheck() {
        //TODO
        return Response.ok().build();
    }
}
