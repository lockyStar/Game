package server;

import org.jetbrains.annotations.NotNull;
import server.api.ApiServlet;

import static org.eclipse.persistence.expressions.ExpressionOperator.NotNull;

/**
 * Created by Alex on 16.10.2016.
 */
public class Server {
    public static void main(@NotNull String[] args) throws Exception {
        ApiServlet.start();
}

}
