// Servidor.java
package Actividad_2_ejercicio_0;    

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    // Funcion para convertir un numero decimal a binario
    public static String decimalABinario(int numero) {
        String binario = "";
        while (numero > 0) {
            if (numero % 2 == 0) {
                binario = "0" + binario;
            } else {
                binario = "1" + binario;
            }
            numero = numero / 2;
        }
        // Bloque para completar con ceros a la izquierda
        int longitud = binario.length();
        for (int i = 0; i < 4 - longitud; i++) {
            binario = "0" + binario;
        }
        return binario;
    }

    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        String line;
        try {
            // Utilizamos la clase ServerSocket en el servidor
            serverSocket = new ServerSocket();
            System.out.println("**CONVERTIDOR DE DECIMAL A BINARIO - SERVIDOR**");
            System.out.println("Esperando Conexion...");
            // Creamos un Socket Adress para una máquina y numero de puerto
            InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
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
                    // Convertimos el numero a binario
                    String binario = decimalABinario(Integer.parseInt(line));
                    System.out.println("Enviado: " + binario);
                    System.out.println();
                    // Enviamos el numero binario
                    PrintStream ps = new PrintStream(socket.getOutputStream(), true);
                    ps.println(binario);
                    ps.flush();
                    line = binario;
                    line = br.readLine();

                }
            } catch (NullPointerException ex) {
                System.out.println("Finalizada transmision...");
            }
            // cerramos conexion
            socket.close();
            System.out.println("Fin.");

        } catch (IOException e) {
            System.out.println("OOOPS !!!");
            e.printStackTrace();
        }
    }

}