import java.util.Random;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    
    // main menu and the game itself are two panels that are swapped

    JPanel northPanel, centerPanel;

    JLabel centerTitle, attempts;
    JTextField inputField;  
    Integer attemptsCount = 0;

    // create the GUI
    public game_interface() {
        super("Guess the Number");
        setLayout(new BorderLayout());

        northPanel = new JPanel(new SpringLayout());
        // text buttons on the left
        // timer on the right
        
        centerPanel = new JPanel(new BorderLayout());

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

        centerPanel.add(centerTitle, BorderLayout.NORTH);
        centerPanel.add(inputField, BorderLayout.CENTER);
        centerPanel.add(attempts, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // pack();
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);
    }

    private void validateInput(String input) {
        // if input is not a number
        // if input is not between 1 to 100
        // if input is not an integer
        
        try {
            int inputNumber = Integer.parseInt(input);

            if (inputNumber <= 1 || inputNumber >= 100) {
                centerTitle.setText(input + " is not between 1 to 100!");
            } else {
                if (inputNumber == randomNumber) {
                    centerTitle.setText(input + " is not between 1 to 100!");
                } else if (inputNumber < randomNumber) {
                    centerTitle.setText(input + " is not between 1 to 100!");
                } else {
                    centerTitle.setText(input + " is not between 1 to 100!");
                }
            }
            revalidate();
            repaint();
        } catch (NumberFormatException e) {
            centerTitle.setText(input + " is not a number!");

            revalidate();
            repaint();
        }
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
