package Actividad_1_ejercicio_3;

import java.util.Random;

// Clase principal Pizzeria que hereda de Thread
public class Cliente extends Thread {

    // Metodo para consumir pizzas, recurso compartido por Pizzeria
    private Pizzeria pizzas;

    // Constructor de la clase Cliente que recibe como parametro el recurso
    // compartido
    public Cliente(Pizzeria pizzas, String nombre) {
        super(nombre);
        this.pizzas = pizzas;
    }

    // Metodo run sobreescrito para ejecutar el hilo en ciclo infinito
    @Override
    public void run() {
        while (true) {
            try {
                int pizza = pizzas.consumir(new Random().nextInt(20)); // Variable que recibe el valor de la pizza consumida
                // Imprime el nombre del hilo y la pizza consumida
                System.out.println("El cliente " + getName() + " ha consumido la pizza " + pizza); 
                // Tiempo de espera aleatorio en bloque try catch ya que el metodo sleep puede lanzar una excepcion de tipo checked
                Thread.sleep(new Random().nextInt(2000)); 
            } catch (InterruptedException e) {

                e.printStackTrace();
            } // Tiempo de espera aleatorio en bloque try catch ya que el metodo sleep puede
              // lanzar una excepcion de tipo checked

        }
    }
}
