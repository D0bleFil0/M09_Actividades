package Actividad_2_ejercicio_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClienteAhorcado {

	public static void main(String[] args) throws IOException {
		// Bloque try-catch para capturar errores de conexion
		try {
			// Crea un socket para conectarse al servidor
			Socket socket = new Socket();
			System.out.println("**JUEGO DEL AHORCADO - CLIENTE**");
			System.out.println("Esperando Conexion...");
			// Crea una direccion para conectarse al servidor
			InetSocketAddress addr = new InetSocketAddress("localhost", 5050);
			socket.connect(addr);
			System.out.println("Conectado...");
			System.out.println();

			// Crea un buffer para leer los datos que nos envia el servidor
			InputStream is = socket.getInputStream();

			// Crea un buffer para enviar datos al servidor
			PrintStream ps = new PrintStream(socket.getOutputStream(), true);
			// Crea un scanner para leer la letra
			Scanner Scanner = new Scanner(System.in);

			// Variable para salir del bucle
			boolean exit = false;

			// Mientras exit sea false, imprime el menu
			while (!exit) {

				// Si recibe un exit del servidor, sale del bucle
				if (is.read() == 'e') {
					exit = true;
					break;
				}
				// limpia la pantalla
				System.out.print("\033[H\033[2J");

				// Creo un buffer para leer los datos que nos envia el servidor
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				// Lee el buffer hasta que encuentre un !
				char buffer[] = new char[1];
				while (br.read(buffer) != -1) {
					if (buffer[0] == '#') {
						break;
					}
					System.out.print(buffer);
				}

				// Pide una letra al cliente
				String letra = Scanner.nextLine();

				// Bucle while para evitar que se envíe algo distinto de 1 o 2
				while (!letra.equals("1") && !letra.equals("2")) {
					System.out.println("Solo se puede enviar 1 o 2");
					letra = Scanner.nextLine();
				}
				// limpia el buffer
				ps.flush();
				// Si la letra es 1, envia la primera letra al servidor
				if (letra.equals("1")) {
					// envia la primera letra al servidor
					ps.println(letra.charAt(0));
					// Si la letra es 2, cierra el programa
				} else if (letra.equals("2")) {
					// envia la primera letra al servidor
					System.out.println("Adios");
					System.exit(0);
				}
			}

			// Variable para salir del bucle
			exit = false;

			// Mientras exit sea false
			while (!exit) {
				// Si recibe un exit del servidor, sale del bucle
				if (is.read() == 'e') {
					exit = true;
					break;
				}
				// limpia la pantalla
				System.out.print("\033[H\033[2J");

				// Imprime la informacion del ahorcado que nos envia el servidor
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				// Lee el buffer hasta que encuentre un !
				char buffer[] = new char[1];
				while (br.read(buffer) != -1) {
					if (buffer[0] == '#') {
						break;
					}
					System.out.print(buffer);
				}

				// pide una letra al cliente
				String letra = Scanner.nextLine();

				// Bucle while para que no se pueda enviar una cadena vacia
				while (letra.equals("")) {
					// System.out.println("No se puede enviar una cadena vacia");
					letra = Scanner.nextLine();
				}
				// limpia el buffer
				ps.flush();
				// envia la primera letra al servidor
				ps.println(letra.charAt(0));
			}

			// Mensaje de despedida
			System.out.println("  Gracias por jugar");
			// cierra el scanner
			Scanner.close();
			// cierra el socket
			socket.close();

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			// cierra el programa
			System.exit(0);

		}
	}
}
