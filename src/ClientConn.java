import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientConn {

    ArrayList<Command> commands = new ArrayList<>();
    Socket socket;
    DataOutputStream output;
    DataInputStream input;

    public ClientConn(ArrayList<JTextField> textFields, JTextArea message) {
        try {
            String[] server = textFields.get(0).getText().split(":");
            String IP = server[0];
            int PORT = Integer.parseInt(server[1]);
            socket = new Socket(IP, PORT);
            System.out.println("Connected");


            output = new DataOutputStream(socket.getOutputStream());
            input = new DataInputStream(socket.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));


            createCommandList(textFields, message);
            for (Command c : commands) {
                String response = reader.readLine();
                if (c.commandACK(response)) {
                    output.writeBytes(c.command);
                    System.out.println("SERVER: " + response);
                    System.out.println("CLIENT: " + c.command);
                } else {
                    System.out.println("CLIENT: Bad command. Aborting...");
                    break;
                }
            }

            input.close();
            output.close();
            socket.close();

            System.out.println("Message delivered. Connection closed");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            textFields.get(0).setBorder(new LineBorder(Color.RED, 3));
        }
    }

    private void createCommandList(ArrayList<JTextField> textFields, JTextArea message) {
        String from = textFields.get(1).getText();
        String to = textFields.get(2).getText();
        String subject = textFields.get(3).getText();

        commands.add(new Command("HELO localhost\n"));
        commands.add(new Command("MAIL FROM: <" + from + ">\n"));
        commands.add(new Command("RCPT TO: <" + to + ">\n"));
        commands.add(new Command("DATA\n"));
        commands.add(new Command("From: " + from + "\n" +
                "To: " + to + "\n" +
                "Subject: " + subject + "\n" +
                message.getText() + "\n" + ".\n"));
        commands.add(new Command("QUIT"));
    }
}
