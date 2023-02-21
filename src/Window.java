import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public static void main(String[] args) {
        new Window();
    }

    public Window() {
        GraphicsPanel gp = new GraphicsPanel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        getContentPane().add(gp);
        pack();
    }
}