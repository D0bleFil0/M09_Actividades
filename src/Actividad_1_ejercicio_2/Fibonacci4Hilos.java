package actividad_1_ejercicio_2;

import java.util.Scanner;

// Clase heredada de Thread
class Fibonacci4Hilos extends Thread {

    // Metodo run para ejecutar el hilo
    public void run() {
        System.out.println("El numero en la posicion " + getName() + " de la serie de Fibonacci es: "
                + fibonacci(Integer.parseInt(getName())));
    }

    // Metodo de Fibonacci4Hilos para calcular el numero de Fibonacci
    public static int fibonacci(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

     // Clase principal que contiene el metodo main
    public static class Hilos {
        public static void main(String[] args) throws InterruptedException {

            // Try with resources para cerrar el scanner automaticamente y los hilos
            try (Scanner sc = new Scanner(System.in)) { 
                int[] numeros = new int[4];
                for (int i = 0; i < 4; i++) {
                    System.out.println("Introduce el numero " + (i + 1) + ": ");
                    numeros[i] = sc.nextInt();
                }
                
                // Creacion de hilos y ejecucion con el metodo run
                Fibonacci4Hilos hilo1 = new Fibonacci4Hilos();
                Fibonacci4Hilos.fibonacci(numeros[0]);
                hilo1.setName(String.valueOf(numeros[0]));
                hilo1.start();
                hilo1.join();

                Fibonacci4Hilos hilo2 = new Fibonacci4Hilos();
                Fibonacci4Hilos.fibonacci(numeros[1]);
                hilo2.setName(String.valueOf(numeros[1]));
                hilo2.start();
                hilo2.join();

                Fibonacci4Hilos hilo3 = new Fibonacci4Hilos();
                Fibonacci4Hilos.fibonacci(numeros[2]);
                hilo3.setName(String.valueOf(numeros[2]));
                hilo3.start();
                hilo3.join();

                Fibonacci4Hilos hilo4 = new Fibonacci4Hilos();
                Fibonacci4Hilos.fibonacci(numeros[3]);
                hilo4.setName(String.valueOf(numeros[3]));
                hilo4.start();
                hilo4.join();
            }
        }
    }
}
