// Servidor.java
package Actividad_2_ejercicio_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServidorAhorcado {

    // Creamos un objeto de la clase ServerSocket para el servidor
    private static ServerSocket serverSocket;

    public static void main(String[] args) {

        // Crear un array de palabras con 100 posiciones posibles
        String[] palabras = new String[100];
        // Variable para guardar el indice del array
        int indice = 0;

        // Leer el fichero de palabras y guardarlas en el array
        try {
            // Obtener ruta del fichero
            String ruta = Ahorcado.class.getResource("diccionario.txt").getPath();
            // Leer el fichero
            BufferedReader br = new BufferedReader(new FileReader(ruta));
            // Leer la primera linea
            String linea = br.readLine();
            // Leer el resto de lineas y guardarlas en el array mientras no sea null
            while (linea != null) {
                palabras[indice] = linea;
                System.out.println(palabras[indice]);
                indice++;
                linea = br.readLine();
            }
            // Cerrar el fichero
            br.close();
            // Capturar errores
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        // Eliminar las posiciones del array que sean null
        String[] palabras2 = new String[indice];
        for (int i = 0; i < indice; i++) {
            palabras2[i] = palabras[i];
        }
        palabras = palabras2;

        // Elegir una palabra aleatoria del diccionario
        Random r = new Random();
        indice = r.nextInt(palabras.length);
        String palabra = palabras[indice];

        // Crear un array de letras con tantas posiciones como letras tenga la palabra
        String[] letras = new String[palabra.length()];

        // Crear un array de strings para dibujar el ahorcado
        String[] ahorcado = new String[6];
        ahorcado[0] = "  +---+";
        ahorcado[1] = "  |   |";
        ahorcado[2] = "      |";
        ahorcado[3] = "      |";
        ahorcado[4] = "      |";
        ahorcado[5] = "========";

        // Inicializar el array de letras con guiones bajos
        for (int i = 0; i < letras.length; i++) {
            letras[i] = "_";
        }

        // Bloque de codigo para el servidor

        String line;

        // Se crea la conexion
        try {
            // Utilizamos la clase ServerSocket en el servidor
            serverSocket = new ServerSocket();
            System.out.println("**JUEGO DEL AHORCADO - SERVIDOR**");
            System.out.println("Esperando Conexion...");
            // Creamos un Socket Adress para una máquina y numero de puerto
            InetSocketAddress addr = new InetSocketAddress("localhost", 5050);
            // Asignamos el socket a la direccion
            serverSocket.bind(addr);
            // Aceptamos la conexion
            Socket socket = serverSocket.accept();
            System.out.println("Cliente conectado...");
            System.out.println();
            // Creamos un buffer para leer los datos que nos envia el cliente
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

            // Recibimos la palabra del cliente
            // Creamos un try para leer los datos que nos envia el cliente
            try {
                // Leemos el numero que nos envia el cliente
                line = br.readLine();
                boolean victoria = false;
                int intentos = 7;
                // Cierra el bucle cuando se acaben los intentos o se acierte la palabra
                do {
                    // borrar la pantalla y dibujar el ahorcado
                    System.out.print("\033[H\033[2J");
                    System.out.println("Intentos: " + intentos);
                    System.out.println("");
                    for (int i = 0; i < ahorcado.length; i++) {
                        System.out.println(ahorcado[i]);
                    }
                    for (int i = 0; i < letras.length; i++) {
                        System.out.print(letras[i] + " ");
                    }
                    // Pedir una letra
                    System.out.println("");
                    System.out.println("");
                    System.out.println("Introduce una letra:");
                    String letra = sc.nextLine();
                    boolean acierto = false;
                    // Comprobar si la letra está en la palabra
                    for (int i = 0; i < palabra.length(); i++) {
                        if (letra.equals(palabra.substring(i, i + 1))) {
                            letras[i] = letra;
                            acierto = true;
                        }
                    }

                    // Si no se ha acertado la letra, se dibuja una parte del ahorcado usando
                    // case-switch
                    if (!acierto) {
                        intentos--;
                        switch (intentos) {
                            case 6:
                                ahorcado[2] = "  O   |";
                                break;
                            case 5:
                                ahorcado[3] = "  |   |";
                                break;
                            case 4:
                                ahorcado[3] = " /|   |";
                                break;
                            case 3:
                                ahorcado[3] = " /|\\  |";
                                break;
                            case 2:
                                ahorcado[4] = " /    |";
                                break;
                            case 1:
                                ahorcado[4] = " / \\  |";
                                break;
                        }
                    }
                    // Comprobar si se ha acertado la palabra y dibujar el ahorcado completo
                    victoria = true;
                    for (int i = 0; i < letras.length; i++) {
                        if (letras[i].equals("_")) {
                            victoria = false;
                        }
                    }
                    // Cierra el bucle cuando se acaben los intentos o se acierte la palabra
                } while (intentos > 0 && !victoria);
                if (victoria) {
                    // Borrar la pantalla
                    System.out.print("\033[H\033[2J");
                    // Dibujar ahorcado
                    for (int i = 0; i < ahorcado.length; i++) {
                        System.out.println(ahorcado[i]);
                    }
                    for (int i = 0; i < letras.length; i++) {
                        System.out.print(letras[i] + " ");
                    }
                    // Mensaje de victoria
                    System.out.println("");
                    System.out.println("");
                    System.out.println("¡Has ganado!");
                    System.out.println("");
                    // Reinicia partida
                    System.out.println("¿Quieres jugar otra vez? (s/n)");
                    String respuesta = sc.nextLine();
                    if (respuesta.equalsIgnoreCase("s")) {
                        main(args);
                    }

                } else {
                    // Borrar la pantalla y mostrar mensaje de derrota con el ahorcado completo
                    System.out.print("\033[H\033[2J");
                    System.out.println("¡Has muerto!");
                    System.out.println("");
                    // Dibujar ahocardo completo
                    for (int i = 0; i < ahorcado.length; i++) {
                        System.out.println(ahorcado[i]);
                    }
                    System.out.print("\nLa palabra era: " + palabra);
                    System.out.println("");
                    System.out.println("");
                    // Reinicia partida
                    System.out.println("¿Quieres jugar otra vez? (s/n)");
                    String respuesta = sc.nextLine();
                    sc.close();
                    // si es igual a s ignorar mayusculas y minusculas
                    if (respuesta.equalsIgnoreCase("s")) {
                        main(args);
                    }
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
