// Model class that can contain a command and response
public class Command {

    String command;
    int response;

    public Command(String command) {
        this.command = command;
    }

    // commandACK ensures successful commands, by only accepting positive reply codes from the server I.E. numbers from 220 to 354.
    public boolean commandACK(String response) {
        String[] data = response.split(" ");
        this.response = Integer.parseInt(data[0]);

        return this.response >= 220 && this.response <= 354;
    }

    // toString override to print or write Command object as String.
    @Override
    public String toString() {
        return command;
    }
}
