package presentation.rest;

import domain.service.RoverService;
import exchange.RoverAPIResource;
import utils.exceptions.InputFormatException;
import utils.log.Logged;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

public class RoverAPI implements RoverAPIResource, Logged {

     @Inject RoverService roverService;

    public Response navigate(String input) {

        logger().debug("[RoverAPI] Navigate called with input: " + input);
        try {
            return Response.ok(roverService.navigate(input)).build();
        } catch (InputFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
