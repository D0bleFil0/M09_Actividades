package Actividad_1_ejercicio_3;

public class Pedidos {
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


    horno1.start(); // Inicio del hilo horno1
    horno2.start(); // Inicio del hilo horno2

    cliente1.start(); // Inicio del hilo cliente1
    cliente2.start(); // Inicio del hilo cliente2

    }
   
}
