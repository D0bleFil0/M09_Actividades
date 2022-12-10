package Actividad_2_ejercicio_1;

import java.net.ServerSocket;
import java.util.Random;
import java.util.Scanner;

public class Ahorcado2 {

    private static ServerSocket serverSocket;
    // puerto para la conexion
    private static final int puerto = 5050;

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

    // metodo del juego
    public static void jugar(String[] palabras) {
        int intentos = 0;
        String palabra = elegirPalabra(palabras);
        String[] letras = crearArrayLetras(palabra);
        String[] ahorcado = crearArrayAhorcado();
        for (int i = 0; i < letras.length; i++) {
            letras[i] = "_";
        }
        Scanner sc = new Scanner(System.in);
        String letra;
        boolean esta;
        boolean victoria = false;
        System.out.println("Adivina la palabra");
        mostrarAhorcado(ahorcado);
        mostrarLetras(letras);
        while (!victoria && intentos < 6) {
            System.out.println("Introduce una letra");
            letra = sc.nextLine();
            esta = comprobarLetra(letra, palabra);
            if (esta) {
                for (int i = 0; i < palabra.length(); i++) {
                    if (letra.equals(palabra.substring(i, i + 1))) {
                        letras[i] = letra;
                    }
                }
                mostrarAhorcado(ahorcado);
                mostrarLetras(letras);
                victoria = comprobarVictoria(letras);
                if (victoria) {
                    System.out.println("Has ganado");
                }
            } else {
                intentos++;
                switch (intentos) {
                    case 1:
                        ahorcado[2] = "  0   |";
                        break;
                    case 2:
                        ahorcado[3] = "  |   |";
                        break;
                    case 3:
                        ahorcado[3] = " /|   |";
                        break;
                    case 4:
                        ahorcado[3] = " /|\\  |";
                        break;
                    case 5:
                        ahorcado[4] = " /    |";
                        break;
                    case 6:
                        ahorcado[4] = " / \\  |";
                        break;
                }
                mostrarAhorcado(ahorcado);
                mostrarLetras(letras);
                if (intentos == 6) {
                    System.out.println("Has perdido");
                }
            }
        }
    }

    // metodo main
    public static void main(String[] args) {
        String[] palabras = new String[10];
        palabras[0] = "programacion";
        palabras[1] = "ordenador";
        palabras[2] = "teclado";
        palabras[3] = "raton";
        palabras[4] = "monitor";
        palabras[5] = "sistemaoperativo";
        palabras[6] = "software";
        palabras[7] = "hardware";
        palabras[8] = "java";
        palabras[9] = "netbeans";
        jugar(palabras);
    }

}
