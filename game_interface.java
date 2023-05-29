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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class game_interface {
    // variables
    JFrame frame;
    static game_interface firstInstance = null;

    Random rand;
    Integer randomNumber;
    
    Integer attemptsCount = 0;
    
    int timeRemaining;
    Timer timer;

    JPanel mainMenuPanel, gamePanel, gameNorthPanel, gameCenterPanel, emptyJPanel;

    JButton startButton, rulesButton, mainMenuButton, newGameButton;

    JLabel centerTitle, attemptsLabel, timerLabel;
    CustomTextField inputField;  

    // pallette 
    Color red = Color.decode("#C56054");
    Color blue = Color.decode("#399FC3");
    Color green = Color.decode("#67B94F");
    Color bluegreen = Color.decode("#40DAA6");
    Color background = Color.decode("#101014");

    Color inputFieldBG = Color.GRAY;

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
        startButton.setForeground(red);
        startButton.setFont(startButton.getFont().deriveFont(25f));
        customizeButton(startButton);
        startButton.setBorderPainted(true);
        startButton.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, bluegreen));
        startButton.setPreferredSize(new Dimension(150, 50));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        rulesButton = new JButton("Rules");
        rulesButton.setForeground(blue);
        rulesButton.setFont(rulesButton.getFont().deriveFont(25f));
        customizeButton(rulesButton);
        rulesButton.setBorderPainted(true);
        rulesButton.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, bluegreen));
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
        gamePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 0, 50));
        gamePanel.setBackground(background);

        gameNorthPanel = new JPanel(new GridBagLayout());
        gameNorthPanel.setBackground(background);
        GridBagConstraints c = new GridBagConstraints();

        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setForeground(bluegreen);
        mainMenuButton.setFont(mainMenuButton.getFont().deriveFont(16f));
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
        newGameButton.setForeground(bluegreen);
        newGameButton.setFont(newGameButton.getFont().deriveFont(16f));
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
        emptyJPanel.setBackground(background);
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        gameNorthPanel.add(emptyJPanel, c);

        timerLabel = new JLabel("30");
        timerLabel.setForeground(green);
        timerLabel.setFont(timerLabel.getFont().deriveFont(16f));
        c.weightx = 0;
        c.gridx = 3;
        c.gridy = 0;
        c.anchor = GridBagConstraints.EAST;
        
        gameNorthPanel.add(timerLabel, c); // add timer later        
        
        c.anchor = GridBagConstraints.CENTER;

        gameCenterPanel = new JPanel(new GridBagLayout());
        gameCenterPanel.setBackground(background);
        gameCenterPanel.setBorder(BorderFactory.createEmptyBorder(75, 0, 0, 0));
        centerTitle = new JLabel("what number am i?");
        centerTitle.setForeground(Color.WHITE);
        centerTitle.setFont(centerTitle.getFont().deriveFont(48f));
        centerTitle.setHorizontalAlignment(JLabel.CENTER);
        c.gridx = 0;
        c.gridy = 0;
        c.insets.bottom = 75;
        gameCenterPanel.add(centerTitle, c);
        
        inputField = new CustomTextField(5, 50, 100);
        inputField.setHorizontalAlignment(CustomTextField.CENTER);
        inputField.setForeground(Color.WHITE);
        // set the font to 60
        inputField.setFont(inputField.getFont().deriveFont(60f));
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                validateInput(input);
            }
        });
        c.gridx = 0;
        c.gridy = 1;
        c.insets.top = 0;
        c.insets.bottom = 100;
        gameCenterPanel.add(inputField, c);
        
        attemptsLabel = new JLabel("Attempts: " + attemptsCount.toString());
        attemptsLabel.setForeground(Color.WHITE);
        attemptsLabel.setFont(centerTitle.getFont().deriveFont(32f));
        attemptsLabel.setHorizontalAlignment(JLabel.CENTER);
        c.gridx = 0;
        c.gridy = 2;
        gameCenterPanel.add(attemptsLabel, c);

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
        centerTitle.setForeground(Color.WHITE);
        inputField.setEditable(true);
        inputFieldBG = Color.GRAY;
        attemptsLabel.setText("Attempts: " + attemptsCount.toString());
        attemptsLabel.setForeground(Color.WHITE);

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

                if (timeRemaining < 1) {
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
                    centerTitle.setForeground(green);
                    attemptsLabel.setForeground(green);
                    inputField.setEditable(false);
                    inputFieldBG = green;
                    timer.stop();
                    attemptsLabel.setText("It took you " + attemptsCount.toString() + " attempts!");
                } else if (inputNumber < randomNumber) {
                    centerTitle.setText(input + " is too low!");
                    centerTitle.setForeground(blue);
                    attemptsLabel.setForeground(blue);
                    inputFieldBG = blue;
                    attemptsLabel.setText("Attempts: " + attemptsCount.toString());
                } else { // input is higher than the target 
                    centerTitle.setText(input + " is too high!");
                    centerTitle.setForeground(red);
                    attemptsLabel.setForeground(red);
                    inputFieldBG = red;
                    attemptsLabel.setText("Attempts: " + attemptsCount.toString());
                }
            }
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

    public class CustomTextField extends JTextField {
        private final int LEFT_PADDING = 10;
        private final int RIGHT_PADDING = 10;
        private final int TOP_PADDING = 5;
        private final int BOTTOM_PADDING = 5;
        private final int MAX_LENGTH = 2; // Maximum total length of the input (including separators)
    
        public CustomTextField(int columns, int width, int height) {
            super(columns);
            setOpaque(false);
            setBorder(new EmptyBorder(TOP_PADDING, LEFT_PADDING, BOTTOM_PADDING, RIGHT_PADDING));
            setPreferredSize(new Dimension(width, height));
            setDocument((Document) new TwoValueDocument());
            setMargin(new Insets(0, LEFT_PADDING, 0, RIGHT_PADDING)); // Add padding on the left side
        }
    
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setColor(inputFieldBG);
            g2.fillRect(LEFT_PADDING, TOP_PADDING, getWidth() - LEFT_PADDING - RIGHT_PADDING, getHeight() - TOP_PADDING - BOTTOM_PADDING);

            super.paintComponent(g2);
            g2.dispose();
        }

        private class TwoValueDocument extends PlainDocument {
            @Override
            public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
                if (getLength() + str.length() <= MAX_LENGTH) {
                    super.insertString(offset, str, attr);
                }
            }
        }
    }
}

