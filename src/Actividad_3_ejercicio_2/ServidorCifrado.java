package Actividad_3_ejercicio_2;

// Importa las librerías necesarias para ficheros
import java.io.FileInputStream;
import java.io.FileOutputStream;
// Importa las librerías necesarias para RMI
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
// Importa las librerías necesarias para encriptación
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
// Importa las librerías necesarias para Base64
import java.util.Base64;

public class ServidorCifrado{

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
            // Lo guarda en el registro
            registro.rebind("Encriptar", stub);
            // Borra la pantalla
            System.out.print("\033[H\033[2J");

            // Notifica que el servidor está preparado en color azul
            System.out.println(" ***Servidor Encriptación Asimétrica RSA***\n"
                    + " \n Servidor preparado y esperando peticiones...\n"
                    + " \n Para salir del programa, pulse Ctrl + C\n"
                    + "\033[31m"
                    + " \n -----------------------------------------"
                    + " \n |        Registro de solicitudes        |"
                    + " \n -----------------------------------------\n");

            // Si se produce una excepción, la muestra
        } catch (Exception e) {
            System.err.println(" Excepción del servidor: " + e.toString());
            e.printStackTrace();
        }
    }

    // Crea la interfaz remota que extiende de la interfaz Remote
    public interface Encriptar extends Remote {
        // Crea los métodos remotos
        public String mensajeMenu() throws RemoteException;
        public String encriptar(String frase) throws RemoteException;
        public String desencriptar(String frase) throws RemoteException;
        public String generarLlaves() throws RemoteException;
        public String[] mostrarLlaves() throws RemoteException;
        
        

    }

    // Crea la clase que implementa la interfaz remota
    public static class ImplementaPublica implements Encriptar {

        // Metodo remoto para el menu, devuelve un String con las opciones
        public String mensajeMenu() throws RemoteException {
            String menu = " ***Cliente Encriptación Asimétrica RSA***\n"
                    + " \n Elija una opción:\n"
                    + " \n 1. Encriptar"
                    + " \n 2. Desencriptar"
                    + " \n 3. Generar llaves nuevas"
                    + " \n 4. Mostrar llaves"
                    + " \n 5. Salir";
            return menu;
        }

        // Metodo remoto para generar las llaves y guardarlas en ficheros
        public String generarLlaves() throws RemoteException {
            try {
                // Generamos el par de claves.
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                KeyPair keyPair = keyPairGenerator.generateKeyPair();
                PublicKey publicKey = keyPair.getPublic();
                PrivateKey privateKey = keyPair.getPrivate();

                // Guardamos la clave publica en un fichero y la recuperamos
                guardarLlave(publicKey, "clavepublica.key");
                publicKey = leerLlavePublica("clavepublica.key");

                // Guardamos la clave privada en un fichero y la recuperamos
                guardarLlave(privateKey, "claveprivada.key");
                privateKey = leerLlavePrivada("claveprivada.key");
                return null;

            } catch (Exception e) {
                System.out.println("Error al generar las llaves");
                return null;
            }
        }

        // Metodo remoto para guardar las llaves en ficheros
        public void guardarLlave(PublicKey publicKey, String string) {
            try {
                byte[] publicKeyBytes = publicKey.getEncoded();
                FileOutputStream fos = new FileOutputStream(string);
                fos.write(publicKeyBytes);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Metodo remoto para leer clave publica
        public PublicKey leerLlavePublica(String string) {
            try {
                // Leemos la clave publica del fichero
                FileInputStream fis = new FileInputStream(string);
                // Creamos un array de bytes del tamaño del fichero
                byte[] publicKeyBytes = new byte[fis.available()];
                // Leemos el contenido del fichero y lo guardamos en el array de bytes
                fis.read(publicKeyBytes);
                fis.close();
                // Pasamos el array de bytes a un objeto X509EncodedKeySpec para poder generar
                // la clave
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
                // Creamos un objeto KeyFactory y le indicamos el algoritmo RSA
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                // Generamos la clave publica
                PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
                return publicKey;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        // Metodo remoto para leer clave privada
        public PrivateKey leerLlavePrivada(String string) {
            try {
                // Leemos la clave privada del fichero
                FileInputStream fis = new FileInputStream(string);
                // Creamos un array de bytes del tamaño del fichero
                byte[] privateKeyBytes = new byte[fis.available()];
                // Leemos el contenido del fichero y lo guardamos en el array de bytes
                fis.read(privateKeyBytes);
                fis.close();
                // Pasamos el array de bytes a un objeto PKCS8EncodedKeySpec para poder generar
                // la clave privada
                PKCS8EncodedKeySpec encriptado = new PKCS8EncodedKeySpec(privateKeyBytes);
                // Creamos un objeto KeyFactory y le indicamos el algoritmo RSA
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                // Generamos la clave privada
                PrivateKey privateKey = keyFactory.generatePrivate(encriptado);
                return privateKey;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        // Metodo remoto para guardar la clave privada en un fichero
        public void guardarLlave(PrivateKey privateKey, String string) {
            try {
                // Guardamos la clave privada en un array de bytes
                byte[] privateKeyBytes = privateKey.getEncoded();
                // Pasamos el array de bytes a un fichero
                FileOutputStream fos = new FileOutputStream(string);
                // Guardamos el array de bytes en el fichero
                fos.write(privateKeyBytes);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Metodo remoto para encriptar el mensaje guardado en el metodo anterior
        public String encriptar(String frase) throws RemoteException {
            try {
                // Recuperamos la clave publica
                PublicKey publicKey = leerLlavePublica("clavepublica.key");
                // Encriptamos el mensaje
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                // Pasamos el mensaje a bytes y lo guardamos en un array
                byte[] mensajeEncriptado = cipher.doFinal(frase.getBytes());
                // Pasamos el mensaje encriptado de bytes a base64 y lo guardamos en un String
                String mensajeEncriptadoString = Base64.getEncoder().encodeToString(mensajeEncriptado);

                // Guarda el mensaje encriptado en un array de 40 caracteres por linea
                String[] cifrado = mensajeEncriptadoString.split("(?<=\\G.{40})");
                System.out.println("\033[31mMensaje encriptado: \033[0m \n");
                // Recorre el array y muestra el mensaje encriptado
                for (int i = 0; i < cifrado.length; i++) {
                    System.out.println("\033[32m" + cifrado[i] + "\033[0m");
                }
                return mensajeEncriptadoString;

            } catch (Exception e) {
                System.out.println("Error al encriptar el mensaje");
                return null;
            }
        }

        // Metodo remoto para desencriptar el mensaje guardado en base64
        public String desencriptar(String frase) throws RemoteException {
            try {
                // Recuperamos la clave privada
                PrivateKey privateKey = leerLlavePrivada("claveprivada.key");
                // Pasamos el mensaje encriptado de base64 a bytes y lo guardamos en un array
                byte[] mensajeEncriptado = Base64.getDecoder().decode(frase);
                // Desencriptamos el mensaje
                Cipher cipher = Cipher.getInstance("RSA");
                // Inicializamos el cifrado con la clave privada
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                // Guardamos el mensaje desencriptado en un array de bytes
                byte[] mensajeDesencriptado = cipher.doFinal(mensajeEncriptado);
                // Pasamos el mensaje desencriptado de bytes a String
                String mensajeDesencriptadoString = new String(mensajeDesencriptado);
                // Mostramos el mensaje desencriptado con color rojo
                System.out.println("");
                System.out.println("\033[31mMensaje desencriptado: \033[0m \n" + "\033[32m\n"
                        + mensajeDesencriptadoString + "\033[0m");
                System.out.println("\033[35m\n+++++++++++++++++++++++++++++++++++++++++ \n");
                return mensajeDesencriptadoString;

            } catch (Exception e) {
                System.out.println("Error al desencriptar el mensaje");
                return null;
            }
        }

        // Metodo remoto para mostrar las llaves en cliente
        public String[] mostrarLlaves() throws RemoteException {
            try {
                // Recuperamos la clave publica
                PublicKey publicKey = leerLlavePublica("clavepublica.key");
                // Recuperamos la clave privada
                PrivateKey privateKey = leerLlavePrivada("claveprivada.key");
                // Mostramos las claves con color rojo
                System.out.println("\033[31mClave publica: \033[0m \n" + "\033[32m" + publicKey + "\033[0m");
                String prublica = "\033[31mClave publica: \033[0m \n" + "\033[32m" + publicKey + "\033[0m";
                System.out.println("\033[31mClave privada: \033[0m \n" + "\033[32m" + privateKey + "\033[0m");
                String privada = "\033[31mClave privada: \033[0m \n" + "\033[32m" + privateKey + "\033[0m";

                String[] llaves = { prublica, privada };
                return llaves;
            } catch (Exception e) {
                System.out.println("Error al mostrar las llaves");
                return null;
            }
        }
    }
}