package Actividad_2_ejercicio_2;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;



public interface ClienteFrases {

    // Metodo principal
    public static void main(String[] args) throws Exception{
        // Crea un try-catch para capturar las excepciones
        try {
            // Se obtiene el registro en el puerto 1099 dentro de un bucle
            Registry registry = LocateRegistry.getRegistry(1099);

            // Se busca el objeto remoto, se hace un cast y se guarda en una variable
            ServidorFrases.Frases obj = (ServidorFrases.Frases) registry.lookup("Frases");
            // Se llama al método remoto
            String frase = obj.getFrase();
            // Se muestra el resultado
            System.out.println("Frase: " + frase);
        } catch (Exception e) {
            // Se captura la excepción
            System.err.println("Excepción del cliente: " + e.toString());
            // Se imprime la pila de llamadas
            e.printStackTrace();
        }
    }
}
            