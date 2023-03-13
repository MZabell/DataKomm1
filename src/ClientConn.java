import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;

// Class contains connection between client and server.
// SMTP commands using MIME format is also found here. See createCommandList.
public class ClientConn {

    ArrayList<Command> commands = new ArrayList<>();
    Socket socket;
    DataOutputStream output;
    DataInputStream input;

    public ClientConn(ArrayList<JTextField> textFields, JTextArea message, ArrayList<File> attachments) {
        try {
            // Server IP and PORT is read from text-field and split with delimiter ":".
            String[] server = textFields.get(0).getText().split(":");
            String IP = server[0];
            int PORT = Integer.parseInt(server[1]);

            // New socket is created and connected to server.
            socket = new Socket(IP, PORT);
            System.out.println("Connected");

            // Datastreams are created to get input and output to/from server
            output = new DataOutputStream(socket.getOutputStream());
            input = new DataInputStream(socket.getInputStream());
            // BufferedReader makes input readable
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            // Commandlist is made and processed.
            createCommandList(textFields, message, attachments);
            for (Command c : commands) {
                String response = reader.readLine();
                // Response is checked to ensure successful commands
                if (c.commandACK(response)) {
                    output.writeBytes(c.toString());
                    System.out.println("SERVER: " + response);
                    System.out.println("CLIENT: " + c);
                } else {
                    System.out.println("CLIENT: Bad command. Aborting...");
                    break;
                }
            }

            // Close up all streams and connections
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

    // Creates commandlist from Command objects. Data in this function uses format MIME 1.0
    private void createCommandList(ArrayList<JTextField> textFields, JTextArea message, ArrayList<File> attachments) throws IOException {
        String from = textFields.get(1).getText();
        String to = textFields.get(2).getText();
        String subject = textFields.get(3).getText();

        // Add start commands
        commands.add(new Command("HELO localhost\n"));
        commands.add(new Command("MAIL FROM: <" + from + ">\n"));
        commands.add(new Command("RCPT TO: <" + to + ">\n"));
        commands.add(new Command("DATA\n"));

        // Add headers
        String body = "From: " + from + "\n" +
                "To: " + to + "\n" +
                "Subject: " + subject + "\n" +
                "MIME-Version: 1.0\n" +
                "Content-Type: multipart/mixed; " +
                "boundary=\"sep\"\n\n";

        // Loop for handling multiple attachments
        for (File f : attachments) {
            String[] data = f.getName().split("\\.");
            body = body + "--sep\n" + "Content-Type: image/" + data[1] + "; name=\"" + f.getName() + "\"\n" +
                    "Content-Disposition: attachment; filename=\"" + f.getName() + "\"\n" +
                    "Content-Transfer-Encoding: base64\n\n" +
                    // Encodes file to base64
                    Base64.getEncoder().encodeToString(Files.readAllBytes(f.toPath())) + "\n\n--sep\n";
        }
        if (attachments.isEmpty()) {
            body = body + "--sep\n";
        }
        // Add the message in plain text
        body = body + "Content-Type: text/plain; charset=us-ascii\n\n" + message.getText() + "\n\n--sep--\n" + ".\n";
        commands.add(new Command(body));
        commands.add(new Command("QUIT"));
    }
}