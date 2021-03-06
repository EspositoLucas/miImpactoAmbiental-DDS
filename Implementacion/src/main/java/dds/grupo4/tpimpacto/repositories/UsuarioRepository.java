package dds.grupo4.tpimpacto.repositories;

import dds.grupo4.tpimpacto.entities.seguridad.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends BaseRepository<Usuario> {
    Optional<Usuario> getByUsername(String username);
}
