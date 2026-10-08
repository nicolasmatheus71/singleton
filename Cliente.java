// Cliente.java
// Representa una parte cualquiera del programa (por ejemplo "Usuarios" o "Reportes")
// que necesita usar la conexion a la base de datos.

public class Cliente {

    private String nombre;

    public Cliente(String nombre) {
        this.nombre = nombre;
    }

    // El cliente NO crea la conexion con "new": se la pide al Singleton con getInstance().
    // Asi todos los clientes terminan usando la misma.
    public void usarConexion() {
        System.out.println(nombre + " pide la conexion...");
        ConexionBD conexion = ConexionBD.getInstance();
        conexion.ejecutarConsulta("SELECT * FROM " + nombre.toLowerCase());
    }
}
