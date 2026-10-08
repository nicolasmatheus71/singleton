// ConexionSinSingleton.java
// Esta clase NO es un Singleton. Solo sirve para comparar:
// aqui el constructor es publico, entonces cada "new" crea una conexion distinta.

public class ConexionSinSingleton {

    // Cada objeto tiene su propio contador, no se comparte con nadie.
    private int consultasEjecutadas;

    // Constructor publico: cualquiera puede hacer "new ConexionSinSingleton()".
    public ConexionSinSingleton() {
        System.out.println("Conectando a la base de datos...");
        consultasEjecutadas = 0;
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
