import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel {

    ArrayList<JTextField> textFields = new ArrayList<>();
    JTextArea messageField = new JTextArea();
    public GraphicsPanel() {
        setVisible(true);
        setPreferredSize(new Dimension(800, 800));
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        String[] labels = {"Server: ", "From: ", "To: ", "Subject: ", "Message: "};
        messageField.setPreferredSize(new Dimension(500, 500));

        for (int i = 0; i < 4; i++) {
            JTextField tf = new JTextField();
            tf.setPreferredSize(new Dimension(500, 25));
            textFields.add(tf);

            JLabel label = new JLabel(labels[i], JLabel.TRAILING);
            label.setPreferredSize(new Dimension(50, 20));
            add(label);
            label.setLabelFor(tf);
            add(tf);
        }
        JLabel label = new JLabel(labels[4], JLabel.TRAILING);
        add(label);
        label.setLabelFor(messageField);
        add(messageField);
        JButton sendButton = new JButton("Send");
        JButton clearButton = new JButton("Clear");
        add(clearButton);
        add(sendButton);

        clearButton.addActionListener(e -> clear());

        sendButton.addActionListener(e -> new ClientConn(getTextFields(), messageField));

        SpringUtilities.makeCompactGrid(this, 6, 2, 6, 6, 6, 6);
    }

    public ArrayList<JTextField> getTextFields() {
        return textFields;
    }

    void clear() {
        for (JTextField tf : textFields) {
            tf.setText("");
            tf.setBorder(new LineBorder(Color.BLACK));
        }
        messageField.setText("");
    }
}
