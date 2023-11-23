import entidades.*;
import repositorios.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
    private static final String PASSWORD = "root";

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
            System.out.println("OK, tablas creadas");


            // Rellenamos nuestra libreria con 5 libros, 3 autores y 2 usuarios
            ////////////////////////////////////////
            ///////INSERTANDO DATOS INICIALES///////
            ////////////////////////////////////////
            System.out.println(RESET+"2) Insertamos datos inciales"+GREEN);
            insertarDatosIniciales();                                               // FUNCION QUE AGREGA LOS DATOS INCIALES
            System.out.println("OK, datos insertados");


            // Operaciones con libros
            ////////////////////////////////////////
            //////////LISTADO DE LOS LIBROS/////////
            ////////////////////////////////////////
            System.out.println(RESET+"3) Listado de libros"+GREEN);
            LibroRepositorio listaLibros = new LibroRepositorio(conn);              // CREO UNA INSTANCIA DE LibroRepositorio PARA PODER LISTAR
            List<Libro> lista = listaLibros.findAll();                              // LOS LIBROS CON UN FOREACH
            for (Libro libro:
                 lista) {
                System.out.println(libro.getTitulo());
            }


            ////////////////////////////////////////
            //////////NUEVO AUTOR AÑADIDO///////////
            ////////////////////////////////////////
            System.out.println(RESET+"4) Añadimos un nuevo autor ('Armando','Argentina')"+GREEN);
            Autor autorNuevo = new Autor(4, "Armando", "Argentina");
            AutorRepositorio nuevoAutor = new AutorRepositorio(conn);
            nuevoAutor.save(autorNuevo);
            System.out.println("OK, nuevo autor insertado id = 4 ");


            ////////////////////////////////////////
            //////MODIFICANDO LIBRO CON ID = 3//////
            ////////////////////////////////////////
            System.out.println(RESET+"5) Modificamos el  libro id = 3  ('Lejos de Luisiana' ,2023, 10 copias) y le asociamos el autor Luz Gabás"+GREEN);
            Libro nuevoLibro = new Libro(3, "Lejos de Luisiana", "Luz Gabás", 2023, 10);    // CREO UN LIBRO Y LUEGO USO EL METODO update()
            listaLibros.update(nuevoLibro);
            System.out.println("Libro modificado correctamente");


            ////////////////////////////////////////
            /////ELIMINANDO EL LIBRO CON ID = 4/////
            ////////////////////////////////////////
            System.out.println(RESET+"6) Eliminamos el libro con id = 4"+GREEN);
            listaLibros.deleteById(4);
            System.out.println("Libro con el id = 4 eliminado");


            ////////////////////////////////////////
            ///USUARIO 1 TOMA LIBRO 5 (FECHA AYER)//
            ////////////////////////////////////////
            System.out.println(RESET+"7) Usuario 1 toma prestado el libro 5 con fecha de ayer"+GREEN);
            // TODO realizar prestamo 
            System.out.println("Préstamo realizado");


            ////////////////////////////////////////
            ///USUARIO 2 TOMA LIBRO 5 (FECHA HOY)//
            ////////////////////////////////////////
            System.out.println(RESET+"8) Usuario 2 toma prestado el libro 5 con fecha de hoy"+GREEN);
            // TODO realizar prestamo
            System.out.println("Lo sentimos pero no quedan copias disponibles");


            ////////////////////////////////////////
            ///USUARIO 2 TOMA LIBRO 2 (FECHA HOY)//
            ////////////////////////////////////////
            System.out.println(RESET+"9) Usuario 2 toma prestado el libro 2 con fecha de hoy"+GREEN);
            // TODO realizar prestamo
            System.out.println("Préstamo realizado");


            ////////////////////////////////////////////////
            //MUESTRA LOS PRESTAMOS DE LOS ULTIMOS 10 DIAS//
            ////////////////////////////////////////////////
            System.out.println(RESET+"10) Mostrar los préstamos de los últimos 10 días"+GREEN);


            ///////////////////////////////////////////////
            ///MUESTRA UNA LISTA DE LIBROS Y SUS AUTORES///
            ///////////////////////////////////////////////
            System.out.println(RESET+"11) Mostrar listado de libros con sus autores"+GREEN);
            // TODO listado de libros con sus autores

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

