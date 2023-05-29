import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GroupLayoutExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("GroupLayout Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        
        // Create components
        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
        JButton button3 = new JButton("Button 3");
        
        // Define the horizontal and vertical groups
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(button1)
                .addComponent(button2)
                .addComponent(button3)
        );
        
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(button1)
                .addComponent(button2)
                .addComponent(button3)
        );
        
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
