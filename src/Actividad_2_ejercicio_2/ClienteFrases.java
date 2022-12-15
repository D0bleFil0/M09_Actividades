package Actividad_2_ejercicio_2;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

// Crea la clase cliente para ServidorFrases que implementa la interfaz remota
public class ClienteFrases {

    // Metodo principal
    public static void main(String[] args) {
        try {
            // Crea el registro en el puerto 1099
            Registry registro = LocateRegistry.getRegistry(1099);
            // Crea el stub
            ServidorFrases.Frases stub = (ServidorFrases.Frases) registro.lookup("Frases");
            // Crea el objeto Scanner
            Scanner sc = new Scanner(System.in);

            String frase = "";

            boolean salir = false;

            while (!salir) {
                // Borra la pantalla
                System.out.print("\033[H\033[2J");
                // Llama al método remoto de menu
                System.out.println(stub.getMenu());
                // Pide al usuario que introduzca una opción
                System.out.print("\n Introduce una opción: ");
                String opcion = sc.nextLine();
                // Si la opción es F, sale del programa
                if (opcion.equalsIgnoreCase("F")) {
                    System.out.println(" Saliendo del programa...");
                    stub.elegirOpcion(opcion, "F");
                    salir = true;
                } else {
                    // Pide al usuario que introduzca una frase
                    System.out.print("\n Introduce una frase: ");
                    frase = sc.nextLine();

                    // Llama al metodo remoto de elegirOpcion
                    String resultado = stub.elegirOpcion(opcion, frase);
                    // Muestra el resultado
                    System.out.println("");
                    System.out.println(resultado);
                    System.out.println("\n");

                    // Pide al usuario que pulse intro para continuar
                    System.out.println(" Pulsa intro para continuar...");
                    sc.nextLine();
                }

            }

            // Cierra el objeto Scanner
            sc.close();

        } catch (Exception e) {
            System.err.println("Excepción del cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}
