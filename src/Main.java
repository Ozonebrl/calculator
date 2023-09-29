import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private JFrame frame;
    private JTextField textField;
    private String currentInput = "";
    private double result = 0;
    private String operator = "";

    public Main() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 24));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        frame.add(textField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 24));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JButton source = (JButton) event.getSource();
            String command = source.getText();

            if (command.matches("[0-9]") || command.equals(".")) {
                currentInput += command;
                textField.setText(currentInput);
            } else if (command.matches("[/*\\-+]")) {
                if (!currentInput.isEmpty()) {
                    if (!operator.isEmpty()) {
                        calculateResult();
                    }
                    operator = command;
                    result = Double.parseDouble(currentInput);
                    currentInput = "";
                }
            } else if (command.equals("=")) {
                calculateResult();
                operator = "";
            }
        }

        private void calculateResult() {
            if (!currentInput.isEmpty()) {
                double input = Double.parseDouble(currentInput);
                switch (operator) {
                    case "+":
                        result += input;
                        break;
                    case "-":
                        result -= input;
                        break;
                    case "*":
                        result *= input;
                        break;
                    case "/":
                        if (input != 0) {
                            result /= input;
                        } else {
                            textField.setText("Error");
                            return;
                        }
                        break;
                    default:
                        result = input;
                }
                textField.setText(String.valueOf(result));
                currentInput = "";
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
