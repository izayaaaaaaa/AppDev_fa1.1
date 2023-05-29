import java.util.Random;
import java.awt.BorderLayout;
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
    JButton fillerHorizontalLine;

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

        startButton = new JButton("Start Game");
        startButton.setPreferredSize(new Dimension(150, 50));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        fillerHorizontalLine = new JButton("Horizontal Line");
        fillerHorizontalLine.setPreferredSize(new Dimension(150, 50));
        rulesButton = new JButton("Rules");
        rulesButton.setPreferredSize(new Dimension(150, 50));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.anchor = GridBagConstraints.CENTER;
        c.insets.top = 10;
        c.insets.bottom = 10;

        mainMenuPanel.add(startButton, c);
        mainMenuPanel.add(fillerHorizontalLine, c);
        mainMenuPanel.add(rulesButton, c);
    }

    void createGameInterface() {
        // GAME INTERFACE COMPONENTS
        gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        gameNorthPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        mainMenuButton = new JButton("Main Menu");
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
                    timer.stop();
                } else if (inputNumber < randomNumber) {
                    centerTitle.setText(input + " is too low!");
                } else {
                    centerTitle.setText(input + " is too high!");
                }
            }

            attemptsLabel.setText("Attempts: " + attemptsCount.toString());
            updateGUI();
        } catch (NumberFormatException e) {
            centerTitle.setText(input + " is not a number!");
            updateGUI();
        }
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
