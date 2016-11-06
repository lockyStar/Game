package server.profile;

/**
 * Created by Alex on 23.10.2016.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.auth.Authentication;
import server.auth.Authorized;
import server.auth.Token;
import server.auth.TokenContainer;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import static server.auth.TokenContainer.getUserByToken;
import static server.auth.TokenContainer.renameUser;

@Path("/profile")
public class profileSettings {
    private static final Logger log = LogManager.getLogger(Authentication.class);


    // curl -X POST
    //      -H "Authorization: Bearer {token}"
    //      -H "Content-Type: application/x-www-form-urlencoded"
    //      -H "Host: localhost:8080"
    //      -d "name=admin"
    // "http://localhost:8080/profile/rename"
    @Authorized
    @POST
    @Path("name")
    @Produces("text/plain")
    public Response renameUser(@HeaderParam(HttpHeaders.AUTHORIZATION) String rawToken,
                                   @FormParam("name") String newName){

        String user = TokenContainer.renameUser(new Token(rawToken), newName);
        if (user.equals(newName)){
            log.info("Cannot rename this user, " + newName + " has alredy registred");
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        log.info("User '{}' was renamed to '{}'", user, newName);

        return Response.ok("User " + user + " was renamed to " + newName).build();
    }



}
