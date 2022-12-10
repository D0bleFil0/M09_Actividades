// juego del ahorcado cliente-servidor

package Actividad_2_ejercicio_1;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClienteAhorcado {

    public static void main(String[] args) {
        String line;
        Scanner sc = new Scanner(System.in);

        try {
            // Creamos un socket con el que nos conectaremos al servidor
            Socket socket = new Socket();

            // Creamos un Socket Adress para una máquina y numero de puerto
            InetSocketAddress addr = new InetSocketAddress("localhost", 5050);

            // Asignamos el socket a la direccion
            socket.connect(addr);

            // Creamos un buffer para leer los datos que nos envia el servidor
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

            // Creamos un try para leer los datos que nos envia el servidor
            try {
                // Leemos la palabra que nos envia el servidor
                line = br.readLine();

                // Mientras no sea “fin”
                while (!line.equalsIgnoreCase("FIN")) {
                    System.out.println("Recibido: " + line);

                    // Enviamos la letra
                    System.out.println("Enviado: ");
                    String letra = sc.nextLine();
                    PrintStream ps = new PrintStream(socket.getOutputStream(), true);
                    ps.println(letra);
                    ps.flush();

                    // Leemos la palabra que nos envia el servidor
                    line = br.readLine();

                }

            } catch (NullPointerException ex) {
                System.out.println("Finalizada transmision…");
            }
            // cerramos conexion
            socket.close();
            System.out.println("Fin");

        } catch (IOException e) {
            System.out.println("OOOPS!!!");
            e.printStackTrace();
        }

    }

}