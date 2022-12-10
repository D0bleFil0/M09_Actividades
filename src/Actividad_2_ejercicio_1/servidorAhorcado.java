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

public class servidorAhorcado {

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
        char letra = ' ';
        // Bucle de juego
        while (intentos < 6 && guiones.indexOf("-") != -1) {
            // Muestra la palabra
            ps.println("Palabra: " + guiones);
            // Pide una letra
            ps.print("Ingresa una letra: ");
            // recibe la letra de un cliente
            letra = br.readLine().charAt(0);

            
            // Si la letra está en la palabra, la mostramos
            if (palabra.indexOf(letra) != -1) {
                for (int i = 0; i < palabra.length(); i++) {
                    if (palabra.charAt(i) == letra) {
                        guiones.setCharAt(i, letra);
                    }
                }
            } else {
                intentos++;
                ps.println("Letra incorrecta. Intentos restantes: " + (6 - intentos));
            }
            ps.println();
            ps.flush();
        }
        // Comprobamos si el jugador ganó
        if (guiones.indexOf("-") == -1) {
            ps.println("¡Has ganado!");
        } else {
            ps.println("¡Has perdido!");
        }
    }
}