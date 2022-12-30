package Actividad_3_ejercicio_1;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClienteEncriptacion {
    public static void main(String[] args) {
        try {
            // Obtiene el registro en el puerto 1099
            Registry registro = LocateRegistry.getRegistry(1099);
            // Obtiene el stub del objeto remoto
            ServidorEncriptacion.Encriptar stub = (ServidorEncriptacion.Encriptar) registro.lookup("Encriptar");
            // Crea el objeto Scanner
            Scanner sc = new Scanner(System.in);

            String frase = "";

            boolean salir = false;

            while (!salir) {
                // Borra la pantalla
                System.out.print("\033[H\033[2J");
                // Llama al método remoto de menu
                System.out.println(stub.mensajeMenu());
                // Pide al usuario que introduzca una opción
                //System.out.print("\n Mensaje: ");
                //String opcion = sc.nextLine();
                // Si la opción es F, sale del programa
                if (frase.equalsIgnoreCase("FIN")) {
                    System.out.println(" Saliendo del programa...");
                    
                    salir = true;
                } else {
                    // Pide al usuario que introduzca una frase
                    System.out.print("\n Introduce una frase: ");
                    frase = sc.nextLine();
                    // Llama al metodo para generar la clave
                    stub.generarLlaves();
                    // Llamma al metodo remoto de encriptar
                    String resultado = stub.encriptar(frase);
                    // Muestra el resultado
                    System.out.println("");
                    System.out.println("Mensaje encriptado: " + resultado);
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
        }
    }
}

