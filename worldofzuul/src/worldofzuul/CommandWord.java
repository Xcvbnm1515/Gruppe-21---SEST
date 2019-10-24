package worldofzuul;

public enum CommandWord {
    GO("go"), QUIT("quit"), SOS("SOS"), UNKNOWN("?"), TAG("tag"), TASKE("taske");

    private String commandString;

    CommandWord(String commandString) {
        this.commandString = commandString;
    }

    @Override
    public String toString() {
        return commandString;
    }
}
