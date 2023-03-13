import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

// Class that contains the panel, which is responsible for the GUI graphics and GUI functionality.
public class GraphicsPanel extends JPanel {

    ArrayList<JTextField> textFields = new ArrayList<>();
    ArrayList<File> attachments = new ArrayList<>();
    JTextArea messageField = new JTextArea();

    public GraphicsPanel() {
        setVisible(true);

        // Size of panel
        setPreferredSize(new Dimension(800, 800));

        // Utilizes the SpringLayout manager for clean and tidy GUI.
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        // Define labels for textfields and area.
        String[] labels = {"Server: ", "From: ", "To: ", "Subject: ", "Message: "};
        messageField.setPreferredSize(new Dimension(500, 500));

        // Create and initialize GUI elements
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
        JButton attachButton = new JButton("Attach");
        add(attachButton);
        add(sendButton);

        // Add button listeners for button functionality
        attachButton.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileNameExtensionFilter("Images", "jpeg", "png"));
            int r = fc.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                attachments.add(fc.getSelectedFile());
                attachButton.setText("Attach: " + attachments.size());
            }
        });

        sendButton.addActionListener(e -> new ClientConn(getTextFields(), messageField, attachments));

        // Layout manager creates GUI grid.
        SpringUtilities.makeCompactGrid(this, 6, 2, 6, 6, 6, 6);
    }

    // Create getter for textfields
    public ArrayList<JTextField> getTextFields() {
        return textFields;
    }
}
