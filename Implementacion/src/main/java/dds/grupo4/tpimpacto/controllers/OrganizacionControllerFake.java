package dds.grupo4.tpimpacto.controllers;

import dds.grupo4.tpimpacto.cargamediciones.MedicionesDataLoader;
import dds.grupo4.tpimpacto.cargamediciones.RowMedicionActividad;
import dds.grupo4.tpimpacto.dtos.AceptarSolicitud;
import dds.grupo4.tpimpacto.entities.organizacion.*;
import dds.grupo4.tpimpacto.entities.trayecto.Espacio;
import dds.grupo4.tpimpacto.extras.ConsoleHelper;
import dds.grupo4.tpimpacto.services.OrganizacionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@Slf4j
public class OrganizacionControllerFake {

    private final OrganizacionService organizacionService;

    public OrganizacionControllerFake(OrganizacionService organizacionService) {
        this.organizacionService = organizacionService;
    }

    public void crearOrganizacion() {
        ConsoleHelper.print("Razon social de la organizacion: ");
        String razonSocial = ConsoleHelper.readString();

        // Para simplificar, usamos estos valores predeterminados
        TipoOrganizacion tipoOrganizacion = TipoOrganizacion.EMPRESA;
        Clasificacion clasificacion = Clasificacion.EMPRESA_SECTOR_PRIMARIO;

        Organizacion nuevaOrganizacion = new Organizacion(razonSocial, tipoOrganizacion, clasificacion);

        String nombreSector = "";
        while (!nombreSector.equals("FIN")) {
            ConsoleHelper.print("Nombre de sector para la organizacion " + razonSocial + " (FIN para finalizar): ");
            nombreSector = ConsoleHelper.readString();
            if (!nombreSector.equals("FIN")) {
                Espacio espacioSector = null;
                Sector nuevoSector = new Sector(nombreSector, nuevaOrganizacion, espacioSector);
                nuevaOrganizacion.addSector(nuevoSector);
            }
        }

        organizacionService.save(nuevaOrganizacion);
    }

    public void listarOrganizaciones() {
        List<Organizacion> organizaciones = organizacionService.getAll();
        for (Organizacion organizacion : organizaciones) {
            String message = "";
            message += "Organizacion " + organizacion.getRazonSocial() + " con sectores:";
            for (Sector sector : organizacion.getSectores()) {
                message += "\n - " + sector.getNombre();
            }
            ConsoleHelper.printLine(message);
        }
    }

    public void aceptarSolicitud() {
        int idSolicitud = ConsoleHelper.readInt("ID de la solicitud: ");
        AceptarSolicitud.Request request = new AceptarSolicitud.Request(idSolicitud);
        organizacionService.aceptarSolicitud(request);
        log.debug("Solicitud de ID " + idSolicitud + " aceptada");
    }

    public void listarMiembros() {
        try {
            Organizacion organizacion = buscarOrganizacionPorRazonSocial();

            ConsoleHelper.printLine("Miembros de organizacion " + organizacion.getRazonSocial() + ":");
            for (Miembro miembro : organizacion.getMiembros()) {
                String message = "\n - " + miembro.getPersona().getNombre() + " (" + miembro.getDocumento() + ")";
                ConsoleHelper.printLine(message);
            }
        } catch (NoSuchElementException e) {
            ConsoleHelper.printLine(e.getMessage());
        }
    }

    public void cargarMediciones() throws IOException {
        try {
            Organizacion organizacion = buscarOrganizacionPorRazonSocial();

            // Esto nos llegaria como un archivo que nos suben desde la Web o algo asi
            String pathPrueba = "static/PruebaCargaMediciones.xlsx";
            URL resource = getClass().getClassLoader().getResource(pathPrueba);
            String filePath = resource.getPath();
            File file = new File(filePath);

            MedicionesDataLoader dataLoader = new MedicionesDataLoader();
            List<RowMedicionActividad> mediciones = dataLoader.loadData(file);
            organizacionService.cargarMediciones(organizacion, mediciones);
        } catch (NoSuchElementException e) {
            ConsoleHelper.printLine(e.getMessage());
        }
    }

    private Organizacion buscarOrganizacionPorRazonSocial() throws NoSuchElementException {
        ConsoleHelper.print("Razon social de la Organizacion: ");
        String razonSocialOrganizacion = ConsoleHelper.readString();

        Optional<Organizacion> optionalOrganizacion = organizacionService.getByRazonSocial(razonSocialOrganizacion);
        return optionalOrganizacion.get();
    }

}
