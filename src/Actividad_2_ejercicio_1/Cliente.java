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
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            PrintStream ps = new PrintStream(socket.getOutputStream(), true);
            Scanner Scanner = new Scanner(System.in);

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                System.out.print("Respuesta: " + line); // Imprime la respuesta del servidor
                
                ps.println(Scanner.nextLine());

                ps.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
