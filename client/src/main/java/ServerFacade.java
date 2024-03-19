import com.google.gson.Gson;
import model.*;

import java.io.*;
import java.net.*;

public class ServerFacade {

    private final String serverUrl;
    private static String authToken = null;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public String register(UserData user) throws ResponseException{
        var path = "/user";
        var response = this.makeRequest("POST", path, user, AuthData.class, null);
        authToken = response.authToken();
        return authToken;
    }

    public String login(UserData user) throws ResponseException{
        var path = "/session";
        var response = this.makeRequest("POST", path, user, AuthData.class, null);
        authToken = response.authToken();
        return authToken;
    }

    public void logout() throws ResponseException{
        var path = "/session";
        var response = this.makeRequest("DELETE", path, null, null, authToken);
    }

    public GameData[] listGames() throws ResponseException {
        var path = "/game";
        record listGameResponse(GameData[] game) {
        }
        var response = this.makeRequest("GET", path, null, listGameResponse.class, null);
        return response.game;
    }

    public int createGame(GameData gameName) throws ResponseException{
        var path = "/game";
        var response = this.makeRequest("POST", path, gameName, int.class, authToken);
        return response;
    }

    public void deleteAllGames() throws ResponseException {
        var path = "/db";
        this.makeRequest("DELETE", path, null, null, null);
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, Object header) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeBody(request, http, header);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http, Object header) throws IOException {
        if (header != null){
            http.addRequestProperty("authorization", authToken);
        }
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
