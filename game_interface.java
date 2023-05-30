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
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

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

    JPanel mainMenuPanel, rulesPanel, ruleItemsPanel, gamePanel, gameNorthPanel, gameCenterPanel, emptyJPanel;

    GridBagConstraints mainMenuConstraints, createRulesConstraints, createGameInterfaceConstraints;

    JButton startButton, rulesButton, mainMenuButton, newGameButton;

    JLabel centerTitle, attemptsLabel, timerLabel, rule1, rule2, rule3, rule4, author;
    CustomTextField inputField;  

    Font customFont, customThinnerFont;

    // pallette 
    Color red = Color.decode("#C56054");
    Color blue = Color.decode("#399FC3");
    Color green = Color.decode("#67B94F");
    Color bluegreen = Color.decode("#3AB98F");
    Color background = Color.decode("#101014");
    Color inputFieldBG = Color.GRAY;


    public game_interface() {
        frame = new JFrame("Guess the Number");
        
        customFont = loadCustomFont("HandjetFlowerDouble-Medium.ttf");

        createMainMenu();
        createGameInterface();
        createRules();
        
        frame.add(mainMenuPanel);
        frame.setSize(1024, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    } // constructor

    void createMainMenu() {
        mainMenuPanel = new JPanel(new GridBagLayout());
        mainMenuPanel.setBackground(Color.decode("#101014"));

        startButton = new JButton("Start Game");
        startButton.setFocusPainted(false);
        startButton.setForeground(red);
        
        startButton.setFont(customFont.deriveFont(40f));
        customizeButton(startButton);
        startButton.setBorderPainted(true);
        startButton.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, bluegreen));
        startButton.setPreferredSize(new Dimension(150, 100));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        rulesButton = new JButton("Rules");
        rulesButton.setForeground(blue);
        rulesButton.setFont(customFont.deriveFont(40f));
        customizeButton(rulesButton);
        rulesButton.setBorderPainted(true);
        rulesButton.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, bluegreen));
        rulesButton.setPreferredSize(new Dimension(150, 100));
        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(mainMenuPanel);
                frame.add(rulesPanel);
                updateGUI();
            }
        });

        mainMenuConstraints = new GridBagConstraints();
        mainMenuConstraints.gridx = 0;
        mainMenuConstraints.gridy = GridBagConstraints.RELATIVE;
        mainMenuConstraints.anchor = GridBagConstraints.CENTER;
        mainMenuConstraints.insets.top = 0;
        mainMenuConstraints.insets.bottom = 0;

        mainMenuPanel.add(startButton, mainMenuConstraints);
        mainMenuPanel.add(rulesButton, mainMenuConstraints);
    }

    void createRules() {
        rulesPanel = new JPanel(new BorderLayout());
        rulesPanel.setBackground(background);
        rulesPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setForeground(bluegreen);
        
        mainMenuButton.setFont(customFont.deriveFont(20f));
        customizeButton(mainMenuButton);
        mainMenuButton.setHorizontalAlignment(JButton.LEFT);
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(rulesPanel);
                frame.add(mainMenuPanel);
                updateGUI();
            }
        });
        rulesPanel.add(mainMenuButton, BorderLayout.NORTH);

        ruleItemsPanel = new JPanel(new GridBagLayout());
        ruleItemsPanel.setBackground(background);
        createRulesConstraints = new GridBagConstraints();

        JLabel rulesTitle = new JLabel("Rules");
        rulesTitle.setForeground(bluegreen);
        rulesTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, bluegreen));
        rulesTitle.setFont(customFont.deriveFont(46f));
        createRulesConstraints.gridx = 0;
        createRulesConstraints.gridy = 0;
        createRulesConstraints.anchor = GridBagConstraints.WEST;
        createRulesConstraints.insets = new Insets(0, 0, 50, 0);
        ruleItemsPanel.add(rulesTitle, createRulesConstraints);

        createRule(
            rule1, 
            "<html><body>" 
            + "<span style='color: #AA3290'>>&nbsp;&nbsp;</span> You have <u>30 seconds</u> to <span style='color: #AA3290'>guess</span> the randomly generated number"
            + "</body></html>",
            1
        );
        createRule(
            rule2, 
            "<html><body>" 
            + "<span style='color: #C56054'>>&nbsp;&nbsp;</span> When the input number is <u>higher</u> than the target, the components will turn <span style='color: #C56054'>red</span>"
            + "</body></html>",
            2
        );
        createRule(
            rule3, 
            "<html><body>" 
            + "<span style='color: #399FC3'>>&nbsp;&nbsp;</span> When the input number is <u>lower</u> than the target, the components will turn <span style='color: #399FC3'>blue</span>"
            + "</body></html>",
            3
        );
        createRule(
            rule4, 
            "<html><body>" 
            + "<span style='color: #67B94F'>>&nbsp;&nbsp;</span> The components will turn <span style='color: #67B94F'>green</span> if the input is <u>equal</u> to the random number"
            + "</body></html>",
            4
        );

        rulesPanel.add(ruleItemsPanel, BorderLayout.CENTER);

        author = new JLabel("Created by Francyn Macadangdang for IT191");
        author.setForeground(Color.GRAY);
        author.setFont(customFont.deriveFont(16f));
        author.setHorizontalAlignment(JLabel.CENTER);
        rulesPanel.add(author, BorderLayout.SOUTH);
    }

    void createRule(JLabel rule, String text, int y) {
        rule = new JLabel();
        rule.setForeground(Color.WHITE);
        rule.setFont(customFont.deriveFont(28f));
        rule.setText(text);
        createRulesConstraints.gridx = 0;
        createRulesConstraints.gridy = y;
        createRulesConstraints.gridwidth = 3;
        createRulesConstraints.anchor = GridBagConstraints.WEST;
        createRulesConstraints.insets = new Insets(0, 0, 30, 0);
        ruleItemsPanel.add(rule, createRulesConstraints);
    }

    void createGameInterface() {
        gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 0, 50));
        gamePanel.setBackground(background);

        gameNorthPanel = new JPanel(new GridBagLayout());
        gameNorthPanel.setBackground(background);
        createGameInterfaceConstraints = new GridBagConstraints();

        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setForeground(bluegreen);
        mainMenuButton.setFont(customFont.deriveFont(20f));
        customizeButton(mainMenuButton);
        createGameInterfaceConstraints.gridx = 0;
        createGameInterfaceConstraints.gridy = 0;
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(gamePanel);
                frame.add(mainMenuPanel);
                updateGUI();
            }
        });
        gameNorthPanel.add(mainMenuButton, createGameInterfaceConstraints);

        newGameButton = new JButton("New Game");
        newGameButton.setForeground(bluegreen);
        newGameButton.setFont(customFont.deriveFont(20f));
        customizeButton(newGameButton);
        createGameInterfaceConstraints.gridx = 1;
        createGameInterfaceConstraints.gridy = 0;
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        gameNorthPanel.add(newGameButton, createGameInterfaceConstraints);


        emptyJPanel = new JPanel();
        emptyJPanel.setBackground(background);
        createGameInterfaceConstraints.weightx = 0.5;
        createGameInterfaceConstraints.gridx = 2;
        createGameInterfaceConstraints.gridy = 0;
        gameNorthPanel.add(emptyJPanel, createGameInterfaceConstraints);

        timerLabel = new JLabel("30");
        timerLabel.setForeground(bluegreen);
        timerLabel.setFont(customFont.deriveFont(20f));
        createGameInterfaceConstraints.weightx = 0;
        createGameInterfaceConstraints.gridx = 3;
        createGameInterfaceConstraints.gridy = 0;
        createGameInterfaceConstraints.anchor = GridBagConstraints.EAST;
        
        gameNorthPanel.add(timerLabel, createGameInterfaceConstraints); // add timer later        
        
        createGameInterfaceConstraints.anchor = GridBagConstraints.CENTER;

        customThinnerFont = loadCustomFont("Handjet[EGRD,ESHP,wght].ttf");

        gameCenterPanel = new JPanel(new GridBagLayout());
        gameCenterPanel.setBackground(background);
        gameCenterPanel.setBorder(BorderFactory.createEmptyBorder(75, 0, 0, 0));
        centerTitle = new JLabel("what number am i?");
        centerTitle.setForeground(Color.WHITE);
        centerTitle.setFont(customThinnerFont.deriveFont(60f));
        centerTitle.setHorizontalAlignment(JLabel.CENTER);
        createGameInterfaceConstraints.gridx = 0;
        createGameInterfaceConstraints.gridy = 0;
        createGameInterfaceConstraints.insets.bottom = 75;
        gameCenterPanel.add(centerTitle, createGameInterfaceConstraints);
        
        inputField = new CustomTextField(5, 50, 100);
        inputField.setHorizontalAlignment(CustomTextField.CENTER);
        inputField.setForeground(Color.WHITE);
        // set the font to 60
        inputField.setFont(customFont.deriveFont(72f));
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                validateInput(input);
            }
        });
        createGameInterfaceConstraints.gridx = 0;
        createGameInterfaceConstraints.gridy = 1;
        createGameInterfaceConstraints.insets.top = 0;
        createGameInterfaceConstraints.insets.bottom = 100;
        gameCenterPanel.add(inputField, createGameInterfaceConstraints);
        
        attemptsLabel = new JLabel("Attempts: " + attemptsCount.toString());
        attemptsLabel.setForeground(Color.WHITE);
        attemptsLabel.setFont(customThinnerFont.deriveFont(40f));
        attemptsLabel.setHorizontalAlignment(JLabel.CENTER);
        createGameInterfaceConstraints.gridx = 0;
        createGameInterfaceConstraints.gridy = 2;
        gameCenterPanel.add(attemptsLabel, createGameInterfaceConstraints);

        gamePanel.add(gameNorthPanel, BorderLayout.NORTH);
        gamePanel.add(gameCenterPanel, BorderLayout.CENTER);
    }

    void startNewGame() {
        // initialize game variables
        rand = new Random();
        randomNumber = rand.nextInt(100);
        attemptsCount = 0;
        timeRemaining = 30;
        inputField.setText(null);

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

    Font loadCustomFont(String fontPath) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
            return font;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return null;
        }
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

