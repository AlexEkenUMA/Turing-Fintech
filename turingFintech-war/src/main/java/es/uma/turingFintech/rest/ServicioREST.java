package es.uma.turingFintech.rest;

import clases.ejb.GestionClientes;
import clases.ejb.GestionCuentas;
import clases.ejb.GestionUsuarios;
import clases.ejb.exceptions.*;
import es.uma.turingFintech.Cliente;
import es.uma.turingFintech.Usuario;
import es.uma.turingFintech.backing.InfoSesion;

import java.net.URI;
import java.util.List;

import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

@Path("")
public class ServicioREST {
    @EJB
    private GestionCuentas gestionCuentas;
    @EJB
    private GestionClientes gestionClientes;
    @EJB
    private GestionUsuarios gestionUsuarios;

    @Context
    private UriInfo uriInfo;

    @Path("/healthcheck")
    @GET
    @Produces ({MediaType.APPLICATION_XML})
    public Response healthcheck() {
        return Response.ok().build();
    }

    @Path("/clients")
    @GET
    @Produces ({MediaType.APPLICATION_JSON})
    public Response getClientes() {
        //TODO
        return Response.ok().build();
    }

    @Path("/products")
    @POST
    @Produces ({MediaType.APPLICATION_JSON})
    public Response getProducto() {
        //TODO
        return Response.ok().build();
    }





}
