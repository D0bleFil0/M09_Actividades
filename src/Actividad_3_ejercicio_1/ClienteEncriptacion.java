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
            // Variable para almacenar la frase
            String frase = "";
            // Variable para salir del programa en bucle while
            boolean salir = false;
            // Bucle while para repetir el programa hasta que el usuario introduzca la FIN
            while (!salir) {
                // Borra la pantalla
                System.out.print("\033[H\033[2J");
                // Llama al método remoto de menu
                System.out.println(stub.mensajeMenu());
                // Pide al usuario que introduzca una frase
                System.out.print("\nIntroduce una frase: ");
                frase = sc.nextLine();
                if (frase.equalsIgnoreCase("FIN")) {
                    System.out.println("\n\033[35mSaliendo del programa...\n");
                    salir = true;
                } else {

                    // Llama al metodo para generar la clave
                    stub.generarLlaves();
                    // Llamma al metodo remoto de encriptar
                    String cifrar = stub.encriptar(frase);
                    // Muestra el resultado
                    System.out.println("");
                    // Guarda el mensaje encriptado en un array de 40 caracteres por linea
                    String[] cifrado = cifrar.split("(?<=\\G.{40})");
                    System.out.println("\033[31mMensaje encriptado: \033[0m \n");
                    // Recorre el array y muestra el mensaje encriptado en color verde
                    for (int i = 0; i < cifrado.length; i++) {
                        System.out.println("\033[32m" + cifrado[i] + "\033[0m");
                    }
                    System.out.println("");
                    String descifrar = stub.desencriptar(cifrar);
                    System.out.println("\033[31mMensaje desencriptado: \033[0m \n" + "\033[32m\n" + descifrar +"\033[0m");
                    System.out.println("");
                    // Pide al usuario que pulse intro para continuar
                    System.out.println("\nPulsa intro para continuar...");
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
