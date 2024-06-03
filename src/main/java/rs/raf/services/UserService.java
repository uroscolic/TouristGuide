package rs.raf.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.apache.commons.codec.digest.DigestUtils;
import rs.raf.entities.User;
import rs.raf.entities.UserType;
import rs.raf.repositories.user.UserRepository;
import rs.raf.requests.UpdateUserInfoRequest;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public class UserService {

    @Inject
    UserRepository userRepository;

    public User registerUser(User user) {
        return this.userRepository.addUser(user);
    }
    public User changeActiveForUser(String email) {
        return this.userRepository.changeActiveForUser(email);
    }
    public User findUser(String email) {
        return this.userRepository.findUser(email);
    }
    public User changeUserInfo(UpdateUserInfoRequest updateUserInfoRequest) {
        return this.userRepository.changeUserInfo(updateUserInfoRequest);
    }

    public List<User> allUsers(int page, int size) {
        return this.userRepository.allUsers(page, size);
    }

    public long getTotalUserCount() {
        return userRepository.countUsers();
    }

    public String login(String email, String password)
    {
        String hashedPassword = DigestUtils.sha256Hex(password);

        User user = this.userRepository.findUser(email);
        if (user == null || !user.getPassword().equals(hashedPassword) || !user.getActive()) {
            return null;
        }

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000); // One day

        Algorithm algorithm = Algorithm.HMAC256("secret");

        // JWT-om mozete bezbedono poslati informacije na FE
        // Tako sto sve sto zelite da posaljete zapakujete u claims mapu
        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(email)
                .withClaim("user_type", user.getUserType().ordinal())
                .sign(algorithm);
    }

    public boolean isAuthorized(String token, boolean adminRequired){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String email = jwt.getSubject();
        int type = jwt.getClaim("user_type").asInt();
        UserType userType = UserType.values()[type];


        User user = this.userRepository.findUser(email);
        if(adminRequired) {
            return user != null && userType == UserType.ADMIN && user.getActive();
        }
        return user != null && user.getActive();
    }
}
