import entidades.*;
import repositorios.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;


public class App {

    // Colores de la fuente
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

    // JDBC URL, usuario, y password para MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/dam";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // Variable JDBC para manejar la conexión con la BD
    private static Connection conn;

    public static void main(String[] args) {
        try {

            // Abrir conexión
            conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);

            // Borramos si existen anteriormente y creamos las tablas de la BD
            ////////////////////////////////////////
            ///////CREANDO LAS TABLAS EN LA BD//////
            ////////////////////////////////////////
            System.out.println(RESET+"1) Creamos las tablas en la BD"+GREEN);
            crearTablasBiblioteca();                                                // FUNCION QUE CREA LAS TABLAS

            ////////////////////////////////////////
            ///////INSERTANDO DATOS INICIALES///////
            ////////////////////////////////////////
            System.out.println(RESET+"2) Insertamos datos inciales"+GREEN);
            insertarDatosIniciales();                                               // FUNCION QUE AGREGA LOS DATOS INCIALES

            ////////////////////////////////////////
            //////////LISTADO DE LOS LIBROS/////////
            ////////////////////////////////////////
            System.out.println(RESET+"3) Listado de libros"+GREEN);
            System.out.println();
                                                                                    /////////////////////////////////////////////////////////////////
            LibroRepositorio libroRepositorio= new LibroRepositorio(conn);         // CREO UNA INSTANCIA DE LibroRepositorio PARA PODER LISTAR    //
            List<Libro> listaLibros = libroRepositorio.findAll();                  // LOS LIBROS CON UN FOREACH, TAMBIEN SERA UTILIZADO PARA LA   //
            for (Libro libro:                                                       // MAYORIA DE LOS METODOS SIGUIENTES                           //
                 listaLibros) {                                                     /////////////////////////////////////////////////////////////////
                System.out.println("ID: "+libro.getIdLibro()+", Titulo: "+libro.getTitulo()+", Anyo: "+libro.getYearPublicacion()+", Copias: "+libro.getCopiasDisponibles());
            }
            System.out.println();


            ////////////////////////////////////////
            //////////NUEVO AUTOR AÑADIDO///////////
            ////////////////////////////////////////
            System.out.println(RESET+"4) Añadimos un nuevo autor ('Luz','Gabás')"+GREEN);
            Autor autorNuevo = new Autor(4, "Luz Gabás", "española");
            AutorRepositorio nuevoAutor = new AutorRepositorio(conn);
            nuevoAutor.save(autorNuevo);


            ////////////////////////////////////////
            //////MODIFICANDO LIBRO CON ID = 3//////
            ////////////////////////////////////////
            System.out.println(RESET+"5) Modificamos el  libro id = 3  ('Lejos de Luisiana' ,2023, 10 copias) y le asociamos el autor Luz Gabás"+GREEN);
            Libro nuevoLibro = new Libro(3, "Lejos de Luisiana", 2023, 10, 4);    // CREO UN LIBRO Y LUEGO USO EL METODO update()
            libroRepositorio.update(nuevoLibro);


            ////////////////////////////////////////
            /////ELIMINANDO EL LIBRO CON ID = 4/////
            ////////////////////////////////////////
            System.out.println(RESET+"6) Eliminamos el libro con id = 4"+GREEN);
            libroRepositorio.deleteById(4);


            ////////////////////////////////////////
            ///USUARIO 1 TOMA LIBRO 5 (FECHA AYER)//
            ////////////////////////////////////////
            System.out.println(RESET+"7) Usuario 1 toma prestado el libro 5 con fecha de ayer"+GREEN);
            Prestamo prestamoNuevo1 = new Prestamo(null, LocalDate.now().minusDays(1), LocalDate.now().plusDays(10), 1, 5);
            PrestamoRespositorio prestamos = new PrestamoRespositorio(conn);
            prestamos.save(prestamoNuevo1);


            ////////////////////////////////////////
            ///USUARIO 2 TOMA LIBRO 5 (FECHA HOY)//
            ////////////////////////////////////////
            System.out.println(RESET+"8) Usuario 2 toma prestado el libro 5 con fecha de hoy"+GREEN);
            Prestamo prestamoNuevo2 = new Prestamo(null, LocalDate.now(), LocalDate.now().plusDays(10), 2, 5);
            prestamos.save(prestamoNuevo2);

            ////////////////////////////////////////
            ///USUARIO 2 TOMA LIBRO 2 (FECHA HOY)//
            ////////////////////////////////////////
            System.out.println(RESET+"9) Usuario 2 toma prestado el libro 2 con fecha de hoy"+GREEN);
            Prestamo prestamoNuevo3 = new Prestamo(null, LocalDate.now(), LocalDate.now().plusDays(10), 2, 2);
            prestamos.save(prestamoNuevo3);


            ////////////////////////////////////////////////
            //MUESTRA LOS PRESTAMOS DE LOS ULTIMOS 10 DIAS//
            ////////////////////////////////////////////////
            System.out.println(RESET+"10) Mostrar los préstamos de los últimos 10 días"+GREEN);
            List<Prestamo> listaPrestamos = prestamos.findDate(10);
            System.out.println();
            for (Prestamo prestamo: listaPrestamos) {
                System.out.println(prestamo);
            }
            System.out.println();


            ///////////////////////////////////////////////
            ///MUESTRA UNA LISTA DE LIBROS Y SUS AUTORES///
            ///////////////////////////////////////////////
            System.out.println(RESET+"11) Mostrar listado de libros con sus autores"+GREEN);
            listaLibros = libroRepositorio.BookAndAutor();
            System.out.println();
            for (Libro libro: listaLibros) {
                System.out.println("ID: "+libro.getIdLibro()+" [Titulo: "+ libro.getTitulo()+"]\n"+
                        "ID Autor: "+libro.getIdAutor()+" [Autor: "+libro.getNombreAutor()+"]");
                System.out.println();
            }
            System.out.println();


        } catch (SQLException e) {
            System.out.println("Error al establecer la conexión con la BD");
            System.out.println(e.getMessage());
        } finally {
            try {
                // Se cierra conexion
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("error al cerrar la conexión con la BD");
                System.out.println(e.getMessage());
            }
        }
    }

    public static void crearTablasBiblioteca() throws SQLException {

        // Creamos las tablas, la funcion createTable de AutorRepositorio comprueba si existen las tablas en la BD para eliminarlas
        // y despues crearlas de nuevo, esto evita errores a la hora de ejecutar por segunda vez la App

        AutorRepositorio crearAutorTabla = new AutorRepositorio(conn);
        crearAutorTabla.createTable();

        LibroRepositorio crearLibroTabla = new LibroRepositorio(conn);
        crearLibroTabla.createTable();

        UsuarioRepositorio crearUsuarioTabla = new UsuarioRepositorio(conn);
        crearUsuarioTabla.createTable();

        PrestamoRespositorio crearPrestamoTabla = new PrestamoRespositorio(conn);
        crearPrestamoTabla.createTable();

        System.out.println("OK, tablas creadas");
     }

    public static void insertarDatosIniciales() {

            AutorRepositorio listaAutores = new AutorRepositorio(conn);
            LibroRepositorio listaLibros = new LibroRepositorio(conn);
            UsuarioRepositorio listaUsuarios = new UsuarioRepositorio(conn); // Creamos instancias de los repositorios para insertar los datos

            Autor julioVerne = new Autor(1, "Julio Verne", "francesa");
            Autor miguelDeCervantes = new Autor(2, "Miguel de Cervantes", "española");
            Autor isabelAllende = new Autor(3, "Isabel Allende", "chilena");
            listaAutores.save(julioVerne);
            listaAutores.save(miguelDeCervantes);
            listaAutores.save(isabelAllende); // Se crea y se guarda los autores en la tabla

            Libro vueltaAlMundo = new Libro(1, "Vuelta al mundo en 80 dias", 1872, 5, 1);
            Libro elQuijote = new Libro(2, "El Quijote", 1615, 20, 2);
            Libro veinteMilLeguas = new Libro(3, "Veinte mil leguas de viaje submarino", 1970, 2, 1);
            Libro deLaTierra = new Libro(4, "De la Tierra a la Luna", 1864, 6, 1);
            Libro laCasaEspiritus = new Libro(5, "La casa de los espíritus", 1982, 1, 3);
            listaLibros.save(vueltaAlMundo);
            listaLibros.save(elQuijote);
            listaLibros.save(veinteMilLeguas);
            listaLibros.save(deLaTierra);
            listaLibros.save(laCasaEspiritus); // Se crean y se guardan los libros en la tabla

            Usuario usuario1 = new Usuario(1, "Federico Fiallos", "ffiallos@hotmail.com");
            Usuario usuario2 = new Usuario(2, "Antonia Pallicer", "apllicer@hotmail.com");
            listaUsuarios.save(usuario1);
            listaUsuarios.save(usuario2); // Se crean y se guardan los usuarios en la tabla

            System.out.println("OK, datos iniciales insertados");
    }
}