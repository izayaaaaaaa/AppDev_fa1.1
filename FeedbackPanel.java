// To handle the event, MainFrame should display on the FeedbackPanel the messages corresponding to the user inputs.

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class FeedbackPanel extends JPanel { 
    private InputPanel inputPanel;

    GridBagConstraints constraints = new GridBagConstraints();

    private JTextArea feedbackTextArea;
    private int lineCount = 0;

    Font customFont;

    // pallette 
    Color red = Color.decode("#C56054");
    Color blue = Color.decode("#399FC3");
    Color green = Color.decode("#67B94F");
    Color bluegreen = Color.decode("#3AB98F");
    Color background = Color.decode("#101014");
    Color inputFieldBG = Color.GRAY;

    // constructor
    public FeedbackPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
        setBackground(background);
        setPreferredSize(new Dimension(500, 400));

        customFont = loadCustomFont("Fonts/HandjetFlowerDouble-Medium.ttf");
    
        feedbackTextArea = new JTextArea(17, 35);
        feedbackTextArea.setBackground(background);
        feedbackTextArea.setForeground(Color.WHITE);
        feedbackTextArea.setFont(feedbackTextArea.getFont().deriveFont(24f));       
        feedbackTextArea.setEditable(false);
        feedbackTextArea.setAlignmentX(CENTER_ALIGNMENT);
    
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.CENTER;
        add(feedbackTextArea, constraints);
    }
    

    // assuming that the user input is valid, display the appropriate message
    public void displayMessage() {
        if (lineCount >= 5 && lineCount % 5 == 0) {
            feedbackTextArea.setText("");
        }

        lineCount++;

        if (inputPanel.getInputNumber() > inputPanel.getRandomNumber()) {
            feedbackTextArea.append("Your guess is too high.\n");
        } else if (inputPanel.getInputNumber() < inputPanel.getRandomNumber()) {
            feedbackTextArea.append("Your guess is too low.\n");
        } else {
            feedbackTextArea.append("You guessed the number!\n");
        }
    }   

    public void resetFeedbackPanel() {
        // reset the feedback panel
    }

    public void setInputPanel(InputPanel inputPanel) {
        this.inputPanel = inputPanel;
    } // setter for inputPanel

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
}
