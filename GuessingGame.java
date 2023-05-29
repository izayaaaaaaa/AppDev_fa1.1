// USED FOR THE CHANGING BETWEEN MAIN MENU AND THE GAME ITSELF

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuessingGame {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel gamePanel;
    private JTextField guessField;
    private JButton startButton;
    private JButton guessButton;
    private JLabel resultLabel;

    private int targetNumber;
    private int attempts;

    public GuessingGame() {
        frame = new JFrame("Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        mainPanel = new JPanel();
        gamePanel = new JPanel();

        // Components for main menu
        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGameInterface();
            }
        });
        mainPanel.add(startButton);

        // Components for game interface
        guessField = new JTextField(10);
        guessButton = new JButton("Guess");
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processGuess();
            }
        });
        resultLabel = new JLabel();

        gamePanel.add(guessField);
        gamePanel.add(guessButton);
        gamePanel.add(resultLabel);
        gamePanel.setVisible(false);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        frame.getContentPane().add(gamePanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void showGameInterface() {
        mainPanel.setVisible(false);
        gamePanel.setVisible(true);

        // Initialize game variables
        targetNumber = (int) (Math.random() * 100) + 1;
        attempts = 0;
        resultLabel.setText("");
        guessField.setText("");
    }

    private void processGuess() {
        try {
            int guess = Integer.parseInt(guessField.getText());
            attempts++;

            if (guess == targetNumber) {
                resultLabel.setText("Congratulations! You guessed the number in " + attempts + " attempts.");
                guessButton.setEnabled(false);
            } else if (guess < targetNumber) {
                resultLabel.setText("Too low! Try again.");
            } else {
                resultLabel.setText("Too high! Try again.");
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid input. Please enter a number.");
        }

        guessField.setText("");
        guessField.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GuessingGame();
            }
        });
    }
}
