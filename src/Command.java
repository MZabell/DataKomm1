public class Command {

    String command;
    int response;

    public Command(String command) {
        this.command = command;
    }

    public boolean commandACK(String response) {
        String[] data = response.split(" ");
        this.response = Integer.parseInt(data[0]);

        return this.response >= 220 && this.response <= 354;
    }
}
