package es.uma.turingFintech.rest;

import clases.ejb.GestionClientes;
import clases.ejb.GestionCuentas;
import clases.ejb.GestionUsuarios;
import clases.ejb.exceptions.*;
import es.uma.turingFintech.Cliente;
import es.uma.turingFintech.CuentaFintech;
import es.uma.turingFintech.PersonaFisica;
import es.uma.turingFintech.PersonaJuridica;
import es.uma.turingFintech.restClasses.*;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @POST
    @Consumes ({MediaType.APPLICATION_JSON})
    @Produces ({MediaType.APPLICATION_JSON})
    public Response getClientes(SearchParametersClients clientsRequest) {
        try {
            List<Cliente> clientes = gestionClientes.getClientesHolanda(clientsRequest.getName().getFirstName(), clientsRequest.getName().getLastName(),
                    clientsRequest.getStartPeriod(), clientsRequest.getEndPeriod());
            //TODO: Modelar formato de respuesta JSON
            List<Individual> respuestaAPI = new ArrayList<>();

            for(Cliente c : clientes){
                Individual i = new Individual();
                //OBTENEMOS CUENTAS A LAS QUE TIENE ACCESO COMO PROPIETARIA
                List<ProductsClients> listaProductosCliente = new ArrayList<>();
                for(CuentaFintech cf : c.getCuentasFintech()){
                    ProductsClients productoCliente = new ProductsClients();
                    productoCliente.setProductNumber(cf.getIBAN());
                    productoCliente.setStatus(cf.getEstado());
                    productoCliente.setRelationship("propietaria");
                    listaProductosCliente.add(productoCliente);
                }
                //OBTENEMOS CUENTAS A LAS QUE TIENE ACCESO COMO AUTORIZADO
                if(c.getUsuario().getAutorizado() != null){
                    List<PersonaJuridica> listaEmpresas = c.getUsuario().getAutorizado().getEmpresas();
                    for(PersonaJuridica pj : listaEmpresas){
                        for(CuentaFintech cf : pj.getCuentasFintech()){
                            ProductsClients productoCliente = new ProductsClients();
                            productoCliente.setProductNumber(cf.getIBAN());
                            productoCliente.setStatus(cf.getEstado());
                            productoCliente.setRelationship("autorizada");
                            listaProductosCliente.add(productoCliente);
                        }
                    }
                }
                i.setProductsClients(listaProductosCliente);
                //ACTIVE CUSTOMER
                if(c.getEstado().equals("Activo")){
                    i.setActiveCustomer("true");
                }
                else{
                    i.setActiveCustomer("false");
                }
                //DNI
                i.setIdentificationNumber(c.getIdentificacion());
                //FECHA NACIMIENTO
                if(c instanceof PersonaFisica){
                    PersonaFisica pf = (PersonaFisica) c;
                    //para darle formato a la fecha como string
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                    Date fechaNacimiento = pf.getFecha_Nacimiento();
                    i.setDateOfBirth(dateFormat.format(fechaNacimiento));
                    //NOMBRE
                    ClientName clientName = new ClientName();
                    clientName.setFirstName(pf.getNombre());
                    clientName.setLastName(pf.getApellidos());
                    i.setName(clientName);

                }
                //DIRECCION
                Adress adress = new Adress();
                adress.setCity(c.getCiudad());
                adress.setCountry(c.getPais());
                adress.setPostalCode(c.getCodigo_Postal().toString());
                adress.setStreetNumber(c.getDireccion());
                i.setAdress(adress);
                respuestaAPI.add(i);
            }
            return Response.ok(respuestaAPI).build();
        } catch (NoEsAdministrativo e) {
            throw new RuntimeException(e);
        } catch (UsuarioNoEncontrado e) {
            throw new RuntimeException(e);
        } catch (NingunClienteCoincideConLosParametrosDeBusqueda e) {
            throw new RuntimeException(e);
        }
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
