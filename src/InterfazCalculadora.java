import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfazCalculadora extends JFrame implements ActionListener {
    private JTextField pantalla;
    private Calculadora calc = new Calculadora();
    private double num1, num2;
    private String operacion;
    private boolean nuevoNumero = true;

    public InterfazCalculadora() {
        setTitle("CALCULADORA - CALC EQUIPO 1");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Pantalla
        pantalla = new JTextField();
        pantalla.setFont(new Font("Arial", Font.BOLD, 35));
        pantalla.setHorizontalAlignment(JTextField.RIGHT);
        pantalla.setEditable(false);
        add(pantalla, BorderLayout.NORTH);

        // Botones
        JPanel botonesPanel = new JPanel(new GridLayout(5, 4, 10, 10));
        String[] botones = {
            "7", "8", "9", "/",
            "4", "5", "6", "*", 
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String texto : botones) {
            JButton btn = new JButton(texto);
            btn.setFont(new Font("Arial", Font.BOLD, 20));
            btn.addActionListener(this);
            
            // Colores
            if ("+-*/".contains(texto)) btn.setBackground(Color.ORANGE);
            else if ("=".equals(texto)) btn.setBackground(Color.GREEN);
            else if ("C".equals(texto)) btn.setBackground(Color.RED);
            else btn.setBackground(Color.WHITE);
            
            botonesPanel.add(btn);
        }

        add(botonesPanel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        String textoActual = pantalla.getText();

        // Números
        if ("0123456789".contains(cmd)) {
            if (nuevoNumero) {
                pantalla.setText(cmd);
                nuevoNumero = false;
            } else {
                pantalla.setText(textoActual + cmd);
            }
        } 
        // Operadores: Permite cambiar el operador si ya hay uno
        else if ("+-*/".contains(cmd)) {
            if (!textoActual.isEmpty()) {
                // Si ya hay un operador, lo cambia
                if (textoActual.contains(" + ") || textoActual.contains(" - ") || 
                    textoActual.contains(" * ") || textoActual.contains(" / ")) {
                    
                    // Encuentra el primer número (antes del espacio)
                    String primerNumero = textoActual.split(" ")[0];
                    pantalla.setText(primerNumero + " " + cmd + " ");
                } 
                // Si no hay operador, agrega normalmente
                else if (!textoActual.endsWith(" ")) {
                    pantalla.setText(textoActual + " " + cmd + " ");
                }
                nuevoNumero = false;
            }
        } 
        // Igual: calcular resultado
        else if ("=".equals(cmd)) {
            String[] partes = textoActual.split(" ");
            if (partes.length == 3) {
                try {
                    num1 = Double.parseDouble(partes[0]);
                    num2 = Double.parseDouble(partes[2]);
                    String operador = partes[1];
                    
                    double resultado = 0;
                    switch (operador) {
                        case "+": resultado = calc.sumar(num1, num2); break;
                        case "-": resultado = calc.restar(num1, num2); break;
                        case "*": resultado = calc.multiplicar(num1, num2); break;
                        case "/": 
                            if (num2 == 0) {
                                pantalla.setText("Error: División por 0");
                                nuevoNumero = true;
                                return;
                            }
                            resultado = calc.dividir(num1, num2); 
                            break;
                    }
                    
                    pantalla.setText(String.valueOf(resultado));
                    nuevoNumero = true;
                    
                } catch (NumberFormatException ex) {
                    pantalla.setText("Error en número");
                    nuevoNumero = true;
                }
            }
        } 
        // Limpiar
        else if ("C".equals(cmd)) {
            pantalla.setText("");
            nuevoNumero = true;
        }
    }

    public static void main(String[] args) {
        new InterfazCalculadora().setVisible(true);
    }
}