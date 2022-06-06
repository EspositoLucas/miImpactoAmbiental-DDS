package dds.grupo4.tpimpacto.services;

import dds.grupo4.tpimpacto.entities.Sector;
import dds.grupo4.tpimpacto.repositories.SectorRepository;

import java.util.List;
import java.util.Optional;

public class SectorServiceImpl implements SectorService {

    private final SectorRepository sectorRepository;

    public SectorServiceImpl(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    @Override
    public void save(Sector obj) {
        sectorRepository.save(obj);
    }

    @Override
    public List<Sector> getAll() {
        return sectorRepository.getAll();
    }

    @Override
    public Optional<Sector> getByNombreYOrganizacion(String nombreSector, String razonSocialOrganizacion) {
        return sectorRepository.getByNombreYOrganizacion(nombreSector, razonSocialOrganizacion);
    }
}
