package Actividad_3_ejercicio_2;

import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import java.rmi.registry.LocateRegistry;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class ServidorCifrado {

    // Metodo principal
    public static void main(String[] args) throws RemoteException {
        // Try catch para controlar las excepciones del servidor
        try {
            // Crea el registro en el puerto 1099
            Registry registro = LocateRegistry.createRegistry(1099);
            // Instancia el objeto remoto
            ImplementaPublica obj = new ImplementaPublica();
            // Crea el stub
            Encriptar stub = (Encriptar) UnicastRemoteObject.exportObject(obj, 0);
            // Lo registra en el registro
            registro.rebind("Encriptar", stub);
            // Borra la pantalla
            System.out.print("\033[H\033[2J");
            // Notifica que el servidor está preparado
            System.out.println(" ***Servidor preparado***");
            System.out.println("");
            System.out.println("");
            System.out.println(" Pulse control + c para salir del programa");
            // Muestra lo que va haciendo el cliente
            System.out.println(" Esperando peticiones del cliente...");
            System.out.println("");

            // Mostrar registro de lo que va haciendo el cliente
            System.out.println(" Registro de peticiones realizadas por cliente:");
            System.out.println("");

            // Si se produce una excepción, la muestra
        } catch (Exception e) {
            System.err.println(" Excepción del servidor: " + e.toString());
            e.printStackTrace();
        }
    }


    // Crea la interfaz remota que extiende de la interfaz Remote
    public interface Encriptar extends Remote {
        // Crea los métodos remotos
        public String getMenu() throws RemoteException;
        public String encriptar(String frase) throws RemoteException;
        public String desencriptar(String frase) throws RemoteException;
        public String generarLlaves() throws RemoteException;
        public String leerLlavePublica() throws RemoteException;
        public String leerLlavePrivada() throws RemoteException;
     }

    // Crea la clase que implementa la interfaz remota
    public static class ImplementaPublica implements Encriptar {

        // Meotodo remoto para el menu, devuelve un String con las opciones
        public String getMenu() throws RemoteException {
            String menu = "Escribe un mensaje para encriptar. Sin escribes FIN se cerrará el programa.";
            return menu;
        }


        // Meotodo remoto para generar las llaves y guardarlas en ficheros
        public String generarLlaves() throws RemoteException {
            // Crea una variable String para almacenar el mensaje
            String mensaje = "";
            try {
                // Crea el generador de llaves
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                // Inicializa el generador de llaves
                keyGen.initialize(2048);
                // Crea el par de llaves
                KeyPair pair = keyGen.generateKeyPair();
                // Crea las llaves
                PrivateKey priv = pair.getPrivate();
                PublicKey pub = pair.getPublic();
                // Guarda las llaves en ficheros
                File f = new File("private.key");
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(priv.getEncoded());
                fos.close();
                f = new File("public.key");
                fos = new FileOutputStream(f);
                fos.write(pub.getEncoded());
                fos.close();
                // Mensaje de que se han generado las llaves
                mensaje = "Llaves generadas correctamente";
                // Si se produce una excepción, la muestra
            } catch (Exception e) {
                mensaje = "Error al generar las llaves";
            }
            return mensaje;
        }

        public String leerLlavePublica() throws RemoteException {
            String mensaje = "";
            try {
                File filePublicKey = new File("public.key");
                FileInputStream fis = new FileInputStream("public.key");
                byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
                fis.read(encodedPublicKey);
                fis.close();
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                KeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
                PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
                mensaje = "Llave pública leída correctamente";
            } catch (Exception e) {
                mensaje = "Error al leer la llave pública";
            }
            return mensaje;
        }

        public String leerLlavePrivada() throws RemoteException {
            String mensaje = "";
            try {
                File filePrivateKey = new File("private.key");
                FileInputStream fis = new FileInputStream("private.key");
                byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
                fis.read(encodedPrivateKey);
                fis.close();
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                KeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
                PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
                mensaje = "Llave privada leída correctamente";
            } catch (Exception e) {
                mensaje = "Error al leer la llave privada";
            }
            return mensaje;

        }

        public String encriptar(String frase) throws RemoteException {
            String mensaje = "";
            try {
                File filePublicKey = new File("public.key");
                FileInputStream fis = new FileInputStream("public.key");
                byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
                fis.read(encodedPublicKey);
                fis.close();
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                KeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
                PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                byte[] cipherText = cipher.doFinal(frase.getBytes());
                mensaje = new String(cipherText);
            } catch (Exception e) {
                mensaje = "Error al encriptar la frase";
            }
            return mensaje;
        }

        public String desencriptar(String frase) throws RemoteException {
            String mensaje = "";
            try {
                File filePrivateKey = new File("private.key");
                FileInputStream fis = new FileInputStream("private.key");
                byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
                fis.read(encodedPrivateKey);
                fis.close();
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                KeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
                PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                byte[] cipherText = cipher.doFinal(frase.getBytes());
                mensaje = new String(cipherText);
            } catch (Exception e) {
                mensaje = "Error al desencriptar la frase";
            }
            return mensaje;
        }

    }
}
