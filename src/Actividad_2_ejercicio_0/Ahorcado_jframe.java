package Actividad_2_ejercicio_0;

// juego del ahorcado, pero en vez de imprimir el ahorcado en la consola, se envia a un archivo de texto y se muestra en un jframe
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Ahorcado_jframe extends JFrame implements ActionListener {

    private String[] ahorcado = new String[6];
    private String[] letras = new String[10];
    private String palabra = "palabra";
    private int intentos = 7;
    private JLabel[] lblAhorcado = new JLabel[6];
    private JLabel[] lblLetras = new JLabel[10];
    private JButton btnLetra = new JButton("Introducir letra");
    private JTextField txtLetra = new JTextField(1);
    private JPanel pnlAhorcado = new JPanel();
    private JPanel pnlLetras = new JPanel();
    private JPanel pnlBoton = new JPanel();
    private JPanel pnlPrincipal = new JPanel();

    public Ahorcado_jframe() {
        // inicializar la ventana
        super("Ahorcado");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // inicializar el panel principal
        pnlPrincipal.setLayout(new GridLayout(3, 1));

        // inicializar el panel del ahorcado
        pnlAhorcado.setLayout(new GridLayout(6, 1));
        for (int i = 0; i < lblAhorcado.length; i++) {
            lblAhorcado[i] = new JLabel(ahorcado[i]);
            pnlAhorcado.add(lblAhorcado[i]);
        }

        // inicializar el panel de las letras
        pnlLetras.setLayout(new GridLayout(1, 10));
        for (int i = 0; i < lblLetras.length; i++) {
            lblLetras[i] = new JLabel(letras[i]);
            pnlLetras.add(lblLetras[i]);
        }

        // inicializar el panel del boton
        pnlBoton.setLayout(new FlowLayout());
        pnlBoton.add(txtLetra);
        pnlBoton.add(btnLetra);

        // añadir los paneles al panel principal
        pnlPrincipal.add(pnlAhorcado);
        pnlPrincipal.add(pnlLetras);
        pnlPrincipal.add(pnlBoton);

        // añadir el panel principal a la ventana
        add(pnlPrincipal);

        // inicializar el array de letras con guiones bajos
        for (int i = 0; i < letras.length; i++) {
            letras[i] = "_";
        }

        // inicializar el array del ahorcado
        ahorcado[0] = "  +---+";
        ahorcado[1] = "  |   |";
        ahorcado[2] = "      |";
        ahorcado[3] = "      |";
        ahorcado[4] = "      |";
        ahorcado[5] = "========";

        // añadir el listener al boton
        btnLetra.addActionListener(this);

        // hacer visible la ventana
        setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        // comprobar si la letra introducida esta en la palabra
        if (palabra.contains(txtLetra.getText())) {
            // si esta, se sustituye el guion bajo por la letra
            for (int i = 0; i < palabra.length(); i++) {
                if (palabra.charAt(i) == txtLetra.getText().charAt(0)) {
                    letras[i] = txtLetra.getText();
                }
            }
        } else {
            // si no esta, se resta un intento
            intentos--;
        }

        // actualizar los labels de las letras
        for (int i = 0; i < lblLetras.length; i++) {
            lblLetras[i].setText(letras[i]);
        }

        // actualizar el ahorcado
        switch (intentos) {
            case 6:
                ahorcado[2] = "  O   |";
                break;
            case 5:
                ahorcado[3] = "  |   |";
                break;
            case 4:
                ahorcado[3] = " /|   |";
                break;
            case 3:
                ahorcado[3] = " /|\\  |";
                break;
            case 2:
                ahorcado[4] = " /    |";
                break;
            case 1:
                ahorcado[4] = " / \\  |";
                break;
            case 0:
                ahorcado[5] = "========";
                JOptionPane.showMessageDialog(null, "Has perdido");
                break;
        }

        // actualizar los labels del ahorcado
        for (int i = 0; i < lblAhorcado.length; i++) {
            lblAhorcado[i].setText(ahorcado[i]);
        }

        // comprobar si se ha ganado
        if (Arrays.equals(letras, palabra.split(""))) {
            JOptionPane.showMessageDialog(null, "Has ganado");
        }
    }

    public static void main(String[] args) {
        Ahorcado_jframe a = new Ahorcado_jframe();
        a.setAlwaysOnTop(isDefaultLookAndFeelDecorated());
        
    }
}

