// juego del ahorcado cliente-servidor

package Actividad_2_ejercicio_1;

// cliente para enviar mensajes al servidor
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClienteAhorcado {

    private static Socket socket;
    //puerto para la conexion
    private static final int puerto = 5050;

    public static void main(String[] args) {
        String line;
        try {
            // Creamos un socket para conectarnos con el servidor
            socket = new Socket();
            System.out.println("Conectando...");
            // Creamos un Socket Adress para una máquina y numero de puerto
            InetSocketAddress addr = new InetSocketAddress("localhost", puerto);
            // Asignamos el socket a la direccion
            socket.connect(addr);
            System.out.println("Conectado");
            System.out.println();
            // Creamos un buffer para leer los datos que nos envia el cliente
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            // Creamos un try para leer los datos que nos envia el cliente
            try {
                // Creamos un buffer para escribir los datos que nos envia el cliente
                PrintStream out = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                // Creamos un Scanner para leer los datos que nos envia el cliente
                Scanner sc = new Scanner(System.in);
                // Leemos el numero que nos envia el cliente
                line = sc.nextLine();
                // Mientras no sea "fin"
                while (!line.equalsIgnoreCase("FIN")) {
                    // Escribimos el numero que nos envia el cliente
                    out.println(line);
                    // Leemos el numero que nos envia el cliente
                    line = br.readLine();
                    System.out.println(line);
                    line = sc.nextLine();
                }
                System.out.println("Fin de la conexion");
                socket.close();
            } catch (IOException e) {
                System.out.println("Error de E/S");
            }
        } catch (IOException e) {
            System.out.println("Error de E/S");
        }
    }

}
