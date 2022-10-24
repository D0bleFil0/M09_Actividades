package Actividad_1_ejercicio_2;

import java.util.Scanner;

public class Fibonacci4Hilos extends Thread {
    
    //Calculo de la sucesion de Fibonacci
    public static int fibonacci(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        
        //Creacion de array para almacenar valores a calcular
        Scanner sc = new Scanner(System.in);
        int[] numeros = new int[4];
        for (int i = 0; i < 4; i++) {
            System.out.println("Introduce el numero " + (i + 1) + ": ");
            numeros[i] = sc.nextInt();
        }

        //Creacion de los hilos con lambdas usando valores del array y llamada a la funcion fibonacci
        Thread hilo1 = new Thread(() -> System.out.println("El numero " + numeros[0] + " de la sucesion de Fibonacci es: " + fibonacci(numeros[0])));
        Thread hilo2 = new Thread(() -> System.out.println("El numero " + numeros[1] + " de la sucesion de Fibonacci es: " + fibonacci(numeros[1])));
        Thread hilo3 = new Thread(() -> System.out.println("El numero " + numeros[2] + " de la sucesion de Fibonacci es: " + fibonacci(numeros[2])));
        Thread hilo4 = new Thread(() -> System.out.println("El numero " + numeros[3] + " de la sucesion de Fibonacci es: " + fibonacci(numeros[3])));
        
        //Ejecucion de los hilos y join para que se ejecuten en orden
        hilo1.start();
        hilo1.join();
        hilo2.start();
        hilo2.join();
        hilo3.start();
        hilo3.join();
        hilo4.start();
        hilo4.join();

        //Cierre del scanner
        sc.close();
    }
} 
        
