import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class SpringLayoutExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("SpringLayout Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        SpringLayout layout = new SpringLayout();
        panel.setLayout(layout);
        
        // Create components
        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
        
        // Define the layout constraints
        layout.putConstraint(SpringLayout.WEST, button1, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, button1, 10, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, button2, -100, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.NORTH, button2, 10, SpringLayout.NORTH, panel);
        
        panel.add(button1);
        panel.add(button2);
        
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
