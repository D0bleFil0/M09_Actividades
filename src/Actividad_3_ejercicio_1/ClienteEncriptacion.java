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
            // Crea la variable para almacenar la frase encriptada
            String fraseEncriptada2;
            // Crea la variable para almacenar la frase desencriptada


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
            // Si la opción es 1   
            if (opcion == 1) {
                // Encripta la frase
                fraseEncriptada = stub.encriptar(frase);
                // Muestra la frase encriptada
                System.out.println(" Frase encriptada: " + fraseEncriptada);
                // Desencripta la frase
                fraseDesencriptada = stub.desencriptar(fraseEncriptada);
                // Muestra la frase desencriptada
                System.out.println(" Frase desencriptada: " + fraseDesencriptada);
                // Si la opción es 2
            } else if (opcion == 2) {
                // Encripta la frase
                fraseEncriptada2 = stub.encriptar(frase);
                // Muestra la frase encriptada
                System.out.println(" Frase encriptada: " + fraseEncriptada2);
                // Desencripta la frase
                fraseDesencriptada = stub.desencriptar(fraseEncriptada2);
                // Muestra la frase desencriptada
                System.out.println(" Frase desencriptada: " + fraseDesencriptada);
            }
            // Si la opción es 3
            else if (opcion == 3) {
                // Encripta la frase
                fraseEncriptada = stub.encriptar(frase);
                // Muestra la frase encriptada
                System.out.println(" Frase encriptada: " + fraseEncriptada);
                // Desencripta la frase
                fraseDesencriptada = stub.desencriptar(fraseEncriptada);
                // Muestra la frase desencriptada
                System.out.println(" Frase desencriptada: " + fraseDesencriptada);
                // Encripta la frase
                fraseEncriptada2 = stub.encriptar(frase);
                // Muestra la frase encriptada
                System.out.println(" Frase encriptada: " + fraseEncriptada2);
                // Desencripta la frase
                fraseDesencriptada = stub.desencriptar(fraseEncriptada2);
                // Muestra la frase desencriptada
                System.out.println(" Frase desencriptada: " + fraseDesencriptada);
            }
        } catch (Exception e) {
            System.err.println("Excepción del cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}


