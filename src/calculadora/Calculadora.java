package calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculadora extends JFrame{
    private JTextField display;
    private JPanel botonesPanel;
    private String operacion;
    private double resultado;
    
    public Calculadora() {
        super("Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        display = new JTextField();
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        botonesPanel = new JPanel(new GridBagLayout());
        add(botonesPanel, BorderLayout.CENTER);

        String[][] botonesTexto = {{"C", "/", "*", "-"},
                                    {"7", "8", "9", "+"},
                                    {"4", "5", "6", ""},
                                    {"1", "2", "3", "="},
                                    {"0", "", ".", ""}};

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(2, 2, 2, 2);

        // Crear y agregar botones a los subpaneles
        for (int i = 0; i < botonesTexto.length; i++) {
            for (int j = 0; j < botonesTexto[i].length; j++) {
                String texto = botonesTexto[i][j];
                JButton boton = new JButton(texto);
                boton.addActionListener(new BotonListener());

                if (i == 4 && (j == 0)) {
                    gbc.gridwidth = 2;
                } else {
                    gbc.gridwidth = 1;
                }
                
                if((i == 1 && j == 3) || (i == 3 && j == 3)) {
                    gbc.gridheight = 2;
                } else {
                    gbc.gridheight = 1;
                }

                gbc.gridx = j;
                gbc.gridy = i;
                botonesPanel.add(boton, gbc);
            }
        }

        setResizable(false);
        setVisible(true);
    }
    
    private class BotonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String textoBoton = ((JButton) e.getSource()).getText();
            
            if (textoBoton.equals("C")) {
                operacion = "";
                resultado = 0;
                display.setText("");
            } else if (textoBoton.equals("=")) {
                calcularResultado();
            } else {
                display.setText(display.getText() + textoBoton);
            }
        }
        
        private void calcularResultado() {
            String entrada = display.getText();
            String[] elementos = entrada.split("[+\\-*/]");
            
            resultado = Double.parseDouble(elementos[0]);
            operacion = "";
            
            for (int i = 1; i < elementos.length; i++) {
                String operador = entrada.substring(elementos[i - 1].length(), elementos[i - 1].length() + 1);
                double numero = Double.parseDouble(elementos[i]);
                
                switch (operador) {
                    case "+":
                        resultado += numero;
                        break;
                    case "-":
                        resultado -= numero;
                        break;
                    case "*":
                        resultado *= numero;
                        break;
                    case "/":
                        resultado /= numero;
                        break;
                }
            }
            
            display.setText(String.valueOf(resultado));
        }
    }

    public static void main(String[] args) {
        new Calculadora();
    }
    
}
