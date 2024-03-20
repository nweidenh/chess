package clientOpps;

import ui2.EscapeSequences2;

import java.util.Objects;
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
            try {
                result = preClient.eval(line);
                System.out.print(result);
                while (preClient.hasAuth()){
                    printPrompt();
                    line = scanner.nextLine();
                    try {
                        result = postClient.eval(line);
                        System.out.print(result);
                        if(Objects.equals(result, "You logged out")){
                            preClient.nullifyAuth();
                        }
                    } catch (Throwable e) {
                        var msg = e.toString();
                        System.out.print(msg);
                    }
                }
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n" + EscapeSequences2.RESET_BG_COLOR + ">>> " + EscapeSequences2.SET_BG_COLOR_BLACK);
    }

}
