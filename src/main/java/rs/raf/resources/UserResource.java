package rs.raf.resources;

import rs.raf.entities.User;
import rs.raf.requests.LoginRequest;
import rs.raf.requests.UpdateUserInfoRequest;
import rs.raf.services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON )
    public Response all(@QueryParam("page") @DefaultValue("1") int page,
                        @QueryParam("size") @DefaultValue("10") int size) {
        List<User> users = this.userService.allUsers(page, size);
        long totalUsers = this.userService.getTotalUserCount();

        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        response.put("totalUsers", totalUsers);
        response.put("currentPage", page);
        response.put("totalPages", (int) Math.ceil((double) totalUsers / size));

        return Response.ok(response).build();
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(@Valid User user) {
        if(userService.findUser(user.getEmail()) != null)
            return Response.status(422, "Unprocessable Entity").entity("User with this email already exists.").build();
        return Response.ok(this.userService.registerUser(user)).build();
    }
    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("email") String email) {
        return Response.ok(this.userService.findUser(email)).build();
    }


    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Valid UpdateUserInfoRequest updateUserInfoRequest) {
        if(userService.findUser(updateUserInfoRequest.getNewEmail()) != null && !updateUserInfoRequest.getOldEmail().equals(updateUserInfoRequest.getNewEmail()))
            return Response.status(422, "Unprocessable Entity").entity("User with this email already exists.").build();
        return Response.ok(this.userService.changeUserInfo(updateUserInfoRequest)).build();
    }

    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginRequest loginRequest)
    {
        Map<String, String> response = new HashMap<>();

        String jwt = this.userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (jwt == null) {
            response.put("message", "These credentials do not match our records");
            System.out.println("These credentials do not match our records: " + loginRequest.getEmail() + " " + loginRequest.getPassword());
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }


        response.put("jwt", jwt);
        response.put("userType", this.userService.findUser(loginRequest.getEmail()).getUserType().toString());
        response.put("name", this.userService.findUser(loginRequest.getEmail()).getName());
/*
        response.put("email", loginRequest.getEmail());
        response.put("surname", this.userService.findUser(loginRequest.getEmail()).getSurname());
*/
        return Response.ok(response).build();
    }

    @PUT
    @Path("/changeActive/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeActive(@PathParam("email") String email) {
        return Response.ok(this.userService.changeActiveForUser(email)).build();
    }

}
