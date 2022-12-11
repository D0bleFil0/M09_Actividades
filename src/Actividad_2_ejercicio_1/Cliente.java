package Actividad_2_ejercicio_1;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

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

            // cuenta durante 5 segundos y despues borra la pantalla
            for (int i = 5; i > 0; i--) {
                System.out.print("\033[H\033[2J");
                System.out.println("Juego del ahorcado");
                // imprime el ahorcado
                System.out.println("  +---+");
                System.out.println("  |   |");
                System.out.println("  O   |");
                System.out.println(" /|\\  |");
                System.out.println(" / \\  |");
                System.out.println("      |");
                System.out.println("=========");

                System.out.println("Comienza en " + i);
                Thread.sleep(600);
                // borra la pantalla
                System.out.print("\033[H\033[2J");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            PrintStream ps = new PrintStream(socket.getOutputStream(), true);
            Scanner Scanner = new Scanner(System.in);

            // recibe la palabra del servidor y la muestra

            while ((line = br.readLine()) != null) {

                

                // borra la pantalla
                System.out.print("\033[H\033[2J");
                // se muestra la palabra

                System.out.println(line);

                // pide una letra al cliente
                String letra = Scanner.nextLine();
                // limpia el buffer
                ps.flush();
                // envia la letra al servidor
                ps.println(letra);

                // si se acaba el juego pregunta si quiere volver a jugar

            }
            
            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }   


}
