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
            // Crea la variable para almacenar la frase
            String frase;
            // Crea la variable para almacenar la opción
            int opcion;
            // Crea la variable para almacenar la frase encriptada
            String fraseEncriptada;
            // Crea la variable para almacenar la frase desencriptada
            String fraseDesencriptada;


            // Borra la pantalla
            System.out.print("\033[H\033[2J");
            // Muestra el menú
            System.out.println(stub.getMenu());
            // Pide la opción
            System.out.print(" Introduzca la opción deseada: ");
            // Almacena la opción
            opcion = sc.nextInt();
            // Pide la frase
            System.out.print(" Introduzca la frase: ");
            // Almacena la frase
            frase = sc.next();
            // Si la opción es 1, genera las claves
            if (opcion == 1) {
                // Llama al método remoto de generarClaves
                stub.generarLlaves();
                // Muestra el mensaje de claves generadas
                System.out.println(" Claves generadas");
            // Si la opción es 2, encripta la frase
            } else if (opcion == 2) {
                // Llama al método remoto de encriptar
                fraseEncriptada = stub.encriptar(frase);
                // Muestra la frase encriptada
                System.out.println(" Frase encriptada: " + fraseEncriptada);
            // Si la opción es 3, desencripta la frase
            } else if (opcion == 3) {
                // Llama al método remoto de desencriptar
                fraseDesencriptada = stub.desencriptar(frase);
                // Muestra la frase desencriptada
                System.out.println(" Frase desencriptada: " + fraseDesencriptada);
            // Si la opción es 4, sale del programa
            } else if (opcion == 4) {
                System.out.println(" Saliendo del programa...");
            // Si la opción no es correcta, muestra un mensaje de error
            } else {
                System.out.println(" Opción incorrecta");
            }
        } catch (Exception e) {
            System.err.println(" Error: " + e.toString());
            e.printStackTrace();
        }
    }
}

 