// Servidor.java
package Actividad_2_ejercicio_1;

// servidor para enviar mensajes a cliente
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorAhorcado {

    private static ServerSocket serverSocket;
    //puerto para la conexion
    private static final int puerto = 5050;

    public static void main(String[] args) {
        String line;
        try {
            // Utilizamos la clase ServerSocket en el servidor
            serverSocket = new ServerSocket();
            System.out.println("**SERVIDOR DE MENSAJES**");
            System.out.println("Esperando Conexion...");
            // Creamos un Socket Adress para una máquina y numero de puerto
            InetSocketAddress addr = new InetSocketAddress("localhost", puerto);
            // Asignamos el socket a la direccion
            serverSocket.bind(addr);
            // Aceptamos la conexion
            Socket socket = serverSocket.accept();
            System.out.println("Cliente conectado...");
            System.out.println();
            // Creamos un buffer para leer los datos que nos envia el cliente
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            // Creamos un try para leer los datos que nos envia el cliente
            try {
                // Leemos el numero que nos envia el cliente
                line = br.readLine();
                // Mientras no sea "fin"
                while (!line.equalsIgnoreCase("FIN")) {
                    System.out.println("Recibido: " + line);
                    // Creamos un buffer para escribir los datos que nos envia el cliente
                    PrintStream out = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                    out.println("Mensaje recibido");
                    // Leemos el numero que nos envia el cliente
                    line = br.readLine();
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
