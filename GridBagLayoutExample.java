import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class GridBagLayoutExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("GridBagLayout Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        
        // Create components
        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
        JButton button3 = new JButton("Button 3");
        
        // Define the layout constraints
        GridBagConstraints constraints = new GridBagConstraints();
        
        // constraints.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally
        constraints.gridx = 0; // Column 0
        constraints.gridy = 0; // Row 0
        constraints.insets = new Insets(10, 10, 10, 10); // Padding
        panel.add(button1, constraints);
        
        constraints.gridx = 1; // Column 1
        constraints.gridy = 0; // Row 0
        panel.add(button2, constraints);
        
        constraints.gridx = 0; // Column 0
        constraints.gridy = 1; // Row 1
        constraints.gridwidth = 2; // Span 2 columns
        panel.add(button3, constraints);
        
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
