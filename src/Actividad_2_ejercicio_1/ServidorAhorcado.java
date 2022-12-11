package Actividad_2_ejercicio_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class ServidorAhorcado {

    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        String line;
        Scanner scanner = new Scanner(System.in);

        // Utilizamos la clase ServerSocket en el servidor
        serverSocket = new ServerSocket();
        System.out.println("**CONVERTIDOR DE DECIMAL A BINARIO - SERVIDOR**");
        System.out.println("Esperando Conexion...");
        // Creamos un Socket Adress para una máquina y numero de puerto
        InetSocketAddress addr = new InetSocketAddress("localhost", 5050);
        // Asignamos el socket a la direccion
        serverSocket.bind(addr);
        // Aceptamos la conexion
        Socket socket = serverSocket.accept();
        System.out.println("Cliente conectado...");
        System.out.println();
        // Creamos un buffer para leer los datos que nos envia el cliente
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        PrintStream ps = new PrintStream(socket.getOutputStream(), true);

        Random rand = new Random();
        // Crea una lista de palabras
        String[] palabras = {
                "casa",
                "gato",
        };
        // Selecciona una palabra al azar
        int aleatorio = rand.nextInt(palabras.length);
        String palabra = palabras[aleatorio];
        // Inicializa el contador de intentos
        int intentos = 0;
        // Crea una cadena de guiones para mostrar la palabra
        StringBuilder guiones = new StringBuilder();
        for (int i = 0; i < palabra.length(); i++) {
            guiones.append("-");
        }

        // Crea una variable para guardar la última letra ingresada
        String letra = "";
        // Bucle de juego
        while (intentos < 6) {

            // Muestra la palabra
            ps.println("Palabra: " + guiones + " [Intentos: " + intentos + "]");

            ps.flush();

            // recibe la letra de un cliente
            letra = br.readLine();

            // Si la letra está en la palabra, reemplaza los guiones por la letra
            if (palabra.indexOf(letra) != -1) {
                for (int i = 0; i < palabra.length(); i++) {
                    if (palabra.charAt(i) == letra.charAt(0)) {
                        guiones.setCharAt(i, letra.charAt(0));
                    }
                }
            } else {
                // Si la letra no está en la palabra, aumenta el contador de intentos
                intentos++;
                // Imprime los intentos

            }

            // Si la palabra ya no tiene guiones, el jugador ha ganado
            if (guiones.indexOf("-") == -1) {
                ps.println("¡Ganaste! La palabra era " + palabra + "¿Quieres jugar de nuevo? (s/n)");
                ps.flush();
                String respuesta = br.readLine();
                if (respuesta.equals("s")) {
                    // Selecciona una palabra al azar
                    aleatorio = rand.nextInt(palabras.length);
                    palabra = palabras[aleatorio];
                    // Inicializa el contador de intentos
                    intentos = 0;
                    // Crea una cadena de guiones para mostrar la palabra
                    guiones = new StringBuilder();
                    for (int i = 0; i < palabra.length(); i++) {
                        guiones.append("-");
                    }
                } else {
                    break;
                }

            }
            // Si el contador de intentos es igual a 6, el jugador ha perdido
            if (intentos == 6) {
                ps.println("Perdiste! La palabra era " + palabra + "¿Quieres jugar de nuevo? (s/n)");
                // Preguntar si quiere jugar de nuevo
                ps.flush();
                String respuesta = br.readLine();
                if (respuesta.equals("s")) {
                    // Selecciona una palabra al azar
                    aleatorio = rand.nextInt(palabras.length);
                    palabra = palabras[aleatorio];
                    // Inicializa el contador de intentos
                    intentos = 0;
                    // Crea una cadena de guiones para mostrar la palabra
                    guiones = new StringBuilder();
                    for (int i = 0; i < palabra.length(); i++) {
                        guiones.append("-");
                    }
                } else {
                    break;
                }
            }
        }

        // Cerrar el servidor
        serverSocket.close();
    }
}
