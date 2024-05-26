package rs.raf.repositories.user;

import rs.raf.entities.User;
import rs.raf.requests.UpdateUserInfoRequest;

import java.util.List;

public interface UserRepository {

    User findUser(String email);
    User addUser(User user);
    List<User> allUsers();
    User changeActiveForUser(String email);
    User changeUserInfo(UpdateUserInfoRequest updateUserInfoRequest);


}
