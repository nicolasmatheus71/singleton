// ConexionBD.java
// Esta clase es el Singleton: en todo el programa solo puede existir
// un objeto de ConexionBD, y todos lo comparten.

public class ConexionBD {

    // Aqui se guarda la unica instancia. Es static porque pertenece a la clase
    // y no a un objeto. El volatile es para que los hilos vean siempre el valor actualizado.
    private static volatile ConexionBD instance;

    // Cuenta cuantas consultas se han ejecutado. Como la conexion es una sola,
    // este contador lo comparten todos los que la usen.
    private int consultasEjecutadas;

    // Constructor privado: desde otra clase no se puede hacer "new ConexionBD()".
    private ConexionBD() {
        System.out.println("Conectando a la base de datos...");
        consultasEjecutadas = 0;
    }

    // Unica forma de obtener la conexion.
    // Si todavia no existe la crea, y si ya existe devuelve la misma.
    public static ConexionBD getInstance() {
        if (instance == null) {                 // primera revision
            synchronized (ConexionBD.class) {   // evita que dos hilos la creen a la vez
                if (instance == null) {         // segunda revision, ya dentro del bloqueo
                    instance = new ConexionBD();
                }
            }
        }
        return instance;
    }

    // Simula ejecutar una consulta SQL.
    public void ejecutarConsulta(String sql) {
        consultasEjecutadas++;
        System.out.println("Ejecutando: " + sql);
    }

    public int getConsultasEjecutadas() {
        return consultasEjecutadas;
    }
}
