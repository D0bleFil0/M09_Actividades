package Actividad_2_ejercicio_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class ServidorAhorcado {

    // metodo para elegir una palabra aleatoria del diccionario
    public static String elegirPalabra(String[] palabras) {
        Random r = new Random();
        int indice = r.nextInt(palabras.length);
        String palabra = palabras[indice];
        return palabra;
    }

    // metodo para crear un array de letras con tantas posiciones como letras tenga
    // la palabra
    public static String[] crearArrayLetras(String palabra) {
        String[] letras = new String[palabra.length()];
        return letras;
    }

    // metodo para crear un array de strings para dibujar el ahorcado
    public static String[] crearArrayAhorcado() {
        String[] ahorcado = new String[6];
        ahorcado[0] = "  +---+";
        ahorcado[1] = "  |   |";
        ahorcado[2] = "      |";
        ahorcado[3] = "      |";
        ahorcado[4] = "      |";
        ahorcado[5] = "========";
        return ahorcado;
    }

    // metodo para mostrar el ahorcado
    public static void mostrarAhorcado(String[] ahorcado) {
        for (int i = 0; i < ahorcado.length; i++) {
            System.out.println(ahorcado[i]);
        }
    }

    // metodo para mostrar las letras
    public static void mostrarLetras(String[] letras) {
        for (int i = 0; i < letras.length; i++) {
            System.out.print(letras[i] + " ");
        }
        System.out.println();
    }

    // metodo para comprobar si la letra esta en la palabra
    public static boolean comprobarLetra(String letra, String palabra) {
        boolean esta = false;
        for (int i = 0; i < palabra.length(); i++) {
            if (letra.equals(palabra.substring(i, i + 1))) {
                esta = true;
            }
        }
        return esta;
    }

    // metodo para comprobar si se ha ganado
    public static boolean comprobarVictoria(String[] letras) {
        boolean victoria = true;
        for (int i = 0; i < letras.length; i++) {
            if (letras[i].equals("_")) {
                victoria = false;
            }
        }
        return victoria;
    }

    // metodo del juego con conexion al cliente
    public static void juego(String[] palabras, Socket clienteSocket) {
        try {
            String palabra = elegirPalabra(palabras);
            String[] letras = crearArrayLetras(palabra);
            for (int i = 0; i < letras.length; i++) {
                letras[i] = "_";
            }
            String[] ahorcado = crearArrayAhorcado();
            boolean victoria = false;
            int fallos = 0;
            boolean esta;
            BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clienteSocket.getOutputStream()));
            Scanner sc = new Scanner(System.in);
            String inputLine;
            while (!victoria && fallos < 6) {
                out.write("La palabra es: ");
                out.newLine();
                out.flush();
                mostrarLetras(letras);
                out.write("El ahorcado es: ");
                out.newLine();
                out.flush();
                mostrarAhorcado(ahorcado);
                out.write("Introduce una letra: ");
                out.newLine();
                out.flush();
                String letra = in.readLine();
                esta = comprobarLetra(letra, palabra);
                if (esta) {
                    out.write("La letra " + letra + " esta en la palabra");
                    out.newLine();
                    out.flush();
                    for (int i = 0; i < palabra.length(); i++) {
                        if (letra.equals(palabra.substring(i, i + 1))) {
                            letras[i] = letra;
                        }
                    }
                    victoria = comprobarVictoria(letras);
                } else {
                    out.write("La letra " + letra + " no esta en la palabra");
                    out.newLine();
                    out.flush();
                    ahorcado[fallos + 2] = "  O   |";
                    fallos++;
                }
            }
            if (victoria) {
                out.write("Enhorabuena, has ganado");
                out.newLine();
                out.flush();
            } else {
                out.write("Has perdido");
                out.newLine();
                out.flush();
            }
            out.write("La palabra era: " + palabra);
            out.newLine();
            out.flush();
            out.write("adios");
            out.newLine();
            out.flush();
            in.close();
            out.close();
            clienteSocket.close();
        } catch (Exception e) {
            
        }
    }

    // metodo main
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket();
            InetSocketAddress addr = new InetSocketAddress("localhost", 5050);
            serverSocket.bind(addr);
            String[] palabras = {"algoritmo", "bug", "compilador", "debug", "error", "expresion", "funcion", "hacker", "hardware", "idioma", "informatica", "inteligencia", "inteligencia artificial", "juego", "lenguaje", "libreria", "ordenador", "palabra", "programa", "programacion", "programador", "software", "variable"};
            while (true) {
                System.out.println("Esperando cliente...");
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Cliente conectado");
                juego(palabras, clienteSocket);
            }
        } catch (Exception e) {
            
        }
    }
}

