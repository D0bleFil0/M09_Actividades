package Actividad_1_ejercicio_3;

import java.util.Random;

public class Horno extends Thread {

    // Metodo para producir pizzas, recurso compartido por Pizzeria
    private Pizzeria pizzas;
    
    // Constructor de la clase Horno que recibe como parametro el recurso compartido
    public Horno(Pizzeria pizzas, String nombre) {
        super(nombre);
        this.pizzas = pizzas;
    }

     // Metodo run sobreescrito para ejecutar el hilo en ciclo infinito
    @Override
    public void run() {
        while(true){
            try {
                pizzas.producir(new Random().nextInt(5)); // Metodo para producir pizzas con un tiempo de espera aleatorio
                Thread.sleep(new Random().nextInt(5000)); // Tiempo de espera aleatorio en bloque try catch ya que el metodo sleep puede lanzar una excepcion de tipo checked
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
