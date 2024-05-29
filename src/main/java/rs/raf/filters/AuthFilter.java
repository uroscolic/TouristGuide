package rs.raf.filters;


import rs.raf.entities.Destination;
import rs.raf.resources.DestinationResource;
import rs.raf.resources.UserResource;
import rs.raf.services.DestinationService;
import rs.raf.services.SubjectService;
import rs.raf.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Inject
    UserService userService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

//        if (true) return;
        System.out.println("Filtering");
        if (!this.isAuthRequired(requestContext)) {
            return;
        }
        System.out.println("Auth required");
        try {
            String token = requestContext.getHeaderString("Authorization");
            if(token != null && token.startsWith("Bearer ")) {
                token = token.replace("Bearer ", "");
            }

            if (!this.userService.isAuthorized(token)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } catch (Exception exception) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean isAuthRequired(ContainerRequestContext req) {
        System.out.println(req.getUriInfo().getPath());
        if (req.getUriInfo().getPath().contains("login")) {
            return false;
        }

        List<Object> matchedResources = req.getUriInfo().getMatchedResources();
        for (Object matchedResource : matchedResources) {
            System.out.println("-----------------");
            System.out.println(matchedResource.getClass().getName());
            if (matchedResource instanceof UserResource) {
                return true;
            }
        }
        System.out.println("No destination resource");

        return false;
    }
}
