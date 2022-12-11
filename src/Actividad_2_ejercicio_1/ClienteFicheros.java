package Actividad_2_ejercicio_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClienteFicheros {

	public static void main(String[] args) {

		 if (args.length != 1) {
		 System.out.println("argumentos: <id del fichero>");
		 System.exit(1);
		 }

		 try {

		 String id_fichero = args[0];

		 Socket clientSocket = new Socket();
		 //conectamos con el servidor.
		 InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
		 clientSocket.connect(addr);

		 InputStream is = clientSocket.getInputStream();
		 OutputStream os = clientSocket.getOutputStream();
		 PrintWriter pw = new PrintWriter(os, true);

		 System.out.println("Descargando fichero "+id_fichero);
		 //pasamos al servidor el nombre del fichero
		 //se podria modificar para aceptar un nombre por el teclado.
		 pw.println(id_fichero);
		 BufferedReader br = new BufferedReader(new InputStreamReader(is));
		 //obtenemos del servidor el fichero letra a letra.
		 char buffer[] = new char[1];
		 while (br.read(buffer) != -1) {
		 System.out.print(buffer[0]);
		 }
		 clientSocket.close();
		 } catch (IOException e) {
		 e.printStackTrace();
		 }
		 }
}
