package main.service;
import main.controller.ResponseDTO.LoginResponseDTO;
import main.domain.*;
import main.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDTO login(String email, String password) throws Exception {

        User user = userRepository.findByEmail(email);

        if (user != null) {
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new Exception("Bad credentials");
            }
            if( !user.getIsEnabled()){
                throw new Exception("Cont inactiv!");
            }

            return new LoginResponseDTO(user.getId(), user.getRole().name());
        }

        throw new Exception("Bad credentials");
    }

    @Override
    public void createVerificationToken(User user, String token) {

        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
       return tokenRepository.findByToken(token);
    }

    @Override
    public void enableUser(User user) {
        User userToActivate = userRepository.findByEmail(user.getEmail());
        user.setIsEnabled(true);
        userRepository.save(userToActivate);
    }


}