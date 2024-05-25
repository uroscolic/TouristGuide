package rs.raf.resources;

import rs.raf.entities.Activity;
import rs.raf.services.ActivityService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/activities")
public class ActivityResource {

    @Inject
    private ActivityService activityService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allActivities() {
        System.out.println("All activities");
        return Response.ok(this.activityService.allActivities()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid Activity activity) {
        if(activityService.findActivity(activity.getName()) != null)
            return Response.status(422, "Unprocessable Entity").entity("Activity with this name already exists.").build();
        return Response.ok(this.activityService.addActivity(activity)).build();
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("name") String name) {
        return Response.ok(this.activityService.findActivity(name)).build();
    }


}
