import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class CustomTextField extends JTextField {
    private Color inputFieldBG = Color.GRAY;
    
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