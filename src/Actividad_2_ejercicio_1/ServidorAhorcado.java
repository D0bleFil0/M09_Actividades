package Actividad_2_ejercicio_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

//Clase principal del programa servidor para jugar al ahorcado con un cliente y un servidor
public class ServidorAhorcado {

    // Método principal del programa
    public static void main(String[] args) throws IOException {
        // Crear un socket para escuchar en el puerto 1234
        ServerSocket servidor = new ServerSocket();
        // Crear un socket para escuchar en el puerto 1234
        InetSocketAddress addr = new InetSocketAddress("localhost", 1234);
        servidor.bind(addr);
        // Crear un socket para escuchar en el puerto 1234
        System.out.println("Esperando cliente...");
        // Crear un socket para escuchar en el puerto 1234
        Socket cliente = servidor.accept();
        // Crear un objeto para enviar datos al cliente
        PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);
        // Crear un objeto para recibir datos del cliente
        BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        // Crear un array de palabras con 100 posiciones posibles
        String[] palabras = new String[100];
        // Variable para guardar el indice del array
        int indice = 0;
        // Leer el fichero de palabras y guardarlas en el array
        try {
            // Obtener ruta del fichero
            String ruta = ServidorAhorcado.class.getResource("palabras.txt").getPath();
            // Crear un objeto para leer el fichero
            Scanner lector = new Scanner(new java.io.File(ruta));
            // Leer el fichero de palabras y guardarlas en el array
            while (lector.hasNext()) {
                palabras[indice] = lector.next();
                indice++;
            }
            // Cerrar el fichero
            lector.close();
        } catch (Exception e) {
            System.out.println("Error al leer el fichero de palabras");
        }
        // Crear un array de caracteres para guardar la palabra a adivinar
        char[] palabra = new char[100];
        // Crear un array de caracteres para guardar la palabra a mostrar
        char[] palabra_mostrar = new char[100];
        // Crear un array de caracteres para guardar las letras introducidas
        char[] letras = new char[100];
        // Variable para guardar el indice del array
        int indice_letras = 0;
        // Variable para guardar el numero de fallos
        int fallos = 0;
        // Variable para guardar el estado del juego
        String estado = "continuar";
        // Bucle principal del juego
        while (true) {
            // Generar una palabra aleatoria
            int aleatorio = (int) (Math.random() * indice);
            // Guardar la palabra en el array de caracteres
            palabra = palabras[aleatorio].toCharArray();
            // Guardar la palabra a mostrar en el array de caracteres
            palabra_mostrar = palabras[aleatorio].toCharArray();
            // Bucle para ocultar las letras de la palabra
            for (int i = 0; i < palabra_mostrar.length; i++) {
                palabra_mostrar[i] = '_';
            }
            // Bucle principal del juego
            while (true) {
                // Mostrar el estado del ahorcado
                for (int i = 0; i < 6; i++) {
                    if (i == fallos) {
                        salida.println("X");
                    } else {
                        salida.println(" ");
                    }
                }
                // Mostrar la palabra a adivinar
                salida.println(palabra_mostrar);
                // Leer la letra introducida por el usuario
                String letra = entrada.readLine();
                // Guardar la letra en el array de caracteres
                letras[indice_letras] = letra.charAt(0);
                // Variable para guardar si la letra esta en la palabra
                boolean esta = false;
                // Bucle para comprobar si la letra esta en la palabra
                for (int i = 0; i < palabra.length; i++) {
                    if (palabra[i] == letra.charAt(0)) {
                        palabra_mostrar[i] = letra.charAt(0);
                        esta = true;
                    }
                }
                // Si la letra no esta en la palabra aumentar el numero de fallos
                if (!esta) {
                    fallos++;
                }
                // Si el numero de fallos es 6 el estado es "perder"
                if (fallos == 6) {
                    estado = "perder";
                }
                // Si la palabra a mostrar es igual a la palabra el estado es "ganar"
                if (String.valueOf(palabra_mostrar).equals(String.valueOf(palabra))) {
                    estado = "ganar";
                }
                // Enviar el estado del juego al cliente
                salida.println(estado);
                // Si el estado es "perder" o "ganar" terminar el juego
                if
                (estado.equals("perder") || estado.equals("ganar")) {
                    break;
                }
                // Incrementar el indice del array de letras
                indice_letras++;
            }
            // Si el estado es "perder" mostrar la palabra a adivinar
            if (estado.equals("perder")) {
                salida.println(palabra);
            }
            // Leer la respuesta del cliente
            String respuesta = entrada.readLine();
            // Si la respuesta es "si" reiniciar el juego
            if (respuesta.equals("si")) {
                fallos = 0;
                estado = "continuar";
                indice_letras = 0;
            } else {
                // Si la respuesta es "no" terminar el juego
                break;
            }
        }
        // Cerrar el socket del cliente
        cliente.close();
        // Cerrar el socket del servidor
        servidor.close();
    }
}
