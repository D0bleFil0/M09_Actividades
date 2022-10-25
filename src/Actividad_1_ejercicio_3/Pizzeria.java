package Actividad_1_ejercicio_3;

import java.util.LinkedList;
import java.util.Random; 

// Clase principal Pizzeria
public class Pizzeria {
    // Uso de Linkedlist para usar cola de espera de pizzas
    private LinkedList<Integer> pizzas = new LinkedList<>();

    // Metodo para consumir pizzas con synchronized para que solo un hilo pueda acceder a este metodo a la vez
    public synchronized int consumir(int cantidad) throws InterruptedException {           
            // Ciclo while para que el hilo repartidor espere mientras no haya suficientes pizzas en la cola
            while (pizzas.size() < cantidad || pizzas.size() == 0) {           
                try {
                    // El hilo repartidor espera
                    wait();
     
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        
        return pizzas.poll(); // Metodo poll para eliminar la pizza que sale del horno de la cola
    }

    // Metodo para producir pizzas con synchronized para que solo un hilo pueda acceder a este metodo a la vez
    public synchronized void producir(int unidades){
        // Metodo offer para agregar pizzas a la cola
        pizzas.offer(unidades);
        //Imprime las pizzas que hay en la cola
        System.out.printf("++++++++++++++++++++++++++++++++\n");
        System.out.printf("Pizza(s) en cola: %d", pizzas.size());
        System.out.printf("\n++++++++++++++++++++++++++++++++\n");
        // Notificacion de todos los hilos para que se ejecuten en orden de llegada a la cola
        notifyAll();
        // Imprime el nombre del hilo y la cantidad de pizzas producidas
        System.out.println(Thread.currentThread().getName() + " produce " + unidades + " pizzas"); 
      
    }  
}

// Clase pedidos, contiene el metodo main
 class Pedidos {
    // Metodo main
    public static void main(String[] args) throws InterruptedException{

    // Objeto de tipo Pizzeria con el recurso compartido
    Pizzeria pizzas = new Pizzeria();

    // Objetos de tipo Repartidor que recibe como parametro el recurso compartido
    Repartidor repartirdor1 = new Repartidor(pizzas, "Repartidor 1");
    Repartidor repartirdor2 = new Repartidor(pizzas, "Repartidor 2");
    Repartidor repartirdor3 = new Repartidor(pizzas, "Repartidor 3");
    Repartidor repartirdor4 = new Repartidor(pizzas, "Repartidor 4");
    Repartidor repartirdor5 = new Repartidor(pizzas, "Repartidor 5");


    // Objetos de tipo Horno que recibe como parametro el recurso compartido
    Horno horno1 = new Horno(pizzas, "Horno 1");
    Horno horno2 = new Horno(pizzas, "Horno 2");

    // Inicio de los hilos de los repartidores
    horno1.start(); 
    horno2.start(); 
    repartirdor1.start();
    repartirdor2.start();
    repartirdor3.start();
    repartirdor4.start();
    repartirdor5.start();
    }
}

// Clase Horno que hereda de la clase Thread
class Horno extends Thread {

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
                // Metodo para producir una cantidad aleatoria de pizzas entre 1 y 5
                pizzas.producir(new Random().nextInt(4)+1);
                // Tiempo de espera aleatorio en bloque try catch ya que el metodo sleep puede lanzar una excepcion de tipo checked 
                Thread.sleep(new Random().nextInt(10000)+2000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Clase Repartidor que hereda de la clase Thread
class Repartidor extends Thread {

    // Metodo para consumir pizzas, recurso compartido por Pizzeria
    private Pizzeria pizzas;

    // Constructor de la clase Repartidor que recibe como parametro el recurso
    // compartido
    public Repartidor(Pizzeria pizzas, String nombre) {
        super(nombre);
        this.pizzas = pizzas;
    }

    // Metodo run sobreescrito para ejecutar el hilo en ciclo infinito
    @Override
    public void run() {
        while (true) {
            
            try {
                // Variable que recibe el valor de la pizza consumida
                int pizza = pizzas.consumir(new Random().nextInt(6)+1); 
                // Imprime el nombre del hilo y la pizza consumida
                System.out.println(Thread.currentThread().getName() + " se lleva " + pizza + " pizzas");
                // Tiempo de espera aleatorio en bloque try catch ya que el metodo sleep puede lanzar una excepcion de tipo checked
                Thread.sleep(new Random().nextInt(2000)+500);
                   
                } catch (InterruptedException e) {

                e.printStackTrace();
            } // Tiempo de espera aleatorio en bloque try catch ya que el metodo sleep puede lanzar una excepcion de tipo checked

        }
    }
}
