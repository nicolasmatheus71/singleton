// Main.java

import java.util.Scanner;

public class Main {

    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        System.out.println(" DEMOSTRACION DEL PATRON SINGLETON ");
        pausar("Presione Enter para empezar...");

        escenario1();
        pausar("Presione Enter para el escenario 2...");

        escenario2();
        pausar("Presione Enter para el escenario 3...");

        escenario3();
        pausar("Presione Enter para el escenario 4...");

        escenario4();

        System.out.println();
        System.out.println("Fin de la demostracion.");
    }

    // Escenario 1: pedimos la conexion dos veces y miramos si es el mismo objeto.
    private static void escenario1() {
        System.out.println();
        System.out.println("--- Escenario 1: la misma instancia ---");

        ConexionBD a = ConexionBD.getInstance();
        ConexionBD b = ConexionBD.getInstance();

        System.out.println("a == b ? " + (a == b));
        System.out.println("Identificador de a: " + System.identityHashCode(a));
        System.out.println("Identificador de b: " + System.identityHashCode(b));
    }

    // Escenario 2: dos clientes distintos usan la conexion y el contador es de los dos.
    private static void escenario2() {
        System.out.println();
        System.out.println("--- Escenario 2: estado compartido ---");

        Cliente usuarios = new Cliente("Usuarios");
        Cliente reportes = new Cliente("Reportes");

        usuarios.usarConexion();
        reportes.usarConexion();
        usuarios.usarConexion();

        int total = ConexionBD.getInstance().getConsultasEjecutadas();
        System.out.println("Total de consultas en la conexion: " + total);
    }

    // Escenario 3: sin Singleton, cada "new" crea una conexion nueva e independiente.
    private static void escenario3() {
        System.out.println();
        System.out.println("--- Escenario 3: sin Singleton ---");

        ConexionSinSingleton c1 = new ConexionSinSingleton();
        ConexionSinSingleton c2 = new ConexionSinSingleton();

        c1.ejecutarConsulta("SELECT * FROM usuarios");
        c2.ejecutarConsulta("SELECT * FROM reportes");

        System.out.println("c1 == c2 ? " + (c1 == c2));
        System.out.println("Consultas de c1: " + c1.getConsultasEjecutadas());
        System.out.println("Consultas de c2: " + c2.getConsultasEjecutadas());
    }

    // Escenario 4: varios hilos piden la conexion al mismo tiempo.
    // Todos deben recibir la misma instancia y ninguna consulta se debe perder.
    private static void escenario4() throws InterruptedException {
        System.out.println();
        System.out.println("--- Escenario 4: varios hilos ---");

        int cantidad = 5;
        int[] identificadores = new int[cantidad];
        Thread[] hilos = new Thread[cantidad];

        int antes = ConexionBD.getInstance().getConsultasEjecutadas();

        // Cada hilo pide la conexion, guarda el identificador y hace una consulta.
        for (int i = 0; i < cantidad; i++) {
            final int numero = i;
            hilos[i] = new Thread(() -> {
                ConexionBD conexion = ConexionBD.getInstance();
                identificadores[numero] = System.identityHashCode(conexion);
                conexion.ejecutarConsulta("SELECT * FROM hilo" + numero);
            });
            hilos[i].start();
        }

        // Esperamos a que todos terminen antes de mostrar los resultados.
        for (Thread hilo : hilos) {
            hilo.join();
        }

        boolean todosIguales = true;
        for (int i = 0; i < cantidad; i++) {
            System.out.println("Hilo " + i + " recibio el identificador: " + identificadores[i]);
            if (identificadores[i] != identificadores[0]) {
                todosIguales = false;
            }
        }

        int despues = ConexionBD.getInstance().getConsultasEjecutadas();
        System.out.println("Todos recibieron la misma instancia ? " + todosIguales);
        System.out.println("Consultas hechas por los hilos: " + (despues - antes));
    }

    // Muestra un mensaje y espera a que se presione Enter.
    private static void pausar(String mensaje) {
        System.out.println();
        System.out.println(mensaje);
        if (teclado.hasNextLine()) {
            teclado.nextLine();
        }
    }
}

