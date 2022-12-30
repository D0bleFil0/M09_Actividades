package Actividad_3_ejercicio_1;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClienteEncriptacion {
    public static void main(String[] args) {
        try {
            // Obtiene el stub del registro
            Registry registry = LocateRegistry.getRegistry();
            ServidorRMIInterface stub = (ServidorRMIInterface) registry.lookup("ServidorRMI");
            // Llama al método del objeto remoto
            String mensaje = stub.saludar();
            System.out.println(mensaje);
        } catch (Exception e) {
            System.err.println("Error del cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}