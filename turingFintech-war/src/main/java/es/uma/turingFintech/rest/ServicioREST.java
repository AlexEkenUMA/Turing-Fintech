package es.uma.turingFintech.rest;

import clases.ejb.GestionClientes;
import clases.ejb.GestionCuentas;
import clases.ejb.GestionUsuarios;
import clases.ejb.exceptions.*;
import es.uma.turingFintech.*;
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
    @Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
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
            List<Segregada> cuentas = gestionCuentas.getCuentasHolanda(productsRequest.getStatus(), productsRequest.getProductNumber());
            //TODO: Modelar formato de respuesta JSON
            List<Products> respuestaAPI = new ArrayList<>();
            for(Segregada s : cuentas){
                Products producto = new Products();
                AccountHolder accountHolder = new AccountHolder();
                if(s.getCliente().getEstado().equals("Activa")){
                    accountHolder.setActiveCustomer("true");
                }
                else{
                    accountHolder.setActiveCustomer("false");
                }
                if(s.getCliente().getTipo_Cliente().equals("Fisico")){
                    accountHolder.setAccounttype("Fisica");
                }
                if(s.getCliente().getTipo_Cliente().equals("Juridico")){
                    accountHolder.setAccounttype("Empresa");
                }
                if(s.getCliente() instanceof PersonaFisica){
                    PersonaFisica pf = (PersonaFisica) s.getCliente();
                    //NOMBRE
                    ClientName clientName = new ClientName();
                    clientName.setFirstName(pf.getNombre());
                    clientName.setLastName(pf.getApellidos());
                    accountHolder.setName(clientName);
                }
                //DIRECCION
                Adress adress = new Adress();
                adress.setCity(s.getCliente().getCiudad());
                adress.setCountry(s.getCliente().getPais());
                adress.setPostalCode(s.getCliente().getCodigo_Postal().toString());
                adress.setStreetNumber(s.getCliente().getDireccion());
                accountHolder.setAdress(adress);

                producto.setAccountHolder(accountHolder);

                producto.setProductNumber(s.getIBAN());
                producto.setStatus(s.getEstado());
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                Date startDate = s.getFecha_Apertura();

                producto.setStartDate(dateFormat.format(startDate));
                Date endDate = s.getFecha_cierre();
                if(endDate == null){
                    producto.setStartDate("non-existent");
                }
                else{
                    producto.setStartDate(dateFormat.format(endDate));
                }


                respuestaAPI.add(producto);
            }
            return Response.ok(respuestaAPI).build();

        } catch (NoEsAdministrativo e) {
            throw new RuntimeException(e);
        } catch (UsuarioNoEncontrado e) {
            throw new RuntimeException(e);
        } catch (NingunaCuentaCoincideConLosParametrosDeBusqueda e) {
            throw new RuntimeException(e);
        }

    }

}
