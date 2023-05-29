import java.util.Random;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class game_interface extends JFrame {
    // variables
    private static game_interface firstInstance = null;

    Random rand = new Random();
    Integer randomNumber = rand.nextInt(100);
    
    JPanel mainMenuPanel, gamePanel, gameNorthPanel, gameCenterPanel;

    JButton startButton, rulesButton;

    JLabel centerTitle, attempts;
    JTextField inputField;  
    Integer attemptsCount = 0;

    // create the GUI
    public game_interface() {
        super("Guess the Number");
        setLayout(new BorderLayout());

        // main menu components
        mainMenuPanel = new JPanel(new BorderLayout());

        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGameInterface();
            }
        });
        rulesButton = new JButton("Rules");

        mainMenuPanel.add(startButton, BorderLayout.NORTH);
        mainMenuPanel.add(rulesButton, BorderLayout.SOUTH);

        // game interface components
        gamePanel = new JPanel(new BorderLayout());

        gameNorthPanel = new JPanel(new SpringLayout());
        // text buttons on the left
        // timer on the right
        
        gameCenterPanel = new JPanel(new BorderLayout());
        centerTitle = new JLabel("what number am i?");
        inputField = new JTextField();
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                validateInput(input);
            }
        });
        attempts = new JLabel("Attempts: " + attemptsCount.toString());

        gameCenterPanel.add(centerTitle, BorderLayout.NORTH);
        gameCenterPanel.add(inputField, BorderLayout.CENTER);
        gameCenterPanel.add(attempts, BorderLayout.SOUTH);

        gamePanel.add(gameNorthPanel, BorderLayout.NORTH);
        gamePanel.add(gameCenterPanel, BorderLayout.CENTER);

        // setup the frame
        add(mainMenuPanel, BorderLayout.CENTER);
        // pack();
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);
    }

    private void showGameInterface() {
        remove(mainMenuPanel);
        add(gamePanel, BorderLayout.CENTER);
        updateGUI();
    }

    private void validateInput(String input) {
        // if input is not a number
        // if input is not between 1 to 100
        // if input is not an integer
        
        try {
            int inputNumber = Integer.parseInt(input);

            if (inputNumber < 1 || inputNumber > 100) {
                centerTitle.setText(input + " is not between 1 to 100!");
            } else {
                if (inputNumber == randomNumber) {
                    centerTitle.setText("you guessed it right!");
                } else if (inputNumber < randomNumber) {
                    centerTitle.setText(input + " is too low!");
                } else {
                    centerTitle.setText(input + " is too high!");
                }
            }
            updateGUI();
        } catch (NumberFormatException e) {
            centerTitle.setText(input + " is not a number!");
            updateGUI();
        }
    }

    private void updateGUI(){
        revalidate();
        repaint();
    }

    // ensure only one instance of the game_interface is created
    public static game_interface getInstance() {
        if (firstInstance == null) {
            firstInstance = new game_interface();
        }
        return firstInstance;
    }

    public static void main(String[] args) {
        game_interface.getInstance();
    }
}
