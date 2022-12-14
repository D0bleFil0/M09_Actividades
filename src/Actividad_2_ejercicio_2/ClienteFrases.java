package Actividad_2_ejercicio_2;

import java.rmi.registry.LocateRegistry;

public interface ClienteFrases {

    private ClienteFrases() {}

    public static void main(String[] args) {
        // Crea un try-catch para capturar las excepciones
        try {
            // Se obtiene el registro en el puerto 1099
            Registry registry = LocateRegistry.getRegistry(1099);
            // Se busca el objeto remoto
            ServidorFrases.Frases stub = (ServidorFrases.Frases) registry.lookup("Frases");
            // Se llama al método remoto
            String frase = stub.getFrase();
            // Se muestra el resultado
            System.out.println("Frase: " + frase);
        } catch (Exception e) {
            