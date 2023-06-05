// The event will then be passed on to the MainFrame who will handle the event.
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
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

public class MainFrame {
    static MainFrame firstinstance = null;

    private JFrame frame;
    InputPanel inputPanel;
    FeedbackPanel feedbackPanel;
    // Rules rules;

    private JPanel mainMenuPanel, rulesPanel, ruleItemsPanel, gamePanel, gameNorthPanel, gameCenterPanel, emptyPanel;
    private GridBagConstraints mainMenuConstraints, createRulesConstraints, gameNorthConstraints, gameCenterConstraints;
    private JButton startButton, rulesButton, mainMenuButton, newGameButton;
    private JLabel gameTitle, rule1, rule2, rule3, rule4, author;

    Font customFont;

    // pallette 
    Color red = Color.decode("#C56054");
    Color blue = Color.decode("#399FC3");
    Color green = Color.decode("#67B94F");
    Color bluegreen = Color.decode("#3AB98F");
    Color background = Color.decode("#101014");
    Color inputFieldBG = Color.GRAY;

    public MainFrame() {
        frame = new JFrame("Higher-Lower Game");
        
        customFont = loadCustomFont("Fonts/HandjetFlowerDouble-Medium.ttf");

        createMainMenu();
        createRules();
        createGamePanel(); 

        frame.add(mainMenuPanel);

        frame.setSize(1024, 720);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void createMainMenu() {
        mainMenuPanel = new JPanel(new GridBagLayout());
        mainMenuPanel.setBackground(Color.decode("#101014"));

        // rules = new Rules();

        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        startButton.setForeground(red);
        startButton.setFont(customFont.deriveFont(40f));
        startButton.setPreferredSize(new Dimension(150, 100));
        customizeButton(startButton); 
        startButton.setFocusPainted(false); // removes the select outline
        startButton.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, bluegreen));
        startButton.setBorderPainted(true); 
        
        rulesButton = new JButton("Rules");
        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(mainMenuPanel);
                frame.add(rulesPanel);
                updateGUI();
            }
        });
        rulesButton.setForeground(blue);
        rulesButton.setFont(customFont.deriveFont(40f));
        rulesButton.setPreferredSize(new Dimension(150, 100));
        customizeButton(rulesButton);
        rulesButton.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, bluegreen));
        rulesButton.setBorderPainted(true);

        mainMenuConstraints = new GridBagConstraints();
        mainMenuConstraints.gridx = 0;
        mainMenuConstraints.gridy = GridBagConstraints.RELATIVE;
        mainMenuConstraints.anchor = GridBagConstraints.CENTER;
        mainMenuConstraints.insets.top = 0;
        mainMenuConstraints.insets.bottom = 0;

        mainMenuPanel.add(startButton, mainMenuConstraints);
        mainMenuPanel.add(rulesButton, mainMenuConstraints);
    }

    private void createRules() {
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

    private void createRule(JLabel rule, String text, int y) {
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

    private void createGamePanel() {
        gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 0, 50));
        gamePanel.setBackground(background);

        // gameNorthPanel (main menu, new game, timer?) NORTH -> GridBag
        gameNorthPanel = new JPanel(new GridBagLayout());
        gameNorthPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        gameNorthPanel.setBackground(background);
        gameNorthConstraints = new GridBagConstraints();

        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(gamePanel);
                frame.add(mainMenuPanel);
                updateGUI();
            }
        });
        mainMenuButton.setForeground(bluegreen);
        mainMenuButton.setFont(customFont.deriveFont(20f));
        customizeButton(mainMenuButton);
        mainMenuButton.setBorderPainted(true);
        mainMenuButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, bluegreen));
        mainMenuButton.setPreferredSize(new Dimension(100, 20));

        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        newGameButton.setForeground(bluegreen);
        newGameButton.setFont(customFont.deriveFont(20f));
        customizeButton(newGameButton);
        newGameButton.setBorderPainted(true);
        newGameButton.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, bluegreen));
        newGameButton.setPreferredSize(new Dimension(100, 20));

        emptyPanel = new JPanel();
        emptyPanel.setBackground(background);
        
        gameNorthConstraints.gridx = 0;
        gameNorthConstraints.gridy = 0;
        gameNorthPanel.add(mainMenuButton, gameNorthConstraints);

        gameNorthConstraints.gridx = 1;
        gameNorthConstraints.gridy = 0;
        gameNorthPanel.add(newGameButton, gameNorthConstraints);

        gameNorthConstraints.gridx = 2;
        gameNorthConstraints.gridy = 0;
        gameNorthConstraints.weightx = 1;
        gameNorthPanel.add(emptyPanel, gameNorthConstraints);

        //  gameCenterPanel (inputPanel, feedbackPanel) CENTER -> GridBag
        gameCenterPanel = new JPanel(new GridBagLayout());
        gameCenterPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        gameCenterPanel.setBackground(background);
        gameCenterConstraints = new GridBagConstraints();
        
        gameTitle = new JLabel("Higher-Lower Game");
        gameTitle.setFont(customFont.deriveFont(48f));
        gameTitle.setForeground(Color.decode("#AA3290"));

        inputPanel = new InputPanel();
        feedbackPanel = new FeedbackPanel();

        inputPanel.setFeedbackPanel(feedbackPanel); // establish the connection between the inputPanel and the feedbackPanel
        feedbackPanel.setInputPanel(inputPanel); // establish the connection between the feedbackPanel and the inputPanel

        gameCenterConstraints.gridx = 0;
        gameCenterConstraints.gridy = 0;
        gameCenterConstraints.gridwidth = 2;
        gameCenterConstraints.anchor = GridBagConstraints.WEST;
        gameCenterPanel.add(gameTitle, gameCenterConstraints);

        gameCenterConstraints.gridx = 0;
        gameCenterConstraints.gridy = 1;
        gameCenterConstraints.gridwidth = 1;
        gameCenterConstraints.weightx = 1;
        gameCenterConstraints.weighty = 1;
        gameCenterConstraints.anchor = GridBagConstraints.WEST;
        gameCenterPanel.add(inputPanel, gameCenterConstraints);

        gameCenterConstraints.gridx = 1;
        gameCenterConstraints.gridy = 1;
        gameCenterConstraints.gridwidth = 1;
        gameCenterConstraints.weightx = 1;
        gameCenterConstraints.weighty = 1;
        gameCenterConstraints.anchor = GridBagConstraints.EAST;
        gameCenterPanel.add(feedbackPanel, gameCenterConstraints);

        gamePanel.add(gameNorthPanel, BorderLayout.NORTH);
        gamePanel.add(gameCenterPanel, BorderLayout.CENTER);
    } 

    private void startNewGame() {
        // identify if the current panel is the main menu or the game panel
        if (frame.getContentPane().getComponent(0).equals(mainMenuPanel)) {
            frame.getContentPane().remove(mainMenuPanel);
        } else {
            frame.getContentPane().remove(gamePanel);
            resetComponents();
        }

        inputPanel.generateRandomNumber();
        
        frame.add(gamePanel);
        updateGUI();
    }

    public void resetComponents() {
        inputPanel.resetInputField();
        feedbackPanel.resetFeedbackPanel();
    }

    public void updateGUI(){
        frame.revalidate();
        frame.repaint();
    }

    public void customizeButton (JButton button) {
        // removes everything except the text
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
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

    public static MainFrame getInstance() {
        if (firstinstance == null) {
            firstinstance = new MainFrame();
        }
        return firstinstance;
    }

    public static void main(String[] args) {
        MainFrame.getInstance();
    }

}