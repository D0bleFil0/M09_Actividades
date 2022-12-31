package Actividad_3_ejercicio_2;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClienteCifrado {
    public static void main(String[] args) {
        try {
            // Obtiene el registro en el puerto 1099
            Registry registro = LocateRegistry.getRegistry(1099);
            // Obtiene el stub del objeto remoto
            ServidorCifrado.Encriptar stub = (ServidorCifrado.Encriptar) registro.lookup("Encriptar");
            // Crea el objeto Scanner
            Scanner sc = new Scanner(System.in);
            // Variable para almacenar la frase
            String frase = "";

            // Bucle infinito
            while (true) {
                // Borra la pantalla
                System.out.print("\033[H\033[2J");
                // Llama al método remoto de menu
                System.out.println(stub.mensajeMenu());
                // Pide al usuario que introduzca una frase
                System.out.print("\nOpción: ");
                // Almacena la opción elegida por el usuario
                int opcion = sc.nextInt();
                // Borra la pantalla
                System.out.print("\033[H\033[2J");

                // Estructura switch para elegir la opción

                switch (opcion) {
                    case 1:
                        // Pide al usuario que introduzca una frase
                        System.out.print("Introduce una frase: ");
                        // Almacena la frase introducida por el usuario
                        frase = sc.next();
                        // Borra la pantalla
                        System.out.print("\033[H\033[2J");
                        // Llama al método remoto de encriptar
                        System.out.println("Frase encriptada: " + stub.encriptar(frase));
                        break;

                    case 2:
                        // Pide al usuario que introduzca una frase
                        System.out.print("Introduce una frase: ");
                        // Almacena la frase introducida por el usuario
                        frase = sc.next();
                        // Borra la pantalla
                        System.out.print("\033[H\033[2J");
                        // Llama al método remoto de desencriptar
                        System.out.println("Frase desencriptada: " + stub.desencriptar(frase));
                        break;

                    case 3:
                        // Borra la pantalla
                        System.out.print("\033[H\033[2J");
                        // Llama al método remoto generarLlaves
                        System.out.println(stub.generarLlaves());
                        break;

                    case 4:
                        // Borra la pantalla
                        System.out.print("\033[H\033[2J");
                        // Llama al método remoto mostrarLlaves, que devuelve un array
                        String[] llaves = stub.mostrarLlaves();
                        // Muestra las llaves
                        System.out.println(llaves[0]);
                        System.out.println(llaves[1]);
                        break;

                    case 5:
                        // Borra la pantalla
                        System.out.print("\033[H\033[2J");
                        // Mensaje fin del programa
                        System.out.println("Fin del programa");
                        // Finaliza el programa
                        System.exit(0);
                        break;

                    default:
                        // Borra la pantalla
                        System.out.print("\033[H\033[2J");
                        // Mensaje de error
                        System.out.println("Error, opción incorrecta");
                        break;
                    // Vuelve al main

                }
                // Pausa el programa
                System.out.println("\nPulse una tecla para continuar...");
                System.in.read();
            }
        } catch (Exception e) {
            System.err.println("Excepción del cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}
