// Conversor.java

package actividad_1_ejercicio_1;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Conversor {

    public static void main(String[] args) throws Exception {

        // Comunicacion entre proceso padre y proceso hijo
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String lineaEnvio = new String();
        lineaEnvio = br.readLine();

        Integer valor = Integer.parseInt(lineaEnvio);
        String resultado = decimalABinario(valor);
        System.out.println(resultado);
    }

    // Funcion para convertir un numero decimal a binario
    public static String decimalABinario(int numero) {
        String binario = "";
        while (numero > 0) {
            if (numero % 2 == 0) {
                binario = "0" + binario;
            } else {
                binario = "1" + binario;
            }
            numero = numero / 2;
        }
        // Bloque para completar con ceros a la izquierda
        int longitud = binario.length();
        for (int i = 0; i < 4 - longitud; i++) {
            binario = "0" + binario;
        }
        return binario;
    }
}
