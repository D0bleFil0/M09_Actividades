package Actividad_2_ejercicio_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


  
public class ClienteAhorcado {

    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static int puerto = 5050;
  // metodo main para el cliente
    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", puerto);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner sc = new Scanner(System.in);
            String palabra = in.readLine();
            String[] letras = new String[palabra.length()];
            String[] ahorcado = crearArrayAhorcado();
            int intentos = 0;
            boolean acertado = false;
            String letra = "";
            while (intentos < 6 && !acertado) {
                mostrarAhorcado(ahorcado);
                mostrarLetras(letras);
                System.out.println("Introduce una letra:");
                letra = sc.nextLine();
                out.println(letra);
                if (comprobarLetra(letra, palabra)) {
                    for (int i = 0; i < palabra.length(); i++) {
                        if (palabra.charAt(i) == letra.charAt(0)) {
                            letras[i] = letra;
                        }
                    }
                    acertado = comprobarGanador(letras);
                } else {
                    intentos++;
                    ahorcado = cambiarAhorcado(ahorcado, intentos);
                }
            }
            mostrarAhorcado(ahorcado);
            mostrarLetras(letras);
            if (acertado) {
                System.out.println("Has ganado!");
            } else {
                System.out.println("Has perdido!");
            }
            out.close();
            in.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();    }
    }
    // metodo para crear el array del ahorcado
    public static String[] crearArrayAhorcado() {
        String[] ahorcado = new String[8];
        ahorcado[0] = " _______";
        ahorcado[1] = " |     |";
        ahorcado[2] = " |";
        ahorcado[3] = " |";
        ahorcado[4] = " |";
        ahorcado[5] = " |";
        ahorcado[6] = " |";
        ahorcado[7] = "_|_";
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
            if (letras[i] == null) {
                System.out.print("_ ");
            } else {
                System.out.print(letras[i] + " ");
            }
        }
        System.out.println("");
    }
    // metodo para cambiar el ahorcado
    public static String[] cambiarAhorcado(String[] ahorcado, int intentos) {
        switch (intentos) {
            case 1:
                ahorcado[2] = " |     O";
                break;
            case 2:
                ahorcado[3] = " |     |";
                break;
            case 3:
                ahorcado[3] = " |    /|";
                break;
            case 4:
                ahorcado[3] = " |    /|\\";
                break;
            case 5:
                ahorcado[4] = " |    /";
                break;
            case 6:
                ahorcado[4] = " |    / \\";
                break;
        }
        return ahorcado;
    }
    // metodo para comprobar si la letra esta en la palabra
    public static boolean comprobarLetra(String letra, String palabra) {
        boolean acertado = false;
        for (int i = 0; i < palabra.length(); i++) {
            if (palabra.charAt(i) == letra.charAt(0)) {
                acertado = true;
            }
        }
        return acertado;
    }
    // metodo para comprobar si se ha ganado
    public static boolean comprobarGanador(String[] letras) {
        boolean ganador = true;
        for (int i = 0; i < letras.length; i++) {
            if (letras[i] == null) {
                ganador = false;
            }
        }
        return ganador;
    }
}
