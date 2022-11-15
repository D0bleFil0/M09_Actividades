// Cliente.java

package actividad_2_ejercicio_0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Cliente {

	private static Socket clientSocket;

	public static void main(String[] args) {
		String line;
		// definimos el socket
		clientSocket = new Socket();
		// Creamos un Socket Adress para una máquina y numero de puerto
		InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
		try {
			System.out.println("**CONVERTIDOR DE DECIMAL A BINARIO - CLIENTE**");
			// Conectamos !!!
			clientSocket.connect(addr);
			// Envio la informacion a traves del canal de comunicacion establecido con el
			// socket
			PrintStream ps = new PrintStream(clientSocket.getOutputStream(), true);
			// Lee la informacion del teclado para enviarla al servidor
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
			System.out.println("-Conexión establecida-");
			System.out.println();
			System.out.println("Introduce un numero o escribe FIN para salir");
			System.out.println();
			// Lee la informacion del teclado para enviarla al servidor
			line = in.readLine();
			// Condicional para que solo pueda enviar numeros o la palabra FIN
			while (!line.equalsIgnoreCase("FIN")) {
				if (line.matches("[0-9]+")) {
					ps.println(line);
					// Creamos un buffer para leer los datos que nos envia el servidor
					BufferedReader br = new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
					// Leemos el numero que nos envia el servidor
					line = br.readLine();
					System.out.println("Enviado: " + line);
					System.out.println("Recibido: " + line);
					System.out.println();
					// Lee la informacion del teclado para enviarla al servidor
					line = in.readLine();
				} else {
					System.out.println("Solo puedes enviar numeros o la palabra FIN");
					System.out.println();
					// Lee la informacion del teclado para enviarla al servidor
					line = in.readLine();
				}
			}
			// Cerramos el socket
			clientSocket.close();
			System.out.println("Conexión cerrada");
			// Capturamos las excepciones
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
