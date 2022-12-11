package Actividad_2_ejercicio_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorFicheros {
	public static void procesarCliente(Socket socket) {
		try {
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			BufferedReader br_socket = new BufferedReader(new InputStreamReader(is));
			// obtenemos el nombre del fichero
			String id_fichero = br_socket.readLine();
			BufferedReader br_fichero = new BufferedReader(new FileReader(id_fichero+".txt"));
			// leemos el fichero y lo mandamos por el socket
			while (br_fichero.ready()) {
				char b = (char) br_fichero.read();
				os.write(b);
			}
			br_fichero.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	 
	public static void main(String[] args) {
	 try {
		 ServerSocket serverSocket = new ServerSocket();
		// Creamos un Socket Adress para una  m�quina y numero de puerto
		 InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
		// Enlaza el serverSocket a una direccion especifica (IP  y numero de puerto).
		 serverSocket.bind(addr);
		 while (true) {
			// Estamos a la escucha de una connexi�n a este socket y la aceptamos
			 Socket newSocket = serverSocket.accept();
			 procesarCliente(newSocket);
			 newSocket.close();
		 }
	 	} catch (IOException e) {
	 		e.printStackTrace();
	 	}
	 }
}

