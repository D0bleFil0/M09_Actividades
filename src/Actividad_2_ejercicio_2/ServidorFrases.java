package Actividad_2_ejercicio_2;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Remote;
import java.rmi.RemoteException;




public class ServidorFrases {

    // Crea la interfaz remota
    public interface Frases extends Remote {
        public String getFrase() throws RemoteException;
    }

    // Implementa la interfaz remota

    public static class FrasesImpl implements Frases {
        private String[] frases = {"La vida es bella", "El que la sigue la consigue", "El que madruga, dios le ayuda"};
        private int contador = 0;
        public String getFrase() throws RemoteException {
            if (contador == frases.length) {
                contador = 0;
            }
            return frases[contador++];
        }
    }
    // Frases que se van a devolver
    public static void main(String[] args) throws RemoteException {
        
        try {
            // Crea el registro en el puerto 1099
            Registry registro = LocateRegistry.createRegistry(1099);
            // Instancia el objeto remoto
            FrasesImpl obj = new FrasesImpl();
            // Crea el stub
            Frases stub = (Frases) UnicastRemoteObject.exportObject(obj, 0);
            // Lo registra en el registro
            registro.rebind("Frases", stub);
            System.out.println("Servidor listo");
        } catch (Exception e) {
            System.err.println("Excepción del servidor: " + e.toString());
            e.printStackTrace();
        }
    }

}
