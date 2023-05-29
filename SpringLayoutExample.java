// USED FOR THE NORTH PANEL

import javax.swing.*;
// import java.awt.*;

public class SpringLayoutExample extends JFrame {
    public SpringLayoutExample() {
        setTitle("SpringLayout Example");

        JPanel contentPane = new JPanel();
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        JButton saveButton = new JButton("Save");

        contentPane.add(nameLabel);
        contentPane.add(nameField);
        contentPane.add(saveButton);

        SpringLayout.Constraints constraints;

        constraints = layout.getConstraints(nameLabel);
        constraints.setX(Spring.constant(10));
        constraints.setY(Spring.constant(10));

        constraints = layout.getConstraints(nameField);
        constraints.setX(Spring.sum(Spring.constant(10), layout.getConstraint(SpringLayout.EAST, nameLabel)));
        constraints.setY(Spring.constant(10));

        constraints = layout.getConstraints(saveButton);
        constraints.setX(Spring.constant(10));
        constraints.setY(Spring.sum(Spring.constant(50), layout.getConstraint(SpringLayout.SOUTH, nameField)));

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SpringLayoutExample example = new SpringLayoutExample();
            example.setVisible(true);
        });
    }
}
