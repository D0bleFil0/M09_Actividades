package Actividad_1_ejercicio_3;

import java.util.LinkedList; 

// Clase principal Pizzeria
public class Pizzeria {
    // Uso de Linkedlist para usar cola de espera de pizzas
    private LinkedList<Integer> pizzas = new LinkedList<>();

    // Metodo para consumir pizzas con synchronized para que solo un hilo pueda acceder a este metodo a la vez
    public synchronized int consumir(int i){
        // Ciclo while para que el hilo cliente espere mientras no haya pizzas en la cola
        while(pizzas.size() <= 0){ 
            try {
                wait(); // Metodo wait para que el hilo espere
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return pizzas.poll(); // Metodo poll para eliminar la pizza que sale del horno de la cola
    }

    // Metodo para producir pizzas con synchronized para que solo un hilo pueda acceder a este metodo a la vez
    public synchronized void producir(int unidades){
        pizzas.offer(unidades);
        notifyAll(); // Notificacion de todos los hilos para que se ejecuten en orden de llegada a la cola
    }  

}
 