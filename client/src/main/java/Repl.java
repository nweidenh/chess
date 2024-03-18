import ui.EscapeSequences;

import java.util.Scanner;

public class Repl{
    private final PreLogin preClient;
    private final PostLogin postClient;

    public Repl(String serverUrl) {
        preClient = new PreLogin(serverUrl);
        postClient = new PostLogin(serverUrl);
    }

    public void run() {
        System.out.println("Welcome to Chess 240");
        System.out.print(preClient.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();
            if (preClient.hasAuth()){
                try {
                    result = postClient.eval(line);
                    System.out.print(result);
                } catch (Throwable e) {
                    var msg = e.toString();
                    System.out.print(msg);
                }
            } else {
                try {
                    result = preClient.eval(line);
                    System.out.print(result);
                } catch (Throwable e) {
                    var msg = e.toString();
                    System.out.print(msg);
                }
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n" + EscapeSequences.RESET_BG_COLOR + ">>> " + EscapeSequences.SET_BG_COLOR_BLACK);
    }

}
