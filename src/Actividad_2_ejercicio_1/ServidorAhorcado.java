package Actividad_2_ejercicio_1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class ServidorAhorcado {

    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException, InterruptedException, NullPointerException {

        // GESTION DE FICHERO DE PALABRAS
        // Crea un objeto de la clase File para leer el archivo de texto
        File archivo = new File("palabras.txt");

        // Comprueba si el archivo existe, si no, lo crea con el nombre palabras.txt y
        // añade la palabra "hola"
        if (!archivo.exists()) {
            archivo.createNewFile();
            PrintStream ps = new PrintStream(archivo);
            ps.println("hola");
            ps.close();
        }
        // Crea un array de String para guardar las palabras del archivo
        try (BufferedReader obj = new BufferedReader(new FileReader(archivo))) {
            String palabras[] = new String[100];
            int contador = 0;
            String linea = obj.readLine();
            while (linea != null) {
                palabras[contador] = linea;
                contador++;
                linea = obj.readLine();
            }

            // Elimina las posiciones vacias del array
            String palabras2[] = new String[contador];
            for (int i = 0; i < contador; i++) {
                palabras2[i] = palabras[i];
            }

            // Escoge una palabra al azar
            Random rand = new Random();
            // Selecciona una palabra al azar
            int aleatorio = rand.nextInt(palabras2.length);
            String palabra = palabras2[aleatorio];
            // Inicializa el contador de intentos
            int intentos = 0;
            // Crea una cadena de guiones para mostrar la palabra
            StringBuilder guiones = new StringBuilder();
            for (int i = 0; i < palabra.length(); i++) {
                guiones.append("-");
            }

            // BLOQUE DE CODIGO PARA EL SERVIDOR, CONEXION Y COMUNICACION
            // Utilizamos la clase ServerSocket en el servidor
            serverSocket = new ServerSocket();
            System.out.println("**JUEGO DEL AHORCADO - SERVIDOR**");
            System.out.println("Esperando Conexion...");
            System.out.println("");
            System.out.println(
                    "El fichero de palabras es palabras.txt, si no existe se creara uno nuevo con la palabra hola. Si quieres cambiar las palabras, añade una palabra por linea en el fichero de texto y reinicia el servidor.");
            System.out.println("");
            System.out.println("Puedes cerrar el servidor con Ctrl + C");

            // Creamos un Socket Adress para una máquina y numero de puerto
            InetSocketAddress addr = new InetSocketAddress("localhost", 5050);
            // Asignamos el socket a la direccion
            serverSocket.bind(addr);
            // Acepta la conexion
            Socket socket = serverSocket.accept();
            System.out.println("Cliente conectado...");
            System.out.println();
            // Crea un buffer para leer los datos que nos envia el cliente
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            // Crea un buffer para enviar datos al cliente
            PrintStream ps = new PrintStream(socket.getOutputStream(), true);
            // Crea un scanner para leer la letra
            Scanner scanner = new Scanner(System.in);

            // BLOQUE DE CODIGO DEL JUEGO
            // Crear un array de strings para dibujar el ahorcado
            String[] ahorcado = new String[6];
            ahorcado[0] = "    :---+";
            ahorcado[1] = "        |";
            ahorcado[2] = "        |";
            ahorcado[3] = "        |";
            ahorcado[4] = "        |";
            ahorcado[5] = "  ========";

            // Crear un array de strings para dibujar el ahorcado completo
            String[] ahorcado2 = new String[6];
            ahorcado2[0] = "    +---+";
            ahorcado2[1] = "    |   |";
            ahorcado2[2] = "    O   |";
            ahorcado2[3] = "   /|\\  |";
            ahorcado2[4] = "   / \\  |";
            ahorcado2[5] = "  ========";

            // Crea variables para guardar la letra y la respuesta del menu
            String letra;
            String respuesta = "1";

            // MENU PRINCIPAL
            // Bucle del menu principal en un try catch para controlar las excepciones
            try {
                // Crea una variable para salir del bucle
                boolean exit = false;

                // Bucle de juego
                while (!exit) {
                    // Titulo del juego
                    ps.println("  ***JUEGO DEL AHORCADO - CLIENTE**\n");
                    // Muestra el ahorcado completo
                    for (int i = 0; i < ahorcado2.length; i++) {
                        ps.println(ahorcado2[i]);
                    }
                    // Muestra menu con opciones 1 y 2
                    ps.println("");
                    ps.println("  1. Jugar");
                    ps.println("  2. Salir");
                    ps.print("\n  Elige una opcion: ");
                    ps.println("#");
                    ps.flush();
                    respuesta = br.readLine();
                    // Si elige la opcion 1, empieza el juego
                    if (respuesta.equals("1")) {
                        ps.print("e");
                        exit = true;
                    }
                    if (respuesta.equals("2")) {
                        ps.print("e");
                        respuesta = "0";
                        // volver al inicio del programa
                        main(args);
                    }
                }

                // Devuelve el valor de la variable exit al valor inicial
                exit = false;

                // JUEGO
                // Bucle de juego en un try catch para controlar las excepciones
                while (!exit) {
                    // Vacía el buffer
                    ps.flush();
                    // Si la palabra ya no tiene guiones, el jugador ha ganado
                    if (guiones.indexOf("-") == -1) {
                        // Titulo del juego
                        ps.println("  ***JUEGO DEL AHORCADO - CLIENTE**\n");
                        // Muestra el ahorcado completo o parcial
                        for (int i = 0; i < intentos; i++) {
                            ps.println(ahorcado2[i]);
                        }
                        for (int i = intentos; i < 6; i++) {
                            ps.println(ahorcado[i]);
                        }
                        ps.println("\n  ¡Ganaste!");
                        ps.println("  La palabra era: " + palabra);
                        ps.print("\n  ¿Quieres jugar de nuevo? (s/n): ");
                        ps.println("#");
                        ps.flush();
                        // Lee la respuesta del cliente
                        respuesta = br.readLine();
                        // Si la respuesta es s, se reinicia el juego
                        if (respuesta.equals("s")) {
                            aleatorio = rand.nextInt(palabras2.length);
                            palabra = palabras2[aleatorio];
                            intentos = 0;
                            guiones = new StringBuilder();
                            for (int i = 0; i < palabra.length(); i++) {
                                guiones.append("-");
                            }
                            // Si la respuesta es n, se sale del juego
                        } else {
                            exit = true;
                            ps.print("e");
                        }
                    }

                    // Si el contador de intentos es 6, el jugador ha perdido
                    if (intentos == 6) {
                        ps.println("  ***JUEGO DEL AHORCADO - CLIENTE**\n");
                        // Se imprime el ahorcado completo
                        for (int i = 0; i < ahorcado2.length; i++) {
                            ps.println(ahorcado2[i]);
                        }
                        ps.println("\n  ¡Perdiste!");
                        ps.println("  La palabra era: " + palabra);
                        ps.print("\n  ¿Quieres jugar de nuevo? (s/n): ");
                        ps.println("#");
                        ps.flush();
                        // Lee la respuesta del cliente
                        respuesta = br.readLine();
                        // Si la respuesta es s, se reinicia el juego
                        if (respuesta.equals("s")) {
                            aleatorio = rand.nextInt(palabras2.length);
                            palabra = palabras2[aleatorio];
                            intentos = 0;
                            guiones = new StringBuilder();
                            for (int i = 0; i < palabra.length(); i++) {
                                guiones.append("-");
                            }
                            // Si la respuesta es n, se sale del juego
                        } else {
                            exit = true;
                            ps.print("e");
                        }
                    }
                    // Titulo del juego
                    ps.println("  ***JUEGO DEL AHORCADO - CLIENTE**\n");
                    // Muestra el ahorcado completo o parcial
                    for (int i = 0; i < intentos; i++) {
                        ps.println(ahorcado2[i]);
                    }
                    for (int i = intentos; i < 6; i++) {
                        ps.println(ahorcado[i]);
                    }
                    ps.println();

                    // Muestra la palabra
                    ps.println("  Palabra: " + guiones);
                    ps.println("  Intentos: " + intentos + "/6");
                    // Pide una letra
                    ps.print("\n  Ingresa una letra: ");
                    ps.println("#");
                    ps.flush();
                    // recibe la letra de un cliente
                    letra = br.readLine();

                    // Si la letra está en la palabra, reemplaza los guiones por la letra
                    if (palabra.indexOf(letra) != -1) {
                        for (int i = 0; i < palabra.length(); i++) {
                            if (palabra.charAt(i) == letra.charAt(0)) {
                                guiones.setCharAt(i, letra.charAt(0));
                            }
                        }
                    } else {
                        // Si la letra no está en la palabra, aumenta el contador de intentos
                        intentos++;
                    }
                }
                // Cierra el Scanner
                scanner.close();
                // Cierra el PrintStream
                ps.close();
                // Cierra el buffer
                br.close();
                // Cierra el socket
                socket.close();
                // Cerrar el servidor
                serverSocket.close();
                // Termina el programa
                System.exit(0);

            } catch (NullPointerException e) {
                // Cierra el socket y el servidor
                socket.close();
                serverSocket.close();
                System.out.println("  ¡Cliente desconectado!");
                System.out.println("");
                // vuelve al inicio del programa
                main(args);
            } catch (IOException e) {
                // Cierra el socket y el servidor
                socket.close();
                serverSocket.close();
                System.out.println("  ¡Cliente desconectado!");
                System.out.println("");
                // vuelve al inicio del programa
                main(args);
            }
        }
    }
}
