package server.data;

/**
 * Created by Alex on 16.10.2016.
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import model.dao.ScoreDao;
import static server.auth.TokenContainer.writeUsersJson;
import static server.auth.TokenContainer.writeTopNJson;

@Path("/data")
public class DataProvider {
    @NotNull
    private static final Logger log = LogManager.getLogger(DataProvider.class);
    //curl -i
    //     -X GET
    // "localhost:8080/data/users"
    @GET
    @Produces("application/json")
    @Path("users")
    public Response getUsers(){
        log.info("List of online users was sent");
        return Response.ok("Logged Users: " + writeUsersJson()).build();
    }

    @GET
    @Produces("application/json")
    @Path("leaderboard")
    public Response getLeaderboard(@QueryParam("number") int num){
        log.info("Top users : " + writeTopNJson(num));
        return Response.ok(("Top users : " + writeTopNJson(num))).build();
    }

}