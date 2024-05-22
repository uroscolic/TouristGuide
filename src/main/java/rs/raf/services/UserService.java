package rs.raf.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.apache.commons.codec.digest.DigestUtils;
import rs.raf.entities.User;
import rs.raf.repositories.user.UserRepository;

import javax.inject.Inject;
import java.util.Date;

public class UserService {

    @Inject
    UserRepository userRepository;

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
                .withClaim("type", user.getUserType().ordinal())
                .sign(algorithm);
    }

    public boolean isAuthorized(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String email = jwt.getSubject();
//        jwt.getClaim("role").asString();

        User user = this.userRepository.findUser(email);

        return user != null;
    }
}
