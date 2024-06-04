package rs.raf.resources;

import rs.raf.entities.Destination;
import rs.raf.services.DestinationService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/destinations")
public class DestinationResource {

    @Inject
    private DestinationService destinationService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(@QueryParam("page") @DefaultValue("1") int page,
                        @QueryParam("size") @DefaultValue("100") int size) {
        List<Destination> destinations = this.destinationService.allDestinations(page, size);
        long totalDestinations = this.destinationService.countDestinations();
        Map<String, Object> response = new HashMap<>();
        response.put("destinations", destinations);
        response.put("totalDestinations", totalDestinations);
        response.put("currentPage", page);
        response.put("totalPages", (int) Math.ceil((double) totalDestinations / size));

        return Response.ok(response).build();
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("name") String name) {
        return Response.ok(this.destinationService.findDestination(name)).build();
    }
    @GET
    @Path("/by/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") Long id) {
        return Response.ok(this.destinationService.findDestination(id)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid Destination destination) {
        if(destinationService.findDestination(destination.getName()) != null)
            return Response.status(422, "Unprocessable Entity").entity("Destination with this name already exists.").build();
        return Response.ok(this.destinationService.addDestination(destination)).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Valid Destination destination) {
        Destination destination1 = destinationService.findDestination(destination.getName());
        if(destination1 != null && !destination1.getId().equals(destination.getId()))
            return Response.status(422, "Unprocessable Entity").entity("Destination with this name already exists.").build();
        return Response.ok(this.destinationService.updateDestination(destination)).build();
    }

    @DELETE
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("name") String name) {
        Destination destination = destinationService.findDestination(name);
        if(destination == null)
            return Response.status(422, "Unprocessable Entity").entity("Destination with this name does not exist.").build();
        return Response.ok(this.destinationService.removeDestination(destination)).build();
    }

}
