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
            while (true) {
                //Borra la pantalla
                System.out.print("\033[H\033[2J");
                // Llama al método remoto de menu
                System.out.println(stub.getMenu());
                // Pide al usuario que introduzca una opción
                System.out.print("\nIntroduce una opción: ");
                String opcion = sc.nextLine();
                // Pide al usuario que introduzca una frase
                System.out.print("\nIntroduce una frase: ");
                String frase = sc.nextLine();
                // Llama al metodo remoto de elegirOpcion
                String resultado = stub.elegirOpcion(opcion, frase);
                // Muestra el resultado
                System.out.println(resultado);
                if (opcion.equalsIgnoreCase("S")) {
                    break;
                }
                // Pausa el programa hasta que el usuario pulse una tecla
                System.out.println("\nPulsa una tecla para continuar...");
                System.in.read();
            }
        } catch (Exception e) {
            System.err.println("Excepción del cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}
