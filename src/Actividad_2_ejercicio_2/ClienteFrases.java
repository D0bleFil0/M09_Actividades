package Actividad_2_ejercicio_2;

import java.rmi.registry.LocateRegistry;

public interface ClienteFrases {

    private ClienteFrases() {
    }

    public static void main(String[] args) {
        // Crea un try-catch para capturar las excepciones
        try {
            // Se obtiene el registro en el puerto 1099
            Registry registry = LocateRegistry.getRegistry(1099);
            // Se obtiene el stub del objeto remoto que se encuentra en el registro
            Frases stub = (Frases) registry.lookup("Frases");
            // Se llama al método remoto
            String response = stub.getFrase();
            //
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
}
