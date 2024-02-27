package userDataAccess;

import model.AuthData;
import model.UserData;

import java.util.UUID;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO{

    //Create the HashMap with keys as the authToken and values as the authData
    final private static HashMap<String, AuthData> tokens = new HashMap<>();

    //Create an authToken and put it in the database
    public AuthData createAuth (String username) throws DataAccessException{
        AuthData authToken = new AuthData(UUID.randomUUID().toString(), username);
        tokens.put(authToken.authToken(), authToken);
        return authToken;
    }

    //Verify that an authToken actually exists
    public AuthData getAuth (String authToken) throws DataAccessException{
        if(tokens.get(authToken) != null){
            return tokens.get(authToken);
        } else {
            throw new DataAccessException(401, "Error: unauthorized");
        }
    }

    public HashMap<String, AuthData> getAuths () throws DataAccessException{
        return tokens;
    }

    //Delete one auth token
    public void deleteAuth(String authToken) throws DataAccessException{
        tokens.remove(authToken);
    }

    //Delete all authTokens from the database
    public void deleteAllAuths() throws DataAccessException{
        tokens.clear();
    }
}
