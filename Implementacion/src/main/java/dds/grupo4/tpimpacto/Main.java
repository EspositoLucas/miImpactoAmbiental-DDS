package dds.grupo4.tpimpacto;

import dds.grupo4.tpimpacto.common.LectorDeArchivoImpl;
import dds.grupo4.tpimpacto.common.ResultadoDeValidacion;
import dds.grupo4.tpimpacto.common.ValidadorContrasenia;
import dds.grupo4.tpimpacto.entities.Usuario;
import dds.grupo4.tpimpacto.extras.OperacionTesteo;
import dds.grupo4.tpimpacto.repositories.UsuarioRepositoryImpl;
import dds.grupo4.tpimpacto.services.UsuarioService;
import dds.grupo4.tpimpacto.services.UsuarioServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class Main {

    private static final int MAX_INTENTOS_REGISTRO = 3;
    private static final UsuarioService usuarioService = new UsuarioServiceImpl(new UsuarioRepositoryImpl());

    public static void main(String[] args) throws Exception {
        /*
         * Descomentar esto cuando querramos usar Spring para hacer la API loca
         * SpringApplication.run(Main.class, args);
         */

        boolean terminarEjecucion = false;
        do {
            OperacionTesteo operacionTesteo = mostrarOperacionesYElegir();
            switch (operacionTesteo) {
                case REGISTRO:
                    registrarUsuario();
                    break;
                case LOGIN:
                    logearse();
                    break;
                case EXIT:
                    terminarEjecucion = true;
                    break;
            }

            System.out.println("\n");
        } while (!terminarEjecucion);
    }

    public static OperacionTesteo mostrarOperacionesYElegir() {
        try (Scanner scanner = new Scanner(System.in)) {
            mostrarOperaciones();

            while (true) {
                String ingresado = scanner.nextLine();
                try {
                    int operacion = Integer.parseInt(ingresado);
                    return OperacionTesteo.of(operacion);
                } catch (Exception e) {
                    System.out.println("No te pases de vivarache, mete algo valido");
                    System.out.println();
                    mostrarOperaciones();
                }
            }
        }
    }

    private static void mostrarOperaciones() {
        System.out.println("1- Login");
        System.out.println("2- Registrar usuario");
        System.out.println("3- Salir");
        System.out.print("Elegi una opcion: ");
    }

    public static void registrarUsuario() throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            String username;
            boolean usernameCorrecto = false;
            do {
                System.out.print("Ingresa el nuevo username: ");
                username = scanner.nextLine();
                if (usuarioService.existeUsuarioConUsername(username)) {
                    System.out.println("Ya existe otro usuario con ese username!");
                } else {
                    usernameCorrecto = true;
                }
            } while (!usernameCorrecto);

            String password;
            boolean passwordCorrecta = false;
            int intentosFallidos = 0;
            do {
                System.out.print("Ingresa la nueva contrasenia: ");
                password = scanner.nextLine();
                ResultadoDeValidacion resultado = validarNuevaContrasenia(password);
                if (resultado.esValido()) {
                    Usuario nuevoUser = new Usuario(username, password);
                    usuarioService.agregarUsuario(nuevoUser);
                    System.out.println("Usuario creado exitosamente!");
                    passwordCorrecta = true;
                } else {
                    intentosFallidos++;
                    System.out.println("Contrasenia invalida:-");
                    System.out.println(resultado.getErroresEnLineas());
                    if (intentosFallidos > MAX_INTENTOS_REGISTRO) {
                        System.out.println("Demasiados errores");
                    }
                }
            } while (!passwordCorrecta && intentosFallidos <= MAX_INTENTOS_REGISTRO);
        }
    }

    public static void logearse() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Ingrese su usuario: ");
            String username = scanner.nextLine();

            Optional<Usuario> optionalUser = usuarioService.getUsuarioPorUsername(username);
            if (!optionalUser.isPresent()) {
                System.out.println("Usuario incorrecto!");
                return;
            }

            Usuario user = optionalUser.get();
            if (user.estaBloqueado()) {
                System.out.println("Estas bloqueado hasta la fecha " + formatearBloqueadoHasta(user.getBloqueadoHasta()));
                return;
            }

            System.out.print("Ingrese la contraseña: ");
            String password = scanner.nextLine();
            if (user.validarContrasenia(password)) {
                System.out.println("Usuario " + user.getUsername() + " logeado joyita");
                user.logeoCorrecto();
            } else {
                System.out.println("Contrasenia incorrecta!");
                user.logeoIncorrecto();
                if (user.estaBloqueado()) {
                    System.out.println("Estas bloqueado hasta la fecha " + formatearBloqueadoHasta(user.getBloqueadoHasta()));
                }
            }
        }
    }

    public static ResultadoDeValidacion validarNuevaContrasenia(String nuevaPassword) {
        ValidadorContrasenia validador = new ValidadorContrasenia(new LectorDeArchivoImpl());
        return validador.validarContrasenia(nuevaPassword);
    }

    public static String formatearBloqueadoHasta(LocalDateTime bloqueadoHasta) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return bloqueadoHasta.format(formatter);
    }

}
