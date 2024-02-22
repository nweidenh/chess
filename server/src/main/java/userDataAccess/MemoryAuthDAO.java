package userDataAccess;

import model.AuthData;
import model.UserData;

import java.util.UUID;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO{
    private static HashMap<String, AuthData> tokens = new HashMap<>();
    public AuthData createAuth (String username){
        AuthData authToken = new AuthData(UUID.randomUUID().toString(), username);
        tokens.put(authToken.username(), authToken);
        return authToken;
    }
    public AuthData getAuth (String username){
        if(tokens.get(username) != null){
            return tokens.get(username);
        } else return null;
    }
    public void deleteAllAuths(){
        tokens.clear();
    }
}
