package server.auth;

/**
 * Created by Alex on 16.10.2016.
 */

import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import javax.ws.rs.core.HttpHeaders;

import static server.auth.TokenContainer.*;


@Path("/auth")
public class Authentication {
    private static final Logger log = LogManager.getLogger(Authentication.class);
    // curl -i
    //      -X POST
    //      -H "Content-Type: application/x-www-form-urlencoded"
    //      -H "Host: {IP}:8080"
    //      -d "login={}&password={}"
    // "{IP}:8080/auth/register"
    @POST
    @Path("register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@FormParam("user") String user,
                             @FormParam("password") String password) {

        if (user == null || password == null) {

            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User newUser = new User(user)
                .setPassword(password);

        log.info("New temp user created {} ", user);
        if (!TokenContainer.addUser(newUser)) {
            log.info("Not Acceptable you ruined registration");
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        log.info("New user '{}' registered", user);
        return Response.ok("User " + user + " registered.").build();
    }



    // curl -X POST
    //      -H "Content-Type: application/x-www-form-urlencoded"
    //      -H "Host: localhost:8080"
    //      -d "login=admin&password=admin"
    // "http://localhost:8080/auth/login"
    @POST
    @Path("login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response authenticateUser(@FormParam("user") String user,
                                     @FormParam("password") String password) {

        if (user == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        //logCred();
        try {
            // Authenticate the user using the credentials provided
            User currentUser = getUserByString(user);
            log.info(currentUser.getName() + "not bad request");
            if ((currentUser.getName() == null)||(!authenticate(currentUser,user, password))) {
                log.info("Bad password");
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            // Issue a token for the user
            Token token = issueToken(currentUser);
            log.info("User '{}' logged in, token '{}'", user, token.getToken());

            // Return the token on the response
            return Response.ok(Long.toString(token.getToken())).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }


    // curl -X POST
    //      -H "Authorization: Bearer {token}"
    //      -H "Host: localhost:8080"
    // "http://localhost:8080/auth/logout"
    @Authorized
    @POST
    @Path("logout")
    @Produces("text/plain")
    public Response deactivateUser(@HeaderParam(HttpHeaders.AUTHORIZATION) String rawToken){

        //remove token-user pair from maps
        rawToken = rawToken.substring("Bearer".length()).trim();
        Long token = Long.parseLong(rawToken);
        String user = removeToken(token);

        log.info("User logged out");

        return Response.ok("User logged out successfully.").build();
    }





}