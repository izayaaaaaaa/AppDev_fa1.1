// Requirements:
// 1. Allow players to keep guessing until they guess the number —— DONE (check validateInput method)
// 2. Keep a count of the number of guesses. —— DONE (check validateInput method)
// 3. Choose two colors for your game: —— DONE (check validateInput method)
//      one should be used to indicate that the value the players guessed is higher than the target; 
//      the other is used to indicate that the value the players guessed is lower than the target.
// 4. With each new guess, show the guess count and change the form color based on whether the guess is higher than the target or lower. —— DONE (check validateInput method)
// 5. When the player hits the target, display a message indicating the number of guesses it took. —— DONE (check validateInput method)
// 6. Set the time to guess the number. You should define this in your program. If the the player took more time than what is allowed, the player looses the game. —— DONE (check gameTimer method)
// 7. Provide a reset button to enable the user to re-start the game without re-running your application. —— DONE (check newGame button)
// 8. Use the  Random class of Java in generating the target number as follows. —— DONE (check startNewGame method)

import java.util.Random;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class game_interface {
    // variables
    JFrame frame;
    static game_interface firstInstance = null;

    Random rand;
    Integer randomNumber;
    
    Integer attemptsCount = 0;
    
    int timeRemaining;
    Timer timer;

    JPanel mainMenuPanel, gamePanel, gameNorthPanel, gameCenterPanel, emptyJPanel, inputFieldPanel;

    JButton startButton, rulesButton, mainMenuButton, newGameButton;

    JLabel centerTitle, attemptsLabel, timerLabel;
    JTextField inputField;  
    
    public game_interface() {
        frame = new JFrame("Guess the Number");
        
        createMainMenu();
        createGameInterface();
        
        frame.add(mainMenuPanel);
        frame.setSize(1024, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    } // constructor

    void createMainMenu() {
        // MAIN MENU COMPONENTS
        mainMenuPanel = new JPanel(new GridBagLayout());
        mainMenuPanel.setBackground(Color.decode("#101014"));

        startButton = new JButton("Start Game");
        startButton.setFocusPainted(false);
        startButton.setForeground(Color.decode("#C56054"));
        startButton.setFont(startButton.getFont().deriveFont(25f));
        customizeButton(startButton);
        startButton.setBorderPainted(true);
        startButton.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#40DAA6")));
        startButton.setPreferredSize(new Dimension(150, 50));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        rulesButton = new JButton("Rules");
        rulesButton.setForeground(Color.decode("#399FC3"));
        rulesButton.setFont(startButton.getFont().deriveFont(25f));
        customizeButton(rulesButton);
        rulesButton.setBorderPainted(true);
        rulesButton.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.decode("#40DAA6")));
        rulesButton.setPreferredSize(new Dimension(150, 50));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.anchor = GridBagConstraints.CENTER;
        c.insets.top = 0;
        c.insets.bottom = 0;

        mainMenuPanel.add(startButton, c);
        mainMenuPanel.add(rulesButton, c);
    }

    void createGameInterface() {
        // GAME INTERFACE COMPONENTS
        gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        // gamePanel.setBackground(Color.decode("#101014"));

        gameNorthPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        mainMenuButton = new JButton("Main Menu");
        customizeButton(mainMenuButton);
        c.gridx = 0;
        c.gridy = 0;
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(gamePanel);
                frame.add(mainMenuPanel);
                updateGUI();
            }
        });
        gameNorthPanel.add(mainMenuButton, c);

        newGameButton = new JButton("New Game");
        customizeButton(newGameButton);
        c.gridx = 1;
        c.gridy = 0;
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        gameNorthPanel.add(newGameButton, c);


        emptyJPanel = new JPanel();
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        gameNorthPanel.add(emptyJPanel, c);

        timerLabel = new JLabel("30");
        c.weightx = 0;
        c.gridx = 3;
        c.gridy = 0;
        c.anchor = GridBagConstraints.EAST;
        
        gameNorthPanel.add(timerLabel, c); // add timer later

        gameCenterPanel = new JPanel(new BorderLayout());
        gameCenterPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        centerTitle = new JLabel("what number am i?");

        inputFieldPanel = new JPanel();
        inputField = new JTextField("_ _");
        inputField.setColumns(2);
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                validateInput(input);
            }
        });
        inputFieldPanel.add(inputField);
        
        attemptsLabel = new JLabel("Attempts: " + attemptsCount.toString());

        gameCenterPanel.add(centerTitle, BorderLayout.NORTH);
        gameCenterPanel.add(inputFieldPanel, BorderLayout.CENTER);
        gameCenterPanel.add(attemptsLabel, BorderLayout.SOUTH);

        gamePanel.add(gameNorthPanel, BorderLayout.NORTH);
        gamePanel.add(gameCenterPanel, BorderLayout.CENTER);
    }

    void startNewGame() {
        // initialize game variables
        rand = new Random();
        randomNumber = rand.nextInt(100);
        attemptsCount = 0;
        timeRemaining = 30;

        timerLabel.setText(Integer.toString(timeRemaining));
        centerTitle.setText("what number am i?");
        inputField.setEditable(true);
        inputField.setText("_ _");
        attemptsLabel.setText("Attempts: " + attemptsCount.toString());

        // if timer is running, stop it
        if (timer != null) {
            timer.stop();
        }
        
        // identify if the current panel is the main menu or the game panel
        if (frame.getContentPane().getComponent(0).equals(mainMenuPanel)) {
            frame.getContentPane().remove(mainMenuPanel);
        } else {
            frame.getContentPane().remove(gamePanel);
        }

        gameTimer();

        frame.add(gamePanel);
        updateGUI();
    }

    void gameTimer() {
        ActionListener timerAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    timerLabel.setText(Integer.toString(timeRemaining));
                });
                timeRemaining--;

                if (timeRemaining < 0) {
                    timer.stop();
                    centerTitle.setText("Time's up!");
                    inputField.setEditable(false);
                }
            }
        };
        timer = new Timer(1000, timerAction);
        timer.start();
    }

    void validateInput(String input) {
        // if input is not a number
        // if input is not between 1 to 100
        // if input is not an integer
        
        try {
            int inputNumber = Integer.parseInt(input);

            if (inputNumber < 1 || inputNumber > 100) {
                centerTitle.setText(input + " is not between 1 to 100!");
            } else {
                attemptsCount++;

                if (inputNumber == randomNumber) {
                    centerTitle.setText("you guessed it right!");
                    centerTitle.setForeground(Color.GREEN);
                    attemptsLabel.setForeground(Color.GREEN);
                    inputField.setEditable(false);
                    timer.stop();
                } else if (inputNumber < randomNumber) {
                    centerTitle.setText(input + " is too low!");
                    centerTitle.setForeground(Color.BLUE);
                    attemptsLabel.setForeground(Color.BLUE);
                } else { // input is higher than the target 
                    centerTitle.setText(input + " is too high!");
                    centerTitle.setForeground(Color.RED);
                    attemptsLabel.setForeground(Color.RED);
                }
            }

            attemptsLabel.setText("It took you " + attemptsCount.toString() + " attempts!");
            updateGUI();
        } catch (NumberFormatException e) {
            centerTitle.setText(input + " is not a number!");
            updateGUI();
        }
    }

    void customizeButton (JButton button) {
        // removes borders, background, and opaque; just the text is visible
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }

    void updateGUI(){
        frame.revalidate();
        frame.repaint();
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
