package Actividad_2_ejercicio_1;


import java.util.Random;
import java.util.Scanner;

public class prueba {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        // Crea una lista de palabras
        String[] palabras = {
            "casa",
            "gato",
            "perro",
            "computadora",
            "celular",
            "coche"
        };
        // Selecciona una palabra al azar
        int aleatorio = rand.nextInt(palabras.length);
        String palabra = palabras[aleatorio];
        // Inicializa el contador de intentos
        int intentos = 0;
        // Crea una cadena de guiones para mostrar la palabra
        StringBuilder guiones = new StringBuilder();
        for (int i = 0; i < palabra.length(); i++) {
            guiones.append("-");
        }
        // Crea una variable para guardar la última letra ingresada
        char letra = ' ';
        // Bucle de juego
        while (intentos < 6 && guiones.indexOf("-") != -1) {
            // Muestra la palabra
            System.out.println("Palabra: " + guiones);
            // Pide una letra
            System.out.print("Ingresa una letra: ");
            letra = scanner.next().charAt(0);
            // Si la letra está en la palabra, la mostramos
            if (palabra.indexOf(letra) != -1) {
                for (int i = 0; i < palabra.length(); i++) {
                    if (palabra.charAt(i) == letra) {
                        guiones.setCharAt(i, letra);
                    }
                }
            } else {
                intentos++;
                System.out.println("Letra incorrecta. Intentos restantes: " + (6 - intentos));
            }
            System.out.println();
        }
        // Comprobamos si el jugador ganó
        if (guiones.indexOf("-") == -1) {
            System.out.println("¡Has ganado!");
        } else {
            System.out.println("¡Has perdido!");
        }
    }
}