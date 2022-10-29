// Principal.java 

package actividad_1_ejercicio_1;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Principal {

    public static void banner() {
        System.out.printf("Conversor Decimal a Binario\n");
        System.out.printf("[Escribe FIN para terminar]\n");
    }
    public static void main(String[] args) throws Exception {

        // Bienvenida
        banner();

        // Comunicacion entre proceso padre y proceso hijo
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String lineaEnvio = new String();
        lineaEnvio = br.readLine();

       loop: while (true) {
            // Creacion del proceso hijo
            ProcessBuilder pb = new ProcessBuilder("java","-jar","conversor.jar");
            Process p = pb.start();

            // Comunicacion entre proceso padre y proceso hijo
            OutputStream os = p.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.println(lineaEnvio);
            pw.flush();

            // Comunicacion entre proceso hijo y proceso padre
            InputStream is = p.getInputStream();
            BufferedReader br2 = new BufferedReader(new InputStreamReader(is));
            String lineaRecibida = br2.readLine();
            System.out.println(lineaRecibida);

            // Evaluacion de la condicion de salida
            lineaEnvio = br.readLine();

            if (lineaEnvio.equalsIgnoreCase("fin")) {
                break loop;
                
            }
        }
    }
}
