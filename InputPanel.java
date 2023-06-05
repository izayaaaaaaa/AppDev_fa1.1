// The event will be coming from InputPanel (when the textbox is “entered” after typing a number).

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
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InputPanel extends JPanel {
    private FeedbackPanel feedbackPanel;

    private Random rand;
    private Integer randomNumber;
    private boolean isInputValidBool;
    
    private JLabel inputLabel;
    private CustomTextField inputField;
    private String stringInput;

    Font customFont;

    // pallette 
    private Color background = Color.decode("#101014");

    // ask if attempts counter and timer are included IMPORTANT!!!
    
    public InputPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
        setBackground(background);
        setPreferredSize(new Dimension(350, 400));

        customFont = loadCustomFont("Fonts/HandjetFlowerDouble-Medium.ttf");

        GridBagConstraints constraints = new GridBagConstraints();
    
        inputLabel = new JLabel("Enter a number [1 to 100]");
        inputLabel.setForeground(Color.WHITE);
        inputLabel.setFont(customFont.deriveFont(24f));
        inputField = new CustomTextField(3, 40, 80);
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stringInput = inputField.getText();
                isInputValidBool = validateInput(stringInput);

                if (isInputValidBool == true) {
                    feedbackPanel.displayMessage();
                }
            }
        });
        inputField.setHorizontalAlignment(CustomTextField.CENTER);
        inputField.setForeground(Color.WHITE);
        inputField.setFont(customFont.deriveFont(48f));

        constraints.insets = new Insets(10, 0, 10, 0);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        add(inputLabel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(inputField, constraints);
    } // constructor

    public void generateRandomNumber() {
        rand = new Random();
        randomNumber = rand.nextInt(100);

        System.out.println("Random number is: " + randomNumber);
    }

    public boolean validateInput(String stringInput) {
        try {
            int intInput = Integer.parseInt(stringInput);

            if (intInput < 1 || intInput > 100) {
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public void lockInputField() {
        inputField.setEditable(false);;
        inputField.setFocusable(false);
    }

    public void resetInputField() {
        inputField.setText("");
        inputField.setEditable(true);
        inputField.setFocusable(true);
    }

    public int getInputNumber() {
        return Integer.parseInt(stringInput);
    }

    public boolean getIsInputValidBool() {
        return isInputValidBool;
    } // getter for isInputValidBool

    public int getRandomNumber() {
        return randomNumber;
    } // getter for randomNumber

    public void setFeedbackPanel(FeedbackPanel feedbackPanel) {
        this.feedbackPanel = feedbackPanel;
    } // setter for feedbackPanel

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
