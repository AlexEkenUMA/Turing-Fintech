package es.uma.turingFintech.backing;

import clases.ejb.GestionCuentas;
import clases.ejb.exceptions.NoEsAdministrativo;
import clases.ejb.exceptions.UsuarioNoEncontrado;

import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named(value = "informesAlemania")
public class InformesAlemania {
    @Inject
    GestionCuentas gestionCuentas;

    @Inject
    private InfoSesion sesion;

    public void descargarInformeInicialAlemania() throws IOException {
        try {
            String path = gestionCuentas.getInformeInicialAlemania(sesion.getUsuario());
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            SimpleDateFormat sdf=new SimpleDateFormat("ddMMYYYYhhmmss");
            String dateString=sdf.format(new Date());
            String nombreArchivo = "Turing_IBAN_" + dateString + ".csv";
            ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
            ec.setResponseContentType("text/csv"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

            OutputStream output = ec.getResponseOutputStream();
            File file = new File(path);
            String fileName = file.getName();
            String contentType = ec.getMimeType(fileName); // JSF 1.x: ((ServletContext) ec.getContext()).getMimeType(fileName);
            int contentLength = (int) file.length();
            Files.copy(file.toPath(), output);
            fc.responseComplete(); // Important! Otherwise JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
        } catch (UsuarioNoEncontrado e) {
            throw new RuntimeException(e);
        } catch (NoEsAdministrativo e) {
            throw new RuntimeException(e);
        }
    }

    public void descargarInformeSemanalAlemania() throws IOException {
        try {
            String path = gestionCuentas.getInformeSemanalAlemania(sesion.getUsuario());
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            SimpleDateFormat sdf=new SimpleDateFormat("ddMMYYYYhhmmss");
            String dateString=sdf.format(new Date());
            String nombreArchivo = "Turing_IBAN_" + dateString + ".csv";
            ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
            ec.setResponseContentType("text/csv"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

            OutputStream output = ec.getResponseOutputStream();
            File file = new File(path);
            String fileName = file.getName();
            String contentType = ec.getMimeType(fileName); // JSF 1.x: ((ServletContext) ec.getContext()).getMimeType(fileName);
            int contentLength = (int) file.length();
            Files.copy(file.toPath(), output);
            fc.responseComplete(); // Important! Otherwise JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
        } catch (UsuarioNoEncontrado e) {
            throw new RuntimeException(e);
        } catch (NoEsAdministrativo e) {
            throw new RuntimeException(e);
        }

    }
}
