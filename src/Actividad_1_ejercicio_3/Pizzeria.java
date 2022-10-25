package Actividad_1_ejercicio_3;

import java.util.LinkedList;
import java.util.Random; 

// Clase principal Pizzeria
public class Pizzeria {
    // Uso de Linkedlist para usar cola de espera de pizzas
    private LinkedList<Integer> pizzas = new LinkedList<>();

    // Metodo para consumir pizzas con synchronized para que solo un hilo pueda acceder a este metodo a la vez
    public synchronized int consumir(int cantidad) throws InterruptedException {
        // Imprime el nombre del hilo y el mensaje de que no hay suficientes pizzas
        System.out.println("Cliente ha pedido " + (cantidad - pizzas.size()) + " pizzas");
        // Ciclo while para que el hilo cliente espere mientras no haya suficientes pizzas en la cola
        while (pizzas.size() < cantidad) {           
            try {
                // El hilo cliente espera
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
        System.out.println("Hay " + pizzas.size() + " pizzas en la cola");
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

    // Objetos de tipo Cliente que recibe como parametro el recurso compartido
    Cliente cliente1 = new Cliente(pizzas, "Cliente 1");
    Cliente cliente2 = new Cliente(pizzas, "Cliente 2");
    Cliente cliente3 = new Cliente(pizzas, "Cliente 3");
    Cliente cliente4 = new Cliente(pizzas, "Cliente 4");
    Cliente cliente5 = new Cliente(pizzas, "Cliente 5");

    // Objetos de tipo Horno que recibe como parametro el recurso compartido
    Horno horno1 = new Horno(pizzas, "Horno 1");
    Horno horno2 = new Horno(pizzas, "Horno 2");

    // Inicio de los hilos
    horno1.start(); 
    horno2.start(); 
    cliente1.start(); 
    cliente2.start(); 
    cliente3.start(); 
    cliente4.start(); 
    cliente5.start(); 
    
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
                pizzas.producir(new Random().nextInt(2)+1);
                // Tiempo de espera aleatorio en bloque try catch ya que el metodo sleep puede lanzar una excepcion de tipo checked 
                Thread.sleep(new Random().nextInt(10000)+2000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Clase Cliente que hereda de la clase Thread
class Cliente extends Thread {

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
                int pizza = pizzas.consumir(new Random().nextInt(5)+1); // Variable que recibe el valor de la pizza consumida
                // Imprime el nombre del hilo y la pizza consumida
                System.out.println("Cliente se lleva " + pizza + " pizzas");
                // Si no hay pizzas en la cola, el hilo espera
                if (pizza == 0) {
                    // Tiempo de espera aleatorio en bloque try catch ya que el metodo sleep puede lanzar una excepcion de tipo checked
                    Thread.sleep(new Random().nextInt(2000)+500);
                    System.out.println("Cliente está esperando...");
                }
            } catch (InterruptedException e) {

                e.printStackTrace();
            } // Tiempo de espera aleatorio en bloque try catch ya que el metodo sleep puede lanzar una excepcion de tipo checked

        }
    }
}
