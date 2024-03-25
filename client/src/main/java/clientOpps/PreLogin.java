package clientOpps;

import model.UserData;

import java.util.Arrays;

public class PreLogin {

        private String username = null;
        private String password = null;
        private String email = null;
        private final ServerFacade server;
        private final String serverUrl;
        private String authToken = null;

        public PreLogin(String serverUrl) {
            server = new ServerFacade(serverUrl);
            this.serverUrl = serverUrl;
        }

        public String eval(String input) {
            try {
                var tokens = input.toLowerCase().split(" ");
                var cmd = (tokens.length > 0) ? tokens[0] : "help";
                var params = Arrays.copyOfRange(tokens, 1, tokens.length);
                return switch (cmd) {
                    case "register" -> register(params);
                    case "login" -> login(params);
                    case "quit" -> "quit";
                    default -> help();
                };
            } catch (ResponseException ex) {
                return ex.getMessage();
            }
        }

        public String register(String... params) throws ResponseException {
            if (params.length == 3) {
                username = params[0];
                password = params[1];
                email = params[2];
                authToken = server.register(new UserData(username, password, email));
                return String.format("You registered as %s.", username);
            }
            throw new ResponseException(400, "Expected: <USERNAMER> <PASSWORD> <EMAIL>");
        }


        public String login(String... params) throws ResponseException {
            if (params.length == 2) {
                username = params[0];
                password = params[1];
                authToken = server.login(new UserData(username, password, null));
                return String.format("You logged in as %s.", username);
            }
            throw new ResponseException(400, "Expected: <USERNAMER> <PASSWORD>");
        }

        public String help() {
                return """
                        register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                        login <USERNAME> <PASSWORD> - to play chess
                        quit - playing chess
                        help - with possible commands
                        """;
        }

        public Boolean hasAuth(){
            if (this.authToken != null){
                return Boolean.TRUE;
            } return Boolean.FALSE;
        }

        public void nullifyAuth(){
            this.authToken = null;
        }
}
