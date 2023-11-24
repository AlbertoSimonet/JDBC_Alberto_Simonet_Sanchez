import entidades.*;
import repositorios.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class App {

    // Colores de la fuente
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

    // JDBC URL, usuario, y password para MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/dam";
    private static final String USER = "root";
    private static final String PASSWORD = "VUESTRA CONTRASEÑA DE LA BASE DE DATOS";

    // Variable JDBC  para manejar la conexión con la BD
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

            // Rellenamos nuestra libreria con 5 libros, 3 autores y 2 usuarios
            ////////////////////////////////////////
            ///////INSERTANDO DATOS INICIALES///////
            ////////////////////////////////////////
            System.out.println(RESET+"2) Insertamos datos inciales"+GREEN);
            insertarDatosIniciales();                                               // FUNCION QUE AGREGA LOS DATOS INCIALES

            // Operaciones con libros
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
                System.out.println("Titulo: "+libro.getTitulo());
            }
            System.out.println();


            ////////////////////////////////////////
            //////////NUEVO AUTOR AÑADIDO///////////
            ////////////////////////////////////////
            System.out.println(RESET+"4) Añadimos un nuevo autor ('Armando','Argentina')"+GREEN);
            Autor autorNuevo = new Autor(4, "Armando", "Argentina");
            AutorRepositorio nuevoAutor = new AutorRepositorio(conn);
            nuevoAutor.save(autorNuevo);


            ////////////////////////////////////////
            //////MODIFICANDO LIBRO CON ID = 3//////
            ////////////////////////////////////////
            System.out.println(RESET+"5) Modificamos el  libro id = 3  ('Lejos de Luisiana' ,2023, 10 copias) y le asociamos el autor Luz Gabás"+GREEN);
            Libro nuevoLibro = new Libro(3, "Lejos de Luisiana", "Luz Gabás", 2023, 10);    // CREO UN LIBRO Y LUEGO USO EL METODO update()
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
            List<Prestamo> listaPrestamos = prestamos.findAll();
            for (Prestamo prestamo: listaPrestamos) {
                System.out.println(prestamo);
            }
            ///////////////////////////////////////////////
            ///MUESTRA UNA LISTA DE LIBROS Y SUS AUTORES///
            ///////////////////////////////////////////////
            System.out.println(RESET+"11) Mostrar listado de libros con sus autores"+GREEN);
            for (Libro libro: listaLibros) {
                System.out.println("Titulo: "+ libro.getTitulo()+", Autor: "+libro.getAutor());
            }


        } catch (SQLException e) {
            System.out.println("Error al establecer la conexión con la BD");
            System.out.println(e.getMessage());
        } finally {
            try {
                // Cerrar conexión
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("error al cerrar la conexión con la BD");
                System.out.println(e.getMessage());
            }
        }
    }

    public static void crearTablasBiblioteca() throws SQLException {

        LibroRepositorio crearLibroTabla = new LibroRepositorio(conn);
        crearLibroTabla.createTable();

        UsuarioRepositorio crearUsuarioTabla = new UsuarioRepositorio(conn);
        crearUsuarioTabla.createTable();

        AutorRepositorio crearAutorTabla = new AutorRepositorio(conn);
        crearAutorTabla.createTable();

        PrestamoRespositorio crearPrestamoTabla = new PrestamoRespositorio(conn);
        crearPrestamoTabla.createTable();

        System.out.println("OK, tablas creadas");
     }

    public static void insertarDatosIniciales() {
        try {
            Statement sentencia = conn.createStatement();

            sentencia.executeUpdate("INSERT INTO Autor (ID, Nombre, Nacionalidad) VALUES " +
                    "(1, 'Julio Verne', 'francesa'), " +
                    "(2, 'Miguel de Cervantes', 'española'), " +
                    "(3, 'Isabel Allende', 'chilena')");

            sentencia.executeUpdate("INSERT INTO Libro (ID, Titulo, Autor, AnioPublicacion, CopiasDisponibles) VALUES " +
                    "(1, 'Vuelta al mundo en 80 dias', 'Julio Verne', 1872, 5), " +
                    "(2, 'El Quijote', 'Miguel de Cervantes', 1615, 20), " +
                    "(3, 'Veinte mil leguas de viaje submarino', 'Julio Verne', 1970, 2), " +
                    "(4, 'De la Tierra a la Luna', 'Julio Verne', 1864, 6), " +
                    "(5, 'La casa de los espíritus', 'Isabel Allende', 1982, 1)");

            sentencia.executeUpdate("INSERT INTO Usuario (ID, Nombre, CorreoElectronico) VALUES " +
                    "(1, 'Federico Fiallos', 'ffiallos@hotmail.com'), " +
                    "(2, 'Antonia Pallicer', 'apllicer@hotmail.com')");

            sentencia.close();
            System.out.println("OK, datos iniciales insertados");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

