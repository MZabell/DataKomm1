import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel {

    public GraphicsPanel() {
        setVisible(true);
        setPreferredSize(new Dimension(800, 800));
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        ArrayList<JTextField> textFields = new ArrayList<>();
        String[] labels = {"Server: ", "From: ", "To: ", "Subject: ", "Message: "};
        JTextArea messageField = new JTextArea();
        messageField.setPreferredSize(new Dimension(500,500));

        for (int i = 0; i < labels.length; i++) {
            JTextField tf = new JTextField();
            tf.setPreferredSize(new Dimension(500,25));
            textFields.add(tf);

            JLabel label = new JLabel(labels[i], JLabel.TRAILING);
            label.setPreferredSize(new Dimension(50,20));
            add(label);
            label.setLabelFor(tf);
            add(tf);
        }
        JLabel label = new JLabel(labels[4], JLabel.TRAILING);
        add(label);
        label.setLabelFor(messageField);
        add(messageField);
        JButton send = new JButton();
        add(send);

        SpringUtilities.makeCompactGrid(this, labels.length, 2, 6, 6 ,6 ,6);
    }
}
