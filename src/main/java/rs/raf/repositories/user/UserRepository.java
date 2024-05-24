package rs.raf.repositories.user;

import rs.raf.entities.User;
import rs.raf.requests.UpdateUserInfoRequest;

import java.util.List;

public interface UserRepository {

    public User findUser(String email);
    public User addUser(User user);
    public List<User> allUsers();
    public User changeActiveForUser(String email);
    public User changeUserInfo(UpdateUserInfoRequest updateUserInfoRequest);


}
