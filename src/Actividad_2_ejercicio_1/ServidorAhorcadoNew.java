package Actividad_2_ejercicio_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;


public class ServidorAhorcadoNew {

    //private static ServerSocket serverSocket;

    // Funcion para leer por teclado
    public static String leerTeclado() throws IOException {
        // Crea un buffer para leer por teclado
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // Lee el buffer
        String texto = br.readLine();
        // Devuelve el texto leido
        return texto;
    }

    // Funcion para leer el fichero de palabras
    public static String[] leerFichero() throws IOException {
        // Crea un fichero con el nombre de palabras.txt
        File fichero = new File("palabras.txt");
        // Si el fichero no existe, crea uno nuevo con la palabra hola
        if (!fichero.exists()) {
            fichero.createNewFile();
            PrintStream ps = new PrintStream(fichero);
            ps.println("hola");
            ps.close();
        }
        // Crea un buffer para leer el fichero
        BufferedReader br = new BufferedReader(new FileReader(fichero));
        // Crea un string para guardar las palabras
        String palabras = "";
        // Lee el fichero hasta que no haya mas lineas
        while (br.ready()) {
            palabras += br.readLine() + " ";
        }
        // Cierra el buffer
        br.close();
        // Separa las palabras por espacios
        String[] palabras2 = palabras.split(" ");
        // Devuelve un array con las palabras
        return palabras2;
    }

    // Funcion para comprobar si la letra esta en la palabra
    public static boolean comprobarLetra(char[] letras, char letra) {
        // Recorre el array de letras
        for (int i = 0; i < letras.length; i++) {
            // Si la letra esta en el array de letras
            if (letras[i] == letra) {
                // Devuelve true
                return true;
            }
        }
        // Devuelve false
        return false;
    }


    // Funcion para comprobar si la palabra esta completa
    public static boolean comprobarPalabra(String palabra, String palabra2) {
        // Si la palabra es igual a la palabra2, devuelve true
        if (palabra.equals(palabra2)) {
            return true;
        }
        // Si no, devuelve false
        return false;
    }

    // Funcion para mostrar el ahorcado
    public static void mostrarAhorcado(int intentos) {
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

            // Dibuja el ahorcado segun el numero de intentos
            // Guarda el ahorcado completo o parcial en un array que devuelve esta funcion
            String[] ahorcado3 = new String[6];
            for (int i = 0; i < 6; i++) {
                if (intentos == 0) {
                    ahorcado3[i] = ahorcado[i];
                } else if (intentos == 1) {
                    ahorcado3[i] = ahorcado2[i];
                } else if (intentos == 2) {
                    ahorcado3[i] = ahorcado2[i];
                } else if (intentos == 3) {
                    ahorcado3[i] = ahorcado2[i];
                } else if (intentos == 4) {
                    ahorcado3[i] = ahorcado2[i];
                } else if (intentos == 5) {
                    ahorcado3[i] = ahorcado2[i];
                } else if (intentos == 6) {
                    ahorcado3[i] = ahorcado2[i];
                }
            }
            // Muestra el ahorcado  
            for (int i = 0; i < 6; i++) {
                System.out.println(ahorcado3[i]);
            }
        }
        
        // Genera una palabra al azar
        public static String generarPalabra() throws IOException {
            // Crea un array con las palabras del fichero
            String[] palabras = leerFichero();
            // Crea un numero al azar
            Random r = new Random();
            int numero = r.nextInt(palabras.length);
            // Devuelve la palabra
            return palabras[numero];
        }

        // Funcion para generar guiones
        public static String generarGuiones(String palabra) {
            // Crea una cadena de guiones para mostrar la palabra
            StringBuilder guiones = new StringBuilder();
            for (int i = 0; i < palabra.length(); i++) {
                guiones.append("_");
            }
            // Devuelve la cadena de guiones
            return guiones.toString();
        }

                //Funcion para limpiar la pantalla
    private static void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
    }


        //Funcion para el menu
        public static void menu() {
            // Muestra el menu
            limpiarPantalla();
            System.out.println("+++++++++++++++++++++++++++++++");
            System.out.println("++++++++++AHORCADO+++++++++++++");
            System.out.println("+++++++++++++++++++++++++++++++");
            // Dibuja el ahorcado completo
            mostrarAhorcado(6);
            System.out.println("+++++++++++++++++++++++++++++++");
            // Muestra las opciones del menu
            System.out.println("1. Jugar");
            System.out.println("2. Salir");
            System.out.println("+++++++++++++++++++++++++++++++");
            System.out.print("Elige una opcion: ");
        }

           

            //Funcion escoger opcion
    public static int leerOpcion() throws IOException {
        // Lee la opcion del teclado con un buffer
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // Lee la opcion
        int opcion = Integer.parseInt(br.readLine());

        // Devuelve la opcion
        return opcion;
    }

    // Funcion para leer letras del teclado
    public static String leerLetra() throws IOException {
        // Lee la letra del teclado con un buffer
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // Lee la letra
        String letra = br.readLine();
        // Devuelve la letra
        return letra;
    }

        // Funcion para jugar al ahorcado con las funciones anteriores
    public static void jugar() throws IOException, InterruptedException, NullPointerException {
        // Genera una palabra al azar
        String palabra = generarPalabra();
        // Genera una cadena de guiones para mostrar la palabra
        String palabra2 = generarGuiones(palabra);
        // Crea un array para guardar las letras que ya se han probado
        String[] letrasUsadas = new String[26];
        // Crea un contador para las letras usadas
        int contador = 0;
        // Crea un contador para los intentos
        int intentos = 0;
        // Crea un bucle infinito
        while (true) {
            // Muestra los intentos
            System.out.println("Intentos: " + intentos);
            System.out.println();
            // Muestra el ahorcado
            mostrarAhorcado(intentos);
            
            // Muestra la palabra con guiones
            System.out.println();
            System.out.println("Palabra: " + palabra2);
            // Muestra las letras usadas
            System.out.print("Letras usadas: ");
            for (int i = 0; i < contador; i++) {
                System.out.print(letrasUsadas[i] + " ");
            }
            System.out.println();
            System.out.println();
            System.out.print("Introduce una letra: ");
            // Lee la letra con la funcion anterior
            String letra =  leerLetra();
            // Comprobar si la letra esta en la palabra
            if (palabra.contains(letra)) {
                // Si la letra esta en la palabra
                // Crea un array con las letras de la palabra
                char[] letras = palabra.toCharArray();
                // Crea un array con los guiones de la palabra
                char[] letras2 = palabra2.toCharArray();
                // Recorre el array de letras
                for (int i = 0; i < letras.length; i++) {
                    // Si la letra es igual a la letra introducida
                    if (letras[i] == letra.charAt(0)) {
                        // Cambia el guion por la letra
                        letras2[i] = letra.charAt(0);
                    }
                }
                // Crea una cadena con los guiones y las letras
                palabra2 = new String(letras2);
                // Si la palabra no tiene guiones
                if (!palabra2.contains("_")) {
                    // Muestra el ahorcado
                    mostrarAhorcado(intentos);
                    // Muestra las letras usadas
                    System.out.print("Letras usadas: ");
                    for (int i = 0; i < contador; i++) {
                        System.out.print(letrasUsadas[i] + " ");
                    }
                    // Muestra la palabra con guiones
                    System.out.println();
                    System.out.println("Palabra: " + palabra2);
                    // Muestra el mensaje de victoria
                    System.out.println("Has ganado!");
                    // Sale del bucle
                    break;
                }
            } else {
                // Si la letra no esta en la palabra
                // Aumenta el contador de intentos
                intentos++;
                // Si el numero de intentos es 6
                if (intentos == 6) {
                    // Muestra el ahorcado
                    mostrarAhorcado(intentos);
                    // Muestra las letras usadas
                    System.out.print("Letras usadas: ");
                    for (int i = 0; i < contador; i++) {
                        System.out.print(letrasUsadas[i] + " ");
                    }
                    // Muestra la palabra con guiones
                    System.out.println();
                    System.out.println("Palabra: " + palabra2);
                    // Muestra el mensaje de derrota
                    System.out.println("Has perdido!");
                    // Sale del bucle
                    break;
                }
            }
            // Guarda la letra en el array de letras usadas
            letrasUsadas[contador] = letra;
            // Aumenta el contador de letras usadas
            contador++;
            // Borrar el contenido de la pantalla
            System.out.print("\033[H\033[2J");
        }
    }


    // Si el numero de intentos es 4, muestra la cabeza, el tronco, el brazo izquierdo y el braz
    public static void main(String[] args) throws IOException, InterruptedException, NullPointerException {

        
        // Crea un bucle infinito
        while (true) {
            // Muestra el menu
            menu();
            // Lee la opcion
            int opcion = leerOpcion();
            // Si la opcion es 1
            if (opcion == 1) {
                // Borrar el contenido de la pantalla
                System.out.print("\033[H\033[2J");
                
                // Juega al ahorcado
                jugar();
            } else {
                // Sale del programa
                System.exit(0);
            }
        }
    }
}