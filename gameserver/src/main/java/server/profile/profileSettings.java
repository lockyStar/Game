package server.profile;

/**
 * Created by Alex on 23.10.2016.
 */
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.auth.Authentication;
import server.auth.Authorized;
import server.auth.Token;
import server.auth.TokenContainer;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;


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
        if (newName == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        String user = TokenContainer.renameUser(new Token(rawToken), newName);
        if (user.equals(newName)){
            log.info("Cannot rename this user, " + newName + " has already registered");
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        log.info("User '{}' was renamed to '{}'", user, newName);

        return Response.ok("User " + user + " was renamed to " + newName).build();
    }

    @Authorized
    @POST
    @Path("password")
    @Produces("text/plain")
    public Response changePassword(@HeaderParam(HttpHeaders.AUTHORIZATION) String rawToken,
                                @FormParam("password") String newPassword){
        if (newPassword == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        String oldpass = TokenContainer.changePassword(new Token(rawToken),newPassword);
        log.info("User changed password from '{}' to '{}'", oldpass, newPassword);
        return Response.ok("User changed password from " + oldpass +  " to " + newPassword).build();
    }



    @Authorized
    @POST
    @Path("email")
    @Produces("text/plain")
    public Response changeEmail(@HeaderParam(HttpHeaders.AUTHORIZATION) String rawToken,
                                @FormParam("email") String newEmail) {
        if (!EmailChecker(newEmail)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        String oldemail = TokenContainer.changeEmail(new Token(rawToken), newEmail);
        log.info("User changed email from '{}' to '{}'", oldemail, newEmail);
        return Response.ok("User changed email from " + oldemail +  " to " + newEmail).build();
    }


    public boolean EmailChecker (String email){
        return EmailValidator.getInstance().isValid(email);
    }
}
