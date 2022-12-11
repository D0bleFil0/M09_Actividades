package Actividad_2_ejercicio_1;

// escribir array en fichero de texto
import java.io.*;
import java.util.Scanner;

public class prueba {

    public static void main(String[] args) {

        int[] numeros = new int[10];
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < numeros.length; i++) {
            System.out.println("Introduce un numero");
            numeros[i] = sc.nextInt();
        }

        try {
            File fichero = new File("/home/alberto/Documentos/numeros.txt");
            FileWriter fw = new FileWriter(fichero);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < numeros.length; i++) {
                bw.write(numeros[i] + " ");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
