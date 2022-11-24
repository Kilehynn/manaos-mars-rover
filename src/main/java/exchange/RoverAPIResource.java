package exchange;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)

@Path("/rover")
public interface RoverAPIResource {

    @POST @Path("/navigate")
    Response navigate(String input);
}
