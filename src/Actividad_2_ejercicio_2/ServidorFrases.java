package Actividad_2_ejercicio_2;

import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.Remote;
import java.rmi.RemoteException;


public class ServidorFrases {

     // Metodo principal
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

    // Crea la interfaz remota
    public interface Frases extends Remote {
        // Crea los métodos remotos
        public String getMenu() throws RemoteException;
        public String longitudFrase(String frase) throws RemoteException;
        public String numeroPalabras(String frase) throws RemoteException;
        public String numeroVocales(String frase) throws RemoteException;
        public String fraseInvertida(String frase) throws RemoteException;
        public String fraseMayusculas(String frase) throws RemoteException;
        public String fraseMinusculas(String frase) throws RemoteException;
        public String elegirOpcion(String opcion, String frase) throws RemoteException;
    }

    // Crea la clase que implementa la interfaz remota

    public static class FrasesImpl implements Frases {
        
        // Crea método remoto para menu, al que se le pasa la frase y devuelve el resultado
        public String getMenu() throws RemoteException {
            
            String[] menu ={"(L) Longitud de la frase ",
                "(P) Número de palabras de la frase, ","(V) Número de vocales de la frase, ",
                "(I) Frase invertida, ","(M) Frase en mayúsculas, ","(m) Frase en minúsculas, ",
                "(F) Salir del programa "};
            String menuString = "";
            for (int i = 0; i < menu.length; i++) {
                menuString += menu[i] + "";
            }
            return menuString;
        }


        // Metodo remoto para elegir la opcion y devuelve el resultado
        public String elegirOpcion(String opcion, String frase) throws RemoteException {
            String resultado = "";
            switch (opcion) {
                case "L":
                    resultado = "Longitud de la frase: " + longitudFrase(frase);
                    break;
                case "P":
                    resultado = "Número de palabras: " + numeroPalabras(frase);
                    break;
                case "V":
                    resultado = "Número de vocales: " + numeroVocales(frase);
                    break;
                case "I":
                    resultado = "Frase invertida: " + fraseInvertida(frase);
                    break;
                case "M":
                    resultado = "Frase en mayúsculas: " + fraseMayusculas(frase);
                    break;
                case "m":
                    resultado = "Frase en minúsculas: " + fraseMinusculas(frase);
                    break;
                case "F":
                    System.exit(0);
                    break;
                default:
                    resultado = "Opción incorrecta";
                    break;
            }
            return resultado;
        }


        // Metodo para longitud de la frase
        public String longitudFrase(String frase) throws RemoteException {
            String longitud = Integer.toString(frase.length());
            return longitud;
        }

        // Metodo para numero de palabras de la frase
        public String numeroPalabras(String frase) throws RemoteException {
            int numeroPalabras = 0;
            for (int i = 0; i < frase.length(); i++) {
                if (frase.charAt(i) == ' ') {
                    numeroPalabras++;
                }
            }
            String numeroPalabras2 = Integer.toString(numeroPalabras) +1;
            return numeroPalabras2;
        }

        // Metodo para numero de vocales de la frase
        public String numeroVocales(String frase) throws RemoteException {
            int numeroVocales = 0;
            for (int i = 0; i < frase.length(); i++) {
                if (frase.charAt(i) == 'a' || frase.charAt(i) == 'e' || frase.charAt(i) == 'i' || frase.charAt(i) == 'o' || frase.charAt(i) == 'u') {
                    numeroVocales++;
                }
            }
            String numeroVocales2 = Integer.toString(numeroVocales);
            // Devuelve el numero de vocales en string
            return numeroVocales2;
        }

        // Metodo para frase invertida
        public String fraseInvertida(String frase) throws RemoteException {
            String fraseInvertida = "";
            for (int i = frase.length() - 1; i >= 0; i--) {
                fraseInvertida += frase.charAt(i);
            }
            return fraseInvertida;
        }

        // Metodo para frase en mayusculas
        public String fraseMayusculas(String frase) throws RemoteException {
            return frase.toUpperCase();
        }

        // Metodo para frase en minusculas
        public String fraseMinusculas(String frase) throws RemoteException {
            return frase.toLowerCase();
        }

    }
}
