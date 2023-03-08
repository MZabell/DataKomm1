import javax.swing.*;

public class Window extends JFrame {
    public Window() {
        GraphicsPanel gp = new GraphicsPanel();

        setTitle("Mail Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        getContentPane().add(gp);
        pack();
    }

    public static void main(String[] args) {
        new Window();
    }
}