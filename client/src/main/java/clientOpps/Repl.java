package clientOpps;

import static ui2.EscapeSequences2.*;

import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.Error;
import websocket.NotificationHandler;

import java.util.Objects;
import java.util.Scanner;

public class Repl implements NotificationHandler{
    private final PreLogin preClient;
    private final PostLogin postClient;
    private final GameUi webSocket;

    public Repl(String serverUrl) {
        preClient = new PreLogin(serverUrl);
        postClient = new PostLogin(serverUrl, this);
        webSocket = new GameUi(serverUrl, this);
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
                        postClient.authToken = preClient.getAuthToken();
                        webSocket.authToken = preClient.getAuthToken();
                        result = postClient.eval(line);
                        System.out.print(result);
                        if(Objects.equals(result, "You logged out")){
                            preClient.nullifyAuth();
                        } while(postClient.inWs){
                            webSocket.gameID = postClient.gameID;
                            printPrompt();
                            line = scanner.nextLine();
                            try {
                                result = webSocket.eval(line);
                                System.out.println(result);
                                if (Objects.equals(result, "You left the game")){
                                    postClient.inWs = false;
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
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n" + RESET_BG_COLOR + ">>> " + SET_BG_COLOR_BLACK);
    }

    @Override
    public void notify(Notification notification) {
        System.out.println(notification.getMessage());
        printPrompt();
    }
    @Override
    public void notifyLoad(LoadGame loadedGame) {
        System.out.println(loadedGame.getBoard().toString());
        printPrompt();
    }

    @Override
    public void notifyError(Error errorMessage) {
        System.out.println(errorMessage);
        printPrompt();
    }
}
