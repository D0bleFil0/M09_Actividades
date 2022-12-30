package Actividad_3_ejercicio_1;

import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.Key;
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
import java.io.PrintStream;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class ServidorEncriptacion {

    // Crea la interfaz remota
    public interface Encriptar extends Remote {
        // Crea los métodos remotos
        public String getMenu() throws RemoteException;
        public String encriptar(String frase) throws RemoteException;
        public String desencriptar(String frase) throws RemoteException;
        public String generarLlaves() throws RemoteException;
        public String leerLlavePublica() throws RemoteException;
        public String leerLlavePrivada() throws RemoteException;
        public String guardarLlavePublica() throws RemoteException;
        public String guardarLlavePrivada() throws RemoteException;

     }

    // Crea la clase que implementa la interfaz remota
    public static class ImplementaPublica implements Encriptar {

        // Crea los métodos remotos
        public String getMenu() throws RemoteException {
            String menu = "1. Encriptar frase" + "\n" + "2. Desencriptar frase" + "\n" + "3. Generar llaves" + "\n"
                    + "4. Leer llave pública" + "\n" + "5. Leer llave privada" + "\n" + "6. Guardar llave pública" + "\n"
                    + "7. Guardar llave privada" + "\n" + "8. Salir" + "\n";
            return menu;
        }

        public String generarLlaves() throws RemoteException {
            String mensaje = "";
            try {
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                keyGen.initialize(1024);
                KeyPair key = keyGen.generateKeyPair();
                PublicKey publicKey = key.getPublic();
                PrivateKey privateKey = key.getPrivate();
                mensaje = "Llaves generadas correctamente";
            } catch (Exception e) {
                mensaje = "Error al generar las llaves";
            }
            return mensaje;
        }

        public String guardarLlavePublica() throws RemoteException {
            String mensaje = "";
            try {
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                keyGen.initialize(1024);
                KeyPair key = keyGen.generateKeyPair();
                PublicKey publicKey = key.getPublic();
                byte[] publicKeyBytes = publicKey.getEncoded();
                FileOutputStream fos = new FileOutputStream("public.key");
                fos.write(publicKeyBytes);
                fos.close();
                mensaje = "Llave pública guardada correctamente";
            } catch (Exception e) {
                mensaje = "Error al guardar la llave pública";
            }
            return mensaje;
        }

        public String guardarLlavePrivada() throws RemoteException {
            String mensaje = "";
            try {
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                keyGen.initialize(1024);
                KeyPair key = keyGen.generateKeyPair();
                PrivateKey privateKey = key.getPrivate();
                byte[] privateKeyBytes = privateKey.getEncoded();
                FileOutputStream fos = new FileOutputStream("private.key");
                fos.write(privateKeyBytes);
                fos.close();
                mensaje = "Llave privada guardada correctamente";
            } catch (Exception e) {
                mensaje = "Error al guardar la llave privada";
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
        

        