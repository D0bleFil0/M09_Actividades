package Actividad_2_ejercicio_1;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClienteAhorcado {

    public static void main(String[] args) throws Exception {
        String line;
        try {
            Socket socket = new Socket();
            System.out.println("**JUEGO DEL AHORCADO - CLIENTE**");
            System.out.println("Esperando Conexion...");
            InetSocketAddress addr = new InetSocketAddress("localhost", 5050);
            socket.connect(addr);
            System.out.println("Conectado...");
            System.out.println();

            // Cotnar durante 3 segundos y comienza el juego
            for (int i = 3; i > 0; i--) {
                System.out.print("\033[H\033[2J");
                System.out.println("**JUEGO DEL AHORCADO - CLIENTE**");
                // imprime el ahorcado
                System.out.println("");
                System.out.println("  +---+");
                System.out.println("  |   |");
                System.out.println("  O   |");
                System.out.println(" /|\\  |");
                System.out.println(" / \\  |");
                System.out.println("      |");
                System.out.println("=========");
                System.out.println("");
                System.out.println("Comienza en " + i);
                Thread.sleep(1000);
                // borra la pantalla
                System.out.print("\033[H\033[2J");
            }

            // crea un buffer para leer los datos que nos envia el servidor
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            // crea un buffer para enviar datos al servidor
            PrintStream ps = new PrintStream(socket.getOutputStream(), true);
            // Crea un scanner para leer la letra
            Scanner Scanner = new Scanner(System.in);

            // Variable para salir del bucle
            boolean exit = false;

            // Mientras exit sea false
            while (!exit) {
                // lee la linea del servidor
                line = br.readLine();
                // si la linea es null, sale del bucle
                if (line == null) {
                    break;
                }
                // borra la pantalla
                System.out.print("\033[H\033[2J");
                // se muestra la palabra
                System.out.println(line);
                // pide una letra al cliente
                String letra = Scanner.nextLine();

                // Bucle while para que no se pueda enviar una cadena vacia
                while (letra.equals("")) {
                    System.out.print("\033[H\033[2J");
                    System.out.println(line);
                    letra = Scanner.nextLine();
                }
                // limpia el buffer
                ps.flush();
                // envia la primera letra al servidor
                ps.println(letra.charAt(0));

            }

            // cierra el scanner
            Scanner.close();
            // cierra el socket
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
